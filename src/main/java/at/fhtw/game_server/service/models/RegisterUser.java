package at.fhtw.game_server.service.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public class RegisterUser {

    @JsonAlias({"username"})
    private String username;

    @JsonAlias({"password"})
    private String passwort;

    @JsonAlias({"coins"})
    private int coins;

    @JsonAlias({"elo"})
    private int elo;

    @JsonAlias({"token"})
    private String token;

    public RegisterUser(){}

    public RegisterUser(String username, String passwort){
        this.username = username;
        this.passwort = passwort;
    }

    public RegisterUser(String username, String passwort, int coins, int elo, String token){
        this.username = username;
        this.passwort = passwort;
        this.coins = coins;
        this.elo = elo;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
