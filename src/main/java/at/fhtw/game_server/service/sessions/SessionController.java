package at.fhtw.game_server.service.sessions;

import at.fhtw.game_server.db.repos.SessionRepo;
import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Response;
import at.fhtw.game_server.controller.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import at.fhtw.game_server.service.models.Session;

import java.lang.constant.Constable;


public class SessionController extends Controller{
    private SessionRepo db;

    public SessionController(){
        db = new SessionRepo();
    };

    public Response login(String body){
        try{
            Session create_session = this.getObjectMapper().readValue(body, Session.class);

            String token = db.loginUser(create_session);


            if(token != null){
            String tokenJSON = this.getObjectMapper().writeValueAsString(token);
                return new Response(
                        HttpStatus.CREATED,
                        ContentType.JSON,
                        tokenJSON
                );
            }
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }

}
