package Game;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;


import cards.*;
import jComponents.BoardObject;
import jComponents.Character;
import jFrame.CFrame;


/**
 * The main class for the Cluedo text-based game. It holds all the active and eliminated players, the cards in the
 * middle room, the weapon tokens, last successfully executed action, board and card deck. It also holds enums for
 * each executable action and the game state. The majority of the pre-game preparation for the game resides within
 * this class.
 *
 * @author zhengzhon & Jeremy
 *
 */
public class GameIndex {

	/**
	 * The potential states of a running game
	 *
	 * @author zhengzhon
	 *
	 */
	public enum GameState {
		ONGOING, FINISHED

	}

	private GameState state =GameState.ONGOING;
	private Set<BoardObject> boardObjects;
	private CardDeck deck;
	private Set<Card> middleCards;
	/**
	 * Setting up the game and then running it.
	 *
	 */
	public GameIndex() {
		boardObjects= populateBoard();
		deck = new CardDeck();
		middleCards= deck.setMiddleCards();

	}

	public CardDeck cardDeck(){
		return deck;
	}

	public Set<Card> getMiddleCards(){
		return middleCards;
	}

	public boolean hasEnded(){
		return state == GameState.FINISHED;
	}

	/**
	 * Prints out the rules for Cluedo and then explains what the ascii board is displaying.
	 * It will also ask for the number of players that will be participating.
	 *
	 */
	public void createStartingPlayers(CFrame frame) {
		Set<String> passableActions = new HashSet<String>();
		int playerNumber = Integer.parseInt(frame.getSidePanel().getButtons().getButton("start", null));
		String name ="", charr;
		Map<String, String> pc = new HashMap<String, String>();
		
		frame.getSidePanel().getText().setText("Please select a character\nand enter your name\n");
		for(int i =0;i<playerNumber;i++){
			String test = frame.getSidePanel().getButtons().getButton("characterSelect", passableActions);

			String Playercharacter[] = test.split(",");
			name= Playercharacter[0];
			charr= Playercharacter[1];
			passableActions.add(charr);
			
			pc.put(charr, name);

			System.out.println("Output: " + name+" : "+charr);
		}
		
		for(BoardObject object: boardObjects){
			System.out.println(object.getName() +"    "+passableActions);
			if(object instanceof Character && passableActions.contains(object.getName())){
				System.out.println(object.getName());
				((Character) object).setAsPlayer();
				((Character) object).setPlayerName(pc.get(object.getName()));
			}
		}
		
		deck.deal(players(), players().size());

		//Testing allocation
		for(Character c: players())
			System.out.println(c.getName() + ": " + c.playerName());
	}

	/**
	 * Returns an image based on the given filename String
	 * Throws and catches an IOException if no valid file is found
	 * @param fileName
	 * @return
	 */
	public BufferedImage getImage(String fileName){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(fileName);
		BufferedImage image = null;
		try {
			image = ImageIO.read(input);
		} catch (IOException e1) {
			System.out.println("invalid image file name");
		}
		//System.out.println(image);
		return image;
	}


	private Set<BoardObject> populateBoard(){
		Set<BoardObject> ob = new HashSet<BoardObject>();
		ob.addAll(createCharacters());
		ob.addAll(createWeapons());
		return ob;
	}
	/**
	 * Creates all the characters Objects in the game
	 * @return
	 */
	private Set<BoardObject> createCharacters(){
		Set<BoardObject> charrs = new HashSet<BoardObject>();
		charrs.add(new Character(getImage("xboardObjects/missscarlettCut.png"),new Coordinate(23,19),"Miss Scarlett"));
		charrs.add(new Character(getImage("xboardObjects/colonelmustardCut.png"),new Coordinate(0,17),"Colonel Mustard"));
		charrs.add(new Character(getImage("xboardObjects/mrswhiteJ2.png"),new Coordinate(14,0),"Mrs White"));
		charrs.add(new Character(getImage("xboardObjects/revgreenCut.png"),new Coordinate(9,0),"Reverend Green"));
		charrs.add(new Character(getImage("xboardObjects/mrspeacockCut.png"),new Coordinate(23,6),"Mrs Peacock"));
		charrs.add(new Character(getImage("xboardObjects/proffessorplumCut.png"),new Coordinate(7,24),"Professor Plum"));
		return charrs;
	}
	/**
	 * Creates Weapons in the games and stores them in rooms so they may be moved around and called upon
	 * @param weapon
	 */
	private Set<BoardObject> createWeapons(){
		Set<BoardObject> weaps = new HashSet<BoardObject>();

		weaps.add(new BoardObject(getImage("xboardObjects/revolver1.png"), new Coordinate(3,12), "Revolver"));
		weaps.add(new BoardObject(getImage("xboardObjects/candlestick3.png"), new Coordinate(2,3), "Candlestick"));
		weaps.add(new BoardObject(getImage("xboardObjects/knife1.png"), new Coordinate(12,3), "Knife"));
		weaps.add(new BoardObject(getImage("xboardObjects/leadpipe1.png"), new Coordinate(20,3), "LeadPipe"));
		weaps.add(new BoardObject(getImage("xboardObjects/rope1.png"), new Coordinate(20,10), "Rope"));
		weaps.add(new BoardObject(getImage("xboardObjects/spanner1.png"), new Coordinate(20,16), "Spanner"));
		return weaps;
	}
	public Set<BoardObject> boardObjects(){
		return boardObjects;
	}

	/**
	 * Returns a Set of characters which have been set as players in the game
	 * @return
	 */
	public Set<Character> players(){
		Set<Character> players = new HashSet<Character>();
		for(Character character: characters()){
			if(character.isPlayer())
				players.add(character);
		}
		return players;
	}
	/**
	 * Returns a Set of all characters in the game
	 * @return
	 */
	public Set<Character> characters(){
		Set<Character> characters = new HashSet<Character>();
		for(BoardObject object: boardObjects){
			if(object instanceof Character)
				characters.add((Character) object);
		}
		return characters;
	}

	/**
	 * Eliminates the player from the game from a false accusation
	 *
	 * @param player
	 */
	public void eliminatePlayer(Character player){
		boardObjects.remove(player);
		System.err.println(player.getName() + " was eliminated");
	}

}
