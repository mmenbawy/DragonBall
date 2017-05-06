package dragonball.model.exceptions;

@SuppressWarnings("serial")
public class NotEnoughAbilityPointsException extends NotEnoughResourcesException {
	public NotEnoughAbilityPointsException() {
		super("Ability Points: 0, No more ability points could be allocated.");
	}
}
