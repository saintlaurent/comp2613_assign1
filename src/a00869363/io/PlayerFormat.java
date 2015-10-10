package a00869363.io;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import a00869363.data.Persona;
import a00869363.data.Player;
import a00869363.data.Score;

public class PlayerFormat {
	List<Player> listOfPlayers;
	static final String FILENAME = "players.dat";
	
	public PlayerFormat() {
		super();
		List<Player> listOfPlayers = new ArrayList<Player>();
		try {
			List<String> arrayOfInfo = FileInput.readFile(FILENAME);
			for(String line : arrayOfInfo){
				Player player = this.createPlayer(line);
				listOfPlayers.add(player);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.listOfPlayers = listOfPlayers;
	}

	public Player createPlayer(String playerInfo){
		String[] playerInfoString = playerInfo.split("\\|");
		Player player = new Player(playerInfoString[0],playerInfoString[1], 
				playerInfoString[2],playerInfoString[3],playerInfoString[4]);
		return player;
	}
	
	public static int calculateAge(String birthday){
		int year = Integer.parseInt(birthday.substring(0, 3));
		int month = Integer.parseInt(birthday.substring(3, 5));
		int day = Integer.parseInt(birthday.substring(5));
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
