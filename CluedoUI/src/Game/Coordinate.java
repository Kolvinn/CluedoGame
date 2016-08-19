package Game;

public class Coordinate {
	public final int X;
	public final int Y;

	public Coordinate(final int X, final int Y) {
		this.X = X;
		this.Y = Y;
	}

	public boolean equals (final Object O) {
		if (!(O instanceof Coordinate)) return false;
		if (((Coordinate) O).X != X) return false;
		if (((Coordinate) O).Y != Y) return false;
		return true;
	}

	public int hashCode() {
		return (X << 16) + Y;
	}
	public String toString(){
		return "["+X+","+Y+"]";
	}

}

