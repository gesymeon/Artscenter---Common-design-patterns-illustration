
package artsCenter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonReader;

public class EndUser implements Serializable {

	private static final long serialVersionUID = -7485116421829229937L;

	private static final String RESOURCE_PATH = "users.json";

	private static final Gson gson;

	static {
		gson = new GsonBuilder().enableComplexMapKeySerialization().registerTypeAdapter(Show.class, new ShowAdapter())
				.registerTypeAdapter(Seat.class, new SeatAdapter()).setPrettyPrinting().create();
	}

	private String name;
	private String telephone;
	private Map<Show, ArrayList<Seat>> reservations;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EndUser other = (EndUser) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone)) {
			return false;
		}
		return true;
	}

	public void saveAccount() throws IOException {

		if (new File(RESOURCE_PATH).exists()) {
			EndUser[] users = gson.fromJson(new JsonReader(new FileReader(RESOURCE_PATH)), EndUser[].class);

			List<EndUser> asList = new LinkedList<>(Arrays.asList(users));
			asList.remove(this);
			asList.add(this);

			JsonElement jsonobject = gson.toJsonTree(asList);

			try (FileWriter writer = new FileWriter(RESOURCE_PATH)) {
				gson.toJson(jsonobject, writer);
				writer.flush();

			} catch (JsonIOException | IOException e) {
				System.out.println("ToLogger");
			}

		} else {
			try (FileWriter writer = new FileWriter(RESOURCE_PATH)) {
				gson.toJson(Arrays.asList(this), writer);
				writer.flush();

			} catch (JsonIOException | IOException e) {
				System.out.println("ToLogger");
			}
		}
	}

	public static EndUser searchUser(String username) throws IOException {

		EndUser[] users = gson.fromJson(new JsonReader(new FileReader(RESOURCE_PATH)), EndUser[].class);

		if (users != null) {
			List<EndUser> asList = new LinkedList<>(Arrays.asList(users));

			for (EndUser user : asList)
				if (user.getName().equals(username))
					return user;
		}
		return null;
	}

	public EndUser(String name, String telephone) {
		this.name = name;
		this.telephone = telephone;
		reservations = new HashMap<>();
	}

	public Map<Show, ArrayList<Seat>> getReservations() {
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
	 * Checks if the the particular seat in the particular show is registered to the
	 * user.
	 * 
	 * @param seat the seat to be checked for registration.
	 * @param show the show to be checked for registration.
	 * @return true if the reservation for the user exists, false otherwise.
	 */
	public boolean containsReservation(Show show, Seat seat) {
		ArrayList<Seat> temp = reservations.get(show);
		return (temp != null && temp.contains(seat));
	}

	/**
	 * Reserves a seat for the user.
	 * 
	 * @param seat the seat to be reserved.
	 * @return true if the reservation can be completed, false if the seat is
	 *         already reserved.
	 */
	public boolean makeReservation(Show show, Seat seat) {
		if (seat.isReserved()) {
			return false;
		}
		seat.setReserved(true);
		ArrayList<Seat> temp = reservations.get(show);
		if (temp == null)
			temp = new ArrayList<>();
		temp.add(seat);
		reservations.put(show, temp);
		return true;
	}

	/**
	 * Cancels the reservation of a specific seat.
	 * 
	 * @param seat the seat whose reservation on behalf of the user will be
	 *             cancelled.
	 * @return true if the seat exists on the user's reservations list, false
	 *         otherwise.
	 */
	public boolean cancelReservation(Show show, Seat seat) {

		if (!seat.isReserved()) {
			return false;
		}
		ArrayList<Seat> temp = reservations.get(show);
		if (temp != null && temp.contains(seat)) {
			seat.setReserved(false);
			temp.remove(seat);
			return true;
		}
		return false;

	}

}
