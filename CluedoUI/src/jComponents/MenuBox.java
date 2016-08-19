package jComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import main.Main;

/**
 * The menu bar frame for the game of cluedo
 * 
 * @author Ryan
 *
 */
public class MenuBox extends JMenuBar{

	JMenuBar menuBar;
	JMenu insultMenu;
	JMenuItem insult1, insult2, insult3, insult4, insult5;
	TextBox textBox;
	
	/**
	 * The Menu Box holds tools and powerful verbal spells that can
	 * drastically alter a game's scenario
	 * 
	 * @param text
	 */
	public MenuBox(TextBox text) {
		textBox = text;
		menuBar = new JMenuBar();
		
		insultMenu = new JMenu("Throw Insult");
		menuBar.add(insultMenu);
		add(menuBar);

		insult1 = new JMenuItem("You suck");
		insult1.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			textBox.setText("You suck\n");
		}});
		
		insult2 = new JMenuItem("You're terrible");
		insult2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				textBox.setText("You're terrible\n");
			}});
		
		insult3 = new JMenuItem("You're pretty bad");
		insult3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				textBox.setText("You're pretty bad\n");
			}});
		
		insult4 = new JMenuItem("You're not too good");
		insult4.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				textBox.setText("You're not too good\n");
			}});
		
		insult5 = new JMenuItem("*too rude*");
		insult5.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				textBox.setText("gg ez\n");
			}});
		
		insultMenu.add(insult1);
		insultMenu.add(insult2);
		insultMenu.add(insult3);
		insultMenu.add(insult4);
		insultMenu.add(insult5);
	}
}
