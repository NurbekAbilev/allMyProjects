import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Window extends Canvas{
	
	public Window(){
		JFrame frame = new JFrame("MineSweeper");
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setSize(Game.SIZE,Game.SIZE+30);
		frame.setLocationRelativeTo(null);
		
		Game game = new Game();
		frame.add(game);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		Thread thread = new Thread(game);
		thread.start();

	}

	
}
