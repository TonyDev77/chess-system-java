package chess;

import boardgame.Position;

public class ChessPosition {

	private char column = 0;
	private int row = 0;
	
	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Erro ao instanciar ChessPosition: Vaores válidos, a1 - h8");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	// convertendo posições do tabuleiro para posição de matriz
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	// convertendo posição da matriz para tabuleiro
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char) ('a' - position.getColumn()), 8 - position.getRow());
	}

	@Override
	public String toString() {
		return "" + column + row;
	}
	
	
}
