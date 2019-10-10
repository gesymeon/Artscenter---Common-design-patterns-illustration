
package ArtsCentre;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
/**
 * Υλοποιεί την οντότητα του πολυχώρου.
 * @author George Simeonidis
 * 
 */
import java.util.Iterator;
import java.util.List;

public class Artspace implements Serializable {

	private static final long serialVersionUID = 8296538502299940605L;
	List<Room> rooms;

	public Artspace() {
		rooms = new ArrayList<>();
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	/**
	 * Calls The factory method and provides validation checks.
	 * 
	 * @param roomName     the name of the room to be inserted in the artspace.
	 * @param roomCapacity the capacity of the room to be inserted in the artspace.
	 * @param roomtype     the type of the room to be inserted in the artspace.
	 * @return true if the insertion was successful, false otherwise.
	 * @throws ParseException 
	 */
	public boolean createRoom(String roomName, int roomCapacity, RoomType roomType) throws ParseException {
		Room newRoom = null;
		newRoom = roomFactory(roomName, roomCapacity, roomType);
		// the comparison is done based on the rooms' names.
		if (newRoom != null && !rooms.contains(newRoom)) {
			rooms.add(newRoom);
			return true;
		} else
			return false;
	}

	/**
	 * Default Factory method implementation.
	 * 
	 * @param roomName     the name of the room to be inserted in the artspace.
	 * @param roomCapacity the capacity of the room to be inserted in the artspace.
	 * @param roomtype     the type of the room to be inserted in the artspace.
	 * @return the newly created room.
	 * @throws ParseException 
	 */
	protected Room roomFactory(String roomName, int roomCapacity, RoomType roomType) throws ParseException {
		Room newRoom = null;

		if (roomType == RoomType.Cinema) {
			newRoom = new Cinema(roomName, roomCapacity);
		} else if (roomType == RoomType.Theatre) {
			newRoom = new Theater(roomName, roomCapacity);
		}

		return newRoom;
	}

	/**
	 * Deletes a room from the artspace based on its name.
	 * 
	 * @author George Simeonidis
	 * @param name the name of the room to be deleted.
	 * @return true if the deletion is successful, false otherwise.
	 */
	public boolean deleteRoom(String name) {

		Iterator<Room> it = rooms.iterator();

		while (it.hasNext()) {
			if (it.next().getName().equals(name)) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a room within the artspace based on its name.
	 * 
	 * @author George Simeonidis
	 * @param name the name of the room within the artspace.
	 * @return the room in case it exists, false otherwise.
	 */
	public Room existingRoom(String name) {

		for (Room room : rooms) {
			if (room.getName().equals(name))
				return room;
		}
		return null;
	}

	/**
	 * Returns a room within the artspace that contains the given show on the given
	 * date.
	 * 
	 * @author George Simeonidis
	 * @param name the show's name.
	 * @param date the date for which we search for the show within the room's
	 *             calendar.
	 * @return the room in case such a room exists, false otherwise.
	 */
	public Room showBasedSearch(String name, String date) {
		Schedule temp;
		for (Room room : rooms) {
			temp = room.getSchedule(date);
			if (temp != null && null != temp.contains(name))
				return room;
		}

		return null;

	}

}