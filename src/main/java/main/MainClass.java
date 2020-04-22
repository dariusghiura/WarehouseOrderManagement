package main;

import businessLayer.Logic;

/**
 * MainClass of the application
 */
public class MainClass {
    /**
     * Main method of the application
     */
    public static void main(String[] args) {
        Logic logic = new Logic("commands.txt");
    }
}
