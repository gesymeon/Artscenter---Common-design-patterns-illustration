
package artsCenter;

import java.io.Serializable;
import java.text.ParseException;

public class Cinema extends Room implements Serializable {

	private static final long serialVersionUID = 4130489801921353248L;

	/**
	 * Creates a cinema with a predefined name and capacity.
	 * 
	 * @param name the cinema's name.
	 * @param cap  the cinema's capacity.
	 * @throws java.text.ParseException
	 * @throws CloneNotSupportedException
	 */
	public Cinema(String name, int capacity) throws ParseException, CloneNotSupportedException {
		super(name, capacity);
	}

	@Override
	public String toString() {
		String description = super.toString();
		description += " Room Type : Cinema";
		return description;
	}

	@Override
	protected RoomType getRoomType() {
		return RoomType.Cinema;
	}

}
