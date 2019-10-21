
package artsCenter;

import java.io.Serializable;
import java.util.List;

public abstract class Show implements Serializable {

	private static final long serialVersionUID = -2544282961812837315L;
	protected String name;
	protected String description;
	protected List<String> actors;
	protected String director;
	protected double duration;

	// the price is calculated based on the show's type and genre
	protected double price;

	public Show() {
	}

	/**
	 * Initializes the common to its subclasses variables.
	 * 
	 * @param name        show's name.
	 * @param description a brief description of the show.
	 * @param actors      list containing actors' names.
	 * @param director    director's name.
	 */
	public Show(String name, String description, List<String> actors, String director, double duration) {

		this.name = name;
		this.description = description;
		this.actors = actors;
		this.director = director;
		this.duration = duration;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((director == null) ? 0 : director.hashCode());
		long temp;
		temp = Double.doubleToLongBits(duration);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Show)) {
			return false;
		}
		Show other = (Show) obj;
		if (director == null) {
			if (other.director != null) {
				return false;
			}
		} else if (!director.equals(other.director)) {
			return false;
		}
		if (Double.doubleToLongBits(duration) != Double.doubleToLongBits(other.duration)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public String getName() {
		return name;
	}

	public List<String> getActors() {
		return actors;
	}

	public String getDescription() {
		return description;
	}

	public void setPrice(double aPrice) {
		price = aPrice;
	}

	public String getDirector() {
		return director;
	}

	public double getDuration() {
		return duration;
	}

	public abstract double getPrice();

	@Override
	public String toString() {
		String text;
		text = "'Show's name : " + name + "<br>" + "<br>" + "'Director'sn name : " + director + "<br>" + "<br>"
				+ "Brief description : " + description + "<br>" + "<br>" + "Actor names:" + "<br>" + "<br>";
		for (String actor : actors)
			text += actor + "<br>" + "<br>";
		return text;
	}

}
