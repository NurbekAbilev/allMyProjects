package Checker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class StreamGobbler extends Thread
	{
	    InputStream is;
	    String type;
	    String streamBuffer = "";
            String status = "";
            
	    StreamGobbler(InputStream is, String type)
	    {
	        this.is = is;
	        this.type = type;
                streamBuffer = "";
	    }
	    
	    public void run()
	    {
	        try
	        {
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            String line=null;
	            while ( (line = br.readLine()) != null){
	                System.out.println(type + ">" + line);
                        streamBuffer = streamBuffer + line + "\n";
                    }
	            } catch (IOException ioe)
	              {
	                ioe.printStackTrace();  
	              }
	    }
	}
	
