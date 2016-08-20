package Game;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import cards.Card;
import jFrame.CFrame;
import main.Main;
import jComponents.Character;

/**
 * The class that handles all the game's executions and logic depending on the
 * user input though the GUI.
 *
 * @author zhengzhon and Jeremy
 *
 */
public class GameRunner implements KeyListener, MouseListener {

	/**
	 * The potential states of a running game
	 */
	public enum GameState {
		ONGOING, FINISHED

	}

	private String[] actions = { "Pass", "Roll", "Suggest", "Accuse", "Stairs" };
	private GameState gameState = GameState.ONGOING;
	private String lastAction = null;
	private int moveCount;

	private GameIndex index;
	private Board board;
	private CFrame frame;
	private Character currentPlayer;
	private boolean completedMove = false, selectedCard =false;
	private boolean hasActions = true;

	/**
	 * The GameRunner constructor takes in a board, the Cluedo window frame and
	 * the game index status. It also runs the game itself after setting up the
	 * fields.
	 *
	 * @param board
	 * @param frame
	 * @param index
	 */
	public GameRunner(Board board, CFrame frame, GameIndex index) {
		this.board = board;
		this.frame = frame;
		this.index = index;
		frame.addKeyListener(this);
		frame.setFocusable(true);
		frame.addMouseListener(this);

		playGame();

	}

	/**
	 * Runs the game. Ends when all players have been eliminated or the murderer
	 * has been correctly identified
	 *
	 */
	private void playGame() {

		while (!index.hasEnded())
			for (Character player : index.players()) {
				currentPlayer = player;
				// start of players turn
				Set<String> playerActions;
				while (hasActions) {
					playerActions = board.availableActions(currentPlayer, lastAction);
					frame.getSidePanel().getText().setText("\n\n***" + currentPlayer.playerName() + "'s turn!***\n\nPlease Select an Action Button\n");
					// deals the hand for the current player
					frame.getCardPanel().setCurrentPlayer(currentPlayer);
					frame.repaint();

					// get button and perform action
					String buttonPressed = frame.getSidePanel().getButtons().getButton("actions", playerActions);
					lastAction = buttonPressed;
					executeAction(buttonPressed, currentPlayer);

				}
				frame.getSidePanel().getText().setText("\n" + currentPlayer.playerName() + "'s turn is over\n");
				lastAction = null;
				hasActions = true;
				System.out.println("here");
			}

		frame.getSidePanel().getText().setText("\nEND GAME\n");
	}
	

