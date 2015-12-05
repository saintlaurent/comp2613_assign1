package a00869363.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.data.Persona;

public class PersonasDAO extends Dao{
	
	private static final Logger LOG = LogManager.getLogger(PlayerDAO.class);
	public static final String TABLE_NAME = "personas";
	private static PersonasDAO personasDao;
	
	private PersonasDAO() {
		super(TABLE_NAME);
	}
	
	public static PersonasDAO getPersonasDAO(){
		if(personasDao == null){
			personasDao = new PersonasDAO();
		}
		return personasDao;
	}

	@Override
	public void create() throws SQLException {
		String createStatement = "create table personas(id varchar(10) not null, player_id VARCHAR(10), gamertag VARCHAR(64), platform VARCHAR(64), primary key (id) )";
		super.create(createStatement);
		LOG.info("Executed statement: " + createStatement);
	}
	
	/**
	 * Inserts singular persona into database
	 * @param player
	 * @throws SQLException
	 */
	public void addPersona(Persona persona) throws SQLException {
		String addStatement = String.format(
		        "insert into %s values('%s', '%s', '%s', '%s')", TABLE_NAME, 
		       persona.getId(),
		       persona.getPlayerId(),
		       persona.getGamerTag(),
		       persona.getPlatform()
		        );
		super.add(addStatement);
	}
	
	public List<Persona> selectAll(){
		List<Persona> personas = new ArrayList<Persona>();
		ResultSet resultSet = super.selectAllFromDb();
		
		try {
			while (resultSet.next()) {
				personas.add(new Persona(
						String.valueOf(resultSet.getInt("id")),
						String.valueOf(resultSet.getInt("player_id")),
						resultSet.getString("gamertag"),
						resultSet.getString("platform")));
			}
		} catch (SQLException e) {
			LOG.error("Could not select all from personas.");
		} 
		return personas;
	}
	
	public void update(String oldGamertag, String newGamertag) throws SQLException {
		Statement statement = null;
		try {
			String sqlString = String
			        .format("UPDATE %s set %s='%s' WHERE %s='%s'",
			                tableName, //
			                "gamertag", newGamertag,			                
			                "gamertag", oldGamertag);			
			Connection connection = database.connect();
			statement = connection.createStatement();
			statement.executeUpdate(sqlString);
		} finally {
			close(statement);
		}
	}
	
	public Persona selectByGamertag(String gamertag){
		Persona persona = null;
		Statement statement = null;
		String sqlString = "";
		try {
			if(gamertag.trim().equals("")){
				sqlString = "SELECT * FROM personas";
			} else {
				sqlString = "SELECT * FROM personas WHERE gamertag = '" + gamertag + "'";	
			}					
			Connection connection = database.connect();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlString);
			while (resultSet.next()) {
				persona = new Persona(
						String.valueOf(resultSet.getInt("id")),
						String.valueOf(resultSet.getInt("player_id")),
						resultSet.getString("gamertag"),
						resultSet.getString("platform"));
			}
		} catch (SQLException e) {
			LOG.error("Error selecting persona by gamertag");
		} finally {
			close(statement);
		}		
		return persona;
	}
	
	public void delete(Persona persona) throws SQLException{
		String deleteStatement = "DELETE FROM " + TABLE_NAME + " WHERE id = " + persona.getId();
		super.delete(deleteStatement);
	}
}
