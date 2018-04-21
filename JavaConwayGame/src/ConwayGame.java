
public class ConwayGame {
	public static final int MAX_GRID_SIZE = 100;
	
	public int grid[][];
	public int size;
	
	private int xLoop[] = {-1,0,1, -1, 1, -1,0,1};
	private int yLoop[] = {1 ,1,1,  0, 0, -1,-1,-1};
	
	public ConwayGame(int [][] grid,int size){
		this.grid = grid;
		this.size = size;
	}
	
	//if exists 0/1 otherwise -1 ( out of bounds)
	public int getContent(int y,int x){
		if(x>=size || y>=size || x<0 || y<0){
			return 0;
		}
		return grid[y][x];
	}
	
	public void iterateStep(){
		
		int temp[][] = new int[size][size];
		
//		System.out.println("==================");
//		for(int i=0;i<size;i++){
//			for(int j=0;j<size;j++){
//				System.out.print(countOnesAround(i,j)+" ");
//			}
//			System.out.println();
//		}
//		
		
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(countOnesAround(i,j)>=4){
					temp[i][j] = 0;
				}
				if(countOnesAround(i,j)<=1){
					temp[i][j] = 0;
				}
				if(countOnesAround(i,j)==3){
					temp[i][j] = 1;
				}
				if(countOnesAround(i,j)==2){
					temp[i][j] = grid[i][j];
				}
				
			}
		}
		
		this.grid = temp;
		
	}
	
	//assume grid[y][x] is valid
	public int countOnesAround(int y,int x){
		int count = 0;
		for(int i=0;i<yLoop.length;i++){
			count+=getContent(y+yLoop[i],x+xLoop[i]);
		}
		return count;
	}
	
	public void debugGrid(){
		System.out.println("[Debug grid]:");
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
}
