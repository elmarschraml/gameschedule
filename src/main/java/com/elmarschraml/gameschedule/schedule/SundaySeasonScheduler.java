package com.elmarschraml.gameschedule.schedule;

import com.elmarschraml.gameschedule.data.Match;
import com.elmarschraml.gameschedule.data.Season;
import com.elmarschraml.gameschedule.data.Team;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * matches will happen each sunday
 * can schedule a maximum of 1000 teams and a 30 year long season
 * enddate is NOT considered a valid day for a match - that's when the championship celebration happens :-)
 *
 * @See com.elmarschraml.gameschedule.schedule.SeasonScheduler
 */

public class SundaySeasonScheduler implements SeasonScheduler {

    public static final int MAX_NR_OF_TEAMS = 1000;
    public static final int MAX_DAYS_OF_SEASON = 10850;

    /**
     * A Schedule where each team will play each other team twice - first as home, then as away team
     * Matches will happen every sunday from the start of the season - with no gap between first and second round
     * Teams with a name that is blank will be ignored
     */
    @Override
    public Season scheduleMatchesForAllTeams(Season season) {
        validateInput(season);
        cleanupTeams(season);
        List<Match> firstRoundPairings = createFirstRound(season);
        List<Match> secondRoundPairings = createSecondRoundFromFirst(firstRoundPairings);
        List<LocalDate> allDates = getAllPossibleMatchDates(season.getStartDate(), season.getEndDate());
        int nrOfPairings = firstRoundPairings.size() + secondRoundPairings.size();
        if (nrOfPairings > allDates.size()) {
            throw new IllegalStateException("not enough Sundays in the season to fit all games - got "+ nrOfPairings +
                    " games, but only " + allDates.size() + " sundays");
        }
        List<Match> scheduledMatches = scheduleMatches(firstRoundPairings, secondRoundPairings, allDates);
        season.setMatches(scheduledMatches);
        return season;
    }

    private void validateInput(Season season) {
        if (season.getEndDate() == null || season.getStartDate() == null) {
            throw new IllegalArgumentException(("can only schedule seasons that have a start- and an enddate"));
        }

        if (season.getEndDate().isBefore(season.getStartDate())) {
            throw new IllegalArgumentException("start date has to be before end date");
        }

        if (season.getTeams() == null || season.getTeams().size() < 2) {
            throw new IllegalArgumentException("makes no sense to schedule a season with fewer than 2 teams");
        }

        if (season.getTeams().size() > MAX_NR_OF_TEAMS) {
            throw new IllegalArgumentException("too many teams - a maximum of " + MAX_NR_OF_TEAMS + " is supported");
        }

        Period seasonLength = Period.between(season.getStartDate(), season.getEndDate());
        if (seasonLength.getDays() > MAX_DAYS_OF_SEASON) {
            throw new IllegalArgumentException("season is too long - can schedule a maximum of " + MAX_DAYS_OF_SEASON + " days");
        }
    }

    protected List<Match> scheduleMatches(List<Match> firstRoundPairings, List<Match> secondRoundPairings,List<LocalDate> allMatchDates ) {
        if ( (firstRoundPairings.size()  + secondRoundPairings.size()) > allMatchDates.size()) {
            throw new IllegalStateException("season is too short - cannot find match dates for all necessary matches");
        }
        //we dont treat first and second round matches differently, so just combine to one list
        firstRoundPairings.addAll(secondRoundPairings);

        for (int pairingIdx = 0; pairingIdx < firstRoundPairings.size(); pairingIdx++) {
            firstRoundPairings.get(pairingIdx).setDate(allMatchDates.get(pairingIdx));
        }
        return firstRoundPairings;
    }

     List<LocalDate> getAllPossibleMatchDates(LocalDate startDate, LocalDate endDate) {
        LocalDate curDate = startDate;
        List<LocalDate> matchDates = new ArrayList<>();
        while (curDate.isBefore(endDate)) {
            if (curDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                matchDates.add(curDate);
            }
            curDate = curDate.plusDays(1);
        }
        return matchDates;
    }

    void cleanupTeams(Season season) {
        Set<Team> cleanTeams = new HashSet<>();
        for (Team curTeam : season.getTeams()) {
            if (! (curTeam == null || curTeam.getName() == null || curTeam.getName().isBlank())) {
                cleanTeams.add(curTeam);
            }
        }
        season.setTeams(cleanTeams);
    }


    protected List<Match> createFirstRound(Season season) {
        List<Team> teams = new ArrayList<>(season.getTeams());
        List<Match> pairings = new ArrayList<>();
        while (teams.size() > 1) {
            Team curTeam = teams.get(0);
            teams.remove(curTeam);
            teams.forEach(opposingTeam -> {
                pairings.add(new Match(curTeam, opposingTeam));
            });
        }
        return pairings;
    }

    protected List<Match> createSecondRoundFromFirst(List<Match> firstRoundMatches) {
        List<Match> secondRoundMatches = new ArrayList<>();
        firstRoundMatches.forEach( match ->
           secondRoundMatches.add(new Match(match.getAwayTeam(), match.getHomeTeam()))
        );
        return secondRoundMatches;
    }
}
