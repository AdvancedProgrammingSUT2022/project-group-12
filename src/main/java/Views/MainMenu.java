package Views;

import Controllers.Command;


public class MainMenu extends Menu {
    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "show current menu" -> System.out.println("Main Menu");
            case "menu exit" -> MenuStack.getInstance().popMenu();
            case "goto game menu" -> MenuStack.getInstance().pushMenu(new GameMenu());
            case "goto profile menu" -> MenuStack.getInstance().pushMenu(new ProfileMenu());
        }
    }
}
