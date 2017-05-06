package dragonball.model.player;

import java.io.Serializable;
import java.util.ArrayList;

import dragonball.model.attack.Attack;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.SuperSaiyan;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Frieza;
import dragonball.model.character.fighter.Majin;
import dragonball.model.character.fighter.Namekian;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.dragon.DragonWish;
import dragonball.model.exceptions.DuplicateAttackException;
import dragonball.model.exceptions.MaximumAttacksLearnedException;
import dragonball.model.exceptions.NotASaiyanException;
import dragonball.model.exceptions.NotEnoughAbilityPointsException;

public class Player implements Serializable {
	public static final int NUM_DRAGON_BALLS = 7;

	private String name;
	private ArrayList<PlayableFighter> fighters;
	private ArrayList<SuperAttack> superAttacks;
	private ArrayList<UltimateAttack> ultimateAttacks;
	private int senzuBeans;
	private int dragonBalls;
	private PlayableFighter activeFighter;
	private int exploredMaps;
	private PlayerListener listener;

	public Player(String name) {
		this(name, new ArrayList<PlayableFighter>(),
				new ArrayList<SuperAttack>(), new ArrayList<UltimateAttack>(),
				0, 0, null, 0);
	}

	public Player(String name, ArrayList<PlayableFighter> fighters,
			ArrayList<SuperAttack> superAttacks,
			ArrayList<UltimateAttack> ultimateAttacks, int senzuBeans,
			int dragonBalls, PlayableFighter activeFighter, int exploredMaps) {
		this.name = name;
		this.fighters = fighters;
		this.superAttacks = superAttacks;
		this.ultimateAttacks = ultimateAttacks;
		this.senzuBeans = senzuBeans;
		this.dragonBalls = dragonBalls;
		this.activeFighter = activeFighter;
		this.exploredMaps = exploredMaps;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<PlayableFighter> getFighters() {
		return fighters;
	}

	public void setFighters(ArrayList<PlayableFighter> fighters) {
		this.fighters = fighters;
	}

	public ArrayList<SuperAttack> getSuperAttacks() {
		return superAttacks;
	}

	public void setSuperAttacks(ArrayList<SuperAttack> superAttacks) {
		this.superAttacks = superAttacks;
	}

	public ArrayList<UltimateAttack> getUltimateAttacks() {
		return ultimateAttacks;
	}

	public void setUltimateAttacks(ArrayList<UltimateAttack> ultimateAttacks) {
		this.ultimateAttacks = ultimateAttacks;
	}

	public int getSenzuBeans() {
		return senzuBeans;
	}

	public void setSenzuBeans(int senzuBeans) {
		this.senzuBeans = senzuBeans;
	}

	public int getDragonBalls() {
		return dragonBalls;
	}

	public void setDragonBalls(int dragonBalls) {
		this.dragonBalls = dragonBalls;
	}

	public PlayableFighter getActiveFighter() {
		return activeFighter;
	}

	public void setActiveFighter(PlayableFighter activeFighter) {
		if (fighters.contains(activeFighter)) {
			this.activeFighter = activeFighter;
		}
	}

	public int getExploredMaps() {
		return exploredMaps;
	}

	public void setExploredMaps(int exploredMaps) {
		this.exploredMaps = exploredMaps;
	}

	// used to decide which map range to use
	public int getMaxFighterLevel() {
		int maxLevel = PlayableFighter.INITIAL_LEVEL;
		for (PlayableFighter fighter : fighters) {
			if (fighter.getLevel() > maxLevel) {
				maxLevel = fighter.getLevel();
			}
		}
		return maxLevel;
	}

	public void createFighter(char race, String name) {
		PlayableFighter fighter = null;
		switch (race) {
		case 'E':
			fighter = new Earthling(name);
			break;
		case 'S':
			fighter = new Saiyan(name);
			break;
		case 'N':
			fighter = new Namekian(name);
			break;
		case 'F':
			fighter = new Frieza(name);
			break;
		case 'M':
			fighter = new Majin(name);
			break;

		}

		if (fighter != null) {
			fighters.add(fighter);

			if (fighters.size() == 1) {
				activeFighter = fighter;
			}
		}
	}

	public void upgradeFighter(PlayableFighter fighter, char fighterAttribute) throws NotEnoughAbilityPointsException {
		if (!fighters.contains(fighter)) {
			return;
		}

		if (fighter.getAbilityPoints() > 0) {
			switch (fighterAttribute) {
			case 'H':
				fighter.setMaxHealthPoints(fighter.getMaxHealthPoints() + 50);
				break;
			case 'B':
				fighter.setBlastDamage(fighter.getBlastDamage() + 50);
				break;
			case 'P':
				fighter.setPhysicalDamage(fighter.getPhysicalDamage() + 50);
				break;
			case 'K':
				fighter.setMaxKi(fighter.getMaxKi() + 1);
				break;
			case 'S':
				fighter.setMaxStamina(fighter.getMaxStamina() + 1);
				break;
			default:
				return;
			}

			fighter.setAbilityPoints(fighter.getAbilityPoints() - 1);
		}else{
			throw new NotEnoughAbilityPointsException();
		}
	}

	public void assignAttack(PlayableFighter fighter, SuperAttack newAttack,
			SuperAttack oldAttack) throws MaximumAttacksLearnedException, DuplicateAttackException, NotASaiyanException {
		assignAttack(fighter, fighter.getSuperAttacks(), newAttack, oldAttack,
				PlayableFighter.MAX_SUPER_ATTACKS);
	}

	public void assignAttack(PlayableFighter fighter, UltimateAttack newAttack,
			UltimateAttack oldAttack) throws MaximumAttacksLearnedException, DuplicateAttackException, NotASaiyanException {
		assignAttack(fighter, fighter.getUltimateAttacks(), newAttack,
				oldAttack, PlayableFighter.MAX_ULTIMATE_ATTACKS);
	}

	@SuppressWarnings("unchecked")
	private <T extends Attack> void assignAttack(PlayableFighter fighter,
			ArrayList<T> attacks, Attack newAttack, Attack oldAttack, int max)
			throws MaximumAttacksLearnedException, DuplicateAttackException,
			NotASaiyanException {

		for (Attack temp : attacks) {
			if (temp.getName().equals(newAttack.getName()))
				throw new DuplicateAttackException(newAttack);
		}

		if (attacks.size() < max || oldAttack != null) {
			if (!(fighter instanceof Saiyan)
					&& newAttack instanceof SuperSaiyan)
				throw new NotASaiyanException();
			int index = attacks.size();
			// if replacing an already assigned attack
			if (oldAttack != null) {
				index = attacks.indexOf(oldAttack);
				attacks.remove(oldAttack);
			}
			attacks.add(index, (T) newAttack);
		} else {
			throw new MaximumAttacksLearnedException();
		}
	}

	public void callDragon() {
		if (dragonBalls >= NUM_DRAGON_BALLS) {
			dragonBalls -= NUM_DRAGON_BALLS;

			notifyOnDragonCalled();
		}
	}

	public void chooseWish(DragonWish wish) {
		switch (wish.getType()) {
		case SENZU_BEANS:
			senzuBeans += wish.getSenzuBeans();
			break;
		case ABILITY_POINTS:
			activeFighter.setAbilityPoints(activeFighter.getAbilityPoints()
					+ wish.getAbilityPoints());
			break;
		case SUPER_ATTACK:
			superAttacks.add(wish.getSuperAttack());
			break;
		case ULTIMATE_ATTACK:
			ultimateAttacks.add(wish.getUltimateAttack());
			break;
		default:
			break;
		}

		notifyOnWishChosen(wish);
	}

	public void setListener(PlayerListener listener) {
		this.listener = listener;
	}

	public void notifyOnDragonCalled() {
		if (listener != null) {
			listener.onDragonCalled();
		}
	}

	public void notifyOnWishChosen(DragonWish wish) {
		if (listener != null) {
			listener.onWishChosen(wish);
		}
	}
}
