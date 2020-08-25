package boardgame;

public abstract class Piece {
	
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

	// testa movimento possível
	public abstract boolean[][] possibleMoves();
	
	// retorna movimento possível
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	// testa se há pelo menos um movimento possível
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
