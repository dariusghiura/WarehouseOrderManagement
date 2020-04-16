package businessLayer;

import dao.OrderItemDAO;
import dao.ProductDAO;
import model.OrderItem;
import model.Product;

/**
 * OrderItem Business Logic Class. It implements the logic of the insert command in the OrderItem table.
 * It uses the SQL statements in the DAO class for the tables OrderItem and Product
 */
public class OrderItemBLL {
    /**
     * OrderItem Data Access Object where the SQL statements are.
     */
    private OrderItemDAO orderItemDAO;
    /**
     * Product Data Access Object where the SQL statements are.
     */
    private ProductDAO productDAO;

    public OrderItemBLL(){
        orderItemDAO = new OrderItemDAO();
        productDAO = new ProductDAO();
    }

    /**
     * Inserts a new order item in the OrderItem table
     * and updates the stock of the product that has been ordered
     * @param i The order item to be inserted
     * @param p Product corresponding to the order item
     */
    public void insert(OrderItem i, Product p){
        OrderItem i1 = orderItemDAO.findByOrderIdAndProductId(i);
        if (i1 != null){
            i.setQuantity(i.getQuantity() + i1.getQuantity());
            orderItemDAO.updateByOrderIDAndProductID(i);
        }
        orderItemDAO.insert(i);
        productDAO.update(p);
    }
}
