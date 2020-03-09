package com.elmarschraml.gameschedule.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a season of a league.
 * Leagues and seasons currently have no metadata, i.e. are represented by an arbitrary String
 */
public class Season {
    List<Team> teams = new ArrayList<>();
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

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
