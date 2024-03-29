package Client.Views;

import Client.Utils.DatabaseQuerier;
import Client.Utils.RequestSender;
import Client.Utils.SelectHandler;
import Project.Enums.BuildingEnum;
import Project.Enums.UnitEnum;
import Project.Models.Cities.City;
import Project.Models.Location;
import Project.Utils.CommandResponse;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class CheatSheetView implements ViewController {
    @FXML
    private Pane pane;
    int TILEGRID_WIDTH;
    int TILEGRID_HEIGHT;
    @FXML
    private Spinner<Integer> goldSpinner;
    private SpinnerValueFactory<Integer> goldValueFactory;
    private int goldAmount;
    @FXML
    private Spinner<Integer> foodSpinner;
    private SpinnerValueFactory<Integer> foodValueFactory;
    private City foodForCity;
    private String foodForCityName;
    private int foodAmount;
    @FXML
    private MenuButton foodForCitySelect;
    @FXML
    private MenuButton unitEnumSelect;
    private UnitEnum selectedUnitEnum;
    @FXML
    private Spinner<Integer> spawnLocationXSpinner;
    private SpinnerValueFactory<Integer> spawnLocationXValueFactory;
    private int locationSpawnX = 0;
    @FXML
    private Spinner<Integer> spawnLocationYSpinner;
    private SpinnerValueFactory<Integer> spawnLocationYValueFactory;
    private int locationSpawnY = 0;
    @FXML
    private Spinner<Integer> tileXSpinner;
    private SpinnerValueFactory<Integer> tileXValueFactory;
    private int locationTileX = 0;
    @FXML
    private Spinner<Integer> tileYSpinner;
    private SpinnerValueFactory<Integer> tileYValueFactory;
    private int locationTileY = 0;
    @FXML
    private MenuButton productionIncreaseCityMenu;
    private City productionIncreaseCity;
    private String productionIncreaseCityName;
    @FXML
    private Spinner<Integer> productionIncreaseSpinner;
    private SpinnerValueFactory<Integer> productionIncreaseValueFactory;
    private int productIncreaseAmount;
    @FXML
    private MenuButton teleportUnit;
    @FXML
    private Spinner<Integer> teleportXSpinner;
    private SpinnerValueFactory<Integer> teleportXValueFactory;
    private int locationTeleportX = 0;
    @FXML
    private Spinner<Integer> teleportYSpinner;
    private SpinnerValueFactory<Integer> teleportYValueFactory;
    private int locationTeleportY = 0;
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
    @FXML
    private Spinner<Integer> movementIncreaseSpinner;
    private SpinnerValueFactory<Integer> movementIncreaseValueFactory;
    private int movementIncreaseAmount;
    @FXML
    private MenuButton cityHealing;
    private City healingCity;
    private String healingCityName;
    @FXML
    private MenuButton unitHealing;
    @FXML
    private MenuButton buildingsMenu;
    private BuildingEnum buildingEnum;
    @FXML
    private MenuButton cityForBuildingsMenu;
    private City cityForBuilding;
    private String cityForBuildingName;
    private String teleportUnitName;
    private String healingUnitName;
    private String increasingMovementUnitName;

    public void initialize() {
        this.TILEGRID_HEIGHT = DatabaseQuerier.getTileGridSize().getFirst();
        this.TILEGRID_WIDTH = DatabaseQuerier.getTileGridSize().getSecond();
//        System.out.println(pane.getStylesheets());
        initSpinners();
        initMenus();
    }

    private void initMenus() {
        initCityMenus();
        initUnitMenus();
        initBuildings();
        initUnitEnumMenu();
    }

    private void initBuildings() {
        for (BuildingEnum buildingEnums : DatabaseQuerier.getBuildingEnums()) {
            MenuItem item = new MenuItem(buildingEnums.name());
            item.setOnAction(actionEvent -> {
                buildingEnum = buildingEnums;
                buildingsMenu.setText(buildingEnums.name());
            });
            buildingsMenu.getItems().add(item);
        }
    }

    private void initCityMenus() {
        for (City city : DatabaseQuerier.getCurrentCivCities()) {
            String name = city.getName();
            MenuItem foodItem = new MenuItem(name);
            MenuItem productionIncreaseItem = new MenuItem(name);
            MenuItem productionFinishingItem = new MenuItem(name);
            MenuItem cityHealItem = new MenuItem(name);
            MenuItem buildingCity = new MenuItem(name);
            foodItem.setOnAction(actionEvent -> {
                foodForCityName = name;
                SelectHandler.sendSelectCityRequest(city);
                foodForCitySelect.setText(name);
            });
            foodForCitySelect.getItems().add(foodItem);
            finishProductionCity.getItems().add(productionFinishingItem);
            cityHealItem.setOnAction(actionEvent -> {
                healingCityName = name;
                SelectHandler.sendSelectCityRequest(city);
                cityHealing.setText(name);
            });
            cityHealing.getItems().add(cityHealItem);
            buildingCity.setOnAction(actionEvent -> {
                cityForBuildingName = name;
                SelectHandler.sendSelectCityRequest(city);
                cityForBuildingsMenu.setText(name);
            });
            cityForBuildingsMenu.getItems().add(buildingCity);
        }
    }

    private void initUnitMenus() {
        ArrayList<String> unitTypeNames = new ArrayList<>(DatabaseQuerier.getCurrentCivUnitsNames());
        ArrayList<Location> unitLocations = new ArrayList<>(DatabaseQuerier.getCurrentCivUnitsLocation());

        for (int i = 0; i < unitTypeNames.size(); i++) {
            String unitName = unitTypeNames.get(i);
            Location unitLocation = unitLocations.get(i);
            MenuItem teleportUnitItem = new MenuItem(unitTypeNames.get(i));
            MenuItem unitMovementIncreaseItem = new MenuItem(unitTypeNames.get(i));
            MenuItem healingUnitItem = new MenuItem(unitName);
            teleportUnitItem.setOnAction(actionEvent -> {
                teleportUnitName = unitName;
                //    SelectHandler.sendSelectUnitRequest(unit);
                teleportUnit.setText(unitName + " ( " + unitLocation.getRow() + " , " + unitLocation.getCol() + ")");
            });
            teleportUnit.getItems().add(teleportUnitItem);
            unitMovementIncreaseItem.setOnAction(actionEvent -> {
                increasingMovementUnitName = unitName;
                //    SelectHandler.sendSelectUnitRequest(unit);
                movementIncreaseUnit.setText(unitName + " ( " + unitLocation.getRow() + " , " + unitLocation.getCol() + ")");
            });
            movementIncreaseUnit.getItems().add(unitMovementIncreaseItem);
            healingUnitItem.setOnAction(actionEvent -> {
                healingUnitName = unitName;
                //    SelectHandler.sendSelectUnitRequest(unit);
                unitHealing.setText(unitName + " ( " + unitLocation.getRow() + " , " + unitLocation.getCol() + " )");
            });
            unitHealing.getItems().add(healingUnitItem);
        }
    }

    private void initUnitEnumMenu() {
        for (UnitEnum unitEnum : DatabaseQuerier.getUnitEnums()) {
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
        goldValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        goldValueFactory.setValue(goldAmount);
        goldSpinner.setValueFactory(goldValueFactory);
        goldSpinner.valueProperty().addListener((observableValue, integer, t1) -> goldAmount = goldSpinner.getValue());
    }

    private void initFoodSpinner() {
        foodValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        foodValueFactory.setValue(foodAmount);
        foodSpinner.setValueFactory(foodValueFactory);
        foodSpinner.valueProperty().addListener((observableValue, integer, t1) -> foodAmount = foodSpinner.getValue());
    }

    private void initIncreaseProductionSpinner() {
        productionIncreaseValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        productionIncreaseValueFactory.setValue(productIncreaseAmount);
        productionIncreaseSpinner.setValueFactory(productionIncreaseValueFactory);
        productionIncreaseSpinner.valueProperty().addListener((observableValue, integer, t1) -> productIncreaseAmount = productionIncreaseSpinner.getValue());
    }

    private void initHappinessSpinner() {
        increaseHappinessValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        increaseHappinessValueFactory.setValue(happinessAmount);
        increaseHappinessSpinner.setValueFactory(increaseHappinessValueFactory);
        increaseHappinessSpinner.valueProperty().addListener((observableValue, integer, t1) -> happinessAmount = increaseHappinessSpinner.getValue());
    }

    private void initBeakerSpinner() {
        increaseBeakerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        increaseBeakerValueFactory.setValue(beakerAmount);
        increaseBeakerSpinner.setValueFactory(increaseBeakerValueFactory);
        increaseBeakerSpinner.valueProperty().addListener((observableValue, integer, t1) -> beakerAmount = increaseBeakerSpinner.getValue());
    }

    private void initMovementIncreaseSpinner() {
        movementIncreaseValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        movementIncreaseValueFactory.setValue(movementIncreaseAmount);
        movementIncreaseSpinner.setValueFactory(movementIncreaseValueFactory);
        movementIncreaseSpinner.valueProperty().addListener((observableValue, integer, t1) -> movementIncreaseAmount = movementIncreaseSpinner.getValue());
    }

    private void initUnitSpawnSpinner() {
        spawnLocationXValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_WIDTH - 1);
        spawnLocationXValueFactory.setValue(locationSpawnX);
        spawnLocationXSpinner.setValueFactory(spawnLocationXValueFactory);
        spawnLocationXSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationSpawnX = spawnLocationXSpinner.getValue());

        spawnLocationYValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_HEIGHT - 1);
        spawnLocationYValueFactory.setValue(locationSpawnY);
        spawnLocationYSpinner.setValueFactory(spawnLocationYValueFactory);
        spawnLocationYSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationSpawnY = spawnLocationYSpinner.getValue());
    }

    private void initTileRevealSpinner() {
        tileXValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_WIDTH - 1);
        tileXValueFactory.setValue(locationTileX);
        tileXSpinner.setValueFactory(tileXValueFactory);
        tileXSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationTileX = tileXSpinner.getValue());

        tileYValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_HEIGHT - 1);
        tileYValueFactory.setValue(locationTileY);
        tileYSpinner.setValueFactory(tileYValueFactory);
        tileYSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationTileY = tileYSpinner.getValue());
    }

    private void initTeleportationSpinner() {
        teleportXValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_WIDTH - 1);
        teleportXValueFactory.setValue(locationTeleportX);
        teleportXSpinner.setValueFactory(teleportXValueFactory);
        teleportXSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationTeleportX = teleportXSpinner.getValue());

        teleportYValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_HEIGHT - 1);
        teleportYValueFactory.setValue(locationTeleportY);
        teleportYSpinner.setValueFactory(teleportYValueFactory);
        teleportYSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationTeleportY = teleportYSpinner.getValue());
    }

    public void increaseGold() {
        String command = "cheat increase gold -a " + goldAmount;
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }

    public void increaseFood() {
        foodForCity = null;
        foodForCitySelect.setText("City");
        String command = "cheat increase food -a " + foodAmount;
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }

    public void spawnUnit() {
        if(this.selectedUnitEnum == null){
            MenuStack.getInstance().showError("No unit type is selected");
            return;
        }
        String command = "cheat spawn unit -u " + selectedUnitEnum.name() + " -p " + locationSpawnX + " " + locationSpawnY;
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        locationSpawnX = 0;
        locationSpawnY = 0;
        selectedUnitEnum = null;
        unitEnumSelect.setText("Unit");
        back();
    }

    public void revealTile() {

        String command = "cheat map reveal -p " + locationTileX + " " + locationTileY;
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        locationTileX = 0;
        locationTileY = 0;
        back();
    }

    public void increaseProduction() {
        if (productionIncreaseCity == null){
            MenuStack.getInstance().showError("No city is selected");
            return;
        }
        String command = "cheat increase production -a " + productIncreaseAmount;
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        productIncreaseAmount = 0;
        productionIncreaseCity = null;
        productionIncreaseCityMenu.setText("City");
        back();
    }

    public void teleport() {
        String command = "cheat teleport -p " + locationTeleportX + " " + locationTeleportY;
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        locationTeleportX = 0;
        locationTeleportY = 0;
        teleportUnit.setText("Unit");
        teleportUnit = null;
        back();
    }

    public void finishProduction() {
        if (productionIncreaseCity == null){
            MenuStack.getInstance().showError("No city is selected");
            return;
        }
        String command = "cheat finish products";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        productionIncreaseCity = null;
        finishProductionCity.setText("City");
        back();
    }

    public void increaseHappiness() {
        String command = "cheat increase happiness -a " + happinessAmount;
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        happinessAmount = 0;
        back();
    }

    public void increaseBeaker() {
        String command = "cheat increase science -a " + beakerAmount;
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        beakerAmount = 0;
        back();
    }

    public void increaseMovement() {
        String command = "cheat increase movement -a " + movementIncreaseAmount;
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        System.out.println(response.toString());
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        increasingMovementUnitName = null;
        movementIncreaseAmount = 0;
        movementIncreaseUnit.setText("Unit");
        back();
    }

    public void healCity() {
        String command = "cheat heal city";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        healingCity = null;
        cityHealing.setText("City");
        cityHealing.setText("City");
        back();
    }

    public void healUnit() {
        String command = "cheat heal unit";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        healingUnitName = null;
        unitHealing.setText("Unit");
        back();
    }

    public void addTechs() {
        String command = "cheat unlock technologies";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
            GameView.reloadHexGrid(); // later with live socket
        }
        back();
    }

    public void addBuildings() {
        if (buildingEnum == null )
        {
                MenuStack.getInstance().showError("No building is selected");
                return;
        }
        String command = "cheat build -n " + buildingEnum.name();
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        buildingEnum = null;
        cityForBuilding = null;
        buildingsMenu.setText("Building");
        cityForBuildingsMenu.setText("City");
        back();

    }

    public void refreshCheatSheet() {
        freeMenuItems();
        initMenus();
        initSpinners();
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


    public void back() {
        MenuStack.getInstance().popMenu();
    }

    public void exit() {
        System.exit(0);
    }
}
