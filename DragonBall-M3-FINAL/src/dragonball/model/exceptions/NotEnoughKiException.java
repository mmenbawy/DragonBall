package dragonball.model.exceptions;

@SuppressWarnings("serial")
public class NotEnoughKiException extends NotEnoughResourcesException {
	private int requiredKi;
	private int availableKi;

	public NotEnoughKiException(int requiredKi, int availableKi) {
		super("Required: " + requiredKi + ", Ki: "
				+ availableKi + "");
		this.requiredKi = requiredKi;
		this.availableKi = availableKi;
	}

	public int getRequiredKi() {
		return requiredKi;
	}

	public int getAvailableKi() {
		return availableKi;
	}
	
	
}
