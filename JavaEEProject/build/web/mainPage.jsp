<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Util.DBUtil" %>

<%
    String username = (String)request.getSession(false).getAttribute("username");
    String password = (String)request.getSession(false).getAttribute("password");
    
    DBUtil util = new DBUtil();
    
    if(util.isAdmin(username, password)){
        response.sendRedirect("adminPage.jsp");
    }
    
     ResultSet rs = util.getProblems();
     //util.closeConnection();
     
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!--<link rel="stylesheet" type="text/css" href="style/mainPage.css">-->
    </head>
    <style>html{background:#b3ffff;}</style>
        
    <body> 
        <a href="leaderboard.html" style="margin:20px">Leaderboard</a>
        <table>
            <tr><th>Task</th><th>Solved</th></tr>
            <%
                while(rs.next()){
                    String name = rs.getString("name");
                    String solved = rs.getString("solved");
                    out.print("<tr>");
                    out.print("<td><a href='randomJsp.jsp?id="+rs.getString("id")+"'>"+name+"</a></td>");
                    out.print("<td>"+solved+"</td>");
                    out.print("</tr>");
                }
                util.closeConnection();
                
            %>
        </table>
    </body>
</html>
