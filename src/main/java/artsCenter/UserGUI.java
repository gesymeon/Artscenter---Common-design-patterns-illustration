
package artsCenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UserGUI extends JFrame {

	private EndUser user;
	private JMenu file;

	public static void main(String[] args) throws ClassNotFoundException, IOException {

		try {
			Artspace.getInstance();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "The artspace must have already been created by the administrator",
					null, JOptionPane.ERROR_MESSAGE, null);
		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Missing data from artspace's class in .jar file", null,
					JOptionPane.ERROR_MESSAGE, null);
		}

		String name = JOptionPane.showInputDialog("Username: ");
		while (name.equals(""))
			name = JOptionPane.showInputDialog("It is mandatory...");

		Object[] options = { "Yes", "'No", "Cancel" };
		int result = JOptionPane.showOptionDialog(null, "Do you have an account;", "Basic question",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

		if (result == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		} else if (result == JOptionPane.YES_OPTION) {

			try {
				EndUser temp = EndUser.searchUser(name);
				if (temp != null)
					new UserGUI(temp);
				else {

					JOptionPane.showMessageDialog(null, "The given username does not exist", null,
							JOptionPane.ERROR_MESSAGE, null);
					System.exit(0);
				}
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Input error while reading data from disk", null,
						JOptionPane.ERROR_MESSAGE, null);
			} catch (ClassNotFoundException ex) {
				JOptionPane.showMessageDialog(null, "Missing data from .jar file about the application's classes", null,
						JOptionPane.ERROR_MESSAGE, null);
			}

		} else if (result == JOptionPane.NO_OPTION) {

			try {
				if (EndUser.searchUser(name) != null) {
					JOptionPane.showMessageDialog(null, "This username is already being used!", null,
							JOptionPane.ERROR_MESSAGE, null);
					System.exit(ERROR);
				} else {// TODO: regex to have a valid phone number format
					String phone = JOptionPane.showInputDialog("Input your phone number : ");
					new UserGUI(new EndUser(name, phone));
				}
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Input error while reading data from disk", null,
						JOptionPane.ERROR_MESSAGE, null);
			}
		}

	}

	/**
	 * 
	 * Creates and shows the main windows and menus of the GUI.
	 * 
	 * @param user the current application's user
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	public UserGUI(EndUser user) throws ClassNotFoundException, IOException {

		this.user = user;
		setTitle("User GUI");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		// in one screen configuration probably comment in
		// frame.setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menubar = new JMenuBar();

		file = new JMenu("File");

		JMenuItem save = new JMenuItem("Save user data");

		file.add(save);

		save.addActionListener(e -> {
			try {
				user.saveAccount();
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "An error occurred while writing data to disk", null,
						JOptionPane.ERROR_MESSAGE, null);
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 5, 10, 5));
		for (int i = 0; i < Artspace.getInstance().getRooms().size(); i++) {

			Room currentRoom = Artspace.getInstance().getRooms().get(i);

			JButton button = new JButton();

			button.addActionListener(e -> displaySchedule(currentRoom));

			JPanel temp = new JPanel(new GridLayout(2, 1, 5, 5));
			JLabel label = new JLabel("<html>" + Artspace.getInstance().getRooms().get(i).toString() + "</html>",
					SwingConstants.CENTER);

			URL imageURL = UserGUI.class.getClassLoader().getResource("images/" + currentRoom.getRoomType() + ".jpg");
			Icon icon = new ImageIcon(imageURL);
			button.setIcon(icon);

			temp.add(label);
			temp.add(button);
			temp.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(temp);
		}

		menubar.add(file);
		add(menubar, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);

		pack();
		setVisible(true);
	}

	/**
	 * Displays the schedule of the room.
	 * 
	 * @param room the room whose schedule will be displayed.
	 */
	private void displaySchedule(Room room) {

		JFrame frame = new JFrame(room.getName() + "'s schedule");
		frame.setSize(1000, 1000);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		LinkedHashMap<String, Schedule> schedules = (LinkedHashMap<String, Schedule>) room.getSchedules();

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
					button.setText("Empty");

				button.addActionListener(e -> {
					if (entry.getShow() != null)
						displayRoom(entry);
					else
						JOptionPane.showMessageDialog(null, "No show has been registered", "Title",
								JOptionPane.INFORMATION_MESSAGE);
				});

				panel.add(button);
				panel.setBorder(BorderFactory.createLineBorder(Color.black));

			}
			URL imageURL = UserGUI.class.getClassLoader().getResource("images/Calendar.jpg");
			Icon icon = new ImageIcon(imageURL);
			temp.add(new JLabel(icon), BorderLayout.CENTER);
			temp.add(panel, BorderLayout.SOUTH);

			temp.setBorder(BorderFactory.createLineBorder(Color.black));

			outer.add(temp);

		}

		frame.add(outer);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Shows the room where the entry's show will be performed.
	 * 
	 * @param room The room where the show will be performed.
	 */
	private void displayRoom(ScheduleEntry entry) {

		JFrame frame = new JFrame(entry.getShow().getName() + "'s Room");
		frame.setSize(1000, 1500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ArrayList<Row> rows = (ArrayList<Row>) entry.getRows();
		int maxSeats = entry.maximumSeats();
		GridLayout grid = new GridLayout(0, maxSeats, 5, 5);
		JPanel inner = new JPanel(grid);

		for (Row row : rows) {
			JButton button;
			for (int k = 1; k <= maxSeats; k++) {
				if (row.getSeat(k) != null) {
					button = new JButton(row.getSeat(k).toString());

					// we create temp so we can reference it as an effectively final variable from
					// within the anonymous inner class of button's actionListener.
					// using k to reference a seat within the actionListener would result to a
					// compile error, and this here is probably the simplest bypass around it.
					Seat temp = row.getSeat(k);
					if (temp.isLuxurious())
						button.setBackground(Color.pink);
					if (user.containsReservation(entry.getShow(), temp))
						button.setBackground(Color.green);
					else if (temp.isReserved())
						button.setBackground(Color.red);

					button.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int result;
							JButton button = (JButton) e.getSource();

							if (user.containsReservation(entry.getShow(), temp)) {
								result = JOptionPane.showConfirmDialog(null, "Do you want to cancel your reservation;",
										"Cancel reservation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
								if (result == JOptionPane.YES_OPTION) {
									user.cancelReservation(entry.getShow(), temp);
									button.setBackground(Color.magenta);
									try {
										Artspace.saveArtspace();
									} catch (IOException ex) {
										System.out.println("toLogger");
									}
								}
								return;
							}

							else if (temp.isReserved()) {
								JOptionPane.showMessageDialog(null, "This seat is already reserved", null,
										JOptionPane.ERROR_MESSAGE, null);
								return;
							}

							result = JOptionPane.showConfirmDialog(null, "Do you want to reserve this seat;", "Title",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

							if (result == JOptionPane.YES_OPTION) {

								boolean succeed = user.makeReservation(entry.getShow(), temp);
								if (succeed) {
									JOptionPane.showMessageDialog(null, "Your reservation is now registered", "Title",
											JOptionPane.INFORMATION_MESSAGE);
									JOptionPane.showMessageDialog(null, "The seat costs: "
											+ (temp.getPrice() + entry.getShow().getPrice()) + " euros.", "Title",
											JOptionPane.INFORMATION_MESSAGE);
									button.setBackground(Color.green);
									try {
										Artspace.saveArtspace();
									} catch (IOException ex) {
										JOptionPane.showMessageDialog(null,
												"An error occured during the new seat registration", "Title",
												JOptionPane.ERROR_MESSAGE);
									}

								} else {
									JOptionPane.showMessageDialog(null,
											"An error occured during the new seat registration", "Title",
											JOptionPane.INFORMATION_MESSAGE);
								}

							}

						}
					});
				} else {
					button = new JButton();
					button.addActionListener(e -> {
						JOptionPane.showMessageDialog(null, "The seat is not available", "Title",
								JOptionPane.INFORMATION_MESSAGE);
					});
				}
				inner.add(button);
			}

		}
		JLabel label = new JLabel();
		URL imageURL = UserGUI.class.getClassLoader()
				.getResource("images/" + entry.getShow().getClass().getSimpleName() + ".jpg");

		final Icon icon = new ImageIcon(
				new ImageIcon(imageURL).getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT));
		label.setIcon(icon);

		label.setFont(new Font("Serif", Font.PLAIN, 30));
		label.setForeground(new Color(0x000000));

		label.addMouseListener(new MouseAdapter() {
			boolean clicked = false;

			@Override
			public void mouseEntered(MouseEvent e) {
				label.setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				label.setBorder(BorderFactory.createLineBorder(Color.black));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				label.setText(entry.getShow().toString());
				if (clicked) {
					label.setIcon(icon);
				} else {
					label.setIcon(null);
				}
				clicked = !clicked;
			}
		});

		frame.add(label, BorderLayout.NORTH);
		frame.add(inner, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

	}
}