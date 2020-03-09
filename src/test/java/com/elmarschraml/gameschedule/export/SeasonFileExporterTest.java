package com.elmarschraml.gameschedule.export;

import com.elmarschraml.gameschedule.data.Match;
import com.elmarschraml.gameschedule.data.Season;
import com.elmarschraml.gameschedule.data.Team;

import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class SeasonFileExporterTest {

    private SeasonFileExporter exporter;
    private Season baseSeason;

    private static final String seasonName = "aSeason";
    private static final String leagueName = "aLeague";
    private static final Team team1 = new Team("Foos");
    private static final Team team2 = new Team("Bars");
    private static final Team team3 = new Team("Bazzes");
    private static final Team team4 = new Team("The Runtime Exceptions");
    LocalDate now;
    private static ExporterProperties properties;
    private static Team[] sampleTeams = {new Team("Foos"), new Team("Bars"), new Team("Bazzes")};
    private static final List<Team> teams = Arrays.asList(sampleTeams);

    @BeforeAll
    public static void setupProperties() {
        properties = new ExporterProperties();
        properties.setDateFromMatchSeparator("|");
        properties.setTeamsOfMatchSeparator("-");
        properties.setDateFormatString("EE dd.MM.yyyy");
        properties.setDestinationFolder("exportdir");
        properties.setDestinationFilename("spielplan");
        properties.setDestinationFileextension("txt");
    }

    @BeforeEach
    public void reset() {
        now = LocalDate.now();
        exporter = new SeasonFileExporter();
        exporter.setExporterProperties(properties);
        baseSeason = new Season();
        setBaseData(baseSeason);
    }

    private void setBaseData(Season season) {
        season.setSeasonName(seasonName);
        season.setLeagueName(leagueName);
        season.setStartDate(LocalDate.of(2015, Month.JANUARY,1));
        season.setStartDate(LocalDate.of(2015, Month.DECEMBER,31));
    }

    @Test
    public void skipIfMatchesEmpty() {
        List<String> outputLines = exporter.createOutputLines(baseSeason);
        Assertions.assertTrue(outputLines.isEmpty(), "a season with no matches should not lead to any output");
    }

    @Test
    public void throwExceptionOnInvalidInput() throws IOException {
        baseSeason.setMatches(null);
        assertThrows(IllegalArgumentException.class,
                ()->{
                    exporter.exportSeason(baseSeason);
                });
    }

    @Test
    public void exportSingleMatch() {
        baseSeason.getMatches().add(new Match(now,team1, team2));
        List<String> outputLines = exporter.createOutputLines(baseSeason);
        assertTrue(outputLines.size() == 1, "a single match should be exported as a single line");
        String outputLine = outputLines.get(0);
        validateMatchLineContent(outputLine, team1.getName(), team2.getName(), now);
    }

    @Test
    public void testCorrectNrOfLines() {
        baseSeason.getMatches().add(new Match(now,team3,team4));
        baseSeason.getMatches().add(new Match(now,team1,team2));
        baseSeason.getMatches().add(new Match(now,team1,team4));
        baseSeason.getMatches().add(new Match(now,team2,team4));
        List<String> outputLines = exporter.createOutputLines(baseSeason);
        assertTrue(outputLines.size() == 4, "export should have one line per match");
    }

    //not an actual unit test, since it has the side effect of actually writing to disk - run for manual checks
    @Test
    @Ignore
    public void actuallyWriteToFile()  {
        baseSeason.getMatches().add(new Match(now,team3,team4));
        baseSeason.getMatches().add(new Match(now,team1,team2));
        baseSeason.getMatches().add(new Match(now,team1,team4));
        baseSeason.getMatches().add(new Match(now,team2,team4));
        try {
            exporter.exportSeason(baseSeason);
        } catch (IOException e) {
            fail("Writing to file failed, reason: " + e);
        }
    }

    private void validateMatchLineContent(String outputLine, String homeTeamName, String awayTeamName, LocalDate date) {
        assertNotNull(outputLine, "export should not contain lines that are null");
        assertFalse(outputLine.isEmpty(), "export should not contain empty lines");
        assertTrue(outputLine.contains(homeTeamName), "a team seems to be missing in the output for a match");
        assertTrue(outputLine.contains(awayTeamName),"a team seems to be missing in the output for a match");
        String yearString = Integer.toString(date.getYear()); //most basic check - assumes year is always contained in date format
        assertTrue(outputLine.contains(yearString),"date seems not to appear in output for match");
        //intentionally does not check format, since that is dependant on configuration and implementation details
    }


}
