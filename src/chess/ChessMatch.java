package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private Board board = null;

	public ChessMatch() {
		board = new Board(8, 8); // cria tabuleiro
		initialSetup(); // inicia a partida
	}
	
	// retorna a matriz de peças da partida
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	// movendo a peça no tabuleiro
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); // converte para posição de matriz
		Position target = targetPosition.toPosition(); // idem...
		
		validateSourcePosition(source); // verifica se existe
		Piece capturedPiece = makeMove(source, target); // captura peça
		
		return (ChessPiece) capturedPiece;
	}
	
	// move a peça
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source); // remove peça da posição de origem
		Piece capturedPiece = board.removePiece(target); // remove peça da posição de 
		board.placePiece(p, target); // colocando a peça na posição de destino
		
		return capturedPiece;
	}
	
	// verifica se posição está ocupada
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPeace(position)) {
			throw new ChessException("Não existe peça na posição de origem!");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Não existem movimentos possíveis para esta peça");
		}
	}
	
	// Coloca peças nas coordenadas do Xadrez
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	// Iniciando setup do jogo
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
	
	
}
