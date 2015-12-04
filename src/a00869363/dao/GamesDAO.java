package a00869363.dao;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.data.Game;

public class GamesDAO extends Dao{
	private static final Logger LOG = LogManager.getLogger(PlayerDAO.class);
	
	private static GamesDAO gamesDao;
	public static final String TABLE_NAME = "games";
	
	
	private GamesDAO() {		
		super(TABLE_NAME);
	}

	public static GamesDAO getGamesGAO(){
		if(gamesDao == null){
			gamesDao = new GamesDAO();
		}		
		return gamesDao;
	}
	@Override
	public void create() throws SQLException {
		String createStatement = "create table games(id varchar(10) not null, name VARCHAR(64), producer VARCHAR(64) )";
		LOG.info("Executed command: " + createStatement);
		super.create(createStatement);
	}
	
	/**
	 * Inserts singular game into database
	 * @param player
	 * @throws SQLException
	 */
	public void addGame(Game game) throws SQLException {		
		String addStatement = String.format(
		        "insert into %s values('%s', '%s', '%s')", TABLE_NAME, 
		       game.getId(),
		       game.getName(),
		       game.getProducer()
		        );
		super.add(addStatement);
	}
	public void delete(Game game) throws SQLException{
		String deleteStatement = "DELETE FROM " + TABLE_NAME + " WHERE id = " + game.getId();
		super.delete(deleteStatement);
	}
}
