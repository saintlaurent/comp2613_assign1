package a00869363.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.data.Score;

public class ScoresDAO extends Dao{
	private static final Logger LOG = LogManager.getLogger(PlayerDAO.class);
	public static final String TABLE_NAME = "scores";
	private static ScoresDAO scoresDao;
	
	private ScoresDAO() {
		super(TABLE_NAME);
	}

	public static ScoresDAO getScoresDao(){
		if(scoresDao == null){
			scoresDao = new ScoresDAO();
		}
		return scoresDao;
	}
	@Override
	public void create() throws SQLException {
		String createStatement = "create table scores(persona_id varchar(10) not null, game_id VARCHAR(10), outcome VARCHAR(10) )";
		super.create(createStatement);
	}
	
	/**
	 * Inserts singular score into database
	 * @param player
	 * @throws SQLException
	 */
	public void addScore(Score score) throws SQLException {
		
		String addStatement = String.format(
		        "insert into %s values('%s', '%s', '%s')", TABLE_NAME, 
		       score.getPersonaId(),
		       score.getGameId(),
		       score.getWinLose()
		        );
		super.add(addStatement);
	}
	
	public void delete(String id) throws SQLException{
		String deleteStatement = "DELETE FROM " + TABLE_NAME + " WHERE persona_id = " + id;
		super.delete(deleteStatement);
	}
	
	public Map<String, Integer> getTotals(){
		Connection connection;
		Statement statement = null;
		Map<String, Integer> gameTotals = new HashMap<String, Integer>();
		try {
			connection = database.connect();
			statement = connection.createStatement();
			String sqlString = String.format("SELECT games.name, COUNT(scores.game_id) AS total FROM scores LEFT JOIN games ON scores.game_id = games.id GROUP BY name");
			ResultSet resultSet = statement.executeQuery(sqlString);
			
			while (resultSet.next()) {
				gameTotals.put(resultSet.getString("name"), resultSet.getInt("total"));
			}
		} catch (SQLException e) {
			LOG.error("SQL error calculating totals");
		} finally {
			close(statement);
		}
		return gameTotals;
	}
	
	public List<Score> selectAll(){
		List<Score> scores = new ArrayList<Score>();
		ResultSet resultSet = super.selectAllFromDb();
		
		try {
			while (resultSet.next()) {
				scores.add(new Score(
						String.valueOf(resultSet.getInt("persona_id")),
						resultSet.getString("game_id"),
						resultSet.getString("outcome")
						));
			}
		} catch (SQLException e) {
			LOG.error("Could not select all scores from database.");
		}
		return scores;
	}

}
