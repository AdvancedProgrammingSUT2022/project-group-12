package Project.Models.Tiles;

import javafx.scene.shape.Polygon;

public class Hex extends Polygon {
    private final double multiply;
    private double w = 10 * Math.sqrt(3);
    private double h = 15;
    private double spacing = 5 * Math.sqrt(3);
    private final int i;
    private final int j;

    public Hex(double multiply, int i, int j) {
        this.i = j;
        this.j = i;
        this.multiply = multiply;
        spacing *= multiply;
        w *= multiply;
        h *= multiply;
        if (j % 2 != 0)
            spacing = 0;
        this.getPoints().addAll(5 * Math.sqrt(3) * multiply + w * i + spacing, 0.0 * multiply + h * j,
                10 * Math.sqrt(3) * multiply + w * i + spacing, 5.0 * multiply + h * j,
                10 * Math.sqrt(3) * multiply + w * i + spacing, 15.0 * multiply + h * j,
                5 * Math.sqrt(3) * multiply + w * i + spacing, 20.0 * multiply + h * j,
                0.0 * multiply + w * i + spacing, 15.0 * multiply + h * j,
                0.0 * multiply + w * i + spacing, 5.0 * multiply + h * j);
    }

    public double getMultiply() {
        return multiply;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public double getSpacing() {
        return spacing;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
