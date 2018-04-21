<%@page import="java.util.Scanner"%>
<%@page import="java.io.File"%>
<%@page import="java.sql.*"%>
<%@page import="Util.DBUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    DBUtil util = new DBUtil();
    PreparedStatement ps = null;
    ResultSet rs = null;
    String id = request.getParameter("id");
    
    if(id==null){
        id = (String)request.getSession().getAttribute("id");
    }
    
    String q = "select * from tasks where id = ?";
    try{
        ps = util.getConnection().prepareStatement(q);
        ps.setString(1, id);
        rs = ps.executeQuery();
    }catch(SQLException e){
        System.out.println(e);
    }
    rs.next();
    String name = rs.getString("name");
    String constraints = rs.getString("constraints");
    String notes = rs.getString("notes");
    
    String realpath = getServletContext().getRealPath("/");
    System.out.println(realpath);
    
    String path = realpath + "../../tasks/" + name + "/description.txt";
    Scanner scanner = new Scanner(new File(path));
    String description = "";
    while(scanner.hasNextLine()){
        description += scanner.nextLine() + '\n';
    }
    System.out.println(description);
    
    path = realpath + "../../tasks/" + name + "/test.txt";
    scanner = new Scanner(new File(path));
    
    String test = "";
    
    scanner.nextInt();
    int input = scanner.nextInt();
    int output = scanner.nextInt();
    int show = input+output;
    System.out.println(show);
    int cnt = 0;
    while(cnt<show && scanner.hasNext()){
        test += scanner.nextInt() + " ";
        cnt++;
    }
    
    
    request.getSession(false).setAttribute("name",name);
    request.getSession(false).setAttribute("id",id);
   
    util.closeConnection();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><%= name %></h1>
        <p><%= constraints %></p>
        <p><%= notes %></p>
        <p><%= description %></p>
                    <p><%= test %></p>
        <form action="FileUploadServlet" method="post" enctype="multipart/form-data">
            <p>Select File to Upload:</p><input type="file" name="fileName">
            <p><input type="submit" name="submitFile"></p>
        </form>
        <hr>
        <%
            String result = (String)request.getAttribute("submit");
            if(result!=null){
                String username = (String)request.getSession(false).getAttribute("username");
        %>
            <h1>Result: <div id='result'>Compiling...</div></h1>
            
            <script>
                var loop = true;
                
                setInterval(loadDoc,200);
                function loadDoc() {
                    if(loop){
                        //alert("SomeStuff");
                        var xhttp = new XMLHttpRequest();
                        var addr = "uploads/<%= username %>/<%= name %>/status.txt";
                        xhttp.onreadystatechange = function() {
                          if (this.readyState == 4 && this.status == 200) {
                           document.getElementById("result").innerHTML = this.responseText;
                           if(this.responseText == "Success!"){
                               loop = false;
                           }
                          }
                        };
                        xhttp.open("GET", addr, true);
                        xhttp.send();
                    }
                }
        </script>
        <%
            }
        %>
        
    </body>
</html>
