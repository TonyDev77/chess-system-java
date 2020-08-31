package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	// para poder ter acesso a possibilidade el passant
	private ChessMatch chessMatch = null;
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		// verificando posições disponíveis
		Position aux = new Position(0, 0); // auxiliar
		
		// verificando cor do jogador
		if (getColor() == Color.WHITE) {
			
			// verificando jogada 1 casa acima
			aux.setValues(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
				mat[aux.getRow()][aux.getColumn()] = true;
			}
			
			// verificando possibilidade de jogada 2 casas acima na 1º vez
			aux.setValues(position.getRow() - 2, position.getColumn());
			Position aux2 = new Position(position.getRow() - 1, position.getColumn());			
			if (getBoard().positionExists(aux) 
				&& !getBoard().thereIsAPiece(aux) 
				&& getBoard().positionExists(aux2) 
				&& !getBoard().thereIsAPiece(aux2)
				&& getMoveCount() == 0)
			{
				mat[aux.getRow()][aux.getColumn()] = true;
			}
			
			// verificando oponente na diagonal a esquerda
			aux.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
				mat[aux.getRow()][aux.getColumn()] = true;
			}
			
			// verificando oponente na diagonal a direita
			aux.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
				mat[aux.getRow()][aux.getColumn()] = true;
			}
			
			// Movimento especial En Passant WHITE
			if (position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				// testa se o peão oponente atende os requisitos p/ tomar um En Passant
				if (getBoard().positionExists(left) 
						&& isThereOpponentPiece(left) 
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() - 1][left.getColumn()] = true;
				}
				
				// TODO: TALVEZ O ERRO ESTJA NA LINHA 77 ABAIXO
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				// testa se o peão oponente atende os requisitos p/ tomar um En Passant
				if (getBoard().positionExists(right) 
						&& isThereOpponentPiece(right) 
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
			}
			
		} else {
			// verificando jogada 1 casa acima
			aux.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
				mat[aux.getRow()][aux.getColumn()] = true;
			}

			// verificando possibilidade de jogada 2 casas acima na 1º vez
			aux.setValues(position.getRow() + 2, position.getColumn());
			Position aux2 = new Position(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux) && getBoard().positionExists(aux2)
					&& !getBoard().thereIsAPiece(aux2) && getMoveCount() == 0) {
				mat[aux.getRow()][aux.getColumn()] = true;
			}

			// verificando oponente na diagonal a esquerda
			aux.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
				mat[aux.getRow()][aux.getColumn()] = true;
			}

			// verificando oponente na diagonal a direita
			aux.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
				mat[aux.getRow()][aux.getColumn()] = true;
			}
			
			// Movimento especial En Passant BLACK
			if (position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				// testa se o peão oponente atende os requisitos p/ tomar um En Passant
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;
				}

				// TODO: TALVEZ O ERRO ESTJA NA LINHA 77 ABAIXO
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				// testa se o peão oponente atende os requisitos p/ tomar um En Passant
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}
		}
		
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
	
	

}
