package com.elmarschraml.gameschedule.read;

import com.elmarschraml.gameschedule.data.Season;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonSeasonReaderTest {
    private final String exampleJsonString = "{ " +
	"\"league\": \"Kreisliga Herren\","+
            "\"season\": \"2015/16\","+
           "\"start\": \"2015-11-01\","+
            "\"end\": \"2016-03-31\","+
            "\"teams\": ["+
                "{ \"name\": \"Hachinger Kickers\" },"+
                "{ \"name\": \"FC Miesbach\"},"+
                "{ \"name\": \"Jahn München\" },"+
                "{ \"name\": \"Weißblau Riem\" },"+
                "{ \"name\": \"SG Moosfeld\" }"+
	        "]"+
    "}";

	@Test
    public void testParsingJson() {
		JsonSeasonReader reader = new JsonSeasonReader();
		Season season = reader.parseJson(exampleJsonString);
		assertNotNull(season,"parse failure - parser returned null");
	}

	@Test
	public void testParsedDates() {
		JsonSeasonReader reader = new JsonSeasonReader();
		Season season = reader.parseJson(exampleJsonString);
		assertTrue(season.getStartDate() != null);
		assertTrue(season.getEndDate() != null);
	}

	@Test
	public void testParsedAttributes() {
		JsonSeasonReader reader = new JsonSeasonReader();
		Season season = reader.parseJson(exampleJsonString);
		assertNotNull(season.getLeagueName());
		assertFalse(season.getLeagueName().isBlank());
		assertNotNull(season.getSeasonName());
		assertFalse(season.getSeasonName().isBlank());
	}

	@Test
	public void testTeamsList() {
		JsonSeasonReader reader = new JsonSeasonReader();
		Season season = reader.parseJson(exampleJsonString);
		assertNotNull(season.getTeams());
		assertEquals(5, season.getTeams().size());

	}

}
