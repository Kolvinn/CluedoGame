package Game;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cards.Card;
import jFrame.CFrame;

import jComponents.Character;

/**
 * The class that handles all the game's executions and logic depending on the
 * user input though the GUI.
 *
 * @author zhengzhon and Jeremy
 *
 */
public class GameRunner implements KeyListener {

	/**
	 * The potential states of a running game
	 */
	public enum GameState {
		ONGOING, FINISHED

	}

	private String[] actions = { "Pass", "Roll", "Suggest", "Accuse", "Stairs" };
	private GameState gameState = GameState.ONGOING;
	private String lastAction=null;
	private int moveCount;

	private GameIndex index;
	private Board board;
	private CFrame frame;
	private Character  currentPlayer;
	private boolean completedMove= false;
	private boolean hasActions = true;

	/**
	 * The GameRunner constructor takes in a board, the Cluedo window frame and
	 * the game index status. It also runs the game itself after setting up the fields.
	 *
	 * @param board
	 * @param frame
	 * @param index
	 */
	public GameRunner(Board board, CFrame frame, GameIndex index){
		this.board = board;
		this.frame=frame;
		this.index=index;
		frame.addKeyListener(this);
		frame.setFocusable(true);


		playGame();

	}

	/**
	 * Runs the game. Ends when all players have been eliminated
	 * or the murderer has been correctly identified
	 *
	 */
	private void playGame() {

			for(Character player: index.players()){
				//start of players turn
				Set<String> playerActions = board.availableActions(player, lastAction);
				while(hasActions){
					frame.getSidePanel().getText().setText("\n\n***"+player.playerName()+"'s turn!***\n\nPlease Select an Action Button\n");
					//deals the hand for the current player
					frame.getCardPanel().setCurrentPlayer(player);
					frame.repaint();

					currentPlayer = player;
					//get button and perform action
					String buttonPressed = frame.getSidePanel().getButtons().getButton("actions", playerActions);
					lastAction= buttonPressed;
					executeAction(buttonPressed, player);

					frame.getSidePanel().getText().setText(player.playerName()+" successfully completed "+lastAction+"\n");
				}
				frame.getSidePanel().getText().setText("\n"+player.playerName()+"'s turn is over\n");
			}
	}

	/**
	 * Executes the selected action for the player
	 *
	 * @param action
	 * @param player
	 */
	private void executeAction(String action, Character player){
		lastAction = action;
		System.out.println(lastAction);
		switch (action){
		case("Roll"):
			prepareMove();
			System.out.println("Moving");
			while(!completedMove){
				sleep(100);
			}
			break;
		case "Pass":
			hasActions =false;
			break;
		case "Stairs":
			UseStairs(player);
			hasActions=false;
			break;
		case "Suggest":
			suggest(player);
			break;
		case "Accuse":
			accuse(player);
			break;
		}
		System.out.println("End sequence");
	}

	/**
	 * Performs the use stairs action on the specified player
	 *
	 * @param player
	 */
	private void UseStairs(Character player){
		player.setCoordinate(board.getPassageCoordiantes(player));
	}

	/**
	 * The player makes a suggestion where they select a Character and a Weapon.
	 * If any of the players has a matching Character, Weapon or Room card that
	 * the suggesting player is in, they have to refute the suggestion by
	 * displaying one of their conflicting cards. If they have one or more
	 * conflicting cards, they can select which one to display. Suggestions must
	 * be done in a room
	 *
	 */
	public void suggest(Character player) {
		Set<String> selectedTokens = new HashSet<String>();

		// Allow the player to determine their suggestion
		while (selectedTokens.size() < 6) {
			selectedTokens.add("Room");
			selectedTokens.add(board.roomName(player.getX(), player.getY()));
			String buttonPressed = frame.getSidePanel().getButtons().getButton("tokens", selectedTokens);

			System.out.println(buttonPressed);
			selectedTokens.add(buttonPressed);

			if (buttonPressed.equals("Character"))
				buttonPressed = frame.getSidePanel().getButtons().getButton("characters", selectedTokens);
			else if (buttonPressed.equals("Weapon"))
				buttonPressed = frame.getSidePanel().getButtons().getButton("weapons", selectedTokens);
			else
				System.err.println("Error encountered");

			System.out.println(buttonPressed);
			selectedTokens.add(buttonPressed);
			frame.getSidePanel().getText().setText("Suggesting: " + buttonPressed + "\n");
		}

		frame.getSidePanel().getText().setText("Suggestion has been made\n");

		/*
		 * Allow other players to refute the player's suggestion based on the
		 * conflicting cards in their hand (if they do conflict)
		 */
		Set<String> refuted = new HashSet<String>();
		Map<Character, Set<String>> refuteCards = new HashMap<Character, Set<String>>();
		for (Character pc : index.players()) {
			for (Card card : pc.hand())
				if (selectedTokens.contains(card.name())) {
					refuted.add(card.name());
					refuteCards.put(pc, refuted);
				}
			refuted.clear();
		}

		if (!refuteCards.isEmpty()) {
			String refutedCard = null;
			for (Map.Entry<Character, Set<String>> entry : refuteCards.entrySet()) {
				if (entry.getValue().size() == 1)
					refutedCard = (String) entry.getValue().toArray()[0];
				else
					for (String card : entry.getValue()) {
						/*
						 * The player has more than one refuted card so they
						 * must choose which card they will want to show
						 */
					}
			}

		}

	}

