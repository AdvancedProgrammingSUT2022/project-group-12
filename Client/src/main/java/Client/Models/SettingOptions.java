package Client.Models;

public class SettingOptions {
    private boolean gameOnMute = false;
    private boolean autoSaveOn = false;

    public boolean isGameOnMute() {
        return gameOnMute;
    }

    public void setGameOnMute(boolean gameOnMute) {
        this.gameOnMute = gameOnMute;
    }

    public boolean isAutoSaveOn() {
        return autoSaveOn;
    }

    public void setAutoSaveOn(boolean autoSaveOn) {
        this.autoSaveOn = autoSaveOn;
    }
}
