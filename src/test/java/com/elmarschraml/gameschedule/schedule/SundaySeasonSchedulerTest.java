package com.elmarschraml.gameschedule.schedule;

import com.elmarschraml.gameschedule.data.Match;
import com.elmarschraml.gameschedule.data.Season;
import com.elmarschraml.gameschedule.data.Team;
import com.elmarschraml.gameschedule.export.SeasonFileExporter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SundaySeasonSchedulerTest {

    protected SundaySeasonScheduler scheduler;
    protected Season baseSeason;

    private static final String seasonName = "aSeason";
    private static final String leagueName = "aLeague";
    protected static final Team team1 = new Team("Foos");
    protected static final Team team2 = new Team("Bars");
    protected static final Team team3 = new Team("Bazzes");
    protected static final Team team4 = new Team("The Runtime Exceptions");
    private static final Team team5 = new Team("the Team with a very, very, very, very, very, very, very, very, very, very, very, very, very, very, very long name");



    protected void setBaseData(Season season) {
        season.setSeasonName(seasonName);
        season.setLeagueName(leagueName);
        season.setStartDate(LocalDate.of(2015, Month.JANUARY,1));
        season.setEndDate(LocalDate.of(2015, Month.DECEMBER,31));
    }

    @BeforeEach
    public void reset() {
        baseSeason = new Season();
        setBaseData(baseSeason);
        scheduler = new SundaySeasonScheduler();
    }

    @Test
    public void testExceptionOnNullTeams() {
        baseSeason.setTeams(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            scheduler.scheduleMatchesForAllTeams(baseSeason);
        });
    }

    @Test
    public void testExceptionOnTooFewTeams() {
        Team oneTeam = new Team("just a lonely team");
        Set<Team> tooFewTeams = new HashSet<>();
        tooFewTeams.add(oneTeam);
        baseSeason.setTeams(tooFewTeams);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            scheduler.scheduleMatchesForAllTeams(baseSeason);
        });
    }

    @Test
    public void testMatchDatesInMonthAllSundays() {
        LocalDate firstOfMonth = LocalDate.of(2020,Month.MARCH,1);
        LocalDate endOfMonth = LocalDate.of(2020,Month.MARCH,31);
        List<LocalDate> sundaysOfMarch2020 = new ArrayList<>();
        sundaysOfMarch2020.add(LocalDate.of(2020,Month.MARCH,1));
        sundaysOfMarch2020.add(LocalDate.of(2020,Month.MARCH,8));
        sundaysOfMarch2020.add(LocalDate.of(2020,Month.MARCH,15));
        sundaysOfMarch2020.add(LocalDate.of(2020,Month.MARCH,22));
        sundaysOfMarch2020.add(LocalDate.of(2020,Month.MARCH,29));
        List<LocalDate> matchDates = scheduler.getAllPossibleMatchDates(firstOfMonth, endOfMonth);
        assertTrue(matchDates.size() == sundaysOfMarch2020.size(), "got the wrong number of Sundays");
        for (LocalDate curSunday : sundaysOfMarch2020) {
            assertTrue(matchDates.contains(curSunday),"did not correctly determine a sunday");
        }
    }

    @Test
    public void dontCountLastDayAsMatchDay() {
        LocalDate firstOfMonth = LocalDate.of(2020,Month.FEBRUARY,1);
        LocalDate endOfSeason = LocalDate.of(2020,Month.MARCH,29);
        List<LocalDate> matchDays = new ArrayList<>();
        matchDays.add(LocalDate.of(2020,Month.FEBRUARY,2));
        matchDays.add(LocalDate.of(2020,Month.FEBRUARY,9));
        matchDays.add(LocalDate.of(2020,Month.FEBRUARY,16));
        matchDays.add(LocalDate.of(2020,Month.FEBRUARY,23));
        matchDays.add(LocalDate.of(2020,Month.MARCH,1));
        matchDays.add(LocalDate.of(2020,Month.MARCH,8));
        matchDays.add(LocalDate.of(2020,Month.MARCH,15));
        matchDays.add(LocalDate.of(2020,Month.MARCH,22));
        //March 29th is a sunday, but last day of season, so NOT a match day
        List<LocalDate> matchDates = scheduler.getAllPossibleMatchDates(firstOfMonth, endOfSeason);
        assertTrue(matchDates.size() == matchDays.size(), "got the wrong number of Sundays");
        for (LocalDate curSunday : matchDays) {
            assertTrue(matchDates.contains(curSunday),"did not correctly determine a sunday");
        }
    }

    @Test
    public void testMinimalTeams() {
        Set<Team> smallTeamSet = new HashSet<>();
        smallTeamSet.add(team1);
        smallTeamSet.add(team2);
        baseSeason.setTeams(smallTeamSet);
        scheduler.scheduleMatchesForAllTeams(baseSeason);
        assertTrue((baseSeason.getMatches().size() == 2), "two teams should have 2 matches (first and second round)");
        assertTrue(baseSeason.getMatches().get(0).getAwayTeam().equals(baseSeason.getMatches().get(1).getHomeTeam()),"each team pairing should alternate home and away teams");
    }

    @Test
    public void testNrOfMatches() {
        Set<Team> smallTeamSet = new HashSet<>();
        smallTeamSet.add(team1);
        smallTeamSet.add(team2);
        smallTeamSet.add(team3);
        baseSeason.setTeams(smallTeamSet);
        scheduler.scheduleMatchesForAllTeams(baseSeason);
        assertTrue((baseSeason.getMatches().size() == 6), "three teams should have 6 matches (first and second round)");
    }

    @Test
    public void testCleanupNullTeams() {
        Set<Team> teamSetWithNulls = new HashSet<>();
        teamSetWithNulls.add(null);
        teamSetWithNulls.add(team1);
        teamSetWithNulls.add(team2);
        teamSetWithNulls.add(null);
        teamSetWithNulls.add(new Team(null));
        baseSeason.setTeams(teamSetWithNulls);
        scheduler.cleanupTeams(baseSeason);
        assertEquals(baseSeason.getTeams().size(), 2, "null teams should have been removed");
    }

    @Test
    public void testCleanupBlankTeams() {
        Set<Team> teamSetWithBlanks = new HashSet<>();
        teamSetWithBlanks.add(new Team(" "));
        teamSetWithBlanks.add(team1);
        teamSetWithBlanks.add(team2);
        teamSetWithBlanks.add(new Team(""));
        baseSeason.setTeams(teamSetWithBlanks);
        scheduler.cleanupTeams(baseSeason);
        assertEquals(baseSeason.getTeams().size(), 2, "null teams should have been removed");
    }

    @Test
    public void testSwitchHomeAndAwayTeamsBetweenRounds() {
        Set<Team> smallTeamSet = new HashSet<>();
        smallTeamSet.add(team1);
        smallTeamSet.add(team2);
        baseSeason.setTeams(smallTeamSet);
        scheduler.scheduleMatchesForAllTeams(baseSeason);
        //two teams -> just one match per round -> home and away should be switched between the two matches
        Match firstMatch = baseSeason.getMatches().get(0);
        Match secondMatch = baseSeason.getMatches().get(1);
        assertEquals(firstMatch.getHomeTeam(),secondMatch.getAwayTeam(),"home and away teams of a team pairing should change between first and second round");


    }

}
