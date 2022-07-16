package Project.Client.Views;

import Project.Enums.ResourceEnum;
import Project.Models.Civilization;
import Project.Server.Controllers.GameController;
import Project.Server.Views.RequestHandler;
import Project.Utils.CommandResponse;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class TradePanelController implements ViewController {
    public ToggleButton ownGoldToggleBtn;
    public ToggleButton ownResourceToggleBtn;
    public VBox myInnerVbox;
    public Label labelRival;
    public ToggleButton rivalGoldToggleBtn;
    public ToggleButton rivalResourceToggleBtn;
    public VBox rivalInnerVbox;
    public VBox rivalOuterVbox;
    public VBox myOuterVbox;
    public TextField tradeNameTextField;
    private MenuButton resourceRivalMenu;
    private MenuButton resourceOwnMenu;
    private ResourceEnum suggestResource;
    private ResourceEnum requiredResource;
    public Spinner<Integer> rivalGoldSpinner = new Spinner<>();
    public Spinner<Integer> ownGoldSpinner = new Spinner<>();
    private String suggest;
    private String required;

    private Civilization selectedCiv;
    public MenuButton rivalCivsComboBox;

    public void initialize() {
        rivalOuterVbox.setVisible(false);
        labelRival.setText("Rival Option");
        Civilization currentCiv = GameController.getGame().getCurrentCivilization();
        initializeRivalCivMenuButton(currentCiv);
        rivalGoldToggleBtn.setDisable(true);
        rivalResourceToggleBtn.setDisable(true);
        initializeGoldToggleButton(currentCiv);
        initializeResourceToggleButtons(currentCiv);

    }

    private void initializeResourceToggleButtons(Civilization currentCiv) {
        rivalResourceToggleBtn.setOnAction(e -> {
                    rivalGoldToggleBtn.setSelected(false);
                    rivalInnerVbox.getChildren().clear();
                    VBox vBox = new VBox(new Label("Resource Option"));
                    resourceRivalMenu = new MenuButton();
                    for (ResourceEnum re :
                            selectedCiv.getResourceEnums()) {
                        MenuItem menuItem = new MenuItem(re.name().toLowerCase());
                        menuItem.setOnAction(e1 -> {
                             required = re.name().toLowerCase();
                        });
                        resourceRivalMenu.getItems().add(menuItem);
                    }
                    vBox.getChildren().add(resourceRivalMenu);
                    rivalInnerVbox.getChildren().add(resourceRivalMenu);
                }
        );
        ownResourceToggleBtn.setOnAction(e -> {
                    ownGoldToggleBtn.setSelected(false);
                    myInnerVbox.getChildren().clear();
                    VBox vBox = new VBox(new Label("Resource Option"));
                    resourceOwnMenu = new MenuButton();
                    for (ResourceEnum re :
                            currentCiv.getResourceEnums()) {
                        MenuItem menuItem = new MenuItem(re.name().toLowerCase());
                        menuItem.setOnAction(e1 -> {
                             suggest  = re.name().toLowerCase();
                        });
                        resourceOwnMenu.getItems().add(menuItem);
                    }
                    vBox.getChildren().add(resourceOwnMenu);
                    myInnerVbox.getChildren().add(resourceOwnMenu);
                }
        );
    }

    private void initializeGoldToggleButton(Civilization currentCiv) {
        rivalGoldToggleBtn.setOnAction(e -> {
            rivalInnerVbox.getChildren().clear();
            rivalResourceToggleBtn.setSelected(false);
            VBox vBox = new VBox(new Label("Gold Amount")); vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(20);
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, selectedCiv.calculateCivilizationGold());
            rivalGoldSpinner.setValueFactory(spinnerValueFactory);
            rivalGoldSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
                @Override
                public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                    required = String.valueOf(rivalGoldSpinner.getValue());
                }
            });
            vBox.getChildren().add(rivalGoldSpinner);
            rivalInnerVbox.getChildren().add(vBox);
        });
        ownGoldToggleBtn.setOnAction(e -> {
            myInnerVbox.getChildren().clear();
            ownResourceToggleBtn.setSelected(false);
            VBox vBox = new VBox(new Label("Gold Amount")); vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(20);
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, currentCiv.calculateCivilizationGold());
            ownGoldSpinner.setValueFactory(spinnerValueFactory);
            ownGoldSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
                @Override
                public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                    suggest = String.valueOf(ownGoldSpinner.getValue());
                }
            });
            vBox.getChildren().add(ownGoldSpinner);
            myInnerVbox.getChildren().add(vBox);
        });
    }

    private void initializeRivalCivMenuButton(Civilization currentCiv) {
        System.out.println("currentCiv.getName() = " + currentCiv.getName());
        for (Civilization civ :
                GameController.getGame().getCivilizations()) {
            MenuItem menuItem = new MenuItem(civ.getName());
            System.out.println("civ.getName() = " + civ.getName());
            if (!civ.getName().equals(currentCiv.getName())) {
                rivalCivsComboBox.getItems().add(menuItem);
            }
            menuItem.setOnAction(e -> {
                rivalOuterVbox.setVisible(true);
                this.selectedCiv = civ;
                System.out.println(this.selectedCiv);
                labelRival.setText(selectedCiv.getName() + " Option");
                rivalGoldToggleBtn.setDisable(false);
                rivalResourceToggleBtn.setDisable(false);
            });
        }
    }


    public void sendRequest(ActionEvent actionEvent) {
        if(!rivalGoldToggleBtn.isSelected() && !rivalResourceToggleBtn.isSelected() ) return;
        if(!ownGoldToggleBtn.isSelected() && !ownResourceToggleBtn.isSelected() ) return;
        if(rivalGoldToggleBtn.isSelected() && ownGoldToggleBtn.isSelected()) return;
        if(required == null || suggest == null) return;
        if(this.tradeNameTextField.getText() == null){
            this.tradeNameTextField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            return;
        }
        String command = "trade create -s " + suggest + " -r " + required + " -c " + selectedCiv.getName() + " -n " +  this.tradeNameTextField.getText();
        System.out.println("command = " + command);
        CommandResponse response = RequestHandler.getInstance().handle(command);
        back();
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }
}
