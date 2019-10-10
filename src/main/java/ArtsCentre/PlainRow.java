
package ArtsCentre;

import java.io.Serializable;

public class PlainRow extends Row implements Serializable {

	private static final long serialVersionUID = 8110872787542237977L;

	public PlainRow(int numberOfSeats, int number) {
		super(numberOfSeats, number);

		for (int i = 0; i < numberOfSeats; i++) {
			Seat s = new PlainSeat(i + 1, 5);
			seats.add(s);
		}
	}
}