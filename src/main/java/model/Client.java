package model;
/**
 * This class represents the Client table in the database
 */
public class Client {
    private int ID;
    private String name;
    private String address;
    /**
     * Indicates if the row in the database is deleted. Initialized with 0 which corresponds to false
     */
    private int deleted = 0;

    /**
     * @param ID Client's ID
     * @param name Client's Name
     * @param address Client's address
     */
    public Client(int ID, String name, String address) {
        this.ID = ID;
        this.name = name;
        this.address = address;
    }

    public Client(){

    }

    /**
     * @return Client's ID
     */
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return Client's name with " '' " to be used in SQL Statements
     */
    public String getName() {
        return "'" + name + "'";
    }

    /**
     * @param name Client's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Client's address with " ' ' " to be used in SQL Statements
     */
    public String getAddress() {
        return "'" + address + "'";
    }

    /**
     * @param address Client's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @param deleted 1 for true, 0 for false
     */
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    /**
     * @return 1 for true, 0 for false
     */
    public int getDeleted() {
        return deleted;
    }


    public String toString(){
        return "Client : " + ID + ", " + name + ", " + address;
    }
}
