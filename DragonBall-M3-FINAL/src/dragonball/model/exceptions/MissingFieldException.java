package dragonball.model.exceptions;

@SuppressWarnings("serial")
public class MissingFieldException extends InvalidFormatException {
	private int missingFields;

	public int getMissingFields() {
		return missingFields;
	}

	public MissingFieldException() {
	}

	public MissingFieldException(String arg0) {
		super(arg0);
	}

	public MissingFieldException(String sFile, int sLine, int missingFields) {
		super(sFile, sLine);
		this.missingFields = missingFields;
	}

	public MissingFieldException(String message, String sFile, int sLine,
			int missingFields) {
		super(message, sFile, sLine);
		this.missingFields = missingFields;
	}

}
