package dao;

import dataAccessLayer.DatabaseConnection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic class that implements the basic SQL statements for a Database
 * @param <T> The table model class.
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * @param field Name of the field in the WHERE clause
     * @return Select * query from the table that it is called by.
     */
    String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        if (!field.isEmpty())
            sb.append(" WHERE " + field + " =? AND deleted = false");
        else
            sb.append(" WHERE deleted = false");
        return sb.toString();
    }

    /**
     * Select *
     * @return list of the table's model objects or null
     */
    public List<T> findAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("");
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            DatabaseConnection.close(resultSet);
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
        return null;
    }

    /**
     * Select * by a name
     * @param name selects the rows with this name
     * @return A table's model object or null
     */
    public T findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("name");
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName " + e.getMessage());
        } catch (IndexOutOfBoundsException ignored){

        } finally {
            DatabaseConnection.close(resultSet);
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
        return null;
    }

    /**
     * Select * by an ID
     * @param id selects the rows with this id
     * @return A table's model object or null
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } catch(IndexOutOfBoundsException ignored){

        } finally {
            DatabaseConnection.close(resultSet);
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
        return null;
    }

    /**
     * Creates a list of objects from the ResultSet
     * @param resultSet
     * @return List of the created objects
     */
    List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    if (field.getName().equals("deleted")){
                        if (value == null || (boolean)value == false)
                            value = 0;
                        else
                            value = 1;
                    }
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Creates insert query for the object
     * @param t object
     * @return the insert query
     */
    private String createInsertQuery(T t) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(t.getClass().getSimpleName()).append(" (");
         for (Field field : type.getDeclaredFields()) {
             sb.append(field.getName());
             if (!field.getName().equals("deleted"))
                sb.append(", ");
         }
        sb.append(") VALUES (");
        for (Field field : t.getClass().getDeclaredFields()){
            getFieldValue(t, sb, field);
        }
        sb.append("); ");
        return sb.toString();
    }


    /**
     * Inserts in the db
     * @param t object to be inserted
     * @return true or false
     */
    public boolean insert(T t) {
        Connection connection = DatabaseConnection.getConnection();
        String insert = createInsertQuery(t);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(insert);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:Insert " + e.getMessage());
        } finally {
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
        return true;
    }

    String createUpdateQuery(T t, String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE " + t.getClass().getSimpleName() + " SET ");
        for (Field field : type.getDeclaredFields()) {
            sb.append(field.getName() + "= ");
            getFieldValue(t, sb, field);

        }
        sb.append(" WHERE " + fieldName + " = ? ");
        return sb.toString();
    }

    void getFieldValue(T t, StringBuilder sb, Field field) {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), t.getClass());
            Method method = propertyDescriptor.getReadMethod();
            sb.append(method.invoke(t));
            if (!field.getName().equals("deleted"))
                sb.append(", ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Updates a row to the data in the object
     * @param t Object with the data to update to
     * @return true or false.
     */
    public boolean update(T t) {
        Connection connection = DatabaseConnection.getConnection();
        String update = createUpdateQuery(t, "id");
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(update);
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor("ID", t.getClass());
            Method method = propertyDescriptor.getReadMethod();
            statement.setInt(1, (Integer) method.invoke(t));
            statement.executeUpdate();
        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:Update " + e.getMessage());
        } finally {
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
        return true;
    }
}
