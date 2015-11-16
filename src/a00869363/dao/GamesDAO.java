package a00869363.dao;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.data.Game;
import a00869363.data.Persona;

public class GamesDAO extends Dao{
	private static final Logger LOG = LogManager.getLogger(PlayerDAO.class);
	public static final String TABLE_NAME = "games";
	public GamesDAO(Database database) {
		super(database, TABLE_NAME);
	}

	@Override
	public void create() throws SQLException {
		String createStatement = "create table games(id varchar(10) not null, name VARCHAR(64), producer VARCHAR(64) )";
		super.create(createStatement);
	}
	
	/**
	 * Inserts singular game into database
	 * @param player
	 * @throws SQLException
	 */
	public void addGame(Game game) throws SQLException {
		 System.out.println("Got to addPersona.");
		String addStatement = String.format(
		        "insert into %s values('%s', '%s', '%s')", TABLE_NAME, 
		       game.getId(),
		       game.getName(),
		       game.getProducer()
		        );
		super.add(addStatement);
	}
	
}
