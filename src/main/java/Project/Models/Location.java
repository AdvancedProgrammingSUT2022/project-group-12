package Project.Models;

import Project.Models.Tiles.Tile;

import java.util.Objects;

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

    public Location(String row, String col) {
        this.row = Integer.parseInt(row);
        this.col = Integer.parseInt(col);
    }

    public void addRow(int row) {
        this.row += row;
    }

    public void addCol(int col) {
        this.col += col;
    }

    @Override
    public String toString() {
        return "(" + this.getRow() + ", " + this.getCol() + ")";
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return row == location.row && col == location.col;
    }
}
