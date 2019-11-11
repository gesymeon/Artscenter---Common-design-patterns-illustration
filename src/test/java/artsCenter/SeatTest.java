package artsCenter;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SeatTest {

	static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	Cinema cinema;
	String date;
	Calendar c;

	@Before
	public void setUp() throws Exception {
		cinema = new Cinema("test_cinema", 30);
		c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		date = sdf.format(c.getTime());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSeatUniquenessAcrossScheduleEntries() {

		Seat s1 = cinema.getSchedule(date).getEntries().get(0).getRows().get(0).getSeat(1);
		Seat s2 = cinema.getSchedule(date).getEntries().get(1).getRows().get(0).getSeat(1);

		assertTrue("Error, corresponding seats within different entries in the same schedule have different ids",
				s1.getID() == s2.getID());

	}

	@Test
	public void testSeatUniquenessAcrossSchedules() {
		c.add(Calendar.DATE, 1);
		String date_after_tomorrow = sdf.format(c.getTime());
		Seat s1 = cinema.getSchedule(date).getEntries().get(0).getRows().get(0).getSeat(1);
		Seat s2 = cinema.getSchedule(date_after_tomorrow).getEntries().get(0).getRows().get(0).getSeat(1);
		assertTrue(
				"Error, corresponding seats within same/different(proved in previous test) entries in different schedules have different ids",
				s1.getID() == s2.getID());
	}

}
