package a00869363.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a00869363.data.Persona;

public class PersonaFormat {
	static final String FILENAME = "personas.dat";
	static List<Persona> listOfPersonas;
	

	public PersonaFormat() {
		super();
		listOfPersonas = new ArrayList<Persona>();
		try {
			listOfPersonas = createListOfPersonas();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			e.printStackTrace();
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
