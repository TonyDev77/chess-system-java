package application12;

import chess.ChessMatch;

public class Program12 {

	public static void main(String[] args) {
		// Criando o tabuleiro da partida
		ChessMatch chessMatch = new ChessMatch();
		UI.printBoard(chessMatch.getPieces());
	}

}
