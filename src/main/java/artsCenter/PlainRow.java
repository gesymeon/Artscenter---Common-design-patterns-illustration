
package artsCenter;

import java.io.Serializable;

public class PlainRow extends Row implements Serializable {

	private static final long serialVersionUID = 8110872787542237977L;

	public PlainRow(int numberOfSeats, int index) {
		super(numberOfSeats, index);

		for (int i = 0; i < numberOfSeats; i++) {
			Seat s = new PlainSeat(i + 1, 5);
			seats.add(s);
		}
	}

	public PlainRow(Row r) {
		super(r);
	}

	@Override
	public Row deepCopy() {
		return new PlainRow(this);
	}
	
	
}