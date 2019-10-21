
package artsCenter;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EndUser implements Serializable {

	private static final long serialVersionUID = -5671014762396218961L;
	private String name;
	private String telephone;
	private ArrayList<Seat> reservations;

	public void saveAccount() throws IOException {

		/*
		 * try (ObjectOutputStream out = new ObjectOutputStream(new
		 * FileOutputStream("users.txt"))) { out.writeObject(this); }
		 */

		boolean exists = new File("users.txt").exists();

		try (FileOutputStream fos = new FileOutputStream("users.txt", true);
				ObjectOutputStream oos = exists ? new ObjectOutputStream(fos) {
					@Override
					protected void writeStreamHeader() throws IOException {
						reset();
					}
				} : new ObjectOutputStream(fos)) {
			oos.writeObject(this);
		}

	}
	
	//TODO: find the best way to refactor this
	public static EndUser searchUser(String username) throws IOException, ClassNotFoundException {
		if (new File("users.txt").exists()) {
			FileInputStream fis = new FileInputStream("users.txt");
			try (ObjectInputStream in = new ObjectInputStream(fis)) {
				while (fis.available() > 0) {
					EndUser temp = (EndUser) in.readObject();
					if (temp.getName().equals(username)) {
						return temp;
					}
				}
			} finally {
				fis.close();
			}

		}
		return null;
	}

	public EndUser(String name, String telephone) {
		this.name = name;
		this.telephone = telephone;
		reservations = new ArrayList<>();
	}

	public List<Seat> getReservations() {
		return reservations;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return telephone;
	}

	public void setPhone(String phone) {
		telephone = phone;
	}

	/**
	 * Reserves a seat for the user.
	 * 
	 * @param seat the seat to be reserved.
	 * @return true if the reservation can be completed, false if the seat is
	 *         already reserved.
	 */
	public boolean makeReservation(Seat seat) {
		if (seat.isReserved())
			return false;
		else {
			seat.setReserved(true);
			reservations.add(seat);
			return true;
		}
	}

	/**
	 * Cancels the reservation of a specific seat.
	 * 
	 * @param seat the seat whose reservation on behalf of the user will be
	 *             cancelled.
	 * @return true if the seat exists on the user's reservations list, false
	 *         otherwise.
	 */
	public boolean cancelReservation(Seat seat) {

		if (!seat.isReserved())
			return false;
		else {
			if (reservations.contains(seat)) {
				seat.setReserved(false);
				reservations.remove(seat);
				return true;
			} else {
				return false;
			}
		}

	}

}
