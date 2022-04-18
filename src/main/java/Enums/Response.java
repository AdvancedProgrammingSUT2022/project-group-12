package Enums;

public enum Response {
    OK("OK");

    private final String message;

    Response(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }

    public boolean isOK() {
        return this == Response.OK;
    }
}
