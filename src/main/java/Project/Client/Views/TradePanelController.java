package Project.Client.Views;

import Project.Client.Utils.DatabaseQuerier;
import Project.Enums.ResourceEnum;
import Project.Models.Resource;
import Project.Server.Views.RequestHandler;
import Project.Utils.CommandResponse;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Random;

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

    public Spinner<Integer> rivalGoldSpinner = new Spinner<>();
    public Spinner<Integer> ownGoldSpinner = new Spinner<>();
    private String suggest;
    private String required;

    public MenuButton rivalCivsComboBox;
    private String selectedCivName;

    public void initialize() {
        //todo : handle selectedCiv
        System.out.println("entered");
        rivalOuterVbox.setVisible(false);
        labelRival.setText("Rival Option");
        initializeRivalCivMenuButton();
        System.out.println("afteerCivsRivals = ");
        rivalGoldToggleBtn.setDisable(true);
        rivalResourceToggleBtn.setDisable(true);
        initializeGoldToggleButton();
        initializeResourceToggleButtons();

    }

    private void initializeResourceToggleButtons() {
        rivalResourceToggleBtn.setOnAction(e -> {
                    rivalGoldToggleBtn.setSelected(false);
                    rivalInnerVbox.getChildren().clear();
                    VBox vBox = new VBox(new Label("Resource Option"));
                    resourceRivalMenu = new MenuButton();
                    for (ResourceEnum re :
                            DatabaseQuerier.getCivResourcesByName(selectedCivName).stream().map(x -> x.getResourceEnum()).toList()) {
                        MenuItem menuItem = new MenuItem(re.name().toLowerCase());
                        menuItem.setOnAction(e1 -> {
                             required = re.name().toLowerCase();
                             resourceRivalMenu.setText(re.name().toLowerCase());
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
                            DatabaseQuerier.getCivResources().stream().map(Resource::getResourceEnum).toList()) {
                        MenuItem menuItem = new MenuItem(re.name().toLowerCase());
                        menuItem.setOnAction(e1 -> {
                             suggest  = re.name().toLowerCase();
                             resourceOwnMenu.setText(re.name().toLowerCase());
                        });
                        resourceOwnMenu.getItems().add(menuItem);
                    }
                    vBox.getChildren().add(resourceOwnMenu);
                    myInnerVbox.getChildren().add(resourceOwnMenu);
                }
        );
    }

    private void initializeGoldToggleButton() {
        rivalGoldToggleBtn.setOnAction(e -> {
            rivalInnerVbox.getChildren().clear();
            rivalResourceToggleBtn.setSelected(false);
            VBox vBox = new VBox(new Label("Gold Amount")); vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(20);
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, DatabaseQuerier.getGoldCivilizationByName(selectedCivName));
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
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, DatabaseQuerier.getGoldOfCurrentCiv());
            ownGoldSpinner.setValueFactory(spinnerValueFactory);
            ownGoldSpinner.valueProperty().addListener((observableValue, integer, t1) -> suggest = String.valueOf(ownGoldSpinner.getValue()));
            vBox.getChildren().add(ownGoldSpinner);
            myInnerVbox.getChildren().add(vBox);
        });
    }

    private void initializeRivalCivMenuButton() {
        for (String civName :
                DatabaseQuerier.getNeighborsCivsName()) {
            MenuItem menuItem = new MenuItem(civName);
            System.out.println("civ.getName() = " + civName);
                rivalCivsComboBox.getItems().add(menuItem);
            menuItem.setOnAction(e -> {
                rivalOuterVbox.setVisible(true);
                //todo : handle selected civ
                this.selectedCivName = civName;
                System.out.println(this.selectedCivName);
                labelRival.setText(civName + " Option");
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
        String command = "trade create -s " + suggest + " -r " + required + " -c " + selectedCivName + " -n " +  new Random().nextInt(2009);
        System.out.println("command = " + command);
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if(!response.isOK()){
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }
}
