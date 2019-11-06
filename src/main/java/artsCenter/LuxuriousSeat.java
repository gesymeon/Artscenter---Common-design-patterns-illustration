
package artsCenter;

import java.io.Serializable;

public class LuxuriousSeat extends Seat implements Serializable {

	private static final long serialVersionUID = 2715665732506243269L;

	public LuxuriousSeat(int index, double aPrice) {
		super();

		positionWithinRow = index;
		price = aPrice;
		table = true;

	}

	public LuxuriousSeat(Seat s) {
		super(s);
		setPrice(10.0);
	}

	@Override
	public Seat deepCopy() {
		return new LuxuriousSeat(this);
	}

	@Override
	public boolean isLuxurious() {
		return true;
	}

}
