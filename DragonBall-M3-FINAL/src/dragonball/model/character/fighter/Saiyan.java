package dragonball.model.character.fighter;

import java.util.ArrayList;

import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.UltimateAttack;

public class Saiyan extends PlayableFighter {
	private boolean transformed;

	public Saiyan(String name) {
		super(name, 1000, 150, 100, 5, 3);
	}

	public Saiyan(String name, int level, int xp, int targetXp, int maxHealthPoints, int blastDamage,
			int physicalDamage, int abilityPoints, int maxKi, int maxStamina, ArrayList<SuperAttack> superAttacks,
			ArrayList<UltimateAttack> ultimateAttacks) {
		super(name, level, xp, targetXp, maxHealthPoints, blastDamage, physicalDamage, abilityPoints, maxKi, maxStamina,
				superAttacks, ultimateAttacks);
	}

	public boolean isTransformed() {
		return transformed;
	}

	public void setTransformed(boolean transformed) {
		this.transformed = transformed;
	}

	@Override
	public void onAttackerTurn() {
		super.onAttackerTurn();

		if (transformed) {
			setKi(getKi() - 1);

			if (getKi() == 0) {
				setStamina(0);
				transformed = false;
			}
		}
	}

	@Override
	public void onDefenderTurn() {
		super.onDefenderTurn();

		if (transformed) {
			setKi(getKi() - 1);

			if (getKi() == 0) {
				setStamina(0);
				transformed = false;
			}
		}
	}
}
