package dragonball.view;

import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dragonball.controller.GUI;

public class GameView extends JFrame {
	private GUI gui;
	public GameView(GUI gui){
		this.gui = gui;
		setTitle("Dragon Ball Adventures");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setBounds(0,0,1000,600);
		try {
			BufferedImage im = ImageIO.read(new File("GameView.jpg"));
			Image dimg = im.getScaledInstance(1000,600, Image.SCALE_SMOOTH);
			ImagePanel i = new ImagePanel(dimg);
			setContentPane(i);
			JButton newGame = new JButton("New Game"); 
			JButton loadGame = new JButton("Load Game");
			JButton tournament = new JButton("Tournament");
			//i.setLayout(new BorderLayout());
			i.add(newGame);
			i.add(loadGame);
			i.add(tournament);
			loadGame.setBounds(450, 290, 100, 40);
			newGame.setBounds(450, 200, 100, 40);
			tournament.setBounds(450, 380, 150, 40);
			newGame.addActionListener(gui);
			loadGame.addActionListener(gui);
			tournament.addActionListener(gui);
			//setDefaultLookAndFeelDecorated(true);
			setIconImage(new ImageIcon("Dragon_Ball.png").getImage());
			setVisible(true);
			validate();
			repaint();
			addWindowListener(new java.awt.event.WindowAdapter() {
			        public void windowClosing(WindowEvent winEvt) {
			        	int returnVal = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				        if(returnVal==JOptionPane.YES_OPTION)
				        	System.exit(1);
			        }
			    });
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
