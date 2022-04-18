import Views.Menu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (!Menu.currentMenu.isEmpty()) {
            Menu.currentMenu.get(Menu.currentMenu.size() - 1).run(scanner);
        }
    }
}