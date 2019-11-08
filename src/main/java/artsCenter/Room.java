
package artsCenter;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class Room implements Serializable {

	private static final long serialVersionUID = -1708615501951868346L;
	protected String name;
	protected int capacity;
	protected List<Row> rows;

	// the key is a specific date, and its value is the corresponding date's
	// schedule.
	protected LinkedHashMap<String, Schedule> schedules;
	
	private List<RowsObserver> observers;


	/**
	 * Creates a room with a specific name and capacity.
	 * 
	 * @param name     room's name.
	 * @param capacity room's capacity.
	 * @throws java.text.ParseException
	 * @throws CloneNotSupportedException
	 */
	public Room(String name, int capacity) throws ParseException {
		
		this.name = name;
		this.capacity = capacity;
		observers = new ArrayList<>();
		rows = new ArrayList<>();
		schedules = new LinkedHashMap<>();
		initializeRows(capacity);
		initializeSchedules();
	}

	public Map<String, Schedule> getSchedules() {
		return schedules;
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

	@Override
	public String toString() {
		String text;
		text = "room name : " + getName() + "<br>" + "<br>" + "<br>" + "Capacity : " + getCapacity() + "<br>" + "<br>"
				+ "<br>" + "<br>";
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((getRoomType() == null) ? 0 : getRoomType().hashCode());
		return result;
	}

	// equality is based on room's name and type

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
		if (getRoomType() != other.getRoomType()) {
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
	 * 
	 * @throws CloneNotSupportedException
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
			Schedule schedule = new Schedule(12, 24, rows);
			schedule.getEntries().forEach(entry -> observers.add(entry));
			schedules.put(date, schedule);
		}
	}

	/**
	 * Initializes the room with rows containing 10 plain seats, until the capacity
	 * is reached. Each row has an incremental number serving as its index within
	 * the room.
	 * 
	 * @param cap the room's capacity.
	 */
	private void initializeRows(int cap) {
		int ascenting = 1;
		Row temp;
		while (cap > 0) {
			temp = new PlainRow(cap > 10 ? 10 : cap, ascenting++);
			cap -= 10;
			rows.add(temp);
		}
	}

	/**
	 * Changes the quality of a seat in the room to luxurious
	 * 
	 * @param row  the ascending number of the row where the seat is contained
	 * @param seat the ascending number of the seat inside the row
	 */
	public void makeLuxurious(int row, int seat) {

		// The new entries copy the structure from the room's rows arrayList so the code
		// below is needed!
		// Ensures that new schedule entries are initialized with the current row's seat
		// quality.
		rows.get(row - 1).makeLuxurious(seat);

		for (Map.Entry<String, Schedule> entry : schedules.entrySet()) {
			Schedule schedule = entry.getValue();
			for (ScheduleEntry e : schedule.getEntries()) {
				e.getRows().get(row - 1).makeLuxurious(seat);
			}
		}

	}

	/**
	 * Deletes the row indicated by position and reduces the following rows' indices
	 * by one. The entries already inserted on the room's schedules are not affected
	 * by this change.
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

		updateObservers();
		
	}

	/**
	 * Returns the room's schedule for a specific date.
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
	 * @throws CloneNotSupportedException
	 */
	public boolean addSchedule(String date) {
		Schedule temp = getSchedule(date);
		if (temp != null)
			return false;
		else {
			Schedule schedule = new Schedule(12, 24, rows);
			schedule.getEntries().forEach(entry -> observers.add(entry));
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
	
	
	private void updateObservers() {
		
		for(RowsObserver obs : observers)
			obs.updateRows(rows);
	}
	

	/**
	 * Adds a new row in the room.
	 * 
	 * @param capacity the number of seats of the new row
	 * @param lux      the new row's quality of seats
	 */
	public void addRow(int capacity, boolean lux) {

		if (lux)
			rows.add(new LuxuriousRow(capacity, rows.get(rows.size() - 1).getIndex() + 1));
		else
			rows.add(new PlainRow(capacity, rows.get(rows.size() - 1).getIndex() + 1));
		
		updateObservers();
		
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
	 * Adds a show on the room's schedule for a specific date.
	 * 
	 * @param show the show to be added.
	 * @param date the date in which's schedule the show will be added.
	 * @return true if the addition is completed successfully, false otherwise.
	 * @throws CloneNotSupportedException
	 */
	public boolean addShow(Show show, String date) {

		if (!checkCompatibility(show))
			return false;

		if (schedules.containsKey(date)) {
			Schedule temp = schedules.get(date);
			return temp.addEntry(show);
		} else {
			Schedule schedule = new Schedule(12, 24, rows);
			schedule.addEntry(show);
			schedule.getEntries().forEach(entry -> observers.add(entry));
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
	 * @return the show if it exists in a room's schedule for some date, null
	 *         otherwise.
	 * 
	 */
	public Show getShow(String name) {

		for (Map.Entry<String, Schedule> entry : schedules.entrySet()) {
			Schedule value = entry.getValue();
			ScheduleEntry temp = value.contains(name);
			if (temp != null)
				return temp.getShow();
		}
		return null;
	}

	// Probably better if a third class had been used to perform this and similar
	// checks.
	/**
	 * Restricts the inclusion of shows to facilities when their types are
	 * incompatible.
	 * 
	 * @return true if the inclusion is allowed, false otherwise.
	 */
	private boolean checkCompatibility(Show show) {
		return (show instanceof Movie && this instanceof Cinema || show instanceof Play && this instanceof Theater);

	}

	protected abstract RoomType getRoomType();
}
