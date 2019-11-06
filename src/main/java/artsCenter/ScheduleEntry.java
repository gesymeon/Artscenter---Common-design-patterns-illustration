
package artsCenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScheduleEntry implements Serializable {

	private static final long serialVersionUID = 4231857028633854264L;
	private Show show;
	private float startingTime;
	private float finishingTime;

	// Every entry contains a copy of the facility's rows, so that the changes on an
	// entry's structure do not affect other schedules' entries.
	private List<Row> rows;

	/**
	 * Initializes an empty entry with its corresponding operating time interval.
	 * 
	 * @param startingTime  The entry's show starting time.
	 * @param finishingTime The entry's show finishing time.
	 */
	public ScheduleEntry(float startingTime, float finishingTime) {
		this.startingTime = startingTime;
		this.finishingTime = finishingTime;
		rows = new ArrayList<>();
	}

	public ScheduleEntry(float startingTime, float finishingTime, List<Row> rows) {
		this.startingTime = startingTime;
		this.finishingTime = finishingTime;
		this.rows = new ArrayList<>();
		Iterator<Row> iterator = rows.iterator();
		while (iterator.hasNext()) {
			this.rows.add(iterator.next().deepCopy());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((show == null) ? 0 : show.hashCode());
		return result;
	}

	// the equality of two entries depends only on the names of the shows they
	// contain
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ScheduleEntry)) {
			return false;
		}
		ScheduleEntry other = (ScheduleEntry) obj;
		if (show == null) {
			if (other.show != null) {
				return false;
			}
		} else if (!show.equals(other.show)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {

		String toReturn1 = String.valueOf(startingTime);
		String toReturn2 = String.valueOf(finishingTime);

		String[] temp1 = toReturn1.split("\\.");
		String[] temp2 = toReturn2.split("\\.");

		return temp1[0] + "::" + temp1[1] + "0" + "<br>" + temp2[0] + "::" + temp2[1] + "0";

	}

	public List<Row> getRows() {
		return rows;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public float getStartingTime() {
		return startingTime;
	}

	public float getFinishingTime() {
		return finishingTime;
	}

	public void setStartingTime(float time) {
		startingTime = time;
	}

	public void setFinishingTime(float time) {
		finishingTime = time;
	}

	/**
	 * Returns the number of the seats of the row containing the maximum number of
	 * seats.
	 * 
	 * @return The maximum number of seats.
	 */
	public int maximumSeats() {
		int max = -1;
		for (Row row : rows)
			if (row.getCapacity() > max)
				max = row.getCapacity();
		return max;
	}

}
