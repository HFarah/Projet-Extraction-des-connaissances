package projetextractionconnaissance2016;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnexionDB {
    
    //private static final String urlbase = "jdbc:mysql://localhost:3306/bdopinionfilm";
    
    private static final String urlbase = "jdbc:postgresql://localhost:5432/postgres";
    
    private static Connection connection;
    
    private ConnexionDB() throws ClassNotFoundException{
        
          try {
              
            //Class.forName("com.mysql.jdbc.Driver");
            
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(urlbase, "postgres", "Admin123");
            
          } catch (SQLException e) 
          {
              System.err.println("Erreur de connexion :\t"+e.getMessage());
          }
    }
    
    public static Connection getConnection() throws ClassNotFoundException{
        
        if(connection == null){
            
           new ConnexionDB();
          
        }
        
    return connection;   
  }  
  
}



