package model;
/**
 * This class represents the OrderItem table in the database
 */
public class OrderItem {
    private int IDOrder;
    private int IDProduct;
    /**
     * Quantity of the ordered item
     */
    private float quantity;
    /**
     * Indicates if the row in the database is deleted. Initialized with 0 which corresponds to false
     */
    private int deleted = 0;

    /**
     * @param IDOrder The order's ID
     * @param IDProduct The ordered product's ID
     * @param quantity The quantity of the ordered product
     */
    public OrderItem(int IDOrder, int IDProduct, float quantity) {
        this.IDOrder = IDOrder;
        this.IDProduct = IDProduct;
        this.quantity = quantity;
    }

    public OrderItem(){

    }

    /**
     * @return Order's ID
     */
    public int getIDOrder() {
        return IDOrder;
    }

    /**
     * @return Product's ID
     */
    public int getIDProduct() {
        return IDProduct;
    }

    /**
     * @return Product's Quantity
     */
    public float getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     */
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    /**
     * @return
     */
    public int getDeleted() {
        return deleted;
    }

    /**
     * @param deleted 1 for true, 0 for false
     */
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public void setIDOrder(int IDOrder) {
        this.IDOrder = IDOrder;
    }

    public void setIDProduct(int IDProduct) {
        this.IDProduct = IDProduct;
    }
}
