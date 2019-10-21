
package artsCenter;

import java.io.Serializable;

public class LuxuriousSeat extends Seat implements Serializable {

	private static final long serialVersionUID = 444260795205856537L;

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
	public Seat deepCopy() {
		return new LuxuriousSeat(this);
	}
	
	
	@Override
	public boolean isLuxurious() {
		return true;
	}


}
