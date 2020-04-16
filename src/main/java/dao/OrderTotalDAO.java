package dao;

import dataAccessLayer.DatabaseConnection;
import model.OrderTotal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * Data Access Object Class for the OrderTotal table. It contains the SQL statements.
 */
public class OrderTotalDAO extends AbstractDAO<OrderTotal> {

    /**
     * Finds the orders that a Client ID
     * @param id  Client's ID
     * @return list of orders or null
     */
    public List<OrderTotal> findByClientId(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("idclient");
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, this.getClass().getName() + "OrderTotalDAO:findByClientId " + e.getMessage());
        } catch (IndexOutOfBoundsException ignored){

        } finally {
            DatabaseConnection.close(resultSet);
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
        return null;
    }
}
