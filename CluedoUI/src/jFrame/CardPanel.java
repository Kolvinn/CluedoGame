package jFrame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import cards.Card;
import cards.CardDeck;
import jComponents.Character;

public class CardPanel extends JPanel {
	private CardDeck deck;
	private Character player;
	private int Y=30, width = 120, height =160;
	public CardPanel(CardDeck deck){
		this.deck=deck;
	}
	public void setCurrentPlayer(Character player){
		this.player=player;
	}

	@Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 210);
    }
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x =0;
        g.setFont(new Font("TimesRoman", Font.HANGING_BASELINE, 20));
        if(player!=null){
        	g.drawString(player.playerName()+"'s ("+player.getName()+"s) Hand", 280, 20);
        	for(Card card: player.hand()){
        		g.drawImage(card.image(), x, Y, width, height, this);
        		x+=width+5; //or whatever the card width is
        	}
        	g.drawString("Shown Cards", 900, 20);
        	//g.drawString("Shown Cards: ", 10, 100);
        	x=800;
        	if(deck.leftOverCards().size()!=0){
        	for(Card card: deck.leftOverCards()){
        		g.drawImage(card.image(), x, Y, width, height, this);
        		x+=width+5;
        		}

        	}
        }

    }

}
