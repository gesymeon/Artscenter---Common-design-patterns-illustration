package artsCenter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RoomTest {

	Cinema cinema;
	Movie movie;
	Theater theater;
	Play play;

	static final List<String> actors = new ArrayList<>(
			Arrays.asList("famous actor 1", "famous actor 2", "famous actor3", "last famous actor"));
	static final String director = "kubrik";
	static final String description = "good show";
	static final String filmName = "Spartacus";
	static final String playName = "Gomorrah";
	static final double defaultDuration = 2.0;
	static final String defaultDate = "09/11/2019";
	static final PlayType defaultPlayGenre = PlayType.Comedy;

	@Before
	public void setUp() throws Exception {
		cinema = new Cinema("test_cinema", 30);
		movie = new Movie(filmName, description, actors, director, defaultDuration);
		theater = new Theater("test_theater", 45);
		play = new Play(playName, description, actors, director, defaultPlayGenre, defaultDuration);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMakeLuxurious() {
		cinema.addShow(movie, defaultDate);
		cinema.makeLuxurious(1, 1);
		assertTrue(cinema.rows.get(0).getSeat(1).isLuxurious());
		for (Map.Entry<String, Schedule> entry : cinema.getSchedules().entrySet()) {
			Schedule schedule = entry.getValue();
			for (ScheduleEntry e : schedule.getEntries()) {
				assertTrue(e.getRows().get(0).getSeat(1).isLuxurious());
			}
		}

	}

}
