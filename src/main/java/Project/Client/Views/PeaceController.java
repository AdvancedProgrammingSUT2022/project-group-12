package Project.Client.Views;

import Project.Client.Utils.DatabaseQuerier;
import Project.Server.Views.RequestHandler;
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
        for (String civName :
                DatabaseQuerier.getCurrentCivInPeaceWith()) {
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
            Project.Client.Views.MenuStack.getInstance().showError("No civ selected");
            return;
        }
        String command = "peace create -c " + this.selectedCivName + " -n " + new Random().nextInt();
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if ( !response.isOK()) {
            Project.Client.Views.MenuStack.getInstance().showError(response.toString());
            return;
        } else {
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
        back();
    }
    public void back(){
        Project.Client.Views.MenuStack.getInstance().popMenu();
    }
}
