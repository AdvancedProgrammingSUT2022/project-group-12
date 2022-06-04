package Project.Views;

import Project.Enums.AvatarURLEnum;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class AvatarView implements ViewController {
    private final FileChooser fileChooser = new FileChooser();
    @FXML
    private
    ImageView imageView;
    @FXML
    private Text userID;
    @FXML
    private ImageView avatar01;
    @FXML
    private ImageView avatar02;
    @FXML
    private ImageView avatar03;
    @FXML
    private ImageView avatar04;
    @FXML
    private ImageView avatar05;
    @FXML
    private ImageView avatar06;
    @FXML
    private ImageView avatar07;
    @FXML
    private ImageView avatar08;

    public void initialize() {
        userID.setText("-" + MenuStack.getInstance().getUser().getUsername());
        avatar01.setImage(new Image(AvatarURLEnum.IMG01.getUrl()));
        avatar02.setImage(new Image(AvatarURLEnum.IMG08.getUrl()));
        avatar03.setImage(new Image(AvatarURLEnum.IMG07.getUrl()));
        avatar04.setImage(new Image(AvatarURLEnum.IMG06.getUrl()));
        avatar05.setImage(new Image(AvatarURLEnum.IMG05.getUrl()));
        avatar06.setImage(new Image(AvatarURLEnum.IMG04.getUrl()));
        avatar07.setImage(new Image(AvatarURLEnum.IMG02.getUrl()));
        avatar08.setImage(new Image(AvatarURLEnum.IMG03.getUrl()));
        imageView.setImage(MenuStack.getInstance().getUser().getImage());
    }

    public void exitClicked() {
        System.exit(0);
    }

    public void backClicked() {
        MenuStack.getInstance().popMenu();
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

    public void avatar01Click() {
        imageView.setImage(new Image(avatar01.getImage().getUrl()));
        MenuStack.getInstance().getUser().setImageUrl("IMG01");
    }

    public void avatar02Click() {
        imageView.setImage(new Image(avatar02.getImage().getUrl()));
        MenuStack.getInstance().getUser().setImageUrl("IMG08");
    }

    public void avatar03Click() {
        imageView.setImage(new Image(avatar03.getImage().getUrl()));
        MenuStack.getInstance().getUser().setImageUrl("IMG07");
    }

    public void avatar04Click() {
        imageView.setImage(new Image(avatar04.getImage().getUrl()));
        MenuStack.getInstance().getUser().setImageUrl("IMG06");
    }

    public void avatar05Click() {
        imageView.setImage(new Image(avatar05.getImage().getUrl()));
        MenuStack.getInstance().getUser().setImageUrl("IMG05");
    }

    public void avatar06Click() {
        imageView.setImage(new Image(avatar06.getImage().getUrl()));
        MenuStack.getInstance().getUser().setImageUrl("IMG04");
    }

    public void avatar07Click() {
        imageView.setImage(new Image(avatar07.getImage().getUrl()));
        MenuStack.getInstance().getUser().setImageUrl("IMG02");
    }

    public void avatar08Click() {
        imageView.setImage(new Image(avatar08.getImage().getUrl()));
        MenuStack.getInstance().getUser().setImageUrl("IMG03");
    }
}
