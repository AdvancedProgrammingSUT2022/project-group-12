package Views;

import Controllers.Command;


public class MainMenu extends Menu {
    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "show current menu" -> System.out.println("Main Menu");
            case "menu exit" -> MenuStack.getInstance().popMenu();
        }
    }
}
