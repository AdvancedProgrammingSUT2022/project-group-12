package Project.Client.Views;

import Project.Client.Utils.DatabaseQuerier;
import Project.Enums.UnitStates;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;
import Project.Server.Views.RequestHandler;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.HashMap;

public class UnitPanelView implements ViewController {
    int TILEGRID_WIDTH;
    int TILEGRID_HEIGHT;

    public Button pillageBtn;
    @FXML
    private Button attackUnitBtn;
    @FXML
    private HBox buttonBox;
    @FXML
    private Text name;
    @FXML
    private Button moveUnitBtn;
    @FXML
    private Text unitType;
    @FXML
    private Text unitCivilization;
    @FXML
    private Text unitXLocation;
    @FXML
    private Text unitYLocation;
    @FXML
    private Text unitState;
    @FXML
    private Text unitHealth;
    @FXML
    private Text combatStrength;
    @FXML
    private Spinner<Integer> xSpinner;
    @FXML
    private Spinner<Integer> xAttackSpinner;
    @FXML
    private Spinner<Integer> yAttackSpinner;
    private int locationX;
    private SpinnerValueFactory<Integer> xValueFactory;
    @FXML
    private Spinner<Integer> ySpinner;
    private int locationY;
    private SpinnerValueFactory<Integer> yValueFactory;
    private Unit unit;
    private TextField cityName;
    private Button createCityBtn;

    public void initialize() {
        this.TILEGRID_HEIGHT = DatabaseQuerier.getTileGridSize().get("Height");
        this.TILEGRID_WIDTH = DatabaseQuerier.getTileGridSize().get("Width");
        unit = MenuStack.getInstance().getCookies().getSelectedUnit();
        initializeSpinners();
        name.setText(unit.getType().name());
        unitType.setText(unit.getType().name());
        unitCivilization.setText(unit.getCivilization().getName());
        locationX = unit.getLocation().getRow();
        locationY = unit.getLocation().getCol();
        unitXLocation.setText(String.valueOf(unit.getLocation().getRow()));
        unitYLocation.setText(String.valueOf(unit.getLocation().getCol()));
        unitHealth.setText(String.valueOf(unit.getHealth()));
        combatStrength.setText(String.valueOf(unit.getType().getCombatStrength()));
        unitState.setText(unit.getState().toString());
    }

    private void initializeSpinners() {
        initButtonBox();
        initializeXSpinner(xSpinner, moveUnitBtn);
        initializeYSpinner(ySpinner, moveUnitBtn);
        initializeXSpinner(xAttackSpinner, attackUnitBtn);
        initializeYSpinner(yAttackSpinner, attackUnitBtn);
    }

    private void initButtonBox() {
        Unit selectedUnit = MenuStack.getInstance().getCookies().getSelectedUnit();
        if (selectedUnit.getType().name().equals("SETTLER")) {
            buttonBox.getChildren().add(initClearLandButton());
            cityName = new TextField("Name");
            createCreateCityButton();
            buttonBox.getChildren().add(createCityBtn);
        }
    }

    private Button initClearLandButton() {
        Button button = new Button("clear land");
        button.setOnAction(actionEvent -> {
            Tile tile = unit.getTile();
            tile.clearLand();
            back();
        });
        return button;
    }

    private void createCreateCityButton() {
        createCityBtn = new Button("Create City");
        createCityBtn.setOnAction(actionEvent -> {
            if (cityName.getText().isEmpty())
                return;
            String command = "unit found city";
            CommandResponse response = RequestHandler.getInstance().handle(command);
            back();
        });
    }


    private void initializeYSpinner(Spinner<Integer> ySpinner, Button moveUnitBtn) {
        yValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,  TILEGRID_HEIGHT - 1);
        System.out.println("x: " + unit.getLocation().getRow());
        System.out.println("y: " + unit.getLocation().getCol());
        yValueFactory.setValue(unit.getLocation().getCol());
        ySpinner.setValueFactory(yValueFactory);
        ySpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            locationY = ySpinner.getValue();
            moveUnitBtn.setStyle("-fx-border-color: none;");
        });
    }

    private void initializeXSpinner(Spinner<Integer> xSpinner, Button spinnerButton) {
        unit = MenuStack.getInstance().getCookies().getSelectedUnit();
        xValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,  TILEGRID_WIDTH - 1);
        xValueFactory.setValue(unit.getLocation().getRow());
        xSpinner.setValueFactory(xValueFactory);
        xSpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            locationX = xSpinner.getValue();
            moveUnitBtn.setStyle("-fx-border-color: none;");
        });

        yValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,  TILEGRID_HEIGHT - 1);
        yValueFactory.setValue(unit.getLocation().getCol());
        ySpinner.setValueFactory(yValueFactory);
        ySpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            locationY = ySpinner.getValue();
            moveUnitBtn.setStyle("-fx-border-color: none;");
        });
    }

    public void moveUnit() {
        unit = MenuStack.getInstance().getCookies().getSelectedUnit();
        // todo : check destination for other units
//        if () {
//            moveUnitBtn.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
//            return;
//        }
        int locationX = xSpinner.getValue();
        int locationY = ySpinner.getValue();
        String command = "unit move -p " + locationX + " " + locationY;
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if (response.isOK()) back();
    }

    public void sleep() {
        unit = MenuStack.getInstance().getCookies().getSelectedUnit();
        String command = "unit sleep";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        unitState.setText("SLEEP");
    }

    public void alert() {
        unit = MenuStack.getInstance().getCookies().getSelectedUnit();
        String command = "unit alert";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        unitState.setText("ALERT");
    }

    public void fortify() {
        unit = MenuStack.getInstance().getCookies().getSelectedUnit();
        String command = "unit fortify";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        unitState.setText("FORTIFY");
    }

    public void awake() {
        unit = MenuStack.getInstance().getCookies().getSelectedUnit();
        String command = "unit wake";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        unitState.setText("AWAKE");
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }

    public void exit() {
        System.exit(0);
    }

    public void attackUnit(ActionEvent actionEvent) {
        int locationX = xAttackSpinner.getValue();
        int locationY = yAttackSpinner.getValue();
        String command;
        command = "unit attack -p " + locationX + " " + locationY;
        CommandResponse response = RequestHandler.getInstance().handle(command);
        HashMap<String, String> parameters = RequestHandler.getInstance().getParameters();
        // response = CommandResponse.INVALID_COMMAND;
        String message;
        if (response.equals(CommandResponse.OK)) {
            message = parameters.get("enemyDamage") + " damage to enemy and " + parameters.get("unitDamage") + " damage to unit";
        } else {
            message = response.toString();
            MenuStack.getInstance().showError(message);
        }
        back();
    }

    public Pane createTextPane(String response) {
        Pane pane = new Pane();
        pane.setPrefHeight(70);
        TextFlow textFlow = new TextFlow(new Text(response));
        textFlow.setTranslateY(20);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(textFlow);
        return pane;
    }

    public void delete() {
        Unit unit = MenuStack.getInstance().getCookies().getSelectedUnit();
        String command = "unit delete";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        back();
    }

    public void pillage()
    {
        Unit unit = MenuStack.getInstance().getCookies().getSelectedUnit();
        String command = "unit pillage";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
        }
    }

    public void buildImprovement() {
        Unit unit = MenuStack.getInstance().getCookies().getSelectedUnit();
        String command = "unit build improvement";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        back();
    }
}
