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
import a00869363.io.Leaderboard;

public class LeaderboardDAO extends Dao{
	private static final Logger LOG = LogManager.getLogger(PlayerDAO.class);
	public static final String TABLE_NAME = "leaderboard";
	
	public LeaderboardDAO(Database database) {
		super(database, TABLE_NAME);
	}

//	@Override
//	public void create() throws SQLException {
//		String createStatement = "create table leaderboard( wins VARCHAR(10), losses VARCHAR(10), game_name VARCHAR(64), gamertag VARCHAR(64), platform VARCHAR(64) )";
//		super.create(createStatement);
//	}
	public void create() throws SQLException {
		String createStatement = "create table leaderboard( wins int(10), losses int(10), game_name VARCHAR(64), gamertag VARCHAR(64), platform VARCHAR(64) )";
		super.create(createStatement);
	}
	/**
	 * Inserts singular persona into database
	 * @param player
	 * @throws SQLException
	 */
	public void addLeaderboardItem(Leaderboard item) throws SQLException {
		 
		String addStatement = String.format(
		        "insert into %s (wins, losses, game_name, gamertag, platform) values('%s', '%s', '%s', '%s', '%s')", TABLE_NAME, 
		       item.getWins(),
		       item.getLosses(),
		       item.getGameName(),
		       item.getGamerTag(),
		       item.getPlatform()
		        );
		super.add(addStatement);
	}
	
	/**
	 * Retrieves a list of all leaderboard rows in the database
	 * @return List<Leaderboard> rows
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Leaderboard> getLeaderboardRows() throws SQLException, Exception {
		Connection connection;
		Statement statement = null;
		List<Leaderboard> leaderboardRows = new ArrayList<Leaderboard>();
		try {
			connection = database.connect();
			statement = connection.createStatement();
			
			String sqlString = String.format("SELECT * FROM " + TABLE_NAME);
			ResultSet resultSet = statement.executeQuery(sqlString);
			
			while (resultSet.next()) {
				leaderboardRows.add(new Leaderboard(
						resultSet.getInt("wins"),
						resultSet.getInt("losses"),
						resultSet.getString("game_name"),
						resultSet.getString("gamertag"),
						resultSet.getString("platform")
						));
			}
		} finally {
			close(statement);
		}
		return leaderboardRows;
	}

}
