package Project.Views;

import Project.CommandlineViews.TileGridPrinter;
import Project.Controllers.CheatCodeController;
import Project.Controllers.GameController;
import Project.Enums.BuildingEnum;
import Project.Enums.UnitEnum;
import Project.Models.Cities.City;
import Project.Models.Game;
import Project.Models.Location;
import Project.Models.Units.Unit;
import Project.Utils.CommandException;
import Project.Utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameView implements ViewController {
    private static GameView instance;
    public Button btn;
    public MenuItem happinessIncrease;
    @FXML
    private VBox enterInfoVbox;
    private int increaseAmount;
    @FXML
    private BorderPane enterInfoPane;
    @FXML
    private Menu cheat;
    @FXML
    private MenuItem goldIncrease;
    @FXML
    private Menu foodIncrease;
    @FXML
    private Menu unitSpawn;
    @FXML
    private Menu productFinish;
    @FXML
    private MenuItem beakerIncrease;
    @FXML
    private Menu teleportation;
    @FXML
    private Menu productionIncrease;
    @FXML
    private MenuItem tileReveal;
    @FXML
    private Menu movementIncrease;
    @FXML
    private Menu cityHeal;
    @FXML
    private Menu unitHeal;
    @FXML
    private MenuItem technologiesUnlock;
    @FXML
    private Menu buildingsAdd;
    @FXML
    private ScrollPane scrollPane;
    private Game game;
    @FXML
    private Pane tilePane;
    @FXML
    private Pane buildingsPane;
    @FXML
    private Pane unitsPane;
    private Spinner<Integer> spinner;
    private SpinnerValueFactory<Integer> valueFactory;
    private int locationX;
    private int locationY;

    public static GameView getInstance() {
        return instance;
    }

    public void initialize() {
        spinner = getSpinner(valueFactory);
        this.game = GameController.getGame();
        GameController.getGame().SetPage(this);
        System.out.println(GameController.getGame().getCurrentCivilization());
        instance = this;
        //todo : initialize
    }

    public Pane getTilePane() {
        return tilePane;
    }

    public Pane getBuildingsPane() {
        return buildingsPane;
    }

    public Pane getUnitsPane() {
        return unitsPane;
    }

    public Game getGame() {
        return game;
    }

    public void click() {
        System.err.println("clicked");
        btn.setVisible(false);
        tilePane.getChildren().addAll(game.getTileGrid().getHexes());
        System.out.print(new TileGridPrinter(game.getTileGrid(), 10, 10).print(new Location(6, 6)));
    }

    public void increaseGold() {
        enterInfoPane.setVisible(true);
        Button confirmButton = new Button("confirm");
        increaseAmount = 1;
        valueFactory.setValue(increaseAmount);
        confirmButton.setOnAction(actionEvent -> {
            CheatCodeController.getInstance().increaseGold(increaseAmount);
            enterInfoVbox.getChildren().removeAll(enterInfoVbox.getChildren());
            enterInfoPane.setVisible(false);
        });
        enterInfoVbox.getChildren().add(spinner);
        enterInfoVbox.getChildren().add(confirmButton);
    }

    private Spinner<Integer> getSpinner(SpinnerValueFactory<Integer> valueFactory) {
        Spinner<Integer> spinner = new Spinner<>();
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99);
        spinner.setValueFactory(valueFactory);
        spinner.valueProperty().addListener((observableValue, integer, t1) -> increaseAmount = spinner.getValue());
        return spinner;
    }

    public void increaseFood() {
        for (City city : game.getCurrentCivilization().getCities()) {
            MenuItem item = new MenuItem(city.getName());
            item.setOnAction(actionEvent -> {
                enterInfoPane.setVisible(true);
                Button confirmButton = new Button("confirm");
                increaseAmount = 1;
                valueFactory.setValue(increaseAmount);
                confirmButton.setOnAction(actionEvent1 -> {
                    CheatCodeController.getInstance().increaseFood(city, increaseAmount);
                    enterInfoVbox.getChildren().removeAll(enterInfoVbox.getChildren());
                    enterInfoPane.setVisible(false);
                });
                enterInfoVbox.getChildren().add(spinner);
                enterInfoVbox.getChildren().add(confirmButton);
            });
            foodIncrease.getItems().add(item);
        }
    }

    public void spawnUnit() {
        for (UnitEnum units : UnitEnum.values()) {
            MenuItem item = new MenuItem(units.name());
            item.setOnAction(actionEvent -> {
                enterInfoPane.setVisible(true);
                Button confirmButton = new Button("confirm");
                increaseAmount = 1;
                Text locationXText = new Text("location x:");
                Text locationYText = new Text("location y:");
                Spinner<Integer> ySpinner = getSpinner(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_HEIGHT));
                Spinner<Integer> xSpinner = getSpinner(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_WIDTH));
                xSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationX = xSpinner.getValue());
                ySpinner.valueProperty().addListener((observableValue, integer, t1) -> locationY = ySpinner.getValue());
                confirmButton.setOnAction(actionEvent1 -> {
                    try {
                        CheatCodeController.getInstance().spawnUnit(units, game.getCurrentCivilization(), new Location(locationX--, locationY--));
                    } catch (CommandException e) {
                        return;
                    } finally {
                        enterInfoVbox.getChildren().removeAll(enterInfoVbox.getChildren());
                        enterInfoPane.setVisible(false);
                    }
                });
                enterInfoVbox.getChildren().add(locationXText);
                enterInfoVbox.getChildren().add(xSpinner);
                enterInfoVbox.getChildren().add(locationYText);
                enterInfoVbox.getChildren().add(ySpinner);
                enterInfoVbox.getChildren().add(confirmButton);
            });
            unitSpawn.getItems().add(item);
        }
    }

    public void finishProduct() {
        for (City city : game.getCurrentCivilization().getCities()) {
            MenuItem item = new MenuItem(city.getName());
            item.setOnAction(actionEvent -> {
                try {
                    CheatCodeController.getInstance().finishProducts(city);
                } catch (CommandException e) {
                    System.out.println("couldn't finish products for city: " + city.getName());
                }
            });
            productFinish.getItems().add(item);
        }
    }

    public void increaseBeaker() {
        enterInfoPane.setVisible(true);
        Button confirmButton = new Button("confirm");
        increaseAmount = 1;
        valueFactory.setValue(increaseAmount);
        confirmButton.setOnAction(actionEvent -> {
            CheatCodeController.getInstance().increaseBeaker(increaseAmount);
            enterInfoVbox.getChildren().removeAll(enterInfoVbox.getChildren());
            enterInfoPane.setVisible(false);
        });
        enterInfoVbox.getChildren().add(spinner);
        enterInfoVbox.getChildren().add(confirmButton);
    }

    public void teleport() {
        for (Unit units : game.getCurrentCivilization().getUnits()) {
            MenuItem item = new MenuItem(units.getLocation() + " " + units.getType().name());
            item.setOnAction(actionEvent -> {
                enterInfoPane.setVisible(true);
                Button confirmButton = new Button("confirm");
                increaseAmount = 1;
                Text locationXText = new Text("location x:");
                Text locationYText = new Text("location y:");
                Spinner<Integer> ySpinner = getSpinner(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_HEIGHT));
                Spinner<Integer> xSpinner = getSpinner(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_WIDTH));
                xSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationX = xSpinner.getValue());
                ySpinner.valueProperty().addListener((observableValue, integer, t1) -> locationY = ySpinner.getValue());
                confirmButton.setOnAction(actionEvent1 -> {
                    try {
                        CheatCodeController.getInstance().teleport(new Location(locationX--, locationY--), units);
                    } catch (CommandException e) {
                        return;
                    } finally {
                        enterInfoVbox.getChildren().removeAll(enterInfoVbox.getChildren());
                        enterInfoPane.setVisible(false);
                    }
                });
                enterInfoVbox.getChildren().add(locationXText);
                enterInfoVbox.getChildren().add(xSpinner);
                enterInfoVbox.getChildren().add(locationYText);
                enterInfoVbox.getChildren().add(ySpinner);
                enterInfoVbox.getChildren().add(confirmButton);
            });
            teleportation.getItems().add(item);
        }
    }

    public void increaseProduction() {
        for (City city : game.getCurrentCivilization().getCities()) {
            MenuItem item = new MenuItem(city.getName());
            item.setOnAction(actionEvent -> {
                enterInfoPane.setVisible(true);
                Button confirmButton = new Button("confirm");
                increaseAmount = 1;
                valueFactory.setValue(increaseAmount);
                confirmButton.setOnAction(actionEvent1 -> {
                    CheatCodeController.getInstance().increaseProduction(city, increaseAmount);
                    enterInfoVbox.getChildren().removeAll(enterInfoVbox.getChildren());
                    enterInfoPane.setVisible(false);
                });
                enterInfoVbox.getChildren().add(spinner);
                enterInfoVbox.getChildren().add(confirmButton);
            });
            productionIncrease.getItems().add(item);
        }
    }

    public void revealTile() {
        enterInfoPane.setVisible(true);
        Button confirmButton = new Button("confirm");
        increaseAmount = 1;
        Text locationXText = new Text("location x:");
        Text locationYText = new Text("location y:");
        Spinner<Integer> ySpinner = getSpinner(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_HEIGHT));
        Spinner<Integer> xSpinner = getSpinner(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_WIDTH));
        xSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationX = xSpinner.getValue());
        ySpinner.valueProperty().addListener((observableValue, integer, t1) -> locationY = ySpinner.getValue());
        confirmButton.setOnAction(actionEvent1 -> {
            CheatCodeController.getInstance().revealTile(new Location(locationX--, locationY--));
            enterInfoVbox.getChildren().removeAll(enterInfoVbox.getChildren());
            enterInfoPane.setVisible(false);
        });
        enterInfoVbox.getChildren().add(locationXText);
        enterInfoVbox.getChildren().add(xSpinner);
        enterInfoVbox.getChildren().add(locationYText);
        enterInfoVbox.getChildren().add(ySpinner);
        enterInfoVbox.getChildren().add(confirmButton);
    }

    public void increaseMovement() {
        for (Unit units : game.getCurrentCivilization().getUnits()) {
            MenuItem item = new MenuItem(units.getType().name());
            item.setOnAction(actionEvent -> {
                enterInfoPane.setVisible(true);
                Button confirmButton = new Button("confirm");
                increaseAmount = 1;
                valueFactory.setValue(increaseAmount);
                confirmButton.setOnAction(actionEvent1 -> {
                    CheatCodeController.getInstance().increaseMovement(units, increaseAmount);
                    enterInfoVbox.getChildren().removeAll(enterInfoVbox.getChildren());
                    enterInfoPane.setVisible(false);
                });
                enterInfoVbox.getChildren().add(spinner);
                enterInfoVbox.getChildren().add(confirmButton);
            });
            movementIncrease.getItems().add(item);
        }
    }

    public void healCity() {
        for (City city : game.getCurrentCivilization().getCities()) {
            MenuItem item = new MenuItem(city.getName());
            item.setOnAction(actionEvent -> {
                CheatCodeController.getInstance().healCity(city);
            });
            cityHeal.getItems().add(item);
        }
    }

    public void healUnit() {
        for (Unit unit : game.getCurrentCivilization().getUnits()) {
            MenuItem item = new MenuItem(unit.getLocation() + " " + unit.getType().name());
            item.setOnAction(actionEvent -> {
                CheatCodeController.getInstance().healUnit(unit);
            });
            unitHeal.getItems().add(item);
        }
    }

    public void techsUnlock() {
        CheatCodeController.getInstance().unlockTechnologies(game.getCurrentCivilization());
    }

    public void addBuilding() {
        for (BuildingEnum buildingEnum : BuildingEnum.values()) {
            Menu menu = new Menu(buildingEnum.name());
            menu.setOnAction(actionEvent -> {
                for (City city : game.getCurrentCivilization().getCities()) {
                    MenuItem cityName = new MenuItem(city.getName());
                    cityName.setOnAction(actionEvent1 -> {
                        try {
                            CheatCodeController.getInstance().addBuilding(BuildingEnum.getBuildingEnumByName(buildingEnum.name()), city);
                        } catch (CommandException e) {
                            System.out.println("error in handling building");
                        }
                    });
                    menu.getItems().add(cityName);
                }
            });
            buildingsAdd.getItems().add(menu);
        }
    }

    public void backToMenu() {
        //todo : check for errors
        MenuStack.getInstance().pushMenu(Project.Views.Menu.loadFromFXML("MainPage"));
    }

    public void exit() {
        //todo : improve for network
        System.exit(0);
    }

    public void increaseHappiness() {
        enterInfoPane.setVisible(true);
        Button confirmButton = new Button("confirm");
        increaseAmount = 1;
        valueFactory.setValue(increaseAmount);
        confirmButton.setOnAction(actionEvent -> {
            CheatCodeController.getInstance().increaseHappiness(increaseAmount);
            enterInfoVbox.getChildren().removeAll(enterInfoVbox.getChildren());
            enterInfoPane.setVisible(false);
        });
        enterInfoVbox.getChildren().add(spinner);
        enterInfoVbox.getChildren().add(confirmButton);
    }
}
