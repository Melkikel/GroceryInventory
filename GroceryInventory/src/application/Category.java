package application;

public class Category {

	private int ID;
	private String Name;

	public Category(int ID, String Name) {
		this.ID = ID;
		this.Name = Name;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}
