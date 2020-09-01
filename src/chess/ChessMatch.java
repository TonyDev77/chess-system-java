package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn = 0;
	private Color currentPlayer = null;
	private Board board = null;
	private boolean check = false;
	private boolean checkMate = false;
	private ChessPiece enPassantVulnerable = null;
	private ChessPiece promoted = null;
	
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

	public boolean getCheckMate() {
		return checkMate;
	}

	// get jogada especial En Passant
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	// get jogada especial Pormotion
	public ChessPiece getPromoted() {
		return promoted;
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
		
		// atributo de referencia para EN PASSANT
		ChessPiece movedPiece = (ChessPiece) board.piece(target);
		
		// atributo de referencia para PROMOTION
		promoted = null;
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) 
					|| (movedPiece.getColor() == Color.BLACK && target.getRow() == 7) ) {
				// identificando o peão
				promoted = (ChessPiece) board.piece(target);
				// transforma por padrão na Rainha e depois deixa o jogador escolher
				promoted = replacePromotedPiece("Q");
				
			}
		}
		
		// informando se o oponente está em check
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		// encerra o jogo caso o rei esteja em check mate
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn(); // troca o turno
		}
		
		// testando possibilidade para En Passant
		if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 
				|| target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movedPiece;
		} else {
			enPassantVulnerable = null;
		}
		
		return (ChessPiece) capturedPiece;
	}

	// deixa usuário escolher a peça para substituir peão
	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("Não há peça p/ ser promovida!");
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
			return promoted;
		}
		
		Position pos = promoted.getChessPosition().toPosition(); // pega posição da peça
		Piece p = board.removePiece(pos); // remove peça e guarda na variável
		piecesOnTheBoard.remove(p); // remove da lista
		
		// instanciando nova peça com método auxiliar
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		return newPiece;
		
	}
	// Método auxiliar para 'replacePromotedPiece()'
	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B")) return new Bishop(board, color);
		if (type.equals("N")) return new Knight(board, color);
		if (type.equals("Q")) return new Queen(board, color);
		return new Rook(board, color);
	}

	// move a peça
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source); // remove peça da posição de origem
		p.increaseMoveCount(); // incrementa jogada
		Piece capturedPiece = board.removePiece(target); // remove peça da posição de
		board.placePiece(p, target); // colocando a peça na posição de destino

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		// Jogada especial Castling - Roque pequeno
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) { // testa para 'Roque pequeno'
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // pega posição da torre R
			Position targetT = new Position(source.getRow(), source.getColumn() + 1); // destino da torre
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);// movendo a torre
			board.placePiece(rook, targetT);// colocando a torre no destino
			rook.increaseMoveCount();// incrementa cont de movimentos
		}
		
		// Jogada especial Castling - Roque Grande
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) { // testa para 'Roque pequeno'
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4); // pega posição da torre L
			Position targetT = new Position(source.getRow(), source.getColumn() - 1); // destino da torre
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);// movendo a torre
			board.placePiece(rook, targetT);// colocando a torre no destino
			rook.increaseMoveCount();// incrementa cont de movimentos
		}
		
		// Jogada especial En Passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition = null;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				} else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		
		return capturedPiece;
	}
	
	// desfaz movimento
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target); // remove do destino
		p.decreaseMoveCount(); // decrementa jogada
		board.placePiece(p, source); // põe de volta na origem
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target); // põe de volta no destino
			capturedPieces.remove(capturedPiece); // remove das capturadas
			piecesOnTheBoard.add(capturedPiece); // reposiciona no tabuleiro
		}
		
		// Jogada especial Castling - Roque pequeno
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) { // testa para 'Roque pequeno'
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // pega posição da torre R
			Position targetT = new Position(source.getRow(), source.getColumn() + 1); // destino da torre
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);// movendo a torre
			board.placePiece(rook, sourceT);// colocando a torre de volta
			rook.decreaseMoveCount();// decrementa cont de movimentos
		}

		// Jogada especial Castling - Roque Grande
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) { // testa para 'Roque pequeno'
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4); // pega posição da torre L
			Position targetT = new Position(source.getRow(), source.getColumn() - 1); // destino da torre
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);// movendo a torre
			board.placePiece(rook, sourceT);// colocando a torre de volta
			rook.decreaseMoveCount();// decrementa cont de movimentos
		}
		
		// Jogada especial En Passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				ChessPiece pawn = (ChessPiece) board.removePiece(target); // tira do lugar errado
				Position pawnPosition = null;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				} else {
					pawnPosition = new Position(4, target.getColumn());
				}
				
				board.placePiece(pawn, pawnPosition); // colocando peça no lugar correto
			}
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

	// verificando se o Rei está em 'checkMate'
		private boolean testCheckMate(Color color) {
			if (!testCheck(color)) { // não está em check
				return false;
			}
			
			// pega todas as peças da cor acima
			List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
			
			// testa o check mate
			for (Piece p : list) {
				boolean[][] mat = p.possibleMoves(); // pega movimentos possíveis
				for (int i = 0; i < board.getRows(); i++) {
					for (int j = 0; j < board.getColumns(); j++) {
						if (mat[i][j]) {
							Position source = ((ChessPiece)p).getChessPosition().toPosition();
							Position target = new Position(i, j);
							Piece capturedPiece = makeMove(source, target); // movimenta para testar o check
							boolean testCheck = testCheck(color);// testa se o rei ainda está em check
							undoMove(source, target, capturedPiece); // desfaz movimento de teste
							// testa se ainda está em check, mesmo após o movimento
							if (!testCheck) {
								return false;
							}
						}
					}
				}
			}
			
			return true;
		}
	
	// Coloca peças nas coordenadas do Xadrez
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // coloca peças no tabuleiro
		piecesOnTheBoard.add(piece); // coloca peças na LISTA do tabuleiro
	}

	// Iniciando setup do jogo
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this)); // 'this' refere-se a esta jogada
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
	
	
	
	
	

}
