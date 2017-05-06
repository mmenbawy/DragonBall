package dragonball.model.dragon;

import java.util.EventObject;

import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.UltimateAttack;

@SuppressWarnings("serial")
public class DragonWish extends EventObject {
	private DragonWishType type;
	private int senzuBeans;
	private int abilityPoints;
	private SuperAttack superAttack;
	private UltimateAttack ultimateAttack;

	// constructor for others
	public DragonWish(Dragon dragon, DragonWishType type) {
		super(dragon);
		this.type = type;
	}

	// constructor for SENZU_BEANS, ABILITY_POINTS
	public DragonWish(Dragon dragon, DragonWishType type, int senzuBeansOrAbilityPoints) {
		this(dragon, type);

		switch (type) {
		case SENZU_BEANS:
			senzuBeans = senzuBeansOrAbilityPoints;
			break;
		case ABILITY_POINTS:
			abilityPoints = senzuBeansOrAbilityPoints;
			break;
		default:
			break;
		}
	}

	// constructor for SUPER_ATTACK
	public DragonWish(Dragon dragon, DragonWishType type, SuperAttack superAttack) {
		this(dragon, type);
		this.superAttack = superAttack;
	}

	// constructor for ULTIMATE_ATTACK
	public DragonWish(Dragon dragon, DragonWishType type, UltimateAttack ultimateAttack) {
		this(dragon, type);
		this.ultimateAttack = ultimateAttack;
	}

	public DragonWishType getType() {
		return type;
	}

	public int getSenzuBeans() {
		return senzuBeans;
	}

	public int getAbilityPoints() {
		return abilityPoints;
	}

	public SuperAttack getSuperAttack() {
		return superAttack;
	}

	public UltimateAttack getUltimateAttack() {
		return ultimateAttack;
	}
}
