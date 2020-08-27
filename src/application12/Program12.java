package application12;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program12 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// Criando o tabuleiro da partida
		ChessMatch chessMatch = new ChessMatch();
		// Lista de peças capturadas
		List<ChessPiece> captured = new ArrayList<>();
		
		while (!chessMatch.getCheckMate()) {
			try {				
				UI.clearScreen(); // limpa a tela
				UI.printMatch(chessMatch, captured); // imprime tabuleiro/jogadas
				
				System.out.println();
				System.out.print("source: ");
				ChessPosition source = UI.readChessPosition(sc); // lê posição de origem
				
				// imprime posições possíveis
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen(); // limpa a tela
				UI.printBoard(chessMatch.getPieces(), possibleMoves); // imprime colorido
				
				
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc); // lê posição de destino
				
				// movendo/realocando a peça
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				// add peça capturada à lista
				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured); 
	}

}
