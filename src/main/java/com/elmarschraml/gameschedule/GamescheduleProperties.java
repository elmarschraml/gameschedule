package com.elmarschraml.gameschedule;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="gameschedule")
public class GamescheduleProperties {
    private String dateFromMatchSeparator;
    private String teamsOfMatchSeparator;
    private String dateFormatString;
    private String destinationFolder;
    private String destinationFilename;
    private String destinationFileextension;
    private String importFolder;
    private String importFilename;
    private String importFileextension;


    public String getDateFromMatchSeparator() {
        return dateFromMatchSeparator;
    }

    public void setDateFromMatchSeparator(String dateFromMatchSeparator) {
        this.dateFromMatchSeparator = dateFromMatchSeparator;
    }

    public String getTeamsOfMatchSeparator() {
        return teamsOfMatchSeparator;
    }

    public void setTeamsOfMatchSeparator(String teamsOfMatchSeparator) {
        this.teamsOfMatchSeparator = teamsOfMatchSeparator;
    }

    public String getDateFormatString() {
        return dateFormatString;
    }

    public void setDateFormatString(String dateFormatString) {
        this.dateFormatString = dateFormatString;
    }


    public String getDestinationFolder() {
        return destinationFolder;
    }

    public void setDestinationFolder(String destinationFolder) {
        this.destinationFolder = destinationFolder;
    }

    public String getDestinationFilename() {
        return destinationFilename;
    }

    public void setDestinationFilename(String destinationFilename) {
        this.destinationFilename = destinationFilename;
    }

    public String getDestinationFileextension() {
        return destinationFileextension;
    }

    public void setDestinationFileextension(String destinationFileextension) {
        this.destinationFileextension = destinationFileextension;
    }

    public String getImportFolder() {
        return importFolder;
    }

    public void setImportFolder(String importFolder) {
        this.importFolder = importFolder;
    }

    public String getImportFilename() {
        return importFilename;
    }

    public void setImportFilename(String importFilename) {
        this.importFilename = importFilename;
    }

    public String getImportFileextension() {
        return importFileextension;
    }

    public void setImportFileextension(String importFileextension) {
        this.importFileextension = importFileextension;
    }
}
