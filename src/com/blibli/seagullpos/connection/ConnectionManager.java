package com.blibli.seagullpos.connection;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionManager {

    private static ConnectionManager connectionManager;
    private static final String url = "jdbc:postgresql://localhost:5432/seagullpos";
    private static Properties props;

    private ConnectionManager(){
        try{
            Class.forName("org.postgresql.Driver");
            props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "DENNIS12345");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public static Connection createConnection() throws SQLException{
        if(connectionManager == null){
            connectionManager = new ConnectionManager();
        }
        try{
            return DriverManager.getConnection(url, props);
        }catch (SQLException e){
            throw e;
        }
    }

    public static void closeConnection(Connection connection){
        try{
            if(connection != null){
                connection.close();
            }
        }catch (SQLException sq){
            sq.printStackTrace();
        }
    }
}
