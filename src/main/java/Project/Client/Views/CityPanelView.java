package Project.Client.Views;

import Project.Enums.BuildingEnum;
import Project.Enums.UnitEnum;
import Project.Models.Cities.City;
import Project.Models.Citizen;
import Project.Models.Civilization;
import Project.Models.Production;
import Project.Server.Controllers.GameController;
import Project.Server.Views.RequestHandler;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class CityPanelView implements ViewController {
    @FXML
    private Button UnAssignCitizen;
    @FXML
    private Label NumberOfUnassignedCitizens;
    @FXML
    private Button assignBtn;
    @FXML
    private Spinner assignCitizenXSpinner;
    @FXML
    private Spinner assignCitizenYSpinner;
    @FXML
    private Button buyUnitBtn;
    @FXML
    private MenuButton unitMenu;
    @FXML
    private Button buyBuildingBtn;
    @FXML
    private MenuButton buildingMenu;
    private BuildingEnum selectedBuilding;
    @FXML
    private MenuButton productionMenu;
    private Production selectedProduction;
    @FXML
    private Button lockUnlockBtn;
    private Citizen selectedCitizen;
    private UnitEnum selectedUnit;
    @FXML
    private MenuButton citizenSelectMenu;
    @FXML
    private Button buyTileBtn;
    @FXML
    private Spinner<Integer> buyTileLocationXSpinner;
    private SpinnerValueFactory<Integer> locationXValueFactory;
    private int locationX;
    @FXML
    private Spinner<Integer> buyTileLocationYSpinner;
    private SpinnerValueFactory<Integer> locationYValueFactory;
    private int locationY;
    @FXML
    private Text production;
    @FXML
    private Text food;
    @FXML
    private Text populationSize;
    @FXML
    private Text cityName;
    @FXML
    private Text gold;
    @FXML
    private Text isCapital;
    @FXML
    private Text state;
    @FXML
    private Button speedUpBtn;
    private City city;

    public void initialize() {
        city = MenuStack.getInstance().getCookies().getSelectedCity();
        this.populationSize.setText(String.valueOf(city.getCitizensCount()));
        this.gold.setText(String.valueOf(city.getGold()));
        this.food.setText(String.valueOf(city.getFood()));
        this.production.setText(String.valueOf(city.getProduction()));
        this.cityName.setText(city.getName());
        this.isCapital.setText(String.valueOf(city.isCapital()));
        this.NumberOfUnassignedCitizens.setText("Number of unassigned citizens : " + city.numberOfUnassignedCitizens());
        initBuyTileSpinner();
        initAssignCitizenSpinner();
        initCitizenMenu();
        initProductMenu();
        initializeBuildingMenu();
        initializeUnitMenu();
    }

    private void initAssignCitizenSpinner() {
        SpinnerValueFactory<Integer> xSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,Constants.TILEGRID_WIDTH);
        SpinnerValueFactory<Integer> ySpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,Constants.TILEGRID_HEIGHT);
        assignCitizenXSpinner.setValueFactory(xSpinnerValueFactory);
        assignCitizenYSpinner.setValueFactory(ySpinnerValueFactory);
        if(city.numberOfUnassignedCitizens() == 0){
            this.assignBtn.setDisable(true);
        } else {
            this.assignBtn.setDisable(false);
        }
    }


    private void initProductMenu() {
        city = MenuStack.getInstance().getCookies().getSelectedCity();
        for (Production p : city.getProductionQueue()) {
            MenuItem item = new MenuItem(p.toString());
            item.setOnAction(actionEvent -> {
                productionMenu.setText(p.toString());
                selectedProduction = p;
            });
            productionMenu.getItems().add(item);
        }
    }

    private void initCitizenMenu() {
        city = MenuStack.getInstance().getCookies().getSelectedCity();
        for (Citizen citizen : city.getCitizens()) {
            StringBuilder citizenInfo = new StringBuilder(citizen.toString());
            if (citizen.isLocked())
                citizenInfo.append(" 🔒️");
            else
                citizenInfo.append(" 🔓");
            MenuItem item = new MenuItem(citizenInfo.toString());
            item.setOnAction(actionEvent -> {
                selectedCitizen = citizen;
                citizenSelectMenu.setText(citizenInfo.toString());
            });
            citizenSelectMenu.getItems().add(item);
        }
    }

    private void initBuyTileSpinner() {
        locationXValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_WIDTH);
        locationXValueFactory.setValue(locationX);
        buyTileLocationXSpinner.setValueFactory(locationXValueFactory);
        buyTileLocationXSpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            locationX = buyTileLocationXSpinner.getValue();
            // todo : check if another civ owns tile
            //if ()
            //    buyTileBtn.setDisable(true);
            //else
            //    buyTileBtn.setDisable(false);
        });

        locationYValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_HEIGHT);
        locationYValueFactory.setValue(locationY);
        buyTileLocationYSpinner.setValueFactory(locationYValueFactory);
        buyTileLocationYSpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            locationY = buyTileLocationYSpinner.getValue();
        });
