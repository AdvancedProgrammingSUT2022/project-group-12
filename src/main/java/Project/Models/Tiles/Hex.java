package Project.Models.Tiles;

import javafx.scene.shape.Polygon;

public class Hex extends Polygon {
    private final double multiply;
    private final int i;
    private final int j;
    private final double w;
    private final double h;
    private double spacing;

    public Hex(double multiply, int i, int j) {
        this.i = i;
        this.j = j;
        this.multiply = multiply;
        spacing = 5 * Math.sqrt(3) * multiply;
        w = 10 * Math.sqrt(3) * multiply;
        h = 15 * multiply;
        if (i % 2 != 0)
            spacing = 0;
        this.getPoints().addAll(
                initX(5 * Math.sqrt(3)), initY(0.0),
                initX(10 * Math.sqrt(3)), initY(5),
                initX(10 * Math.sqrt(3)), initY(15.0),
                initX(5 * Math.sqrt(3)), initY(20.0),
                initX(0.0), initY(15.0),
                initX(0.0), initY(5.0));
    }

    private double initX(double x) {
        return x * multiply + w * j + spacing;
    }

    private double initY(double y) {
        return y * multiply + h * i;
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

    public double getSpacing() {
        return spacing;
    }

    public int getRow() {
        return i;
    }

    public int getColumn() {
        return j;
    }
}
