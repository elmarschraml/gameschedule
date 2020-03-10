package com.elmarschraml.gameschedule;

import com.elmarschraml.gameschedule.data.Season;
import com.elmarschraml.gameschedule.export.SeasonExporter;
import com.elmarschraml.gameschedule.read.SeasonReader;
import com.elmarschraml.gameschedule.schedule.SeasonScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GamescheduleMain implements CommandLineRunner {

    Logger log = LoggerFactory.getLogger(GamescheduleMain.class);

    @Autowired
    SeasonReader reader;

    @Autowired
    SeasonScheduler scheduler;

    @Autowired
    SeasonExporter exporter;

    @Autowired
    private GamescheduleProperties gamescheduleProperties;


    @Override
    public void run(String... args) throws Exception {
        runScheduling();
    }

    public void runScheduling() {
        log.info("SPIELPLAN: Reading input file " + gamescheduleProperties.getImportFilename() + "." +
                gamescheduleProperties.getImportFileextension() +
                " from directory " + gamescheduleProperties.getImportFolder());
        Season inputSeason = null;
        try {
            inputSeason = reader.readSeason();
        } catch (IOException | IllegalArgumentException e) {
            log.error("SPIELPLAN: Could not read input file, exiting....reason was: " + e);
            System.exit(1);
        }
        log.info("SPIELPLAN: Input file was read, now creating schedule...");

        Season scheduledSeason = null;
        try {
            scheduledSeason = scheduler.scheduleMatchesForAllTeams(inputSeason);
            log.info("SPIELPLAN: Schedule was created, now writing to file...");
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error("Could not create schedule, exiting...reason was: " + e);
            System.exit(1);
        }

        try {
            exporter.exportSeason(scheduledSeason);
        } catch (IOException e) {
            log.error("SPIELPLAN: Could not write output file to directory " + gamescheduleProperties.getDestinationFolder());
            System.exit(1);
        }

        log.info("SPIELPLAN: Done! Find your result in directory" + gamescheduleProperties.getDestinationFolder() );
    }

}
