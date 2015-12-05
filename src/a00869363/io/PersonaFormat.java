/**
 * Project: A00869363Gis
 * File: PersonaFormat.java
 * Date: Oct 24th, 2015
 * Time: 10:14 AM	
 */
/**
 * @author Catherine Li, A00869363
 * This class reads the persona data file and creates persona objects
 *
 */
package a00869363.io;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import a00869363.dao.Database;
import a00869363.dao.PersonasDAO;
import a00869363.data.Persona;

public class PersonaFormat {
	private static final Logger LOG = LogManager.getLogger(PersonaFormat.class);
	static final String FILENAME = "personas.dat";
	static List<Persona> listOfPersonas;
	Database database = Database.getDatabaseInstance();
	static PersonasDAO personasDao = PersonasDAO.getPersonasDAO();

	public PersonaFormat() {
		super();
		listOfPersonas = new ArrayList<Persona>();
		try {
			LOG.info("Reading personas.dat");
			listOfPersonas = createListOfPersonas();	
			Connection connection = database.connect();
			if(! Database.tableExists(connection, "personas")){
				personasDao.create();	
				for(Persona persona : listOfPersonas){
				addToDatabase(persona);
				}
			}
			
		} catch (IOException e) {
			LOG.error("Error reading personas.dat file.");
		} catch (SQLException e) {
			LOG.error("Error creating personas table. Class: PersonaFormat. Method: Constructor");
		}
		
		
	}

	public static List<Persona> createListOfPersonas() throws IOException{
		List<String> info = FileInput.readFile(FILENAME);
		List<Persona> personas = new ArrayList<Persona>();
		for(String i : info){
			String[] personaInfoArray = i.split("\\|");
			Persona persona = new Persona(personaInfoArray[0], personaInfoArray[1], 
					personaInfoArray[2], personaInfoArray[3]);
			personas.add(persona);
		}		
		return personas;
	}
	
	public static void addToDatabase(Persona persona){
		try {
			personasDao.addPersona(persona);
		} catch (SQLException e) {
			//LOG.error("Cannot add persona to database. Class PersonaFormat. Method: Constructor.");
			e.printStackTrace();
		}
	}
	public static Map<String, Persona> personaIdToPersonaMap(){
		Map<String, Persona> personaIdToPersona = new HashMap<String, Persona>();
		List<Persona> personas = listOfPersonas;
		for(Persona persona : personas){
			personaIdToPersona.put(persona.getId(), persona);
		}
		return personaIdToPersona;
	}
	
	public static List<Persona> getPersonas(String playerId){
		
		List<Persona> personas = null;
		
		try {
			personas = PersonaFormat.createListOfPersonas();
		} catch (IOException e) {
			LOG.error("Error reading personas.dat file.");
		}
		List<Persona> playerPersonas = new ArrayList<Persona>();
		for(Persona persona : personas){
			if(persona.getPlayerId().equalsIgnoreCase(playerId)){
				playerPersonas.add(persona);
			}
		}
		return playerPersonas;
	}
}
