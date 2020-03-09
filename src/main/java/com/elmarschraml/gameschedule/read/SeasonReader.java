package com.elmarschraml.gameschedule.read;

import com.elmarschraml.gameschedule.data.Season;

import java.io.IOException;

public interface SeasonReader {
    /**
     * Reads and returns a Season object. The source and format to read is up to the implementation.
     * @return a season object
     * @throws IOException on technical errors, e.g. file access or network connection
     * @throws IllegalArgumentException on data errors, e.g. input format
     */
    Season readSeason() throws IOException, IllegalArgumentException;
}
