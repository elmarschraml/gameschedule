package com.elmarschraml.gameschedule.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a season of a league.
 * Leagues and seasons currently have no metadata, i.e. are represented by an arbitrary String
 */
public class Season {
    Set<Team> teams = new HashSet<>();
    List<Match> matches = new ArrayList<>();
    private String leagueName;
    private String seasonName;
    private LocalDate startDate;
    private LocalDate endDate;

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public String getLeagueName() {
        return leagueName;
    }

    @JsonProperty("league")
    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getSeasonName() {
        return seasonName;
    }

    @JsonProperty("season")
    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    @JsonProperty("start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @JsonProperty("end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
}
