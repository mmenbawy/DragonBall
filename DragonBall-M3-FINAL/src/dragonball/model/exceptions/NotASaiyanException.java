package dragonball.model.exceptions;

@SuppressWarnings("serial")
public class NotASaiyanException extends InvalidAssignAttackException {
	public NotASaiyanException() {
		super("Cannot assign this attack to a non saiyan race.");
	}
}
