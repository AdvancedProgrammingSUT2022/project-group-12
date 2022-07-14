package Project.Client.Views;

import Project.Enums.TechnologyEnum;
import Project.Models.Civilization;
import Project.Server.Controllers.GameController;
import Project.Server.Views.RequestHandler;
import Project.Utils.CommandResponse;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class TechPanel implements ViewController {
    @FXML
    private VBox techsBox;
    @FXML
    private VBox currentTechBox;
    @FXML
    private ScrollPane techScrollPane;
    @FXML
    private Button techTree;
    @FXML
    private VBox leadsToBox;
    @FXML
    private VBox technologyShow;



    public void initialize(){

        Civilization currentCiv = GameController.getGame().getCurrentCivilization();
        techScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        techScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        techScrollPane.setStyle("-fx-background-color:transparent;");
        techScrollPane.setContent(techsBox);
        technologyShow.setSpacing(20);
        techsBox.setSpacing(20);
        currentTechBox.setSpacing(20);
        currentTechBox.setAlignment(Pos.CENTER);
        TechnologyEnum currentTechnology = currentCiv.getResearchingTechnology();
        if((currentTechnology != null))
            currentTechBox.getChildren().add(addTechnology(currentCiv.getResearchingTechnology(),currentCiv.getResearchingTechnologies().get(currentTechnology)));
        for (TechnologyEnum tech:
                currentCiv.getResearchingTechnologies().keySet()) {
            techsBox.getChildren().add(addTechnology(tech,currentCiv.getResearchingTechnologies().get(tech)));
        }
        leadsToBox.setVisible(false);
    }

    public HBox addTechnology(TechnologyEnum technologyEnum, int progress){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        VBox vBox = getTechBox(technologyEnum,false);
        setOnMouseEffectTechs(technologyEnum, vBox);
        hBox.getChildren().add(vBox);
        ProgressBar progressBar = getProgressBar(technologyEnum,progress);
        hBox.getChildren().add(progressBar);
        hBox.setSpacing(50);
        return hBox;
    }


    private VBox getTechBox(TechnologyEnum technologyEnum,Boolean isCurrentTech) {
        VBox vBox = new VBox();
        if(!isCurrentTech) {
            vBox.setTranslateX(50);
        }
        vBox.setAlignment(Pos.CENTER);
        ImageView imageView = getTechImageView(technologyEnum);
        Text text1 = new Text(capitalizeFirstString(technologyEnum.name().toLowerCase()));
        text1.setTextAlignment(TextAlignment.CENTER);
        TextFlow textFlow = new TextFlow(text1);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        textFlow.setPrefWidth(150);
        vBox.getChildren().add(textFlow);
        vBox.getChildren().add(imageView);
        return vBox;
    }

    private void setOnMouseEffectTechs(TechnologyEnum technologyEnum, VBox vBox) {
        vBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                vBox.getChildren().get(1).setEffect(new DropShadow());
//                System.out.println(vBox.getHeight());
                vBox.setScaleX(1.2);
                vBox.setScaleY(1.2);
                for (TechnologyEnum tech:
                     technologyEnum.leadsToTech()) {
                    Label label = new Label(capitalizeFirstString(tech.name()));
                    label.setStyle("-fx-background-color: red");
                    leadsToBox.getChildren().add(label);
                }
                leadsToBox.setVisible(true);
            }
        });
        vBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                vBox.getChildren().get(1).setEffect(null);
                vBox.setScaleX(1);
                vBox.setScaleY(1);
                for (int i = leadsToBox.getChildren().size() - 1; i >= 1 ; i--) {
                    leadsToBox.getChildren().remove(i);
                }
                leadsToBox.setVisible(false);
            }
        });
        vBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                Civilization currentCivilization = GameController.getGame().getCurrentCivilization();
                if(currentCivilization.getResearchingTechnology() == null){
                    currentCivilization.setResearchingTechnology(technologyEnum);
                    String command = "research -t " + technologyEnum.name();
                    CommandResponse response = RequestHandler.getInstance().handle(command);
                    currentTechBox.setAlignment(Pos.CENTER);
                    currentTechBox.getChildren().add(getTechBox(technologyEnum,true));
                }else {
                    currentTechBox.getChildren().remove(1);
                    currentTechBox.setAlignment(Pos.CENTER);
                    currentCivilization.setResearchingTechnology(technologyEnum);
                    String command = "research -t " + technologyEnum.name();
                    CommandResponse response = RequestHandler.getInstance().handle(command);
                    currentTechBox.getChildren().add(getTechBox(technologyEnum,true));
                }
            }
        });
    }


    private ImageView getTechImageView(TechnologyEnum technologyEnum) {
        ImageView imageView = new ImageView(technologyEnum.getImage());
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        return imageView;
    }


    private ProgressBar getProgressBar(TechnologyEnum technologyEnum,int progress) {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setTranslateX(100);
        progressBar.setProgress((progress) / technologyEnum.getCost());
        return progressBar;
    }

    public String capitalizeFirstString(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void backToMenu(ActionEvent actionEvent) {
        MenuStack.getInstance().popMenu();
        System.out.println("back");
    }

    public void gotoTechTree(ActionEvent actionEvent) {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("techTree"));
    }
}
