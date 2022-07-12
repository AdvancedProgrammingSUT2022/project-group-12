package Project.Client.Views;

import Project.Client.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TechTree implements ViewController {

    @FXML
    private ScrollPane techTreeScrollPane;
    @FXML
    private Button backToPanel;


    public void initialize(){
       ImageView node = new ImageView(new Image(App.class.getResource("/images/technologies/techTree2.jpg").toExternalForm()));
       techTreeScrollPane.setContent(node);
   }


    public void backToPanel() {
       MenuStack.getInstance().popMenu();
    }


}
