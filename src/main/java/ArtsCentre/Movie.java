
package ArtsCentre;

import java.io.Serializable;
import java.util.List;

public class Movie extends Show implements Serializable {

	private static final long serialVersionUID = -8489722943539979986L;

	/**
	 * @param name        movie's name.
	 * @param description a brief description of the movie.
	 * @param actors      a list containing the actors' names.
	 * @param director    director's name.
	 * @param duration    movie's duration.
	 */
	public Movie(String name, String description, List<String> actors, String director, double duration) {
		super(name, description, actors, director, duration);
	}

	@Override
	public String toString() {
		String text = super.toString();
		text += "Duration : " + duration + "<br>" + "<br>";
		text += "Show type : Movie";
		return text;
	}

	/**
	 * Returns the additional ticket's cost depending on the movie's genre.
	 * 
	 * @return the ticket's additional cost.
	 */
	@Override
	public double getPrice() {
		return 0;
	}

}
