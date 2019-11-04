
package artsCenter;

import java.io.Serializable;

public class PlainSeat extends Seat implements Serializable {

	private static final long serialVersionUID = -3152468396855403108L;

	public PlainSeat(int index, double aPrice) {
		super();

		positionWithinRow = index;
		price = aPrice;
		table = false;

	}

	public PlainSeat(Seat s) {
		super(s);
	}

	@Override
	public Seat deepCopy() {
		return new PlainSeat(this);
	}

	@Override
	public boolean isLuxurious() {
		return false;
	}

}
