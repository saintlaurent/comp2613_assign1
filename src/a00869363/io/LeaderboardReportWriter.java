/**
 * Project: A00869363Gis
 * File: LeaderboardReportWriter.java
 * Date: Oct 24th, 2015
 * Time: 10:14 AM	
 */
/**
 * @author Catherine Li, A00869363
 * This class organizes and sorts information to output to leaderboard report
 */
package a00869363.io;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.data.Persona;
import a00869363.data.Score;
import a00869363.dao.*;

public class LeaderboardReportWriter {
	private static final Logger LOG = LogManager.getLogger(LeaderboardReportWriter.class);
	static final String LEADERBOARD_FILENAME = "leaderboard_report.txt";
	static final String SORT_BY_GAME = "by_game";
	static final String SORT_BY_COUNT = "by_count";
	List<Leaderboard> leaderboardRows;
	
	public LeaderboardReportWriter(boolean sortDescending) {
		super();
	}

	public LeaderboardReportWriter() {
		super();
		this.leaderboardRows = this.createLeaderboardItems();
		Database db = new Database(); 
		LeaderboardDAO dao = new LeaderboardDAO(db);
		try {
			dao.create();
			for (Leaderboard row : this.leaderboardRows){
				dao.addLeaderboardItem(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error("Could not create leaderboard table. Class: LeaderboardReportWriter. Method: Constructor.");
		}
		
	}
	
	public void writeToFile(List<Leaderboard> rows){
		String playerReportOutput = "";
		String border = "------------------------------------------------------------------------------------------------------------------------";
		playerReportOutput += border + "\n";
		playerReportOutput += String.format("%-12s%-24s%-24s%-12s", "Win:Loss",
				"Game Name","Gamertag", "Platform");
		playerReportOutput += "\n" + border + "\n";

		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i) != null) {
				playerReportOutput += String.format("%-12s%-24s%-24s%-12s", rows.get(i).getWins() + ":" +rows.get(i).losses, 
						rows.get(i).getGameName(), rows.get(i).gamerTag, rows.get(i).getPlatform());
				playerReportOutput += "\n";
			}
		}
		
