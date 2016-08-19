package jComponents;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import Game.Coordinate;

/**
 * The board Object superclass that is extened by all the entities on the cluedo
 * game board
 *
 * @author Jeremy
 *
 */
public class BoardObject extends JComponent {
	protected Image image;
	protected int width = 26, height = 26;
	protected Coordinate coordinate;
	protected String name;

	/**
	 * The constructor for board objects. It records an entity's image, coordinates
	 * and their name
	 *
	 * @param image
	 * @param coordinate
	 * @param name
	 */
	public BoardObject(BufferedImage image, Coordinate coordinate, String name) {
		this.image = image;
		this.coordinate = coordinate;
		this.name = name;
	}

	/**
	 * Draws the object using the coordinate values
	 *
	 */
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		int x = (coordinate.X * 30) + 2;
		int y = (coordinate.Y * 30) + 2;
		g.drawImage(image, x, y, width, height, this);

	}

	/**
	 * Returns the name of the object
	 *
	 */
	public String getName() {
		return name;
	}

	/**
	 * Compares the object's name with another
	 *
	 */
	public boolean equals(Object o) {
		String n = "";
		if(o instanceof Component)
		n = (String) ((Component) o).getName();
		return n.equals(name);
	}

	/**
	 * Returns the item's hover-over tooltip value.
	 * It's only used for weapons
	 *
	 */
	public String getToolTipText() {
		return "Weapon\n" + name;
	}

	/**
	 * Sets the coordinate value of the object
	 *
	 * @param coo
	 */
	public void setCoordinate(Coordinate coo) {
		this.coordinate = coo;
	}

	/**
	 * Returns the board's width
	 *
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * Return the board's height
	 *
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the x coordinate of the object
	 *
	 */
	@Override
	public int getX() {
		return coordinate.X;
	}

	/**
	 * Returns the y coordinate of the object
	 *
	 */
	@Override
	public int getY() {
		return coordinate.Y;
	}
}
