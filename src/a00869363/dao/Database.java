package a00869363.dao;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Database {
	
	private static final Logger LOG = LogManager.getLogger(Database.class);

	/**
	 * MySQL credentials
	 */
	public static final String DB_DRIVER_KEY = "com.mysql.jdbc.Driver";
	public static final String DB_URL_KEY = "jdbc:mysql://localhost:3306/test";
	public static final String DB_USER_KEY = "root";
	public static final String DB_PASSWORD_KEY = "";
	
	/**
	 * Derby credentials
	 */
//	public static final String DB_DRIVER_KEY = "org.apache.derby.jdbc.EmbeddedDriver";
//	public static final String DB_URL_KEY = "jdbc:derby:derby_jdbc_test;create=true";
//	public static final String DB_USER_KEY = "admin";
//	public static final String DB_PASSWORD_KEY = "admin";
	
	private static Connection connection;
	
	public void run(){
		connect();
	}
	
	public Connection connect(){
		
		try {
			Class.forName(DB_DRIVER_KEY);
			if (connection != null) {
				return connection;
			}
			connection = DriverManager.getConnection(DB_URL_KEY);

		} catch (ClassNotFoundException e) {
			LOG.error("Class couldn't be loaded.");
		} catch (SQLException e) {
			LOG.error("SQL syntax error.");
		}
		return connection;
	}
	
	public void shutdown() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				LOG.error("Connection could not be closed.");
			}
		}
	}
	
	public static boolean tableExists(Connection connection, String tableName) throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet resultSet = null;
		String rsTableName = null;

		try {
			resultSet = databaseMetaData.getTables(connection.getCatalog(), "%", "%", null);
			while (resultSet.next()) {
				rsTableName = resultSet.getString("TABLE_NAME");
				if (rsTableName.equalsIgnoreCase(tableName)) {
					return true;
				}
			}
		} finally {
			resultSet.close();
		}
		return false;
	}
}
