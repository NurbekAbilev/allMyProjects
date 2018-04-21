import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Game extends Canvas implements Runnable{
	
	public MouseControl mouse;
	public BufferedImage imgEmpty,imgFlagged,imgHovered,imgPressed,imgBomb;
	public BufferedImage numbers[] = new BufferedImage[10];
	
	public static boolean isPressed [][] = new boolean [100][100];
	public static boolean isFlagged [][] = new boolean [100][100];
	public static int minesAround[][] = new int[100][100];
	
	public static final int SIZE = 500;
	public static final int ARRAY_SIZE = 10, BOMB_COUNT = 15;
	public static final int a[][] = new int [100][100];
	
	public Game(){
		mouse = new MouseControl();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		Random r = new Random();
		int n = this.BOMB_COUNT;
		for(int i=0;i<n;i++){
			int x = r.nextInt(ARRAY_SIZE-1);
			int y = r.nextInt(ARRAY_SIZE-1);
			
			a[x][y] = 1;
			try{minesAround[x+1][y+1]++;}catch(Exception e){}
			try{minesAround[x+1][y]++;}catch(Exception e){}
			try{minesAround[x+1][y-1]++;}catch(Exception e){}
			try{minesAround[x][y+1]++;}catch(Exception e){}
			try{minesAround[x][y-1]++;}catch(Exception e){}
			try{minesAround[x-1][y+1]++;}catch(Exception e){}
			try{minesAround[x-1][y]++;}catch(Exception e){}
			try{minesAround[x-1][y-1]++;}catch(Exception e){}
			
			
		}
		//display();
		
		try{
			imgEmpty = ImageIO.read(new File("empty.png"));
			imgHovered = ImageIO.read(new File("hovered.png"));
			imgPressed = ImageIO.read(new File("isPressed.png"));
			imgFlagged = ImageIO.read(new File("flagged.png"));
			imgBomb = ImageIO.read(new File("bomb.png"));
			
			for(int i=1;i<=8;i++){
				String str = i+".png";
				numbers[i] = ImageIO.read(new File(str));
			}
		}catch(Exception e){}
		
		
	}
	
	public static void spread(int x,int y){
		

		if(x>9 || y>9 || x<0 || y<0)
			return;
		
		if(a[x][y] == 1){
			System.out.println("Game over");
			for(int i=0;i<ARRAY_SIZE;i++){
				for(int j = 0 ;j<ARRAY_SIZE;j++){
					isPressed[i][j] = true;
				}
			}
			Thread gameOverThread = new Thread(new Runnable(){
				public void run(){
					try {
						Thread.sleep(2000);
						System.exit(0);
					} catch (InterruptedException e) {}
				}
			});
			//gameOverThread.start();
			
			JOptionPane.showMessageDialog(null,"Game Over");
			System.exit(0);
			
			
			
		}
		
		if(isPressed[x][y])
			return;
		
		
		isPressed[x][y] = true;
		
		if(minesAround[x][y] != 0){
			return;
		}
		
		
		spread(x+1,y);
		spread(x-1,y);
		spread(x,y+1);
		spread(x,y-1);
		
		spread(x+1,y+1);
		spread(x+1,y-1);
		spread(x-1,y+1);
		spread(x-1,y-1);
		
		
	}
	
	
	public void run(){
		while(true){
			render();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	int x=0,y = 0;
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		//g.fillRect(0, 0, SIZE, SIZE);
		int size = SIZE/ARRAY_SIZE;
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, SIZE, SIZE);
		
		for(int i=0;i<ARRAY_SIZE;i++){
			for(int j=0;j<ARRAY_SIZE;j++){
				if(!isPressed[j][i]){
					if(isFlagged[j][i]){
						g.drawImage(imgFlagged, j*size, i*size, size, size, null);
					}
					g.drawImage(imgEmpty, j*size, i*size,size,size, null);	
				}
				else {
					if(a[j][i] == 0){
						int d = minesAround[j][i];
						if(d!=0)
						g.drawImage(numbers[minesAround[j][i]], j*size, i*size, size, size, null);
						else
							g.drawImage(imgPressed, j*size, i*size,size,size, null);
					}
					else
						g.drawImage(imgBomb, j*size, i*size,size,size, null);
				}
				/*if(a[j][i]==0)
					g.drawImage(numbers[minesAround[j][i]], j*size, i*size, size, size, null);
				else
					g.drawImage(imgFlagged, j*size, i*size, size, size, null);*/
			}
		}
		int x = mouse.x;
		int y = mouse.y;
		g.drawImage(imgHovered, x*size, y*size,size,size, null);
		
		
		
		
		g.dispose();
		bs.show();
	}
	
	public void display(){
		int n = this.ARRAY_SIZE;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				System.out.print(a[j][i]);
			}
			System.out.println();
		}
		System.out.println();
		
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				System.out.print(minesAround[j][i]);
			}
			System.out.println();
		}
		
	}
	
	

	public static void main(String[] args){
		new Window();
	}





}
