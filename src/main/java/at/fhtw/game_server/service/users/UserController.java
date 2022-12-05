package at.fhtw.game_server.service.users;

import at.fhtw.game_server.db.repos.UserRepo;
import at.fhtw.game_server.service.models.RegisterUser;
import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Response;
import at.fhtw.game_server.controller.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;

public class UserController extends Controller{
    private UserRepo db;
    public UserController(){
        db = new UserRepo();
    }
    public Response register(String body){
        try{
            RegisterUser reg_user = this.getObjectMapper().readValue(body, RegisterUser.class);

            if(db.registerUser(reg_user) == 1){
                return new Response(
                        HttpStatus.CREATED,
                        ContentType.JSON,
                        "{ message: \"Success\" }"
                );
            }
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }

    public Response getUser(String username, String token){
        try{

            RegisterUser reg_user = db.getUserData(username, token);

            String userDataJSON = this.getObjectMapper().writeValueAsString(reg_user);

            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    userDataJSON
            );

        } catch(JsonProcessingException e){
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }

    public Response updateUser(String username, String body, String token){
        try{
            RegisterUser update_user = this.getObjectMapper().readValue(body, RegisterUser.class);

            if(db.updateUser(update_user, username, token) == 1){
                return new Response(
                        HttpStatus.ACCEPTED,
                        ContentType.JSON,
                        "{ message: \"Success\" }"
                );
            }
        }
        catch(JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
