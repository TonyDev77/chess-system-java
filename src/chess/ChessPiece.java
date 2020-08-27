package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
	private Color color = null;

	public ChessPiece() {
		super();
	}
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	// obtém posição
	public ChessPosition getChessPosition() {
		// converte Position para ChessPosition
		return ChessPosition.fromPosition(position);
	}
	
	// verifica se há peças oponentes no caminho
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		
		return p != null && p.getColor() != color;
		
	}

	
}
