package a00869363.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a00869363.data.Persona;
import a00869363.data.Player;
import a00869363.data.Score;

public class LeaderboardReportFormat {
	List<Leaderboard> leaderboardRows;
	
	public LeaderboardReportFormat() {
		super();
		this.leaderboardRows = this.createLeaderboardItems();
	}

	public List<Leaderboard> sortLeaderboard(List<Leaderboard> row){
		Collections.sort(row, new Comparator<Leaderboard>() {
	        @Override public int compare(Leaderboard first, Leaderboard second) {
	            return first.getGamerTag().compareTo(second.getGamerTag());
	        }
		});
		return row;
	}
	
	public List<Leaderboard> sortLeaderboardByCount(List<Leaderboard> row){
		Collections.sort(row, new Comparator<Leaderboard>() {
	        @Override public int compare(Leaderboard first, Leaderboard second) {
	            return first.wins - second.wins;
	        }
		});
		return row;
	}
	
	public List<Leaderboard> filterByPlatform(String platform){
		List<Leaderboard> allLeaderboardRows = this.leaderboardRows;
		List<Leaderboard> filteredByPlatform = new ArrayList<Leaderboard>();
		for(Leaderboard row : allLeaderboardRows){
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
	
	public static Map<String, Integer> calculateTotals(){
		Map<String, Integer> gameTitleToTotal = new HashMap<String, Integer>();
		List<Score> listOfScores = ScoreFormat.getListOfScores();
		for(Score score : listOfScores){
			String fullTitle = getTitleOfGameByGameId(score.getGameId());
			if(gameTitleToTotal.containsKey(fullTitle)){
				gameTitleToTotal.put(fullTitle, gameTitleToTotal.get(fullTitle) + 1);
			} else {
				gameTitleToTotal.put(fullTitle, 1);
			}
		}
		return gameTitleToTotal;
	}
	
	public List<Leaderboard> createLeaderboardItems(){
		Map<String, int[]> leaderboardEntry = this.getGameNameByGamertag();
		List<Leaderboard> rows = new ArrayList<Leaderboard>();
		
		for (Map.Entry<String, int[]> entry : leaderboardEntry.entrySet()) {			
		    String[] key = entry.getKey().split("\\|");//Key is persona Id | game Id
		    String personaID = key[0];
		    String gameId = key[1];
		    int[] value = entry.getValue();		    
		    String gameName = this.getTitleOfGameByGameId(gameId);
		    Persona persona = this.getPersonaById(personaID);
		    //Get full title of game by id
		    rows.add(new Leaderboard(value[0], value[1], gameName, persona.getGamerTag(), persona.getPlatform()));
		}
		return rows;
	}
	
	
	public Map<String, int[]> getGameNameByGamertag(){//Todo: change this terrible method name
		Map<String, int[]> gameScore = new HashMap<String, int[]>();
		try {
			List<Persona> listOfPersonas = PersonaFormat.createListOfPersonas();//Duplication?
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
			e.printStackTrace();
		}
		return gameScore;
	}
	
//	public Map<String, String> leaderboardToPlatform(){
//		Map<String,String> leaderboardPlatform = new HashMap<String, String>();
//		List<Leaderboard> leaderboardRows = this.leaderboardRows;
//		for(Leaderboard lb : leaderboardRows){
//			leaderboardPlatform.put(lb.getPlatform(), lb);
//		}
//		return leaderboardPlatform;
//	}
}
