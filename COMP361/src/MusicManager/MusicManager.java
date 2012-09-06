package MusicManager;

import globalAccess.Global;

import java.awt.Cursor;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import ManagerUI.MainFrame;

/**
 * @author Rebecca
 * This class represents the manager of the ActiveQueue and the
 * hub of the program activities.
 */
public class MusicManager implements Serializable {

	/** Generated id */
	private static final long serialVersionUID = -2996500455487861644L;

	/** The current Active Queue thread */
	private transient ActiveQueue mQueue;
	
	/** The current selected play list */
	private transient Playlist mPList;
	
	/** The requests received by the Request Handler 
	 * in format Song, hour, minute, second */
	private transient Queue<Request> mRequests;
	
	/** Whether system is live or not */
	private transient boolean mIsLive;
	
	/** Don't let the user set this to some invalid value! */
	final int MINSONGS = 5;
	
	/** For testing; this should actually be 20+ */
	private int mMinListLength = 10;
	
	/** @deprecated The maximum number of times a song may be played per session. */
	/** Update: The min number of minutes that must pass between song replays */
	private int mSongReplayGap = 3;
	
	/** Is streaming enabled */
	private boolean mStreamEnabled;
	
	/** State of ActiveQueue */
	private boolean mQueueIsPaused;
	
	/** The MainFrame */
	private transient MainFrame mFrame;
	
	/** Name of last playlist, if any */
	private String mPlaylistName;
	
