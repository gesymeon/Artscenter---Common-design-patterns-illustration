
package ArtsCentre;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;

public class Administrator implements Serializable {

	private static final long serialVersionUID = 3898723476777410594L;

	// ο πολυχώρος πρέπει να είναι κοινός και για το διαχειριστή και για τους
	// χρήστες, επομένως τον θέτουμε static μεταβλητή του διαχειριστή.
	public static Artspace workplace;

	public Administrator() throws IOException, ClassNotFoundException {
		loadWorkplace();
	}

	public Administrator(Artspace space) {
		workplace = space;
	}

	/**
	 * Επιτρέπει την προσθήκη χώρων στον πολυχώρο μέσω του αντικειμένου του
	 * διαχειριστή.
	 * 
	 * @author George Simeonidis
	 * @param roomName     το όνομα του χώρου που θα προστεθεί.
	 * @param roomCapacity η χωρητικότητα του χώρου που θα προστεθεί.
	 * @param roomtype     roomType ο τύπος του χώρου που θα προστεθεί.
	 * @return true αν η προσθήκη ολοκληρωθεί με επιτυχία , false αν όχι.
	 * @throws ParseException 
	 * 
	 */
	public boolean addRoom(String roomName, int roomCapacity, RoomType roomtype) throws ParseException {
		return workplace.createRoom(roomName, roomCapacity, roomtype);
	}

	/**
	 * Επιτρέπει την διαγραφή χώρων του πολυχώρου μέσω του αντικειμένου του
	 * διαχειριστή.
	 * 
	 * @author George Simeonidis
	 * @param roomName το όνομα του χώρου που θα διαγραφεί.
	 * @return true αν η διαγραφή ολοκληρωθεί με επιτυχία, false αν όχι.
	 */
	public boolean deleteRoom(String roomName) {
		return workplace.deleteRoom(roomName);
	}

	/**
	 * Καταγράφει στον δίσκο το τρέχον στιγμιότυπο του πολυχώρου στο αρχειο
	 * artspace.txt.
	 * 
	 * @author George Simeonidis
	 * @throws java.io.IOException
	 * 
	 */
	public static void saveWorkplace() throws IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("artspace.txt"))) {
			out.writeObject(workplace);
		}
	}

	/**
	 * Ενημερώνει τους χώρους του πολυχώρου με με τους χώρους που βρίσκονται
	 * αποθηκευμένοι στο αρχείο artspace.txt στο δίσκο.
	 * 
	 * @author George Simeonidis
	 * @throws java.io.IOException
	 * @throws java.lang.ClassNotFoundException
	 */
	public static void loadWorkplace() throws IOException, ClassNotFoundException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("artspace.txt"))) {
			workplace = ((Artspace) in.readObject());
		}
	}

}
