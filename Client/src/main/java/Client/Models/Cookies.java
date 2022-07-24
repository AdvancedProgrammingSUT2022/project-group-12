package Client.Models;

import Client.Utils.DatabaseQuerier;
import Client.Views.ChatView;
import Client.Views.MenuStack;
import Client.Views.ViewController;
import Project.Models.Chat;
import Project.Models.Cities.City;
import Project.Models.OpenGame;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;

public class Cookies {
    private String loginToken = null;
    private String currentGameId = null;
    private Unit selectedUnit = null;
    private Tile selectedTile = null;
    private City selectedCity = null;
    private Chat currentChat;
    private Chat publicChat;
    private OpenGame openRoom;
    private final SettingOptions settingOptions = new SettingOptions();

    public String getLoginToken() {
        return this.loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public City getSelectedCity() {
        return this.selectedCity;
    }

    public Tile getSelectedTile() {
        return this.selectedTile;
    }

    public String getCurrentGameId() {
        return currentGameId;
    }

    public void setCurrentGameId(String currentGameId) {
        this.currentGameId = currentGameId;
    }

    public SettingOptions getSettingOptions() {
        return settingOptions;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }


    public Chat getCurrentChat() {
        return currentChat;
    }


    public void setCurrentChat(String name) {
        Chat chat = DatabaseQuerier.getChatByName(name);
        this.currentChat = chat;
    }

    public void setCurrentChat(Chat currentChat) {
        this.currentChat = currentChat;
    }

    public void setPublicChat(Chat publicChat) {
        this.publicChat = publicChat;
    }

    public Chat getPublicChat() {
        return publicChat;
    }

    public OpenGame getOpenRoom() {
        return openRoom;
    }

    public void setOpenRoom(OpenGame openRoom) {
        this.openRoom = openRoom;
    }
}
