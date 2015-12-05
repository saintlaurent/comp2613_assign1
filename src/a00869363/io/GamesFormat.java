/**
 * Project: A00869363Gis
 * File: GamesFormat.java
 * Date: Oct 24th, 2015
 * Time: 10:14 AM	
 */
/**
 * @author Catherine Li, A00869363
 * This class creates the Game objects from the games data file
 *
 */
package a00869363.io;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.dao.Database;
import a00869363.dao.GamesDAO;
import a00869363.data.Game;

public class GamesFormat {
	private static final Logger LOG = LogManager.getLogger(GamesFormat.class);
	List<Game> listOfGames;
	GamesDAO gamesDao;
	Database database;
	public GamesFormat() {
		super();
		this.listOfGames = createGames();
		 gamesDao = GamesDAO.getGamesGAO();
		try {
			database = Database.getDatabaseInstance();
			if(!Database.tableExists(database.connect(), "games")){
				gamesDao.create();
			for(Game game : listOfGames){
				gamesDao.addGame(game);
				}
			}
			
		} catch (SQLException e) {
			LOG.error("Error creating games table. Class: GamesFormat. Method: Constructor.");
		}
	}	
	public static List<Game> createGames(){
		
		List<Game> listOfGames = new ArrayList<Game>();
		try {
			List<String> gameInfo = FileInput.readFile("games.dat");
			for(String gameInfoLine: gameInfo){
				String[] infoArray = gameInfoLine.split("\\|");
				listOfGames.add(new Game(infoArray[0], infoArray[1], infoArray[2]));
			}
		} catch (IOException e) {			
			LOG.error("Error reading games infomation file.");
		}
		return listOfGames;
	}
	
	public static Map<String, String> getIdGameName(){
		Map<String, String> idToGameName = new HashMap<String, String>();
		List<Game> listOfGames = createGames();
		for(Game game : listOfGames){
			idToGameName.put(game.getId(), game.getName());
		}
		return idToGameName;
	}
	
}
