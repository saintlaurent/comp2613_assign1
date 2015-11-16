/**
 * Project: A00869363Gis
 * File: Gis.java
 * Date: Oct 24th, 2015
 * Time: 10:14 AM	
 */
/**
 * @author Catherine Li, A00869363
 * This is the main class which parses the command line arguments.
 *
 */
package a00869363;


import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.io.GamesFormat;
import a00869363.io.Leaderboard;
import a00869363.io.LeaderboardReportWriter;
import a00869363.io.PersonaFormat;
import a00869363.io.PlayerFormat;
import a00869363.io.PlayerReportWriter;
import a00869363.io.ScoreFormat;
import javax.swing.*;


public class Gis {
	
	private static final Logger LOG = LogManager.getLogger(Gis.class);
	static final String TOTAL = "total";
	static final String SORT_BY_GAME = "by_game";
	static final String SORT_BY_COUNT = "by_count";
	static final String SORT_DEFAULT = "by_gamertag";
	static final String DESC = "desc";
	static final String PLATFORM = "platform=";
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		LOG.info("Starting main method");
		PlayerFormat pf = new PlayerFormat(); 
		PersonaFormat personaFormat = new PersonaFormat(); 
		GamesFormat gamesFormat = new GamesFormat();
		ScoreFormat scoreFormat = new ScoreFormat();
		LeaderboardReportWriter lrw = new LeaderboardReportWriter();System.exit(0);
		
		if(args.length == 0){
			//if no command line arguments are entered, the default leaderboard_report.txt will be produced
			//LeaderboardReportWriter lrw = new LeaderboardReportWriter();
			List<Leaderboard> list = lrw.sortLeaderboard(lrw.createLeaderboardItems(),SORT_DEFAULT, false);
			lrw.writeToFile(list);
			System.exit(1);
		} else if (args[0].trim().equalsIgnoreCase("players")){
			PlayerReportWriter.displayPlayers(pf.getListOfPlayers());
			PlayerReportWriter.writePlayerReport(pf.getListOfPlayers());
			System.exit(1);
		} else {
			
			/**
			 * array of command line arguments are passed into the constructor of the object that
			 * sorts the leaderboard
			 **/
			String[] flagParams = args; 
			String sortCriteria = "";
			boolean desc = false;
			String platformFilter = "";
			boolean printTotal = false;
			for(int i = 0; i < flagParams.length && i < 5; i++){
				if(flagParams[i].equals(SORT_BY_GAME)|| flagParams[i].equals(SORT_BY_COUNT)){
					sortCriteria = flagParams[i].equals(SORT_BY_GAME) ? SORT_BY_GAME : SORT_BY_COUNT;
				} else if(flagParams[i].equals(DESC)){
					desc = true;
				} else if (flagParams[i].startsWith(PLATFORM)){
					platformFilter = flagParams[i].substring(9);
				} else if (flagParams[i].trim().equals(TOTAL)){
					printTotal = true;
				}
			}
			
			//LeaderboardReportWriter lrw = new LeaderboardReportWriter();
			List<Leaderboard>list = lrw.createLeaderboardItems();
			
			if(!platformFilter.equals("")){
				//filter by platform
				list = lrw.filterByPlatform(platformFilter, list);
			}	
			 list = lrw.sortLeaderboard(list, sortCriteria, desc);
					
			lrw.writeToFile(list);
			if(printTotal){
				//add on total
				lrw.writeTotal(list);
			}
		}
		LOG.info("End method");
	}
}
