package connectfour;

import java.util.Arrays;

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
	
	private GameHelper.Piece[][] board;
	
	public Board () {
		board = new GameHelper.Piece[6][7];
		// Initialize all entries to NONE
		Arrays.fill(board, GameHelper.Piece.NONE);
	}
	
	public GameHelper.Piece[][] getBoard() {
		return board;
	}
	
	/**
	 * Returns whether or not placing a piece in the given column is valid
	 */
	public boolean isValidMove (int columnNum) {
		// Check if the top of the column is empty
		return this.board[0][columnNum] == GameHelper.Piece.NONE;
	}
	
	/**
	 * Places a given piece type in a given column
	 * <p>
	 * <b>Note</b>: requires the move to be valid
	 */
	public int placePiece(int columnNum, GameHelper.Piece pieceType) {
		// Start at the bottom and find the first empty position
		for (int rowNum = 5; rowNum >= 0; rowNum--) {
			if (board[rowNum][columnNum] == GameHelper.Piece.NONE) {
				board[rowNum][columnNum] = pieceType;
				return rowNum;
			}
		}
		// Fail
		return -1;
	}
	
	/**
	 * Check if the most recent piece in the given column resulted in a win
	 */
	public boolean hasWon (int pieceRow, int pieceColumn) {
		
		// Get type of the most recently added piece
		GameHelper.Piece pieceType = board[pieceRow][pieceColumn];
		
		// Check if piece won vertically
		if (pieceRow < 3 && board[pieceRow + 1][pieceColumn] == pieceType &&
			                  board[pieceRow + 2][pieceColumn] == pieceType &&
			                  board[pieceRow + 3][pieceColumn] == pieceType) {
			return true;
		}
		
		// Check if piece won horizontally
		for (int columnNum = 0; columnNum < 4; columnNum++) {
			if (board[pieceRow][columnNum] == pieceType &&
				board[pieceRow][columnNum + 1] == pieceType &&
				board[pieceRow][columnNum + 2] == pieceType &&
				board[pieceRow][columnNum + 3] == pieceType) {
				return true;
			}
		}

		// Check if piece won diagonally
		for (int rowNum = 0; rowNum < 3; rowNum++) {
			for (int columnNum = 0; columnNum < 4; columnNum++) {
				if (board[rowNum][columnNum] == pieceType &&
					board[rowNum + 1][columnNum + 1] == pieceType && 
					board[rowNum + 1][columnNum + 1] == pieceType && 
					board[rowNum + 1][columnNum + 1] == pieceType) {
						return true;
					}
			}
		}
		
		for (int rowNum = 3; rowNum < 6; rowNum++) {
			for (int columnNum = 0; columnNum < 4; columnNum++) {
				if (board[rowNum][columnNum] == pieceType &&
					board[rowNum - 1][columnNum + 1] == pieceType && 
					board[rowNum - 1][columnNum + 1] == pieceType && 
					board[rowNum - 1][columnNum + 1] == pieceType) {
						return true;
					}
			}
		}
		
		// Return false if the piece didn't result in a win
		return false;
	}
}
