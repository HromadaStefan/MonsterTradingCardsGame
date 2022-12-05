package at.fhtw;

import at.fhtw.game_server.service.cards.CardsService;
import at.fhtw.game_server.service.cards.PackageService;
import at.fhtw.httpserver.utils.Router;
import at.fhtw.httpserver.server.Server;
import at.fhtw.sampleapp.service.echo.EchoService;
import at.fhtw.sampleapp.service.weather.WeatherService;
import at.fhtw.game_server.service.users.UserService;
import at.fhtw.game_server.service.sessions.SessionService;
import at.fhtw.game_server.service.transactions.TransactionService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(10001, configureRouter());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Router configureRouter()
    {
        Router router = new Router();
        // router.addService("/weather", new WeatherService());
        // router.addService("/echo", new EchoService());
        router.addService("/users", new UserService());
        router.addService("/session", new SessionService());
        router.addService("/package", new PackageService());
        router.addService("/transactions", new TransactionService());
        router.addService("/cards", new CardsService());
        return router;
    }
}
