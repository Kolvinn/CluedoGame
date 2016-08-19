package tiles;

import jComponents.BoardObject;

public class Tile {
	boolean isImpassable;
	BoardObject object;
	public Tile(boolean isImpassable){
		this.isImpassable=isImpassable;
	}
	public boolean isImpassable(){
		return isImpassable;
	}
	public void setBoardObject(BoardObject object){
		this.object= object;
	}
	public boolean containsObject(){
		if(this.object!=null)
			return true;
		return false;
	}
	public String toString(){
		if(isImpassable)
			return "Impassable tile";
		else
			return "Hallway";
	}
}
