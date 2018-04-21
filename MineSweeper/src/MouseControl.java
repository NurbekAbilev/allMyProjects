import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

public class MouseControl implements MouseListener,MouseMotionListener{

	public int x,y;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == 1){
			Game.spread(x, y);
		}
		if(e.getButton() == 3){
			if(!Game.isFlagged[x][y]){
				Game.isFlagged[x][y] = true;
			}
			else
				Game.isFlagged[x][y] = false;
		}
	}
	
	//Mouse motion Listener

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	int size = Game.SIZE/Game.ARRAY_SIZE;
	@Override
	public void mouseMoved(MouseEvent e) {
	
		x = e.getX()/size;
		y = e.getY()/size;
		
		
	}

}
