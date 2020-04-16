package businessLayer;

import presentation.Command;
import presentation.FileParser;
import presentation.Report;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This Class executes the commands processed by the File Parser
 */
public class Logic {
    private ArrayList<Command> commands;
    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private OrderTotalBLL orderTotalBLL;


    /**
     * Uses the File Parser to get the commands and executes them
     * @param inputFile Input file from where the commands will be read
     */
    public Logic(String inputFile){
        this.commands = new FileParser(inputFile).getCommands();
        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
        orderTotalBLL = new OrderTotalBLL();

        executeCommands();
    }

    /**
     * For each commands it calls the corresponding method
     */
    private void executeCommands(){
        for (Command c : commands) {
            if (c.getCommand().equals("Insert")){
                int ok;
                Command c1 = commands.get(commands.indexOf(c) + 1);
                if (c1 != null && c1.getCommand().equals("Insert") && c1.getArguments()[0].equals(c.getArguments()[0])){
                    ok = 0;
                }
                else{
                    ok = 1;
                }
                execInsert(c, ok);
            }
            else if (c.getCommand().equals("Delete")){
                execDelete(c);
            }
            else if (c.getCommand().equals("Report")){
                new Report(c.getTable());
            }
            else{
                throw new NoSuchElementException("Wrong command : " + c.getCommand());
            }
        }
    }

    /**
     * Executes the insert command by calling the insert methods in the appropriate table Business Logic Class
     * @param c The insert command to be executed
     * @param ok This is used for the Order table to know when to create the bill. 1 to create it, 0 to not.
     */
    private void execInsert(Command c, int ok){
        String table = c.getTable();
        if (table.equalsIgnoreCase("client")){
            clientBLL.insert(c.getArguments());
        }
        else if (table.equalsIgnoreCase("product")){
            productBLL.insert(c.getArguments());
        }
        else {
            orderTotalBLL.insert(c.getArguments(), ok);
        }
    }

    /**
     * Calls the delete method in the appropriate table Business Logic Class
     * @param c The delete command to be executed
     */
    private void execDelete(Command c) {
        String table = c.getTable();
        if (table.equalsIgnoreCase("client")){
            clientBLL.delete(c.getArguments());
        }
        else if (table.equalsIgnoreCase("product")){
            productBLL.delete(c.getArguments());
        }
    }
}
