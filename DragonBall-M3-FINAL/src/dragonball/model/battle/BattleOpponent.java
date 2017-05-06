package dragonball.model.battle;

import java.io.Serializable;

public interface BattleOpponent extends Serializable {
	void onAttackerTurn();

	void onDefenderTurn();
}
