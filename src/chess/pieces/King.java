package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	public King(Board board, Color color) {
		super(board, color);
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
		
		
		
		return mat;
		// NOTA. O Rei pode mover apenas uma casa para qualquer lado
	}
	
	
}
