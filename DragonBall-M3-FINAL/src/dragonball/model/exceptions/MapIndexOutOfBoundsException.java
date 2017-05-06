package dragonball.model.exceptions;

@SuppressWarnings("serial")
public class MapIndexOutOfBoundsException extends IndexOutOfBoundsException {
	private int row;
	private int column;

	public MapIndexOutOfBoundsException(int row, int column) {
		super("Player Row: " + row + ", Player Column: " + column + ", Invalid Move");
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

}
