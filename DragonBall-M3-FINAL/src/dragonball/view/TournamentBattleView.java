package dragonball.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import dragonball.controller.GUI;
import dragonball.model.attack.Attack;
import dragonball.model.attack.PhysicalAttack;
import dragonball.model.attack.SuperSaiyan;
import dragonball.model.battle.Battle;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Majin;
import dragonball.model.character.fighter.Namekian;
import dragonball.model.character.fighter.Saiyan;

public class TournamentBattleView extends JFrame{
	private TournamentView gui;
	private Battle battle;
	protected JComboBox attacksMenu;
	private JProgressBar myHealth,myStamina,myKi,foeHealth,foeStamina,foeKi;
	private Fighter me,foe;
	private JPanel console;
	private JLabel myImage,foeImage,myConsole,foeConsole,turn;
	public TournamentBattleView(Battle battle,TournamentView gui) throws IOException{
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				int returnVal = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(returnVal==JOptionPane.YES_OPTION)
					System.exit(1);
			}
		});


		this.battle = battle;
		this.gui = gui;
		me = (Fighter)(battle.getMe());
		foe = (Fighter)battle.getFoe();
		setTitle(me.getName()+" VS "+foe.getName());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(0,0,1000,600);
		setResizable(false);
		setContentPane(new JLabel(new ImageIcon("Resources\\Namek.png")));

		JPanel p1,p2,p3;
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();

		setLayout(new BorderLayout());
		p1.setPreferredSize(new Dimension(10,70));
		p2.setPreferredSize(new Dimension(10,200));
		p3.setPreferredSize(new Dimension(10,50));

		JPanel myStatus,foeStatus;
		myStatus = new JPanel();
		foeStatus = new JPanel();
		myStatus.setLayout(new GridLayout(4,2));
		foeStatus.setLayout(new GridLayout(4,2));
		myStatus.add(new JLabel(me.getName()));
		foeStatus.add(new JLabel(foe.getName()));
		myStatus.add(new JLabel("Level "+me.getLevel()));
		foeStatus.add(new JLabel("Level "+foe.getLevel()));

		myHealth = new JProgressBar(0,me.getMaxHealthPoints());
		myStamina = new JProgressBar(0,me.getMaxStamina());
		myKi = new JProgressBar(0,me.getMaxKi());
		foeHealth = new JProgressBar(0,foe.getMaxHealthPoints());
		foeStamina = new JProgressBar(0,foe.getMaxStamina());
		foeKi = new JProgressBar(0,foe.getMaxKi());
		myHealth.setForeground(Color.green);
		myStamina.setForeground(Color.blue);
		myKi.setForeground(Color.yellow);
		foeHealth.setForeground(Color.green);
		foeStamina.setForeground(Color.blue);
		foeKi.setForeground(Color.yellow);
		myHealth.setString(""+me.getHealthPoints());
		myStamina.setString(""+me.getStamina());
		myKi.setString(""+me.getKi());
		foeHealth.setString(""+foe.getHealthPoints());
		foeStamina.setString(""+foe.getStamina());
		foeKi.setString(""+foe.getKi());
		myHealth.setStringPainted(true);
		myStamina.setStringPainted(true);
		myKi.setStringPainted(true);
		foeHealth.setStringPainted(true);
		foeStamina.setStringPainted(true);
		foeKi.setStringPainted(true);
		myHealth.setValue(me.getHealthPoints());
		myStamina.setValue(me.getStamina());
		myKi.setValue(me.getKi());
		foeHealth.setValue(foe.getHealthPoints());
		foeStamina.setValue(foe.getStamina());
		foeKi.setValue(foe.getKi());

		myStatus.add(new JLabel("Health Points (max."+me.getMaxHealthPoints()+")"));
		myStatus.add(myHealth);
		myStatus.add(new JLabel("Stamina (max."+me.getMaxStamina()+")"));
		myStatus.add(myStamina);
		myStatus.add(new JLabel("Ki (max."+me.getMaxKi()+")"));
		myStatus.add(myKi);
		foeStatus.add(new JLabel("Health Points (max."+foe.getMaxHealthPoints()+")"));
		foeStatus.add(foeHealth);
		foeStatus.add(new JLabel("Stamina (max."+foe.getMaxStamina()+")"));
		foeStatus.add(foeStamina);
		foeStatus.add(new JLabel("Ki (max."+foe.getMaxKi()+")"));
		foeStatus.add(foeKi);
		console = new JPanel();
		console.setLayout(new GridLayout(3, 1));
		myConsole = new JLabel("Battle has begun!");
		foeConsole = new JLabel();
		turn = new JLabel(me.getName()+"'s turn");
		myConsole.setHorizontalAlignment(SwingConstants.CENTER);
		foeConsole.setHorizontalAlignment(SwingConstants.CENTER);
		turn.setHorizontalAlignment(SwingConstants.CENTER);
		console.add(myConsole);
		console.add(foeConsole);
		console.add(turn);

		myStatus.setOpaque(false);
		foeStatus.setOpaque(false);
		p1.setLayout(new BorderLayout());
		p1.add(myStatus, BorderLayout.WEST);
		p1.add(foeStatus, BorderLayout.EAST);
		p1.add(console, BorderLayout.CENTER);
		p1.setOpaque(false);

		String imagePath;
		if(me instanceof Earthling){
			imagePath = "Earthling.png";
		}
		else if(me instanceof Namekian){
			imagePath = "Namekian.png";
		}
		else if(me instanceof Majin){
			imagePath = "Majin.png";
		}
		else if(me instanceof Saiyan){
			imagePath = "Saiyan.png";
		}
		else{
			imagePath = "Frieza.png";
		}
		BufferedImage im = ImageIO.read(new File("Resources\\"+imagePath));
		ImageIcon dimg = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Resources\\"+imagePath));
		//Image img = im.getScaledInstance(500, 480, Image.SCALE_SMOOTH);
		p2.setLayout(new BorderLayout());

		myImage = new JLabel(dimg);
		p2.add(myImage,BorderLayout.WEST);
		myImage.setOpaque(false);
		p2.setOpaque(false);

		if(foe.getUltimateAttacks().contains(new SuperSaiyan())){
			imagePath = "Saiyan2.png";
		}
		else{
			String[] ns = {"Frieza2.png","Majin2.png","Saiyan2.png"};
			imagePath = ns[(int)(Math.random()*3)];
		}
		im = ImageIO.read(new File("Resources\\"+imagePath));
		//img = im.getScaledInstance(500, 480, Image.SCALE_SMOOTH);
		foeImage = new JLabel(new ImageIcon(im));
		p2.add(foeImage,BorderLayout.EAST);

		ArrayList<Object> attacks = new ArrayList<>();
		attacks.add(new PhysicalAttack());
		if(me.getSuperAttacks().size()>0)
			attacks.add("---Super Attacks---");
		attacks.addAll(me.getSuperAttacks());
		if(me.getUltimateAttacks().size()>0)
			attacks.add("---Ultimate Attacks---");
		attacks.addAll(me.getUltimateAttacks());
		attacksMenu = new JComboBox<>();
		for(int i = 0;i<attacks.size();i++)
			attacksMenu.addItem(attacks.get(i));
		JButton attack,block,use;
		attack = new JButton("Attack");attack.addActionListener(gui);
		block = new JButton("Block");block.addActionListener(gui);
		use = new JButton("Use");use.addActionListener(gui);
		p3.setLayout(new GridLayout(1, 4));
		p3.add(attacksMenu);
		p3.add(attack);
		p3.add(block);
		p3.add(use);

		add(p1,BorderLayout.NORTH);
		add(p2,BorderLayout.CENTER);
		add(p3,BorderLayout.SOUTH);
		myImage.setOpaque(false);
		console.setOpaque(false);
		setIconImage(new ImageIcon("Dragon_Ball.png").getImage());
		setVisible(true);
		validate();
		repaint();
	}
	
	public void attackUsed(Attack attack){
		if(battle.getAttacker().equals(battle.getMe()))
			myConsole.setText(((Fighter)battle.getAttacker()).getName()+" used "+attack.getName());
		else
			foeConsole.setText(((Fighter)battle.getAttacker()).getName()+" used "+attack.getName());
		turn.setText(((Fighter)battle.getDefender()).getName()+"'s turn");
	}
	public Object getSelectedAttack(){
		return attacksMenu.getSelectedItem();
	}
	public Battle getBattle() {
		return battle;
	}
	public void updateScreen() {
		myHealth.setString(""+me.getHealthPoints());
		myStamina.setString(""+me.getStamina());
		myKi.setString(""+me.getKi());
		foeHealth.setString(""+foe.getHealthPoints());
		foeStamina.setString(""+foe.getStamina());
		foeKi.setString(""+foe.getKi());
		myHealth.setValue(me.getHealthPoints());
		myStamina.setValue(me.getStamina());
		myKi.setValue(me.getKi());
		foeHealth.setValue(foe.getHealthPoints());
		foeStamina.setValue(foe.getStamina());
		foeKi.setValue(foe.getKi());
		if(me instanceof Saiyan&&((Saiyan)me).isTransformed()){
			myImage.setIcon(new ImageIcon("Resources\\SuperSaiyan.png"));
		}
		else if(me instanceof Saiyan&&!((Saiyan)me).isTransformed())
			myImage.setIcon(new ImageIcon("Resources\\Saiyan.png"));

		Fighter attacker = (Fighter)getBattle().getAttacker();
		ArrayList<Object> attacks = new ArrayList<>();
		attacks.add(new PhysicalAttack());
		if(attacker.getSuperAttacks().size()>0)
			attacks.add("---Super Attacks---");
		attacks.addAll(attacker.getSuperAttacks());
		if(attacker.getUltimateAttacks().size()>0)
			attacks.add("---Ultimate Attacks---");
		attacks.addAll(attacker.getUltimateAttacks());
		attacksMenu = new JComboBox<>();
		for(int i = 0;i<attacks.size();i++)
			attacksMenu.addItem(attacks.get(i));
		validate();
		repaint();
	}
	public void blocked() {
		if(battle.getAttacker().equals(battle.getMe()))
			myConsole.setText(((Fighter)battle.getAttacker()).getName()+" blocked");
		else
			foeConsole.setText(((Fighter)battle.getAttacker()).getName()+" blocked");
		turn.setText(((Fighter)battle.getDefender()).getName()+"'s turn");
	}
	public void used() {
		myConsole.setText(((Fighter)battle.getAttacker()).getName()+" used a senzu bean");
		turn.setText(((Fighter)battle.getDefender()).getName()+"'s turn");
	}
}
