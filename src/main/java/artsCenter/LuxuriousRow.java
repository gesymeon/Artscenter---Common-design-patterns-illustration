
package artsCenter;

import java.io.Serializable;

public class LuxuriousRow extends Row implements Serializable {

	private static final long serialVersionUID = 8794737181153171841L;

	public LuxuriousRow(int numberOfSeats, int index) {
		super(numberOfSeats, index);

		for (int i = 0; i < numberOfSeats; i++) {
			Seat s = new LuxuriousSeat(i + 1, 10);
			seats.add(s);
		}
	}

	public LuxuriousRow(Row r) {
		super(r);
	}

	@Override
	public Row deepCopy() {
		return new LuxuriousRow(this);
	}

}