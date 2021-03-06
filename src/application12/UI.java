package application12;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// limpa a tela
	public static void clearScreen() {
		System.out.println("\033[H\033[2J");
		System.out.flush();
	}
	
	// Lê uma posição do usuário 
	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine(); // recebe a jogada
			char column = s.charAt(0); // extrai a letra
			int row = Integer.parseInt(s.substring(1)); // extrai o número
			return new ChessPosition(column, row);
			
		} catch (RuntimeException e) {
			throw new InputMismatchException("Valores válidos: 'a1' até 'h8'");
		}
	}
	
	// Imprime a jogada (informações da jogada)
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		
		printBoard(chessMatch.getPieces()); // imprime o tabuleiro
		System.out.println();
		
		printCapturedPiece(captured); // imprime peças capturadas
		System.out.println();
		
		System.out.println("Turn: " + chessMatch.getTurn());// imprime o turno
		
		if (!chessMatch.getCheckMate()) {
			System.out.println("Aguardando jogador: " + chessMatch.getCurrentPlayer());// vez do player
			// avisa se o jogador está em check
			if (chessMatch.getCheck()) {
				System.out.println("CHECK!");
			}
		} else {
			System.out.println("CHECKMATE!!!");
			System.out.println("Vencedor: " + chessMatch.getCurrentPlayer());
			System.out.println();
		}
		
	}
	
	// Imprime tabuleiro com as peças
	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " "); // nome das linhas
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], false); // imprime as peças
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h"); // nome das colunas
	}
	
	// Imprime tabuleiro com as peças e possíveis jogadas coloridas
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMovies) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " "); // nome das linhas
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], possibleMovies[i][j]); // imprime as peças
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h"); // nome das colunas
	}

	// Imprime uma peça apenas
	private static void printPiece(ChessPiece piece, boolean background) {
    	// se V, então colore o fundo da tela
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		
		if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
	// Imprimindo peças capturadas
	private static void printCapturedPiece(List<ChessPiece> captured) {
		// filtra todas as peças da cor branca
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		// filtra todas as peças da cor preta
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		
		System.out.println("Peças capturadas:");
		System.out.print("Brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray())); // Imprime um array s/ precisar do laço
		System.out.print(ANSI_RESET);
		
		System.out.print("Pretas: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray())); // Imprime um array s/ precisar do laço
		System.out.print(ANSI_RESET);
	}
	
	
	
	
}
