package dragonball.model.character.fighter;

import java.util.ArrayList;

import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.character.PlayableCharacter;

public abstract class PlayableFighter extends Fighter implements PlayableCharacter {
	public static final int INITIAL_LEVEL = 1;
	public static final int INITIAL_XP = 0;
	public static final int INITIAL_TARGET_XP = 10;
	public static final int INITIAL_ABILITY_POINTS = 0;

	private int xp;
	private int targetXp;
	private int abilityPoints;

	public PlayableFighter(String name, int maxHealthPoints, int blastDamage, int physicalDamage, int maxKi,
			int maxStamina) {
		this(name, maxHealthPoints, blastDamage, physicalDamage, maxKi, maxStamina, new ArrayList<SuperAttack>(),
				new ArrayList<UltimateAttack>());
	}

	public PlayableFighter(String name, int maxHealthPoints, int blastDamage, int physicalDamage, int maxKi,
			int maxStamina, ArrayList<SuperAttack> superAttacks, ArrayList<UltimateAttack> ultimateAttacks) {
		this(name, INITIAL_LEVEL, INITIAL_XP, INITIAL_TARGET_XP, maxHealthPoints, blastDamage, physicalDamage,
				INITIAL_ABILITY_POINTS, maxKi, maxStamina, superAttacks, ultimateAttacks);
	}

	public PlayableFighter(String name, int level, int xp, int targetXp, int maxHealthPoints, int blastDamage,
			int physicalDamage, int abilityPoints, int maxKi, int maxStamina, ArrayList<SuperAttack> superAttacks,
			ArrayList<UltimateAttack> ultimateAttacks) {
		super(name, level, maxHealthPoints, blastDamage, physicalDamage, maxKi, maxStamina, superAttacks,
				ultimateAttacks);
		this.xp = xp;
		this.targetXp = targetXp;
		this.abilityPoints = abilityPoints;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;

		// when target xp is reached, keep leveling up until the gained xp no
		// longer reaches the target. on each level up, target increases by 20
		// and 2 ability points are gained
		while (this.xp >= targetXp) {
			setLevel(getLevel() + 1);
			this.xp -= targetXp;
			targetXp += 20;
			abilityPoints += 2;
		}
	}

	public int getTargetXp() {
		return targetXp;
	}

	public void setTargetXp(int targetXp) {
		this.targetXp = targetXp;
	}

	public int getAbilityPoints() {
		return abilityPoints;
	}

	public void setAbilityPoints(int abilityPoints) {
		this.abilityPoints = abilityPoints;
	}
}
