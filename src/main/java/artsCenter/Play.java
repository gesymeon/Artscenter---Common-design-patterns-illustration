
package artsCenter;

import java.io.Serializable;
import java.util.List;

public class Play extends Show implements Serializable {

	private static final long serialVersionUID = -5924551368622889979L;
	private PlayType genre;

	/**
	 * @param name        the play's name.
	 * @param description brief description of the play.
	 * @param actors      list containing the name of the actors in the play.
	 * @param director    director's name.
	 * @param genre       the genre's name.
	 * @param duration    the play's duration.
	 */
	public Play(String name, String description, List<String> actors, String director, PlayType genre,
			double duration) {
		super(name, description, actors, director, duration);
		this.genre = genre;
	}

	@Override
	public String toString() {
		StringBuilder builder = super.toStringBuilder();
		builder.append("Show type : Play<br>Play's Genre: " + genre + "</html>");
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		Play pobj = (Play) obj;
		return genre == pobj.getGenre();

	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = 89 * hash + (genre != null ? genre.hashCode() : 0);
		return hash;
	}

	public PlayType getGenre() {
		return genre;
	}

	/**
	 * Returns the additional ticket's cost depending on the play's genre.
	 * 
	 * @return the ticket's additional cost.
	 */
	@Override
	public double getPrice() {

		if (genre == PlayType.Comedy)
			return 10;
		else if (genre == PlayType.Tragedy)
			return 20;
		else
			return 30;

	}

}
