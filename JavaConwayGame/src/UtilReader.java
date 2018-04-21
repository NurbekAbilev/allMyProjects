import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UtilReader {
	
	public static int[][] readFile(String path){
		Scanner textReader = null;
		try {
			textReader = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int size = textReader.nextInt();
		
		if(size>ConwayGame.MAX_GRID_SIZE){
			System.err.println("Input is too large");
			System.exit(1);
		}
		
		int [][] ret = new int[size][size];
		
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				ret[i][j] = textReader.nextInt();
			}
		}
		
		return ret;
		
	}
	
}
