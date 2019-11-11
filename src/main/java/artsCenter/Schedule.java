
package artsCenter;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Schedule implements Serializable {

	private static final long serialVersionUID = -5537895265539461749L;
	// The list which contains the schedule's entries.
	private List<ScheduleEntry> entries;
	// The maximum number of shows that can be contained inside a schedule.
	private int maximumEntries;
	// The fixed duration of each show.
	private static final int INTERVAL = 3;
	// The time when the schedule for the day starts.
	private int openingTime;
	// The time when the schedule for the day ends.
	private int closingTime;

	public List<ScheduleEntry> getEntries() {
		return entries;
	}

	/**
	 * 
	 * Calculates the starting and closing time of each entry of the schedule based
	 * on the total time the room is operational and the number of shows to be
	 * performed within it each day.
	 * 
	 * @param time_start The start time of the room/schedule.
	 * @param time_close The closing time of the room/schedule.
	 */

	public Schedule(int timeStart, int timeClose, List<Row> rows) {
		openingTime = timeStart;
		closingTime = timeClose;

		calculateMaximumEntries(closingTime - openingTime);
		entries = new ArrayList<>();
		createEntries(rows);
	}

	public void createEntries(List<Row> rows) {
		int temp;
		for (int i = 0; i < maximumEntries; i++) {
			temp = openingTime + entries.size() * INTERVAL;
			ScheduleEntry entry = new ScheduleEntry(temp, temp + INTERVAL, rows);
			entries.add(entry);
		}
	}
	

	/**
	 * Calculates the total maximum number of entries within the schedule.
	 * 
	 * @param total the total time the schedule takes to be completed.
	 */
	private void calculateMaximumEntries(int total) {
		maximumEntries = total / INTERVAL;
	}

	/**
	 * Adds a new show to the schedule searching sequentially for an opening within
	 * it.
	 * 
	 * @param show the show to be added.
	 * @return true if an entry without a show was found, false otherwise.
	 */
	public boolean addEntry(Show show) {
		if (contains(show.getName()) == null) {
			Iterator<ScheduleEntry> it = entries.iterator();
			while (it.hasNext()) {
				ScheduleEntry temp = it.next();
				if (temp.getShow() == null) {
					temp.setShow(show);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if there is a show with the given name registered within the schedule.
	 * 
	 * @param name the show's name.
	 * @return the entry which contains the show with the given name, false
	 *         otherwise.
	 */
	public ScheduleEntry contains(String name) {

		for (Iterator<ScheduleEntry> it = entries.iterator(); it.hasNext();) {
			ScheduleEntry e = it.next();
			if (e.getShow() != null && e.getShow().getName().equals(name))
				return e;
		}
		return null;
	}

	/**
	 * 
	 * Deletes the show with the given name from the schedule.
	 * 
	 * @param name the name of the show.
	 * @return true if the deletion was successful, false otherwise.
	 */
	public boolean deleteEntry(String name) {
		ScheduleEntry temp = contains(name);
		if (temp != null) {
			temp.setShow(null);
			return true;
		}
		return false;
	}

	/**
	 * Swaps two entries within the schedule.
	 * 
	 * @param name1 the name of the first to be swapped entry.
	 * @param name2 the name of the second to be swapped entry.
	 * @return true if the entries with the given shows' names exist, false
	 *         otherwise.
	 */
	public boolean swapEntries(String name1, String name2) {
		Show temp;

		ScheduleEntry toSwap1 = contains(name1);
		ScheduleEntry toSwap2 = contains(name2);

		if (toSwap1 != null && toSwap2 != null) {
			temp = toSwap1.getShow();
			toSwap1.setShow(toSwap2.getShow());
			toSwap2.setShow(temp);
			return true;
		}

		return false;
	}

	/**
	 * Moves an entry within the schedule by offset entries forwards or backwards.
	 * 
	 * @param name   the show's name.
	 * @param offset the offset which will be used to calculate the next position of
	 *               the entry within the schedule.
	 * @return true if the move was successful, false otherwise.
	 */
	public boolean moveEntry(String name, int offset) {
		ScheduleEntry temp = contains(name);
		if (temp == null)
			return false;
		int nextPosition = entries.indexOf(temp) + offset;

		if (nextPosition < 0 || nextPosition > entries.size() - 1)
			return false;
		else {
			if (entries.get(nextPosition).getShow() != null)
				return false;
			else {
				entries.get(nextPosition).setShow(temp.getShow());
				temp.setShow(null);
				return true;
			}
		}
	}

}
