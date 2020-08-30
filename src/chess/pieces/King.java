package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{
	
	private ChessMatch chessMatch; // dá acesso à partida para o Rei

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}
	
	// método auxiliar - retorna se há movimento possível p/ o Rei
	public boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	// Método auxiliar p/ testar a condição da torre p/ jogada especial Castling/Roque
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position); // pega a posição da peça
		
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}
	
	@Override
	public boolean[][] possibleMoves() {
		// Nota. Todas as posições de uma matriz iniciam em null
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		// Posição auxiliar
		Position aux = new Position(0, 0);
		
		// testando acima
		aux.setValues(position.getRow() - 1 , position.getColumn());
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		
		// testando abaixo
		aux.setValues(position.getRow() + 1 , position.getColumn());
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		
		// testando esquerda
		aux.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		
		// testando direita
		aux.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		
		// testando noroeste
		aux.setValues(position.getRow() - 1 , position.getColumn() - 1);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		

		// testando nordeste
		aux.setValues(position.getRow() - 1 , position.getColumn() + 1);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		
		// testando nordeste
		aux.setValues(position.getRow() - 1 , position.getColumn() + 1);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		
		// testando sudoeste
		aux.setValues(position.getRow() + 1 , position.getColumn() - 1);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		
		// testando sudeste
		aux.setValues(position.getRow() + 1 , position.getColumn() + 1);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		
		
		// Implementação da jogada especial Castling
		if (getMoveCount() == 0 && !chessMatch.getCheck()) { // testa check e cont. movimentos
			// Rook pequeno - movimento a direita do Rei
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3); // pega posição da torre R
			if (testRookCastling(posT1)) { // testa posição vazia
				Position p1 = new Position(position.getRow(), position.getColumn() + 1); // casa 1
				Position p2 = new Position(position.getRow(), position.getColumn() + 2); // casa 2
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true; // permite jogada
				}
			}
			
			// Rook Grande - movimento a esquerda do Rei
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4); // pega posição da torre L
			if (testRookCastling(posT2)) { // testa posição vazia
				Position p1 = new Position(position.getRow(), position.getColumn() - 1); // casa 1
				Position p2 = new Position(position.getRow(), position.getColumn() - 2); // casa 2
				Position p3 = new Position(position.getRow(), position.getColumn() - 3); // casa 3
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true; // permite jogada
				}
			}
		}
		
		
		return mat;
		// NOTA. O Rei pode mover apenas uma casa para qualquer lado
	}
	
	
}
