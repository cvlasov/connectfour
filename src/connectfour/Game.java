package connectfour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class Game {

	private boolean isComputerMove;
	
	private Board board;
	
	private Dimension frameSize = new Dimension(1000, 600);
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * The frame is composed of the game to the left and the legend to the right
	 */
	private JFrame frame;
	private JPanel gamePanel;
	private JPanel legendPanel;
	
	private int cellSize;
	
	public Game() {
		JFrame frame = new JFrame("Connect Four");
		board = new Board();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameSize);
		frame.setPreferredSize(frameSize);
		frame.setLocation(
				screenSize.width/2  - frameSize.width/2,
				screenSize.height/2 - frameSize.height/2);
		
		cellSize = frameSize.height / (Board.numOfRows + 3);
		
		gamePanel = new JPanel(true /* enable double-buffering*/); // uses FlowLayout by default
		Dimension gamePanelSize = new Dimension(cellSize * (Board.numOfCols + 2), frameSize.height);
		gamePanel.setSize(gamePanelSize);
		gamePanel.setPreferredSize(gamePanelSize); // figure out why this is needed
		
		legendPanel = new JPanel(true);
		Dimension legendPanelSize = new Dimension(frameSize.width - gamePanelSize.width, frameSize.height - gamePanelSize.height);
		legendPanel.setSize(legendPanelSize);
		legendPanel.setPreferredSize(legendPanelSize);
		
		JLabel testLabel = new JLabel("EMPTY SPACE YAY! ILAJM");
		testLabel.setBackground(Color.BLUE);
		frame.add(testLabel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		
		// Initialize game, let user goes first

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
		int columnNum = (int) Math.random() * board.numOfCols;
		while (!board.isValidMove(columnNum)) {
			columnNum = (int) Math.random() * board.numOfCols;
		}
		
		int rowNum = board.placePiece(columnNum, GameHelper.Piece.COMPUTER);
		if (board.hasWon(rowNum, columnNum)) {
			// print something nice on the screen!
		}
	}
}
