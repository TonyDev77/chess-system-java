package boardgame;

public class Board {
	private int rows = 0;
	private int columns = 0;
	private Piece[][] pieces = null;
	
	public Board() {
		
	}
	
	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException("Erro a criar tabulerio: Deve haver ao menos 1 linha ou 1 coluna");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	//...
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	// retorna coordenadas da peça
	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) {
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return pieces[row][column];
	}
	
	// retorna Posição da peça no contexto
	public Piece piece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	// coloca peça no tabuleiro
	public void placePiece(Piece piece, Position position) {
		if (thereIsAPeace(position)) {
			throw new BoardException("Já existe uma peça nesta posição: " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	// método que auxilia a função abaixo...
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	// retorna booleano se a posição existe
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	// retorna se uma peça existe
	public boolean thereIsAPeace(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return piece(position) != null;
	}
	
	
	
}
