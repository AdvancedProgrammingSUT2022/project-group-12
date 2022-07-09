package Project.Models.Tiles;


import Project.App;
import Project.Controllers.GameController;
import Project.Enums.VisibilityEnum;
import Project.Models.Cities.City;
import Project.Models.Location;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.NonCombatUnit;
import Project.Models.Units.Unit;
import Project.Utils.Constants;
import Project.Utils.Observer;
import Project.Views.Menu;
import Project.Views.MenuStack;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class Hex implements Observer<Tile> {

    private static final Image cityImage = new Image(App.getResourcePath("/images/resources/City center.png"));
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
    private final Text positionText;
    private final ImageView cityImageView;
    private final ColorAdjust groupColorAdjust = new ColorAdjust();

    public Hex(Tile tile, int j, int i, String url) {
        int multiply = Constants.HEX_SIZE_MULTIPLY;
        this.tileLocation = tile.getLocation();
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
        this.group.setCursor(Cursor.HAND);
        this.group.setOnMouseEntered(mouseEvent -> {
            this.group.toFront();
            this.groupColorAdjust.setInput(new DropShadow(20, Color.BLACK));
        });
        this.group.setOnMouseExited(mouseEvent -> {
            this.groupColorAdjust.setInput(null);
        });
        this.group.setOnMouseClicked(mouseEvent -> System.out.println(i + " " + j));
        this.positionText = new Text(i + ", " + j);
        this.positionText.setLayoutX(this.getCenterX() - this.positionText.getBoundsInLocal().getWidth() / 2);
        this.positionText.setLayoutY(this.getCenterY() - this.multiply * 3);
        this.cityImageView = new ImageView(cityImage);
        this.cityImageView.setLayoutX(this.getCenterX() - cityImageView.getBoundsInLocal().getWidth() / 2);
        this.cityImageView.setLayoutY(this.getCenterY() - this.multiply * 3);
        setSelectScaleEffect(this.cityImageView);
        this.group.setEffect(this.groupColorAdjust);
    }

    private static void setSelectScaleEffect(Node node) {
        node.setCursor(Cursor.HAND);
        node.setOnMouseEntered(mouseEvent -> {
            node.setScaleX(1.1);
            node.setScaleY(1.1);
        });
        node.setOnMouseExited((MouseEvent) -> {
            node.setScaleX(1);
            node.setScaleY(1);
        });
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
        return (multiply * 10 + this.initX(0));
    }

    public double getCenterY() {
        return (25 * Math.sqrt(3) + this.initY(0));
    }

    private void addUnitToGroup(Unit unit) {
        Group graphicUnit = unit.getGraphicUnit();
        this.group.getChildren().add(graphicUnit);
        graphicUnit.setTranslateY(this.getCenterY() + multiply * 4);
        graphicUnit.setTranslateX(this.getCenterX() + multiply * 3 * (unit instanceof NonCombatUnit ? 1 : -1));
    }

    @Override
    public void getNotified(Tile tile) {
        System.out.println(tile.getLocation() + " got notified");
        updateHex(tile);
    }

    public void updateHex(Tile tile) {
        polygon.setFill(new ImagePattern(tile.getTerrain().getTerrainType().getTerrainImage()));
        this.groupColorAdjust.setBrightness(tile.getState() == VisibilityEnum.VISIBLE ? 0 : -0.5);
        NonCombatUnit nonCombatUnit = tile.getNonCombatUnit();
        CombatUnit combatUnit = tile.getCombatUnit();
        City city = tile.getCity();
        this.group.getChildren().clear();
        this.group.getChildren().add(this.polygon);
        if (nonCombatUnit != null) {
            this.addUnitToGroup(nonCombatUnit);
        }
        if (combatUnit != null) {
            this.addUnitToGroup(combatUnit);
        }
        if (tile.getCity() != null) {
            this.addCityToGroup(city);
        }
        this.group.getChildren().add(this.positionText);
    }

    private void addCityToGroup(City city) {
        this.group.getChildren().add(this.cityImageView);
        this.cityImageView.setOnMouseClicked(mouseEvent -> {
            GameController.getGame().getCurrentCivilization().setSelectedCity(city);
            MenuStack.getInstance().pushMenu(Menu.loadFromFXML("CityPanelPage"));
        });
    }
}
