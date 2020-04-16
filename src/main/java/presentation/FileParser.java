package presentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This Class represents the File Parser that reads lines from the input file and gets the commands
 */
public class FileParser {
    private File file;
    private static ArrayList<Command> commands;

    /**
     * Reads the file and gets the commands
     * @param fileName Input file
     */
    public FileParser(String fileName){
        file = new File(fileName);
        commands = new ArrayList<Command>();
        try{
            parseFile();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Reads the input file line by line and gets the commands
     * @throws FileNotFoundException
     */
    private void parseFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()){
            commands.add(new Command(scanner.nextLine()));
        }
        scanner.close();
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
