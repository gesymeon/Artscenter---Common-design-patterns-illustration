package artsCenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Row implements Serializable {

	private static final long serialVersionUID = -4303470249348527557L;

	// The row's index (incremental number) inside a room.
	protected int index;

	protected ArrayList<Seat> seats;

	protected int numberOfSeats;

	/**
	 * 
	 * @param numberOfSeats row's number of seats.
	 * @param number        row's incremental number.
	 */
	public Row(int numberOfSeats, int index) {
		this.index = index;
		this.numberOfSeats = numberOfSeats;
		seats = new ArrayList<>(numberOfSeats);
	}

	public Row(Row source) {
		this.index = source.getIndex();
		this.numberOfSeats = source.getCapacity();
		seats = new ArrayList<>(numberOfSeats);
		Iterator<Seat> iterator = source.getSeats().iterator();

		while (iterator.hasNext()) {
			seats.add(iterator.next().deepCopy());
		}

	}

	public abstract Row deepCopy();
	
	public List<Seat> getSeats() {
		return seats;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getCapacity() {
		return numberOfSeats;
	}

	/**
	 * Changes a seat's quality to luxurious.
	 * 
	 * @param position The seat's incremental number within the row.
	 * @return true if the conversion is successful, false otherwise.
	 */
	public boolean makeLuxurious(int position) {
		Seat lux;
		if (position > numberOfSeats || position < 1 || seats.get(position - 1) instanceof LuxuriousSeat) {
			return false;
		} else if (seats.get(position - 1) instanceof PlainSeat) {
			lux = new LuxuriousSeat(seats.get(position - 1));
			lux.setPrice(10.0);
			seats.set(position - 1, lux);
			return true;
		}
		return false;
	}

	/**
	 * Returns a row's seat indexed by its incremental number within the row.
	 * 
	 * @param position the seat's incremental number.
	 * @return the seat with the provided incremental number, null otherwise.
	 */
	public Seat getSeat(int position) {
		if (position >= 1 && position <= seats.size())
			return seats.get(position - 1);
		else
			return null;
	}

}
