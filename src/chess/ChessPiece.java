package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
	
	private Color color = null;
	private int moveCount = 0;

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
	
	public int getMoveCount() {
		return moveCount;
	}

	// incrementa jogada
	public void increaseMoveCount() {
		moveCount = getMoveCount() + 1;
	}

	
	// decrementa jogada
	public void decreaseMoveCount() {
		moveCount = getMoveCount() - 1;
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
