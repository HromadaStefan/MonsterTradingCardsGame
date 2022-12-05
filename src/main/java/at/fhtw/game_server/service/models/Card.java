package at.fhtw.game_server.service.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Card {

    @JsonAlias ({"Id"})
    private String id;

    @JsonAlias ({"Name"})
    private String name;

    @JsonAlias ({"Damage"})
    private int damage;

    @JsonAlias({"Element"})
    private String element;

    @JsonAlias({"Type"})
    private String type;

    public Card(){};

    public Card(String id, String name, int damage, String element, String type){
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.element = element;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
