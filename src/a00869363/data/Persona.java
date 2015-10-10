package a00869363.data;

import java.util.List;

public class Persona {
	String id;
	String playerId;
	String gamerTag;
	String platform;
	static List<Persona> listOfPersonas;
	public Persona(String id, String playerId, String gamerTag, String platform) {
		super();
		this.id = id;
		this.playerId = playerId;
		this.gamerTag = gamerTag;
		this.platform = platform;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public String getGamerTag() {
		return gamerTag;
	}
	public void setGamerTag(String gamerTag) {
		this.gamerTag = gamerTag;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", playerId=" + playerId + ", gamerTag="
				+ gamerTag + ", platform=" + platform + "]";
	}
	
}
