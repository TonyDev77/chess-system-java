package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {
	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		// verificando posições disponíveis
		Position aux = new Position(0, 0); // auxiliar

		// testa posição ACIMA da peça ............................................
		aux.setValues(position.getRow() - 1, position.getColumn());
		// enquanto existir posição pra aux e não estiver ocupada
		while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
			aux.setRow(aux.getRow() - 1); // retrocede mais uma linha
		}
		// se existir posição pra aux e estiver ocupada por peça oponente...
		if (getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
		}

		// testa posição ABAIXO da peça ............................................
		aux.setValues(position.getRow() + 1, position.getColumn());
		// enquanto existir posição pra aux e não estiver ocupada
		while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
			aux.setRow(aux.getRow() + 1); // retrocede mais uma linha
		}
		// se existir posição pra aux e estiver ocupada por peça oponente...
		if (getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
		}

		// testa posição A ESQUERDA da peça .........................................
		aux.setValues(position.getRow(), position.getColumn() - 1);
		// enquanto existir posição pra aux e não estiver ocupada
		while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
			aux.setColumn(aux.getColumn() - 1); // retrocede mais uma coluna
		}
		// se existir posição pra aux e estiver ocupada por peça oponente...
		if (getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
		}
		// testa posição A DIREITA da peça .........................................
		aux.setValues(position.getRow(), position.getColumn() + 1);
		// enquanto existir posição pra aux e não estiver ocupada
		while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
			aux.setColumn(aux.getColumn() + 1); // retrocede mais uma coluna
		}
		// se existir posição pra aux e estiver ocupada por peça oponente...
		if (getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true; // marca como posição válida
		}

		return mat;
	}

}