	/**
	 * Constructor creates a Music Manager for the user session.
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public MusicManager(boolean gui) throws Exception {

		setmQueueIsPaused(false);
		mStreamEnabled = false;
		// Default ActiveQueue is null until system goes live.
		mQueue = null;
		
		// Always is empty at initialization.
		mRequests = new LinkedList<Request>();
		mIsLive = false;
		mPlaylistName = null;
		UIManager uim = new UIManager();
		// Try loading the look and feel
		try {
		    for (LookAndFeelInfo info : uim.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            uim.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) { }

		// Load the past session settings.
		loadAll();
		
		if(mPlaylistName != null) {
			// Load playlist the last session's playlist from database.
			LinkedList<Song> pSongs = new LinkedList<Song>();
			try {
				int pid = Global.getSRVInstance().PlaylistService().
						GetPlaylistIdByName(mPlaylistName);
				pSongs = (LinkedList<Song>) 
						Global.getSRVInstance().SongService().getSongsByPlaylistId(pid);
				mPList = new Playlist();
				mPList.setId(pid);
				mPList.setSongs(pSongs);
				mPList.setName(mPlaylistName);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			
		} else {
			mPList = new Playlist();
		}
		
		if(gui) {
			mFrame = new MainFrame(this);
			changePlaylist(mPList);
		} else {
			mFrame = null;
		}

	}
	
	// TODO Make a constructor for testing with everything given.
	
	/**
	 * Loads a playlist from the database.
	 * @param the Playlist object with the ID and name that we want to add songs to.
	 */
	@SuppressWarnings("static-access")
	public synchronized void addSongsToPlaylist(Playlist selected, LinkedList<Song> songs) {
    	int pid;
		LinkedList<Song> pSongs = new LinkedList<Song>();
		try {
        	// Load the existing playlist from the selected id.
			pid = Global.getSRVInstance().PlaylistService().GetPlaylistIdByName(selected.getName());
        	
			pSongs = (LinkedList<Song>) 
					Global.getSRVInstance().SongService().getSongsByPlaylistId(pid);
			selected.setSongs(pSongs);
			// Now adding the songs into both the database and the playlist instance.
        	for(int i = 0; i < songs.size(); i++) {
        		Global.getSRVInstance().PlaylistService().addSongToPlaylist(pid, songs.get(i).getId());
        	}
        	selected.addAll(songs);
        	System.out.println("Now there are " + selected.getSongs().size() + 
        			" songs on the playlist " + selected.getName());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Removes given list of songs from the current playlist
	 * @param selectedSongs the list of songs to remove
	 * @return boolean whether the removal was successful.
	 */
	@SuppressWarnings("static-access")
	public synchronized boolean removeFromSelectedPlaylist(LinkedList<Song> selectedSongs) {
		Song curr;
		for(int i = 0; i < selectedSongs.size(); i++) {
			curr = selectedSongs.get(i);
			if(mPList.contains(curr)) {
				mPList.remove(curr);
			} else {
				return false;
			}
		}
		
		
		try {
			System.out.println("List length: " + mPList.getListLength());
			int[] ids = new int[mPList.getListLength()];
			for(int i = 0; i < mPList.getListLength(); i++) {
				ids[i] = mPList.getSongID(i);
			}
			
			Global.getSRVInstance().PlaylistService().deletePlaylist(
					Global.getSRVInstance().PlaylistService().GetPlaylistIdByName(mPlaylistName));
			Global.getSRVInstance().PlaylistService().insertPlaylist(mPlaylistName);
			
			// Now add updated list of songs to new playlist
			Global.getSRVInstance().PlaylistService().addSongsToPlaylist(
					Global.getSRVInstance().PlaylistService().GetPlaylistIdByName(mPlaylistName), ids);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(mFrame.getListViewStatus() == 1) {
			// Repaint the updated list.
			try {
				if(mFrame != null) {
					mFrame.repaintListArea(1);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	/**
	 * Closes the system - saves the settings and quits.
	 */
	public void closeAll() {
		File session = new File("session.ser");
	      try {
	          FileOutputStream fileOut = new FileOutputStream(session);
	          ObjectOutputStream out = new ObjectOutputStream(fileOut);
	          out.writeObject(this);
	          out.close();
	          fileOut.close();
	       } catch (IOException i) {
	           i.printStackTrace();
	       }
	}
	
	/**
	 * Loads the system.
	 */
	public void loadAll() {
		File session = new File("session.ser");
		if(session.exists()) {
			  try {
		           FileInputStream fileIn = new FileInputStream(session);
		           ObjectInputStream in = new ObjectInputStream(fileIn);
		           MusicManager m = (MusicManager) in.readObject();
		           in.close();
		           fileIn.close();
		           
		           // Load the stuff we want
		           setMinListLength(m.getMinListLength());
		           setSongReplayGap(m.getSongReplayGap());
		           setmStreamEnabled(m.ismStreamEnabled());
		           setmPlaylistName(m.getmPlaylistName());
		       } catch (IOException i) {
		           i.printStackTrace();
		           return;
		       } catch (ClassNotFoundException c) {
		           c.printStackTrace();
		           return;
		       }
		} else {
			System.out.println("no session existed.");
		}
		// If no session was created then doesn't matter. Stick to defaults.
	}
	
	private void setmPlaylistName(String mPlaylistName) {
		this.mPlaylistName = mPlaylistName;
	}

	/**
	 * Starts the system - checks prerequisites and begins playing music.
	 * @throws HeadlessException 
	 * @throws Exception 
	 */
	public synchronized void goLive() throws HeadlessException, Exception {
		// Check the status
		if(status()) {
			// Start the ActiveQueue in its own thread
			if(!ismQueueIsPaused()) {
				mIsLive = true;
				mQueue = new ActiveQueue(MINSONGS, this);
				mQueue.generateSongs();
			} else {
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				waiting(2);
				mFrame.setCursor(Cursor.getDefaultCursor());
				setmQueueIsPaused(false);
			}
			new Thread(mQueue).start();
		}
	}
	
	/** 
	 * Stops the playing thread but saves state for resume - "Pause" behavior.
	 */
	public synchronized void pause() {
		if(mIsLive) {
			if(!mQueueIsPaused) {
				setmQueueIsPaused(true);
				mQueue.pause();
			}
		} else {
			System.out.println("wasn't live so can't pause.");
		}
	}
	
	/*
	 * Skips one.
	 */
	public synchronized void skip() {
		mQueue.removeFirst();
	}
	
	/**
	 * Stops the playing thread instantly.
	 * @throws Exception 
	 */
	public void stopNow() throws Exception {
		if(mIsLive) {
			if(mFrame != null) {
				mFrame.repaintSongInfo(null);
			}
			setmQueueIsPaused(false);
			mIsLive = false;
			mQueue.terminate();
			mQueue = null;
		}
	}
	
	/**
	 * Adds a song request to the stack of requests.
	 * @param s the Song
	 */
	public void addRequest(Song s) {
		s.setUserRequested(true);
		System.out.println("Got request.");
		mRequests.add(new Request(s));
	}
	
	/**
	 * Adds a song request with a specified time to the stack of requests.
	 * @param s the Song
	 * @param hr the hour
	 * @param min the minute
	 */
	public void addRequest(Song s, int hr, int min) {
		s.setUserRequested(true);
		mRequests.add(new Request(s, hr, min));
		System.out.println("Added a request with a time.");
	}
	
	/**
	 * Sees if there is anything on the Request stack. If so, returns the top element.
	 * For use by the watcher thread in the Active Queue.
	 */
	public Request getRequest() {
		
		if(mRequests.peek() == null) {
			return null;
		} else {
			System.out.println("Aha! Found a request.");
			Request tmp = mRequests.remove();
			if(verifyRequest(tmp)) {
				System.out.println("Returning a valid request.");
				return tmp;
			} else {
				// The request was invalid; try getting another one.
				return getRequest();
			}
		}
		
	}
	
	/** 
	 * Prints Requests. For testing/development.
	 */
	public void printRequests() {
		Request temp;
		System.out.println("The requests:");
		for(int i = 0; i < mRequests.size(); i++) {
			temp = mRequests.remove();
			System.out.println(temp.toString());
			mRequests.add(temp);
		}
	}
	
	/**
	 * Verifies whether a Song is a valid request to send to the ActiveQueue.
	 * @return true if the request was valid and sent; false otherwise.
	 */
	public boolean verifyRequest(Request r) {
		// Can the song be added to the ActiveQueue? Check inside song.
		Song s = r.getSong();
		if(s != null) {
			return true; // Further checks within AQ addSong().
		}
		return false;
	}
	
	/**
	 * Changes the current playlist to the given playlist.
	 * @param p Playlist
	 */
	public synchronized void changePlaylist(Playlist newPlaylist) {
		
		if(newPlaylist != null) {
			// This won't be a problem for playlists generated within the program from the user.
			if(newPlaylist.getName() == null) {
				System.out.println("Playlist must have a unique name.");
				return;
			}
			
			mPList = newPlaylist;
			mPlaylistName = mPList.getName();
			try {
				if(mFrame != null) {
					mFrame.repaintListArea(1);
					System.out.println("Repainting the playlist with name: " + newPlaylist.getName());
					mFrame.repaintPlaylistInfo(newPlaylist.getName());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 		
	}
	
	/**
	 * Validates that a playlist with the given name can be created.
	 * @param name the proposed name.
	 */
	@SuppressWarnings("static-access")
	public boolean validatePlaylist(String name) {
		// Check that the name does not already exist in the database.
		boolean valid;
		
		try {
			valid = Global.getSRVInstance().PlaylistService().doesPlaylistExist(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			valid = false;
		} catch (SQLException e) {
			e.printStackTrace();
			valid = false;
		}
		
		// Return the inverse so that true means OK to create, false means cannot.
		return !valid;
	}
	
	/** 
	 * Returns a shallow copy of the play list.
	 * @return Play list
	 */
	public Playlist getPlaylist() {
		/*
		Playlist clone = mPList;
		return clone;
		*/
		return mPList;
	}
	
	/**
	 *  For testing only. This will be changed to private afterwards.
	 */
	public void setActiveQueue(ActiveQueue aq) {
		// TODO verify. This should not happen unless we're playing the playlist.
		mQueue = aq;
	}
	
	/**
	 * Returns a shallow copy of the Active Queue.
	 * @return
	 */
	public ActiveQueue getActiveQueue() {
		ActiveQueue clone = mQueue;
		return clone;
	}
	
	/** 
	 * Determines the status of the MusicManager - whether or not the ActiveQueue
	 * and Playlist satisfy the requirements necessary to begin playing from the Queue.
	 * @return true if valid; false otherwise.
	 * @throws Exception 
	 * @throws HeadlessException 
	 */
	public boolean status() throws HeadlessException, Exception {
		// Check if Playlist is valid.
		if(mPList.getListLength() < mMinListLength) {
			JOptionPane.showMessageDialog(mFrame, Global.getLOCInstance().getLocalizedString("Error.PlaylistLength"));
			return false;
		}
		return true;
	}
	
	/** 
	 * Returns whether the queue is live or not.
	 * @return mIsLive 
	 */
	public boolean isLive() {
		return mIsLive;
	}
	
	/**@deprecated
	 * Returns the value of the MAXSONGREPEAT
	 * @return int
	 */
	public int getMaxSongConstraint() {
		System.err.println("Deprecated. Use getSongReplayGap()");
		return mSongReplayGap;
	}
	
	/** 
	 * Returns the user-specified minimum gap between song replays.
	 */
	public int getSongReplayGap() {
		return mSongReplayGap;
	}
	
	/**
	 * Sets the MINLISTLENGTH
	 */
	public void setMinListLength(int n) {
		mMinListLength = n;
	}
	
	public int getMinListLength() {
		return mMinListLength;
	}
	
	/**@deprecated
	 * Sets MAXSONGREPEAT
	 */
	public void setMaxSongRepeat(int n) {
		mSongReplayGap = n;
	}
	
	/** 
	 * Sets mSongReplayGap
	 * @param integer value
	 */
	public void setSongReplayGap(int n) {
		if(n >= 0) {
			mSongReplayGap = n;
		}
	}

	public MainFrame getmFrame() {
		return mFrame;
	}

	public boolean ismStreamEnabled() {
		return mStreamEnabled;
	}

	public void setmStreamEnabled(boolean mStreamEnabled) {
		this.mStreamEnabled = mStreamEnabled;
	}

	public boolean ismQueueIsPaused() {
		return mQueueIsPaused;
	}

	private void setmQueueIsPaused(boolean mQueueIsPaused) {
		this.mQueueIsPaused = mQueueIsPaused;
	}
	
	public String getmPlaylistName() {
		return mPlaylistName;
	}

	public static void waiting (int n){
        long t0, t1;

        t0 =  System.currentTimeMillis();

        do{
            t1 = System.currentTimeMillis();
        }
        while ((t1 - t0) < (n * 1000));
  }
	
}