package a00869363.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import a00869363.data.Player;


public class PlayerDAO extends Dao {
	private static final Logger LOG = LogManager.getLogger(PlayerDAO.class);
	public static final String TABLE_NAME = "players";
	private static PlayerDAO playerDao;
	/**
	 * Constructor
	 * @param database
	 */
	private PlayerDAO() {
		super(TABLE_NAME);
	}

	public static PlayerDAO getPlayerDAO(){
		if(playerDao == null){
			playerDao = new PlayerDAO();
		}
		return playerDao;
	}
	/**
	 * Creates players table, calls parent create() method
	 */
	public void create() throws SQLException {		
		String createStatement = "create table players(playerId VARCHAR(10) not null, first_name VARCHAR(64), last_name VARCHAR(64), email VARCHAR(64), birthdate VARCHAR(64), primary key (playerId) )";
		super.create(createStatement);
	}
	
	public void delete(Player player) throws SQLException{
		String deleteStatement = "DELETE FROM " + TABLE_NAME + " WHERE playerId = " + player.getId();
		super.delete(deleteStatement);
	}
	
	/**
	 * Retrieves a record from the table player with the given id
	 * @param id
	 * @return
	 */
	public Player readById(String id){
		Player player = null;
		Connection connection = database.connect();
		try {
			Statement statement = connection.createStatement();
			String sqlString = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, "playerId", id);
			ResultSet resultSet = statement.executeQuery(sqlString); 
			while(resultSet.next()){
				player = new Player(
					resultSet.getString("playerId"), 
					resultSet.getString("first_name"), 
					resultSet.getString("last_name"), 
					resultSet.getString("email"),
					resultSet.getString("birthdate"));
			}
		} catch (SQLException e) {
			LOG.error("SQL error from PlayerDAO from method readById.");
		}
		return player;
	}
	
	/**
	 * Updates record in the database with the given Player object
	 * @param player
	 */
	public void update(Player player){
		Connection connection;
		Statement statement = null;
		try {
			connection = database.connect();
			statement = connection.createStatement();
			
			String sqlString = String
			        .format("UPDATE %s set %s='%s', %s='%s', %s='%s', %s='%s' WHERE %s='%s'",
			                tableName, //
			                "first_name", player.getfName(),
			                "last_name", player.getlName(),
			                "email", player.getEmail(),
			                "birthdate", player.getBirthdate(),
			                "playerId", player.getId());
			System.out.println(sqlString);
			int rowcount = statement.executeUpdate(sqlString);
			System.out.println(String.format("Updated %d rows", rowcount));
		} catch (SQLException e) {
			LOG.error("SQL error from PlayerDAO in method update()");
		} finally {
			close(statement);
		}
	}
	
	/**
	 * Deletes record by given id
	 * @param id
	 */
	public void deleteById(String id){
		Connection connection = database.connect();
		try {
			Statement statement = connection.createStatement();
			String sqlString = String.format("DELETE from %s WHERE %s='%s'", tableName, "playerId", id);
			int rowsDeleted = statement.executeUpdate(sqlString); 
			LOG.info("Deleted " + rowsDeleted + " rows from " + tableName);
		} catch (SQLException e) {
			LOG.error("SQL error from PlayerDAO from method deleteById.");
		}
	}
	
	
	
	/**
	 * Popular the table players with a list of Player objects
	 * @param players
	 */
	public void insertPlayers(List<Player> players){
		try {
			for(Player player : players){
				add(player);
			}
		} catch (SQLException e) {
			LOG.error("SQL error from PlayerDAO method insertPlayers");
		}
		
	}
	/**
	 * Inserts singular player into database
	 * @param player
	 * @throws SQLException
	 */
	public void add(Player player) throws SQLException {
		Statement statement = null;
		try {
			Connection connection = database.connect();
			statement = connection.createStatement();
			String insertString = String.format(
			        "insert into %s values('%s', '%s', '%s', '%s', '%s')", tableName, 
			        player.getId(), 
			        player.getfName(), 
			        player.getlName(), 
			        player.getEmail(),
			        player.getBirthdate()
			        ); 
			System.out.println(insertString);
			statement.executeUpdate(insertString);
		} finally {
			close(statement);
		}
	}
	
	/**
	 * Retrieves a list of all gamertags in the database
	 * @return List<String> gamertags
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<String> getGamertags() throws SQLException, Exception {
		Connection connection;
		Statement statement = null;
		List<String> gamertags = new ArrayList<String>();
		try {
			connection = database.connect();
			statement = connection.createStatement();
			// Execute a statement
			String sqlString = String.format("SELECT gamertag FROM " + tableName);
			ResultSet resultSet = statement.executeQuery(sqlString);
			
			while (resultSet.next()) {
				gamertags.add(resultSet.getString("gamertag"));
			}
		} finally {
			close(statement);
		}
		return gamertags;
	}
	
	
	/**
	 * Retrieves the player from the database associated with the given gamertag
	 * @param gamertag
	 * @return
	 */
	public Player getPlayer(String gamertag){
		Player player = null;
		Connection connection;
		Statement statement = null;
		String player_id = "";
		try {
			connection = database.connect();
			statement = connection.createStatement();
			// Execute a statement
			String sqlString = "SELECT * FROM personas where gamertag = '" + gamertag + "'";
			System.out.println(sqlString);
			ResultSet result1 = statement.executeQuery(sqlString);
			while (result1.next()) {
				player_id = result1.getString("player_id");
			}
			String selectPlayer = "SELECT * FROM players where playerId = '" + player_id + "'";
			ResultSet resultSet = statement.executeQuery(selectPlayer);
			
			while (resultSet.next()) {
				player = new Player(
						resultSet.getString("playerId"), 
						resultSet.getString("first_name"), 
						resultSet.getString("last_name"), 
						resultSet.getString("email"), 
						resultSet.getString("birthdate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(statement);
		}
		return player;
	}
	
	public List<Player> selectAll(){
		List<Player> players = new ArrayList<Player>();
		ResultSet resultSet = super.selectAllFromDb();
		
		try {
			while (resultSet.next()) {
				players.add(new Player(
						String.valueOf(resultSet.getInt("playerId")),
						resultSet.getString("first_name"),
						resultSet.getString("last_name"),
						resultSet.getString("email"),
						resultSet.getString("birthdate")
						));
			}
		} catch (SQLException e) {
			LOG.error("Could not select all players from database.");
		}
		return players;
	}
}
