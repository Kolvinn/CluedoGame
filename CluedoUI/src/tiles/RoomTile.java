package tiles;

public class RoomTile extends Tile {
	String roomName;
	boolean isEntrance;
	public RoomTile(boolean isImpassable, String roomName, boolean isEntrance) {
		super(isImpassable);
		this.roomName=roomName;
		this.isEntrance=isEntrance;

	}	
	public boolean isEntrance(){
		return isEntrance;
	}
	public String roomName(){
		return roomName;
	}
	public boolean hasStairs(){
		return roomName.equals("Kitchen") || roomName.equals("Study")
				||roomName.equals("Conservatory") || roomName.equals("Lounge");
	}
}
