import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MyMouseListener implements MouseListener,MouseMotionListener{
	
	private Grid grid = null;
	
	MyMouseListener(Grid grid){
		this.grid = grid;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
			
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
//		int step = grid.getHeight()/grid.getGame().size;
//		int size = grid.getGame().size;
//		int y = e.getY()/step;
//		int x = e.getX()/step;
//		
//		grid.getGame().grid[y][x] = 1;
//	
//	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int step = grid.getHeight()/grid.getGame().size;
		int size = grid.getGame().size;
		int y = e.getY()/step;
		int x = e.getX()/step;
		
		grid.getGame().grid[y][x] = 1;
	}

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

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub	
		
	}
	
}
