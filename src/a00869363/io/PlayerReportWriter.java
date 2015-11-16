/**
 * Project: A00869363Gis
 * File: PlayerReportWriter.java
 * Date: Oct 24th, 2015
 * Time: 10:14 AM	
 */
/**
 * @author Catherine Li, A00869363
 * This class formats the player data to be displayed in player.txt
 */
package a00869363.io;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.data.Player;

public class PlayerReportWriter {
	private static final Logger LOG = LogManager.getLogger(PlayerReportWriter.class);
	static String title = "";
	static String border = "------------------------------------------------------------------------------------------------------------------------";
	
	public static void displayPlayers(List<Player> players) {
		System.out.println(title);
		System.out.println(border);
		System.out.format("%-12s%-24s%-24s%-12s%-18s%-12s", "ID",
				"Full Name", "Email", "Age", "Games Played", "Total Wins");
		System.out.println();
		System.out.println(border);
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) != null) {
				System.out.format("%-12s%-24s%-24s%-12s%-18s%-12s", players.get(i).getId(), players.get(i)
						.getfName() + " " + players.get(i).getlName(), players.get(i).getEmail(), players.get(i).getAge(),
						PlayerFormat.calculateTotalGamesPlayed(players.get(i)), 
						PlayerFormat.calculateTotalWins(players.get(i)));
				
			}
			System.out.println("");
		}
		System.out.println(border);
	}
	
	public static void writePlayerReport(List<Player> players){
		LOG.info("Generating players report");
		String playerReportOutput = "";
		playerReportOutput += title + "\n";
		playerReportOutput += border + "\n";
		playerReportOutput += String.format("%-12s%-24s%-24s%-12s%-18s%-12s", "ID",
				"Full Name", "Email", "Age", "Games Played", "Total Wins");
		playerReportOutput += "\n" + border + "\n";
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) != null) {
				playerReportOutput += String.format("%-12s%-24s%-24s%-12s%-18s%-12s", players.get(i).getId(), players.get(i)
						.getfName() + " " + players.get(i).getlName(), players.get(i).getEmail(), players.get(i).getAge(),
						PlayerFormat.calculateTotalGamesPlayed(players.get(i)), 
						PlayerFormat.calculateTotalWins(players.get(i)));
				playerReportOutput += "\n";
			}
		}
		playerReportOutput += border;
		WriteToFile.writeReports(playerReportOutput, PlayerFormat.OUTPUT_FILENAME);
	}
}
