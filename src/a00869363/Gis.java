package a00869363;


import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import a00869363.data.Persona;
import a00869363.io.GamesFormat;
import a00869363.io.Leaderboard;
import a00869363.io.LeaderboardReportWriter;
import a00869363.io.PersonaFormat;
import a00869363.io.PlayerFormat;
import a00869363.io.ScoreFormat;


public class Gis {
	static final String TOTAL = "total";
	static final String SORT_BY_GAME = "by_game";
	static final String SORT_BY_COUNT = "by_count";
	static final String DESC = "desc";
	static final String PLATFORM = "platform=";
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		PlayerFormat pf = new PlayerFormat();
		PersonaFormat personaFormat = new PersonaFormat();
		GamesFormat gamesFormat = new GamesFormat();
		ScoreFormat scoreFormat = new ScoreFormat();
//		System.out.println(pf.createPlayer("1|Fred|Fish|fredfish@gamer.net|19770322"));
		
		//List<Persona> p = PersonaFormat.createListOfPersonas();
		//List<Persona> playerPersonas = pf.getPersonas("5");
//		for (int i = 0; i < p.size(); i++) {
//			System.out.println(p.get(i).getGamerTag());
//		}
//		for (int i = 0; i < playerPersonas.size(); i++) {
//			System.out.println(playerPersonas.get(i).getGamerTag());
//		}
//		int num = pf.calculateTotalWins(pf.createPlayer("5|Mark|Chan|mchan@oneminutemail.com|19950401"));
//		System.out.println(num);
		String[] flagParams = args[0].split(" ");
		boolean selectedSortOrder = false;
		for(int i = 0; i < flagParams.length && i < 4; i++){
			if(flagParams[i].equals(SORT_BY_GAME) || flagParams[i].equals(SORT_BY_COUNT)){
				
			} else if(flagParams[i].equals(TOTAL)){
				
			} else if(flagParams[i].equals(DESC)){
				
			} else if (flagParams[i].startsWith(PLATFORM)){
				
			}
		}
		LeaderboardReportWriter lrw = new LeaderboardReportWriter();
		List<Leaderboard> list = lrw.sortLeaderboardByCount(lrw.createLeaderboardItems());
		LeaderboardReportWriter leaderboardWriter = new LeaderboardReportWriter();
		for(Leaderboard l : list){
			System.out.println(l.toString());
		}
		
	}

}
