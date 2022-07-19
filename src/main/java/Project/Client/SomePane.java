package Project.Client;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

class SomePane extends AnchorPane {

    public SomePane(){
       super();
    }
    public SomePane(Node... var1) {
        this.getChildren().addAll(var1);
    }


}
