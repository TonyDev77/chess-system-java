package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {
	public Bishop(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		// verificando posições disponíveis
		Position aux = new Position(0, 0); // auxiliar

		// testa posição NOROESTE da peça ............................................
		aux.setValues(position.getRow() - 1, position.getColumn() - 1);
		// enquanto existir posição pra aux e não estiver ocupada
		while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
			aux.setValues(aux.getRow() - 1, aux.getColumn() - 1); // noroeste
		}
		// se existir posição pra aux e estiver ocupada por peça oponente...
		if (getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
		}


		// testa posição A NORDESTE da peça .........................................
		aux.setValues(position.getRow() -1 , position.getColumn() + 1);
		// enquanto existir posição pra aux e não estiver ocupada
		while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
			aux.setValues(aux.getRow() - 1, aux.getColumn() + 1); // nordeste
		}
		// se existir posição pra aux e estiver ocupada por peça oponente...
		if (getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
		}
		// testa posição A SUDESTE da peça .........................................
		aux.setValues(position.getRow() + 1, position.getColumn() + 1);
		// enquanto existir posição pra aux e não estiver ocupada
		while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
			aux.setValues(aux.getRow() + 1, aux.getColumn() + 1);
		}
		// se existir posição pra aux e estiver ocupada por peça oponente...
		if (getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
		}

		// testa posição SUDOESTE da peça ............................................
		aux.setValues(position.getRow() + 1, position.getColumn() - 1);
		// enquanto existir posição pra aux e não estiver ocupada
		while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
			aux.setValues(aux.getRow() + 1, aux.getColumn() - 1);
		}
		// se existir posição pra aux e estiver ocupada por peça oponente...
		if (getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
		}
		
		
		return mat;
		// NOTA. A Torre pode mover apenas para frente e lados
	}

}
