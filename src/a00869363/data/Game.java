package a00869363.data;

public class Game {
	String id;
	String name;
	String producer;
	public Game(String id, String name, String producer) {
		super();
		this.id = id;
		this.name = name;
		this.producer = producer;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	
	
}
