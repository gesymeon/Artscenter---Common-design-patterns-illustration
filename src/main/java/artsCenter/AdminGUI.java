
package artsCenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AdminGUI extends JFrame implements Serializable {

	private static final long serialVersionUID = -6097025320490573174L;
	private JMenuBar menubar;
	private JMenu rooms;
	private JMenu shows;
	private JMenu scheduleMenu;
	private JMenu file;
	private static Artspace workspace;

	public static void main(String[] args) {

		Administrator admin = new Administrator();
		try {

			workspace = Artspace.getInstance();

		} catch (IOException ex) {

			JOptionPane.showMessageDialog(null, "Could not read the artspace from file", null,
					JOptionPane.ERROR_MESSAGE, null);

		} catch (ClassNotFoundException ex) {

			JOptionPane.showMessageDialog(null, "Missing data from .jar file", null, JOptionPane.ERROR_MESSAGE, null);
		} finally {
			AdminGUI g = new AdminGUI(admin);
		}

	}

	/**
	 * Δημιουργεί και εμφανίζει τα παράθυρα και τα μενού του γραφικού περιβάλλοντος.
	 * 
	 * @param admin Το αντικείμενο Administrator του οποίου ο πολυχώρος θα μπορεί να
	 *              τροποποιηθεί.
	 */

	public AdminGUI(Administrator admin) {

		URL imageURL;
		Icon icon;

		setTitle("Administrator GUI");
		setSize(1000, 1500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel background = new JLabel();
		imageURL = getClass().getResource("images/admin.jpg");
		if (imageURL != null) {
			icon = new ImageIcon(imageURL);
			background.setIcon(icon);
		}

		add(background, BorderLayout.CENTER);

		menubar = new JMenuBar();
		file = new JMenu("File");
		rooms = new JMenu("Rooms");
		menubar.add(file);
		menubar.add(rooms);

		JMenu submenu = new JMenu("Add Room");

		JMenuItem cinema = new JMenuItem("Cinema");
		JMenuItem theatre = new JMenuItem("Theater");
		JMenuItem deleteRoom = new JMenuItem("Delete Room");

		JMenuItem save = new JMenuItem("Save Changes to Disk");

		JMenuItem addrow = new JMenuItem("Add Row");
		JMenuItem deleterow = new JMenuItem("Delete Row");

		submenu.add(theatre);
		submenu.add(cinema);
		rooms.add(submenu);
		rooms.addSeparator();
		rooms.add(deleteRoom);
		rooms.addSeparator();
		rooms.add(addrow);
		rooms.add(deleterow);
		file.add(save);
		rooms.addSeparator();

		JMenu seats = new JMenu("Seat modification");
		JMenuItem makeLux = new JMenuItem("convert to luxurious");
		seats.add(makeLux);
		rooms.add(seats);

		makeLux.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String roomName = (String) JOptionPane.showInputDialog(null,
							"Plese give the name of the room where the conversion will take place",
							JOptionPane.QUESTION_MESSAGE);

					Room found = workspace.existingRoom(roomName);

					if (found != null) {
						Integer row = Integer.parseInt(JOptionPane.showInputDialog(null,
								"Please give the index of the row whose seat will be converted",
								JOptionPane.QUESTION_MESSAGE));

						Integer seat = Integer.parseInt(JOptionPane.showInputDialog(null,
								"Please give the index of the seat to be converted", JOptionPane.QUESTION_MESSAGE));

						found.makeLuxurious(row, seat);
					} else
						JOptionPane.showMessageDialog(null, "No room with the given name exists", null,
								JOptionPane.ERROR_MESSAGE, null);
				} catch (NullPointerException ex) {
					return;
				}
			}

		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					Artspace.saveArtspace();
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "An error occured during the writing of the data to disk", null,
							JOptionPane.ERROR_MESSAGE, null);
				}

			}
		});

		// πρέπει να υπολοιηθούν έλεγχοι για το κατα πόσο οι τιμές που δίνονται απο τον
		// χρηστη ειναι επιτρεπτές.
		cinema.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String roomName;
				int roomCapacity;
				do {
					JOptionPane.showMessageDialog(null,
							"The name of the room must not be empty and the capacity must be greater than zero.", null,
							JOptionPane.INFORMATION_MESSAGE, null);
					roomName = JOptionPane.showInputDialog(null, "Please give the cinema's name",
							JOptionPane.PLAIN_MESSAGE);
					roomCapacity = Integer.parseInt(JOptionPane.showInputDialog(null,
							"Please give the cinema's capacity", JOptionPane.PLAIN_MESSAGE));
				} while ((roomName.trim().equals("") || roomCapacity <= 0));

				try {
					if (admin.addRoom(roomName, roomCapacity, RoomType.Cinema)) {
						JOptionPane.showMessageDialog(null, "The room was added successfully", null,
								JOptionPane.INFORMATION_MESSAGE, null);

					} else
						JOptionPane.showMessageDialog(null, "A room with the given name already exists", null,
								JOptionPane.INFORMATION_MESSAGE, null);
				} catch (ParseException | HeadlessException | ClassNotFoundException | IOException ex) {
					JOptionPane.showMessageDialog(null, "An error has occured during the data reading from disk", null,
							JOptionPane.INFORMATION_MESSAGE, null);
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		theatre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String roomName;
				int roomCapacity;
				do {
					JOptionPane.showMessageDialog(null,
							"The name of the room should not be empty and the capacity should be > 0", null,
							JOptionPane.INFORMATION_MESSAGE, null);
					roomName = (String) JOptionPane.showInputDialog(null, "Please give the name of the theater",
							JOptionPane.PLAIN_MESSAGE);
					roomCapacity = Integer.parseInt(JOptionPane.showInputDialog(null,
							"Please give the capacity of the theater", JOptionPane.PLAIN_MESSAGE));
				} while ((roomName.trim().equals("") || roomCapacity <= 0));

				try {
					if (admin.addRoom(roomName, roomCapacity, RoomType.Theater)) {
						JOptionPane.showMessageDialog(null, "Τhe theater was added successfully", null,
								JOptionPane.INFORMATION_MESSAGE, null);

					} else
						JOptionPane.showMessageDialog(null, "A theater with the given name already exists", null,
								JOptionPane.INFORMATION_MESSAGE, null);

				} catch (ParseException | HeadlessException | ClassNotFoundException | IOException ex) {
					JOptionPane.showMessageDialog(null, "There was an error when reading the data from disk", null,
							JOptionPane.INFORMATION_MESSAGE, null);
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		deleteRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String roomName = JOptionPane.showInputDialog(null,
							"Please give the name of the room to be deleted", JOptionPane.QUESTION_MESSAGE);

					if (admin.deleteRoom(roomName)) {
						JOptionPane.showMessageDialog(null, "The room was successfully deleted", null,
								JOptionPane.INFORMATION_MESSAGE, null);

					} else
						JOptionPane.showMessageDialog(null, "There is no room with the given name", null,
								JOptionPane.INFORMATION_MESSAGE, null);
				} catch (NullPointerException | HeadlessException | ClassNotFoundException | IOException ex) {
					return;
				}
			}
		});

		deleterow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String roomName = JOptionPane.showInputDialog(null,
							"Please give the name of the room from which the row will be deleted ",
							JOptionPane.QUESTION_MESSAGE);

					Room found = workspace.existingRoom(roomName);

					int position = Integer.parseInt(JOptionPane.showInputDialog(null,
							"Please give the index of the row to be deleted", JOptionPane.QUESTION_MESSAGE));

					found.deleteRow(position);

				} catch (NullPointerException ex) {

					JOptionPane.showMessageDialog(null, "No room with the given name was found", null,
							JOptionPane.ERROR_MESSAGE, null);
					return;
				}
			}
		});

		addrow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String roomName = JOptionPane.showInputDialog(null,
							"Please give the name of the room where the addition will take place",
							JOptionPane.QUESTION_MESSAGE);

					int capacity = Integer.parseInt(JOptionPane.showInputDialog(null,
							"Please give the number of the seats within the row", JOptionPane.QUESTION_MESSAGE));

					boolean luxurious = Boolean.parseBoolean(JOptionPane.showInputDialog(null,
							"Will the seat be luxurious (true or false)", JOptionPane.QUESTION_MESSAGE));

					Room found = workspace.existingRoom(roomName);

					found.addRow(capacity, luxurious);

				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(null, "There was no room found with the given name", null,
							JOptionPane.ERROR_MESSAGE, null);
					return;
				}
			}

		});

		createShowMenu();

		createScheduleMenu();

		setJMenuBar(menubar);

		pack();
		setVisible(true);
	}

	/**
	 * Implements the menu "Shows" and its submenus.
	 * 
	 */
	private void createShowMenu() {

		shows = new JMenu("Shows");
		JMenu movies = new JMenu("Movies");
		JMenu plays = new JMenu("Plays");

		shows.add(movies);
		shows.add(plays);
		shows.addSeparator();
		menubar.add(shows);

		JMenuItem addMovie = new JMenuItem("Create Movie");
		JMenuItem addPlay = new JMenuItem("Create Play");
		JMenuItem deleteShow = new JMenuItem("Delete Show");
		JMenuItem infoShow = new JMenuItem("Show Infos");
		movies.add(addMovie);
		plays.add(addPlay);
		shows.add(deleteShow);
		shows.add(infoShow);
		addMovie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					addShow(e.getActionCommand());
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		addPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					addShow(e.getActionCommand());
				} catch (HeadlessException | CloneNotSupportedException ex) {

				}
			}
		});

		deleteShow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				deleteShow();
			}
		});

		infoShow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				infoShow();

			}
		});

	}

	private void infoShow() {

		String roomName;
		String showName;
		try {
			do {
				showName = JOptionPane.showInputDialog(null, "Please give the show's name (Press enter to continue)",
						JOptionPane.QUESTION_MESSAGE);
			} while (showName.trim().equals(""));

			do {
				roomName = JOptionPane.showInputDialog(null,
						"Please give the name of the room where the show is presented", JOptionPane.QUESTION_MESSAGE);
			} while (roomName.trim().equals(""));

			Room temp = workspace.existingRoom(roomName);
			if (temp != null) {
				Show result = temp.getShow(showName);
				if (result != null) {
					JFrame newFrame = new JFrame();
					newFrame.setTitle("Movie information");
					newFrame.setSize(1000, 1500);
					newFrame.setLocationRelativeTo(null);
					newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					JLabel details = new JLabel("<html>" + result.toString() + "</html>", SwingConstants.CENTER);
					newFrame.add(details, BorderLayout.CENTER);
					newFrame.pack();
					newFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "There is not a show give the given name", null,
							JOptionPane.ERROR_MESSAGE, null);
				}
			} else {
				JOptionPane.showMessageDialog(null, "There is not a room with the given name", null,
						JOptionPane.ERROR_MESSAGE, null);
			}
		} catch (NullPointerException ex) {
		}
	}

	/**
	 * 
	 * Καθορίζει την λειτουργικότητα των αντικειμένων μενού που αφορούν την προσθήκη
	 * θεάματος του μενού "θεάματα" με βάση την κατηγορία του εκάστωτε θεάματος προς
	 * εισαγωγή.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws CloneNotSupportedException
	 * @throws HeadlessException
	 */
	private void addShow(String actionCommand) throws CloneNotSupportedException {

		PlayType genre;
		String showName;
		Room room;
		Show show = null;
		String director;
		String description;
		String date = null;
		String roomName = null;
		double duration;
		String temp;
		ArrayList<String> actors = new ArrayList<>();

		roomName = JOptionPane.showInputDialog(null, "Please give the name of the room where the show will take place",
				JOptionPane.QUESTION_MESSAGE);

		room = workspace.existingRoom(roomName);

		if (room != null) {

			if (actionCommand.contains("Pla")) {
				try {
					showName = JOptionPane.showInputDialog(null, "Please give the name of the play",
							JOptionPane.QUESTION_MESSAGE);

					do {
						temp = JOptionPane.showInputDialog(null,
								"Please give the names of the play's actors (press enter to continue)",
								JOptionPane.QUESTION_MESSAGE);
						actors.add(temp);
					} while (!temp.trim().equals(""));

					director = JOptionPane.showInputDialog(null, "Please give the name of the director",
							JOptionPane.QUESTION_MESSAGE);
					description = JOptionPane.showInputDialog(null, "Please give a short description of the play",
							JOptionPane.QUESTION_MESSAGE);
					genre = PlayType.valueOf(JOptionPane.showInputDialog(null, "Please give the type of the play",
							JOptionPane.QUESTION_MESSAGE));
					duration = Double.parseDouble(JOptionPane.showInputDialog(null,
							"Please give the duration of the play", JOptionPane.QUESTION_MESSAGE));
					show = new Play(showName, description, actors, director, genre, duration);

				} catch (NullPointerException ex) {
					return;
				} catch (IllegalArgumentException ex2) {
					JOptionPane.showMessageDialog(null, "There is no such kind of play!", null,
							JOptionPane.INFORMATION_MESSAGE, null);
					return;
				}

			}

			if (actionCommand.contains("Mov")) {

				try {
					showName = JOptionPane.showInputDialog(null, "Please give the name of the movie",
							JOptionPane.QUESTION_MESSAGE);
					Double movieDuration = Double.parseDouble(JOptionPane.showInputDialog(null,
							"Please give the duration of the movie", JOptionPane.QUESTION_MESSAGE));

					do {
						temp = JOptionPane.showInputDialog(null,
								"Please give the names of the play's actors (press enter to continue)",
								JOptionPane.QUESTION_MESSAGE);
						actors.add(temp);
					} while (!temp.trim().equals(""));

					director = JOptionPane.showInputDialog(null, "Please give the name of the director",
							JOptionPane.QUESTION_MESSAGE);
					description = JOptionPane.showInputDialog(null, "Please give a short description of the play",
							JOptionPane.QUESTION_MESSAGE);
					show = new Movie(showName, description, actors, director, movieDuration);

				} catch (NullPointerException | NumberFormatException ex) {
					return;
				}

			}

			date = JOptionPane.showInputDialog(null, "Please give the date when the show will take place",
					JOptionPane.QUESTION_MESSAGE);

			if (room.addShow(show, date))
				JOptionPane.showMessageDialog(null, "The addition was completed succesfully", null,
						JOptionPane.INFORMATION_MESSAGE, null);
			else
				JOptionPane.showMessageDialog(null,
						"The schedule for the specific date is full or the show has already been added", null,
						JOptionPane.INFORMATION_MESSAGE, null);
		} else {
			JOptionPane.showMessageDialog(null, "The room where the show would be added was not found", null,
					JOptionPane.INFORMATION_MESSAGE, null);
		}

	}

	/**
	 * Καθορίζει την λειτουργικότητα του αντικειμένου μενού που αφορά την διαγραφή
	 * ενός θεάματος του μενού "θεάματα".
	 * 
	 */
	private void deleteShow() {
		try {
			String showName = JOptionPane.showInputDialog(null, "Please give the name of the show to be deleted",
					JOptionPane.QUESTION_MESSAGE);

			String date = JOptionPane.showInputDialog(null, "Please give the day of the show's opening",
					JOptionPane.QUESTION_MESSAGE);

			Room temp = workspace.showBasedSearch(showName, date);

			if (temp != null) {
				temp.deleteShow(showName, date);
			} else {
				JOptionPane.showMessageDialog(null,
						"There is not a show with the given name on the schedule of the given date", null,
						JOptionPane.INFORMATION_MESSAGE, null);
			}
		} catch (NullPointerException ex) {
			return;
		}
	}

	/**
	 * Δημιουργεί , καθορίζει τις λειτουργίες και εμφανίζει το μενού "προγράμματα"
	 * και οτι αυτό περιέχει.
	 * 
	 */
	public void createScheduleMenu() {

		scheduleMenu = new JMenu("Schedule");

		JMenuItem addSchedule = new JMenuItem("Add Schedule");
		JMenuItem deleteSchedule = new JMenuItem("Delete Schedule");
		JMenuItem changeSchedule = new JMenuItem("Change Schedule");
		scheduleMenu.add(addSchedule);
		scheduleMenu.add(deleteSchedule);
		scheduleMenu.addSeparator();
		scheduleMenu.add(changeSchedule);
		menubar.add(scheduleMenu);

		addSchedule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String roomName = JOptionPane.showInputDialog(null,
							"Please give the room's name where the schedule will be added",
							JOptionPane.QUESTION_MESSAGE);
					String date = JOptionPane.showInputDialog(null, "Please give the new schedule's date",
							JOptionPane.QUESTION_MESSAGE);

					Room room = workspace.existingRoom(roomName);

					if (room.addSchedule(date))
						JOptionPane.showMessageDialog(null, "The schedule was added successfully", null,
								JOptionPane.INFORMATION_MESSAGE, null);
					else
						JOptionPane.showMessageDialog(null, "There is already a schedule for the given date", null,
								JOptionPane.INFORMATION_MESSAGE, null);
				} catch (NullPointerException | HeadlessException | CloneNotSupportedException ex) {
					return;
				}

			}
		});

		deleteSchedule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String roomName = JOptionPane.showInputDialog(null,
							"Give the name of the room whose schedule will be deleted", JOptionPane.QUESTION_MESSAGE);
					String date = JOptionPane.showInputDialog(null, "Give the date of the to be deleted schedule",
							JOptionPane.QUESTION_MESSAGE);

					Room room = workspace.existingRoom(roomName);

					if (room.deleteSchedule(date))
						JOptionPane.showMessageDialog(null, "The deletion was completed successfully", null,
								JOptionPane.INFORMATION_MESSAGE, null);
					else
						JOptionPane.showMessageDialog(null, "There is not a registered schedule for the given date",
								null, JOptionPane.INFORMATION_MESSAGE, null);
				} catch (NullPointerException ex) {
					return;
				}

			}
		});

		changeSchedule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String roomName = JOptionPane.showInputDialog(null,
							"Please give the name of the room whose schedule will be converted",
							JOptionPane.QUESTION_MESSAGE);

					Room room = workspace.existingRoom(roomName);
					if (room == null)
						JOptionPane.showMessageDialog(null, "There is not a room with the given name", null,
								JOptionPane.INFORMATION_MESSAGE, null);
					else {
						displayConfigScreen(room);
					}
				} catch (NullPointerException ex) {
					return;
				}

			}
		});

	}

	/**
	 * Δημιουργεί το πλαίσιο (frame) του προγράμματος ενός χώρου και επιτρέπει την
	 * τροποποίηση του.
	 * 
	 * @param room η αίθουσα της οποίας το πρόγραμμα θα τροποποιηθεί.
	 */

	private void displayConfigScreen(Room room) {
		JFrame newframe = new JFrame();

		newframe.setSize(1000, 1500);
		newframe.setLocationRelativeTo(null);
		newframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		LinkedHashMap<String, Schedule> schedules = (LinkedHashMap<String, Schedule>) room.getSchedules();

//το panel αυτο (outer) προστιθεται στο τελος στο frame, οταν θα περιεχει ολα τα υπολοιπα panels (BorderLayout)το καθενα απο τα οποια
//περιεχει την ημερομηνια(label-.NORTH) , το προγραμμα και τα θεαματα(panel σε gridlayout(0,2) με το προγραμμα να αποτελειται απο διαδοχικα labels 
// (durations) και τα θεαματα (buttons)) , βρισκοται στο .SOUTH των panels που προσθετονται στο outer).

		JPanel outer = new JPanel(new GridLayout(0, 5, 0, 0));

		for (String date : schedules.keySet()) {

			JPanel temp = new JPanel(new BorderLayout());
			JLabel label = new JLabel(date);
			label.setBorder(BorderFactory.createLineBorder(Color.black));
			temp.add(label, BorderLayout.NORTH);

			JPanel panel = new JPanel(new GridLayout(0, 2, 0, 0));

			Schedule schedule = schedules.get(date);

			for (ScheduleEntry entry : schedule.getEntries()) {
				JLabel duration = new JLabel("<html>" + entry.toString() + "<br>" + "</html>");
				duration.setBorder(BorderFactory.createLineBorder(Color.black));
				panel.add(duration);

				JButton button = new JButton();
				if (entry.getShow() != null)
					button.setText(entry.getShow().getName());
				else
					button.setText("Κενή");

				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						if (entry.getShow() != null) {
							String showName = entry.getShow().getName();
							Object[] options = { "Swap", "Move", "Cancel" };
							int result = JOptionPane.showOptionDialog(null,
									"What do you want to do with the specified schedule?", "Action Question",
									JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
									options[2]);

							if (result == JOptionPane.CANCEL_OPTION) {
								newframe.dispose();

							} else if (result == JOptionPane.YES_OPTION) {

								String nameToSwap = JOptionPane.showInputDialog(null,
										"Please give the name of the show with which will be swapped",
										JOptionPane.QUESTION_MESSAGE);
								if (schedule.swapEntries(showName, nameToSwap)) {
									// καλούμε ξανά την συνάρτηση για να ενημερωθεί το πρόγραμμα της αίθουσας της
									// συγκεκριμένης ημερομηνίας με τις αλλαγές κ κλείνουμε το παλιό παράθυρο.
									newframe.dispose();
									displayConfigScreen(room);
								} else
									JOptionPane.showMessageDialog(null, "There is no show with the given name", null,
											JOptionPane.INFORMATION_MESSAGE, null);

							}

							else if (result == JOptionPane.NO_OPTION) {

								String temp = (String) JOptionPane.showInputDialog(null,
										"How many positions should the entry go forward (+) or backward(-)",
										JOptionPane.QUESTION_MESSAGE);
								int offset = Integer.parseInt(temp);

								if (schedule.moveEntry(e.getActionCommand(), offset)) {
									// καλούμε ξανά την συνάρτηση για να ενημερωθεί το πρόγραμμα της αίθουσας της
									// συγκεκριμένης ημερομηνίας με τις αλλαγές κ κλείνουμε το παλιό παράθυρο.
									newframe.dispose();
									displayConfigScreen(room);

								} else
									JOptionPane.showMessageDialog(null,
											"The number of positions is out of range \n or there is already a show in the to be placed entry",
											null, JOptionPane.INFORMATION_MESSAGE, null);
							}

						} else
							JOptionPane.showMessageDialog(null, "No show has been registered yet", "Title",
									JOptionPane.INFORMATION_MESSAGE);

					}
				});

				panel.add(button);

				panel.setBorder(BorderFactory.createLineBorder(Color.black));

			}

			Icon icon;
			URL imageURL = getClass().getResource("images/calendar.jpg");
			JLabel forimage = new JLabel();
			if (imageURL != null) {
				icon = new ImageIcon(imageURL);
				forimage.setIcon(icon);
			}

			temp.add(forimage, BorderLayout.CENTER);

			temp.add(panel, BorderLayout.SOUTH);

			temp.setBorder(BorderFactory.createLineBorder(Color.black));

			outer.add(temp);

		}

		newframe.add(outer);
		newframe.pack();
		newframe.setVisible(true);

	}

}
