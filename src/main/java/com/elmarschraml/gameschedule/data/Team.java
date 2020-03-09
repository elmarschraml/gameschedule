package com.elmarschraml.gameschedule.data;

/**
 * Represents one of the teams playing in a league.
 * Currently no other data except the team's name, but class is provided for reasons of
 * explicit API and extensibility
 */
public class Team {
    String name;

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
