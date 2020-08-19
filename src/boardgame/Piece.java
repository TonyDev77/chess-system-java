package boardgame;

public class Piece {
	protected Position position = null;
	private Board board = null;
	
	public Piece() {

	}
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}

	
	
	
}
