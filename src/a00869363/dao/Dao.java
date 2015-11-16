package a00869363.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;




public abstract class Dao {

	protected final Database database;
	protected final String tableName;

	protected Dao(Database database, String tableName) {
		this.database = database;
		this.tableName = tableName;
	}

	public abstract void create() throws SQLException;

	protected void create(String createStatement) throws SQLException {
		Statement statement = null;
		try {
			Connection connection = database.connect();
			statement = connection.createStatement();
			if(Database.tableExists(connection, tableName)){
				//drop table
				String dropTable = "DROP TABLE " + tableName;
				statement.executeUpdate(dropTable);
			}
			statement.executeUpdate(createStatement);
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

}
