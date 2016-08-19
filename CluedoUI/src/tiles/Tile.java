package tiles;

import jComponents.BoardObject;
/**
 * The individual tiles on the Game of Cluedo
 *
 * @author Jeremy
 *
 */
public class Tile {
	boolean isImpassable;
	BoardObject object;

	/**
	 * Creates a tile object and sets a boolean for whether or not it is
	 * normally accessible through player movement
	 *
	 * @param isImpassable
	 */
	public Tile(boolean isImpassable) {
		this.isImpassable = isImpassable;
	}

	/**
	 * Returns whether or not a tile is impassable
	 *
	 * @return
	 */
	public boolean isImpassable() {
		return isImpassable;
	}

	/**
	 * Sets an object on the tile
	 *
	 * @param object
	 */
	public void setBoardObject(BoardObject object) {
		this.object = object;
	}

	/**
	 * Returns whether or not the tile contains an object on it
	 *
	 * @return
	 */
	public boolean containsObject() {
		if (this.object != null)
			return true;
		return false;
	}

	/**
	 * Returns the tile's status
	 *
	 */
	public String toString() {
		if (isImpassable)
			return "Impassable tile";
		else
			return "Hallway";
	}
}
