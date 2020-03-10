package com.elmarschraml.gameschedule.data;

import java.time.LocalDate;

/**
 * Represents a single match occuring in a season
 * Does not validate if a match makes sense - you could e.g. set home team and away team to the same team
 * So please use with a well-implemented season scheduler
 */
public class Match {
    private LocalDate date;
    private Team homeTeam;
    private Team awayTeam;

    public Match(LocalDate date, Team homeTeam, Team awayTeam) {
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    /**
     * creates a match with no date set yet
     * so invalid as a scheduled match, but might be useful for scheduling purposes
     * @param homeTeam any Team
     * @param awayTeam any Team
     */
    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }
}
