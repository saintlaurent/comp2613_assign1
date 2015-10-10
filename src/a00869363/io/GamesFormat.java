package a00869363.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a00869363.data.Game;

public class GamesFormat {
	List<Game> listOfGames;
	
	public GamesFormat() {
		super();
		this.listOfGames = createGames();
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
			e.printStackTrace();
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
