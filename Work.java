package JavaDB_001;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Work extends JFrame{
	static String path;
	static String path1;
	static String checksum;
	static String checksum1;
	
    JButton button ;
    JButton button1 ;
    JButton button2;
    JLabel label;
    JLabel label1;
    JLabel textbox;
    JLabel textbox1;
    JLabel textbox2;
    JLabel textbox3;
    
    public Work(){
    super("Detection of modification of image");
    
    button1 = new JButton("Image1");
    button1.setBounds(150,300,100,40);
    
    button = new JButton("Image2");
    button.setBounds(630,300,100,40);
    
    button2 = new JButton("Compare");
    button2.setBounds(390,400,100,40);
    
    label = new JLabel();
    label.setBounds(10,10,400,250);
    
    label1 = new JLabel();
    label1.setBounds(470,10,600,250);
    
    textbox = new JLabel();
    textbox.setBounds(90,350,230,40);
    
    textbox1 = new JLabel();
    textbox1.setBounds(570,350,230,40);
    
    textbox2 = new JLabel("MODIFIED", JLabel.CENTER);
    textbox2.setBounds(340,450,200,40);
    textbox2.setFont(new Font("Serif", Font.BOLD,24));
    textbox2.setForeground(Color.red);
    
    textbox3 = new JLabel("NOT MODIFIED", JLabel.CENTER);
    textbox3.setBounds(290,450,300,40);
    textbox3.setFont(new Font("Serif", Font.BOLD,24));
    textbox3.setForeground(Color.red);
    
    add(button);
    add(label);
    add(button1);
    add(label1);
    add(button2);
    add(textbox);
    add(textbox1);
	add(textbox2);
	add(textbox3);
	
    textbox.setVisible(false);
    textbox1.setVisible(false);
    textbox2.setVisible(false);
    textbox3.setVisible(false);

    button2.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) 
    	{
    		textbox.setText(checksum);
    		textbox1.setText(checksum1);
    		textbox.setVisible(true);
    		textbox1.setVisible(true);
    
    		try {
				gethashvalue();
				gethashvalue1();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

    		if(checksum.compareTo(checksum1) == 0) {
    			textbox3.setVisible(true);
    			textbox2.setVisible(false);
    		}
    		else {
    			textbox2.setVisible(true);
    			textbox3.setVisible(false);

    		}
    	}
    });
    
    button.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
        
          JFileChooser file = new JFileChooser();
          file.setCurrentDirectory(new File(System.getProperty("user.home")));
          
          //filter the files
          FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
          file.addChoosableFileFilter(filter);
          
          int result = file.showSaveDialog(null);
          
          //if the user click on save in Jfilechooser
          if(result == JFileChooser.APPROVE_OPTION){
              File selectedFile = file.getSelectedFile();
              path1 = selectedFile.getAbsolutePath();
              label1.setIcon(ResizeImage(path1));
              
          }
           //if the user click on save in Jfilechooser


          else if(result == JFileChooser.CANCEL_OPTION){
              System.out.println("No File Select");
          }
        }
    });
    
    button1.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
        
          JFileChooser file = new JFileChooser();
          file.setCurrentDirectory(new File(System.getProperty("user.home")));
          
          //filter the files
          FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
          file.addChoosableFileFilter(filter);
          int result = file.showSaveDialog(null);
          
           //if the user click on save in Jfilechooser
          if(result == JFileChooser.APPROVE_OPTION){
              File selectedFile = file.getSelectedFile();
              path = selectedFile.getAbsolutePath();
              label.setIcon(ResizeImage(path));
              
          }
           //if the user click on save in Jfilechooser


          else if(result == JFileChooser.CANCEL_OPTION){
              System.out.println("No File Select");
          }
        }
    });
    
    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
//    setLocationRelativeTo(null);
    setSize(900,600);
    setVisible(true);
    }
     
     // Methode to resize imageIcon with the same size of a Jlabel
    public ImageIcon ResizeImage(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
    
    public static void main(String[] args)
    		throws IOException, NoSuchAlgorithmException
    {
        new Work();
    }
    
    public static void gethashvalue() 
    		throws IOException, NoSuchAlgorithmException
    {
    	File file = new File(path);
        
      MessageDigest mdigest = MessageDigest.getInstance("MD5");

		// Get the checksum
		checksum = checksum(mdigest, file);
    }
    
    public static void gethashvalue1() 
    		throws IOException, NoSuchAlgorithmException
    {
    	File file1 = new File(path1);
        
      MessageDigest mdigest = MessageDigest.getInstance("MD5");

		// Get the checksum
		checksum1 = checksum(mdigest, file1);
    }
    
    private static String checksum(MessageDigest digest,
			File file)
					throws IOException
	{
		// Get file input stream for reading the file
		// content
		FileInputStream fis = new FileInputStream(file);
		
		// Create byte array to read data in chunks
		byte[] byteArray = new byte[1024];
		int bytesCount = 0;
		
		// read the data from file and update that data in
		// the message digest
		while ((bytesCount = fis.read(byteArray)) != -1)
		{
			digest.update(byteArray, 0, bytesCount);
		};
		
		// close the input stream
		fis.close();
		
		// store the bytes returned by the digest() method
		byte[] bytes = digest.digest();
		
		// this array of bytes has bytes in decimal format
		// so we need to convert it into hexadecimal format
		
		// for this we create an object of StringBuilder
		// since it allows us to update the string i.e. its
		// mutable
		StringBuilder sb = new StringBuilder();
		
		// loop through the bytes array
		for (int i = 0; i < bytes.length; i++) {
		
		// the following line converts the decimal into
		// hexadecimal format and appends that to the
		// StringBuilder object
			sb.append(Integer
			.toString((bytes[i] & 0xff) + 0x100, 16)
			.substring(1));
		}
		
		// finally we return the complete hash
		return sb.toString();
	}
    
   }
