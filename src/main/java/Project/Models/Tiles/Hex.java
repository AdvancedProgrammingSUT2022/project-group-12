package Project.Models.Tiles;


import Project.Enums.VisibilityEnum;
import Project.Models.Location;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.NonCombatUnit;
import Project.Models.Units.Unit;
import Project.Utils.Observer;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

public class Hex implements Observer<Tile> {

    private final Polygon polygon;
    private final double multiply;
    private final int i;
    private final int j;
    private final double w;
    private final double h;
    private final double verticalSpacing;
    private final double horizontalSpacing;
    private final double beginningOfLine;
    private final String url;
    private final Location tileLocation;
    private final Group group;

    public Hex(Tile tile, double multiply, int j, int i, String url) {
        tile.addObserver(this);
        this.tileLocation = tile.getLocation();
        Image image = new Image(url);
        this.i = i;
        this.j = j;
        this.url = url;
        this.group = new Group();
        this.multiply = multiply;
        this.verticalSpacing = j * 10 * multiply + 5;
        this.horizontalSpacing = 5 * Math.sqrt(3) * i * multiply + 5;
        beginningOfLine = i % 2 == 0 ? 0 : 15 * multiply;
        w = 20 * multiply;
        h = 10 * Math.sqrt(3) * multiply;
        this.polygon = new Polygon(
                initX(5), initY(0.0),
                initX(15.0), initY(0.0),
                initX(20.0), initY(5 * Math.sqrt(3)),
                initX(15.0), initY(10 * Math.sqrt(3)),
                initX(5.0), initY(10 * Math.sqrt(3)),
                initX(0.0), initY(5 * Math.sqrt(3)));
        polygon.setFill(new ImagePattern(image));
        this.group.setOnMouseEntered(mouseEvent -> {
            this.polygon.setCursor(Cursor.HAND);
            this.group.toFront();
            this.polygon.setEffect(new DropShadow(20, Color.BLACK));
        });
        this.group.setOnMouseExited(mouseEvent -> {
            this.polygon.setCursor(Cursor.DEFAULT);
            this.polygon.setEffect(null);
        });
        this.group.setOnMouseClicked(mouseEvent -> System.out.println(i + " " + j));
        this.updateHex(tile);
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public Group getGroup() {
        return this.group;
    }

    private double initX(double x) {
        return x * multiply + w * j + verticalSpacing + beginningOfLine;
    }

    private double initY(double y) {
        return y * multiply + h * i - horizontalSpacing;
    }

    public double getMultiply() {
        return multiply;
    }

    public double getWidth() {
        return w;
    }

    public void setFogOfWar() {

    }

    public void setVisible() {

    }

    public double getHeight() {
        return h;
    }

    public double getVerticalSpacing() {
        return verticalSpacing;
    }

    public double getHorizontalSpacing() {
        return horizontalSpacing;
    }

    public double getBeginningOfLine() {
        return beginningOfLine;
    }

    public int getRow() {
        return i;
    }

    public int getColumn() {
        return j;
    }

    public double getCenterX() {
        return (50 + this.initX(0));
    }

    public double getCenterY() {
//        System.out.println("this.getLayoutY() = " + this.getLayoutY());
        return (12.5 * Math.sqrt(3) + this.initY(0));
    }

    private void addUnitToGroup(Unit unit) {
        Group graphicUnit = unit.getGraphicUnit();
        this.group.getChildren().add(graphicUnit);
        graphicUnit.setTranslateY(10);
        graphicUnit.setTranslateX(unit instanceof NonCombatUnit ? 15 : -15);
    }

    @Override
    public void getNotified(Tile tile) {
        updateHex(tile);
    }

    private void updateHex(Tile tile) {
        if (tile.getState() == VisibilityEnum.FOG_OF_WAR)
            this.setFogOfWar();
        else if (tile.getState() == VisibilityEnum.VISIBLE)
            this.setVisible();
        NonCombatUnit nonCombatUnit = tile.getNonCombatUnit();
        CombatUnit combatUnit = tile.getCombatUnit();
        this.group.getChildren().clear();
        this.group.getChildren().add(this.polygon);
        if (nonCombatUnit != null) {
            this.addUnitToGroup(nonCombatUnit);
        }
        if (combatUnit != null) {
            this.addUnitToGroup(combatUnit);
        }
    }
}
