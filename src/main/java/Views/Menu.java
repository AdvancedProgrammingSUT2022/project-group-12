package Views;

import java.util.Scanner;

public abstract class Menu {

    public void run() {
        Scanner scanner = MenuStack.getInstance().getScanner();
        while (true) {
            String line = scanner.nextLine().trim();
            if (handleCommand(line)) {
                break;
            }
        }
    }

    protected abstract boolean handleCommand(String input);
}
