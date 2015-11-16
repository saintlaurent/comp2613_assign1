package a00869363.dao;

import java.sql.SQLException;

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

	@Override
	public void create() throws SQLException {
		String createStatement = "create table leaderboard( win_lose VARCHAR(10), game_name VARCHAR(64), gamertag VARCHAR(64), platform VARCHAR(64) )";
		super.create(createStatement);
	}
	
	/**
	 * Inserts singular persona into database
	 * @param player
	 * @throws SQLException
	 */
	public void addLeaderboardItem(Leaderboard item) throws SQLException {
		 
		String addStatement = String.format(
		        "insert into %s (win_lose, game_name, gamertag, platform) values('%s', '%s', '%s', '%s')", TABLE_NAME, 
		       item.getWins() + ":" + item.getLosses(),
		       item.getGameName(),
		       item.getGamerTag(),
		       item.getPlatform()
		        );System.out.println(addStatement);
		super.add(addStatement);
	}

}
