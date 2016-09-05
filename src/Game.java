package connectfour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;

public final class Game {

	// params representing game state
	
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
	}
	
	public void userMove() {}
	
	public void computerMove() {}
	
	// ...
}
