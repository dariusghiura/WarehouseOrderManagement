package businessLayer;

import dao.ClientDAO;
import dao.OrderTotalDAO;
import model.Client;
import model.OrderTotal;

import java.util.List;

/**
 * Client Business Logic Class. It implements the logic of the insert and delete command in the Client table.
 * It uses the SQL statements in the DAO class for the tables Client and OrderTotal
 */
public class ClientBLL {
    /**
     * Client Data Access Object, where the SQL statements are.
     */
    private ClientDAO clientDAO;
    /**
     * OrderTotal Data Access Object where the SQL statements are.
     */
    private OrderTotalDAO orderTotalDAO;
    /**
     * The id for the inserted clients is incremented when a new client is inserted
     */
    private int id = 1;

    public ClientBLL(){
        clientDAO = new ClientDAO();
        orderTotalDAO = new OrderTotalDAO();
    }

    /**
     * Inserts the client in the Client table
     * @param arguments Data of the client that needs to be inserted
     */
    public void insert(String[] arguments) {
        Client c = new Client(id++, arguments[0], arguments[1]);
        clientDAO.insert(c);
    }

    /**
     * It sets the deleted flag to true for the client and the client's orders
     * @param arguments The data of the client that needs to be deleted
     */
    public void delete(String[] arguments) {
        Client c = clientDAO.findByName(arguments[0]);
        c.setDeleted(1);
        clientDAO.update(c);
        List<OrderTotal> orders = orderTotalDAO.findByClientId(c.getID());
        for (OrderTotal o : orders) {
            o.setDeleted(1);
            orderTotalDAO.update(o);
        }
    }
}
