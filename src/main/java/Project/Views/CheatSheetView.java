package Project.Views;

import Project.Controllers.CheatCodeController;
import Project.Controllers.GameController;
import Project.Enums.BuildingEnum;
import Project.Enums.UnitEnum;
import Project.Models.Cities.City;
import Project.Models.Location;
import Project.Models.Units.Unit;
import Project.Utils.CommandException;
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
        CheatCodeController.getInstance().increaseGold(goldAmount);
        MenuStack.getInstance().popMenu();
    }

    public void increaseFood() {
        if (foodForCity == null)
            return;
        CheatCodeController.getInstance().increaseFood(foodForCity, foodAmount);
        foodForCity = null;
        foodForCitySelect.setText("City");
        MenuStack.getInstance().popMenu();
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
            MenuStack.getInstance().popMenu();
        }
    }

    public void revealTile() {
        if (locationTileX == 0 || locationTileY == 0)
            return;
        CheatCodeController.getInstance().revealTile(new Location(locationTileX--, locationTileY--));
        locationTileX = 0;
        locationTileY = 0;
        MenuStack.getInstance().popMenu();
    }

    public void increaseProduction() {
        if (productionIncreaseCity == null)
            return;
        CheatCodeController.getInstance().increaseProduction(productionIncreaseCity, productIncreaseAmount);
        productIncreaseAmount = 0;
        productionIncreaseCity = null;
        productionIncreaseCityMenu.setText("City");
        MenuStack.getInstance().popMenu();
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
            MenuStack.getInstance().popMenu();
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
            MenuStack.getInstance().popMenu();
        }
    }

    public void increaseHappiness() {
        CheatCodeController.getInstance().increaseHappiness(happinessAmount);
        happinessAmount = 0;
        MenuStack.getInstance().popMenu();
    }

    public void increaseBeaker() {
        CheatCodeController.getInstance().increaseBeaker(beakerAmount);
        beakerAmount = 0;
        MenuStack.getInstance().popMenu();
    }

    public void increaseMovement() {
        if (increasingMovementUnit == null)
            return;
        CheatCodeController.getInstance().increaseMovement(increasingMovementUnit, movementIncreaseAmount);
        increasingMovementUnit = null;
        movementIncreaseAmount = 0;
        movementIncreaseUnit.setText("Unit");
        MenuStack.getInstance().popMenu();
    }

    public void healCity() {
        if (healingCity == null)
            return;
        CheatCodeController.getInstance().healCity(healingCity);
        healingCity = null;
        cityHealing.setText("City");
        MenuStack.getInstance().popMenu();
    }

    public void healUnit() {
        if (healingUnit == null)
            return;
        CheatCodeController.getInstance().healUnit(healingUnit);
        healingUnit = null;
        unitHealing.setText("Unit");
        MenuStack.getInstance().popMenu();
    }

    public void addTechs() {
        CheatCodeController.getInstance().unlockTechnologies(GameController.getGame().getCurrentCivilization());
        MenuStack.getInstance().popMenu();
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
            MenuStack.getInstance().popMenu();
        }
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
