package artsCenter;

import java.io.Serializable;
import java.text.ParseException;

public class Theater extends Room implements Serializable {

	private static final long serialVersionUID = -7000918600822945391L;

	/**
	 * Creates a theater with a predefined name and capacity.
	 * 
	 * @param name theatre's name.
	 * @param cap  theatre's capacity.
	 * @throws java.text.ParseException
	 * @throws CloneNotSupportedException 
	 */
	public Theater(String name, int capacity) throws ParseException, CloneNotSupportedException {
		super(name, capacity);
	}

	@Override
	public String toString() {
		String description = super.toString();
		description += " Room type : Theatre";
		return description;
	}

	@Override
	protected RoomType getRoomType() {
		return RoomType.Theater;
	}
}