	/**
	 * Executes the selected action for the player
	 *
	 * @param action
	 * @param player
	 */
	private void executeAction(String action, Character player) {
		lastAction = action;
		System.out.println(lastAction);
		switch (action) {
		case ("Roll"):
			prepareMove();
			System.out.println("Moving");
			while (!completedMove) {
				sleep(100);
			}
			completedMove = false;
			break;
		case "Pass":
			hasActions = false;
			break;
		case "Stairs":
			UseStairs(player);
			hasActions = false;
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
	private void UseStairs(Character player) {
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
		System.out.println("suggested Cards: "+selectedTokens);

		/*
		 * Allow other players to refute the player's suggestion based on the
		 * conflicting cards in their hand (if they do conflict)
		 */
		Set<Card> refuted = new HashSet<Card>();
		Map<Character, Set<Card>> refuteCards = new HashMap<Character, Set<Card>>();
		for (Character pc : index.players()) {
			//checking for all player apart from current
			if(!pc.equals(currentPlayer)){
				System.out.println("player: "+pc.getName()+",   hand: "+pc.hand());
				for (Card card : pc.hand()){
					if (selectedTokens.contains(card.name())) {
						System.out.println("THE SELECTED CARDS  : "+card);
						refuted.add(card);   //getting all cards that match selected suggest cards
						refuteCards.put(pc, refuted);
					}
				}
				refuted= new HashSet<Card>();
			}
		}

		if (!refuteCards.isEmpty()) {
			for (Map.Entry<Character, Set<Card>> entry : refuteCards.entrySet()) {
				frame.getCardPanel().setCurrentPlayer(entry.getKey());
				frame.getCardPanel().setSuggestCards(entry.getValue());
				frame.getSidePanel().getText().setText("\nPlease pick a card to show");
				frame.repaint();
				//while the player has not selected the card
				while(!selectedCard){
					sleep(100);
				}
				frame.repaint();
				sleep(5000);
				selectedCard=false;		
				frame.getCardPanel().hasSuggested(false);
						
			}
			frame.getCardPanel().setSuggestCards(null);		
		}
		else{

			frame.getSidePanel().getText().setText("\n\n***No cards to show!!***\n\n");
			
		}
		frame.repaint();

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
			frame.getSidePanel().getText().setText("Accusation: " + buttonPressed + "\n");
		}
		

		// Determines whether or not the accusation was correct
		boolean victory = true;
		Set<String> middleCards = new HashSet<String>();
		for (Card card : index.getMiddleCards())
			middleCards.add(card.name());

		if (!selectedTokens.containsAll(middleCards))
			victory = false;

		if (victory)
			win(player);
		else{
			index.eliminatePlayer(player);
			hasActions=false;
			frame.getSidePanel().getText().setText("\n\n***THE ACCUSATION WAS***\n       INCORRECT\n\n");
			frame.getSidePanel().getText().setText("\n\n***"+player.getName()+"***\n HAS BEEN ELIMINATED\n\n");
		}

	}

	/**
	 * Prepares the move for the player by rolling two die
	 *
	 */

	private void prepareMove() {
		moveCount = (int) (Math.random() * 10 + 3);
		frame.getSidePanel().getText().setText(currentPlayer.playerName() + " has rolled a " + moveCount
				+ "\n\nPlease use the directional pad \nto move to your desired square\n");
	}

	/**
	 * Sets the game condition as "Finished". Is called by a successful accuse
	 * move
	 *
	 */
	private void win(Character player) {
		gameState = GameState.FINISHED;
		String ObjButtons[] = { "Restart", "Exit" };
		int PromptResult = JOptionPane.showOptionDialog(null, "GAME OVER\n"+player.playerName()+" "
				+ ""
				+ "HAS WON",
				"Close Confirmation", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, ObjButtons,
				ObjButtons[1]);
		if (PromptResult == 1) {
			System.exit(0);
		}
		else if(PromptResult == 0){
			Main.restart();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (lastAction == "Roll" && moveCount != 0) {
			System.out.println("is inside key pressed, moveCOunt: " + moveCount);
			int code = e.getKeyCode();
			int x = 0, y = 0;
			if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
				x = 1;
			} else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
				x = -1;
			} else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_UP) {
				y = 1;
			} else if (code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
				y = -1;
			}
			// move was successful
			moveCount = board.movePlayer(x, y, currentPlayer, moveCount);
			if (moveCount == 0) {
				completedMove = true;
				System.out.println("Move completed, Last action: " + lastAction);
			}
			frame.getSidePanel().getText().setText(moveCount + " moves left\n");
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if(frame.getCardPanel().getSuggestCards()!=null){
			System.out.println(frame.getCardPanel().getSuggestCards());
			Card theCard;
			for(Card c : frame.getCardPanel().getSuggestCards()){
				System.out.println("card rectangle: "+c.getRectangle()+"    mouse Point: "+e.getPoint());
				if(c.getRectangle().contains(e.getPoint())){
					selectedCard=true;				
					frame.getCardPanel().hasSuggested(true);
					frame.getCardPanel().getSuggestCards().clear();
					frame.getCardPanel().getSuggestCards().add(c);
					break;
				}
			}
		}


	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}
	@Override
	public void mouseExited(MouseEvent e) {		
	}

	@Override
	public void mousePressed(MouseEvent e) {		
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
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
