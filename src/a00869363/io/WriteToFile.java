package a00869363.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {
	static final String LEADERBOARD_FILE = "leaderboard_report.txt";
	static final String PLAYER_FILE = "players.txt";
	
	public static void writeReports(String stringToOutput){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE));
			writer.write(stringToOutput);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
