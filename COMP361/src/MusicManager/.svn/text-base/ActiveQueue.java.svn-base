package MusicManager;

import globalAccess.Global;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import jgroove.StreamingManager;

import org.joda.time.DateTime;

import MusicManager.Song.TrackType;
import SongGenerator.SimpleGenerator;
import SongGenerator.SongGenerator;


/**
 * @author Rebecca
 * The ActiveQueue contains the list of the songs which are currently
 * on the list to be played, and runs independently and concurrently with
 * the MusicManager.
 */
public class ActiveQueue extends MusicList implements Runnable {

	/** Minimum number of songs that must be on the queue */
	final private int MIN;
	
	/** The music player */
	private MusicPlayer mPlayer;
	
	/** The Music Manager, for stack access */
	private MusicManager mManager;

	/** For player thread control */
	private boolean mActivated;
	
	/** The range constraint */
	final private int RANGE = 3;
	
	/** The song generator */
	private SongGenerator mSongGen;
	
	/** The default number of songs requested for a fill */
	final private int DEFAULT_FILL = 5;
	
	/** The handle on the watcher thread */
	final AtomicBoolean mWatcher = new AtomicBoolean(true);
	
	/** For pausing. */
	private boolean mPaused;
	/** For playing streamed songs */
	private StreamingManager mStream;

	
	/**
	 * Constructor creates a new thread with the given minimum.
	 * @param min the minimum number of songs that must be on the queue.
	 */
	public ActiveQueue(int min, MusicManager m) {
		MIN = min;
		mActivated = true;
		mPlayer = new MusicPlayer();
		mManager = m;
		mSongGen = new SimpleGenerator(mManager);
		mPaused = false;
		startWatcher();
	}

	/**
	 * Spawns the anonymous thread which stares at the Music Manager Request stack,
	 * watching for the stack to be not-null. Takes the first element off it to be
	 * handled by the queue, waits for this to terminate, then continues watching...
	 */
	public void startWatcher() {
		Thread watcher = new Thread(new Runnable() {
			private boolean temp;
			@Override
			public void run() {
				System.out.println("Watching for requests...");
					do {
						Request r = mManager.getRequest();
						if(r != null) {
							// Add it
							temp = addSong(r);
							if(temp) {
								// It was added... now what.
							}
						}
					} while(mWatcher.get());
					// Terminates
				}
			});
		
		watcher.start();
	}
	
	/** 
	 * Terminate watcher thread. It will stop after whatever check or add it is doing terminates.
	 */
	public void terminateWatcher() {
		mWatcher.set(false);
	}
	
