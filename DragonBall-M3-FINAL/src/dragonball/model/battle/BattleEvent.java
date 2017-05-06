package dragonball.model.battle;

import java.util.EventObject;

import dragonball.model.attack.Attack;
import dragonball.model.cell.Collectible;

@SuppressWarnings("serial")
public class BattleEvent extends EventObject {
	private BattleEventType type;
	private BattleOpponent currentOpponent;
	private BattleOpponent winner;
	private Attack attack;
	private Collectible collectible;

	// constructor for ENDED
	public BattleEvent(Battle battle, BattleEventType type, BattleOpponent winner) {
		this(battle, type);
		this.winner = winner;
	}

	// constructor for ATTACK
	public BattleEvent(Battle battle, BattleEventType type, Attack attack) {
		this(battle, type);
		this.attack = attack;
	}

	// constructor for USE
	public BattleEvent(Battle battle, BattleEventType type, Collectible collectible) {
		this(battle, type);
		this.collectible = collectible;
	}

	// constructor for others
	public BattleEvent(Battle battle, BattleEventType type) {
		super(battle);
		this.type = type;
		this.currentOpponent = battle.getAttacker();
	}

	public BattleEventType getType() {
		return type;
	}

	public BattleOpponent getCurrentOpponent() {
		return currentOpponent;
	}

	public BattleOpponent getWinner() {
		return winner;
	}

	public Attack getAttack() {
		return attack;
	}

	public Collectible getCollectible() {
		return collectible;
	}
}
