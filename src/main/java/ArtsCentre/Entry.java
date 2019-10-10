
package ArtsCentre;

/**
 * @author George Simeonidis
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Entry implements Serializable {

	private static final long serialVersionUID = 1L;

	private Show show;
	private float startingTime;
	private float finishingTime;

	// Every entry contains a copy of the facility's rows, so that the changes on an
	// entry's status do not effect other schedules' entries.
	private ArrayList<Row> rows;

	/**
	 * Initializes an empty entry with its corresponding operating time interval.
	 * 
	 * @param startingTime  The entry's show starting time.
	 * @param finishingTime The entry's show finishing time.
	 */
	public Entry(float startingTime, float finishingTime) {
		this.startingTime = startingTime;
		this.finishingTime = finishingTime;
		rows = new ArrayList<>();
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
		if (!(obj instanceof Entry)) {
			return false;
		}
		Entry other = (Entry) obj;
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
	 * Copies the facility's rows into the rows of the entry(deep copy).
	 * 
	 * @param r The facility's rows.
	 * @return true, if the facility's rows are not null, false otherwise.
	 */
	public boolean setRows(ArrayList<Row> r) {
		Row temp = null;

		// This part initializes the entry's rows.
		if (rows.isEmpty()) {

			for (Row row : r) {

				if (row instanceof PlainRow) {
					temp = new PlainRow(row.getCapacity(), row.getIndex());
				} else if (row instanceof LuxuriousRow) {
					temp = new LuxuriousRow(row.getCapacity(), row.getIndex());
				}
				rows.add(temp);
			}

			return true;
		}

		else if (!r.isEmpty()) {

			// if from the time the entry was created the rows of the facility have been
			// increased, we extend the entry's rows accordingly
			if (r.size() > rows.size()) {
				for (int counter = rows.size() + 1; counter <= r.size(); counter++) {

					if (r.get(counter - 1) instanceof PlainRow)
						rows.add(new PlainRow(r.get(counter - 1).getCapacity(), counter));

					if (r.get(counter - 1) instanceof LuxuriousRow)
						rows.add(new LuxuriousRow(r.get(counter - 1).getCapacity(), counter));

				}
			}

			// the content of the entry's rows is not overwritten during the copying
			// procedure
			for (int i = 0; i < r.size(); i++) {
				if (r.get(i) instanceof PlainRow) {
					temp = new PlainRow(r.get(i).getCapacity(), r.get(i).getIndex());

					for (int j = 0; j < rows.get(i).getCapacity(); j++) {
						Seat temp_seat = new PlainSeat(rows.get(i).getSeat(j + 1));
						temp.getSeats().set(j, temp_seat);
					}

				} else if (r.get(i) instanceof LuxuriousRow) {
					temp = new LuxuriousRow(r.get(i).getCapacity(), r.get(i).getIndex());

					for (int j = 0; j < rows.get(i).getCapacity(); j++) {
						Seat temp_seat = new LuxuriousSeat(rows.get(i).getSeat(j + 1));
						temp.getSeats().set(j, temp_seat);
					}
				}
				rows.set(i, temp);
			}

			// σε πε�?ίπτωση που έχει π�?οηγηθεί αφαί�?εση σει�?άς απο τον χώ�?ο
			// φ�?οντίζουμε η "αν�?πα�?κτη" σει�?ά που β�?ίσκεται στο τέλος των σει�?ών της
			// καταχώ�?ησης να αφαι�?εθεί.
			// αφαι�?ώντας την σει�?ά με αυτόν τον τ�?όπο φ�?οντίζουμε οι κ�?ατήσεις των
			// θεατών να μην κατα�?γο�?νται αφο�? οι πλη�?οφο�?ίες σχετικά με την κ�?άτηση
			// τους έχουν ήδη π�?οστεθεί στην καταχώ�?ηση μέσω της
			// temp πα�?απάνω.
			if (rows.size() > r.size())
				rows.remove(rows.size() - 1);

		} else
			return false;

		return true;
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
