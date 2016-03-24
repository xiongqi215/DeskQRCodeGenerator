package cn.com.justin.qrcode;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class QrCodImgLabelGenerator {
//   private BufferedImage image;
   private JLabel jpanel_Image;
   
   public JLabel generate(int width,int height,BufferedImage image){
//        BufferedImage bi=new BufferedImage(width, height, imageType)
	   
	   jpanel_Image=new JLabel(new ImageIcon(image),SwingConstants.CENTER);
	
	   
	   return this.jpanel_Image;
   }
}
