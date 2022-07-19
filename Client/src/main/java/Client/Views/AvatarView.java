package Client.Views;

import Client.App;
import Project.Enums.AvatarURLEnum;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class AvatarView implements ViewController {
    private final FileChooser fileChooser = new FileChooser();
    @FXML
    private VBox imageBox;
    @FXML
    private
    ImageView imageView;
    @FXML
    private Text userID;

    public void initialize() {
        userID.setText("-" + MenuStack.getInstance().getUser().getNickname());
        imageView.setImage(AvatarView.getAvatarImage(MenuStack.getInstance().getUser().getAvatarURL()));
        for (int i = 0; i < 52; i += 2) {
            HBox newBox = new HBox();
            newBox.setSpacing(100);
            newBox.getChildren().addAll(returnImages(i), returnImages(i + 1));
            imageBox.getChildren().add(newBox);
        }
    }

    public static Image getAvatarImage(AvatarURLEnum avatarURL) {
        return new Image(App.getResourcePath("/images/avatars/" + avatarURL.getUrl()));
    }

    private ImageView returnImages(int i) {
        Image avatar = AvatarView.getAvatarImage(AvatarURLEnum.valueOf("IMG" + i));
        ImageView newImageView = new ImageView(avatar);
        newImageView.setFitHeight(92);
        newImageView.setFitWidth(87);
        newImageView.setOnMouseClicked(event -> {
            imageView.setImage(newImageView.getImage());
            MenuStack.getInstance().getUser().setImageUrl("IMG" + i);
        });
        return newImageView;
    }

    public void exitClicked() {
        System.exit(0);
    }

    public void backClicked() {
        MenuStack.getInstance().popMenu();
        MenuStack.getInstance().popMenu();
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("ProfilePage"));
    }

    public void openFileClick() throws IOException {
        fileChooser.setTitle("Select Avatar");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.JPG"));
        File file = fileChooser.showOpenDialog(null);
        if (file == null)
            return;
        MenuStack.getInstance().getUser().setImageUrl(file.toURI().toString());
        imageView.setImage(new Image((file.toURI().toString())));
    }
}
