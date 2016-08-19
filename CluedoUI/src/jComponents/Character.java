package jComponents;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Game.Coordinate;
import cards.Card;

public class Character extends BoardObject{
	boolean isPlayer= false;
	ArrayList<Card> hand;
	private String playerName;
	public Character(BufferedImage image, Coordinate coordinate, String name) {
		super(image, coordinate, name);
	}
	public String playerName(){
		return playerName;
	}
	public void setPlayerName(String playerName){
		if(isPlayer())
			this.playerName = playerName;
	}
	public String getToolTipText(){
		if(isPlayer)
			return "Player\n"+"Character: "+ name+"\nName: "+playerName;
		return "Character\n"+name;
    }
	public void setAsPlayer(){
		isPlayer= true;
	}
	public boolean isPlayer(){
		return isPlayer;		
	}
	public ArrayList<Card> hand(){
		return hand;
	}
	public boolean createHand(ArrayList<Card> hand){
		this.hand = hand;
		return true;			
	}
	public boolean equals(Object o){
    	String n = (String) o;
    	return o.equals(name);
    }

}
