package Views;

import java.util.Scanner;

public class Menu {
    protected Scanner scanner;

    public void run(Scanner newScanner) {
        this.scanner = newScanner;
        while (true) {
            if (checkInput(this.scanner.nextLine())) {
                break;
            }
        }
    }

    protected boolean checkInput(String input) {
        return false;
    }
}
