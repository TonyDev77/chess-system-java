package application12;

import chess.ChessPiece;

public class UI {
	
	// Imprime tabuleiro com as peças
	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " "); // nome das linhas
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j]); // imprime as peças
			}
			System.out.println();
		}
		System.out.println("  a b d d e f g h"); // nome das colunas
	}
	
	// Imprime uma peça apenas
	private static void printPiece(ChessPiece piece) {
		if (piece == null) {
			System.out.print("-");
		} else {
			System.out.print(piece);
		}
		System.out.print(" ");
	}
}
