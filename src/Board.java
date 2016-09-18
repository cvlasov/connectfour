package connectfour;

import connectfour.GameHelper.Piece;

/**
 *    The Connect Four board storing pieces represented by {@code GameHelper.Piece}
 *    <p>
 *    It is initialized with 6 rows and 7 columns in the constructor such that:
 *    <ul>
 *      <li>Top left is {@code board[0][0]}</li>
 *      <li>Bottom left is {@code board[5][0]}</li>
 *      <li>Top right is {@code board[0][6]}</li>
 *      <li>Bottom right is {@code board[5][6]}</li>
 *    </ul>
 */
public final class Board {
	
	private Piece[][] pieces;
	
	/**
	 * Initializes the board such that there are no pieces.
	 */
	public Board() {
		pieces = new Piece[GameHelper.NUM_OF_ROWS][GameHelper.NUM_OF_COLS];
		for (int row = 0; row < GameHelper.NUM_OF_ROWS; row++) {
			for (int col = 0; col < GameHelper.NUM_OF_COLS; col++) {
				pieces[row][col] = Piece.NONE; 
			}
		}
	}
	
	/**
	 * Returns whether or not placing a piece in the given column is valid
	 */
	public boolean isValidMove (int columnNum) {
		return columnNum > -1
				&& columnNum < GameHelper.NUM_OF_COLS
				&& this.pieces[0][columnNum] == Piece.NONE;
	}
	
	/**
	 * Places a given piece type in a given column
	 * <p>
	 * <b>Note</b>: requires the move to be valid
	 */
	public int placePiece(int columnNum, Piece pieceType) {
		// Start at the bottom and find the first empty position
		for (int rowNum = GameHelper.NUM_OF_ROWS - 1; rowNum >= 0; rowNum--) {
			if (pieces[rowNum][columnNum] == Piece.NONE) {
				pieces[rowNum][columnNum] = pieceType;
				return rowNum;
			}
		}
		return -1;
	}
	
	/**
	 * Checks if the piece in the given row and column resulted in a win.
	 */
	public boolean hasWon (int pieceRow, int pieceColumn) {
		
		// Get type of the most recently added piece
		Piece pieceType = pieces[pieceRow][pieceColumn];
		
		// Check if piece won vertically
		if (pieceRow < 3 && pieces[pieceRow + 1][pieceColumn] == pieceType &&
			                  pieces[pieceRow + 2][pieceColumn] == pieceType &&
			                  pieces[pieceRow + 3][pieceColumn] == pieceType) {
			return true;
		}
		
		// Check if piece won horizontally
		for (int columnNum = 0; columnNum < 4; columnNum++) {
			if (pieces[pieceRow][columnNum] == pieceType &&
				pieces[pieceRow][columnNum + 1] == pieceType &&
				pieces[pieceRow][columnNum + 2] == pieceType &&
				pieces[pieceRow][columnNum + 3] == pieceType) {
				return true;
			}
		}

		// Check if piece won diagonally
		for (int rowNum = 0; rowNum < 3; rowNum++) {
			for (int columnNum = 0; columnNum < 4; columnNum++) {
				if (pieces[rowNum][columnNum] == pieceType &&
					pieces[rowNum + 1][columnNum + 1] == pieceType && 
					pieces[rowNum + 1][columnNum + 1] == pieceType && 
					pieces[rowNum + 1][columnNum + 1] == pieceType) {
						return true;
					}
			}
		}
		
		for (int rowNum = 3; rowNum < 6; rowNum++) {
			for (int columnNum = 0; columnNum < 4; columnNum++) {
				if (pieces[rowNum][columnNum] == pieceType &&
					pieces[rowNum - 1][columnNum + 1] == pieceType && 
					pieces[rowNum - 1][columnNum + 1] == pieceType && 
					pieces[rowNum - 1][columnNum + 1] == pieceType) {
						return true;
					}
			}
		}
		
		return false;
	}
	
	public Piece[][] getPieces() {
		return pieces;
	}
}