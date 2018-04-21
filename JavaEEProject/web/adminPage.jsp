
<%@page import="java.sql.ResultSet"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.BufferedWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import= "Util.DBUtil"%>

<%
    DBUtil util = new DBUtil();
    
    if(request.getParameter("addUser")!=null){
        out.print("<h1>Command : AddUser </h1>");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        util.addUser(username, password);
    }
    
    if(request.getParameter("deleteUser")!=null){
        out.print("<h1>Command : deleteUser </h1>");
        int id = Integer.parseInt((String)request.getParameter("id"));
        util.deleteUserById(id);
    }
    
    if(request.getParameter("addProblem")!=null){
        out.print("<h1>Command : addProblem </h1>");
        
        String test = request.getParameter("test");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String constraints = request.getParameter("constraints");
        String notes = request.getParameter("notes");
        
        String path = "C:/Users/User/Documents/NetBeansProjects/Test/tasks/" + name;
        
        File file = new File(path);
        file.mkdirs();
        
        util.addProblem(name, constraints, notes);
        
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            
            fw = new FileWriter(path + "/test.txt");
            bw = new BufferedWriter(fw);
            bw.write(test);
            bw.close();
            fw.close();
            
            fw = new FileWriter(path + "/description.txt");
            bw = new BufferedWriter(fw);
            bw.write(description);
            bw.close();
            fw.close();
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    if(request.getParameter("deleteProblem")!=null){
        out.print("<h1>Command : deleteProblem </h1>");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Admin Page</title>
        <style>
            *{
                margin: 2px;
                padding: 0px;
            }
        </style>
    </head>
    <body>
        <a href="index.html"><p>Go back to Welcoming page</p></a>
        <form action = "adminPage.jsp" method="post">
            <p>Username:<input type="text" name="username"></p>
            <p>Password:<input type="text" name="password"></p>
            <input type="submit" name="addUser" value="Add user">
        </form>
        <hr>
        <form action = "adminPage.jsp" method="post">
            <% 
                ResultSet rs = util.getAllUsers();
                while(rs.next()){
            %>
            <p><input type="radio" name="id" value="<%= rs.getString("id")%>"><%= rs.getString("username") + " " + rs.getString("password") + " " + rs.getString("rating")%></p>
            <%
              }
            %>
            <input type="submit" name="deleteUser" value="Delete User">
            
        </form>
        <hr>
        
        <form action = "adminPage.jsp" method="post">
            <p>Name:<input type="text" name="name" required></p>
            <p>Description:</p><textarea rows="5" cols="45" name="description"></textarea><br>
            <p>Constraints(time limit and other stuff):</p><input type="text" name="constraints"><br>
            <p>Test Cases(1:number of tests,2:input size,3:output size):</p><textarea name="test" cols="45" rows="5"></textarea><br>
            <p>Notes:</p><input type="textarea" name="notes"><br>
            <input type="submit" name="addProblem" value="Add Problem Task">
        </form>
        
        
    </body>
</html>

<%
    util.closeConnection();
%>
