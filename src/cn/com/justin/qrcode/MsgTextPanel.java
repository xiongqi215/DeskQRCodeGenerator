package cn.com.justin.qrcode;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class MsgTextPanel extends JTextPane{

	
	public void setText(String text,Color color) throws BadLocationException{
		this.setText(text,color,true);
	}
	
	
	
	
	public void setText(String text,Color color,boolean append) throws BadLocationException{
		SimpleAttributeSet set = new SimpleAttributeSet(); 
    	StyleConstants.setForeground(set, Color.red);//设置文字颜色 
    	StyleConstants.setFontSize(set, 12);//设置字体大小 
    	if(append){
    		this.getDocument().insertString(0, text, set);
    	}else{
    		this.getDocument().remove(0, this.getDocument().getLength());
    		this.getDocument().insertString(0, text, set);
    	}
		
		
	}
}
