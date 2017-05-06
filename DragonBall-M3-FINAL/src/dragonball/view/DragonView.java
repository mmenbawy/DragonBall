package dragonball.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dragonball.controller.GUI;
import dragonball.model.dragon.Dragon;

public class DragonView extends JFrame{
	private Dragon dragon;
	private GUI gui;
	
	
	
	public Dragon getDragon() {
		return dragon;
	}

	public DragonView(Dragon dragon, GUI gui){
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	//public DragonView(String name){
		
		this.gui=gui;
		this.dragon=dragon;
		String name=dragon.getName();
		this.setTitle("The Dragon "+name);
		
		int style = Font.BOLD | Font.ITALIC ;
		Font font = new Font ("Narkisim", style , 30);
		Font font2 =new Font ("Goudy Stout", style, 15);
		String file="";
		
		switch (name){
		case "Shenron": file="ShenronROF.png";break;
		case "Porunga": file="Porunga.jpg";break;
		default: file="ShenronROF.png";break;
		}
		
		
		BufferedImage im = null;
		try {
			im = ImageIO.read(new File(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//java.awt.Image dimg = im.getScaledInstance(1000,600, java.awt.Image.SCALE_SMOOTH);
		ImagePanel i = new ImagePanel(im);
		//setSize(im.getWidth(),im.getHeight());
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setContentPane(i);
		setVisible(true);
		setLayout(new BorderLayout());
		JButton btn1=new JButton("Super Attack");
		JButton btn2=new JButton("Ultimate Attack");
		JButton btn3=new JButton("Senzu Beans");
		JButton btn4=new JButton("Ability Points");
		JLabel greet=new JLabel("HAAhhahahha Choose a Wish");
		JPanel panel=new JPanel();
		add(panel,BorderLayout.CENTER);
		panel.setOpaque(false);
		
		btn1.setPreferredSize(new Dimension(300, 60));
		btn2.setPreferredSize(new Dimension(350, 60));
		btn3.setPreferredSize(new Dimension(300, 60));
		btn4.setPreferredSize(new Dimension(300, 60));
		greet.setBounds(150, 60, 30, 30);
		greet.setFont(font);
		greet.setForeground(Color.white);
		greet.setBackground(Color.DARK_GRAY);
		greet.setOpaque(true);
		btn1.setFont(font2);
		btn2.setFont(font2);
		btn3.setFont(font2);
		btn4.setFont(font2);
		
		panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill= GridBagConstraints.HORIZONTAL;
        panel.add(greet, gbc);
        panel.add(btn1, gbc);
        panel.add(btn2, gbc);
        panel.add(btn3, gbc);
        panel.add(btn4, gbc);
        
        btn1.addActionListener(gui);
        btn2.addActionListener(gui);
        btn3.addActionListener(gui);
        btn4.addActionListener(gui);
        
        
        //setDefaultLookAndFeelDecorated(true);
		setIconImage(new ImageIcon("Dragon_Ball.png").getImage());
		
		addWindowListener(new java.awt.event.WindowAdapter() {
	        public void windowClosing(WindowEvent winEvt) {
	        	int returnVal = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		        if(returnVal==JOptionPane.YES_OPTION)
		        	System.exit(1);
	        }
	    });
       
	}
}
