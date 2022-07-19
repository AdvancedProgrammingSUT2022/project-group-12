package Client.Views;

import Client.Utils.DatabaseQuerier;
import Client.Utils.RequestHandler;
import Project.Enums.BuildingEnum;
import Project.Enums.TechnologyEnum;
import Project.Enums.UnitEnum;
import Project.Models.Cities.City;
import Project.Models.Citizen;
import Project.Models.Production;
import Project.Utils.CommandResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class CityPanelView implements ViewController {
    int TILEGRID_WIDTH;
    int TILEGRID_HEIGHT;
    @FXML
    private HBox requirements;
    @FXML
    private Spinner attackXSpinner;
    @FXML
    private Spinner attackYSpinner;
    @FXML
    private Button attackBtn;
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
        this.TILEGRID_HEIGHT = DatabaseQuerier.getTileGridSize().get("Height");
        this.TILEGRID_WIDTH = DatabaseQuerier.getTileGridSize().get("Width");
        this.populationSize.setText(String.valueOf(city.getCitizensCount()));
        this.gold.setText(String.valueOf(city.calculateGold()));
        this.food.setText(String.valueOf(city.calculateFood()));
        this.production.setText(String.valueOf(String.format("%.2f", city.calculateProduction())));
        this.cityName.setText(city.getName());
        this.isCapital.setText(String.valueOf(city.isCapital()));
        this.NumberOfUnassignedCitizens.setText("Number of unassigned citizens : " + city.numberOfUnassignedCitizens());
        initBuyTileSpinner();
        initAssignCitizenSpinner();
        initCitizenMenu();
        initProductMenu();
        initializeBuildingMenu();
        initializeUnitMenu();
        initAttackSpinner();
    }

    private void initAssignCitizenSpinner() {
        SpinnerValueFactory<Integer> xSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_WIDTH - 1);
        SpinnerValueFactory<Integer> ySpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_HEIGHT - 1);
        assignCitizenXSpinner.setValueFactory(xSpinnerValueFactory);
        assignCitizenYSpinner.setValueFactory(ySpinnerValueFactory);
        if (city.numberOfUnassignedCitizens() == 0) {
            this.assignBtn.setDisable(true);
        } else {
            this.assignBtn.setDisable(false);
        }
    }

    private void initAttackSpinner() {
        SpinnerValueFactory<Integer> xSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_WIDTH - 1);
        SpinnerValueFactory<Integer> ySpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_HEIGHT - 1);
        attackXSpinner.setValueFactory(xSpinnerValueFactory);
        attackYSpinner.setValueFactory(ySpinnerValueFactory);

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
        locationXValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_WIDTH - 1);
        locationXValueFactory.setValue(locationX);
        buyTileLocationXSpinner.setValueFactory(locationXValueFactory);
        buyTileLocationXSpinner.valueProperty().addListener((observableValue, integer, t0) -> {
            locationX = buyTileLocationXSpinner.getValue();
            // todo : check if another civ owns tile
            //if ()
            //    buyTileBtn.setDisable(true);
            //else
            //    buyTileBtn.setDisable(false);
        });

        locationYValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_HEIGHT - 1);
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
        if (city.calculateGold() == 0)
            buyTileBtn.setDisable(true);
        String command = "city buy tile -p " + this.city.getLocation().getRow() + " " + this.city.getLocation().getCol();
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if( !response.isOK()){
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
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
        if (selectedCitizen.isLocked()) {
            command = "city citizen unlock -p " + selectedCitizen.getLocation().getRow() + " " + selectedCitizen.getLocation().getCol();
        } else {
            command = "city citizen lock -p " + selectedCitizen.getLocation().getRow() + " " + selectedCitizen.getLocation().getCol();
        }
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if( !response.isOK()){
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        selectedCitizen = null;
        back();
    }

    private void initializeBuildingMenu() {
        city = MenuStack.getInstance().getCookies().getSelectedCity();
        // todo: check is ok? bug?
        ArrayList<TechnologyEnum> civTechnologies = DatabaseQuerier.getTechnologies();
        for (BuildingEnum buildingEnum : BuildingEnum.values()) {
            MenuItem item = new MenuItem(buildingEnum.name() + " " + buildingEnum.requiredTechName());
            item.setOnAction(actionEvent -> {
                requirements.setVisible(true);
                requirements.getChildren().removeAll(requirements.getChildren());
                for (TechnologyEnum tech : buildingEnum.getRequiredTechs()) {
                    Text text = new Text(tech.name());
                    if (civTechnologies.contains(tech))
                        text.setFill(Color.GREEN);
                    else
                        text.setFill(Color.RED);
                    requirements.getChildren().add(text);
                }
                if (!buildingEnum.checkIfHasRequiredTechs(civTechnologies)) {
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
        ArrayList<TechnologyEnum> civTechnologies = DatabaseQuerier.getTechnologies();
        for (UnitEnum unitEnum : DatabaseQuerier.getUnitEnums()) {
            MenuItem item = new MenuItem(unitEnum.name());
            item.setOnAction(actionEvent -> {
                if (!unitEnum.hasRequiredTech(civTechnologies)) {
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
        String command = "city build building -n " + selectedBuilding.name();
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if( !response.isOK()){
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        selectedBuilding = null;
        back();
    }

    public void buyUnit() {
        City city = MenuStack.getInstance().getCookies().getSelectedCity();
        if (selectedUnit == null) {
            return;
        }
        String command = "city buy unit -u " + selectedUnit.name();
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if( !response.isOK()){
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }

    public void assignBtnAction(ActionEvent actionEvent) {
        String command = "city citizen assign -p " + assignCitizenXSpinner.getValue() + " " + assignCitizenYSpinner.getValue();
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if( !response.isOK()){
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }

    public void unAssign(ActionEvent actionEvent) {
        if (selectedCitizen == null) {
            return;
        }
        String command = "city citizen unassign -p " + selectedCitizen.getLocation().getRow() + " " + selectedCitizen.getLocation().getCol();
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if( !response.isOK()){
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }

    public void attackBtnAction(ActionEvent actionEvent) {

        int locationX = (int) attackXSpinner.getValue();
        int locationY = (int) attackYSpinner.getValue();
        String command = "city attack -p " + locationX + " " + locationY;
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if( !response.isOK()){
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }
}
