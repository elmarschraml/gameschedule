package com.elmarschraml.gameschedule.export;

import com.elmarschraml.gameschedule.GamescheduleProperties;
import com.elmarschraml.gameschedule.data.Match;
import com.elmarschraml.gameschedule.data.Season;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class SeasonFileExporter implements SeasonExporter {

    private static final String encoding = "UTF-8"; //get from application properties if needs to be configurable
    private static final String timestampPattern = "";

    @Autowired
    private GamescheduleProperties gamescheduleProperties;

    public void setGamescheduleProperties(GamescheduleProperties gamescheduleProperties) {
        this.gamescheduleProperties = gamescheduleProperties;
    }

    @Override
    public void exportSeason(Season season) throws IOException {
        if (season.getMatches() == null) {
            throw new IllegalArgumentException("cannot export a season with no matches");
        }

        writeToFile(createOutputLines(season));
    }

    List<String> createOutputLines(Season season) {
        List<String> outputLines = new ArrayList<>();
        season.getMatches().forEach(match -> {
            outputLines.add(printMatch(match));
        });
        return outputLines;
    }

    void writeToFile(List<String> outputLines) throws IOException {
        File outputFile = new File(getFilePathAndName());
        FileUtils.writeLines(outputFile,encoding, outputLines);
    }

    private String getFilePathAndName() {
        StringBuilder pathToFile = new StringBuilder();
        if (!gamescheduleProperties.getDestinationFolder().isBlank()) {
            pathToFile.append(gamescheduleProperties.getDestinationFolder());
            pathToFile.append("/");
        }
        pathToFile.append(gamescheduleProperties.getDestinationFilename());
        DateTimeFormatter timestampFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        pathToFile.append(timestampFormat.format(LocalDateTime.now()));
        pathToFile.append(".");
        pathToFile.append(gamescheduleProperties.getDestinationFileextension());
        return pathToFile.toString();
    }

    private String printMatch(Match match) {
        StringBuilder output = new StringBuilder();
        //will throw IllegalArgumentException on wrong pattern syntax - which is an illegal input, so re-throw according to our own javadoc
        //passing a formatter, instead of re-creating it, would increase performance, but handled internally here for the sake of a clean API
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(gamescheduleProperties.getDateFormatString(), Locale.GERMANY);
        output.append(dateFormatter.format(match.getDate()));
        output.append(" ");
        output.append(gamescheduleProperties.getDateFromMatchSeparator());
        output.append(" ");
        output.append(match.getHomeTeam().getName());
        output.append(" ");
        output.append(gamescheduleProperties.getTeamsOfMatchSeparator());
        output.append(" ");
        output.append(match.getAwayTeam().getName());
        return output.toString();
    }
}
