package jComponents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jFrame.ButtonBox;

public class SidePanel extends JPanel {
	
	private TextBox textBox;
	private ButtonBox buttonBox;

	public SidePanel(){
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
