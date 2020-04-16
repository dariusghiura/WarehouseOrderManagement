package dao;

import dataAccessLayer.DatabaseConnection;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Data Access Object Class for the Product table. It contains the SQL statements.
 */
public class ProductDAO extends AbstractDAO<Product> {

}
