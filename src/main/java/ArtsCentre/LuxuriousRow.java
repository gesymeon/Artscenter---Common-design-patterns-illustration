
package ArtsCentre;

import java.io.Serializable;


public class LuxuriousRow extends Row implements Serializable {

	private static final long serialVersionUID = 1L;

	public LuxuriousRow(int number_of_seats, int number) {
		super(number_of_seats, number);

		for (int i = 0; i < number_of_seats; i++) {
			Seat s = new LuxuriousSeat(i + 1, 10);
			seats.add(s);
		}
	}

}