package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece{

	public Knight(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "N";
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
		
		aux.setValues(position.getRow() - 1, position.getColumn() - 2);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}

		aux.setValues(position.getRow() - 2, position.getColumn() - 1);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}

		aux.setValues(position.getRow() - 2, position.getColumn() + 1);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}

		aux.setValues(position.getRow() - 1, position.getColumn() + 2);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}

		aux.setValues(position.getRow() + 1, position.getColumn() + 2);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}

		aux.setValues(position.getRow() + 2, position.getColumn() + 1);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}

		aux.setValues(position.getRow() + 2, position.getColumn() - 1);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}

		aux.setValues(position.getRow() + 1, position.getColumn() - 2);
		if (getBoard().positionExists(aux) && canMove(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		
		return mat;
	}
	
	
}
