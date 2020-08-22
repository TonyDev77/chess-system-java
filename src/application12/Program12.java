package application12;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program12 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// Criando o tabuleiro da partida
		ChessMatch chessMatch = new ChessMatch();
		
		while (true) {
			UI.printBoard(chessMatch.getPieces()); // imprime tabuleiro
			
			System.out.println();
			System.out.print("source: ");
			ChessPosition source = UI.readChessPosition(sc); // lê posição de origem
			
			System.out.print("Target: ");
			ChessPosition target = UI.readChessPosition(sc); // lê posição de destino
			
			// movendo a peça
			ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
			
			
			
		}
	}

}
