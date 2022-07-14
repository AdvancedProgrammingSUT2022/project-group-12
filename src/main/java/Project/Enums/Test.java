package Project.Enums;

import Project.Client.App;
import Project.Models.Tiles.Hex;
import Project.Models.Tiles.Tile;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.RangedUnit;
import Project.Models.Units.Unit;
import Project.Utils.Constants;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Test extends Application {
    CombatUnit unit;
    Hex hex;
    static  Scene scene;
    Group mainGroup = new Group();
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("by");
        ImprovementEnum improvementEnum = ImprovementEnum.CAMP;
        AnchorPane root = new AnchorPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(100);
        root.setPrefHeight(397);
        root.setPrefWidth(599);
     //   Dialog<String> dialog = new myDialog();
     //   Optional<String> s = dialog.showAndWait();
     //   System.out.println(s.get());
        scrollPane.setContent(root);
        Button button = new Button();
        scene = new Scene(scrollPane);
        stage.setScene(scene);
        stage.show();
        System.out.println(scrollPane.getVmax());
        scrollPane.setVvalue(50);
    }


    public static Parent addRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Project/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void changeRoot(String fxml) throws IOException {
        scene.setRoot(addRoot(fxml));
    }

//    @Override
//    public void init() {
//        System.out.println("hello");
//        BuildingEnum buildingEnum = BuildingEnum.ARMORY;
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                if(i != 0 && j != 0)
//                mainGroup.getChildren().add(new Hex(new Tile(5,new Terrain(TerrainEnum.DESERT),new Location(i,j),"BLUE"),5,i,j,"KHAKI"));
//            }
//        }
//        createUnitGroup();
//    }
//
//    public void createUnitGroup() {
//        this.unit = new RangedUnit(UnitEnum.ARCHER, null, new Location(0, 0));
//        this.hex = (Hex) mainGroup.getChildren().get(0);
//        // this.hex.setEffect(new Bloom());
//        Group group = new Group();
//        int down = 12;
//        int right = 5;
//        unit.setHealth(70);
//        int numberOfRowsOfUnits;
//        if(unit.getHealth() < Constants.UNIT_FULL_HEALTH / 3){
//            numberOfRowsOfUnits = 1;
//        } else if(unit.getHealth() < (Constants.UNIT_FULL_HEALTH  * 2) / 3){
//            numberOfRowsOfUnits = 2;
//        }  else {
//            numberOfRowsOfUnits = 3;
//        }
//        for (int i = numberOfRowsOfUnits - 1; i >= 0; i--) {
//            for (int j = 0; j < 3; j++) {
//                ImageView imageView = new ImageView(unit.getType().getAssetImage());
//                imageView.setLayoutX(hex.getCenterX() + right - right * j - imageView.getImage().getWidth() / 2);
//                imageView.setLayoutY(hex.getCenterY() + down - down * i );
//                group.getChildren().add(imageView);
//            }
//        }
//        group.setOnMouseEntered((MouseEvent) -> {
//            this.hex.setEffect(new DropShadow());
//            System.out.println(this.hex.getEffect());
//            DropShadow boxBlur = (DropShadow) this.hex.getEffect();
//            System.out.println(boxBlur);
//            boxBlur.setInput(new GaussianBlur());
//        });
//        group.setOnMouseExited((MouseEvent) -> {
//            DropShadow effect = (DropShadow) this.hex.getEffect();
//            System.out.println(this.hex.getEffect());
//            effect.setInput(null);
//        });
//        this.mainGroup.getChildren().add(group);
//    }
}





