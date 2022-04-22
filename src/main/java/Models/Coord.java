package Models;

public class Coord {
    public int row, col;

    public Coord(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Coord(Coord that) {
        this.row = that.row;
        this.col = that.col;
    }
}
