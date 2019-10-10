
package ArtsCentre;

/**
 *Υλοποιεί την οντότητα του προγράμματος ενός χώρου.
 * @author George Simeonidis
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Schedule implements Serializable {

	private static final long serialVersionUID = -5537895265539461749L;
	// λίστα η οποία αποθηκεύει τις εγγραφές στο πρόγραμμα.
	private ArrayList<Entry> entries;
	// το μέγιστο πλήθος εγγραφών (παραστάσεων ή ταινιών) που μπορεί να περιέχονται
	// στη συγκεκριμένη ημερομηνία.
	private int maximumEntries;
	// every show has a fixed length
	private static final int INTERVAL = 3;
	// η ώρα που ξεκινάει να λειτουργεί η αίθουσα την συγκεκριμένη ημέρα.
	private int openingTime;
	// η ώρα που σταματάει η λειτουργία της αίθουσας την συγκεκριμένη ημέρα.
	private int closingTime;

	public List<Entry> getEntries() {
		return entries;
	}

	/**
	 * Υπολογίζει την ώρα έναρξης και τερματισμού κάθε καταχώρησης του προγράμματος
	 * με βάση τις ώρες λειτουργίας του χώρου και την προκαθορισμένη διάρκεια κάθε
	 * καταχώρησης(3 ώρες).
	 * 
	 * @author George Simeonidis
	 * @param time_start
	 *            η ώρα έναρξης λειτουργίας του χώρου.
	 * @param time_close
	 *            η ώρα τερματισμού λειτουργίας του χώρου.
	 */
	public Schedule(int timeStart, int timeClose) {
		openingTime = timeStart;
		closingTime = timeClose;

		calculateRequirements(closingTime - openingTime);
		entries = new ArrayList<>();
		createEntries();

	}

	/**
	 * Αρχικοποιεί το πρόγραμμα με κενές καταχωρήσεις σε σωστή χρονική διάταξη.
	 * 
	 * @author George Simeonidis
	 */
	public void createEntries() {
		int temp;
		for (int i = 0; i < maximumEntries; i++) {
			temp = openingTime + entries.size() * INTERVAL;
			Entry entry = new Entry(temp, temp + INTERVAL);
			entries.add(entry);

		}

	}

	/**
	 * Υπολογίζει το πλήθος των καταχωρήσεων του προγράμματος.
	 * 
	 * @author George Simeonidis
	 * @param total
	 *            το χρονικό διάστημα λειτουργίας της αίθουσας.
	 */
	private void calculateRequirements(int total) {

		maximumEntries = total / INTERVAL;

	}

	/**
	 * προσθέτει στο πρόγραμμα ένα νέο θέαμα ξεκινώντας την αναζήτηση κενής
	 * καταχώρησης απο την πρώτη διαθέσιμη.
	 * 
	 * @param show
	 *            το θέαμα προς εισαγωγή
	 * @return true αν βρέθηκε καταχώρηση χωρίς θέαμα στο πρόγραμμα , false
	 *         διαφορετικά.
	 */
	public boolean addEntry(Show show) {
		// ελέγχουμε αν το όνομα του συγκεκριμένου θεάματος υπάρχει ήδη στο προγραμμα.
		if (contains(show.getName()) == null) {
			Iterator<Entry> it = entries.iterator();
			while (it.hasNext()) {
				Entry temp = it.next();
				if (temp.getShow() == null) {
					temp.setShow(show);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Eλέγχει αν υπάρχει στη λίστα καταχωρήσεων του προγράμματος θέαμα με όνομα
	 * ίδιο με την τυπική παράμετρο name.
	 * 
	 * @author George Simeonidis
	 * @param name
	 *            το όνομα του θεάματος.
	 * @return η καταχώρηση που περιέχει θέαμα με το ζητούμενο όνομα , διαφορετικά
	 *         null.
	 */
	public Entry contains(String name) {

		for (Iterator<Entry> it = entries.iterator(); it.hasNext();) {
			Entry e = it.next();
			if (e.getShow() != null && e.getShow().getName().equals(name))
				return e;
		}
		return null;
	}

	/**
	 * Απαλοίφει το θέαμα με όνομα ίδιο με αυτό της τυπικής παραμέτρου απο την
	 * καταχώρηση του προγράμματος.
	 * 
	 * @author George Simeonidis
	 * @param name
	 *            το όνομα του θεάματος.
	 * @return true αν η διαγραφή ολοκληρώθηκε με επιτυχία , false διαφορετικά.
	 */
	public boolean deleteEntry(String name) {
		Entry temp = contains(name);
		if (temp != null) {
			temp.setShow(null);
			return true;
		}
		return false;
	}

	/**
	 * Υποστηρίζει την αντιμετάθεση δυο καταχωρήσεων του προγράμματος με βάση τα
	 * ονόματα των θεαμάτων τους.
	 * 
	 * @author George Simeonidis
	 * @param name1
	 *            το όνομα του πρώτου θεάματος.
	 * @param name2
	 *            το όνομα του δεύτερου θεάματος.
	 * @return true αν υπήρχαν δύο καταχωρήσεις να περιέχουν τα εν λόγω θεάματα
	 *         ,false διαφορετικά.
	 */
	public boolean swapEntries(String name1, String name2) {
		Show temp;

		Entry toSwap1 = contains(name1);
		Entry toSwap2 = contains(name2);

		if (toSwap1 != null && toSwap2 != null) {
			temp = toSwap1.getShow();
			toSwap1.setShow(toSwap2.getShow());
			toSwap2.setShow(temp);
			return true;
		}
		
		return false;
	}

	/**
	 * Υποστηρίζει την μετακίνηση μιας καταχώρησης μέσα στο πρόγραμμα κατα offset
	 * πλήθος θέσεων.
	 * 
	 * @author George Simeonidis
	 * @param name
	 *            το όνομα του θεάματος.
	 * @param offset
	 *            το πλήθος των θέσεων που θα μετακινηθεί.
	 * @return true αν η μετακίνηση επιτεύχθηκε με επιτυχία, false διαφορετικά.
	 */
	public boolean moveEntry(String name, int offset) {
		Entry temp = contains(name);
		if (temp == null)
			return false;
		int nextPosition = entries.indexOf(temp) + offset;

		if (nextPosition < 0 || nextPosition > entries.size() - 1)
			return false;
		else {
			if (entries.get(nextPosition).getShow() != null)
				return false;
			else {
				entries.get(nextPosition).setShow(temp.getShow());
				temp.setShow(null);
				return true;
			}
		}
	}

}
