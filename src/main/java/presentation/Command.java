package presentation;
/**
 * This Class represents the format of a line read by the File Parser
 */
public class Command {
    private String command;
    private String table;
    private String[] arguments;

    /** Splits the line read by the File Parser in to the format of a command : command, table, arguments
     * @param command Line read by the File Parser
     */
    public Command(String command) {
        String[] s = command.split(": ");
        if (s.length > 1){
            if (s[0].equalsIgnoreCase("Order")){
                this.command = "Insert";
                this.table = "Order";
            }
            else {
                this.command = s[0].split(" ")[0];
                this.table = s[0].split(" ")[1];
            }
            this.arguments = s[1].split(", ");
        }
        else{
            this.command = "Report";
            this.table = s[0].split(" ")[1];
            this.arguments = null;
        }
    }

    public String getCommand() {
        return command;
    }

    public String getTable() {
        return table;
    }

    public String[] getArguments() {
        return arguments;
    }
}
