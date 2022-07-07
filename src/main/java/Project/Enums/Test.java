package Project.Enums;

import Project.App;
import Project.Models.Civilization;
import Project.Models.Location;
import Project.Models.Terrains.Terrain;
import Project.Models.Tiles.Hex;
import Project.Models.Tiles.Tile;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.RangedUnit;
import Project.Models.Units.Unit;
import Project.Utils.Constants;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Test extends Application {
    CombatUnit unit;
    Hex hex;
    Group mainGroup = new Group();
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("by");

     //  mainGroup.getChildren().add(0,this.hex);

        System.out.println(mainGroup.getBoundsInParent().getWidth() + " " + mainGroup.getBoundsInParent().getHeight());
        Scene scene = new Scene(mainGroup);
        ResourceEnum resourceEnum = ResourceEnum.BANANA;
        System.out.println(scene.getWidth() + " " + scene.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() {
        System.out.println("hello");
        BuildingEnum buildingEnum = BuildingEnum.ARMORY;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(i != 0 && j != 0)
                mainGroup.getChildren().add(new Hex(new Tile(5,new Terrain(TerrainEnum.DESERT),new Location(i,j),"BLUE"),5,i,j,"KHAKI"));
            }
        }
        createUnitGroup();
    }

    public void createUnitGroup() {
        this.unit = new RangedUnit(UnitEnum.ARCHER, null, new Location(0, 0));
        this.hex = (Hex) mainGroup.getChildren().get(0);
        // this.hex.setEffect(new Bloom());
        Group group = new Group();
        int down = 12;
        int right = 5;
        unit.setHealth(70);
        int numberOfRowsOfUnits;
        if(unit.getHealth() < Constants.UNIT_FULL_HEALTH / 3){
            numberOfRowsOfUnits = 1;
        } else if(unit.getHealth() < (Constants.UNIT_FULL_HEALTH  * 2) / 3){
            numberOfRowsOfUnits = 2;
        }  else {
            numberOfRowsOfUnits = 3;
        }
        for (int i = numberOfRowsOfUnits - 1; i >= 0; i--) {
            for (int j = 0; j < 3; j++) {
                ImageView imageView = new ImageView(unit.getType().getAssetImage());
                imageView.setLayoutX(hex.getCenterX() + right - right * j - imageView.getImage().getWidth() / 2);
                imageView.setLayoutY(hex.getCenterY() + down - down * i );
                group.getChildren().add(imageView);
            }
        }
        group.setOnMouseEntered((MouseEvent) -> {
            this.hex.setEffect(new DropShadow());
            System.out.println(this.hex.getEffect());
            DropShadow boxBlur = (DropShadow) this.hex.getEffect();
            System.out.println(boxBlur);
            boxBlur.setInput(new GaussianBlur());
        });
        group.setOnMouseExited((MouseEvent) -> {
            DropShadow effect = (DropShadow) this.hex.getEffect();
            System.out.println(this.hex.getEffect());
            effect.setInput(null);
        });
        this.mainGroup.getChildren().add(group);
    }
}
