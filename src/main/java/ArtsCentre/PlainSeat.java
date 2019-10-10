
package ArtsCentre;

import java.io.Serializable;

/**
 * @author George Simeonidis
 */
public class PlainSeat extends Seat implements Serializable {

	private static final long serialVersionUID = 1L;

	public PlainSeat(int number, double aPrice) {
		super();

		table = false;
		positionWithinRow = number;
		price = aPrice;

	}

	public PlainSeat(Seat s) {
		super(s);
	}

	@Override
	public boolean isLuxurious() {
		return false;
	}

}
