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
import Project.Models.Tiles.TileGrid;
import Project.Models.Units.NonCombatUnit;
import Project.Models.Units.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameView implements ViewController {
    private static GameView instance;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Button btn;
    @FXML
    private Menu cheat;
    @FXML
    private Menu info;
    @FXML
    private ScrollPane scrollPane;
    private Game game;
    @FXML
    private Pane tilePane;
    @FXML
    private Pane buildingsPane;
    @FXML
    private Pane unitsPane;
    @FXML
    private Pane cheatSelectPane;
    @FXML
    private Spinner<Integer> goldSpinner;
    private SpinnerValueFactory<Integer> goldValueFactory;
    private int goldAmount;
    @FXML
    private Spinner<Integer> foodSpinner;
    private SpinnerValueFactory<Integer> foodValueFactory;
    private City foodForCity;
    private int foodAmount;
    @FXML
    private MenuButton foodForCitySelect;
    @FXML
    private MenuButton unitEnumSelect;
    private UnitEnum selectedUnitEnum;
    @FXML
    private Spinner<Integer> spawnLocationXSpinner;
    private SpinnerValueFactory<Integer> spawnLocationXValueFactory;
    private int locationSpawnX;
    @FXML
    private Spinner<Integer> spawnLocationYSpinner;
    private SpinnerValueFactory<Integer> spawnLocationYValueFactory;
    private int locationSpawnY;
    @FXML
    private Spinner<Integer> tileXSpinner;
    private SpinnerValueFactory<Integer> tileXValueFactory;
    private int locationTileX;
    @FXML
    private Spinner<Integer> tileYSpinner;
    private SpinnerValueFactory<Integer> tileYValueFactory;
    private int locationTileY;
    @FXML
    private MenuButton productionIncreaseCityMenu;
    private City productionIncreaseCity;
    @FXML
    private Spinner<Integer> productionIncreaseSpinner;
    private SpinnerValueFactory<Integer> productionIncreaseValueFactory;
    private int productIncreaseAmount;
    @FXML
    private MenuButton teleportUnit;
    private Unit teleportingUnit;
    @FXML
    private Spinner<Integer> teleportXSpinner;
    private SpinnerValueFactory<Integer> teleportXValueFactory;
    private int locationTeleportX;
    @FXML
    private Spinner<Integer> teleportYSpinner;
    private SpinnerValueFactory<Integer> teleportYValueFactory;
    private int locationTeleportY;
    @FXML
    private MenuButton finishProductionCity;
    private City productionFinishingCity;
    @FXML
    private Spinner<Integer> increaseHappinessSpinner;
    private SpinnerValueFactory<Integer> increaseHappinessValueFactory;
    private int happinessAmount;
    @FXML
    private Spinner<Integer> increaseBeakerSpinner;
    private SpinnerValueFactory<Integer> increaseBeakerValueFactory;
    private int beakerAmount;
    @FXML
    private MenuButton movementIncreaseUnit;
    private Unit increasingMovementUnit;
    @FXML
    private Spinner<Integer> movementIncreaseSpinner;
    private SpinnerValueFactory<Integer> movementIncreaseValueFactory;
    private int movementIncreaseAmount;
    @FXML
    private MenuButton cityHealing;
    private City healingCity;
    @FXML
    private MenuButton unitHealing;
    private Unit healingUnit;
    @FXML
    private MenuButton buildingsMenu;
    private BuildingEnum buildingEnum;
    @FXML
    private MenuButton cityForBuildingsMenu;
    private City cityForBuilding;
    @FXML
    private Pane pane;

    public static GameView getInstance() {
        return instance;
    }

    public void initialize() {
        this.game = GameController.getGame();
        GameController.getGame().SetPage(this);
        initSpinners();
        initMenus();
        instance = this;
        //todo : initialize
    }
    public void initializeUnits(TileGrid tileGrid){
        for (int i = 0; i < tileGrid.getHeight(); i++) {
            for (int j = 0; j < tileGrid.getWidth(); j++) {
                NonCombatUnit nonCombatUnit = tileGrid.getTile(i, j).getNonCombatUnit();
                if(nonCombatUnit != null) {
                    unitsPane.getChildren().add(nonCombatUnit.getGraphicUnit());
                }
            }
        }
    }

    private void initMenus() {
        initCityMenus();
        initUnitMenus();
        initBuildings();
        initUnitEnumMenu();
    }

    private void initBuildings() {
        for (BuildingEnum buildingEnums : BuildingEnum.values()) {
            MenuItem item = new MenuItem(buildingEnums.name());
            item.setOnAction(actionEvent -> {
                buildingEnum = buildingEnums;
                buildingsMenu.setText(buildingEnums.name());
            });
            buildingsMenu.getItems().add(item);
        }
    }

    private void initCityMenus() {
        for (City city : GameController.getGame().getCurrentCivilization().getCities()) {
            MenuItem foodItem = new MenuItem(city.getName());
            MenuItem productionIncreaseItem = new MenuItem(city.getName());
            MenuItem productionFinishingItem = new MenuItem(city.getName());
            MenuItem cityHealItem = new MenuItem(city.getName());
            MenuItem buildingCity = new MenuItem(city.getName());
            foodItem.setOnAction(actionEvent -> foodForCity = city);
            foodForCitySelect.getItems().add(foodItem);
            productionIncreaseItem.setOnAction(actionEvent -> {
                productionIncreaseCity = city;
                productionIncreaseCityMenu.setText(city.getName());
            });
            productionIncreaseCityMenu.getItems().add(productionIncreaseItem);
            productionFinishingItem.setOnAction(actionEvent -> {
                productionFinishingCity = city;
                finishProductionCity.setText(city.getName());
            });
            finishProductionCity.getItems().add(productionFinishingItem);
            cityHealItem.setOnAction(actionEvent -> {
                healingCity = city;
                cityHealing.setText(city.getName());
            });
            cityHealing.getItems().add(cityHealItem);
            buildingCity.setOnAction(actionEvent -> {
                cityForBuilding = city;
                cityForBuildingsMenu.setText(city.getName());
            });
            cityForBuildingsMenu.getItems().add(buildingCity);
        }
    }

    private void initUnitMenus() {
        for (Unit unit : GameController.getGame().getCurrentCivilization().getUnits()) {
            MenuItem teleportUnitItem = new MenuItem(unit.getType().name());
            MenuItem unitMovementIncreaseItem = new MenuItem(unit.getType().name());
            MenuItem healingUnitItem = new MenuItem(unit.getType().name());
            teleportUnitItem.setOnAction(actionEvent -> {
                teleportingUnit = unit;
                teleportUnit.setText(unit.getType().name());
            });
            teleportUnit.getItems().add(teleportUnitItem);
            unitMovementIncreaseItem.setOnAction(actionEvent -> {
                increasingMovementUnit = unit;
                movementIncreaseUnit.setText(unit.getType().name());
            });
            movementIncreaseUnit.getItems().add(unitMovementIncreaseItem);
            healingUnitItem.setOnAction(actionEvent -> {
                healingUnit = unit;
                unitHealing.setText(unit.getType().name());
            });
            unitHealing.getItems().add(healingUnitItem);
        }
    }

    private void initUnitEnumMenu() {
        for (UnitEnum unitEnum : UnitEnum.values()) {
            MenuItem unitEnumItem = new MenuItem(unitEnum.name());
            unitEnumItem.setOnAction(actionEvent -> {
                selectedUnitEnum = unitEnum;
                unitEnumItem.setText(unitEnum.name());
            });
            unitEnumSelect.getItems().add(unitEnumItem);
        }
    }

    private void initSpinners() {
        initGoldSpinner();
        initFoodSpinner();
        initIncreaseProductionSpinner();
        initHappinessSpinner();
        initBeakerSpinner();
        initMovementIncreaseSpinner();
        initUnitSpawnSpinner();
        initTileRevealSpinner();
        initTeleportationSpinner();
    }

    private void initGoldSpinner() {
        goldSpinner = new Spinner<>();
        goldValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        goldSpinner.valueProperty().addListener((observableValue, integer, t1) -> goldAmount = goldSpinner.getValue());
    }

    private void initFoodSpinner() {
        foodSpinner = new Spinner<>();
        foodValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        foodSpinner.valueProperty().addListener((observableValue, integer, t1) -> foodAmount = foodSpinner.getValue());
    }

    private void initIncreaseProductionSpinner() {
        productionIncreaseSpinner = new Spinner<>();
        productionIncreaseValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        productionIncreaseSpinner.valueProperty().addListener((observableValue, integer, t1) -> productIncreaseAmount = productionIncreaseSpinner.getValue());
    }

    private void initHappinessSpinner() {
        increaseHappinessSpinner = new Spinner<>();
        increaseHappinessValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        increaseHappinessSpinner.valueProperty().addListener((observableValue, integer, t1) -> happinessAmount = increaseHappinessSpinner.getValue());
    }

    private void initBeakerSpinner() {
        increaseBeakerSpinner = new Spinner<>();
        increaseBeakerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        increaseBeakerSpinner.valueProperty().addListener((observableValue, integer, t1) -> beakerAmount = increaseBeakerSpinner.getValue());
    }

    private void initMovementIncreaseSpinner() {
        movementIncreaseSpinner = new Spinner<>();
        movementIncreaseValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        movementIncreaseSpinner.valueProperty().addListener((observableValue, integer, t1) -> movementIncreaseAmount = movementIncreaseSpinner.getValue());
    }

    private void initUnitSpawnSpinner() {
        spawnLocationXSpinner = new Spinner<>();
        spawnLocationXValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_WIDTH);
        spawnLocationXSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationSpawnX = spawnLocationXSpinner.getValue());
        spawnLocationYSpinner = new Spinner<>();
        spawnLocationYValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_HEIGHT);
        spawnLocationYSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationSpawnY = spawnLocationYSpinner.getValue());
    }

    private void initTileRevealSpinner() {
        tileXSpinner = new Spinner<>();
        tileXValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_WIDTH);
        tileXSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationTileX = tileXSpinner.getValue());
        tileYSpinner = new Spinner<>();
        tileYValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_HEIGHT);
        tileYSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationTileY = tileYSpinner.getValue());
    }

    private void initTeleportationSpinner() {
        teleportXSpinner = new Spinner<>();
        teleportXValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_WIDTH);
        teleportXSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationTeleportX = teleportXSpinner.getValue());
        teleportYSpinner = new Spinner<>();
        teleportYValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_HEIGHT);
        teleportYSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationTeleportY = teleportYSpinner.getValue());
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
    public void addUnit(Unit unit){
        unitsPane.getChildren().add(unit.getGraphicUnit());
    }

    public void click() {
        System.err.println("clicked");
        btn.setVisible(false);
        System.out.println(tilePane.getChildren());
        tilePane.getChildren().addAll(0,game.getTileGrid().getHexes());
        initializeUnits(game.getTileGrid());
        tilePane.requestFocus();
        System.out.print(new TileGridPrinter(game.getTileGrid(),10,10).print(new Location(6,6)));
    }

    public void gotoCheatSelectPage() {
        cheatSelectPane.setVisible(true);
    }

    public void increaseGold() {
        CheatCodeController.getInstance().increaseGold(goldAmount);
        cheatSelectPane.setVisible(false);
    }

    public void increaseFood() {
        if (foodForCity == null)
            return;
        CheatCodeController.getInstance().increaseFood(foodForCity, foodAmount);
        foodForCity = null;
        foodForCitySelect.setText("City");
        cheatSelectPane.setVisible(false);
    }

    public void spawnUnit() {
        if (selectedUnitEnum == null || locationSpawnX == 0 || locationSpawnY == 0)
            return;
        try {
            CheatCodeController.getInstance().spawnUnit(selectedUnitEnum, GameController.getGame().getCurrentCivilization(), new Location(locationSpawnX--, locationSpawnY--));
        } catch (CommandException e) {
            System.out.println("error in game view/ spawn unit");
        } finally {
            locationSpawnX = 0;
            locationSpawnY = 0;
            selectedUnitEnum = null;
            unitEnumSelect.setText("Unit");
            cheatSelectPane.setVisible(false);
        }
    }

    public void revealTile() {
        if (locationTileX == 0 || locationTileY == 0)
            return;
        CheatCodeController.getInstance().revealTile(new Location(locationTileX--, locationTileY--));
        locationTileX = 0;
        locationTileY = 0;
        cheatSelectPane.setVisible(false);
    }

    public void increaseProduction() {
        if (productionIncreaseCity == null)
            return;
        CheatCodeController.getInstance().increaseProduction(productionIncreaseCity, productIncreaseAmount);
        productIncreaseAmount = 0;
        productionIncreaseCity = null;
        productionIncreaseCityMenu.setText("City");
        cheatSelectPane.setVisible(false);
    }

    public void teleport() {
        if (teleportUnit == null || locationTeleportX == 0 || locationTeleportY == 0)
            return;
        try {
            CheatCodeController.getInstance().teleport(new Location(locationTeleportX--, locationTeleportY--), teleportingUnit);
        } catch (CommandException e) {
            System.out.println("error in game view/ teleport");
        } finally {
            locationTeleportX = 0;
            locationTeleportY = 0;
            teleportUnit.setText("Unit");
            teleportUnit = null;
            cheatSelectPane.setVisible(false);
        }
    }

    public void finishProduction() {
        if (productionIncreaseCity == null)
            return;
        try {
            CheatCodeController.getInstance().finishProducts(productionFinishingCity);
        } catch (CommandException e) {
            System.out.println("error in game view / finish production");
        } finally {
            productionIncreaseCity = null;
            finishProductionCity.setText("City");
            cheatSelectPane.setVisible(false);
        }
    }

    public void increaseHappiness() {
        CheatCodeController.getInstance().increaseHappiness(happinessAmount);
        happinessAmount = 0;
        cheatSelectPane.setVisible(false);
    }

    public void increaseBeaker() {
        CheatCodeController.getInstance().increaseBeaker(beakerAmount);
        beakerAmount = 0;
        cheatSelectPane.setVisible(false);
    }

    public void increaseMovement() {
        if (increasingMovementUnit == null)
            return;
        CheatCodeController.getInstance().increaseMovement(increasingMovementUnit, movementIncreaseAmount);
        increasingMovementUnit = null;
        movementIncreaseAmount = 0;
        movementIncreaseUnit.setText("Unit");
        cheatSelectPane.setVisible(false);
    }

    public void healCity() {
        if (healingCity == null)
            return;
        CheatCodeController.getInstance().healCity(healingCity);
        healingCity = null;
        cityHealing.setText("City");
        cheatSelectPane.setVisible(false);
    }

    public void healUnit() {
        if (healingUnit == null)
            return;
        CheatCodeController.getInstance().healUnit(healingUnit);
        healingUnit = null;
        unitHealing.setText("Unit");
        cheatSelectPane.setVisible(false);
    }

    public void addTechs() {
        CheatCodeController.getInstance().unlockTechnologies(GameController.getGame().getCurrentCivilization());
        cheatSelectPane.setVisible(false);
    }

    public void addBuildings() {
        if (buildingEnum == null || cityForBuildingsMenu == null)
            return;
        try {
            CheatCodeController.getInstance().addBuilding(BuildingEnum.getBuildingEnumByName(buildingEnum.name()), cityForBuilding);
        } catch (CommandException e) {
            System.out.println("error in game view / add buildings");
        } finally {
            buildingEnum = null;
            cityForBuilding = null;
            buildingsMenu.setText("Building");
            cityForBuildingsMenu.setText("City");
            cheatSelectPane.setVisible(false);
        }
    }

    public void backToMenu() {
        // todo : go back to menu
    }

    public void exit() {
        System.exit(0);
        // todo : check for thread
    }

    private void freeMenuItems() {
        unitEnumSelect.getItems().removeAll(unitEnumSelect.getItems());
        unitHealing.getItems().removeAll(unitHealing.getItems());
        cityHealing.getItems().removeAll(cityHealing.getItems());
        buildingsMenu.getItems().removeAll(buildingsMenu.getItems());
        cityForBuildingsMenu.getItems().removeAll(cityForBuildingsMenu.getItems());
        foodForCitySelect.getItems().removeAll(foodForCitySelect.getItems());
        productionIncreaseCityMenu.getItems().removeAll(productionIncreaseCityMenu.getItems());
        teleportUnit.getItems().removeAll(teleportUnit.getItems());
        finishProductionCity.getItems().removeAll(finishProductionCity.getItems());
        movementIncreaseUnit.getItems().removeAll(movementIncreaseUnit.getItems());
    }

    public void refreshCheatSheet() {
        freeMenuItems();
        initMenus();
        initSpinners();
    }

    public void exitPane(KeyEvent keyEvent) {
        System.out.println(keyEvent.getCode().getName());
        if (keyEvent.getCode().getName().equals("Esc"))
            cheatSelectPane.setVisible(false);
    }
}
