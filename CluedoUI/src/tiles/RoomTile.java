package tiles;

/**
 * The room tile object. It is a subclass of the Tile object
 *
 * @author Jeremy
 *
 */
public class RoomTile extends Tile {
	String roomName;
	boolean isEntrance;

	/**
	 * Constructs a room tile based on whether or not it can be accessed
	 * normally, the name of the room and whether or not the specific room
	 * tile is an entrance to the room
	 *
	 * @param isImpassable
	 * @param roomName
	 * @param isEntrance
	 */
	public RoomTile(boolean isImpassable, String roomName, boolean isEntrance) {
		super(isImpassable);
		this.roomName = roomName;
		this.isEntrance = isEntrance;

	}

	/**
	 * Returns whether or not the room tile is an entrance
	 *
	 * @return
	 */
	public boolean isEntrance() {
		return isEntrance;
	}

	/**
	 * Returns the room that the tile is located at
	 *
	 * @return
	 */
	public String roomName() {
		return roomName;
	}

	/**
	 * Returns whether or not the room tile's room contains stairs
	 *
	 * @return
	 */
	public boolean hasStairs() {
		return roomName.equals("Kitchen") || roomName.equals("Study") || roomName.equals("Conservatory")
				|| roomName.equals("Lounge");
	}
}
