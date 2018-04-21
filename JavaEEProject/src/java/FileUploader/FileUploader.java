package FileUploader;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
 

public class FileUploader {
    
        private final boolean DEBUG = false;
    
        private String fileName;
        private String applicationPath;
        private String UPLOAD_DIR;
        private String uploadFilePath;
 
        private HttpServletRequest request;
        
        private String username;
        private String task;
                
        public FileUploader(HttpServletRequest request,String username,String task){
            
            applicationPath = request.getServletContext().getRealPath("");
            UPLOAD_DIR = "uploads";
            
            this.username = username;
            this.task = task;
            this.request = request;
            
            uploadFilePath = applicationPath + File.separator + UPLOAD_DIR + "\\" + username + "\\" + task;
            
            if(DEBUG){
                System.out.println(this.applicationPath);
                System.out.println(this.uploadFilePath);
                System.out.println(this.UPLOAD_DIR);
            }
            
        }
        
        public String getFileName(Part part) {
            String contentDisp = part.getHeader("content-disposition"); 
            if(DEBUG){
                System.out.println("content-disposition header= "+contentDisp);
            }
            String[] tokens = contentDisp.split(";");
            for (String token : tokens) {
                if (token.trim().startsWith("filename")) {
                    return token.substring(token.indexOf("=") + 2, token.length()-1);
                }
            }
            return "";
        }
        
        public void Upload(){
            File fileSaveDir = new File(uploadFilePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }   
            
            if(DEBUG){
                System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
            }
            
            fileName = null;
            try {
                for (Part part : request.getParts()) {
                    fileName = getFileName(part);
                    System.out.println("Part :" + fileName);
                    part.write(uploadFilePath + File.separator + fileName);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        

    public String getFileName() {
        return fileName;
    }    
        
    public String getApplicationPath() {
        return applicationPath;
    }

    public String getUploadDir() {
        return UPLOAD_DIR;
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
