package jFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

/**
 * The window that notifies the user if they want to exit the game
 * of cluedo upon clicking the exit button
 *
 * @author Jeremy
 *
 */
public class CWindowListener extends WindowAdapter {

	/**
	 * Brings up the confirmation window with options to confirm
	 * their choice
	 *
	 */
	@Override
	public void windowClosing(WindowEvent we) {
		String ObjButtons[] = { "Yes", "No" };
		int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?",
				"Close Confirmation", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
				ObjButtons[1]);
		if (PromptResult == 0) {
			System.exit(0);
		}
	}

}
