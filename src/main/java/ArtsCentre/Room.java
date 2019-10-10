
package ArtsCentre;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author George Simeonidis
 */

//Theatres and cinemas are not allowed to have the same name

public abstract class Room implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String name;
	protected int capacity;
	protected ArrayList<Row> rows;
	protected RoomType type;

	// the key is a specific date, and its value is the corresponding date's
	// schedule.
	protected HashMap<String, Schedule> schedules;

	public Map<String, Schedule> getSchedules() {
		return schedules;
	}

	public RoomType getFacilityType() {
		return type;
	}

	public List<Row> getRows() {
		return rows;
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}

	/**
	 * Creates a facility with a specific name and capacity.
	 * 
	 * @param name facility's name.
	 * @param cap  facility's capacity.
	 * @throws java.text.ParseException
	 */
	public Room(String name, int cap) throws ParseException {
		this.name = name;
		capacity = cap;
		rows = new ArrayList<>();
		schedules = new HashMap<>();
		initializeSchedules();
		initializeRows(cap);
		updateRows();
	}

	@Override
	public String toString() {
		String text;
		text = "Facility name : " + getName() + "<br>" + "<br>" + "<br>" + "Capacity : " + getCapacity() + "<br>"
				+ "<br>" + "<br>" + "<br>";
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacity;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Room)) {
			return false;
		}
		Room other = (Room) obj;
		if (capacity != other.capacity) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/**
	 * adds a schedule for each of the ten following days (excluding today). By
	 * default the schedule starts from 12 a.m up until 12 p.m
	 */
	private void initializeSchedules() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date;
		Date today = new Date();
		String todayString = sdf.format(today);

		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(todayString));
		for (int i = 0; i < 10; i++) {
			c.add(Calendar.DATE, 1);
			date = sdf.format(c.getTime());
			schedules.put(date, new Schedule(12, 24));
		}
	}

	/**
	 * Initializes the facility with rows containing 10 plain seats, until the
	 * capacity is reached. Each row has an incremental number serving as its index
	 * within the facility.
	 * 
	 * @param cap the facility's capacity.
	 */
	private void initializeRows(int cap) {
		int ascenting = 1;
		Row temp;
		while (cap > 10) {
			temp = new PlainRow(10, ascenting++);
			cap -= 10;
			rows.add(temp);
		}
		if (cap > 0) {
			temp = new PlainRow(cap, ascenting);
			rows.add(temp);
		}
	}

	/**
	 * Updates all the entries in all schedules in order to contain the most recent
	 * structure of the facility regarding its rows and seats.
	 * 
	 */
	private void updateRows() {
		for (String date : schedules.keySet()) {
			Schedule temp = schedules.get(date);
			for (Entry entry : temp.getEntries()) {
				entry.setRows(rows);
			}
		}
	}

	/**
	 * Changes the quality of a seat in the facility to luxurious
	 * 
	 * @param row  the ascending number of the row where the seat is contained
	 * @param seat the ascending number of the seat inside the row
	 */
	public void makeLuxurious(int row, int seat) {

		// Ensures that new entries are initialized with the current row's seat quality.
		rows.get(row - 1).makeLuxurious(seat);

		// update the entries about the change in the seat's quality.
		for (String date : schedules.keySet()) {
			Schedule temp = schedules.get(date);
			for (Entry entry : temp.getEntries()) {
				entry.getRows().get(row - 1).makeLuxurious(seat);
			}

		}

	}

	/**
	 * Deletes the row indicated by the passed argument and reduces the following
	 * rows' indexes by one. The current entries on the facility's schedules are not
	 * affected by this change.
	 * 
	 * @param position The row's incremental number.
	 */
	public void deleteRow(int position) {
		if (position >= 1 && position <= rows.size()) {
			rows.remove(position - 1);
			for (int i = position - 1; i < rows.size(); i++) {
				rows.get(i).setIndex(rows.get(i).getIndex() - 1);
			}
		}

	}

	/**
	 * Returns the facility's schedule for a specific date.
	 * 
	 * @param date schedule's date.
	 * @return the schedule if it exists for the specified date, otherwise null.
	 */
	public Schedule getSchedule(String date) {

		if (schedules.keySet().contains(date))
			return schedules.get(date);
		else
			return null;

	}

	/**
	 * Adds a new schedule for a specific date.
	 * 
	 * @param date the schedule's date.
	 * @return true if the addition is completed correctly, false otherwise.
	 */
	public boolean addSchedule(String date) {
		Schedule temp = getSchedule(date);
		if (temp != null)
			return false;
		else {

			Schedule schedule = new Schedule(12, 24);
			// we update the new schedule's entries so that their rows correspond to the
			// facility's structure
			for (Entry e : schedule.getEntries()) {
				e.setRows(rows);
			}
			schedules.put(date, schedule);
			return true;
		}
	}

	/**
	 * Remove's a specific date's schedule.
	 * 
	 * @param date the date of the to be deleted schedule.
	 * @return false if the schedule does not exist, true otherwise.
	 */
	public boolean deleteSchedule(String date) {
		Schedule temp = getSchedule(date);
		if (temp == null)
			return false;
		else {
			schedules.remove(date);
			return true;
		}
	}

	/**
	 * Adds a row in the facility and properly updates all the schedule's to keep up
	 * with the new facility's structure.
	 * 
	 * @param capacity the number of seats of the new row
	 * @param lux      the new row's quality of seats
	 */
	public void addRow(int capacity, boolean lux) {

		if (lux)
			rows.add(new LuxuriousRow(capacity, rows.get(rows.size() - 1).getIndex() + 1));
		else
			rows.add(new PlainRow(capacity, rows.get(rows.size() - 1).getIndex() + 1));

		// toggle to add/not add the new row to the already existing entries of all the
		// current schedules.
		// updateRows();

	}

	/**
	 * Returns the row which has the index passed as the argument
	 * 
	 * @param position the row's index
	 * @return the row containing the incremental number passed as argument, null
	 *         otherwise
	 */
	public Row getRow(int position) {
		Row temp;
		Iterator<Row> it = rows.iterator();
		while (it.hasNext()) {
			temp = it.next();
			if (temp.getIndex() == position)
				return temp;
		}
		return null;
	}

	/**
	 * Adds a show on the facility's schedule for a specific date.
	 * 
	 * @param show the show to be added.
	 * @param date the date in which's schedule the show will be added.
	 * @return true if the addition is completed successfully, false otherwise.
	 */
	public boolean addShow(Show show, String date) {

		if (!checkCompatibility(show))
			return false;

		if (schedules.containsKey(date)) {
			Schedule temp = schedules.get(date);
			if (temp.addEntry(show))
				return true;
			else
				return false;
		} else {
			Schedule schedule = new Schedule(12, 24);

			for (Entry e : schedule.getEntries()) {
				e.setRows(rows);
			}
			schedule.addEntry(show);
			schedules.put(date, schedule);

		}
		return true;

	}

	/**
	 * deletes a show from a specific date's schedule.
	 * 
	 * @param name the name of the show to be deleted.
	 * @param date the date when the show is to be presented.
	 * @return true if the schedule contains the specified show, false otherwise.
	 */
	public boolean deleteShow(String name, String date) {
		Schedule temp = schedules.get(date);
		return temp.deleteEntry(name);
	}

	/**
	 * Moves a show in a specific date's schedule by a specified number of
	 * positions.
	 * 
	 * @param name   the name of the show to be moved.
	 * @param date   the date in which's schedule the show is included.
	 * @param offset the number of positions the show is to be moved within the
	 *               specified date's schedule.
	 * @return true if the schedule contains the show, false otherwise.
	 */
	public boolean moveShow(String name, String date, int offset) {
		Schedule temp = schedules.get(date);
		return temp.moveEntry(name, offset);
	}

	/**
	 * Swaps two shows in a specified date's schedule.
	 * 
	 * @param name1 the name of the first show to be swapped.
	 * @param name2 the name of the second show to be swapped.
	 * @param date  the date in which's schedule the shows will be swapped.
	 * @return true if the swapping is successful, false otherwise.
	 */
	public boolean swapShows(String name1, String name2, String date) {
		
		Schedule temp = schedules.get(date);
		return temp.swapEntries(name1, name2);
		
	}

	/**
	 * Returns the show named as the passed argument.
	 * 
	 * @param name the show's name.
	 * @return the show if it exists in a facility's schedule for some date, null
	 *         otherwise.
	 * 
	 */
	public Show getShow(String name) {
		/*
		 * Entry temp; for (String date : schedules.keySet()) { temp =
		 * schedules.get(date).Contains(name); if (temp != null) return temp.getShow();
		 * } return null;
		 */	
		for (Map.Entry<String, Schedule> entry : schedules.entrySet()) {
		    Schedule value = entry.getValue();
		    Entry temp = value.contains(name);
		    if(temp != null)
		    	return temp.getShow();     
		}
		return null;  
	}

	/**
	 * Restricts the inclusion of shows to facilities when their types are
	 * incompatible.
	 * 
	 * @return true if the inclusion is allowed, false otherwise.
	 */
	private boolean checkCompatibility(Show show) {
		if (show instanceof Movie && this instanceof Cinema || show instanceof Play && this instanceof Theater){ 
			return true;}
		 else
			{return false;}
	}
}
