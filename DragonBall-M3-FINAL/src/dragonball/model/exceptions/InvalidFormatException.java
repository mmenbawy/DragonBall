package dragonball.model.exceptions;

import java.io.IOException;

@SuppressWarnings("serial")
public abstract class InvalidFormatException extends IOException {

	private String sourceFile;
	private int sourceLine;
	public InvalidFormatException() {
	}

	public InvalidFormatException(String arg0) {
		super(arg0);
	}
	
	public InvalidFormatException(String sFile, int sLine) {
		this.sourceFile = sFile;
		this.sourceLine = sLine;
	}
	
	public InvalidFormatException(String message, String sFile, int sLine) {
		super(message);
		this.sourceFile = sFile;
		this.sourceLine = sLine;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public int getSourceLine() {
		return sourceLine;
	}

}
