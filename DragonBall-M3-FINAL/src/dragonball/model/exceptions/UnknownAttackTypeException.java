package dragonball.model.exceptions;

@SuppressWarnings("serial")
public class UnknownAttackTypeException extends InvalidFormatException {

	private String unknownType;

	public UnknownAttackTypeException() {
	}

	public UnknownAttackTypeException(String arg0) {
		super(arg0);
	}

	public UnknownAttackTypeException(String sFile, int sLine, String type) {
		super(sFile, sLine);
		this.unknownType = type;
	}

	public UnknownAttackTypeException(String message, String sFile, int sLine,
			String type) {
		super(message, sFile, sLine);
		this.unknownType = type;
	}

	public String getUnknownType() {
		return unknownType;
	}

}
