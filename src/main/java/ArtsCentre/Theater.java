package ArtsCentre;
/**
 *@author George Simeonidis
 */

import java.io.Serializable;
import java.text.ParseException;

public class Theater extends Room implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a theatre with a predefined name and capacity.
	 * 
	 * @param name
	 *            theatre's name.
	 * @param cap
	 *            theatre's capacity.
	 * @throws java.text.ParseException
	 */
	public Theater(String name, int cap) throws ParseException {
		super(name, cap);
		type = RoomType.Theatre;
	}

	@Override
	public String toString() {
		String description = super.toString();
		description += " Facility type : Theatre";
		return description;
	}

}
