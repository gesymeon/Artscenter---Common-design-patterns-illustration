
package artsCenter;

import java.io.IOException;
import java.text.ParseException;

public class Administrator {

	/**
	 * Adds a new room to the artCenter.
	 * 
	 * @param roomName     the name of the room.
	 * @param roomCapacity the capacity of the room.
	 * @param roomtype     roomType the type of the room.
	 * @return true if the addition is successful, false otherwise.
	 * @throws ParseException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws CloneNotSupportedException
	 * 
	 */
	public boolean addRoom(String roomName, int roomCapacity, RoomType roomtype)
			throws ParseException, ClassNotFoundException, IOException, CloneNotSupportedException {
		return Artspace.getInstance().createRoom(roomName, roomCapacity, roomtype);
	}

	/**
	 * Deletes a room from the artsCenter.
	 * 
	 * @param roomName the name of the room to be deleted.
	 * @return true if the deletion is successful.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public boolean deleteRoom(String roomName) throws ClassNotFoundException, IOException {
		return Artspace.getInstance().deleteRoom(roomName);
	}
	
	
	public boolean existingRoom(String roomName)
			throws ClassNotFoundException, IOException{
		return Artspace.getInstance().existingRoom(roomName) != null ;
	}
	

}
