package dragonball.model.attack;

import java.io.Serializable;

import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.exceptions.NotEnoughKiException;

public abstract class Attack implements Serializable {
	private String name;
	private int damage;

	public Attack(String name, int damage) {
		this.name = name;
		this.damage = damage;
	}

	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}

	public abstract int getAppliedDamage(BattleOpponent attacker);

	public void onUse(BattleOpponent attacker, BattleOpponent defender, boolean defenderBlocking) throws NotEnoughKiException {
		Fighter defenderFighter = (Fighter) defender;

		// get the applied damage of the attack, taking into consideration
		// the fighter's attributes
		int damage = getAppliedDamage(attacker);

		// if the fighter is a transformed saiyan, increase damage by 25%
		if (attacker instanceof Saiyan && ((Saiyan) attacker).isTransformed()) {
			damage = (int) (damage * 1.25);
		}

		// if opponent is in block mode, consume stamina first before applying
		// remaining damage
		if (defenderBlocking) {
			while (damage > 0 && defenderFighter.getStamina() > 0) {
				defenderFighter.setStamina(defenderFighter.getStamina() - 1);
				damage -= damage >= 100 ? 100 : damage;
			}
		}
		defenderFighter.setHealthPoints(defenderFighter.getHealthPoints() - damage);
	}
	public String toString(){
		return this.name;
	}
}
