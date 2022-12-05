package at.fhtw.game_server.db.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import at.fhtw.game_server.service.models.RegisterUser;

import at.fhtw.game_server.db.dbconfig.ConnectDB;

public class UserRepo {
    private Connection db;

    public UserRepo(){
        db = (new ConnectDB()).startConnection();
    }

    public int registerUser(RegisterUser reguser){
        try(
                PreparedStatement statement = db.prepareStatement("""
            INSERT INTO users 
            (username, password) 
            VALUES(?, ?);
            """)
        ){
            statement.setString(1, reguser.getUsername());
            statement.setString(2, reguser.getPasswort());
            statement.execute();
            return 1;
        } catch(SQLException ex){
            ex.printStackTrace();
            return 0;
        }
    }

    public RegisterUser getUserData(String username, String token){
        try(
                PreparedStatement statement = db.prepareStatement("""
            SELECT * FROM users WHERE username = ? AND token = ?;    
            """)
        ){
            statement.setString(1, username);
            statement.setString(2, token);
            ResultSet rs = statement.executeQuery();


            if(rs.next() == false){
                return null;
            }
            else{
                RegisterUser getuser = new RegisterUser(rs.getString("username"), rs.getString("password"), rs.getInt("coins"), rs.getInt("elo"), rs.getString("token"));
                return getuser;
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public int updateUser(RegisterUser userdata, String username, String token){
        try(
                PreparedStatement statement = db.prepareStatement("""
                UPDATE users SET username = ?,
                password = ? WHERE username = ? AND token = ?;
        """)){
            statement.setString(1, userdata.getUsername());
            statement.setString(2, userdata.getPasswort());
            statement.setString(3, username);
            statement.setString(4, token);

            statement.execute();
            return 1;

        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return -1;
    }

}
