package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


    public class DBConnection {
        private static Connection connection;
    
        public static void connectToDatabase() {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=E_Library_DB;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";            
            try 
            {
                connection = DriverManager.getConnection(url);
                System.out.println("Database connected!");
            } 
            catch (SQLException e)
            {
                System.out.println("Connection failed");
                e.printStackTrace();
            }
        }
    
        public static Connection getConnection() {
            try {
                if (connection == null || connection.isClosed()) {
                    connectToDatabase();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
    }
    
//String url = "jdbc:sqlserver://localhost\\MSSQLSERVERR:1433;databaseName=E_Library_DB;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";


