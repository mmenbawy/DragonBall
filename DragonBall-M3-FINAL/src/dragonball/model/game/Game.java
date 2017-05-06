package dragonball.model.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

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
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.player.Player;
import dragonball.model.player.PlayerListener;
import dragonball.model.world.World;
import dragonball.model.world.WorldListener;

public class Game implements Serializable, PlayerListener, WorldListener,
BattleListener {
	private Player player;
	private World world;
	private GameState state;
	private ArrayList<NonPlayableFighter> weakFoes;
	private ArrayList<NonPlayableFighter> strongFoes;
	private ArrayList<Attack> attacks,newAttacks;
	private ArrayList<Dragon> dragons;
	private transient GameListener listener;
	private String lastSavedFile;
	private int lastLevel,newLevel,oldAbilityPoints;

	public int getLastLevel() {
		return lastLevel;
	}

	public int getNewLevel() {
		return newLevel;
	}

	public int getOldAbilityPoints() {
		return oldAbilityPoints;
	}

	public String getLastSavedFile() {
		return lastSavedFile;
	}

	public Game() throws MissingFieldException, UnknownAttackTypeException {
		player = new Player("Player");
		world = new World();
		state = GameState.WORLD;
		weakFoes = new ArrayList<>();
		strongFoes = new ArrayList<>();
		attacks = new ArrayList<>();
		dragons = new ArrayList<>();
		lastSavedFile = "";

		try {
			loadAttacks("Database-Attacks.csv");
		} catch (dragonball.model.exceptions.InvalidFormatException e) {
			loadAttacks("Database-Attacks-aux.csv");
		}

		try {
			loadFoes("Database-Foes-Range1.csv");
		} catch (MissingFieldException e) {
			loadFoes("Database-Foes-aux.csv");
		}
		try {
			loadDragons("Database-Dragons.csv");
		} catch (MissingFieldException e) {
			loadDragons("Database-Dragons-aux.csv");
		}

		world.generateMap(weakFoes, strongFoes);

		player.setListener(this);
		world.setListener(this);
	}

	public Player getPlayer() {
		return player;
	}

	public World getWorld() {
		return world;
	}

	public GameState getState() {
		return state;
	}

	public ArrayList<NonPlayableFighter> getWeakFoes() {
		return weakFoes;
	}

	public ArrayList<NonPlayableFighter> getStrongFoes() {
		return strongFoes;
	}

	public ArrayList<Attack> getAttacks() {
		return attacks;
	}

	public ArrayList<Dragon> getDragons() {
		return dragons;
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
			if (strong) {
				strongFoes.add(foe);
			} else {
				weakFoes.add(foe);
			}
		}
	}

	public void loadDragons(String filePath) throws MissingFieldException {
		String[][] lines = loadCSV(filePath);

		for (int i = 0; i < lines.length; i += 3) {

			// if number of fields is less than expected.
			if (lines[i].length < 3)
				throw new MissingFieldException("File: " + filePath
						+ ", Line: " + (i + 1) + ", Expected: 3, Missing: "
						+ (3 - lines[i].length), filePath, i + 1,
						3 - lines[i].length);

			String name = lines[i][0];
			int senzuBeans = Integer.parseInt(lines[i][1]);
			int dragonsBalls = Integer.parseInt(lines[i][2]);
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

			Dragon dragon = new Dragon(name, superAttacks, ultimateAttacks,
					senzuBeans, dragonsBalls);
			dragons.add(dragon);
		}
	}

	@Override
	public void onDragonCalled() {
		state = GameState.DRAGON;

		Dragon dragon = dragons.get(new Random().nextInt(dragons.size()));

		notifyOnDragonCalled(dragon);
	}

	@Override
	public void onWishChosen(DragonWish wish) {
		state = GameState.WORLD;
		PlayableFighter activeFighter=this.getPlayer().getActiveFighter();
		Player player=this.getPlayer();

		switch(wish.getType()){
		case SENZU_BEANS:
			player.setSenzuBeans(player.getSenzuBeans()+wish.getSenzuBeans());break;

		case ABILITY_POINTS:
			activeFighter.setAbilityPoints(activeFighter.getAbilityPoints()+wish.getAbilityPoints());break;

		case SUPER_ATTACK:
			if(!(player.getSuperAttacks().contains(wish.getSuperAttack())))
				player.getSuperAttacks().add(wish.getSuperAttack());break;
				//JOptionPane.showMessageDialog(dragonView,message+ superAttack.getName()+" attack.",note, JOptionPane.INFORMATION_MESSAGE,new ImageIcon("superAttackm.png"));break;

		case ULTIMATE_ATTACK:
			if(!(player.getUltimateAttacks().contains(wish.getUltimateAttack())))
				player.getUltimateAttacks().add(wish.getUltimateAttack());break;
		default: break;	

		}
	}

	@Override
	public void onFoeEncountered(final NonPlayableFighter foe) {
		state = GameState.BATTLE;

		Battle battle = new Battle(player.getActiveFighter(), foe);
		// handle winning and losing in a battle
		battle.setListener(this);
		// start the battle
		battle.start();
	}

	@Override
	public void onCollectibleFound(Collectible collectible) {
		switch (collectible) {
		case SENZU_BEAN:
			player.setSenzuBeans(player.getSenzuBeans() + 1);
			notifyOnCollectibleFound(collectible);
			break;
		case DRAGON_BALL:
			player.setDragonBalls(player.getDragonBalls() + 1);
			notifyOnCollectibleFound(collectible);

			player.callDragon();
			break;
		}
	}

	@Override
	public void onBattleEvent(BattleEvent e){
		NonPlayableFighter foe = (NonPlayableFighter) ((Battle) e.getSource())
				.getFoe();

		if (e.getType() == BattleEventType.ENDED) {
			PlayableFighter me = player.getActiveFighter();
			// if i won
			if (e.getWinner() == me) {
				// gain xp
				lastLevel = player.getActiveFighter().getLevel();
				oldAbilityPoints = player.getActiveFighter().getAbilityPoints();
				me.setXp(me.getXp() + foe.getLevel() * 5);
				newLevel = player.getActiveFighter().getLevel();

				// learn opponents super and ultimate attacks
				newAttacks = new ArrayList<>();
				for (SuperAttack superAttack : foe.getSuperAttacks()) {
					if (!player.getSuperAttacks().contains(superAttack)) {
						player.getSuperAttacks().add(superAttack);
						newAttacks.add(superAttack);
					}
				}
				for (UltimateAttack ultimateAttack : foe.getUltimateAttacks()) {
					if (!player.getUltimateAttacks().contains(ultimateAttack)) {
						player.getUltimateAttacks().add(ultimateAttack);
						newAttacks.add(ultimateAttack);
					}
				}

				// if opponent is boss
				if (foe.isStrong()) {
					// increment explored maps by 1
					player.setExploredMaps(player.getExploredMaps() + 1);

					// reload foes in case range changed
					int foesRange = (player.getMaxFighterLevel() - 1) / 10 + 1;
					try {
						loadFoes("." + File.separator + "Database-Foes-Range"
								+ foesRange + ".csv");
					} catch (MissingFieldException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// regenerate map
					world.generateMap(weakFoes, strongFoes);
				}
				state = GameState.WORLD;
				// if my opponent won
			} else if (e.getWinner() == foe) {
				try {
					// load last saved game.
					String temp = lastSavedFile;
					((GUI)listener).loadLast(lastSavedFile);
					lastSavedFile = temp;
				} catch (FileNotFoundException exp) {
					// regenerate a new map
					world = new World();
					world.generateMap(weakFoes, strongFoes);
					world.setListener(this);
					state = GameState.WORLD;
					
				} catch (Exception exp) {
					// do nothing
				}
			}

		}

		try {
			notifyOnBattleEvent(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public ArrayList<Attack> getNewAttacks() {
		return newAttacks;
	}

	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	public void notifyOnCollectibleFound(Collectible collectible) {
		if (listener != null) {
			listener.onCollectibleFound(collectible);
		}
	}

	public void notifyOnBattleEvent(BattleEvent e) throws IOException {
		if (listener != null) {
			listener.onBattleEvent(e);
		}
	}

	public void notifyOnDragonCalled(Dragon dragon) {
		if (listener != null) {
			listener.onDragonCalled(dragon);
		}
	}

	public void save(String path) throws IOException {
		// Create an output stream to be able to write the file.
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				new File(path)));
		// Serializing the object and writing it on the hard disk.
		oos.writeObject(this);
		// Updating the last saved file path to the current file.
		lastSavedFile = path;
		// close the output stream.
		oos.close();
	}

	public void load(String path) throws ClassNotFoundException, IOException {
		// Create an input stream to be able to read the file.
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				new File(path)));
		// Deserializing the object and loading into a game type object.
		Game temp = (Game) ois.readObject();
		// Adjusting the instance variables.
		this.player = temp.player;
		this.world = temp.world;
		this.state = temp.state;
		this.weakFoes = temp.weakFoes;
		this.strongFoes = temp.strongFoes;
		this.attacks = temp.attacks;
		this.dragons = temp.dragons;
		this.listener = temp.listener;
		this.lastSavedFile = temp.lastSavedFile;
		// closing the stream.
		ois.close();
		// reset the listeners
		world.setListener(this);
		player.setListener(this);
		// clear the temp variable
		temp = null;
	}

}
