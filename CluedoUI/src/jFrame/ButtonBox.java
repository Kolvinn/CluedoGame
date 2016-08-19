package jFrame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jComponents.TextBox;

public class ButtonBox extends JPanel {

	private JSlider playerSlider;
	private TextBox textBox;

	private int setPlayers = 0;
	private JButton confirmPlayers;

	private JButton Pass, Roll, Suggest, Accuse, Stairs, Character, Room, Weapon, Scarlett, Mustard, White, Green,
			Peacock, Plum, Kitchen, Ballroom, Conservatory, DiningRoom, BillardRoom, Library, Lounge, Hall, Study,
			Candlestick, Dagger, LeadPipe, Revolver, Rope, Spanner;
	private JRadioButton RScarlett, RMustard, RWhite, RGreen, RPeacock, RPlum;

	private JButton[] actions = { Pass, Roll, Suggest, Accuse, Stairs };
	private String[] actionNames = { "Pass", "Roll", "Suggest", "Accuse", "Stairs" };

	private JButton[] tokens = { Character, Room, Weapon };
	private String[] tokenNames = { "Character", "Room", "Weapon" };

	private JButton[] characters = { Scarlett, Mustard, White, Green, Peacock, Plum };
	private JRadioButton[] Rcharacters = { RScarlett, RMustard, RWhite, RGreen, RPeacock, RPlum };
	private String[] characterNames = { "Miss Scarlett", "Colonel Mustard", "Mrs White", "Reverend Green",
			"Mrs Peacock", "Professor Plum" };

	private JButton[] rooms = { Kitchen, Ballroom, Conservatory, DiningRoom, BillardRoom, Library, Lounge, Hall,
			Study };
	private String[] roomNames = { "Kitchen", "Ball room", "Conservatory", "Dining Room", "Billard Room", "Library",
			"Lounge", "Hall", "Study" };

	private JButton[] weapons = { Candlestick, Dagger, LeadPipe, Revolver, Rope, Spanner };
	private String[] weaponNames = { "Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner" };

	private String action = null;

	private JButton[][] buttons = { actions, tokens, characters, rooms, weapons };
	private String[][] buttonNames = { actionNames, tokenNames, characterNames, roomNames, weaponNames };

	private boolean buttonPressed;

	/**
	 * The JPanel storing all the buttons for the GUI
	 *
	 * @param textBox
	 */
	public ButtonBox(TextBox textBox) {
		this.textBox = textBox;
		this.buttonPressed = false;
		this.setLayout(new GridLayout(7, 2, 5, 5));
		this.setBorder(new EmptyBorder(20, 5, 80, 5));

	}

	/**
	 * Displays and enables a set of JComponets based on what is passed in the
	 * parameters: "start", "characterSelect", "actions", "tokens",
	 * "characters", "rooms", "weapons"
	 *
	 */
	public String getButton(String value, Set<String> buttons) {
		System.out.println("Getting Button");

		this.removeAll();

		if (buttons == null && !value.equals("start")) {
			System.err.println("Parameter is invalid");
			return "list parameter is invalid";
		}

		if (value.equals("start"))
			return enableSlider();
		else if (value.equals("characterSelect"))
			return enableCharacterSelect(buttons);
		else
			return enableButtons(value, buttons);

	}

	/**
	 * Enables the JSlider for player selection
	 *
	 * @return players playing
	 */
	private String enableSlider() {
		System.out.println("Enabling sliders");
		textBox.setText("Please use the slider to set the\nnumber of players");

		makeSliders();
		confirmPlayers = new JButton("Set");
		add(confirmPlayers);
		confirmPlayers.addActionListener(setPlayers());

		validate();
		setVisible(true);

		while (!buttonPressed)
			sleep(100);

		buttonPressed = false;

		this.removeAll();
		validate();

		return playerSlider.getValue() + "";
	}

