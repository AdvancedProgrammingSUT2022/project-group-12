package Project.Views;

import Project.Controllers.GameController;
import Project.Models.Cities.City;
import Project.Models.Citizen;
import Project.Models.Production;
import Project.Utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class CityPanelView implements ViewController {
    @FXML
    private MenuButton productionMenu;
    private Production selectedProduction;
    @FXML
    private Button lockUnlockBtn;
    private Citizen selectedCitizen;
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
        city = GameController.getGame().getCurrentCivilization().getSelectedCity();
        this.populationSize.setText(String.valueOf(city.getCitizensCount()));
        this.gold.setText(String.valueOf(city.getGold()));
        this.food.setText(String.valueOf(city.getFood()));
        this.production.setText(String.valueOf(city.getProduction()));
        this.cityName.setText(city.getName());
        this.isCapital.setText(String.valueOf(city.isCapital()));
        initBuyTileSpinner();
        initCitizenMenu();
        initProductMenu();
    }

    private void initProductMenu() {
        city = GameController.getGame().getCurrentCivilization().getSelectedCity();
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
        city = GameController.getGame().getCurrentCivilization().getSelectedCity();
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
    }

    public void gotoShop() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("ShopPage"));
    }

    public void exit() {
        System.exit(0);
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }

    public void BuyTile() {
        city = GameController.getGame().getCurrentCivilization().getSelectedCity();
        if (city.getGold() == 0)
            buyTileBtn.setDisable(true);
        // todo : buy tile
    }


    public void SpeedUp() {
        if (selectedProduction == null)
            return;
        selectedProduction.decreaseRemainedProduction(10);
        initProductMenu();
    }

    public void lockOrUnlock() {
        if (selectedCitizen == null)
            return;
        selectedCitizen.setLock(!selectedCitizen.isLocked());
        selectedCitizen = null;
        initCitizenMenu();
    }
}
