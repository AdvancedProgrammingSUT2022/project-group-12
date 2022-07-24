package Client.Views;

import Client.Utils.DatabaseQuerier;
import Client.Utils.RequestSender;
import Project.Models.Units.Unit;
import Project.Utils.CommandResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.Optional;

public class UnitPanelView implements ViewController {
    public Button pillageBtn;
    int TILEGRID_WIDTH;
    int TILEGRID_HEIGHT;
    @FXML
    private Text movementPoint;
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
        this.TILEGRID_HEIGHT = DatabaseQuerier.getTileGridSize().getFirst();
        this.TILEGRID_WIDTH = DatabaseQuerier.getTileGridSize().getSecond();
        unit = DatabaseQuerier.getSelectedUnit();
        System.out.println("unit.getClass().getName() = " + unit.getClass().getName());
        initializeSpinners();
        name.setText(unit.getUnitType().name());
        unitType.setText(unit.getUnitType().name());
        unitCivilization.setText(unit.getCivName());
        locationX = unit.getLocation().getRow();
        locationY = unit.getLocation().getCol();
        movementPoint.setText(String.valueOf(unit.getAvailableMoveCount()));
        unitXLocation.setText(String.valueOf(unit.getLocation().getRow()));
        unitYLocation.setText(String.valueOf(unit.getLocation().getCol()));
        unitHealth.setText(String.valueOf(unit.getHealth()));
        combatStrength.setText(String.valueOf(unit.getUnitType().getCombatStrength()));
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
        Unit selectedUnit = DatabaseQuerier.getSelectedUnit();
        if (selectedUnit.getUnitType().name().equals("SETTLER")) {
            buttonBox.getChildren().add(initClearLandButton());
            cityName = new TextField("Name");
            createCreateCityButton();
            buttonBox.getChildren().add(createCityBtn);
        }
    }

    private Button initClearLandButton() {
        Button button = new Button("clear land");
        button.setOnAction(actionEvent -> {
            String command = "unit clear land"; // todo: implement on server
            CommandResponse response = RequestSender.getInstance().sendCommand(command);
            if (!response.isOK()) {
                MenuStack.getInstance().showError(response.toString());
                return;
            } else {
                MenuStack.getInstance().showSuccess(response.getMessage());
            }
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
            CommandResponse response = RequestSender.getInstance().sendCommand(command);
            if (!response.isOK()) {
                MenuStack.getInstance().showError(response.toString());
                return;
            } else {
                System.out.println("response.getMessage() = " + response.getMessage());
                MenuStack.getInstance().showSuccess(response.getMessage());
            }
            back();
        });
    }


    private void initializeYSpinner(Spinner<Integer> ySpinner, Button moveUnitBtn) {
        yValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_HEIGHT - 1);
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
        unit = DatabaseQuerier.getSelectedUnit();
        System.out.println("unit.getClass().getName() = " + unit.getClass().getName());
        xValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_WIDTH - 1);
        xValueFactory.setValue(unit.getLocation().getRow());
        xSpinner.setValueFactory(xValueFactory);
        xSpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            locationX = xSpinner.getValue();
            moveUnitBtn.setStyle("-fx-border-color: none;");
        });

        yValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, TILEGRID_HEIGHT - 1);
        yValueFactory.setValue(unit.getLocation().getCol());
        ySpinner.setValueFactory(yValueFactory);
        ySpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            locationY = ySpinner.getValue();
            moveUnitBtn.setStyle("-fx-border-color: none;");
        });
    }

    public void moveUnit() {
        unit = DatabaseQuerier.getSelectedUnit();
        // todo : check destination for other units
//        if () {
//            moveUnitBtn.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
//            return;
//        }
        int locationX = xSpinner.getValue();
        int locationY = ySpinner.getValue();
        String command = "unit move -p " + locationX + " " + locationY;
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else if (RequestSender.getInstance().getParameter("foundRuin") != null) {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }

    public void sleep() {
        unit = DatabaseQuerier.getSelectedUnit();
        String command = "unit sleep";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        unitState.setText("SLEEP");
    }

    public void alert() {
        unit = DatabaseQuerier.getSelectedUnit();
        String command = "unit alert";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        unitState.setText("ALERT");
    }

    public void fortify() {
        unit = DatabaseQuerier.getSelectedUnit();
        String command = "unit fortify";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        unitState.setText("FORTIFY");
    }

    public void awake() {
        unit = DatabaseQuerier.getSelectedUnit();
        String command = "unit wake";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
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
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if (response == CommandResponse.CITY_CAPTURED || response == CommandResponse.CAPITAL_CAPTURED) {
            // dialog
            MenuStack.getInstance().showSuccess("enemy city was captured");
            Optional<String> optional = new WinCityDialog(response == CommandResponse.CAPITAL_CAPTURED).showAndWait();
            if (optional.get().equals("Annexed")) {
                command = "capture annex -p " + locationX + " " + locationY;
                response = RequestSender.getInstance().sendCommand(command);
            } else {
                command = "capture destroy -p " + locationX + " " + locationY;
                response = RequestSender.getInstance().sendCommand(command);
            }
        }
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        // response = CommandResponse.INVALID_COMMAND;
        String message;
        if (response.equals(CommandResponse.OK)) {
            String enemyDamage = RequestSender.getInstance().getParameter("enemyDamage");
            String unitDamage = RequestSender.getInstance().getParameter("unitDamage");
            message = enemyDamage + " damage to enemy and " + unitDamage + " damage to unit";
        } else {
            message = response.toString();
            MenuStack.getInstance().showError(message);
            return;
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
        Unit unit = DatabaseQuerier.getSelectedUnit();
        String command = "unit delete";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }

    public void pillage() {
        Unit unit = DatabaseQuerier.getSelectedUnit();
        String command = "unit pillage";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
    }

    public void buildImprovement() {
        String command = "unit build improvement";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }

    public void setupUnit() {
        String command = "unit setup";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
    }
}
