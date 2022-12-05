package at.fhtw.game_server.db.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import at.fhtw.game_server.service.models.Card;

import at.fhtw.game_server.db.dbconfig.ConnectDB;

public class PackageRepo {

    private Connection db;

    public PackageRepo(){
        db = (new ConnectDB()).startConnection();
    }

    public int createPackage(Card[] cards, String token){
        Card card;
        int packageId;

        try(
                PreparedStatement get_admintoken_statement = db.prepareStatement("""
                SELECT * FROM users WHERE token = ?;
            """)
                ){
            get_admintoken_statement.setString(1, token);
            ResultSet rs_admintoken = get_admintoken_statement.executeQuery();

            if(rs_admintoken.next() == false){
                System.out.printf("Admin not Authorized ");
                return 0;
            }

            else if(!rs_admintoken.getString("username").equals("admin")){
                System.out.printf("admin" + " and " + rs_admintoken.getString("username") + "\n");
                System.out.printf("Admin not Authorized ");
                return 0;
            }

        } catch(SQLException ex){
            ex.printStackTrace();
        }


        try(
                PreparedStatement packageId_statement = db.prepareStatement("""
                SELECT MAX(package) as maxId FROM cards;
                """);
                ){
            ResultSet rs_packageId = packageId_statement.executeQuery();
            if(rs_packageId.next() == false){
                packageId = 0;
            }
            else{
                packageId = rs_packageId.getInt("maxId") + 1;
            }
        } catch (SQLException ex){
            packageId = -1;
            ex.printStackTrace();
            return 0;
        }

        for (int  i = 0; i < cards.length; i++){
            card = cards[i];
            try(
                    PreparedStatement statement = db.prepareStatement("""
                INSERT INTO CARDS (cardid, name, damage, package, elementId, typeId) VALUES (?, ?, ?, ?, ?, ?);
                """);

                    PreparedStatement createElement_statement = db.prepareStatement("""
                INSERT INTO element (name) VALUES (?);
                """);

                    PreparedStatement selectElement_statement = db.prepareStatement("""
                SELECT * FROM element WHERE name = ?;
                """);

                    PreparedStatement createType_statement = db.prepareStatement("""
                INSERT INTO type (name) VALUES (?);
                """);

                    PreparedStatement selectType_statement = db.prepareStatement("""
                SELECT * FROM type WHERE name = ?;
                """);
            ){

                // GET ELEMENT
                selectElement_statement.setString(1, "test");
                ResultSet rs_selectElement = selectElement_statement.executeQuery();
                if(rs_selectElement.next() == false){
                    // Statt "test" richtige Daten einbauen
                    createElement_statement.setString(1, "test");
                    createElement_statement.execute();
                }
                // Statt "test" richtige Daten einbauen
                selectElement_statement.setString(1, "test");
                rs_selectElement = selectElement_statement.executeQuery();
                rs_selectElement.next();

                // GET TYPE
                selectType_statement.setString(1, "test");
                ResultSet rs_selectType = selectType_statement.executeQuery();
                if(rs_selectType.next() == false){
                    // Statt "test" richtige Daten einbauen
                    createType_statement.setString(1, "test");
                    createType_statement.execute();
                }
                // Statt "test" richtige Daten einbauen
                selectType_statement.setString(1, "test");
                rs_selectType = selectType_statement.executeQuery();
                rs_selectType.next();

                statement.setString(1, card.getId());
                statement.setString(2, card.getName());
                statement.setInt(3, card.getDamage());
                statement.setInt(4, packageId);
                statement.setInt(5, rs_selectElement.getInt("elementId"));
                statement.setInt(6, rs_selectType.getInt("typeId"));

                statement.execute();
            } catch(SQLException ex){
                ex.printStackTrace();
                return 0;
            }
        }
        return 1;
    }
}
