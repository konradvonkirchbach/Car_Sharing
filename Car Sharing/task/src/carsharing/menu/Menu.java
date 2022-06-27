package carsharing.menu;

import carsharing.menu.implementation.MenuEntryPoint;

import java.util.Map;
import java.util.Scanner;

public final class Menu
{
    private MenuEntryPoint entryPoint;

    public Menu(Map<String, String> args) {
        entryPoint = new MenuEntryPoint(args);
    }

    public void run() {
        AbstractMenuNode currentNode = entryPoint;
        while (true) {
            currentNode.update();
            if (currentNode.isLeaf()) {
                currentNode = currentNode.performAction();
            } else {
                currentNode.listActions();
                Integer index = readInput();
                currentNode = currentNode.chooseAction(index);
            }
        }
    }

    private Integer readInput() {
        Scanner scanner = new Scanner(System.in);
        return Integer.parseInt(scanner.nextLine());
    }
}
