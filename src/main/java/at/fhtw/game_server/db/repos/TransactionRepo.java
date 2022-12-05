package at.fhtw.game_server.db.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import at.fhtw.game_server.service.models.RegisterUser;

import at.fhtw.game_server.db.dbconfig.ConnectDB;

import at.fhtw.game_server.db.dbconfig.ConnectDB;
import net.bytebuddy.dynamic.scaffold.MethodRegistry;

public class TransactionRepo {
    private Connection db;

    public TransactionRepo(){
        db = (new ConnectDB()).startConnection();
    }

    public int aquirePackage(String token){
        try(
                PreparedStatement newestpackage_statement = db.prepareStatement("""
            SELECT MIN(package) as newestpackageId FROM cards WHERE username IS NULL;
            """);

                PreparedStatement aquire_cards_statement = db.prepareStatement("""
            UPDATE cards SET username = ? WHERE package = ?;
            """);

                PreparedStatement get_username_statement = db.prepareStatement("""
            SELECT username from users WHERE token = ?;
            """);
                PreparedStatement update_coins_statement = db.prepareStatement("""
            UPDATE users  SET coins = ? WHERE username = ?;
            """)
                ){

            String username;
            int coins;

            get_username_statement.setString(1, token);
            ResultSet rs_username = get_username_statement.executeQuery();

            if(rs_username.next() == false){
                return 0;
            }
            else{
                username = rs_username.getString("username");
                coins = rs_username.getInt("coins");
                if(coins < 5){
                    return -1;
                }
                update_coins_statement.setInt(1, coins - 5);
                update_coins_statement.setString(2, username);
            }

            int packageId;
            ResultSet rs = newestpackage_statement.executeQuery();
            if(rs.next() == false){
                return 0;
            }
            else{
                packageId = rs.getInt("newestpackageId");
            }

            aquire_cards_statement.setString(1, username);
            aquire_cards_statement.setInt(2, packageId);
            aquire_cards_statement.execute();

            return 1;

        } catch(SQLException ex){
            ex.printStackTrace();
            return 0;
        }
    }
}
