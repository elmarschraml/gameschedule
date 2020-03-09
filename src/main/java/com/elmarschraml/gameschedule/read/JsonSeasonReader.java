package com.elmarschraml.gameschedule.read;

import com.elmarschraml.gameschedule.GamescheduleProperties;
import com.elmarschraml.gameschedule.data.Season;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;


/**
 * reads the data of a season (without matches, since those will be created in-app) from json
 */
public class JsonSeasonReader implements SeasonReader {

    @Autowired
    private GamescheduleProperties gamescheduleProperties;

    @Override
    public Season readSeason() throws IOException, IllegalArgumentException {
        String json = readJsonFromFile();
        Season season = parseJson(json);
        if (season == null) {
            throw new IllegalArgumentException("Could not read a valid season");
        } else {
            return season;
        }
    }

    Season parseJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        Season season;
        try {
            season = objectMapper.readValue(json, Season.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Supplied json could not be parsed, reason: " + e);
        }
        return season;
    }

    private String readJsonFromFile() throws IOException{
        return FileUtils.readFileToString(new File(getFilePathAndName()),"ISO-8859-1");
    }

    private String getFilePathAndName() {
        StringBuilder pathToFile = new StringBuilder();
        if (!gamescheduleProperties.getImportFolder().isBlank()) {
            pathToFile.append(gamescheduleProperties.getImportFolder());
            pathToFile.append("/");
        }
        pathToFile.append(gamescheduleProperties.getImportFilename());
        pathToFile.append(".");
        pathToFile.append(gamescheduleProperties.getImportFileextension());
        return pathToFile.toString();
    }

    public void setGamescheduleProperties(GamescheduleProperties gamescheduleProperties) {
        this.gamescheduleProperties = gamescheduleProperties;
    }
}
