package cards;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import cards.Card.*;

/**
 * The CardDeck class holds all the cards in the game - A total of 6 Character cards,
 * 6 Weapon cards and 9 Room cards. It primarilly deals with creating, shuffling and
 * dealing out cards
 *
 * @author zhengzhon & Jeremy
 *
 */
public class CardDeck {
	/**
	 * All the Room Cards in the game
	 *
	 * @author Jeremy
	 */
	public enum Room {
		BallRoom, BilliardRoom, Conservatory, DiningRoom, Kitchen, Hall, Study, Library, Lounge,
	}

	/**
	 * All the Character Cards in the game
	 *
	 * @author Jeremy
	 */
	public enum Character {
		MissScarlett, ColonelMustard, MrsWhite, ReverendGreen, MrsPeacock, ProfessorPlum
	}

	/**
	 * All the Weapon Cards in the game
	 *
	 * @author Jeremy
	 */
	public enum Weapon {
		Candlestick, Knife, LeadPipe, Revolver, Rope, Wrench
	}

	private ArrayList<Card> weapons = new ArrayList<Card>(), rooms = new ArrayList<Card>(),
			characters = new ArrayList<Card>(), fullDeck = new ArrayList<Card>(), gameDeck;
	private ArrayList<Card> shownCards = new ArrayList<Card>();

	public CardDeck() {
		this.fullDeck = createCards();
		Collections.shuffle(fullDeck);
		gameDeck = (ArrayList<Card>) fullDeck.clone();

	}

	/**
	 * Checks if the card is within the currently used game cards
	 *
	 * @param c
	 * @return
	 */
	public boolean contains(Card c) {
		return gameDeck.contains(c);
	}

	/**
	 * Checks if the card belongs to the set of Room cards
	 *
	 * @param c
	 * @return
	 */
	public boolean isRoomCard(Card c) {
		return rooms.contains(c);
	}

	/**
	 * Checks if the card belongs to the set of Character cards
	 *
	 * @param c
	 * @return
	 */
	public boolean isCharacterCard(Card c) {
		return characters.contains(c);
	}

	/**
	 * Checks if the card belongs to the set of Weapon cards
	 *
	 * @param c
	 * @return
	 */
	public boolean isWeaponCard(Card c) {
		return weapons.contains(c);
	}

	/**
	 * Cards to be shown to everyone if there are any left over after dealing
	 * @return
	 */
	public ArrayList<Card> leftOverCards(){
		return shownCards;
	}

	/**
	 * Creates the hand of cards from the gameDeck for each each given player
	 *
	 * @param players
	 */
	public void deal(Set<jComponents.Character> players, int playerNum){
		int cardNumber = 18/playerNum;
		ArrayList<Card> temp = new ArrayList<Card>();
		for(jComponents.Character player: players){
			System.out.println(cardNumber);
			System.out.println(gameDeck);
			for(int i =0;i< cardNumber;i++){
				temp.add(gameDeck.remove(0));
			}
			System.out.println(temp);
			player.createHand(temp);
			System.out.println(player.hand());
			temp = new ArrayList<Card>();
			gameDeck.trimToSize();
		}
		if(gameDeck!=null && gameDeck.size()!=0)
			shownCards.addAll(gameDeck);

	}

	/**
	 * Sets "victory cards" for the game of Cluedo. These cards are the "mystery" cards
	 * that will win the game for the player
	 *
	 * @return
	 */
	public Set<Card> setMiddleCards() {
		Set<Card> temp = new HashSet<Card>();
		Collections.shuffle(rooms);
		temp.add(rooms.get(0));
		Collections.shuffle(characters);
		temp.add(characters.get(0));
		Collections.shuffle(weapons);
		temp.add(weapons.get(0));
		gameDeck.removeAll(temp);
		return temp;
	}


	/**
	 * Creates all the cards for the game
	 *
	 * @return
	 */
	private ArrayList<Card> createCards() {
		ArrayList<Card> temp = new ArrayList<Card>();
		createRoomCards();
		createWeaponCards();
		createCharacterCards();

		// now add all cards to create a fullDeck
		temp.addAll(rooms);
		temp.addAll(weapons);
		temp.addAll(characters);
		return temp;
	}

	public BufferedImage getImage(String fileName){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(fileName);
		BufferedImage image = null;
		//System.out.println(fileName);
		try {
			image = ImageIO.read(input);
		} catch (IOException e1) {
			System.out.println("invalid image file name");
		}
		//System.out.println(image);
		return image;
	}

	/**
	 * Adds a new card object to this.rooms for every value in the Room enum
	 */
	private void createRoomCards() {
		for (Room r : Room.values()){
			System.out.println(("xroomCards/"+r.name()+".jpg").toLowerCase());
			rooms.add(new Card(r.name(), getImage(("xroomCards/"+r.name()+".jpg").toLowerCase())));
			}
		}

	/**
	 * Adds a new card object to this.weapons for every value in the Weapon enum
	 */
	private void createWeaponCards() {
		for (Weapon w : Weapon.values())
			weapons.add(new Card(w.name(), getImage(("xweaponCards/"+w.name()+".jpg").toLowerCase())));
	}

	/**
	 * Adds a new card object to this.characters for every value in the
	 * Character enum
	 */
	private void createCharacterCards() {
		for (Character c : Character.values())
			characters.add(new Card(c.name(), getImage((
					"xcharacterCards/"+c.name()+".jpg").toLowerCase())));
	}

}
