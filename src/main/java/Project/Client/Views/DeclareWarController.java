package Project.Client.Views;

import Project.Client.Utils.DatabaseQuerier;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class DeclareWarController implements ViewController {

    public MenuButton rivalCivsMenuButton;
    String selectedCivName;


    public void initialize(){
       initializeRivalCivMenuButton();
    }
    private void initializeRivalCivMenuButton() {
        for (String civName :
                DatabaseQuerier.getNeighborsCivsName()) {
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



}
