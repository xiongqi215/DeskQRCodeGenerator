package cn.com.justin.qrcode;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeFactory {
	  public static final int BLACK = 0xFF000000;
	  public static final int WHITE = 0xFFFFFFFF;
	  private int OnColor;
	  private int OffColor;
	  private   int QRCODE_SIZE = 250;  
	
	  public QRCodeFactory(){
		  OnColor=this.BLACK;
		  OffColor=this.WHITE;
	  }
	  
  public QRCodeFactory(int QRCODE_SIZE){
	  OnColor=this.BLACK;
	  OffColor=this.WHITE;
	  this.QRCODE_SIZE=QRCODE_SIZE;
	  }
  

	public  void encode(String content,String filePath) throws WriterException, IOException{
		BufferedImage bi=this.encode(content);
	    ImageIO.write(bi, "png", new File(filePath));
	}
	
	public  BufferedImage encode(String content) throws WriterException, IOException{
		
		Map<EncodeHintType,Object> qrParam=new HashMap<EncodeHintType,Object>();
		qrParam.put(EncodeHintType.CHARACTER_SET, "GBK");
		qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		qrParam.put(EncodeHintType.MARGIN,0);
		MultiFormatWriter writer=new MultiFormatWriter();
		BitMatrix bitMatrix=writer.encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, qrParam);

		int[] rec = bitMatrix.getEnclosingRectangle();  
	    int resWidth = rec[2] + 1;  
	    int resHeight = rec[3] + 1;  
	  
	    BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);  
	    resMatrix.clear();  
	    for (int i = 0; i < resWidth; i++) {  
	        for (int j = 0; j < resHeight; j++) {  
	            if (bitMatrix.get(i + rec[0], j + rec[1]))  
	                resMatrix.set(i, j);  
	        }  
	    }  
	    
		int height=resMatrix.getWidth();
	    int width=resMatrix.getHeight();
	    
	 
	    BufferedImage bi=new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	        	  bi.setRGB(x, y, resMatrix.get(x, y) ? OnColor : OffColor);
	          }
	        }
	   return bi;
	}
	
	
	public void decode(InputStream input){
		
		 BufferedImage image;    
         try {    
             image = ImageIO.read(input);    
             if (image == null) {    
                 System.out.println("Could not decode image");    
             }    
             LuminanceSource source = new BufferedImageLuminanceSource(image);    
             BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source)); 
             
             Result result;    
             Hashtable <DecodeHintType,Object>hints = new Hashtable <DecodeHintType,Object>();    
             hints.put(DecodeHintType.CHARACTER_SET, "GBK");    
             result = new MultiFormatReader().decode(bitmap, hints);    
             String resultStr = result.getText(); 
             
             System.out.println(resultStr);    
  
         } catch (IOException ioe) {    
             System.out.println(ioe.toString());    
         } catch (ReaderException re) {    
             System.out.println(re.toString());    
         }    
		
	}
public static void main(String[] args) {
	QRCodeFactory factory=new QRCodeFactory();
	try {
		factory.encode("BEGIN:VCARD"+System.getProperty("line.separator")+
					   "VERSION:3.0"+System.getProperty("line.separator")+
					   "N:Justin"+System.getProperty("line.separator")+
					   "EMAIL:爱上大声地"+System.getProperty("line.separator")+
					   "END:VCARD", "d:/xx.png");
		
		factory.decode(new FileInputStream("d:/xx.png"));
	} catch (WriterException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	

	}
