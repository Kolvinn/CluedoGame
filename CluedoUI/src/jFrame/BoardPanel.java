package jFrame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JPanel;

import jComponents.BoardObject;

public class BoardPanel extends JPanel  {
    private Image image;
    Set<BoardObject> objects;
    public BoardPanel(Image image, Set<BoardObject> objects) {
        this.image = image;
        this.objects=objects;
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(720, 750);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        for(BoardObject object: objects){
        	object.setToolTipText(object.getToolTipText());
        	object.paint(g);
        }

    }


}
