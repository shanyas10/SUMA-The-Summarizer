
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sanya
 */
class Database 
{
    Connection connection;
    double Position, NumericalData, ICommas, Length, Keyword, TitleSimilarity, CentroidCoherence, Names, CuePhase;
    String Classi;
    public Database()
    {
        //System.out.println("Database");
        
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
    }
    
    public void store()
    {
        try {
            Statement stmt = connection.createStatement();
            String insert="insert into Weights (Position,NumericalData,ICommas,Length,Keyword,TitleSimilarity,CentroidCoherence,Names,CuePhrase,Class) values ("+Position+","+NumericalData+","+ICommas+","+Length+","+Keyword+","+TitleSimilarity+","+CentroidCoherence+","+Names+","+CuePhase+",'"+Classi+"')";
            stmt.executeUpdate(insert);
        } catch (Exception ex) {
            ex.printStackTrace();
        }    
    }

    int getId() {
        Statement st;
        int lastid = 0;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(ID) AS id FROM Weights");
            while( rs.next() )
            {
                lastid = rs.getInt("id");
                System.out.println(lastid);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lastid;
         //To change body of generated methods, choose Tools | Templates.
    }

    void set(double value, String attribute) 
    {
        if(attribute=="Position")
        {
            Position=value;
        }
        if(attribute=="Length")
        {
            Length=value;
        }
        if(attribute=="Key")
        {
            Keyword=value;
        }
        if(attribute=="Number")
        {
            NumericalData=value;
        }
        if(attribute=="Name")
        {
            Names=value;
        }
       
    }

    void set(String value) 
    {       
        Classi=value;
    }
}