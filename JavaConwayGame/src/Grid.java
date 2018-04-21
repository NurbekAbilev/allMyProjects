import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Grid extends Canvas{
	
	private Boolean started = false;
	private ConwayGame game = null;
	
	int temp = 0;
	
	public Grid(int y,int x,ConwayGame game){
		this.setBounds(y,x,512,512);	
		this.setGame(game);
	}
	
	public void start(){
		new Thread(){
			public void run(){
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					draw();
				}
			}
		}.start();
		
		new Thread(){
			public void run(){
				while(true){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					game.iterateStep();
				}
			}
		}.start();
		
		
	}
	
	public void paint(Graphics g){
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawString("Choose Your File", 100, 100);
	}
	
	public void draw(){
		BufferStrategy bs = getBufferStrategy();
 	    if (bs== null){
		    createBufferStrategy(3);
		    return;
	    } 

	    Graphics g = bs.getDrawGraphics();
	    g.setColor(Color.DARK_GRAY);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    int step = this.getHeight()/game.size;
	    int w = step/10;
	    

		g.setColor(Color.YELLOW);
	    for(int i=0;i<game.size;i++){
	    	for(int j=0;j<game.size;j++){
	    		if(game.grid[i][j] == 1){
	    			g.fillRect(j*step, i*step, step, step);	    			
	    		}
	    	}
	    }
	    
	    g.setColor(Color.LIGHT_GRAY);
		for(int i=1;i<game.size;i++){
			g.fillRect(i*step-w/2,0,w,this.getHeight());
			g.fillRect(0,i*step-w/2,this.getHeight(),w);
	    }
	    
	    bs.show();
	    
	}



	public ConwayGame getGame() {
		return game;
	}

	public void setGame(ConwayGame game) {
		this.game = game;
	}

	public Boolean isStarted() {
		return started;
	}



	public void setStarted(Boolean started) {
		this.started = started;
	}
	
	
}
