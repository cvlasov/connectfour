package connectfour;

import java.awt.Color;
import java.awt.Dimension;

public final class GameHelper {
	
	public static final int FRAME_WIDTH = 1200;
	public static final int FRAME_HEIGHT = 800;
	public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
	
	public static enum Piece {
		PLAYER,
		COMPUTER,
		NONE
	}
	
	public static final Color GRID_COLOR = Color.BLACK;
	public static final Color PLAYER_COLOR = Color.RED;
	public static final Color PLAYER_COLOR_DARK = PLAYER_COLOR.darker();
	public static final Color COMPUTER_COLOR = Color.BLUE;
	public static final Color COMPUTER_COLOR_DARK = COMPUTER_COLOR.darker();
	
	public static final int NUM_OF_ROWS = 6;
	public static final int NUM_OF_COLS = 7;
	
	/**
	 * Number of pixels between token and cell wall.
	 */
	public static final int PIECE_BORDER = 5;
	
	public static final int COMPUTER_MOVE_WAIT_MS = 1000;
	
	/**
	 * Returns the distance between two points.
	 */
	public static int distanceBetween(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
	}
}
