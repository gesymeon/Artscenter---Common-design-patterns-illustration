
package ArtsCentre;

import java.io.Serializable;

/**
 * @author George Simeonidis
 */
public abstract class Seat implements Serializable {

	private static final long serialVersionUID = 1L;

	// used to id its seat uniquely.
	private static int shared = 0;
	// keeps the reservation status of the seat.
	protected boolean reserved;

	protected boolean table;

	// the incremental number indexing the seat within its row.
	protected int positionWithinRow;

	// a unique id for each seat.
	protected int id;

	// the seat's price depending on its quality.
	protected double price;

	public Seat() {
		++shared;
		id = shared;
		reserved = false;
	}

	public Seat(Seat source) {
		positionWithinRow = source.getNumber();
		reserved = source.isReserved();
		table = source.hasTable();
		id = source.getID();
		price = source.getPrice();
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double aPrice) {
		price = aPrice;
	}

	public void setNumber(int number) {
		positionWithinRow = number;
	}

	public int getNumber() {
		return positionWithinRow;
	}

	// TODO: I should test !
	public abstract boolean isLuxurious();

	public void setReserved(Boolean b) {
		reserved = b;
	}

	public boolean isReserved() {
		return reserved;
	}

	@Override
	public String toString() {
		return String.valueOf(positionWithinRow);
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass())
			return false;

		Seat s = (Seat) obj;

		return id == s.getID();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash + id;
		hash = 47 * hash;
		return hash;
	}

	public boolean hasTable() {
		return table;
	}

}
