import Checker.Checker;
import FileUploader.FileUploader;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
 
@WebServlet("/FileUploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB 
                 maxFileSize=1024*1024*1,      	// 1 MB
                 maxRequestSize=1024*1024*1)   	// 1 MB

//maxFileSize=1024*1024*50,

public class FileUploadServlet extends HttpServlet {
 
    private static final long serialVersionUID = 205242440643911308L;
     
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        String username = (String) request.getSession(false).getAttribute("username");
        String task = (String) request.getSession(false).getAttribute("name");
        String uploadDir = null;
        String fileName = null;
        
        
        try{
            FileUploader upload = new FileUploader(request,username,task);

            upload.Upload();
            Collection<Part> parts = upload.getRequest().getParts();

            for(Part part : parts){
                fileName = upload.getFileName(part);
                break;
            }
            uploadDir = upload.getUploadFilePath();
        }catch(Exception e){
            throw new ServletException(e);
        }
        System.out.println("FileName: " + fileName);
        System.out.println("UploadDir: " + uploadDir);
        
        
        request.setAttribute("submit","submit");
        
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/randomJsp.jsp");
        requestDispatcher.forward(request, response);
        
        Checker checker;
        checker = new Checker(fileName,username,task);
        if(checker.compile()){
            System.out.println("Compiled Succesfully");
            checker.runTests("test.txt", 1);
        }
        else{
            System.out.println("Compilation Error");
        }
        
        System.out.println("CHECKING IS DONE !!!");
        
        
    }
    
    /*class CustomThread extends Thread{
        Checker checker = null;
        public CustomThread(Checker checker,String fileName,String username,String task){
            this.checker = checker;
        }
        
        public void run(){
            Checker checker;
                checker = new Checker(fileName,username,task);
                if(checker.compile()){
                    System.out.println("True");
                }
                else{
                    System.out.println("False");
                }
                checker.runTests("test.txt", 1);
        }
        
    }*/
 
    
}