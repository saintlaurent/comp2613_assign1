package a00869363.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Dao {

	protected final Database database;
	protected final String tableName;

	protected Dao(String tableName) {
		this.database = Database.getDatabaseInstance();
		this.tableName = tableName;
	}

	public abstract void create() throws SQLException;

	protected void create(String createStatement) throws SQLException {
		Statement statement = null;
		try {
			Connection connection = database.connect();
			statement = connection.createStatement();
			
			//Only create the table if it doesn't exist already
			if(!Database.tableExists(connection, tableName)){
				statement.executeUpdate(createStatement);
			}			
		} finally {
			close(statement);
		}
	}

	protected void add(String updateStatement) throws SQLException {
		Statement statement = null;
		try {
			Connection connection = database.connect();
			statement = connection.createStatement();
			statement.executeUpdate(updateStatement);
		} finally {
			close(statement);
		}
	}
	
	protected void delete(String deleteStatement) throws SQLException {
		Statement statement = null;
		try {
			Connection connection = database.connect();
			statement = connection.createStatement();
			statement.executeUpdate(deleteStatement);
		} finally {
			close(statement);
		}
	}
	public void drop() throws SQLException {
		Statement statement = null;
		try {
			Connection connection = database.connect();
			statement = connection.createStatement();
			if (Database.tableExists(connection, tableName)) {
				statement.executeUpdate("drop table " + tableName);
			}
		} finally {
			close(statement);
		}
	}

	protected void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected ResultSet selectAllFromDb(){
		Connection connection;
		Statement statement = null;
		connection = database.connect();
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			String sqlString = String.format("SELECT * FROM " + tableName);
			 resultSet = statement.executeQuery(sqlString);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//close(statement);
		}
		return resultSet;		
	}

}
