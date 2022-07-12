package Project.Client.Views;

import Project.Enums.BuildingEnum;
import Project.Enums.UnitEnum;
import Project.Models.Cities.City;
import Project.Models.Units.Unit;
import Project.Server.Controllers.CheatCodeController;
import Project.Server.Controllers.GameController;
import Project.Server.Views.RequestHandler;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;

public class CheatSheetView implements ViewController {
    @FXML
    private Pane pane;
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

    public void initialize() {
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
                sendSelectCityRequest(city);
                productionIncreaseCityMenu.setText(city.getName());
            });
            productionIncreaseCityMenu.getItems().add(productionIncreaseItem);
            productionFinishingItem.setOnAction(actionEvent -> {
                productionFinishingCity = city;
                sendSelectCityRequest(city);
                finishProductionCity.setText(city.getName());
            });
            finishProductionCity.getItems().add(productionFinishingItem);
            cityHealItem.setOnAction(actionEvent -> {
                healingCity = city;
                sendSelectCityRequest(city);
                cityHealing.setText(city.getName());
            });
            cityHealing.getItems().add(cityHealItem);
            buildingCity.setOnAction(actionEvent -> {
                cityForBuilding = city;
                sendSelectCityRequest(city);
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
                sendSelectUnitRequest(unit);
                teleportUnit.setText(unit.getType().name() + " ( " + unit.getLocation().getRow() + " , " + unit.getLocation().getCol() + ")");
            });
            teleportUnit.getItems().add(teleportUnitItem);
            unitMovementIncreaseItem.setOnAction(actionEvent -> {
                increasingMovementUnit = unit;
                sendSelectUnitRequest(unit);
                movementIncreaseUnit.setText(unit.getType().name() + " ( " + unit.getLocation().getRow() + " , " + unit.getLocation().getCol() + ")");
            });
            movementIncreaseUnit.getItems().add(unitMovementIncreaseItem);
            healingUnitItem.setOnAction(actionEvent -> {
                healingUnit = unit;
                sendSelectUnitRequest(unit);
                unitHealing.setText(unit.getType().name() + " ( " + unit.getLocation().getRow() + " , " + unit.getLocation().getCol() + " )");
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
        spawnLocationXValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Constants.TILEGRID_WIDTH - 1);
        spawnLocationXValueFactory.setValue(locationSpawnX);
        spawnLocationXSpinner.setValueFactory(spawnLocationXValueFactory);
        spawnLocationXSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationSpawnX = spawnLocationXSpinner.getValue());

        spawnLocationYValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Constants.TILEGRID_HEIGHT - 1);
        spawnLocationYValueFactory.setValue(locationSpawnY);
        spawnLocationYSpinner.setValueFactory(spawnLocationYValueFactory);
        spawnLocationYSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationSpawnY = spawnLocationYSpinner.getValue());
    }

    private void initTileRevealSpinner() {
        tileXValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Constants.TILEGRID_WIDTH - 1);
        tileXValueFactory.setValue(locationTileX);
        tileXSpinner.setValueFactory(tileXValueFactory);
        tileXSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationTileX = tileXSpinner.getValue());

        tileYValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Constants.TILEGRID_HEIGHT - 1);
        tileYValueFactory.setValue(locationTileY);
        tileYSpinner.setValueFactory(tileYValueFactory);
        tileYSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationTileY = tileYSpinner.getValue());
    }

    private void initTeleportationSpinner() {
        teleportXValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Constants.TILEGRID_WIDTH - 1);
        teleportXValueFactory.setValue(locationTeleportX);
        teleportXSpinner.setValueFactory(teleportXValueFactory);
        teleportXSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationTeleportX = teleportXSpinner.getValue());

        teleportYValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Constants.TILEGRID_HEIGHT - 1);
        teleportYValueFactory.setValue(locationTeleportY);
        teleportYSpinner.setValueFactory(teleportYValueFactory);
        teleportYSpinner.valueProperty().addListener((observableValue, integer, t1) -> locationTeleportY = teleportYSpinner.getValue());
    }

    public void increaseGold() {
        String command = "cheat increase gold -a " + goldAmount;
        CommandResponse response = RequestHandler.getInstance().handle(command);
        MenuStack.getInstance().popMenu();
    }

    public void increaseFood() {
        if (foodForCity == null)
            return;
        foodForCity = null;
        foodForCitySelect.setText("City");
        String command = "cheat increase food -a " + foodAmount;
        CommandResponse response = RequestHandler.getInstance().handle(command);
        MenuStack.getInstance().popMenu();
    }

    public void spawnUnit() {
        if (selectedUnitEnum == null || locationSpawnX == 0 || locationSpawnY == 0)
            return;
            String command = "cheat spawn unit -u " + selectedUnitEnum.name() + " -p " + locationSpawnX + " " + locationSpawnY;
            CommandResponse response = RequestHandler.getInstance().handle(command);
            locationSpawnX = 0;
            locationSpawnY = 0;
            selectedUnitEnum = null;
            unitEnumSelect.setText("Unit");
            MenuStack.getInstance().popMenu();
    }

    public void revealTile() {
        if (locationTileX == 0 || locationTileY == 0)
            return;
        String command = "cheat map reveal -p " + locationSpawnX + " " + locationSpawnY;
        CommandResponse response = RequestHandler.getInstance().handle(command);
        locationTileX = 0;
        locationTileY = 0;
        MenuStack.getInstance().popMenu();
    }

    public void increaseProduction() {
        if (productionIncreaseCity == null)
            return;
        String command = "cheat increase production -a " + productIncreaseAmount;
        CommandResponse response = RequestHandler.getInstance().handle(command);
        productIncreaseAmount = 0;
        productionIncreaseCity = null;
        productionIncreaseCityMenu.setText("City");
        MenuStack.getInstance().popMenu();
    }

    public void teleport() {
        if (teleportUnit == null || locationTeleportX == 0 || locationTeleportY == 0)
            return;
            String command = "cheat teleport -p " + locationTeleportX + " " + locationTeleportY;
            CommandResponse response = RequestHandler.getInstance().handle(command);
            locationTeleportX = 0;
            locationTeleportY = 0;
            teleportUnit.setText("Unit");
            teleportUnit = null;
            MenuStack.getInstance().popMenu();
    }

    public void finishProduction() {
        if (productionIncreaseCity == null)
            return;
            String command = "cheat finish products";
            CommandResponse response = RequestHandler.getInstance().handle(command);
            productionIncreaseCity = null;
            finishProductionCity.setText("City");
            MenuStack.getInstance().popMenu();
    }

    public void increaseHappiness() {
        String command = "cheat increase happiness -a " + happinessAmount;
        CommandResponse response = RequestHandler.getInstance().handle(command);
        happinessAmount = 0;
        MenuStack.getInstance().popMenu();
    }

    public void increaseBeaker() {
        String command = "cheat increase science -a " + beakerAmount;
        CommandResponse response = RequestHandler.getInstance().handle(command);
        beakerAmount = 0;
        MenuStack.getInstance().popMenu();
    }

    public void increaseMovement() {
        if (increasingMovementUnit == null)
            return;
        String command = "cheat increase production -a " + productIncreaseAmount;
        CommandResponse response = RequestHandler.getInstance().handle(command);
        increasingMovementUnit = null;
        movementIncreaseAmount = 0;
        movementIncreaseUnit.setText("Unit");
        MenuStack.getInstance().popMenu();
    }

    public void healCity() {
        if (healingCity == null)
            return;
        String command = "cheat heal city";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        healingCity = null;
        cityHealing.setText("City");
        cityHealing.setText("City");
        MenuStack.getInstance().popMenu();
    }

    public void healUnit() {
        if (healingUnit == null)
            return;
        String command = "cheat heal unit";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        healingUnit = null;
        unitHealing.setText("Unit");
        MenuStack.getInstance().popMenu();
    }

    public void addTechs() {
        CheatCodeController.getInstance().unlockTechnologies(GameController.getGame().getCurrentCivilization());
        String command = "cheat unlock technologies";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        MenuStack.getInstance().popMenu();
    }

    public void addBuildings() {
        if (buildingEnum == null || cityForBuildingsMenu == null)
            return;

            String command = "cheat build -n " + buildingEnum.name();
            CommandResponse response = RequestHandler.getInstance().handle(command);
            buildingEnum = null;
            cityForBuilding = null;
            buildingsMenu.setText("Building");
            cityForBuildingsMenu.setText("City");
            MenuStack.getInstance().popMenu();

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
    private void sendSelectCityRequest(City city) {
        String command = "select city " + " -p " + city.getLocation().getRow() + " " + city.getLocation().getCol();
        RequestHandler.getInstance().handle(command);
    }
    private void sendSelectUnitRequest(Unit unit) {
        String command = "select unit " + " -p " + unit.getLocation().getRow() + " " + unit.getLocation().getCol();
        RequestHandler.getInstance().handle(command);
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }

    public void exit() {
        System.exit(0);
    }
}
