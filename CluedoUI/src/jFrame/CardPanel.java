package jFrame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import cards.Card;
import cards.CardDeck;
import jComponents.Character;

/**
 * The card panel component of the Game of Cluedo. It displays the current
 * player's hand as well as the cards that were initially displayed from the
 * leftover cards at the distribution from the game setup phases.
 *
 * @author Jeremy & Ryan
 *
 */
public class CardPanel extends JPanel {
	private CardDeck deck;
	private Character player;
	private int Y = 30, width = 120, height = 160;

	/**
	 * Constructor for the card panel. It receives the whole deck of the game
	 * through the parameter
	 *
	 * @param deck
	 */
	public CardPanel(CardDeck deck) {
		this.deck = deck;
	}

	/**
	 * Sets the current player to decide which cards to display
	 *
	 * @param player
	 */
	public void setCurrentPlayer(Character player) {
		this.player = player;
	}

	/**
	 * Sets the size of the panel
	 *
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1000, 210);
	}

	/**
	 * Displays both the player's hand and the cards that were not distributed
	 * to the panel (if there were any)
	 *
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (player != null) {

			int x = 0;
			int val;
			/*
			 * Sets up the location of where the string for 'player's hand'
			 * will be placed
			 */
			if(player.hand().size() == 6)
				val = 2 * width;
			else if(player.hand().size() == 4)
				val = (int) (1.5 * width);
			else
				val = (int) (0.8 * width);

			g.setFont(new Font("TimesRoman", Font.HANGING_BASELINE, 20));
			g.drawString(player.playerName() + "'s (" + player.getName() + ") Hand", val, 20);
			for (Card card : player.hand()) {
				g.drawImage(card.image(), x, Y, width, height, this);
				x += width + 5; // or whatever the card width is
			}

			/*
			 * Determines the for where the 'Known cards' string will be
			 * placed. If there are no known cards, the method will stop here
			 */
			if (deck.leftOverCards().size() == 0)
				return;
			else if(deck.leftOverCards().size() == 2){
				x = player.hand().size() * width + (int)(width * 1.5);
				val = 6 * width;
			}
			else{
				x = (player.hand().size() + 1) * width + width/2;
				val = (int) (5.5 * width);
			}

			g.drawString("Shown Cards", val, 20);
			for (Card card : deck.leftOverCards()) {
				g.drawImage(card.image(), x, Y, width, height, this);
				x += width + 5;
			}

		}

	}

}
