/**
 * Project: A00869363Gis
 * File: ScoreFormat.java
 * Date: Oct 24th, 2015
 * Time: 10:14 AM	
 */
/**
 * @author Catherine Li, A00869363
 * This class reads the score data file and creates the scores objects
 *
 */
package a00869363.io;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.dao.Database;
import a00869363.dao.PersonasDAO;
import a00869363.dao.ScoresDAO;
import a00869363.data.Score;


public class ScoreFormat {
	private static final Logger LOG = LogManager.getLogger(ScoreFormat.class);
	static final String INPUT_FILENAME = "scores.dat";
	static List<Score> listOfScores;
	private Database db;
	private ScoresDAO dao;

	public ScoreFormat() {
		super();
		db = Database.getDatabaseInstance(); 
		dao = ScoresDAO.getScoresDao();
		listOfScores = createListOfScores();
		try {
			if(!Database.tableExists(db.connect(), "scores")){
				dao.create();
			for (Score score : listOfScores){
				dao.addScore(score);
				}
			}
			
		} catch (SQLException e) {
			LOG.error("Error creating scores table. Class: ScoreFormat. Method: constructor.");
		}
		
		
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
		LOG.info("Readings scores data file.");
		try {
			info = FileInput.readFile(INPUT_FILENAME);
			for(String i : info){
				String[] scoreInfoArray = i.split("\\|");
				scores.add(new Score(scoreInfoArray[0], scoreInfoArray[1], 
						scoreInfoArray[2]));
			}
		} catch (IOException e) {
			LOG.error("Error reading scores data file.");
		}
		return scores;
	}
	
}
