package main;

import java.awt.BorderLayout;

import Game.Board;
import Game.GameIndex;
import Game.GameRunner;
import jComponents.MenuBox;
import jComponents.SidePanel;
import jFrame.BoardPanel;
import jFrame.CFrame;
import jFrame.CardPanel;

/**
 * Primarily for running the game of Cluedo
 *
 * @author Jeremy
 *
 */
public class Main {
	/**
	 * Creates instances of what is needed to start the game
	 * @param args
	 */

	public static void main (String args[]){
		CFrame frame = new CFrame();
		GameIndex game = new GameIndex();
		
		//create the Menu bar
		
		//create the jpanel that stores the gameboard
		frame.add(new BoardPanel(game.getImage("xboardObjects/finalBoard.png"), game.boardObjects()));
		//the board that is used for player movement logic
		Board board = new Board(game.boardObjects());

		//the jpanel used for all player interactions
		SidePanel buttonPanel = new SidePanel();
		frame.add(buttonPanel, BorderLayout.EAST);
		frame.add(new MenuBox(buttonPanel.getText()),BorderLayout.NORTH);

		frame.add(new CardPanel(game.cardDeck()), BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.createStartingPlayers(frame);
		GameRunner gameRunner = new GameRunner(board, frame, game);
	}
	public static void restart(){
		main(null);
	}
}
