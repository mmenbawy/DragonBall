package dragonball.model.cell;

public enum Collectible {
	SENZU_BEAN, DRAGON_BALL;

	@Override
	public String toString() {
		String toString = "";
		for (String namePart : name().split("_")) {
			if (!toString.isEmpty()) {
				toString += " ";
			}
			toString += Character.toUpperCase(namePart.charAt(0)) + namePart.toLowerCase().substring(1);
		}
		return toString;
	}
}
