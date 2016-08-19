package cards;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Represents a card in the game. The cards hold no function of their own and
 * thus can be given
 *
 * @author Jeremy
 *
 */
public class Card {
	
	BufferedImage image;
	private String name;
	private Rectangle rec;

	public Card(String name, BufferedImage  image) {
		this.name = name;
		this.image = image;
	}
	public BufferedImage image(){
		return image;
	}

	/**
	 * Checks if the card has the same name as the other
	 *
	 */
	public boolean equals(Object o) {
		return ((Card) o).name().toLowerCase().equals(name.toLowerCase());

	}
	public void setRectangle(int x, int y, int width, int height){
		rec = new Rectangle(x,y,width,height);
	}
	public Rectangle getRectangle(){
		return rec;
	}

	/**
	 * Returns the name of the card
	 *
	 * @return
	 */
	public String name() {
		return name;
	}

	/**
	 * Returns the name of the card
	 *
	 * @return
	 */
	public String toString(){
		return name;
	}
}
