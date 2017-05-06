package dragonball.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dragonball.controller.GUI;
import dragonball.model.attack.Attack;
import dragonball.model.attack.MaximumCharge;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.SuperSaiyan;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleEventType;
import dragonball.model.battle.BattleListener;
import dragonball.model.cell.Collectible;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.NotEnoughKiException;
import dragonball.model.exceptions.NotEnoughSenzuBeansException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.GameState;
import dragonball.model.world.World;

public class TournamentView extends JFrame implements ActionListener, BattleListener{
	private GUI gui;
	private ArrayList<PlayableFighter> warriors;
	private ArrayList<NonPlayableFighter> foes;
	private Fighter[] participants;
	private ArrayList<Attack> attacks;
	private Fighter[] winners;
	private Battle[] battles;
	private JLabel[] info;
	private JButton next;
	private JPanel infoPanel;
	private TournamentBattleView tournamentBattleView;
	private static int battleIndex;
	public TournamentView(GUI gui,ArrayList<PlayableFighter> warriors) {
		battleIndex = 0;
		this.gui = gui;
		this.warriors = warriors;
		foes = new ArrayList<>();
		attacks = new ArrayList<>();
		participants = new Fighter[16];
		try {
			loadAttacks("Database-Attacks.csv");
		} catch (MissingFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownAttackTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadFoes("Database-Foes-Range1.csv");
		} catch (MissingFieldException e) {
			
		}
		NonPlayableFighter[] actualFoes = new NonPlayableFighter[16-warriors.size()];
		for(int i = 0; i<actualFoes.length;i++){
			int ran = (int)(Math.random()*foes.size());
			actualFoes[i] = foes.get(ran);
		}
		for(int i = 0;i<warriors.size();i++){
			int r =(int )(Math.random()*16);
			if (participants[r]==null)
				participants[r]=warriors.get(i);
			else{
				while(participants[r]!=null){
					r++;
					if (r==16)
						r=0;
				}
				participants[r]=warriors.get(i);
			}	
		}
		for(int i = 0;i<actualFoes.length;i++){
			int r =(int )(Math.random()*16);
			if (participants[r]==null)
				participants[r]=actualFoes[i];
			else{
				while(participants[r]!=null){
					r++;
					if (r==16)
						r=0;
				}
				participants[r]=actualFoes[i];
			}
		}
		
		
		winners = new Fighter[8];
		battles = new Battle[participants.length/2];
		for(int i = 0;i<participants.length;i+=2){
			if(participants[i] instanceof PlayableFighter || participants[i+1] instanceof PlayableFighter ){
				battles[i/2]=new Battle(participants[i],participants[i+1]);
				battleIndex = i/2;
				System.out.println(battles[i/2]);
			}
			else{
				int ran = (int)(Math.random()*2);
				winners[i/2]=participants[i+ran];
			}
		}
		info = new JLabel[8];
		infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(0,1));
		setLayout(new BorderLayout());
		next = new JButton("Next Battle");
		next.addActionListener(this);
		add(next,BorderLayout.SOUTH);
		
