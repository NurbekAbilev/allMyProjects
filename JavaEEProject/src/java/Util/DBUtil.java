
package Util;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    
    static final String user = "root";
    static final String pass = "root";
    static final String DBname = "mydb";
    
    private Connection connection;
    
    public Connection getConnection(){
        return connection;
    }

    public DBUtil(){
        try{
            Class.forName("com.mysql.jdbc.Driver") ;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBname, user, pass) ;
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void closeConnection(){
        try{
            connection.close();
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public ResultSet getRSFromQuery(String q){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(q);
            rs = ps.executeQuery();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return rs;
    }
    
    public boolean userExists(String username,String password){
        
        boolean returnValue = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Statement st = null;
        
        String q = "SELECT EXISTS(SELECT 1 FROM users WHERE username = ? and password = ?)";
        
        try {
            
            ps = connection.prepareStatement(q);
            ps.setString(1, username);
            ps.setString(2, password);
            
            System.out.println(ps);
            
            rs = ps.executeQuery();
            
            rs.next();
            if(rs.getBoolean(1)){
                returnValue = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return returnValue;
    }
    
    public boolean userExists(String username){
        
        boolean returnValue = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Statement st = null;
        
        String q = "SELECT EXISTS(SELECT 1 FROM users WHERE username = ?)";
        
        try {
            
            ps = connection.prepareStatement(q);
            ps.setString(1, username);
            
            System.out.println(ps);
            
            rs = ps.executeQuery();
            
            rs.next();
            if(rs.getBoolean(1)){
                returnValue = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return returnValue;
    }
    
    public void addUser(String username,String password){
        String q = "insert into users(username,`password`) values(?,?);"; // OTHER FIELDS HAVE DEAFAULT VALUES
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            ps = connection.prepareStatement(q);
            
            ps.setString(1,username);
            ps.setString(2,password);
            
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public boolean isAdmin(String username,String password){
        String q = "select * from users where username = ? and password = ? limit 1;";
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean retVal = false;
        
        try{
            ps = connection.prepareStatement(q);
            
            ps.setString(1,username);
            ps.setString(2,password);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                if(rs.getInt("user_type") == 1){  // 1 - ADMIN , 0 - DEFAULT USER
                    retVal = true;
                }
            }
        
        }catch(SQLException e){
            System.out.println(e);
        }
        
        return retVal;
        
    }
    
    
    public void addProblem(String name,String constraints,String notes){
        ResultSet rs = null;
        PreparedStatement ps = null;
        String q = "insert into tasks (name,constraints,notes) values (?,?,?);";
        
        try {
            ps = connection.prepareStatement(q);
            ps.setString(1,name);
            ps.setString(2,constraints);
            ps.setString(3,notes);
            
            ps.execute();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public ResultSet getProblems(){
        ResultSet rs = null;
        PreparedStatement ps = null;
        String q = "select * from tasks";
        
        try {
            ps = connection.prepareStatement(q);
            rs = ps.executeQuery();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rs;
    }
    
    public void deleteUserById(int id){
        ResultSet rs = null;
        PreparedStatement ps = null;
        String q = "delete from users where id = ?;";
        
        try {
            ps = connection.prepareStatement(q);
            ps.setInt(1, id);
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public ResultSet getProblemById(int id){
        ResultSet rs = null;
        PreparedStatement ps = null;
        String q = "select * from tasks where id = ?";
        
        try {
            ps = connection.prepareStatement(q);
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rs;
    }
    
    public ResultSet getAllUsers(){
        ResultSet rs = null;
        PreparedStatement ps = null;
        String q = "select * from users where user_type!=1;";
        
        try {
            ps = connection.prepareStatement(q);
            rs = ps.executeQuery();
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return rs;
    }
    
    public static void main(String[] args) throws FileNotFoundException  {
        
        
        DBUtil util = new DBUtil();
        
        util.deleteUserById(5);
        
        
        util.closeConnection();
        
        
        
    }
    
    
}
