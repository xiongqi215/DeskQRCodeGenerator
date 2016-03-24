package cn.com.justin.qrcode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = -2116037791969714233L;
	private JFrame  parent=this;
	private JTextField content;
	private JTextField size;
	private MsgTextPanel msg;
	private JButton btn_start;
	private JButton btn_default;
	private JButton btn_save;
    private JPanel controlPanel;
    private JPanel mainPanel ;
    private JPanel msgPanel ;
    private JLabel label_QrImage;
    private BufferedImage current_produce;
    private JFileChooser chooser;
    private int QRCODE_SIZE=300;
    private QRCodeFactory factory;
    private int DEFAULT_WIDTH=600;
    private int DEFAULT_HEIGHT=600;
    private String DEFAULT_IMAGE_TYPE="png";
    private String DEFAULT_EXTENSION="."+DEFAULT_IMAGE_TYPE;
    private  String[] availabelFileType=new String[]{"jpg","png","jpeg"};
 
	public MainFrame()  {
		try{
			chooser=new JFileChooser();
			factory=new QRCodeFactory(QRCODE_SIZE);
		this.init();
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, "初始化失败！("+e.getClass()+")!");
		}
		btn_default.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				msg.setText("  输入内容与尺寸后，按确定按钮生成二位图片   ");
				mainPanel.removeAll();
				mainPanel.revalidate();
				mainPanel.repaint();
				btn_default.setEnabled(false);
			    btn_save.setEnabled(false);
				current_produce.flush();
				current_produce=null;
			}
		});
        btn_start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
				String c=content.getText();
				if(c.length()==0){
					msg.setText("请输入二维码内容！ ", Color.red,false);
					return;
				}
				if(size.getText().length()==0){
					msg.setText("请输入二维码图片尺寸！ ", Color.red,false);
					return;
				}
				boolean b = Pattern.matches("[0-9]*", size.getText());
				if(!b){
					msg.setText("二维码图片尺寸必须是数字！ ", Color.red,false);
					return;
				}
					current_produce= factory.encode(c);
					
					label_QrImage=new JLabel(new ImageIcon(current_produce),SwingConstants.CENTER);
					label_QrImage.setBorder( new LineBorder(Color.black));
					mainPanel.removeAll();
					mainPanel.add(label_QrImage,BorderLayout.CENTER);
					mainPanel.revalidate();
					msg.setText("生成成功!", Color.red,false);
					btn_default.setEnabled(true);
					btn_save.setEnabled(true);
				} catch (Exception e) {
					try {
						msg.setText("生成失败("+e.getClass()+")!", Color.red,false);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
        
        btn_save.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
	}
    public void init()throws Exception{
 
    	this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    	setIconImage(Toolkit.getDefaultToolkit().createImage(  
        		this.getClass().getResource("icon.png")));  
		int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;  
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;  
        this.setLocation((screen_width - this.getWidth()) / 2,  
                (screen_height - this.getHeight()) / 2);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
       	this.setLayout(new BorderLayout());
    	initControlPanel(new GridLayout(1, 5));
    	this.add(controlPanel, "North");
    	initMainPanel(new BorderLayout());
    	this.add(mainPanel, "Center");
    	
    	msgPanel=new JPanel();
    	msgPanel.setSize(DEFAULT_WIDTH, 10);
    	msg=new MsgTextPanel();
    	msg.setText("  输入内容与尺寸后，按确定按钮生成二位图片   ", Color.red,false);
    	msg.setEditable(false);
    	
    	btn_default=new JButton("清除");
    	btn_default.setEnabled(false);
    	
    	btn_save=new JButton("保存");
    	btn_save.setEnabled(false);
		  
    	msgPanel.add(msg);
    	msgPanel.add(btn_save);
    	msgPanel.add(btn_default);
    	this.add(msgPanel, "South");
        this.setVisible(true);  
    }
    
    public void initControlPanel(LayoutManager layoutManager){
    	controlPanel = new JPanel();
    	controlPanel.setBorder(new LineBorder(getBackground(),5));
		JLabel label_content = new JLabel("二维码内容");
		content = new JTextField();
		JLabel label_size = new JLabel("二维码尺寸");
		size = new JTextField();
		btn_start = new JButton("确定");
		controlPanel.setLayout(layoutManager);
		controlPanel.add(label_content);
		controlPanel.add(content);
		controlPanel.add(label_size);
		controlPanel.add(size);
		controlPanel.add(btn_start);
//		controlPanel.add(fileChooser);
    }
    
    public void initMainPanel(LayoutManager layoutManager){

		mainPanel = new JPanel(layoutManager);
		mainPanel.setBackground(Color.white);
		mainPanel.setBorder(new TitledBorder("图片预览"));  
    }
    
    public void saveFile() {
    	chooser.setSelectedFile(new File("我的二维码.png"));
    	chooser.setDialogTitle("保存二维码图片");
 
    	FileNameExtensionFilterExt filter=new FileNameExtensionFilterExt("图片文件(jpg,jpeg,png)",availabelFileType);
    	chooser.setFileFilter(filter);
    	int result=chooser.showSaveDialog(parent);
    	
		try {
			if(result==JFileChooser.APPROVE_OPTION){
				int s=Integer.valueOf(size.getText());
				BufferedImage bi=ImageUtil.resizeImage(current_produce, s, s);
				File file=chooser.getSelectedFile();
				if(!filter.accept(file)){
					String path=file.getAbsolutePath();
					path+=DEFAULT_EXTENSION;
                	file=new File(path);
				}
				
				if(!file.exists()){
					file.createNewFile();
				}
				ImageIO.write(bi, "png", file);
				JOptionPane.showMessageDialog(this, "文件保存成功!");
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "文件保存失败！("+e.getClass()+")!");
		}
    }
  
	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
	}
}