		for(int i = 0;i<8;i++){int style = Font.BOLD | Font.ITALIC ;
		Font font = new Font ("Narkisim", style , 30);
		Font font2 =new Font ("Goudy Stout", style, 15);
		JLabel l = new JLabel(participants[i*2].getName()+"    vs    "+participants[i*2+1].getName());
		l.setFont(font2);
		l.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		info[i] = l;
		}
		for(int i = 0;i<8;i++){
			infoPanel.add(info[i]);
		}
		add(infoPanel,BorderLayout.CENTER);
		setVisible(true);
	}
	public void loadFoes(String filePath) throws MissingFieldException {
		String[][] lines = loadCSV(filePath);

		for (int i = 0; i < lines.length; i += 3) {

			// if number of fields is less than expected.
			if (lines[i].length < 8)
				throw new MissingFieldException("File: " + filePath
						+ ", Line: " + (i + 1) + ", Expected: 8, Missing: "
						+ (8 - lines[i].length), filePath, i + 1,
						8 - lines[i].length);

			String name = lines[i][0];
			int level = Integer.parseInt(lines[i][1]);
			int maxHealthPoints = Integer.parseInt(lines[i][2]);
			int blastDamage = Integer.parseInt(lines[i][3]);
			int physicalDamage = Integer.parseInt(lines[i][4]);
			int maxKi = Integer.parseInt(lines[i][5]);
			int maxStamina = Integer.parseInt(lines[i][6]);
			boolean strong = Boolean.parseBoolean(lines[i][7]);
			ArrayList<SuperAttack> superAttacks = new ArrayList<>();
			ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<>();

			for (int j = 0; j < lines[i + 1].length; j++) {
				String attackName = lines[i + 1][j];
				for (Attack attack : attacks) {
					if (attack instanceof SuperAttack
							&& attack.getName().equalsIgnoreCase(attackName)) {
						superAttacks.add((SuperAttack) attack);
						break;
					}
				}
			}

			for (int j = 0; j < lines[i + 2].length; j++) {
				String attackName = lines[i + 2][j];
				for (Attack attack : attacks) {
					if (attack instanceof UltimateAttack
							&& attack.getName().equalsIgnoreCase(attackName)) {
						ultimateAttacks.add((UltimateAttack) attack);
						break;
					}
				}
			}

			NonPlayableFighter foe = new NonPlayableFighter(name, level,
					maxHealthPoints, blastDamage, physicalDamage, maxKi,
					maxStamina, strong, superAttacks, ultimateAttacks);
			foes.add(foe);
		}
	}
	public void loadAttacks(String filePath) throws MissingFieldException,
	UnknownAttackTypeException {
		String[][] lines = loadCSV(filePath);

		for (int i = 0; i < lines.length; i++) {

			// if number of fields is less than expected.
			if (lines[i].length < 3)
				throw new MissingFieldException("File: " + filePath
						+ ", Line: " + (i + 1) + ", Expected: 3, Missing: "
						+ (3 - lines[i].length), filePath, i + 1,
						3 - lines[i].length);

			Attack attack = null;
			String attackType = lines[i][0];
			String name = lines[i][1];
			int damage = Integer.parseInt(lines[i][2]);

			if (attackType.equalsIgnoreCase("SA")) {
				attack = new SuperAttack(name, damage);
			} else if (attackType.equalsIgnoreCase("UA")) {
				attack = new UltimateAttack(name, damage);
			} else if (attackType.equalsIgnoreCase("MC")) {
				attack = new MaximumCharge();
			} else if (attackType.equalsIgnoreCase("SS")) {
				attack = new SuperSaiyan();
			} else {
				// If the attack type given is not one of the four defined types.
				throw new UnknownAttackTypeException("File: " + filePath
						+ ", Line: " + (i + 1) + ", Attack Type: " + attackType
						+ " is undefined.", filePath, i + 1, attackType);
			}

			if (attack != null) {
				attacks.add(attack);
			}
		}
	}
	private String[][] loadCSV(String filePath) {
		ArrayList<String[]> lines = new ArrayList<>();

		BufferedReader reader = null;
		String line = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			while ((line = reader.readLine()) != null) {
				lines.add(line.split(","));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return lines.toArray(new String[][] {});
	}
	public void update(Fighter[] participants, Fighter[] winner){
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		switch (arg0.getActionCommand()){
		default :try {
			tournamentBattleView = new TournamentBattleView(battles[battleIndex], this);
			battleIndex++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		break;
		case "Attack":if(!(tournamentBattleView.getSelectedAttack() instanceof String)){
			Attack attack = (Attack) tournamentBattleView.getSelectedAttack();
			Battle battle  = tournamentBattleView.getBattle();
			try {
				battle.attack(attack);
				if(((Fighter)battle.getFoe()).getHealthPoints()!=0){
					battle.play();
				}
			} catch (NotEnoughKiException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
		break;
		case "Block" : tournamentBattleView.getBattle().block();
		tournamentBattleView.getBattle().play();
		break;
		}
	}
	
	public void onBattleEvent(BattleEvent e){
		
	}
}
