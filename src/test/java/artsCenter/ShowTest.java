package artsCenter;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShowTest {

	Movie movie, movie2, movie3, movie4, movie5, movie6;
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

		movie = new Movie(filmName, description, actors, director, defaultDuration);

		movie2 = new Movie(filmName, "different description", actors, director, defaultDuration);
		movie3 = new Movie(filmName, description, Arrays.asList("different actors"), director, defaultDuration);
		movie4 = new Movie(filmName, description, actors, "different director", defaultDuration);
		movie5 = new Movie(filmName, description, actors, director, 1.0);
		movie6 = new Movie("different file name", description, actors, director, 2.0);

		play = new Play(playName, description, actors, director, defaultPlayGenre, defaultDuration);
	}

	@After
	public void tearDown() throws Exception {
	}

	// verifies that the description and the actors of a show do not play a part on
	// defining its identity.
	@Test
	public void testShowEquality() {
		assertEquals(movie, movie2);
		assertEquals(movie, movie3);
		assertThat(movie, not(equalTo(play)));
		assertThat(movie, not(equalTo(movie4)));
		assertThat(movie, not(equalTo(movie5)));
		assertThat(movie, not(equalTo(movie6)));
	}

}
