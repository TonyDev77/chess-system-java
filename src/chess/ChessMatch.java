package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn = 0;
	private Color currentPlayer = null;
	private Board board = null;
	private boolean check = false;
	
	// Listas de peças no tabuleiro e capturadas
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8); // cria tabuleiro
		turn = 1; // vez do jogador
		currentPlayer = Color.WHITE; // identidade do jogador
		initialSetup(); // inicia a partida
	}
	
	// GET
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
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

	// imprimindo posições possíveis a partir da posição de origem
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		
		return board.piece(position).possibleMoves();
	}
	
	// movendo a peça no tabuleiro
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); // converte para posição de matriz
		Position target = targetPosition.toPosition(); // idem...

		validateSourcePosition(source); // verifica se existe posição de origem
		validateTargetPosition(source, target); // verifica se existe posição de destino
		Piece capturedPiece = makeMove(source, target); // captura peça
		
		// testa se o jogador se colocou em check e o avisa
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece); // desfaz jogada
			throw new ChessException("Você não pode se colocar em cheque!");
		}
		
		// informando se o oponente está em check
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		nextTurn(); // troca o turno
		return (ChessPiece) capturedPiece;
	}

	// move a peça
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source); // remove peça da posição de origem
		Piece capturedPiece = board.removePiece(target); // remove peça da posição de
		board.placePiece(p, target); // colocando a peça na posição de destino

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}
	
	// desfaz movimento
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target); // remove do destino
		board.placePiece(p, source); // põe de volta na origem
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target); // põe de volta no destino
			capturedPieces.remove(capturedPiece); // remove das capturadas
			piecesOnTheBoard.add(capturedPiece); // reposiciona no tabuleiro
		}
	}

	// verifica se posição de origem está ocupada
	private void validateSourcePosition(Position position) {
		
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("Não existe peça na posição de origem!");
		}
		
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("A peça escolhida não é sua!");
		}
		
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Não existem movimentos possíveis para esta peça");
		}
	}

	// verifica se posição de destino está ocupada
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("Peça escolhida não pode ser movida p/ posição de destino");
		}
	}
	
	// Método para trocar o turno
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	// Retorna a cor do oponente (black/white)
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	// Localiza o Rei de uma determinada cor
	private ChessPiece king(Color color) {
		// filtrando a lista
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
		
		for (Piece piece : list) {
			if (piece instanceof King) {
				return (ChessPiece) piece;
			}
		}
		
		throw new IllegalStateException("Não existe o Rei da cor " + color + "no tabuleiro");
	}
	
	// verificando se o Rei está em 'check'
	private boolean testCheck(Color color) {
		// pegando a posição do Rei (formato matriz)
		Position kingPosition = king(color).getChessPosition().toPosition();
		
		// lista de peças com a cor do oponente
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		
		// testa se a lista contém alguma peça que ponha o Rei em check
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves(); // lista movimentos possíveis de 'p'
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) { // Rei em check
				return true;
			}
		}
		return false;
	}

	// Coloca peças nas coordenadas do Xadrez
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // coloca peças no tabuleiro
		piecesOnTheBoard.add(piece); // coloca peças na LISTA do tabuleiro
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
