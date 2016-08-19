package jFrame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jComponents.TextBox;

public class ButtonPanel extends JPanel {
	
	private TextBox textBox;
	private ButtonBox buttonBox;

	public ButtonPanel(){
		textBox = new TextBox();
		buttonBox = new ButtonBox(textBox);

		setLayout(new GridLayout(2, 1, 20, 20));
		add(textBox, BorderLayout.NORTH);
		add(buttonBox, BorderLayout.SOUTH);

		setVisible(true);
	}

	public ButtonBox getButtons(){
		return buttonBox;
	}
	
	public TextBox getText(){
		return textBox;
	}
	
}
