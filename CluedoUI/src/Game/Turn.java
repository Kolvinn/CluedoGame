package Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.tools.DocumentationTool.Location;

import Game.GameIndex.Action;
import boardObjects.Player;
import boardObjects.Weapon;
import cards.Card;
import cards.CardDeck;
import cards.InvalidCard;
import locations.Room;
import parser.Parser;

/**
 * The Turn class contains all the possible actions a player may perform on
 * their turn. The actions are divided into enums with move specifically having
 * its own set of enums for the direction of movement.
 *
 *
 * @author zhengzhon
 *
 */
public class Turn {

	private GameIndex game;
	private Player player;
	private CardDeck cards;
	private Board board;
	private Action current;
	private ArrayList<Card> middleCards;
	private Map<String, Player> players;
	private ArrayList<Weapon> weapons;

	/**
	 *
	 * A turn is a single sequence of actions.
	 *
	 * @param game
	 * @param p
	 * @param cardDeck
	 * @param players
	 * @param board
	 * @param middleCards
	 * @param action
	 */
	public Turn(GameIndex game, Player p, CardDeck cardDeck, Map<String, Player> players, Board board,
			ArrayList<Card> middleCards, Action action, ArrayList<Weapon> weapons) {
		this.game = game;
		this.player = p;
		this.cards = cardDeck;
		this.board = board;
		this.players = players;
		this.middleCards = middleCards;
		this.current = action;
		this.weapons = weapons;

	}

	/**
	 * Execute evaluates the current action passed into turn and then performs
	 * it
	 *
	 * @throws InvalidGamePlay
	 */
	public void execute() throws InvalidGamePlay {

		switch (this.current) {
		case PASS:
			pass();
			break;
		case ROLL:
			move(roll());
			break;
		case STAIRS:
			stairs();
			break;
		case SUGGEST:
			suggest();
			break;
		case ACCUSE:
			accuse();
			break;
		default:
			System.err.println("Parsing Error");
			break;

		}
	}

	/**
	 * The player rolls the dice to make their move
	 *
	 * @return
	 */
	public int roll() {
		int maxRoll = (int) (Math.random() * 10 + 3);

		System.out.println(player.boardCode() + " has rolled " + maxRoll);

		return maxRoll; // Changed the max roll
	}

	/**
	 * The move is directly followed by the roll method. It changes the location
	 * of the player based on what is parsed into the system console. It is
	 * limited by the maximum dice roll from the roll method and will throw
	 * exceptions and errors if the player attempts to move their character into
	 * an invalid tile such as a non-entrance wall. It will not throw an error
	 * outside the method but instead catch it and return an error message and
	 * ask the player to re-do their move.
	 *
	 * @param maxRoll
	 */
	public void move(int maxRoll) {

		System.out.println("Determine your route by typing out the direction and steps\n"
				+ "Separate your direction and number of steps with a space, i.e up 2");

		int steps = 0;
		Scanner scan = new Scanner("");
		while (true) {
			System.out.println("You have " + (maxRoll - steps) + " steps remaining\n");
			String move = Parser.inputString();

			// To avoid null errors
			if (!scan.hasNext())
				scan = new Scanner(move);

			while (scan.hasNext()) {
				try {

					String direction = "";
					direction = scan.next();

					int multiplier = 0;
					/*
					 * Due to the board being a cascading line of single-value
					 * coordinates, the multiplier is what determines the
					 * direction of movement for the character on the board
					 */

					boolean toBreak = false;
					switch (direction.toUpperCase()) {

					case "NORTH":
					case "UP":
						multiplier = -24;
						break;

					case "SOUTH":
					case "DOWN":
						multiplier = 24;
						break;

					case "WEST":
					case "LEFT":
						multiplier = -1;
						break;

					case "EAST":
					case "RIGHT":
						multiplier = 1;
						break;

					default:
						System.err.println(direction + " is not a valid direction\n");
						scan = new Scanner("");
						toBreak = true;
						break;

					}

					if (toBreak)
						break;

					int coordinate = 0;
					int tiles = 0;
					int value = 0;

					if (scan.hasNextInt())
						value = Integer.parseInt(scan.next());

					else {
						System.err.println(scan.next() + " is not a valid integer\n");
						scan = new Scanner("");
						break;

					}

					if(value <= 0){
						System.err.println(value + " is not a valid integer\n");
						break;
					}

					steps += value;
					coordinate = value * multiplier;
					tiles = value;

					try {
						boolean enteredRoom = false;
						// When the total amount of entered steps is too high
						if (steps > maxRoll) {
							System.err.println("Too many steps entered\n");
							scan = new Scanner("");
							steps -= value;

							// When there are still steps remaining for the
							// movement
						} else if (steps < maxRoll) {
							enteredRoom = board.move(player, coordinate, tiles);

						} else if (steps == maxRoll) {
							enteredRoom = board.move(player, coordinate, tiles);
							// For when the player has entered a room so that it
							// prints the correct statement
							if (!enteredRoom)
								return;
						}

						if (enteredRoom) {
							System.out.println(player.boardCode() + " has entered "
									+ ((Room) player.getLocation()).name() + "\n" + "Your move has ended");
							return;
						}
					} catch (InvalidGamePlay e) {
						System.err.println("Invalid move detected\n");
						scan = new Scanner("");
						steps -= value;
					}
				} catch (NoSuchElementException e) {
					System.err.println("Please enter a value\n");
					break;
				}
			}

		}
		// End of while loop

	}

