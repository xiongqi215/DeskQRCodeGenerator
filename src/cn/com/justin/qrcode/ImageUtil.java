package cn.com.justin.qrcode;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	public static void diposeWaterMark(String orgFie, String markFile,String destFile) throws IOException{
		BufferedImage src= ImageIO.read(new File(orgFie));
	  int width=src.getWidth(null);
	  int height=src.getHeight(null);

	  Graphics2D g=src.createGraphics();
	  
	  BufferedImage mark= ImageIO.read(new File(markFile));
	  int mark_width=mark.getWidth(null);
	  int mark_height=mark.getHeight(null);
	  
	  if((width*height)<=(mark_width*mark_height)){
		  mark=zoomImage(mark, 10, false);
		  
		 mark_width=mark.getWidth(null);
		   mark_height=mark.getHeight(null);
	  }
	  g.drawImage(mark, (width - mark_width) / 2,
              (height - mark_height) / 2, mark_width, mark_height, null);
	  
	  
	  g.dispose();
	  
	 ImageIO.write(src, "JPEG", new File(destFile));
	}
	
	public  static BufferedImage resizeImage(BufferedImage bi,int width,int height) throws IOException{
		double ratio = 0.0; // 缩放比例
		
        Image itemp = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		int src_width=bi.getWidth(null);
		  int src_height=bi.getHeight(null);
		  if(src_height>src_width){
			  ratio = (new Integer(height)).doubleValue() /src_height;
		  }else{
			  ratio = (new Integer(width)).doubleValue() /src_width;
		  }
		 
		AffineTransformOp op=new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
		
		  itemp = op.filter(bi, null);
		  
		  
		  return (BufferedImage)itemp;
	}
	
	public static BufferedImage zoomImage(BufferedImage bi,int scale,boolean flag) throws IOException{
		int src_width=bi.getWidth(null);
		  int src_height=bi.getHeight(null);
		  if(flag){
			  src_width=src_width*scale;
			  src_height=src_height*scale;
		  }else{
			  src_width=src_width/scale;
			  src_height=src_height/scale;
		  }
		  
		  Image itemp = bi.getScaledInstance(src_width, src_height, Image.SCALE_DEFAULT);
		  
		  BufferedImage temp=new BufferedImage(src_width, src_height, BufferedImage.TYPE_INT_RGB);
		  Graphics2D g=temp.createGraphics();
		  g.drawImage(itemp, 0, 0, null);
		  g.dispose();
//		  ImageIO.write(temp, "JPEG", new File("D:/AAA.JPEG"));
		  return temp;
	}
	
	public static void main(String[] args) throws IOException {
		ImageUtil.diposeWaterMark("d:/xxx.jpg","d:/IMG_20120808_224056.JPG","d:/xx.jpg");
	}
}
