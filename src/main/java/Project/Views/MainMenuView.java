package Project.Views;

public class MainMenuView {
    public void logout() {
        MenuStack.getInstance().popMenu();
    }

    public void gotoScoreboard() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("ScoreboardPage"));
    }

    public void gotoProfileMenu() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("ProfilePage"));
    }
}
