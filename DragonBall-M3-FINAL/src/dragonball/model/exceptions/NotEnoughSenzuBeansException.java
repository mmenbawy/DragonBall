package dragonball.model.exceptions;

@SuppressWarnings("serial")
public class NotEnoughSenzuBeansException extends NotEnoughResourcesException {
	public NotEnoughSenzuBeansException() {
		super("SenzuBeans: 0, There are no SenzuBeans available to be used.");
	}
}
