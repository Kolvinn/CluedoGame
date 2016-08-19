package jComponents;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import Game.Coordinate;

public class BoardObject extends JComponent {
	protected Image image;
	protected int width=26, height=26;
	protected Coordinate coordinate;
	protected String name;
	public BoardObject(BufferedImage image, Coordinate coordinate, String name){
		this.image= image;
		this.coordinate= coordinate;
		this.name= name;
	}
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        int x = (coordinate.X*30)+2;
        int y = (coordinate.Y*30)+2;
        g.drawImage(image, x, y,width,height, this);	
        
    }
    public String getName(){
    	return name;
    }
    public boolean equals(Object o){
    	String n = (String) o;
    	return o.equals(name);
    }
    public String getToolTipText(){
    	return "Weapon\n"+name;
    }


	public void setCoordinate(Coordinate coo){
		this.coordinate=coo;
	}
	@Override
    public int getWidth(){
    	return width;
    }
    @Override
    public int getHeight(){
    	return height;
    }
    @Override
    public int getX(){
    	return coordinate.X;
    }
    @Override
    public int getY(){
    	return coordinate.Y;
    }
}
