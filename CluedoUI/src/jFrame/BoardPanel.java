package jFrame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JPanel;

import jComponents.BoardObject;

/**
 * The java panel that holds the image and grid of the board.
 *
 * @author Jeremy
 *
 */
public class BoardPanel extends JPanel {
	private Image image;
	Set<BoardObject> objects;

	/**
	 * Construct and build the board. It sets up the board's background
	 * as well as the objects that will be on the board itself
	 *
	 * @param image
	 * @param objects
	 */
	public BoardPanel(Image image, Set<BoardObject> objects) {
		this.image = image;
		this.objects = objects;
	}

	/**
	 * Sets the dimensions of the board
	 *
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(720, 750);
	}

	/**
	 * Paints the board and the objects on it
	 *
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
		for (BoardObject object : objects) {
			object.setToolTipText(object.getToolTipText());
			object.paint(g);
		}

	}

	}



