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

public class CFrame extends JFrame {
	ArrayList<Component> components = new ArrayList<Component>();
	private SidePanel sidePanel;
	private CardPanel cardPanel;
	private BoardPanel boardPanel;
	public CFrame(){

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new CWindowListener());
		setBounds(0, 0, 1050, 1020);
		setLayout(new BorderLayout());
	}

	public void add(Component comp, Object constraints){
		if(comp instanceof SidePanel){
			System.out.println("side");
			sidePanel = (SidePanel) comp;
		}
		else if(comp instanceof CardPanel){
			System.out.println("card");
			cardPanel = (CardPanel) comp;
		}
		else if(comp instanceof BoardPanel){
			System.out.println("cluedo");
			boardPanel= (BoardPanel) comp;
		}
		//System.out.println(comp.getClass());
		super.add(comp, constraints);
	}
	public SidePanel getSidePanel(){
		return sidePanel;
	}
	public BoardPanel getBoardPanel(){
		return boardPanel;
	}
	public CardPanel getCardPanel(){
		return cardPanel;
	}

}
