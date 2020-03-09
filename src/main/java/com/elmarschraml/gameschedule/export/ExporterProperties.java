package com.elmarschraml.gameschedule.export;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="export")
public class ExporterProperties {
    private String dateFromMatchSeparator;
    private String teamsOfMatchSeparator;
    private String dateFormatString;
    private String destinationFolder;
    private String destinationFilename;
    private String destinationFileextension;


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
}
