package MusicManager;

import java.util.LinkedList;

/**
 * @author Rebecca
 * An interface for any list of music, to be implemented by the ActiveQueue
 * and the Playlist.
 */
public abstract class MusicList {

	/** The linked list of songs in the list */
	protected LinkedList<Song> songs;
	
	/** The total duration of all the songs in the list */
	protected int totalMin;
	protected int totalSec;
	
	/** Name of the list; for Playlist */
	protected String mName;
	
	/** Number of songs in the list */
	protected int listLength;
	
	/**
	 * Constructor creates an empty MusicList.
	 */
	public MusicList() {
		songs = new LinkedList<Song>();
		listLength = 0;
		
		mName = null;
		
		totalMin = 0;
		totalSec = 0;
	}
	
	/**
	 * Adds a song from a request to the list.
	 * @param r the Request
	 * @return true if add was successful; false otherwise.
	 */
	public abstract boolean addSong(Request r);
	
	/**
	 * Determines whether the list contains a given Song
	 * @return boolean true it contains the song; false otherwise.
	 */
	public boolean contains(Song s) {
		if(songs.contains(s)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Removes the given Song from the list.
	 */
	public void remove(Song s) {
		if(songs.contains(s)) {
			songs.remove(s);
		} else {
			//Given song not found in the list.
			System.out.println("Error: Song cannot be found.");
		}
	}

	/**
	 * Returns the id of the song at the given index
	 * @param index of the song
	 * @return the id of the song
	 */
	public int getSongID(int index) {
		return songs.get(index).getId();
	}
	
	public LinkedList<Song> getSongs() {
		return songs;
	}

	public void setSongs(LinkedList<Song> songs) {

		this.songs = songs;
		// TODO What about the length?
	}

	/**
	 * Prints contents of the list.
	 */
	public void printList() {
		int size = songs.size();
		System.out.println("List size is: " + size);
		Song temp;
		System.out.println("------------------");
		System.out.println("The list contains:");
		System.out.println("");
		
		for(int i = 0; i < size; i++) {
			temp = songs.get(i);
			System.out.println(temp.printSong());
		}
		
		System.out.println("----------------");
	}
	
	public int getListLength() {
		listLength = songs.size();
		return listLength;
	}
	
	public int getTotalMin() {
		return totalMin;
	}
	
	public void setTotalMin(int totalMin) {
		this.totalMin = totalMin;
	}
	
	public int getTotalSec() {
		return totalSec;
	}
	
	public void setTotalSec(int totalSec) {
		this.totalSec = totalSec;
	}
}
