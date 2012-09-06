package MusicManager;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Rebecca
 * A list of songs for the user-selected play list.
 */
public class Playlist extends MusicList {

	/** id of the Playlist */
	protected int mId;
	
	/**
	 * Add the given Song to the end of the list.
	 * @param s the Song to add
	 */
	@Override
	// TODO Delete this version of the method.
	public boolean addSong(Request r) {
		
		if(r == null || r.getSong() == null) {
			return false;
		}
		
		songs.addLast(r.getSong());
		int secs = songs.getLast().getSec();
		setTotalMin(getTotalMin() + songs.getLast().getMin() + (int) Math.floor(secs/60));
		setTotalSec(getTotalSec() + secs%60);
		
		//System.out.println("Added song " + songs.getLast().getTitle());
		//System.out.println("Playlist length: " + getListLength());
		//Always returns true since no constraints on Play list length.
		return true; 
	}
	
	/**
	 * Add a list of songs.
	 */
	public void addAll(LinkedList<Song> s) {
		songs.addAll(s);
	}
	
	/**
	 * Add the given Song to the end of the list
	 * @param s the Song
	 */
	public boolean addSong(Song s) {
		
		if(s.getSrc() == null) {
			System.out.println("Song path error. Not adding.");
			return false;
		}
		// Everything on playlist is by default not requested.
		s.setUserRequested(false);
		songs.addLast(s);
		setTotalMin(getTotalMin() + s.getMin());
		setTotalSec(getTotalSec() + s.getSec());
		
		return true;
	}
	
	/**
	 * Returns the first n songs from the playlist.
	 * For testing of ActiveQueue.
	 * @return Collection<Song> the songs
	 */
	public Collection<Song> getFromPlaylist(int n) {
		if(n <= 0) {
			return null;
		} else {
			Collection<Song> coll = new LinkedList<Song>();
			for(int i = 0; i < n; i++) {
				coll.add(songs.get(i));
			}
			return coll;
		}
	}
	
	/** 
	 * Set the playlist name.
	 * @param name the name
	 */
	public boolean setName(String name) {
		
		// TODO Check that it is unique
		this.mName = name;
		
		return true;
	}
	

	/** 
	 * Set the playlist id.
	 * @param id the id
	 */
	public boolean setId(int id) {
		
		// TODO Check that it is unique
		this.mId = id;
		
		return true;
	}

	/** 
	 * Gets the playlist name
	 * @return String the name
	 */
	public String getName() {
		return mName;
	}
	

	

}