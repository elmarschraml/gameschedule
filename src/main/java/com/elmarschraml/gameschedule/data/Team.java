package com.elmarschraml.gameschedule.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Represents one of the teams playing in a league.
 * Currently no other data except the team's name, but class is provided for reasons of
 * explicit API and extensibility
 * Teams with the exact same name will be considered equal
 */

public class Team {
    String name;

    public Team(String name) {
        this.name = name;
    }

    public Team() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    /**
     * Teams are equal if they have the exact same name (incl. whitespace)
     */
    public boolean equals(Object obj) {
        return (obj instanceof Team ) && ((Team) obj).getName().equals(this.getName());
    }
}
