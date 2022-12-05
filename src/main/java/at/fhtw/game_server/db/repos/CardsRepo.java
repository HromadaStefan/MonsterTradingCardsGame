package at.fhtw.game_server.db.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import at.fhtw.game_server.db.dbconfig.ConnectDB;
import at.fhtw.game_server.service.models.Card;

public class CardsRepo {
    private Connection db;

    public CardsRepo(){
        db = (new ConnectDB()).startConnection();
    }

    public Card[] getCards(String token){
    try(
            PreparedStatement get_username_statement = db.prepareStatement("""
            SELECT username FROM users  WHERE token = ?;
            """);

            PreparedStatement getCards_statement = db.prepareStatement("""
            SELECT\s
            	c.cardid,
            	c.username,
            	c.name,
            	c.damage,
            	c.package,
            	e.name as elementname,
            	t.name as typename
            FROM cards c
            JOIN element e ON e.elementid = c.elementid
            JOIN type t ON t.typeid = c.typeid
            WHERE username = ?;
            """)
            ){

            List<Card> cards = new ArrayList<Card>();

            get_username_statement.setString(1, token);
            ResultSet rs_username = get_username_statement.executeQuery();
            if(rs_username.next() == false){
                return null;
            }

            String username = rs_username.getString("username");

            getCards_statement.setString(1, username);
            ResultSet rs_cards = getCards_statement.executeQuery();


            while(rs_cards.next() != false){
                cards.add(new Card(rs_cards.getString("cardid"), rs_cards.getString("name"), rs_cards.getInt("damage"), rs_cards.getString("elementname"), rs_cards.getString("typename")));
            }

            Card[] cards_array = new Card[cards.size()];
            cards.toArray(cards_array);
            return cards_array;
    }
    catch (SQLException ex){
        ex.printStackTrace();
        return null;
    }
    }
}
