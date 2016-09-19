package connectfour;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import connectfour.GameHelper.Piece;

public final class Game {

	/**
	 * The frame is composed of the game to the left and the legend to the right
	 */
	private JFrame frame;
	private GamePanel gamePanel;
	private JPanel legendPanel;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int cellSize;
	
	private Board board;
	private boolean gameWon;
	
	private boolean isComputerMove;
	
	/**
	 * Initializes the frame containing the {@code gamePanel} and {@code legendPanel} and
	 * allows the user to go first. 
	 */
	public Game() {
		frame = new JFrame("Connect Four");
		board = new Board();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setPreferredSize(GameHelper.FRAME_SIZE);
		frame.setLocation(screenSize.width/2  - GameHelper.FRAME_WIDTH/2,
						  screenSize.height/2 - GameHelper.FRAME_HEIGHT/2);
		
		cellSize = GameHelper.FRAME_HEIGHT / (GameHelper.NUM_OF_ROWS + 3);
		
		gamePanel = new GamePanel(); // uses FlowLayout by default
		Dimension gamePanelSize = new Dimension(cellSize * (GameHelper.NUM_OF_COLS + 2),
												GameHelper.FRAME_HEIGHT);
		gamePanel.setSize(gamePanelSize);
		gamePanel.setPreferredSize(gamePanelSize); // figure out why this is needed
		
		legendPanel = new JPanel(true);
		Dimension legendPanelSize = new Dimension(GameHelper.FRAME_WIDTH - gamePanelSize.width,
												  GameHelper.FRAME_HEIGHT - gamePanelSize.height);
		legendPanel.setSize(legendPanelSize);
		legendPanel.setPreferredSize(legendPanelSize);
		
		frame.getContentPane().add(gamePanel);
		frame.getContentPane().add(legendPanel);
		frame.pack();
		frame.setVisible(true);
		
		// Initialize game, let user goes first
		gameWon = false;
		isComputerMove = false;
		frame.getContentPane().addMouseListener(gamePanel);
		playGame();
	}

	/**
	 * Alternates between user and computer moves.
	 * <p>
	 * At the end of each turn, repaints {@code gamePanel} and then checks if the
	 * player who just moved won the game.
	 */
	public void playGame() {
		if (this.isComputerMove) {
			computerMoveHard();
			frame.getContentPane().addMouseListener(gamePanel);
		}
		else {
			userMove();
			frame.getContentPane().removeMouseListener(gamePanel);
		}
		
		gamePanel.repaint();
		
		if (gameWon) {
			System.out.println("Someone won!!!");
			return;
		}
		
		if (board.isFilled()) {
			System.out.println("Game over!!!");
			return;
		}
		
		isComputerMove = !isComputerMove;
		playGame();
	}
	
	/**
	 * Waits for user to click on a valid column and then places user piece.
	 */
	public void userMove() {
		synchronized (gamePanel.userClicked) {
			int colClicked = -1;
			
			while (!board.isValidMove(colClicked)) {
				try {
					gamePanel.userClicked.wait();
				} catch (InterruptedException e) {
					// Thread should never be interrupted
					System.out.println("Should not be interrupted");
				}
				
				colClicked = gamePanel.getUserClickCol();
			}
			
			int row = board.placePiece(colClicked, Piece.PLAYER);
			
			if (board.hasWon(row, colClicked)) {
				gameWon = true;
			}
			
			System.out.println("User placed piece in column " + colClicked);
		}
	}
		
	/**
	 * Makes computer move and returns whether or not resulted in a win.
	 * <p>
	 * Strategy: randomly selects a column where a valid move can be made
	 */
	public void computerMove() {
		try {
			Thread.sleep(GameHelper.COMPUTER_MOVE_WAIT_MS);
		} catch (InterruptedException e) {}
		
		int columnNum;
		do {
			columnNum = (int) (Math.random() * GameHelper.NUM_OF_COLS);
		} while (!board.isValidMove(columnNum));
		
		int rowNum = board.placePiece(columnNum, Piece.COMPUTER);
		
		if (board.hasWon(rowNum, columnNum)) {
			gameWon = true;
		}
		
		System.out.println("Computer placed piece in column " + columnNum);
	}
	
