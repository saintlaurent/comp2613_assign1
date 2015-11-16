package a00869363.dao;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.data.Game;
import a00869363.data.Score;

public class ScoresDAO extends Dao{
	private static final Logger LOG = LogManager.getLogger(PlayerDAO.class);
	public static final String TABLE_NAME = "scores";
	public ScoresDAO(Database database) {
		super(database, TABLE_NAME);
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
		 System.out.println("Got to addPersona.");
		String addStatement = String.format(
		        "insert into %s values('%s', '%s', '%s')", TABLE_NAME, 
		       score.getPersonaId(),
		       score.getGameId(),
		       score.getWinLose()
		        );
		super.add(addStatement);
	}

}
