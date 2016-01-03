package Data;

public class Special extends Tile {
	
	private int type;
	
	public Special(int ID, int type){
		this.ID = ID;
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
