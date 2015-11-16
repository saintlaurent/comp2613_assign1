/**
 * Project: A00869363Gis
 * File: PlayerFormat.java
 * Date: Oct 24th, 2015
 * Time: 10:14 AM	
 */
/**
 * @author Catherine Li, A00869363
 * This is the main class which readers the player data and creates the player objects
 *
 */
package a00869363.io;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.data.Persona;
import a00869363.data.Player;
import a00869363.data.Score;
import a00869363.dao.*;

public class PlayerFormat {
	private static final Logger LOG = LogManager.getLogger(PlayerFormat.class);
	List<Player> listOfPlayers;
	static final String OUTPUT_FILENAME = "players_report.txt";
	static final String INPUT_FILENAME = "players.dat";
	public List<Player> getListOfPlayers() {
		return listOfPlayers;
	}
	
	public PlayerFormat() {
		super();
		List<Player> listOfPlayers = new ArrayList<Player>();
		Database db = new Database(); 
		PlayerDAO playerDAO = new PlayerDAO(db);
		try {
			playerDAO.create();
		} catch (SQLException e1) {
			LOG.error("Cannot create players table. Class: PlayerFormat.");
		}
		try {
			LOG.info("Reading player infomation file.");
			List<String> arrayOfInfo = FileInput.readFile(INPUT_FILENAME);
			for(String line : arrayOfInfo){
				Player player = this.createPlayer(line);
				listOfPlayers.add(player);
				//playerDAO.add(player);
			}
		} catch (IOException e) {
			LOG.error("Problem reading player data file.");
		} 
//		catch (SQLException e) {
//			LOG.error("Error in adding player. Class: PlayerFormat.");
//		}
		this.listOfPlayers = listOfPlayers;
	}

	public Player createPlayer(String playerInfo){
		String[] playerInfoString = playerInfo.split("\\|");
		Player player = new Player(playerInfoString[0],playerInfoString[1], 
				playerInfoString[2],playerInfoString[3],playerInfoString[4]);
		return player;
	}
	
	public static int calculateAge(String birthday){
		int year = Integer.parseInt(birthday.substring(0, 4));
		int month = Integer.parseInt(birthday.substring(4, 6));
		int day = Integer.parseInt(birthday.substring(6));
		LocalDate today = LocalDate.now();
		LocalDate birthdayDate = LocalDate.of(year, month, day);
		Period p = Period.between(birthdayDate, today);
		return p.getYears();
	}
	
	public static int calculateTotalGamesPlayed(Player player){
		int total = 0;
		List<Persona> playerPersonas = PersonaFormat.getPersonas(player.getId());
		List<Score> listOfScores = ScoreFormat.getListOfScores();
		for(Score score : listOfScores){
			for(Persona p : playerPersonas){
				if(p.getId().equalsIgnoreCase(score.getPersonaId())){
					total++;
				}
			}
		}
		return total;
	}
	
	public static int calculateTotalWins(Player player){
		
		int totalWins = 0;
		List<Persona> playerPersonas = PersonaFormat.getPersonas(player.getId());
		
		List<Score> listOfScores = ScoreFormat.getListOfScores();
		for(Score score : listOfScores){
			for(Persona p : playerPersonas){
				if(p.getId().equalsIgnoreCase(score.getPersonaId()) && score.getWinLose().equalsIgnoreCase("WIN")){
					totalWins++;
				}
			}
		}
		
		return totalWins;
	}
	
}
