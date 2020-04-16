package model;
/**
 * This class represents the OrderTotal table in the database
 */
public class OrderTotal {
    private int ID;
    private int IDClient;
    private float totalPrice;
    /**
     * Indicates if the row in the database is deleted. Initialized with 0 which corresponds to false
     */
    private int deleted = 0;

    /**
     * @param ID Order's ID
     * @param IDClient Client's ID
     * @param totalPrice Order's Total Price
     */
    public OrderTotal(int ID, int IDClient, float totalPrice) {
        this.ID = ID;
        this.IDClient = IDClient;
        this.totalPrice = totalPrice;
    }

    public OrderTotal(){

    }

    /**
     * @return Order's ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID Order's ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return Client's ID
     */
    public int getIDClient() {
        return IDClient;
    }

    /**
     * @return Total price of the order
     */
    public float getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice Total price of the order
     */
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @param deleted 1 for true, 0 for false
     */
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public void setIDClient(int IDClient) {
        this.IDClient = IDClient;
    }

    public int getDeleted() {
        return deleted;
    }
}
