package com.elmarschraml.gameschedule.schedule;

import com.elmarschraml.gameschedule.data.Match;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Variant of the regular SundaySeasonScheduler with 2 differences:
 * - Matches of the second run of pairings will be scheduled as LATE in the season as possible (i.e. leaving a break in the middle of the season)
 * - Matches will be randomized (instead of all games of a team following each other)
 *
 * @See com.elmarschraml.gameschedule.schedule.SeasonScheduler
 */
@Component
public class EvenSundaySeasonScheduler extends SundaySeasonScheduler {

    protected List<Match> scheduleMatches(List<Match> firstRoundPairings, List<Match> secondRoundPairings,List<LocalDate> allMatchDates) {
        int nrOfGamesPerRound = firstRoundPairings.size();
        int nrOfGames = nrOfGamesPerRound * 2;
        int nrOfDates = allMatchDates.size();
        if (  nrOfGames > nrOfDates) {
            throw new IllegalStateException("season is too short - cannot find match dates for all necessary matches");
        }
        int nrOfMatchDatesToSkip = nrOfDates - nrOfGames;

        Collections.shuffle(firstRoundPairings);
        for (int pairingIdx = 0; pairingIdx < firstRoundPairings.size(); pairingIdx++) {
            firstRoundPairings.get(pairingIdx).setDate(allMatchDates.get(pairingIdx));
        }

        int startIdxOfSecondRound = nrOfGamesPerRound + nrOfMatchDatesToSkip;

        Collections.shuffle(secondRoundPairings);
        for (int pairingIdx = 0; pairingIdx < secondRoundPairings.size(); pairingIdx++) {
            secondRoundPairings.get(pairingIdx).setDate(allMatchDates.get(pairingIdx + startIdxOfSecondRound));
        }


        List<Match> allMatches = new ArrayList<>();
        allMatches.addAll(firstRoundPairings);
        allMatches.addAll(secondRoundPairings);
        return allMatches;


    }
}
