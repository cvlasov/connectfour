package connectfour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;

public final class Game {

	private boolean isComputerMove;
	private Board board;
	
	public Game() {
		JFrame frame = new JFrame("Connect Four");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(500, 400));
		frame.setPreferredSize(new Dimension(500, 400));
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(
				dim.width/2  - frame.getSize().width/2,
				dim.height/2 - frame.getSize().height/2);
		
		JLabel testLabel = new JLabel("EMPTY SPACE YAY! ILAJM");
		testLabel.setBackground(Color.BLUE);
		frame.add(testLabel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		
		// initialize params: user goes first
		
		board = new Board();
		isComputerMove = false;
	}

	/**
	 * Completes a turn of the game
	 */
	public void gameMove() {
		if (this.isComputerMove) {
			computerMove();
		}
		else {
			userMove();
		}
		
		isComputerMove ^= true;
	}
	
	public void userMove() {}
		
	/**
	 * Randomly selects a column where a valid move can be made and places piece there.
	 */
	public void computerMove() {
		int columnNum = (int) Math.random() * board.getNumOfCols();
		while (!board.isValidMove(columnNum)) {
			columnNum = (int) Math.random() * board.getNumOfCols();
		}
		
		int rowNum = board.placePiece(columnNum, GameHelper.Piece.COMPUTER);
		if (board.hasWon(rowNum, columnNum)) {
			// print something nice on the screen!
		}
	}
}
