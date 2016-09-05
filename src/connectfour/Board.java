package connectfour;

import java.util.Arrays;

import connectfour.GameHelper.Piece;

public final class Board {
	
	/*
	 *    The board has 6 rows and 7 columns
	 *    Top left is board[0][0]
	 *    Bottom left is board[5][0]
	 *    Top right is board[0][6]
	 *    Bottom right is board[5][6]
	 */
	
	private GameHelper.Piece[][] board;
	
	public Board () {
		board = new GameHelper.Piece[6][7];
		// initialize all entries to NONE
		Arrays.fill(board, Piece.NONE);
	}
	
	public Piece[][] getBoard() {
		return board;
	}
	
	// the move is valid if the top of the column is empty
	public boolean isValidMove (int columnNum) {
		return this.board[0][columnNum] == Piece.NONE;
	}
	
	// places pieceType in columnNum, requires the move to be valid
	public void placePiece(int columnNum, GameHelper.Piece pieceType) {
		assert(isValidMove (columnNum)) : "invalid move";
		// starts at the bottom and finds the first empty position
		for (int rowNum = 5; rowNum >= 0; rowNum--) {
			if (board[rowNum][columnNum] == Piece.NONE) {
				board[rowNum][columnNum] = pieceType;
				return;
			}
		}	
	}
	
	// checks if the most recent addition to the board resulted in a win
	public boolean hasWon (int columnOfPiece) {
		
		// gets row of piece (0-5)
		int rowOfPiece = 0;
		for (int rowNum = 0; rowNum < 6; rowNum++) {
			if (board[rowNum][columnOfPiece] != Piece.NONE) {
				rowOfPiece = rowNum;
			}
		}
		
		// gets type of the most recently added piece
		GameHelper.Piece pieceType = board[rowOfPiece][columnOfPiece];
		
		// checks if piece has won vertically
		if (rowOfPiece < 3 && board[rowOfPiece + 1][columnOfPiece] == pieceType &&
			                  board[rowOfPiece + 2][columnOfPiece] == pieceType &&
			                  board[rowOfPiece + 3][columnOfPiece] == pieceType) {
			return true;
		}
		
		// checks if piece has won horizontally
		for (int columnNum = 0; columnNum < 4; columnNum++) {
			if (board[rowOfPiece][columnNum] == pieceType &&
				board[rowOfPiece][columnNum + 1] == pieceType &&
				board[rowOfPiece][columnNum + 2] == pieceType &&
				board[rowOfPiece][columnNum + 3] == pieceType) {
				return true;
			}
		}

		// checks if piece has won diagonally
		
		
		// return false if the piece didn't win
		return false;
		
	}

}
