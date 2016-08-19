package jComponents;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Game.Coordinate;
import cards.Card;

/**
 * The individual charcter class. It is assigned a sprite on the board,
 * coordinates on the board and the name of the character itself.
 *
 * @author Jeremy
 *
 */
public class Character extends BoardObject {
	boolean isPlayer = false;
	ArrayList<Card> hand;
	private String playerName;

	/**
	 * Constructs the character object
	 *
	 * @param image
	 * @param coordinate
	 * @param name
	 */
	public Character(BufferedImage image, Coordinate coordinate, String name) {
		super(image, coordinate, name);
	}

	/**
	 * Returns the name of the player assigned to the character
	 *
	 * @return
	 */
	public String playerName() {
		return playerName;
	}

	/**
	 * Sets the player's name to the assigned character
	 *
	 * @param playerName
	 */
	public void setPlayerName(String playerName) {
		if (isPlayer())
			this.playerName = playerName;
	}

	/**
	 * Returns a string that composes of the player and character information
	 * depending on whether the character is player controlled
	 *
	 */
	public String getToolTipText() {
		if (isPlayer)
			return "Player\n" + "Character: " + name + "\nName: " + playerName;
		return "Character\n" + name;
	}

	/**
	 * Sets the character as a player character
	 *
	 */
	public void setAsPlayer() {
		isPlayer = true;
	}

	/**
	 * Returns whether or not the character is controller by a player
	 *
	 * @return
	 */
	public boolean isPlayer() {
		return isPlayer;
	}

	/**
	 * Returns the character's hand
	 *
	 * @return
	 */
	public ArrayList<Card> hand() {
		return hand;
	}

	/**
	 * Sets the hand of the character
	 *
	 * @param hand
	 * @return
	 */
	public boolean createHand(ArrayList<Card> hand) {
		this.hand = hand;
		return true;
	}

	/**
	 * Compares the name of the character to an object
	 *
	 */
	public boolean equals(Object o) {
		String n = "";
		if(o instanceof BoardObject)
			n = ((BoardObject) o).getName();
		return n.equals(name);
	}

}
