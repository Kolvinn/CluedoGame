package jComponents;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TextBox extends JPanel {

	private JTextField textField;
	private JTextArea textArea;

	/**
	 * The JPanel storing all the Text-based material for the GUI
	 *
	 */
	public TextBox() {
		super(new GridBagLayout());

		setBorder(new EmptyBorder(20, 20, 20, 20));
		setupText();
	}

	/**
	 * Sets up the text box
	 *
	 */
	private void setupText() {

		textField = new JTextField(20);
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				String text = textField.getText();

				textArea.append(text + "\n");
				textField.selectAll();
				textArea.setCaretPosition(textArea.getDocument().getLength());
			}
		});

		textArea = new JTextArea(20, 20);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textField.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;

		add(textField, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollPane, c);
	}

	public void setText(String text){
		textArea.setText(textArea.getText()+text);
	}

	public void clearTextField(){
		textField.setText("");
	}

	public void clearTextArea(){
		textArea.setText("");
	}

	public String getTextField(){
		return textField.getText();
	}

	public void enableTextField(){
		clearTextField();
		textField.setEditable(!textField.isEditable());
	}

}
