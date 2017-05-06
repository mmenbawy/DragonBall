package dragonball.model.world;

import java.util.EventListener;

import dragonball.model.cell.Collectible;
import dragonball.model.character.fighter.NonPlayableFighter;

public interface WorldListener extends EventListener {
	void onFoeEncountered(NonPlayableFighter foe);

	void onCollectibleFound(Collectible collectible);
}