	/**
	 * The accusing play. This is an ultimatum where if all 3 accused cards
	 * matches the cards in the middle room, the game ends on the spot and the
	 * player wins. Else the player has made a false accusation and is
	 * immediately removed from the game. Even though they are 'eliminated',
	 * they are still in the game and are required to display conflicting cards.
	 * Accusations can be made at any point of their turn
	 *
	 */
	public void accuse(Character player) {
		Set<String> selectedTokens = new HashSet<String>();

		// Allow the player to determine their suggestion
		while (selectedTokens.size() < 6) {
			String buttonPressed = frame.getSidePanel().getButtons().getButton("tokens", selectedTokens);

			System.out.println(buttonPressed);
			selectedTokens.add(buttonPressed);
			if (buttonPressed.equals("Character")) {
				buttonPressed = frame.getSidePanel().getButtons().getButton("characters", selectedTokens);

			} else if (buttonPressed.equals("Room")) {
				buttonPressed = frame.getSidePanel().getButtons().getButton("rooms", selectedTokens);

			} else if (buttonPressed.equals("Weapon")) {
				buttonPressed = frame.getSidePanel().getButtons().getButton("weapons", selectedTokens);

			} else {
				System.err.println("Error encountered");
			}
			System.out.println(buttonPressed);
			selectedTokens.add(buttonPressed);
			frame.getSidePanel().getText().setText("Suggestion: " + buttonPressed + "\n");
		}

		// Determines whether or not the accusation was correct
		boolean victory = true;
		Set<String> middleCards = new HashSet<String>();
		for(Card card : index.getMiddleCards())
			middleCards.add(card.name());

		if(!selectedTokens.containsAll(middleCards))
			victory = false;

		if(victory)
			win();
		else
			index.eliminatePlayer(player);

	}

	/**
	 * Prepares the move for the player by rolling two die
	 *
	 */

	private void prepareMove(){
		moveCount = (int) (Math.random() * 10 + 3);
		frame.getSidePanel().getText().setText(currentPlayer.playerName()+" has rolled a "+moveCount+"\n\nPlease use the directional pad \nto move to your desired square");
	}

	/**
	 * Sets the game condition as "Finished". Is called by a successful accuse move
	 *
	 */
	public void win(){
		gameState = GameState.FINISHED;
	}


	@Override
	public void keyPressed(KeyEvent e){

		if(lastAction=="Roll" && moveCount!=0){
			System.out.println("is inside key pressed, moveCOunt: "+moveCount);
			int code = e.getKeyCode();
			int x=0,y=0;
			if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
				x = 1;
			}
			else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
				x = -1;
			}
			else if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_UP) {
				y=1;
			}
			else if(code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
				y=-1;
			}
			//move was successful
			moveCount = board.movePlayer(x,y, currentPlayer, moveCount);
			if(moveCount==0){
				completedMove=true;
				System.out.println("Move completed");
			}
			frame.getSidePanel().getText().setText(moveCount+" moves left\n");
			System.out.println(moveCount);
			frame.repaint();

		}

	}
	@Override
	public void keyReleased(KeyEvent arg0) {
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	/**
	 * To allow the while loops to not automatically time out
	 *
	 * @param mils
	 */
	private void sleep(int mils) {
		try {
			Thread.sleep(mils);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