	/**
	 * The player makes a suggestion where they select a Character and a Weapon.
	 * If any of the players has a matching Character, Weapon or Room card that
	 * the suggesting player is in, they have to refute the suggestion by
	 * displaying one of their conflicting cards. If they have one or more
	 * conflicting cards, they can select which one to display. Suggestions must
	 * be done in a room
	 *
	 * @throws InvalidGamePlay
	 */
	public void suggest() throws InvalidGamePlay {
		System.out.println(player.boardCode() + " is making an announcement\n\n" + "What is your suggestion?\n"
				+ "Select a Character and a Weapon that you think the murder is related to\n" + "You are in the "
				+ ((Room) player.getLocation()).name());

		System.out.println("Known cards: " + player.getHand().getCards());

		Card character = null;
		Card weapon = null;
		// Room is preset as the room you are currently in.
		Card room = cards.validateCard(((Room) player.getLocation()).name());

		while (true) {
			String suggestion = "";
			try {
				// Set the accused character
				if (character == null) {
					System.out.println("\nEnter the Character you suggest might be the murderer");
					System.out.println("Characters: " + Arrays.asList(CardDeck.Character.values()));
					suggestion = Parser.inputString();
					// Checks if the parsed input is a valid character
					if (cards.isCharacterCard(cards.validateCard(suggestion))) {
						character = cards.validateCard(suggestion);
					} else {
						System.err.println(suggestion + " is not a valid character");
						continue;
					}
				}

				// Set the accused weapon
				if (weapon == null) {
					System.out.println("\nEnter the item you suggest might be the murderer's Weapon");
					System.out.println("Weapons: " + Arrays.asList(CardDeck.Weapon.values()));
					suggestion = Parser.inputString();
					// Checks if the parsed input is a valid weapon
					if (cards.isWeaponCard(cards.validateCard(suggestion))) {
						weapon = cards.validateCard(suggestion);
					} else {
						System.err.println(suggestion + " is not a valid weapon");
						continue;
					}
				}

			} catch (InvalidGamePlay e) {
				System.err.println(suggestion + " is not a valid card");
				continue;
			}

			if (character != null && weapon != null) {
				break;
			}

		}

		// Outside the while loop
		// moving a player to the room if has been called on
		for (Player p : players.values()) {
			if (p.character().toString().equals(character.name()))
				board.updateBoard(p, board.getRoomStorage(((Room) player.getLocation()).name()));
		}
		// moving a weapon to the room if it has been called on
		for (Weapon w : weapons) {
			if (w.equals(weapon.name())) {
				board.updateBoard(w, board.getRoomStorage(((Room) player.getLocation()).name()));
			}
		}

		List<Card> suggestedCards = new ArrayList<Card>();
		suggestedCards.add(weapon);
		suggestedCards.add(character);
		suggestedCards.add(room);

		// Gets every player's hand and compares their cards to the cards that
		// are suggested
		for (Map.Entry<String, Player> entry : players.entrySet()) {
			List<Card> detectedCards = new ArrayList<Card>();
			if (!entry.getValue().equals(player)) {
				for (Card c : entry.getValue().getHand().getCards()) {
					if (suggestedCards.contains(c)) {
						detectedCards.add(c);
					}
				}
			}
			if (!detectedCards.isEmpty()) {
				System.out
						.println(entry.getKey() + " has one or more cards in their hand that matches the suggestion\n");
				Card selectedCard = null;
				// If a player has more than one card in their hand that matches
				// the suggested cards
				if (detectedCards.size() > 1) {
					String clashedCard = "";
					while (true) {
						System.out.println(entry.getKey() + " needs to select one of their clashed cards");
						// System.err.println("Tryna print cards");
						System.out.println("Cards: " + detectedCards);
						try {
							clashedCard = Parser.inputString();
							// Checks if the card the player passed in is
							// actually a valid card and is in their hand
							if (detectedCards.contains(cards.validateCard(clashedCard))) {
								selectedCard = cards.validateCard(clashedCard);
								break;
							} else {
								System.err.println(clashedCard + " is not a clashed card");
								continue;
							}
						} catch (InvalidGamePlay e) {
							System.err.println(clashedCard + " is not a clashed card");
							continue;

						}
					}
				} else
					selectedCard = detectedCards.get(0);

				System.err.println(entry.getKey() + " has the card " + selectedCard + " in their hand\n");

			}
		}

	}
	/*
	 * } }
	 */

