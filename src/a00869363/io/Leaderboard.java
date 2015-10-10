package a00869363.io;

public class Leaderboard {
	int wins;
	int losses;
	String gameName;
	String gamerTag;
	String platform;
	public Leaderboard(int wins, int losses, String gameName, String gamerTag,
			String platform) {
		super();
		this.wins = wins;
		this.losses = losses;
		this.gameName = gameName;
		this.gamerTag = gamerTag;
		this.platform = platform;
	}
	
	public int getWins() {
		return wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public String getGameName() {
		return gameName;
	}
	public String getGamerTag() {
		return gamerTag;
	}
	public String getPlatform() {
		return platform;
	}
	
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public void setGamerTag(String gamerTag) {
		this.gamerTag = gamerTag;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	@Override
	public String toString() {
		return "Leaderboard [wins=" + wins + " losses=" + losses +", gameName=" + gameName
				+ ", gamerTag=" + gamerTag + ", platform=" + platform + "]";
	}
	
}
