package at.fhtw.game_server.db.dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
    public Connection startConnection(){
        Connection connection = null;

        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:49153/mtcg", "postgres", "postgres");
            if(connection != null){
                System.out.println("Connection OK");
            }
            else{
                System.out.println("Connection failed");
            }
            return connection;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
}