	/**
	 * The accusing play. This is an ultimatum where if all 3 accused cards
	 * matches the cards in the middle room, the game ends on the spot and the
	 * player wins. Else the player has made a false accusation and is
	 * immediately removed from the game. Even though they are 'eliminated',
	 * they are still in the game and are required to display conflicting cards.
	 * Accusations can be made at any point of their turn
	 *
	 * @throws InvalidGamePlay
	 */
	public void accuse() throws InvalidGamePlay {

		System.out.println(player.boardCode() + " is making an accusation\n\nWhat is your accusation?\n"
				+ "Select a Character that performed the murder,\nthe Weapon that was employed,\n"
				+ "and a Room that the murder occured in\n\n");

		System.err.println("Once you've made an accusation, you have either won or lost.\n\n");

		Card character = null;
		Card weapon = null;
		Card room = null;

		while (true) {
			String suggestion = "";
			// Select the accused character
			try {
				if (character == null) {
					System.out.println("Enter the Character you suggest might be the murderer");
					System.out.println("Characters: " + Arrays.asList(CardDeck.Character.values()));
					suggestion = Parser.inputString();
					if (cards.isCharacterCard(cards.validateCard(suggestion))) {
						character = cards.validateCard(suggestion);
					} else {
						System.err.println(suggestion + " is not a valid character");
						continue;
					}
				}

				// Select the accused weapon
				if (weapon == null) {
					System.out.println("Enter the item you suggest might be the murderer's Weapon");
					System.out.println("Weapons: " + Arrays.asList(CardDeck.Weapon.values()));
					suggestion = Parser.inputString();
					if (cards.isWeaponCard(cards.validateCard(suggestion))) {
						weapon = cards.validateCard(suggestion);
					} else {
						System.err.println(suggestion + " is not a valid weapon");
						continue;
					}
				}

				// Select the accused room
				if (room == null) {
					System.out.println("Enter the Room you suggest might be where the murder took place");
					System.out.println("Rooms: " + Arrays.asList(CardDeck.Room.values()));
					suggestion = Parser.inputString();
					if (cards.isRoomCard(cards.validateCard(suggestion))) {
						room = cards.validateCard(suggestion);
					} else {
						System.err.println(suggestion + " is not a valid room");
						continue;
					}
				}
			} catch (InvalidGamePlay e) {
				System.err.println(suggestion + " is not a valid card");
				continue;
			}

			if (character != null && weapon != null && room != null) {
				break;
			}

		}

		// Outside the while loop

		List<Card> suggestedCards = new ArrayList<Card>();
		suggestedCards.add(weapon);
		suggestedCards.add(character);
		suggestedCards.add(room);

		boolean victory = true;
		// Determine if the player was correct in their accusation
		for (Card c : suggestedCards)
			if (!middleCards.contains(c))
				victory = false;

		if (victory) {
			System.out.println(player.boardCode() + " has solved the mystery!");
			game.win();
		} else {
			System.out.println(player.boardCode() + " has made an incorrect accusation\n");
			System.err.println(player.boardCode() + " has been eliminated!");

			game.eliminate(player);
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

	}

	/**
	 * Uses the stairs to set their location to another room
	 *
	 * @throws InvalidGamePlay
	 *
	 */
	public void stairs() throws InvalidGamePlay {
		System.out.println(player.boardCode() + " is using the stairs");

		if (((Room) player.getLocation()).name().equals("Kitchen")) {
			board.updateBoard(player, board.getRoomStorage("Study"));
			System.out.println(player.boardCode() + " is now at the Study");

		} else if (((Room) player.getLocation()).name().equals("Conservatory")) {
			board.updateBoard(player, board.getRoomStorage("Lounge"));
			System.out.println(player.boardCode() + " is now at the Lounge");

		} else if (((Room) player.getLocation()).name().equals("Lounge")) {
			board.updateBoard(player, board.getRoomStorage("Conservatory"));
			System.out.println(player.boardCode() + " is now at the Conservatory");

		} else if (((Room) player.getLocation()).name().equals("Study")) {
			board.updateBoard(player, board.getRoomStorage("Kitchen"));
			System.out.println(player.boardCode() + " is now at the Kitchen");

		} else
			throw new InvalidGamePlay(player.boardCode() + " is not in a room with stairs");

	}

	/**
	 * Essentially "end turn". Can be used at any point during your turn except
	 * in mid-action.
	 */
	public void pass() {
		System.out.println(player.boardCode() + " ends his turn");
		// That is all.

	}
}
