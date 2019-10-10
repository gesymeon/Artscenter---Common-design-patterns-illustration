
package ArtsCentre;

import java.io.Serializable;
import java.text.ParseException;

/**
 * @author George Simeonidis
 */

public class Cinema extends Room implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a cinema with a predefined capacity and name.
	 * 
	 * @param name
	 *            cinema's name.
	 * @param cap
	 *            cinema's capacity.
	 * @throws java.text.ParseException
	 */
	public Cinema(String name, int cap) throws ParseException {
		super(name, cap);
		type = RoomType.Cinema;
	}

	@Override
	public String toString() {
		String description = super.toString();
		description += " Facility type : cinema";
		return description;
	}

}
