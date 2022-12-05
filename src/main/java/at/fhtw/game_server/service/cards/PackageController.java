package at.fhtw.game_server.service.cards;

import at.fhtw.game_server.db.repos.PackageRepo;
import at.fhtw.game_server.service.models.Card;
import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Response;
import at.fhtw.game_server.controller.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.swing.text.AbstractDocument;

public class PackageController extends Controller{
    private PackageRepo db;
    public PackageController() { db = new PackageRepo();}

    public Response addPackage(String body, String token){
        try{
            Card[] cardobjects = this.getObjectMapper().readValue(body, Card[].class);
            if(db.createPackage(cardobjects, token) == 1){
                return new Response(
                        HttpStatus.CREATED,
                        ContentType.JSON,
                        "{ \" message \" : \"Package created\"}"
                );
            }
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "{ \" message \" : \"Error creating Package\"}"
        );
    }
}
