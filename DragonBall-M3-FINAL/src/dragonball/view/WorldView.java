package dragonball.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import dragonball.controller.GUI;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Frieza;
import dragonball.model.character.fighter.Namekian;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.player.Player;
import dragonball.model.world.World;

@SuppressWarnings("serial")
public class WorldView extends JFrame implements KeyListener {
	private JLabel[][] jlabels;
	private int row, col;
	private ImageIcon fighter;
	private ImageIcon boss;
	private GUI gui;
	private Player player;
	private World world;
	private JMenuItem save, create, senzu, ability,xp,targetXp, dragon, maxh, maxk, maxs, pd, bd,
	supera, ultimatea;
	private JMenu switchf, upgrade, assign, info;
	private ButtonGroup group;

	public WorldView(GUI gui, World world, Player player) {
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				int returnVal = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(returnVal==JOptionPane.YES_OPTION)
					System.exit(1);
			}
		});


		this.gui = gui;
		this.player = player;
		this.world = world;

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		// setting the map first
		addKeyListener(this);
		setTitle("Dragon Ball Adventures (World View) ");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, 1000, 600);
		JPanel panel = new JPanel(new GridLayout(10, 10, -1, -1));
		panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		jlabels = new JLabel[10][10];

//		BufferedImage im = null;
//		try {
//			im = ImageIO.read(new File("ShenronROF.png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//java.awt.Image dimg = im.getScaledInstance(1000,600, java.awt.Image.SCALE_SMOOTH);
//		ImagePanel ik = new ImagePanel(im);
//		//setSize(im.getWidth(),im.getHeight());
//		setExtendedState(JFrame.MAXIMIZED_BOTH); 
//		setContentPane(ik);
		
		
		for (row = 0; row < 10; row++)
			for (col = 0; col < 10; col++) {
				JLabel label = new JLabel();
				//label.setBackground(Color.white);
				label.setBorder(BorderFactory.createLineBorder(Color.orange));
				label.setOpaque(false);
				jlabels[row][col] = label;
				panel.add(label);

			}

		panel.setOpaque(false);
		this.add(panel);

		String race = "";
		if (player.getActiveFighter() instanceof Saiyan)
			race = "goku";
		else if (player.getActiveFighter() instanceof Earthling)
			race = "earthling";
		else if (player.getActiveFighter() instanceof Namekian)
			race = "namekian";
		else if (player.getActiveFighter() instanceof Frieza)
			race = "frieza";
		else
			race = "majin";
		fighter = new ImageIcon(race + ".png");
		boss = new ImageIcon("boss.png");
		row = world.getPlayerRow();// default values for the player position
		col = world.getPlayerColumn();
		jlabels[world.getPlayerRow()][world.getPlayerColumn()].setIcon(fighter);
		jlabels[row][col].setHorizontalAlignment(JLabel.CENTER);

		// displaying player info
		displayinfo(player.getName(), player.getActiveFighter().getLevel(),
				player.getActiveFighter().getName());

		jlabels[0][0].setIcon(boss);
		jlabels[0][0].setHorizontalAlignment(JLabel.CENTER);

		int style = Font.ITALIC;
		Font font = new Font("Lucida Bright", style, 20);

		// setting the menu bar
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
		// Create the menu bar.
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.ORANGE);
		menuBar.setOpaque(true);
		// Build the first menu.
		JMenu options = new JMenu("Options");
		options.setForeground(Color.black);
		options.setFont(font);
		menuBar.add(options);

		// a group of JMenuItems
		save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, ActionEvent.ALT_MASK));

		save.setActionCommand("Save");
		save.addActionListener(gui);
		options.add(save);
		create = new JMenuItem("Create Fighter");
		create.setActionCommand("Create Fighter");
		create.addActionListener(gui);
		options.add(create);

		// a submenu for displaying the items
		options.addSeparator();
		submenu = new JMenu("items");
		senzu = new JMenuItem("Senzu Beans " + player.getSenzuBeans());
		submenu.add(senzu);
		dragon = new JMenuItem("Dragon Balls " + player.getDragonBalls());
		submenu.add(dragon);
		options.add(submenu);
		// a sub menu to switch between fighters
		switchf = new JMenu("Switch Fighters");
		switchf.setFont(font);
		switchf.setForeground(Color.black);
		// switchf.addMenuListener(gui);
		// adding the list of fighters
		group = new ButtonGroup();

		for (int i = 0; i < player.getFighters().size(); i++) {
			JRadioButtonMenuItem rbMenuItem;
			rbMenuItem = new JRadioButtonMenuItem(player.getFighters().get(i)
					.getName());
			rbMenuItem.addActionListener(gui);
			rbMenuItem.setActionCommand("Fighter Selected");

			group.add(rbMenuItem);
			if (player.getFighters().get(i).equals(player.getActiveFighter()))
				rbMenuItem.setSelected(true);
			switchf.add(rbMenuItem);
		}
		menuBar.add(switchf);
		// a sub menu to upgrade the active fighter
		submenu = new JMenu("Upgrade");
		submenu.setFont(font);
		submenu.setForeground(Color.black);
		maxh = new JMenuItem("Max Health Points "
				+ player.getActiveFighter().getMaxHealthPoints());
		maxh.addActionListener(gui);
		maxh.setActionCommand("Max Health Points");

		submenu.add(maxh);
		maxk = new JMenuItem("Max Ki " + player.getActiveFighter().getMaxKi());
		maxk.setActionCommand("Max Ki");

		maxk.addActionListener(gui);
		submenu.add(maxk);
		maxs = new JMenuItem("Max Stamina "
				+ player.getActiveFighter().getMaxStamina());
		maxs.setActionCommand("Max Stamina");
		maxs.addActionListener(gui);
		submenu.add(maxs);
		pd = new JMenuItem("Physical Damage "
				+ player.getActiveFighter().getPhysicalDamage());
		pd.setActionCommand("Physical Damage");

		pd.addActionListener(gui);
		submenu.add(pd);
		bd = new JMenuItem("Blast Damage "
				+ player.getActiveFighter().getBlastDamage());
		bd.setActionCommand("Blast Damage");

		bd.addActionListener(gui);
		submenu.add(bd);
		menuBar.add(submenu);
		// a sub menu to assign attack to the active fighter
		submenu = new JMenu("Assign Attack ");
		// adding the attacks
		menuItem = new JMenuItem("Super Attack");
		menuItem.addActionListener(gui);
		submenu.add(menuItem);
		menuItem = new JMenuItem("Ultimate Attack");
		menuItem.addActionListener(gui);
		submenu.add(menuItem);
		options.add(submenu);
		menuItem = new JMenuItem("Exit!");
		menuItem.addActionListener(gui);
		options.add(menuItem);
		this.setJMenuBar(menuBar);
		info = new JMenu("fighter's info");
		menuBar.add(info);
		info.setFont(font);
		info.setForeground(Color.black);
		ability = new JMenuItem("Ability Points "
				+ player.getActiveFighter().getAbilityPoints());
		xp = new JMenuItem("XP points "
				+ player.getActiveFighter().getXp());
		targetXp = new JMenuItem("Target XP "
				+ player.getActiveFighter().getTargetXp());
		info.add(ability);
		info.add(xp);
		info.add(targetXp);
		info.addSeparator();
		supera = new JMenuItem("Super Attacks");
		info.add(supera);
		for (int i = 0; i < player.getActiveFighter().getSuperAttacks().size(); i++) {
			supera = new JMenuItem(player.getActiveFighter().getSuperAttacks()
					.get(i).getName());		
			info.add(supera);


		}

		info.addSeparator();
		ultimatea = new JMenuItem("Ultimate Attacks");
		info.add(ultimatea);
		for (int i = 0; i < player.getActiveFighter().getUltimateAttacks()
				.size(); i++) {
			ultimatea = new JMenuItem(player.getActiveFighter()
					.getUltimateAttacks().get(i).getName());
			info.add(ultimatea);

		}

		// setDefaultLookAndFeelDecorated(true);
		setIconImage(new ImageIcon("Dragon_Ball.png").getImage());
		setVisible(true);
		validate();
		panel.setOpaque(false);
		

	}

	// navigating the player
	public void keyPressed(KeyEvent e) {

		int keycode = e.getKeyCode();
		if (keycode == KeyEvent.VK_UP)
			this.moveup();
		else if (keycode == KeyEvent.VK_DOWN)
			this.movedown();
		else if (keycode == KeyEvent.VK_RIGHT)
			this.moveright();
		else if (keycode == KeyEvent.VK_LEFT)
			this.moveleft();
		else
			e.consume();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	public void moveup() {
		if (row != 0) {
			jlabels[row][col].setBackground(Color.DARK_GRAY);
			jlabels[row][col].setIcon(null);
			jlabels[row][col].setToolTipText(null);
			row--;
			world.moveUp();
			if ((jlabels[row][col].getBackground()).equals(Color.DARK_GRAY))
				jlabels[row][col].setBackground(Color.white);
			jlabels[row][col].setIcon(fighter);
			jlabels[row][col].setHorizontalAlignment(JLabel.CENTER);

			displayinfo(player.getName(), player.getActiveFighter().getLevel(),
					player.getActiveFighter().getName());

			validate();
		}
	}

	public void movedown() {
		if (row != 9) {
			jlabels[row][col].setBackground(Color.DARK_GRAY);
			jlabels[row][col].setIcon(null);
			jlabels[row][col].setToolTipText(null);

			row++;
			world.moveDown();

			if ((jlabels[row][col].getBackground()).equals(Color.DARK_GRAY))
				jlabels[row][col].setBackground(Color.white);
			jlabels[row][col].setIcon(fighter);
			jlabels[row][col].setHorizontalAlignment(JLabel.CENTER);

			displayinfo(player.getName(), player.getActiveFighter().getLevel(),
					player.getActiveFighter().getName());

			validate();
		}
	}

	public void moveright() {
		if (col != 9) {
			jlabels[row][col].setBackground(Color.DARK_GRAY);
			jlabels[row][col].setIcon(null);
			jlabels[row][col].setToolTipText(null);

			col++;
			world.moveRight();
			if ((jlabels[row][col].getBackground()).equals(Color.DARK_GRAY))
				jlabels[row][col].setBackground(Color.white);
			jlabels[row][col].setIcon(fighter);
			jlabels[row][col].setHorizontalAlignment(JLabel.CENTER);

			displayinfo(player.getName(), player.getActiveFighter().getLevel(),
					player.getActiveFighter().getName());

			validate();
		}
	}

	public void moveleft() {
		if (col != 0) {
			jlabels[row][col].setBackground(Color.DARK_GRAY);
			jlabels[row][col].setIcon(null);
			jlabels[row][col].setToolTipText(null);

			col--;
			world.moveLeft();
			if ((jlabels[row][col].getBackground()).equals(Color.DARK_GRAY))
				jlabels[row][col].setBackground(Color.white);
			jlabels[row][col].setIcon(fighter);
			jlabels[row][col].setHorizontalAlignment(JLabel.CENTER);

			displayinfo(player.getName(), player.getActiveFighter().getLevel(),
					player.getActiveFighter().getName());
			validate();

		}
	}

	// displaying events in the game
	public void display(String s, Icon icon) {
		JOptionPane.showMessageDialog(this, s, "Notification",
				JOptionPane.INFORMATION_MESSAGE, icon);

	}

	public void display(String s) {
		JOptionPane.showMessageDialog(this, s, "Notification",
				JOptionPane.INFORMATION_MESSAGE);

	}

	public void update() {
		senzu.setText("Senzu Beans " + player.getSenzuBeans());
		ability.setText("Ability Points "
				+ player.getActiveFighter().getAbilityPoints());
		xp.setText("XP Points "
				+ player.getActiveFighter().getXp());
		targetXp.setText("Target XP "
				+ player.getActiveFighter().getTargetXp());
		dragon.setText("Dragon Balls " + player.getDragonBalls());
		maxh.setText("Max Health  Points "
				+ player.getActiveFighter().getMaxHealthPoints());
		maxk.setText("Maximum  Ki " + player.getActiveFighter().getMaxKi());
		maxs.setText("Maximum Stamina "
				+ player.getActiveFighter().getMaxStamina());
		pd.setText("Physical  Damage "
				+ player.getActiveFighter().getPhysicalDamage());
		bd.setText("Blast Damage " + player.getActiveFighter().getBlastDamage());
		jlabels[row][col].setBackground(Color.DARK_GRAY);
		jlabels[row][col].setIcon(null);
		jlabels[row][col].setToolTipText(null);
		row = world.getPlayerRow();
		col = world.getPlayerColumn();
		String race = "";
		if (player.getActiveFighter() instanceof Saiyan)
			race = "goku";
		else if (player.getActiveFighter() instanceof Earthling)
			race = "earthling";
		else if (player.getActiveFighter() instanceof Namekian)
			race = "namekian";
		else if (player.getActiveFighter() instanceof Frieza)
			race = "frieza";
		else
			race = "majin";
		fighter = new ImageIcon(race + ".jpg");
		if ((jlabels[row][col].getBackground()).equals(Color.DARK_GRAY))
			jlabels[row][col].setBackground(Color.white);
		jlabels[row][col].setIcon(fighter);
		jlabels[row][col].setHorizontalAlignment(JLabel.CENTER);

		displayinfo(player.getName(), player.getActiveFighter().getLevel(),
				player.getActiveFighter().getName());

		validate();

	}

	public void updateinfo() {
		info.removeAll();
		ability = new JMenuItem("Ability Points "
				+ player.getActiveFighter().getAbilityPoints());
		xp = new JMenuItem("XP Points "
				+ player.getActiveFighter().getXp());
		targetXp = new JMenuItem("Target XP "
				+ player.getActiveFighter().getTargetXp());
		info.add(ability);
		info.add(xp);
		info.add(targetXp);
		info.addSeparator();
		supera = new JMenuItem("Super Attacks");
		info.add(supera);
		for (int i = 0; i < player.getActiveFighter().getSuperAttacks().size(); i++) {
			supera = new JMenuItem(player.getActiveFighter().getSuperAttacks()
					.get(i).getName());
			info.add(supera);

		}

		info.addSeparator();
		ultimatea = new JMenuItem("Ultimate Attacks");
		info.add(ultimatea);
		for (int i = 0; i < player.getActiveFighter().getUltimateAttacks()
				.size(); i++) {
			ultimatea = new JMenuItem(player.getActiveFighter()
					.getUltimateAttacks().get(i).getName());
			info.add(ultimatea);

		}
		validate();

	}

	public void updateFighters() {

		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem(player
				.getFighters().get(player.getFighters().size() - 1).getName());
		rbMenuItem.addActionListener(gui);
		rbMenuItem.setActionCommand("Fighter Selected");
		group.add(rbMenuItem);
		switchf.add(rbMenuItem);

	}

	// displaying the player info
	public void displayinfo(String name, int level, String fname) {
		jlabels[row][col].setToolTipText("<html>" + "Name: " + name + "<br>"
				+ " Level: " + level + "<br>" + "Fighter Name: " + fname
				+ "</html>");
	}
	/*
	 * public String getswitchfselected(){ return getSelectedButtonText(group);
	 * } public String getSelectedButtonText(ButtonGroup buttonGroup) { for
	 * (Enumeration<AbstractButton> buttons = buttonGroup.getElements();
	 * buttons.hasMoreElements();) { AbstractButton button =
	 * buttons.nextElement();
	 * 
	 * if (button.isSelected()) { return button.getText(); } }
	 * 
	 * return null; } public ButtonGroup getGroup() { return group;
	 * 
	 * }
	 */

}
