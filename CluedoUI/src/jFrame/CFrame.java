package jFrame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

import jComponents.SidePanel;

/**
 * The JFrame that holds the whole game. It holds the side panel, the
 * card panel and the board panel.
 * 
 * @author Jeremy
 *
 */
public class CFrame extends JFrame {
	private SidePanel sidePanel;
	private CardPanel cardPanel;
	private BoardPanel boardPanel;
	
	/**
	 * Creates a CFrame object and sets the frame options
	 * 
	 */
	public CFrame(){

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new CWindowListener());
		setBounds(0, 0, 1050, 1020);
		setLayout(new BorderLayout());
	}

	/**
	 * Adds the panels to the cluedo frame object
	 * 
	 */
	public void add(Component comp, Object constraints){
		if(comp instanceof SidePanel){
			sidePanel = (SidePanel) comp;
		}
		else if(comp instanceof CardPanel){
			cardPanel = (CardPanel) comp;
		}
		else if(comp instanceof BoardPanel){
			boardPanel= (BoardPanel) comp;
		}
		super.add(comp, constraints);
	}
	/**
	 * Returns the instance of the SidePanel that contains the button box and Text field
	 * @return
	 */
	public SidePanel getSidePanel(){
		return sidePanel;
	}
	/**
	 * Returns the instance of the board panel that contains the board
	 * @return
	 */
	public BoardPanel getBoardPanel(){
		return boardPanel;
	}
	/**
	 * Returns an instance of cardPanel
	 * @return
	 */
	public CardPanel getCardPanel(){
		return cardPanel;
	}

}
