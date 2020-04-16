package businessLayer;

import dao.OrderItemDAO;
import dao.ProductDAO;
import model.OrderItem;
import model.Product;

import java.util.List;
/**
 * Product Business Logic Class. It implements the logic of the insert and delete command in the Product table.
 * It uses the SQL statements in the DAO class for the tables Product and OrderItem
 */
public class ProductBLL {
    /**
     * Product Data Access Object where the SQL statements are.
     */
    private ProductDAO productDAO;
    /**
     * The id for the inserted clients is incremented when a new product is inserted
     */
    private int id = 1;
    /**
     * OrderItem Data Access Object where the SQL statements are.
     */
    private OrderItemDAO orderItemDAO;

    public ProductBLL(){
        productDAO = new ProductDAO();
        orderItemDAO = new OrderItemDAO();
    }

    /**
     * Inserts the product if it is new or updates the stock of an existing one.
     * @param arguments Product's Data.
     */
    public void insert(String[] arguments) {
        Product p = new Product(id++, arguments[0], Integer.parseInt(arguments[1]), Float.parseFloat(arguments[2]));
        Product p1 = productDAO.findByName(arguments[0]);
        if (p1 == null)
            productDAO.insert(p);
        else{
            p.setID(p1.getID());
            p.setStock(p1.getStock() + p.getStock());
            id--;
            productDAO.update(p);
        }
    }

    /**
     * It sets the deleted flag to true for the product and the order items that correspond to the product
     * @param arguments The data of the product that needs to be deleted
     */
    public void delete(String[] arguments) {
        Product p = productDAO.findByName(arguments[0]);
        p.setDeleted(1);
        productDAO.update(p);

        List<OrderItem> items = orderItemDAO.findByProductId(p.getID());
        for (OrderItem i : items) {
            i.setDeleted(1);
            orderItemDAO.updateByProductID(i);
        }
    }
}
