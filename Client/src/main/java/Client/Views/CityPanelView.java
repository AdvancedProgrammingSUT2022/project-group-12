package Client.Views;

import Client.Utils.DatabaseQuerier;
import Client.Utils.RequestSender;
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
import java.util.Optional;

public class CityPanelView implements ViewController {
    public Button addProductionBtn;
    public Button removeProduction;
    public MenuButton productionsMenu;
    String selectedProudtionName;
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
        initializeProductionMenu();
        this.TILEGRID_HEIGHT = DatabaseQuerier.getTileGridSize().getFirst();
        this.TILEGRID_WIDTH = DatabaseQuerier.getTileGridSize().getSecond();
        this.populationSize.setText(String.valueOf(city.getCitizensCount()));
        this.gold.setText(String.valueOf(DatabaseQuerier.getSelectedCityGold()));
        this.food.setText(String.valueOf(DatabaseQuerier.getSelectedCityFood()));
        this.production.setText(String.valueOf(String.format("%.2f", DatabaseQuerier.getSelectedCityProduction())));
        this.cityName.setText(city.getName());
        this.isCapital.setText(String.valueOf(city.isCapital()));
        this.NumberOfUnassignedCitizens.setText("Number of unassigned citizens : " + DatabaseQuerier.getSelectedCityNumberOfUnAssignedCitizen());
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
        xSpinnerValueFactory.setValue(city.getLocation().getRow());
        ySpinnerValueFactory.setValue(city.getLocation().getCol());
        assignCitizenXSpinner.setValueFactory(xSpinnerValueFactory);
        assignCitizenYSpinner.setValueFactory(ySpinnerValueFactory);
        if (DatabaseQuerier.getSelectedCityNumberOfUnAssignedCitizen() == 0) {
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
                productionsMenu.setText(p.toString());
                selectedProduction = p;
            });
            productionsMenu.getItems().add(item);
        }
    }
    public String capitalizeFirstString(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    private void initCitizenMenu() {
        city = MenuStack.getInstance().getCookies().getSelectedCity();
        for (Citizen citizen : DatabaseQuerier.getSelectedCityAssignedCitizens()) {
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
    private void initializeProductionMenu() {
        for (String name:
                DatabaseQuerier.getCurrentCivAvailableProductions()) {
            MenuItem menuItem = new MenuItem(capitalizeFirstString(name.toLowerCase()));
            menuItem.setOnAction(actionEvent -> {
                selectedProudtionName = menuItem.getText();
                productionsMenu.setText(selectedProudtionName);
                addProductionBtn.setDisable(false);
            });
            productionsMenu.getItems().add(menuItem);
        }
    }

    private void initBuyTileSpinner() {
        locationXValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_WIDTH - 1);
        locationXValueFactory.setValue(city.getLocation().getRow());
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
        locationYValueFactory.setValue(city.getLocation().getCol());
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
        if (DatabaseQuerier.getSelectedCityGold() == 0)
            buyTileBtn.setDisable(true);
        String command = "city buy tile -p " + this.city.getLocation().getRow() + " " + this.city.getLocation().getCol();
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
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
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
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
        ArrayList<TechnologyEnum> civTechnologies = DatabaseQuerier.getCurrentTechnologies();
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
        ArrayList<TechnologyEnum> civTechnologies = DatabaseQuerier.getCurrentTechnologies();
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
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
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
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
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
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
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
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
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
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if( !response.isOK()){
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }
    public void addToProductionQueue() {
        String command;
        if(selectedProudtionName == null) return;
        if(BuildingEnum.getBuildingEnumByName(selectedProudtionName.toUpperCase()) == null)
        {
            command = "city build unit -u " + selectedProudtionName;
        } else {
            command = "city build building -n " + selectedProudtionName;
        }
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if( !response.isOK()){
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }

    public void removeFromProductionQueue() {
        Optional<String> dialogOutput = new RemoveProductionDialog(DatabaseQuerier.getSelectedCityProductionQueue()).showAndWait();
        String result = extractNumber(dialogOutput.get().toString().toCharArray());
        if(!result.equals("")){
            int resultNumber = Integer.parseInt(result);
            String command = "city queue remove -n " + (resultNumber);
            CommandResponse response = RequestSender.getInstance().sendCommand(command);
            if( !response.isOK()){
                MenuStack.getInstance().showError(response.toString());
                return;
            } else {
                MenuStack.getInstance().showSuccess("production removed successfully");
            }
            back();
        }
    }
    public String extractNumber(char[] chars){
        StringBuilder extractNumber = new StringBuilder("");
        for (char ch:
             chars) {
            if(Character.isDigit(ch)) {
                extractNumber.append(ch);
            }
        }
        return extractNumber.toString();
    }
}
