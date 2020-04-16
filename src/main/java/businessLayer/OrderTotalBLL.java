package businessLayer;

import dao.ClientDAO;
import dao.OrderItemDAO;
import dao.OrderTotalDAO;
import dao.ProductDAO;
import model.Client;
import model.OrderItem;
import model.OrderTotal;
import model.Product;
import presentation.Bill;
import presentation.Understock;

import java.util.Date;
import java.util.List;

/**
 * OrderTotal Business Logic Class. It implements the logic of the insert command in the OrderTotal table.
 * It uses the SQL statements in the DAO class for the tables OrderTotal, Client, OrderItem and Product
 */
public class OrderTotalBLL {
    /**
     * OrderTotal Data Access Object where the SQL statements are.
     */
    private OrderTotalDAO orderTotalDAO;
    /**
     * Client Data Access Object where the SQL statements are.
     */
    private ClientDAO clientDAO;
    /**
     * Product Data Access Object where the SQL statements are.
     */
    private ProductDAO productDAO;
    /**
     * OrderItem Data Access Object where the SQL statements are.
     */
    private OrderItemBLL orderItemBLL;

    /**
     * The id for the inserted order is incremented when a new order is inserted
     */
    private int id = 1;

    public OrderTotalBLL(){
        orderItemBLL = new OrderItemBLL();
        orderTotalDAO = new OrderTotalDAO();
        clientDAO = new ClientDAO();
        productDAO = new ProductDAO();
    }

    /**
     * Inserts the order in the OrderTotal table and each product ordered that corresponds to the same order
     * In the OrderItem table which has a idOrder field that says to which order it belongs
     * If the stock for an ordered product is not enough a under-stock PDF will be generated and the order
     * cancelled
     * @param arguments Order's Data
     * @param ok 1 to print the Bill, 0 to not.
     */
    public void insert(String[] arguments, int ok) {
        Client c = clientDAO.findByName(arguments[0]);
        Product p = productDAO.findByName(arguments[1]);
        if (c == null || p == null)
            return;
        OrderTotal ot = new OrderTotal(id++, c.getID(), Float.parseFloat(arguments[2]));
        List<OrderTotal> orderTotals = orderTotalDAO.findByClientId(c.getID());
        float quantity = ot.getTotalPrice() / p.getPrice();
        if (p.getStock() - quantity < 0) {
            new Understock(ot, p, new Date());
            return;
        }
        p.setStock(p.getStock() - quantity);
        boolean b = false;
        if (orderTotals != null){
            for (OrderTotal ot1 : orderTotals) {
                if (ot1.getID() == ot.getID() - 1){
                    ot.setID(ot.getID() - 1);
                    ot.setTotalPrice(ot.getTotalPrice() + ot1.getTotalPrice());
                    id--;
                    orderTotalDAO.update(ot);
                    b = true;
                    break;
                }
            }
        }
        if (orderTotals == null || !b)
            orderTotalDAO.insert(ot);
        orderItemBLL.insert(new OrderItem(ot.getID(), p.getID(), quantity),p);
        if (ok == 1)
            new Bill(c,ot, new Date());
    }
}
