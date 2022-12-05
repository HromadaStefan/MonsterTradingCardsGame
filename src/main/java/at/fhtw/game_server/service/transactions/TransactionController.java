package at.fhtw.game_server.service.transactions;

import at.fhtw.game_server.db.repos.TransactionRepo;
import at.fhtw.game_server.service.models.RegisterUser;
import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Response;
import at.fhtw.game_server.controller.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;

public class TransactionController extends Controller{
    private TransactionRepo db;

    public TransactionController() { db = new TransactionRepo();}

    public Response aquire_package(String token){
        if(db.aquirePackage(token) == 1){
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    "{\"message\" : \"Success\"}"
            );
        } else{
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    "{\"message\" : \"Error aquiring package\"}"
            );
        }
    }

}