	/**
	 * Attempts to add the given approved song to the list, satisfying
	 * the time constraints and respecting the pre-existing songs.
	 * @param s the Song to add
	 * @return boolean whether the add was successful or not
	 */
	@Override
	public synchronized boolean addSong(Request r) {
		boolean timeRequested = false;
		Song reqSong;
		
		if(r == null) {
			//System.err.println("Invalid request.");
			return false;
		} else {
			if(r.getSong() == null) {
				//System.err.println("Invalid song.");
				return false;
			} else {
				reqSong = r.getSong();
			}
		}
		// Default - last index is the worst case
		int bestIndex = getListLength();
		int reqHr = -1;
		int reqTime = -1;
		
		try {
			reqHr = r.getHour();
			reqTime = r.getMin();
			
			if(reqHr == -1 && reqTime == -1) {
				DateTime now = new DateTime();
				reqHr = now.getHourOfDay();
				reqTime = now.getMinuteOfHour();
			}
			
			if(reqHr >= 0 && reqTime >= 0) {
				timeRequested = true;
				reqTime += reqHr*60;
			}
			
		} catch(Exception e) {
			//System.err.println("Request times not accessible.");
			// timeReq remains false
		}
		
		Song temp;
		int reqSongLength = 0;
		
		try {
			reqSongLength = reqSong.getSongLength();
		} catch(Exception e) { 
			// Ignore the song length
			reqSongLength = -1;
		}
		// Current time.
		DateTime date = new DateTime();
		int hour = date.getHourOfDay();
		int currTime = date.getMinuteOfHour() + hour*60;
		


		
		int diff = 0;
		int prevDiff;
		boolean inserted = false;
		int listSize = songs.size();		
		diff = reqTime - currTime;
		prevDiff = reqTime;

		DateTime ptr = new DateTime();
		int ptrM = ptr.getMinuteOfHour();
		int ptrH = ptr.getHourOfDay();
		int ptrT = ptrH*60 + ptrM;
		
		for(int i = 2; i < listSize; i++) {
			temp = songs.get(i);
			// Start examining with the 3rd song in the queue.
			diff -= temp.getSongLength();
			//System.out.println("The current difference is: " + diff + ", iteration " + i);
			ptrT += temp.getSongLength();
			
			if(temp.equals(reqSong)) {
				if(timeRequested) { // Check the constraint 
					if(Math.abs(currTime - ptrT) <= mManager.getSongReplayGap()) {
						System.out.println("The gap is " + mManager.getSongReplayGap() +
									" and song time requested is too close to the current time.");
						reqTime = currTime + mManager.getSongReplayGap(); // Force constraint to be met.
					}
				}
			}
			
			if(!temp.isUserRequested()) {
				if(!timeRequested) {
					// Insert it at earliest possible time while keeping length constraint.
					if((Math.abs(temp.getMin() - reqSongLength) < RANGE)) {
						songs.remove(i);
						songs.add(i, reqSong);
						inserted = true;
						break;
					}
				} else {
					// There's a time requested; find the minimum difference.
					if(diff <= 0) {
						//System.out.println("Overshot at index: " + i);
						// Overshot and new difference is better.
						if(Math.min(Math.abs(prevDiff),  Math.abs(diff)) == diff) {
							// Insert it if time constraint met.
							bestIndex = i;
						} else {
							// Best choice was the last one.
							// Earliest possible switch is with 2nd song on queue.
							bestIndex = i-1;
						}
						// Try to switch it now with a non-user requested song of comparable length.
						if((Math.abs(temp.getMin() - reqSongLength) < RANGE)) {
							songs.remove(bestIndex);
							songs.add(bestIndex, reqSong);
							//System.out.println("Replacing a non-requested song w/ " +
								//	"a requested song w/ time request.");
							inserted = true;
							break;
						} 
					}
				}
			}
			prevDiff = diff;
			// We ignore all user-requested songs - giving time priority.
		}
		if(!inserted) {
			//System.out.println("Couldn't insert it; adding to end");
			if(diff > 0) {
				if(diff > RANGE) {
					//System.out.println("Need filler.");
					Collection<Song> filler = fillGap(diff);
					if(filler != null) {
						songs.addAll(filler);
					}
				}
			}
			songs.addLast(reqSong);
		}
		
		// Repaint Q to show new addition.
		if(mManager.getmFrame() != null) {
			if(mManager.getmFrame().getListViewStatus() == 0) {
				try {
					mManager.getmFrame().repaintListArea(0);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

		return true;
	}
	
	/**
	 * Adds three more songs to the queue from the AI Generator.
	 */
	public synchronized boolean generateSongs() {
		
		Collection<Song> coll = mSongGen.getSongs(DEFAULT_FILL);
		if(coll != null) {
			songs.addAll(coll);
			return true;
		}
		
		return false;
	}
	
	public void setQueueList(LinkedList<Song> s) {
		if(s.size() > 0) {
			songs.clear();
			songs = s;	
		}
	}
	
	/**
	 * Iterate through list; for every non-user requested song that is not playing at
	 * a correct time, calculate the minutes of song needed to fill the gap and request
	 * a list of songs of that length.
	 */
	public Collection<Song> fillGap(int n) {
		Collection<Song> filler = mSongGen.getSongs(n, RANGE);
		
		LinkedList<Song> test = new LinkedList<Song>();
		test.addAll(filler);
		
		return filler;
	}

	public void setActivated(boolean activated) {
		this.mActivated = activated;
	}
	
	public boolean isActivated() {
		return mActivated;
	}
	
	/**
	 * Cleans up all child threads and returns.
	 * @throws InterruptedException 
	 */
	public synchronized void terminate() {
		if(!mPaused) {
			mPlayer.setIsPlaying(false);
			mPlayer.close();
		}
		mPaused = false;
		mPlayer = null;
		mActivated = false;
		terminateWatcher();
		System.out.println("Returning the ActiveQueue thread.");
		
		return;
	}
	
	public MusicPlayer getPlayer() {
		return mPlayer;
	}
	
	/**
	 * Removes the first song on the queue.
	 */
	public synchronized void removeFirst() {
		songs.removeFirst();
	}
	
	/**
	 * Pauses the player but saves queue state.
	 */
	public void pause() {
		if(mPlayer.getIsPlaying()) {
			mPlayer.mContinue.set(false);
			mPlayer.close();
			mPlayer = null;
			mActivated = false;
			// Watcher remains active on pause.
			System.out.println("Pausing the ActiveQueue thread.");
			mPaused = true;
		}
	}
	
	@Override
	public void run() {
		mActivated = true;
		mPaused = false;
		mPlayer = new MusicPlayer();
		mPlayer.setIsPlaying(true);
		mPlayer.mContinue.set(true);

		do {

			printList();
			if(songs.size() >= 0) {	
				
 				Song s = songs.getFirst();
				
				try {
					if(mManager.getmFrame() != null) {
						mManager.getmFrame().repaintSongInfo(s.printSong());
						// If currently viewing the queue, repaint it
						if(mManager.getmFrame().getListViewStatus() == 0) {
							mManager.getmFrame().repaintListArea(0);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				if(s.getTrackType() == TrackType.local) {
					mPlayer.play(s.getSrc());
					updateSong(s);
				} else { // It is a streamed song
					try {
						mStream = new StreamingManager(mManager);
						mStream.play(s, mPlayer);
						updateSong(s);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				return;
			}
			// If song number has fallen below MIN, generate more
			if(getListLength() < MIN) {
				generateSongs();
			}
			
			if(!mPaused) {
				if(mPlayer != null) {
					mPlayer.setIsPlaying(false);
				}
				// De-queue the song that terminated or was aborted
				songs.removeFirst();	
			}
			
		} while(mActivated);
	}
	
	/** Updates song stuff */
	@SuppressWarnings({"static-access" })
	private void updateSong(Song s) {
		s.incrementPlayCount();
		s.setLastPlay(new DateTime());
		System.out.println("Marking song stats.");
		// Save updated song state in the database.
		try {
			Global.getSRVInstance().SongService().updateRequestCount(s.getId());
			Global.getSRVInstance().SongService().updateLastPlayed(s.getId());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