		playerReportOutput += border;
		WriteToFile.writeReports(playerReportOutput, LEADERBOARD_FILENAME);
		LOG.info(playerReportOutput);
	}
	public List<Leaderboard> sortLeaderboard(List<Leaderboard> row, String sortCriteria, boolean desc){
		
		switch (sortCriteria){
		case "":
			this.sortLeaderboardByGamertag(row, desc);
			break;
		case SORT_BY_COUNT:
			this.sortLeaderboardByCount(row, desc);
			break;
		case SORT_BY_GAME:
			this.sortLeaderboardByGameName(row, desc);
			break;
		default:
			this.sortLeaderboardByGamertag(row, desc);
			break;
			
		}
		return row;
	}
	
	public List<Leaderboard> sortLeaderboardByGamertag(List<Leaderboard> row, boolean desc){
		Collections.sort(row, new Comparator<Leaderboard>() {
	        @Override public int compare(Leaderboard first, Leaderboard second) {
	        	if(!desc){
	        		return first.getGamerTag().compareTo(second.getGamerTag());
	        	} else {
	        		return second.getGamerTag().compareTo(first.getGamerTag());
	        	}
	        }
		});
		return row;
	}
	public List<Leaderboard> sortLeaderboardByCount(List<Leaderboard> row, boolean desc){		
		Collections.sort(row, new Comparator<Leaderboard>() {
	        @Override public int compare(Leaderboard first, Leaderboard second) {
	        	if(desc){
	        		return second.wins - first.wins;
	        	} else {
	        		return first.wins - second.wins;
	        	}	            
	        }
		});
		return row;
	}
	
	public List<Leaderboard> sortLeaderboardByGameName(List<Leaderboard> row, boolean desc){
		Collections.sort(row, new Comparator<Leaderboard>() {
	        @Override public int compare(Leaderboard first, Leaderboard second) {
	        	if(!desc){
	        		return first.gameName.compareTo(second.gameName);
	        	} else {
	        		return second.gameName.compareTo(first.gameName);
	        	}	            
	        }
		});
		return row;
	}
	
	public List<Leaderboard> filterByPlatform(String platform, List<Leaderboard> rowsBeforeFilter){
		
		List<Leaderboard> filteredByPlatform = new ArrayList<Leaderboard>();
		for(Leaderboard row : rowsBeforeFilter){
			if(row.getPlatform().equals(platform)){
				filteredByPlatform.add(row);
			}
		}
		return filteredByPlatform;
	}
	public static String getTitleOfGameByGameId(String gameId){
		String title = "";
		Map<String, String> gameIdToName = GamesFormat.getIdGameName();
		if(gameIdToName.containsKey(gameId)){
			title = gameIdToName.get(gameId);
		}
		return title;
	}
	
	public Persona getPersonaById(String id){
		Map<String, Persona> idToPersona = PersonaFormat.personaIdToPersonaMap();
		Persona persona = null;
		if(idToPersona.containsKey(id)){
			persona = idToPersona.get(id);
		}
		return persona;
	}
	
	/**
	 * This method calculates the totals of each game played 
	 * @param list
	 * @return
	 */
	public static Map<String, Integer> calculateTotals(List<Leaderboard> list){
		Map<String, Integer> gameTitleToTotal = new HashMap<String, Integer>();
		//List<Score> listOfScores = ScoreFormat.getListOfScores();
		for(Leaderboard item : list){
			String fullTitle = item.getGameName();
			if(gameTitleToTotal.containsKey(fullTitle)){
				gameTitleToTotal.put(fullTitle, gameTitleToTotal.get(fullTitle) + item.getWins() + item.getLosses());
			} else {
				gameTitleToTotal.put(fullTitle, item.getLosses()+item.getWins());
			}
		}
		return gameTitleToTotal;
	}
	
	public void writeTotal(List<Leaderboard> list){
		Map<String, Integer> gameNameToTotal = calculateTotals(list);
		String output = "\n";
		for (Map.Entry<String, Integer> entry : gameNameToTotal.entrySet()) {
			output+= entry.getKey() + ": " + entry.getValue() + "\n";
		}
		WriteToFile.appendToFile(output, LEADERBOARD_FILENAME);
		LOG.info(output);
	}
	
	public List<Leaderboard> createLeaderboardItems(){
		Map<String, int[]> leaderboardEntry = this.getGameNameByGamertag();
		List<Leaderboard> rows = new ArrayList<Leaderboard>();
		
		for (Map.Entry<String, int[]> entry : leaderboardEntry.entrySet()) {			
		    String[] key = entry.getKey().split("\\|");//Key is persona Id | game Id
		    String personaID = key[0];
		    String gameId = key[1];
		    int[] value = entry.getValue();		    
		    String gameName = LeaderboardReportWriter.getTitleOfGameByGameId(gameId);
		    Persona persona = this.getPersonaById(personaID);
		    //Get full title of game by id
		    rows.add(new Leaderboard(value[0], value[1], gameName, persona.getGamerTag(), persona.getPlatform()));
		}
		return rows;
	}
	
	
	public Map<String, int[]> getGameNameByGamertag(){
		Map<String, int[]> gameScore = new HashMap<String, int[]>();
		List<Persona> listOfPersonas;
		try {
			listOfPersonas = PersonaFormat.createListOfPersonas();
			List<Score> listOfScores = ScoreFormat.getListOfScores();
			
			for(Persona persona : listOfPersonas){				
				String personaId = persona.getId();
				for(Score score : listOfScores){
					if(score.getPersonaId().equalsIgnoreCase(persona.getId())){
						String key = personaId + "|" + score.getGameId();
						if(gameScore.containsKey(key)){
							int[] gameScoreValue = gameScore.get(key);
							//gameScoreValue = 
							if(score.getWinLose().equalsIgnoreCase("WIN")) {//Turn into ternary
								gameScore.put(key, new int[] {gameScoreValue[0] + 1,gameScoreValue[1] });
							} else {
								gameScore.put(key, new int[] {gameScoreValue[0],gameScoreValue[1] + 1});
							}		
							
						} else {
							if(score.getWinLose().equalsIgnoreCase("WIN")) {//Turn into ternary
								gameScore.put(key, new int[] {1,0});
							} else {
								gameScore.put(key, new int[] {0,1});
							}
						}						
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error("Error creating personas.");
		}//Duplication?
		
		return gameScore;
	}
	
}
