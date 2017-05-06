package dragonball.model.cell;

public class CollectibleCell extends Cell {
	private Collectible collectible;

	public CollectibleCell(Collectible collectible) {
		this.collectible = collectible;
	}

	public Collectible getCollectible() {
		return collectible;
	}

	@Override
	public void onStep() {
		notifyOnCollectibleFound(collectible);
	}

	@Override
	public String toString() {
		return "[" + Character.toLowerCase(collectible.name().charAt(0)) + "]";
	}
}
