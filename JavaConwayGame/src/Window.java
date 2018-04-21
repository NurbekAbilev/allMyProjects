import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Window {
	
	public Window(){
		
		JFrame frame = new JFrame("GAME ABILEV NURBEK");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        
        Grid grid = new Grid(8,8,null);
        
        JButton buttonOpen = new JButton("OpenFile");
        buttonOpen.setBounds(10, 532, 100, 30);
        buttonOpen.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(new File("").getAbsolutePath());
				
				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					System.out.println(jfc.getSelectedFile().getAbsolutePath());
					int gr [][] = UtilReader.readFile(selectedFile.getAbsolutePath());
					ConwayGame cg = new ConwayGame(gr,gr.length);
					grid.addMouseListener(new MyMouseListener(grid));
					grid.setGame(cg);
					grid.start();
					buttonOpen.setEnabled(false);
				}
				
			}
        	
        });
        
        
        frame.add(buttonOpen);
        frame.add(grid);
        
        
        frame.setSize(532, 640);
        frame.setVisible(true);
        
        
        
        
	}
	
	
}
