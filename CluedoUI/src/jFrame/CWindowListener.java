package jFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class CWindowListener extends WindowAdapter{
	
	 @Override
	  public void windowClosing(WindowEvent we){
	    String ObjButtons[] = {"Yes","No"};
	    int PromptResult = JOptionPane.showOptionDialog(null, 
	        "Are you sure you want to exit?", "Online Examination System", 
	        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
	        ObjButtons,ObjButtons[1]);
	    if(PromptResult==0)
	    {
	      System.exit(0);          
	    }
	 }
	
}
