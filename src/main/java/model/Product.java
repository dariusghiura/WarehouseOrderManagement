package model;
/**
 * This class represents the Product table in the database
 */
public class Product {
    private int ID;
    private String name;
    private float stock;
    private float price;
    /**
     * Indicates if the row in the database is deleted. Initialized with 0 which corresponds to false
     */
    private int deleted = 0;

    /**
     * @param ID Product's ID
     * @param name Product's name
     * @param stock Stock of the product
     * @param price Product's price
     */
    public Product(int ID, String name, float stock, float price) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Product(){

    }

    /**
     * @return  Product's ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID Product's ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return Product's name with " '' " to be used in SQL Statements
     */
    public String getName() {
        return "'" + name + "'";
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Product's price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @return Stock of the product
     */
    public float getStock() {
        return stock;
    }

    /**
     * @param stock Stock of the product
     */
    public void setStock(float stock) {
        this.stock = stock;
    }

    /**
     * @param deleted 1 for true, 0 for false
     */
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDeleted() {
        return deleted;
    }
}
