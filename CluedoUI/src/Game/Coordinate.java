package Game;

/**
 * The coordinate class that stores the x and y values for
 * an object on the Game of Cluedo board
 *
 * @author Jeremy
 *
 */
public class Coordinate {
	public final int X;
	public final int Y;

	/**
	 * Sets up and records the coordinates for an object
	 *
	 * @param X
	 * @param Y
	 */
	public Coordinate(final int X, final int Y) {
		this.X = X;
		this.Y = Y;
	}

	/**
	 * Compares one coordinate to another
	 */
	public boolean equals(final Object O) {
		if (!(O instanceof Coordinate))
			return false;
		if (((Coordinate) O).X != X)
			return false;
		if (((Coordinate) O).Y != Y)
			return false;
		return true;
	}

	/**
	 * Returns the hashcode of the coordinate
	 */
	public int hashCode() {
		return (X << 16) + Y;
	}

	/**
	 * Returns the coordinate as a string
	 */
	public String toString() {
		return "[" + X + "," + Y + "]";
	}

}
