package dao;

import dataAccessLayer.DatabaseConnection;
import model.OrderItem;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * Data Access Object Class for the OrderItem table. It contains the SQL statements.
 */
public class OrderItemDAO extends AbstractDAO<OrderItem> {

    /**
     * Finds the OrderItem rows by the Product's ID.
     * @param id Product's ID.
     * @return A list of the found order items or null.
     */
    public List<OrderItem> findByProductId(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("idproduct");
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, this.getClass().getName() + "OrderItemDAO:findByProductId " + e.getMessage());
        } catch (IndexOutOfBoundsException ignored){

        } finally {
            DatabaseConnection.close(resultSet);
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
        return null;
    }

    /**
     * Finds the OrderItem rows by the Order's ID.
     * @param id Order's ID.
     * @return A list of the found order items or null.
     */
    public List<OrderItem> findByOrderId(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("idorder");
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, this.getClass().getName() + "OrderItemDAO:findByOrderId " + e.getMessage());
        } catch (IndexOutOfBoundsException ignored){

        } finally {
            DatabaseConnection.close(resultSet);
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
        return null;
    }

    /**
     * Updates the order item that has a Product ID
     * @param t It contains the Product ID that we are searching and the data to update that row to.
     */
    public void updateByProductID(OrderItem t) {
        Connection connection = DatabaseConnection.getConnection();
        String update = createUpdateQuery(t, "idproduct");
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(update);
            statement.setInt(1, t.getIDProduct());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, this.getClass().getName() + "DAO:Update " + e.getMessage());
        } finally {
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
    }

    /**
     * @return The select query with idorder and idproduct in the WHERE clause
     */
    private String createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(" orderitem");
        sb.append(" WHERE idorder = ?  AND idproduct = ? AND deleted = false");
        return sb.toString();
    }


    /**
     * Finds the OrderItem rows by the Order's ID and Product's ID.
     * @param i OrderItem that contains the Order's ID and Product's ID
     * @return The found order item or null.
     */
    public OrderItem findByOrderIdAndProductId(OrderItem i) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, i.getIDOrder());
            statement.setInt(2, i.getIDProduct());
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, this.getClass().getName() + "DAO:findByOrderIdAndProductId " + e.getMessage());
        } catch(IndexOutOfBoundsException ignored){

        } finally {
            DatabaseConnection.close(resultSet);
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
        return null;
    }

    /**
     * @return The Update statement with idorder and idproduct in the WHERE clause
     */
    private String createUpdateQuery(OrderItem t) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE orderitem SET ");
        for (Field field : this.getClass().getDeclaredFields()) {
            sb.append(field.getName() + "= ");
            getFieldValue(t, sb, field);

        }
        sb.append(" WHERE idclient  = ?  AND idproduct = ? ");
        return sb.toString();
    }


    /**
     * Updates the order item that has a Order ID and Product ID
     * @param i It contains the Order ID and Product ID that we are searching and the data to update that row to.
     */
    public void updateByOrderIDAndProductID(OrderItem i) {
        Connection connection = DatabaseConnection.getConnection();
        String update = createUpdateQuery(i);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(update);
            statement.setInt(1, i.getIDOrder());
            statement.setInt(2, i.getIDProduct());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, this.getClass().getName() + "DAO:UpdateByOrderIDAndProductID " + e.getMessage());
        } finally {
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
    }
}
