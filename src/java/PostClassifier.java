
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sanya
*/

public class PostClassifier extends Classifier {
    double ypos = 0, npos = 0, ylength = 0, nlength = 0, yname = 0, ykeyw = 0, ynum = 0, nname = 0, nkeyw = 0, nnum = 0;
    public PostClassifier()
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = null;
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/summarizer","root", "");
            
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            System.out.println("Connection Failed! Check output console");
        }
    }
    public String finalClass(double pos,double length,double name,double keyw,double num)
    {
        
        String query;
        ResultSet rs;
        try 
        {
            Statement stmt = connection.createStatement();
            query= "SELECT COUNT(*) FROM Weights where Position="+pos+" and Class= 'Yes'";
            rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                ypos=rs.getInt(1);				
            }
            
            query= "SELECT COUNT(*) FROM Weights where Position="+pos+" and Class= 'No'";
            rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                npos=rs.getInt(1);				
            }
            
            query= "SELECT COUNT(*) FROM Weights where Length="+length+" and Class= 'Yes'";
            rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                ylength=rs.getInt(1);				
            }
            
            query= "SELECT COUNT(*) FROM Weights where Length="+length+" and Class= 'No'";
            rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                nlength=rs.getInt(1);				
            }
            
            query= "SELECT COUNT(*) FROM Weights where NumericalData="+num+" and Class= 'Yes'";
            rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                ynum=rs.getInt(1);				
            }
            
            query= "SELECT COUNT(*) FROM Weights where NumericalData="+num+" and Class= 'No'";
            rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                nnum=rs.getInt(1);				
            }
            
            query= "SELECT COUNT(*) FROM Weights where Keyword="+keyw+" and Class= 'Yes'";
            rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                ykeyw=rs.getInt(1);				
            }
            
            query= "SELECT COUNT(*) FROM Weights where Keyword="+keyw+" and Class= 'No'";
            rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                nkeyw=rs.getInt(1);				
            }
            
            query= "SELECT COUNT(*) FROM Weights where Names="+name+" and Class= 'Yes'";
            rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                yname=rs.getInt(1);				
            }
            
            query= "SELECT COUNT(*) FROM Weights where Names="+name+" and Class= 'No'";
            rs=stmt.executeQuery(query);				
            while(rs.next())
            {
                nname=rs.getInt(1);				
            }
            rs.close();
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex);
            System.out.println("Poor");
        }
        String cal=mleCalc();
        return cal;
    }       

    private String mleCalc()
    {
      double yPos = 0, nPos = 0, yLength = 0, nLength = 0, yName = 0, yKeyw = 0, yNum = 0, nName = 0, nKeyw = 0, nNum = 0;  
      double Y,N;
      String _class = null;
      yPos= (ypos+1)/(yes+pos);
      nPos= (npos+1)/(no+pos);
      yLength= (ylength+1)/(yes+length);
      nLength= (nlength+1)/(no+length);
      yName= (yname+1)/(yes+names);
      nName= (nname+1)/(no+names);
      yNum= (ynum+1)/(yes+num);
      nNum= (nnum+1)/(no+num);
      yKeyw= (ykeyw+1)/(yes+key);
      nKeyw= (nkeyw+1)/(no+key);     
      Y=(yPos*yLength*yName*yKeyw*yNum)*yprior;
      N=(nPos*nLength*nName*nKeyw*nNum)*nprior;
      if(Y>N)
      {
          _class="Yes";
      }
      else if(N>=Y)
          _class="No";
      
      return _class;
    }
}
