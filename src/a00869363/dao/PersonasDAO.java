package a00869363.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.data.Persona;
import a00869363.data.Player;

public class PersonasDAO extends Dao{
	
	private static final Logger LOG = LogManager.getLogger(PlayerDAO.class);
	public static final String TABLE_NAME = "personas";
	
	public PersonasDAO(Database database) {
		super(database, TABLE_NAME);

	}

	@Override
	public void create() throws SQLException {
		String createStatement = "create table personas(id varchar(10) not null, player_id VARCHAR(10), gamertag VARCHAR(64), platform VARCHAR(64), primary key (id) )";
		super.create(createStatement);
	}
	
	/**
	 * Inserts singular persona into database
	 * @param player
	 * @throws SQLException
	 */
	public void addPersona(Persona persona) throws SQLException {
		 System.out.println("Got to addPersona.");
		String addStatement = String.format(
		        "insert into %s values('%s', '%s', '%s', '%s')", TABLE_NAME, 
		       persona.getId(),
		       persona.getPlayerId(),
		       persona.getGamerTag(),
		       persona.getPlatform()
		        );
		super.add(addStatement);
	}

}
