package Project.Models.Tiles;


import Project.Models.Location;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Hex extends Polygon {

    private final double multiply;
    private final int i;
    private final int j;
    private final double w;
    private final double h;
    private final double verticalSpacing;
    private final double horizontalSpacing;
    private final double beginningOfLine;
    private final String url;

    public Hex(double multiply, int i, int j, String url) {
//        Image image = new Image("-url-");
        this.i = i;
        this.j = j;
        this.url = url;
        this.multiply = multiply;
        this.verticalSpacing = j * 10 * multiply;
        this.horizontalSpacing = 5 * Math.sqrt(3) * i * multiply;
        beginningOfLine = i % 2 == 0 ? 0 : 15 * multiply;
        w = 20 * multiply;
        h = 10 * Math.sqrt(3) * multiply;
        this.getPoints().addAll(
                initX(5), initY(0.0),
                initX(15.0), initY(0.0),
                initX(20.0), initY(5 * Math.sqrt(3)),
                initX(15.0), initY(10 * Math.sqrt(3)),
                initX(5.0), initY(10 * Math.sqrt(3)),
                initX(0.0), initY(5 * Math.sqrt(3)));
        // TODO: change after adding tile images
        // this.setFill(new ImagePattern(image));
        this.setFill(Color.valueOf(url));
        this.setOnMouseEntered(mouseEvent -> {
            setCursor(Cursor.HAND);
            System.out.println("this.getRow() = " + this.getRow());
            System.out.println("this.getColumn() = " + this.getColumn());
            this.setEffect(new DropShadow());
        });
        this.setOnMouseExited(mouseEvent -> {
            setCursor(Cursor.DEFAULT);
            this.setEffect(null);
        });
        this.setOnMouseClicked(mouseEvent -> System.out.println(j + " " + i));
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
    public double getCenterX(){
        return (50 + this.initX(0));
    }
    public double getCenterY(){
        System.out.println("this.getLayoutY() = " + this.getLayoutY());
        return (12.5 * Math.sqrt(3) +  this.initY(0));
    }
}
