
package ArtsCentre;

import java.io.Serializable;

/**
 * @author George Simeonidis
 */

public class LuxuriousSeat extends Seat implements Serializable {

	private static final long serialVersionUID = 1L;

	public LuxuriousSeat(int number, double aPrice) {
		super();

		positionWithinRow = number;
		price = aPrice;
		table = true;

	}

	public LuxuriousSeat(Seat s) {
		super(s);
	}

	@Override
	public boolean isLuxurious() {
		return true;
	}

}
