package Checker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUpdater {
    
   
    public static void main(String[] args) {
        
        try{
            FileWriter fw = new FileWriter("file.txt",false);
            BufferedWriter bw = new BufferedWriter(fw);
            
            int cnt = 0;
            while(true){
                fw = new FileWriter("file.txt",false);
                bw = new BufferedWriter(fw);
                bw.write(cnt+"");
                bw.flush();
                Thread.sleep(1000);
                cnt++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
    
}
    