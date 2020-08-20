package boardgame;

public class Board {
	private int rows = 0;
	private int columns = 0;
	private Piece[][] pieces = null;
	
	public Board() {
		
	}
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	//...
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	// retorna coordenadas da peça
	public Piece piece(int row, int column) {
		return pieces[row][column];
	}
	
	// retorna Posição da peça no contexto
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}
	
	
	
}
