package jComponents;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;

import jFrame.ButtonBox;

/**
 * The right-side panel of the Game of Cluedo. It holds both the text field and
 * the buttons and is only composes of the Graphical based input that the user is
 * able to utilize.
 *
 * @author Ryan
 *
 */
public class SidePanel extends JPanel {

	private TextBox textBox;
	private ButtonBox buttonBox;

	/**
	 * The constructor for the Side Panel
	 *
	 */
	public SidePanel(){
		textBox = new TextBox();
		buttonBox = new ButtonBox(textBox);

		setLayout(new GridLayout(2, 1, 20, 20));
		add(textBox, BorderLayout.NORTH);
		add(buttonBox, BorderLayout.SOUTH);

		setVisible(true);
	}
	public void setBoldFont(){
		getText().setFont(new Font("Dialog", Font.BOLD, 14));
	}
	public void setNormalFont(){
		getText().setFont(new Font("Dialog", Font.PLAIN, 12));
	}
	/**
	 * Returns the buttons section of the side panel
	 *
	 * @return
	 */
	public ButtonBox getButtons(){
		return buttonBox;
	}

	/**
	 * Returns the text section of the side panel
	 *
	 * @return
	 */
	public TextBox getText(){
		return textBox;
	}

}
