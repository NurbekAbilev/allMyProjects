package Checker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Checker {
	
	public static final String JAVA_PATH = "C:\\Users\\User\\Documents\\NetBeansProjects\\Test\\build\\web\\uploads";
	public static final String TEST_PATH = "C:\\Users\\User\\Documents\\NetBeansProjects\\Test\\tasks";
	private static final String statusFile = "status.txt";
        private static final boolean WAIT = false;
        
        private static String outputFile = null;
        
	private String fileName;
	private String userName;
        private String taskName;
        
        private String status = "Compiling...";
        private String error = "";
         
    
	
	public Checker(String fileName,String userName,String taskName){
		this.fileName = fileName;
                this.userName = userName;
                this.taskName = taskName;
                outputFile = JAVA_PATH+"\\"+userName+"\\"+taskName+"\\"+statusFile;
	}
	
	public boolean compile(){
            int exitVal = 1;
            try{ 
                updateOutputFile(outputFile);
                if(WAIT){
                    Thread.sleep(2000);
                }
                
            String cmd[] = {"javac.exe",JAVA_PATH+"\\"+userName+"\\"+taskName+"\\"+fileName};  
            
            Runtime rt = Runtime.getRuntime();
            
            Process proc = rt.exec(cmd);
            
            StreamGobbler errorGobbler = new 
                StreamGobbler(proc.getErrorStream(), "ERROR");            
            
            StreamGobbler outputGobbler = new 
                StreamGobbler(proc.getInputStream(), "OUTPUT");
             
            errorGobbler.start();
            outputGobbler.start();
            
            exitVal = proc.waitFor();
            if(exitVal == 0){
                status = "Compiled";
            }
            
            error = errorGobbler.streamBuffer;
            
            System.out.println("ExitValue: " + exitVal);        
            }catch (Throwable t){
                t.printStackTrace();
            }
            return exitVal == 0;
        }
	
	public int runTests(String testsName,int timeLimit){
		
		String name = fileName.substring(0, fileName.indexOf(".java")); // Main.java --> Main
		
		String cmd[] =  {"java.exe","-classpath",JAVA_PATH+"\\"+userName+"\\"+taskName,name};
                
                System.out.println(cmd[2]);
                
		Runtime rt = Runtime.getRuntime();
                Process proc = null;
		int testsNumber = 0, inputSize = 0,outputSize = 0;
		Scanner fileScanner = null;
		
		try {
			fileScanner = new Scanner(new File(TEST_PATH+"\\"+taskName+"\\"+testsName));
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		}
                
		testsNumber = fileScanner.nextInt();
		inputSize = fileScanner.nextInt();
		outputSize = fileScanner.nextInt();
			
		for(int i=0;i<testsNumber;i++){
                    try{
                            if(WAIT){
                                Thread.sleep(2000);
                            }
                            status = "Running on test:"+i;
                            
                            updateOutputFile(outputFile);
                            
                            String inputCase = "";
                            for(int j=0;j<inputSize;j++){
                                    inputCase += fileScanner.nextInt() + " ";
                            }
                            String outputCase = "";
                            for(int j=0;j<outputSize;j++){
                                    outputCase += fileScanner.nextInt() +  " ";
                            }

                            System.out.println(inputCase);

                            proc = rt.exec(cmd);
                            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");            
                            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");
                            errorGobbler.start();
                            outputGobbler.start();
                            OutputStream stdout = proc.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdout));
                            writer.write(inputCase + "\n");
                            writer.flush();

                            if(!proc.waitFor(timeLimit, TimeUnit.SECONDS)) { //timeLimit
                                    error = "Time limit on test "+(i+1);
                                proc.destroy();
                                return i+1;
                            }

                            int exitVal = proc.waitFor();
                            System.out.println("ExitValue: " + exitVal);

                            if(exitVal!=0){
                                    error = "Runtime error on test "+(i+1);
                                    return i+1; // test number
                            }

                            Scanner outputRead = new Scanner(outputGobbler.streamBuffer);
                            Scanner answerRead = new Scanner(outputCase);

                            int cnt = 0;
                            System.out.println(outputGobbler.streamBuffer);
                            System.out.println(outputCase);


                            while(answerRead.hasNextInt()){
                                    if(!outputRead.hasNextInt()){
                                            error = "Presentation error "+(i+1);
                                            return i+1;
                                    }
                                    if(outputRead.nextInt() != answerRead.nextInt()){
                                            error = "Wrong answer on "+(i+1);
                                            return i+1;
                                    }

                            }

                            System.out.println("=================================");

                    }catch(Exception e){
                            System.out.println(e);
                    }
		}
                
                status = "Success!";
                
                updateOutputFile(outputFile);
		return 0;
        }
        
        public void updateOutputFile(String path){
            FileWriter fw;
            try {
                fw = new FileWriter(path);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(status);
                bw.flush();
                bw.close();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Checker.class.getName()).log(Level.SEVERE, null, ex);
            }
                            
        }
        
        
	public String getStatus() {
		return status;
        }
        
        public String getError(){
            return error;
        }
        
        /*public static void main(String[] args) {
            Checker checker = new Checker("Main.java","sample","task01");
            if(checker.compile()){
                System.out.println("True");
            }
            else{
                System.out.println("False");
            }
            checker.runTests("test.txt", 1);
            
        }*/
		
	
    
}