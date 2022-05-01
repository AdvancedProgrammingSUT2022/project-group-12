package Models;

public class Location {
    private int row;
    private int col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Location(Location that) {
        this.row = that.row;
        this.col = that.col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void addRow(int row) {
        this.row += row;
    }

    public void addCol(int col) {
        this.col += col;
    }
}
