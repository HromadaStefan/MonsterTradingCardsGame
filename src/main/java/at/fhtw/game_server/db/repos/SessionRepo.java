package at.fhtw.game_server.db.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import at.fhtw.game_server.service.models.Session;

import at.fhtw.game_server.db.dbconfig.ConnectDB;

public class SessionRepo {
    private Connection db;
    public SessionRepo(){
        db = (new ConnectDB()).startConnection();
    }

    public String loginUser(Session create_session) {
        try(
                PreparedStatement statement = db.prepareStatement("""
                    Select * FROM users WHERE username = ? AND password = ?;
                """)
        ){
            statement.setString(1, create_session.getUsername());
            statement.setString(2, create_session.getPassword());
            ResultSet rs = statement.executeQuery();

            if (rs.next() == false){
                return null;
            }
            else{
                Session session = new Session(rs.getString("username"), rs.getString("password"));

                String token = "Basic " + session.getUsername() + "-mtcgToken";

                PreparedStatement post_token = db.prepareStatement("""
                UPDATE users 
                SET     token = ?
                WHERE username = ? AND password = ?; 
                """);

                post_token.setString(1, token);
                post_token.setString(2, session.getUsername());
                post_token.setString(3, session.getPassword());

                post_token.execute();

                return token;
            }
        } catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
