package Project.Client.Views;

import Project.Enums.ResourceEnum;
import Project.Models.Civilization;
import Project.Models.Game;
import Project.Server.Controllers.GameController;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class TradePanelController implements ViewController {
    public ToggleButton ownGoldToggleBtn;
    public ToggleButton ownResourceToggleBtn;
    public VBox myInnerVbox;
    public Label labelRival;
    public ToggleButton rivalGoldToggleBtn;
    public ToggleButton rivalResourceToggleBtn;
    public VBox rivalInnerVbox;
    private MenuButton resourceRivalMenu;
    private MenuButton resourceOwnMenu;
    public Spinner<Integer> rivalGoldSpinner = new Spinner<>();
    public Spinner<Integer> ownGoldSpinner = new Spinner<>();

    private Civilization selectedCiv;
    public MenuButton rivalCivsComboBox;

    public void initialize(){
        labelRival.setText("Rival Option");
        Civilization currentCiv = GameController.getGame().getCurrentCivilization();
        initializeRivalCivMenuButton(currentCiv);
        rivalGoldToggleBtn.setDisable(true);
        rivalResourceToggleBtn.setDisable(true);
        initializeGoldToggleButton(currentCiv,selectedCiv);
        rivalResourceToggleBtn.setOnAction(e -> {
            rivalGoldToggleBtn.setSelected(false);
            VBox vBox = new VBox(new Label("Resource Option"));
            resourceRivalMenu = new MenuButton();
            for (ResourceEnum re :
                  selectedCiv.getResources().keySet() ) {
                MenuItem menuItem = new MenuItem(re.name().toLowerCase());

        }}
        );

    }

    private void initializeGoldToggleButton(Civilization currentCiv,Civilization selectedCiv) {
        rivalGoldToggleBtn.setOnAction( e -> {
            rivalResourceToggleBtn.setSelected(false);
            VBox vBox = new VBox(new Label("Gold Amount")); vBox.setSpacing(20);
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,selectedCiv.calculateCivilizationGold());
            rivalGoldSpinner.setValueFactory(spinnerValueFactory);
            vBox.getChildren().add(rivalGoldSpinner);
            rivalInnerVbox.getChildren().add(vBox);
        });
        ownGoldToggleBtn.setOnAction( e -> {
            ownGoldToggleBtn.setSelected(false);
            VBox vBox = new VBox(new Label("Gold Amount")); vBox.setSpacing(20);
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, currentCiv.calculateCivilizationGold());
            ownGoldSpinner.setValueFactory(spinnerValueFactory);
            vBox.getChildren().add(ownGoldSpinner);
            myInnerVbox.getChildren().add(vBox);
        });
    }

    private void initializeRivalCivMenuButton(Civilization currentCiv) {
        for (Civilization civ:
                GameController.getGame().getCivilizations()) {
            MenuItem menuItem = new MenuItem(civ.getName());
            if(!civ.equals(currentCiv)){
                rivalCivsComboBox.getItems().add(menuItem);
            }
            menuItem.setOnAction(e -> {
                this.selectedCiv = civ;
                labelRival.setText(selectedCiv.getName() + " Option");
                rivalGoldToggleBtn.setDisable(false);
                rivalResourceToggleBtn.setDisable(false);
            });
        }
    }

    private void initializeInnerVboxRival(Civilization selectedCiv) {



    }

}
