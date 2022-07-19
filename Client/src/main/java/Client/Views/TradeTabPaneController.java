package Client.Views;

import Client.App;
import Project.Client.Utils.DatabaseQuerier;
import Project.Server.Views.RequestHandler;
import Project.Utils.CommandResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Random;
import java.util.stream.Collectors;

public class TradeTabPaneController implements ViewController {


    public VBox SourceVbox;
    public ToggleButton Goldbtn;
    public ToggleButton Resourcebtn;
    public MenuButton civilzationMenus;
    public Tab demandPanel;
    public Tab tradePanel;
    public Tab declarePeace;
    public Tab declareWar;
    String selectedCivName;

    MenuButton resourceMenu = new MenuButton();
    String request;
    Spinner<Integer> rivalCivGoldSpinner = new Spinner<>();

    public void initialize() throws IOException {
        tradePanel.setClosable(false);
        demandPanel.setClosable(false);
        declareWar.setClosable(false);
        declarePeace.setClosable(false);
        tradePanel.setContent(new FXMLLoader(App.class.getResource("/Project/fxml/TradeMenu.fxml")).load());
        declarePeace.setContent(new FXMLLoader(App.class.getResource("/Project/fxml/Peace.fxml")).load());
        declareWar.setContent(new FXMLLoader(App.class.getResource("/Project/fxml/DeclareWar.fxml")).load());
        initializeDemandPanel();
    }

    private void initializeDemandPanel() {
        for (String civNames :
                DatabaseQuerier.getNeighborsCivsName()) {
            MenuItem menuItem = new MenuItem(civNames);
            menuItem.setOnAction(e -> {
                System.out.println("hello");
                Goldbtn.setDisable(false);
                Resourcebtn.setDisable(false);
                this.selectedCivName = civNames;
            });
            civilzationMenus.getItems().add(menuItem);
        }
        Goldbtn.setDisable(true);
        Resourcebtn.setDisable(true);
        Goldbtn.setOnAction(e -> {
            SourceVbox.getChildren().clear();
            Resourcebtn.setSelected(false);
            initializeSpinnerValueFactory();
            VBox vBox = new VBox(new Text("Gold to select"));
            vBox.setSpacing(10);
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(rivalCivGoldSpinner);
            SourceVbox.getChildren().add(vBox);
            initializeSpinnerValueFactory();
        });
        Resourcebtn.setOnAction(e -> {
            SourceVbox.getChildren().clear();
            Goldbtn.setSelected(false);
            VBox vBox = new VBox(new Text("Resource to select"));
            vBox.setSpacing(10);
            vBox.setAlignment(Pos.CENTER);
            initializeResourceMenu();
            vBox.getChildren().add(resourceMenu);
            SourceVbox.getChildren().add(vBox);
        });
    }

    private void initializeSpinnerValueFactory() {
        int goldOfCiv = DatabaseQuerier.getGoldCivilizationByName(selectedCivName);
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, goldOfCiv);
        rivalCivGoldSpinner.setValueFactory(spinnerValueFactory);
        rivalCivGoldSpinner.valueProperty().addListener((observableValue, o, t1) -> {
            System.out.println("request = " + request);
            ;
            TradeTabPaneController.this.request = rivalCivGoldSpinner.getValue().toString();
            System.out.println("request = " + request);
        });
    }

    private void initializeResourceMenu() {
        resourceMenu.getItems().clear();
        for (String resourceName :
                DatabaseQuerier.getCivResourcesByName(selectedCivName).stream().map(e -> capitalizeFirstString(e.getResourceEnum().name().toLowerCase())).collect(Collectors.toList())) {
            MenuItem menuItem = new MenuItem(resourceName);
            menuItem.setOnAction(e -> {
                this.request = resourceName;
            });
            resourceMenu.getItems().add(menuItem);
        }
    }

    public String capitalizeFirstString(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void createDemand(ActionEvent actionEvent) {
        if (!Goldbtn.isSelected() && !Resourcebtn.isSelected()) return;
        if (request == null) return;
        String command = "demand create -r " + request + " -n " + selectedCivName + " -d " + new Random().nextInt(3000);
        System.out.println("command = " + command);
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if (!response.isOK()) {
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
