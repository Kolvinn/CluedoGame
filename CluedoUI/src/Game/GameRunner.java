package Game;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Set;




import jFrame.CFrame;

import jComponents.Character;


public class GameRunner implements KeyListener {

	/**
	 * The potential states of a running game
	 *
	 * @author zhengzhon
	 *
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
	public GameRunner(Board board, CFrame frame, GameIndex index){
		this.board = board;
		this.frame=frame;
		this.index=index;
		frame.addKeyListener(this);
		frame.setFocusable(true);		

		
		playGame();

	}

	/**
	 * Runs the game. Ends when all players have been eliminated or the murderer has been correctly identified
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
				frame.getSidePanel().getText().setText("\n"+player.playerName()+"'s turn is over");
			}
	}
	
	private void executeAction(String action, Character player){
		lastAction = action;
		System.out.println(lastAction);
		switch (action){
		case("Roll"):
			prepareMove();
			while(!completedMove){
			}
			break;
		case "Pass":
			hasActions =false;
			break;
		case "Stairs":
			UseStairs(player);
			hasActions=false;
		case "Suggest":
			//suggest();
			break;
		case "Accuse":
		}
	}

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
	 * @throws InvalidGamePlay
	 */
	public void suggest() throws InvalidGamePlay {


	}

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

}

