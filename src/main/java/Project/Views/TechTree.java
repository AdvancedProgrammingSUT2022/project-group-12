package Project.Views;

import Project.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TechTree implements ViewController {

    public ScrollPane techTreeScrollPane;


   public void initialize(){
       ImageView node = new ImageView(new Image(App.class.getResource("/images/technologies/techTree2.jpg").toExternalForm()));
       techTreeScrollPane.setContent(node);
   }


    public void backToPanel(ActionEvent actionEvent) {
       MenuStack.getInstance().popMenu();
    }
}