	/**
	 * Enables the character radio buttons for player selection
	 *
	 * @return player name and character selected
	 */
	private String enableCharacterSelect(Set<String> values) {
		System.out.println("Setting player characters");
		ButtonGroup buttonMenu = new ButtonGroup();
		textBox.enableTextField();

		System.out.println(values);

		/*
		 * Creating the JRadioButtons for the character selection and adding them
		 * to a buttonMenu collection so only one of them can be active at a time
		 * to ensure one player can not be more than one character
		 */
		for (int i = 0; i < Rcharacters.length; i++) {
			Rcharacters[i] = new JRadioButton(characterNames[i]);
			buttonMenu.add(Rcharacters[i]);
			add(Rcharacters[i]);
			Rcharacters[i].setEnabled(false);
			Rcharacters[i].setActionCommand(characterNames[i]);

			if (!values.contains(characterNames[i]))
				Rcharacters[i].setEnabled(true);

		}

		confirmPlayers = new JButton("Set");
		add(confirmPlayers);
		confirmPlayers.addActionListener(setCharacter());

		validate();

		while (!buttonPressed || textBox.getTextField().equals("")){
			buttonPressed = false;
			sleep(100);
		}

		/*
		 * The try and catch is to check that there is a radio button selected
		 */
		String selection = "";
		String name = "";
		try {
			selection = buttonMenu.getSelection().getActionCommand();
			name = textBox.getTextField();
			buttonMenu.clearSelection();
			textBox.setText(name + " as\n" + selection);
		} catch (NullPointerException e) {
			textBox.setText("Please select a character");
			sleep(1000);
			return enableCharacterSelect(values);
		}

		this.removeAll();
		validate();

		buttonPressed = false;
		textBox.enableTextField();
		return name + "," + selection;
	}

	/**
	 * Creates and enables an array of buttons
	 *
	 * @return button clicked
	 */
	private String enableButtons(String array, Set<String> enabled) {
		JButton[] buttons;
		String[] buttonNames;

		/*
		 * Based on the 'array' string passed in, it determines which
		 * array of JButtons to use when creating the buttons
		 */
		switch (array) {
		case "actions":
			System.out.println("Enabling action buttons");
			buttons = actions;
			buttonNames = actionNames;
			break;
		case "tokens":
			System.out.println("Enabling token buttons");
			buttons = tokens;
			buttonNames = tokenNames;
			break;
		case "characters":
			System.out.println("Enabling character buttons");
			buttons = characters;
			buttonNames = characterNames;
			break;
		case "rooms":
			System.out.println("Enabling room buttons");
			buttons = rooms;
			buttonNames = roomNames;
			break;
		case "weapons":
			System.out.println("Enabling weapon buttons");
			buttons = weapons;
			buttonNames = weaponNames;
			break;
		default:
			System.out.println("Invalid input for buttons");
			return "Invalid input detected";
		}

		System.out.println("Creating buttons");

		/*
		 * Creating the JButtons by iterating through the array of
		 * the specific JButtons and enabling/disabling them based
		 * on the enabled set
		 */
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(buttonNames[i]);
			add(buttons[i]);

			if (array.equals("actions") || array.equals("tokens") || array.equals("characters") || array.equals("rooms")
					|| array.equals("weapons"))
				buttons[i].addActionListener(theAction(buttonNames[i], buttons));
			else
				return "failure detected";

			if (enabled.contains(buttonNames[i]))
				buttons[i].setEnabled(true);
			else
				buttons[i].setEnabled(false);

		}

		validate();
		this.setVisible(true);
		this.repaint();

		while (!buttonPressed)
			sleep(100);

		this.removeAll();
		validate();

		buttonPressed = false;
		return action;
	}

	/**
	 * The action button listener sets the players based on the slider position
	 *
	 * @param name
	 * @return
	 */
	private ActionListener setPlayers() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Player set button detected");
				setPlayers = playerSlider.getValue();
				buttonPressed = true;
				playersSet();
			}
		};
	}

	/**
	 * The action listener for setting the player's character
	 *
	 */
	private ActionListener setCharacter() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Character set button detected");
				buttonPressed = true;
			}
		};
	}

	/**
	 * The action listener sets the button event
	 *
	 * @param name
	 * @return
	 */
	private ActionListener theAction(String name, JButton[] buttons) {

		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(name + " button detected");
				action = name;
				buttonPressed = true;
				actionPressed(buttons);
				setVisible(true);
			}
		};
	}

	/**
	 * Makes the slider and set button invisible
	 *
	 */
	private void playersSet() {
		System.out.println("Players set: " + setPlayers);
		confirmPlayers.setVisible(false);
		playerSlider.setVisible(false);
	}

	/**
	 * Makes the selected buttons invisible
	 *
	 */
	private void actionPressed(JButton[] buttons) {
		for (JButton butt : buttons)
			butt.setVisible(false);

	}

	/**
	 * Creates JSliders and returns the value the slider is set on
	 *
	 */
	private void makeSliders() {
		playerSlider = new JSlider(3, 6);
		playerSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				textBox.setText("Players: " + playerSlider.getValue());
			}
		});
		add(playerSlider);
		System.out.println("Slider made");

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
