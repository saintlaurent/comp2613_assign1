package a00869363.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import a00869363.data.Score;


public class ScoreFormat {
	static final String FILENAME = "scores.dat";
	
	static List<Score> listOfScores;

	public ScoreFormat() {
		super();
		listOfScores = createListOfScores();
	}
	
	
	public static List<Score> getListOfScores() {
		return listOfScores;
	}

	/**
	 * static final String FILENAME = "personas.dat";
	List<Persona> listOfPersonas;


	public static List<Persona> createListOfPersonas() throws IOException{
		List<String> info = FileInput.readFile(FILENAME);
		List<Persona> personas = new ArrayList<Persona>();
		for(String i : info){
			String[] personaInfoArray = i.split("\\|");
 		personas.add(new Persona(personaInfoArray[0], personaInfoArray[1], 
					personaInfoArray[2], personaInfoArray[3]));
		}
		
		return personas;
	}
	 * @throws IOException 
	 */
	public static List<Score> createListOfScores(){
		List<String> info;
		List<Score> scores = new ArrayList<Score>();
		try {
			info = FileInput.readFile(FILENAME);
			for(String i : info){
				String[] scoreInfoArray = i.split("\\|");
				scores.add(new Score(scoreInfoArray[0], scoreInfoArray[1], 
						scoreInfoArray[2]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scores;
	}
	
}
