package com.elmarschraml.gameschedule.schedule;

import com.elmarschraml.gameschedule.data.Match;
import com.elmarschraml.gameschedule.data.Season;
import com.elmarschraml.gameschedule.data.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EvenSundaySeasonSchedulerTest extends SundaySeasonSchedulerTest{

    @BeforeEach
    public void reset() {
        baseSeason = new Season();
        setBaseData(baseSeason);
        scheduler = new EvenSundaySeasonScheduler();
    }


    @Test
    public void testIsGapBetweenRounds() {
        //2 teams, so 1 match per round
        Set<Team> smallTeamSet = new HashSet<>();
        smallTeamSet.add(team1);
        smallTeamSet.add(team2);
        baseSeason.setTeams(smallTeamSet);
        scheduler.scheduleMatchesForAllTeams(baseSeason);
        List<LocalDate> gameDays = scheduler.getAllPossibleMatchDates(baseSeason.getStartDate(), baseSeason.getEndDate());
        List<Match> matches = baseSeason.getMatches();
        matches.sort( (m1,m2) -> {
            if (m1.getDate().isBefore(m2.getDate())) {
                return -1;
            }
            if (m1.getDate().equals(m2.getDate())) {
                return 0;
            }
            return 1;
        });
        LocalDate dateOfSecondMatch = matches.get(1).getDate();
        LocalDate secondGameDay = gameDays.get(1);
        //plenty of dates in the season, so the second round match should not immediately follow the first round
        assertFalse(dateOfSecondMatch.equals(secondGameDay), "no break between first and second round");

    }

    @Test
    public void testSecondRoundEndsAtEndOfSeason() {
        //2 teams, so 1 match per round
        Set<Team> smallTeamSet = new HashSet<>();
        smallTeamSet.add(team1);
        smallTeamSet.add(team2);
        baseSeason.setTeams(smallTeamSet);
        scheduler.scheduleMatchesForAllTeams(baseSeason);
        List<LocalDate> gameDays = scheduler.getAllPossibleMatchDates(baseSeason.getStartDate(), baseSeason.getEndDate());
        List<Match> matches = baseSeason.getMatches();
        matches.sort( (m1,m2) -> {
            if (m1.getDate().isBefore(m2.getDate())) {
                return -1;
            }
            if (m1.getDate().equals(m2.getDate())) {
                return 0;
            }
            return 1;
        });
        LocalDate dateOfLastMatch = matches.get(matches.size()-1).getDate();
        LocalDate lastGameDay = gameDays.get(gameDays.size() -1);
        assertTrue(dateOfLastMatch.equals(lastGameDay), "games of second round should end on the last possible game day");

    }

    @Test
    public void testNoGapsWithinRound() {
        Set<Team> smallTeamSet = new HashSet<>();
        smallTeamSet.add(team1);
        smallTeamSet.add(team2);
        smallTeamSet.add(team3);
        baseSeason.setTeams(smallTeamSet);
        scheduler.scheduleMatchesForAllTeams(baseSeason);
        List<LocalDate> gameDays = scheduler.getAllPossibleMatchDates(baseSeason.getStartDate(), baseSeason.getEndDate());
        List<Match> matches = baseSeason.getMatches();
        matches.sort( (m1,m2) -> {
            if (m1.getDate().isBefore(m2.getDate())) {
                return -1;
            }
            if (m1.getDate().equals(m2.getDate())) {
                return 0;
            }
            return 1;
        });
        int nrOfGamesInFirstRound = 3;
        for (int idx = 0; idx < nrOfGamesInFirstRound; idx++) {
            LocalDate nextGameDay = gameDays.get(idx);
            LocalDate nextMatch = matches.get(idx).getDate();
            assertTrue(nextMatch.equals(nextGameDay),"there should be no gaps between matches");
        }

    }


    //TODO test


}