//        String command = "city buy tile -p " + locationX + " " + locationY;
//        CommandResponse response = RequestHandler.getInstance().handle(command);
    }

    public void gotoShop() {
        back();
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("ShopPage"));
    }

    public void exit() {
        System.exit(0);
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }

    public void BuyTile() {
        city = MenuStack.getInstance().getCookies().getSelectedCity();
        if (city.getGold() == 0)
            buyTileBtn.setDisable(true);
        String command = "city buy tile -p " + this.city.getLocation().getRow() + " " + this.city.getLocation().getCol();
        CommandResponse response = RequestHandler.getInstance().handle(command);
        back();
    }


    public void speedUp() {
        if (selectedProduction == null)
            return;
        selectedProduction.decreaseRemainedProduction(10);
        initProductMenu();
        back();
    }

    public void lockOrUnlock() {
        if (selectedCitizen == null)
            return;
        //todo requires correct logically
        String command;
        if(selectedCitizen.isLocked()){
            command = "city citizen unlock -p " + selectedCitizen.getLocation().getRow() + " " + selectedCitizen.getLocation().getCol();
        } else {
            command = "city citizen lock -p " + selectedCitizen.getLocation().getRow() + " " + selectedCitizen.getLocation().getCol();
        }
        CommandResponse response = RequestHandler.getInstance().handle(command);
        selectedCitizen = null;
        back();
    }

    private void initializeBuildingMenu() {
        city = MenuStack.getInstance().getCookies().getSelectedCity();
        Civilization civilization = GameController.getGame().getCurrentCivilization();
        for (BuildingEnum buildingEnum : BuildingEnum.values()) {
            MenuItem item = new MenuItem(buildingEnum.name() + " " + buildingEnum.requiredTechName());
            item.setOnAction(actionEvent -> {
                if (!buildingEnum.checkIfHasRequiredTechs(civilization.getTechnologies())) {
                    buyBuildingBtn.setDisable(true);
                } else {
                    buyBuildingBtn.setDisable(false);
                    selectedBuilding = buildingEnum;
                    buildingMenu.setText(buildingEnum.name());
                }
            });
            buildingMenu.getItems().add(item);
        }
    }
    private void initializeUnitMenu() {
        city = MenuStack.getInstance().getCookies().getSelectedCity();
        Civilization civilization = GameController.getGame().getCurrentCivilization();
        for (UnitEnum unitEnum : UnitEnum.values()) {
            MenuItem item = new MenuItem(unitEnum.name());
            item.setOnAction(actionEvent -> {
                if (!unitEnum.hasRequiredTech(civilization.getTechnologies())) {
                    buyUnitBtn.setDisable(true);
                } else {
                    buyUnitBtn.setDisable(false);
                    selectedUnit = unitEnum;
                    unitMenu.setText(unitEnum.name());
                }
            });
            this.unitMenu.getItems().add(item);
        }
    }


    public void buyBuilding() {
//        System.out.println(selectedBuilding);
        if (selectedBuilding == null)
            return;
        city = MenuStack.getInstance().getCookies().getSelectedCity();
        String command = "city build building -n " +  selectedBuilding.name();
        CommandResponse response = RequestHandler.getInstance().handle(command);
        selectedBuilding = null;
        back();
    }

    public void buyUnit() {
        City city = MenuStack.getInstance().getCookies().getSelectedCity();
        if(selectedUnit == null){
            return;
        }
        String command = "city buy unit -u " + selectedUnit.name();
        CommandResponse response = RequestHandler.getInstance().handle(command);
        back();
    }

    public void assignBtnAction(ActionEvent actionEvent) {
        String command = "city citizen assign -p " + assignCitizenXSpinner.getValue() + " " + assignCitizenYSpinner.getValue();
        CommandResponse response = RequestHandler.getInstance().handle(command);
        back();
    }

    public void unAssign(ActionEvent actionEvent) {
        if(selectedCitizen == null){
            return;
        }
        String command = "city citizen unassign -p " + selectedCitizen.getLocation().getRow() + " " + selectedCitizen.getLocation().getCol();
        CommandResponse response = RequestHandler.getInstance().handle(command);
        back();
    }
}
