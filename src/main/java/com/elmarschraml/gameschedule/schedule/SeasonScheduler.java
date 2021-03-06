package com.elmarschraml.gameschedule.schedule;

import com.elmarschraml.gameschedule.data.Season;

/**
 * Contains the scheduling logic for seasons
 */
public interface SeasonScheduler {
    /**
     * Adds a list of matches to be played to a season
     * @param season a season that has a start- and enddate, and a list of 2+ teams. Existing scheduled matches will be untouched and ignored.
     * @return a copy of the input season that has a list of scheduled matches.
     * @throws IllegalArgumentException on invalid input (no or only 1 team, no start- or enddate, or enddate before startdate)
     * @throws IllegalStateException if no complete schedule is possible within the given timeframe
     */
    Season scheduleMatchesForAllTeams(Season season);
}