	/**
	 * Makes computer move and returns whether or not resulted in a win.
	 * <p>
	 * Strategy:
	 * <ol>
	 * <li>If win is possible, place piece there
	 * <li>If user can win next turn, place piece there
	 * <li>Place piece randomly
	 * </ol>
	 */
	public void computerMoveHard() {
		// Make computer move more realistic
		try {
			Thread.sleep(GameHelper.COMPUTER_MOVE_WAIT_MS);
		} catch (InterruptedException e) {}
		
		int columnNum = -1;
		
		// Check if computer can win
		for (int col = 0; col < GameHelper.NUM_OF_COLS; col++) {
			Board boardCopy = new Board(this.board.getPieces());
			if (boardCopy.isValidMove(col)) {
				int row = boardCopy.placePiece(col, Piece.COMPUTER);
				if (boardCopy.hasWon(row, col)) {
					columnNum = col;
				}
			}
		}
		
		if (columnNum == -1) {
			// Check if user can win
			for (int col = 0; col < GameHelper.NUM_OF_COLS; col++) {
				Board boardCopy = new Board(this.board.getPieces());
				if (boardCopy.isValidMove(col)) {
					int row = boardCopy.placePiece(col, Piece.PLAYER);
					if (boardCopy.hasWon(row, col)) {
						columnNum = col;
					}
				}
			}
		}
		
		if (columnNum == -1) {
			// Randomly choose column
			do {
				columnNum = (int) (Math.random() * GameHelper.NUM_OF_COLS);
			} while (!board.isValidMove(columnNum));
		}
		
		int rowNum = board.placePiece(columnNum, Piece.COMPUTER);
		
		if (board.hasWon(rowNum, columnNum)) {
			gameWon = true;
		}
		
		System.out.println("Computer placed piece in column " + columnNum);
	}
	
	//---------------------------------------------------------------------
	
	public final class GamePanel extends JPanel implements MouseListener {

		private static final long serialVersionUID = 1L;
		private int boardTopLeftX;
		private int boardTopLeftY;
		
		private Boolean userClicked;
		private int userClickCol;

		public GamePanel() {
			super();
			this.boardTopLeftX = cellSize;
			this.boardTopLeftY = 2*cellSize;
			this.userClicked = false;
		}
		
		@Override
		public void paintComponent(Graphics g) {
			g.setColor(GameHelper.GRID_COLOR);
			
			int verticalLineLength = cellSize * GameHelper.NUM_OF_ROWS;
			int horizontalLineLength = cellSize * GameHelper.NUM_OF_COLS;
			
			// Paint vertical grid lines
			for (int colNum = 0; colNum <= GameHelper.NUM_OF_COLS; colNum++) {
				int xCoord = boardTopLeftX + colNum * cellSize;
				g.drawLine(xCoord, boardTopLeftY, xCoord, boardTopLeftY + verticalLineLength);
			}

			// Paint horizontal grid lines
			for (int rowNum = 0; rowNum <= GameHelper.NUM_OF_ROWS; rowNum++) {
				int yCoord = boardTopLeftY + rowNum * cellSize;
				g.drawLine(boardTopLeftX, yCoord, boardTopLeftX + horizontalLineLength, yCoord);
			}
			
			// Paint tokens
			Piece[][] pieces = board.getPieces();
			int pieceDiameter = cellSize - GameHelper.PIECE_BORDER*2;
			for (int row = 0; row < GameHelper.NUM_OF_ROWS; row++) {
				for (int col = 0; col < GameHelper.NUM_OF_COLS; col++) {
					Piece piece = pieces[row][col];
					
					switch(piece) {
						case PLAYER:
							g.setColor(GameHelper.PLAYER_COLOR);
							break;
						case COMPUTER:
							g.setColor(GameHelper.COMPUTER_COLOR);
							break;
						case NONE:
							continue;
					}
					
					g.fillOval(boardTopLeftX + col * cellSize + GameHelper.PIECE_BORDER,
							   boardTopLeftY + row * cellSize + GameHelper.PIECE_BORDER,
							   pieceDiameter,
							   pieceDiameter);
				}
			}
			
			// Paint slightly darker tokens above board to indicate possible positions
			g.setColor(isComputerMove ? GameHelper.COMPUTER_COLOR_DARK : GameHelper.PLAYER_COLOR_DARK);
			
			for (int col = 0; col < GameHelper.NUM_OF_COLS; col++) {
				g.fillOval(boardTopLeftX + col * cellSize + GameHelper.PIECE_BORDER,
						boardTopLeftY - cellSize + GameHelper.PIECE_BORDER,
						pieceDiameter,
						pieceDiameter);
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			synchronized (userClicked) {
				userClickCol = getColOfClick(e.getX(), e.getY());
				userClicked.notifyAll();
			}
		}
		
		/**
		 * Returns column where user clicked if user clicked on token above board and -1 otherwise.
		 */
		private int getColOfClick(int x, int y) {
			int pieceDiameter = cellSize - GameHelper.PIECE_BORDER*2;
			
			for (int col = 0; col < GameHelper.NUM_OF_COLS; col++) {
				int distBetweenClickAndCenter =
						GameHelper.distanceBetween(x, y,
												   boardTopLeftX + (int) ((col + 0.5) * cellSize),
												   boardTopLeftY - (int) (0.5 * cellSize));
				if (distBetweenClickAndCenter < pieceDiameter / 2) {
					return col;
				}
			}
			return -1;
		}
		
		public int getUserClickCol() {
			return userClickCol;
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {}
		
		@Override
		public void mouseExited(MouseEvent e) {}
		
		@Override
		public void mousePressed(MouseEvent e) {}
		
		@Override
		public void mouseReleased(MouseEvent e) {}	
	}
}