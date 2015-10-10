package a00869363.data;

public class Score {
	String personaId;
	String gameId;
	String winLose;
	
	public Score(String personaId, String gameId, String winLose) {
		super();
		this.personaId = personaId;
		this.gameId = gameId;
		this.winLose = winLose;
	}
	
	public String getPersonaId() {
		return personaId;
	}
	public void setPersonaId(String personaId) {
		this.personaId = personaId;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getWinLose() {
		return winLose;
	}
	public void setWinLose(String winLose) {
		this.winLose = winLose;
	}
	
}
