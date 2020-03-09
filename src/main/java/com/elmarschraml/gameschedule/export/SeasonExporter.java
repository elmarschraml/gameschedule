package com.elmarschraml.gameschedule.export;

import com.elmarschraml.gameschedule.data.Season;

import java.io.IOException;

/**
 * Exports the data for a season, with target and format up to the implementation.
 */
public interface SeasonExporter {

    /**
     * @param season an instance of season with valid data. Missing data will be ignored.
     * @throws IOException if the export fails due to technical reasons
     * @throws IllegalArgumentException on invalid input that prevents the export from running
     */
    public void exportSeason(Season season) throws IOException;
}
