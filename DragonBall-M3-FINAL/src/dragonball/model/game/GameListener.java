package dragonball.model.game;

import java.io.IOException;
import java.io.Serializable;
import java.util.EventListener;

import dragonball.model.battle.BattleEvent;
import dragonball.model.cell.Collectible;
import dragonball.model.dragon.Dragon;

public interface GameListener extends EventListener, Serializable {
	void onDragonCalled(Dragon dragon);

	void onCollectibleFound(Collectible collectible);

	void onBattleEvent(BattleEvent e) throws IOException;
}
