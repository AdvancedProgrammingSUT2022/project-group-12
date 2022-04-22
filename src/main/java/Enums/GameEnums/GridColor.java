package Enums.GameEnums;

public enum GridColor {
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    BLUE("\033[0;34m"),
    WHITE("\033[0;37m"),
    YELLOW("\033[0;33m"),
    CYAN("\033[1,36m"),
    BLACK("\033[0;30m");

    private final String color;

    GridColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
}
