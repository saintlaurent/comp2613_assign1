package a00869363.data;

import a00869363.io.PlayerFormat;

public class Player {

	private String id;
	private String fName;
	private String lName;
	private String email;
	private String gamerTag;
	private String birthdate;
	private int age;

	/**
	 * @param id
	 * @param fName
	 * @param lName
	 * @param email
	 * @param gamerTag
	 */
	public Player(String id, String fName, String lName, String email,
			String birthdate) {
		super();
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.birthdate = birthdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGamerTag() {
		return gamerTag;
	}

	public void setGamerTag(String gamerTag) {
		this.gamerTag = gamerTag;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public int getAge() {
		return age;
	}

	public void setAge(String birthdate) {		
		this.age = PlayerFormat.calculateAge(birthdate);
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", fName=" + fName + ", lName=" + lName
				+ ", gamerTag=" + gamerTag + ", birthdate=" + birthdate + "]";
	}

}
