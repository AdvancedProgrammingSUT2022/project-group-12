package Client.Views;

import Client.Utils.DatabaseQuerier;
import Client.Utils.RequestHandler;
import Project.Utils.CommandResponse;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.util.Random;

public class PeaceController implements ViewController{
    public MenuButton rivalCivsMenuButton;
    String selectedCivName;


    public void initialize(){
        initializeRivalCivMenuButton();
    }
    private void initializeRivalCivMenuButton() {
        for (String civName : DatabaseQuerier.getCurrentCivInWarWith()) {
            MenuItem menuItem = new MenuItem(civName);
            System.out.println("civ.getName() = " + civName);
            rivalCivsMenuButton.getItems().add(menuItem);
            menuItem.setOnAction(e -> {
                //todo : handle selected civ
                this.selectedCivName = civName;
                rivalCivsMenuButton.setText(civName);
            });
        }
    }


    public void sendRequest() {
        if (this.selectedCivName == null){
            MenuStack.getInstance().showError("No civ selected");
            return;
        }
        String command = "peace create -c " + this.selectedCivName + " -n " + new Random().nextInt();
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if ( !response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }
    public void back(){
        MenuStack.getInstance().popMenu();
    }
}
