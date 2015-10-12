package a00869363.io;

import java.util.List;

import a00869363.data.Player;

public class PlayerReportWriter {
	static String title = "";
	static String border = "------------------------------------------------------------------------------------------------------------------------";;
	
	public static void displayPlayers(List<Player> players) {
		System.out.println(title);
		System.out.println(border);
		System.out.format("%-12s%-24s%-24s%-12s%-12s%-12s", "ID",
				"Full Name", "Email", "Age", "Total Games Played", "Total Wins");
		System.out.println();
		System.out.println(border);
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) != null) {
				System.out.format("%-12s%-24s%-24s%-12s%-12s%-12s", players.get(i).getId(), players.get(i)
						.getfName() + " " + players.get(i).getlName(), players.get(i).getEmail(), players.get(i).getAge(),
						PlayerFormat.calculateTotalGamesPlayed(players.get(i)), 
						PlayerFormat.calculateTotalWins(players.get(i)));
				System.out.println();
			}
		}
	}
}
