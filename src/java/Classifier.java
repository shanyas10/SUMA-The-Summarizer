
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shanya
 */
public class Classifier {
    
    Connection connection;
    double total=0,yes=0,no=0,pos,num,key,names,length,yprior,nprior;
    
    public Classifier()
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = null;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/summarizer","root", "");
            
        } 
        catch (Exception e) 
        {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        
        
        double prior=0;
        try 
        {
            Statement stmt = connection.createStatement();
            String query = "SELECT COUNT(*) FROM Weights";
            ResultSet rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                total=rs.getInt(1);				
            }
            
            rs.close();
            String yquery = "SELECT COUNT(*) FROM weights WHERE Class = 'Yes'";
            ResultSet rsy=stmt.executeQuery(yquery);				
            while(rsy.next())
            {
                yes=rsy.getInt(1);				
            }
            rsy.close();
            
            String nquery = "SELECT COUNT(*) FROM weights WHERE Class = 'No'";
            ResultSet rsn=stmt.executeQuery(nquery);				
            while(rsn.next())
            {
                no=rsn.getInt(1);				
            }
            //System.out.println(total+" yes "+yes+" no "+no);
            rsn.close();
            
        } 
        catch (Exception ex) 
        {
            System.out.println(ex);
            System.out.println("Poor");
        }
        pos();
        length();
        name();
        num();
        key();
        yprior();
        nprior();
        //System.out.println(pos+" "+length+" "+names+" "+num+" "+key);
    }
    
    private void pos()
    {
        try 
        {
            Statement stmt = connection.createStatement();
            String query = "SELECT COUNT(DISTINCT Position) FROM Weights";
            ResultSet rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                pos=rs.getInt(1);				
            }
            
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //System.out.println(pos);
        
    }
    
    private void length()
    {
        try 
        {
            Statement stmt = connection.createStatement();
            String query = "SELECT COUNT(DISTINCT Length) FROM Weights";
            ResultSet rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                length=rs.getInt(1);				
            }
            
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private void num()
    {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT COUNT(DISTINCT NumericalData) FROM Weights";
            ResultSet rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                num=rs.getInt(1);				
            }
            
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
    }
    
    private void key()
    {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT COUNT(DISTINCT Keyword) FROM Weights";
            ResultSet rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                key=rs.getInt(1);				
            }
            
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex);
        } 
    }
    
    private void name()
    {
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT COUNT(DISTINCT Names) FROM Weights";
            ResultSet rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                names=rs.getInt(1);				
            }
            
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private void yprior()
    {
        yprior=yes/total;
    }
    
    private void nprior()
    {
        nprior=no/total;
    }
}
