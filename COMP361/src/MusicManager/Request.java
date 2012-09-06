package MusicManager;

/**
 * @author Rebecca
 * This class represents a song request and the preferred play time, when applicable. 
 */
public class Request {

	/** The song requested */
	private Song song;
	
	/** The time requested */
	private int hour;
	private int min;
	
	/** Constructor creates a new request given a song.
	 * @param s the Song
	 */
	public Request(Song s) {
		song = s;
		hour = -1;
		min = -1;
	}
	
	/** Constructor creates a new request given a song and a requested time.
	 * @param s the Song
	 * @param h the hour
	 * @param m the minute
	 */
	public Request(Song s, int h, int m) {
		this(s);
		hour = h;
		min = m;
	}

	/**
	 * Creates a String representation of the object.
	 * @return String the representation
	 */
	public String toString() {
		String result = "Request for: " + song.getSrc();
		if(hour != -1) {
			result += " at " + hour + " h " + min + ".";
		} else {
			result += " with no time requested.";
		}
		return result;
	}
	
	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}
}
