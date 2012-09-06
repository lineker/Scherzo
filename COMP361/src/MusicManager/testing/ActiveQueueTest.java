package MusicManager.testing;

import static org.junit.Assert.*;

import globalAccess.Global;

import java.io.File;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import MusicManager.ActiveQueue;
import MusicManager.MusicManager;
import MusicManager.Playlist;
import MusicManager.Request;
import MusicManager.Song;

/**
 * @author Rebecca
 *
 */
public class ActiveQueueTest {

	/* The ActiveQueue for testing */
	private ActiveQueue mQueue1;
	private ActiveQueue mQueue2;
	
	/* MusicManager */
	private MusicManager mMan;
	/* The minimum number of songs required on the queue */
	private int MIN = 3;
	/* List of songs */
	private LinkedList<Song> mList;
	
	/**
	 * Set-up.
	 * @throws Exception 
	 */
	@Before
	public void setUp() throws Exception {
		mMan = new MusicManager(false);
		
		File session = new File("session.ser");
		if(session.exists()) {
			// Destroy any saved playlist/active queue settings for next test run
			session.delete();
		}
		
		mQueue1 = new ActiveQueue(MIN, mMan); 
		mQueue2 = new ActiveQueue(MIN, mMan);
		
		// Block any client requests
		mQueue1.terminateWatcher();
		mQueue2.terminateWatcher();
		
		// Insert songs onto the list
		Song s1 = new Song("files/02 We're In The Club Now.mp3");
		Song s2 = new Song("files/03 Married Life.mp3");
		Song s3 = new Song("files/04 Carl Goes Up.mp3");
		Song s4 = new Song("files/05 52 Chachki Pickup.mp3");
		
		mList = new LinkedList<Song>();
		mList.add(s1);
		mList.add(s2);
		mList.add(s3);
		mList.add(s4);
		mQueue1.setSongs(mList);
		mQueue2.setSongs(mList);
	}
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link MusicManager.ActiveQueue#addSong(MusicManager.Request)}.
	 */
	@Test
	public void testAddSongBoundaries() {
		Song testSong1 = new Song("files/01 Up With Titles.mp3");
		Song testSong2 = new Song("files/22 Up With End Credits.mp3");
		DateTime date = new DateTime();
		int hour = date.getHourOfDay();
		int min = date.getMinuteOfHour();
		
		// Boundary case 1: Request r = null;
		assertFalse(mQueue1.addSong(null));
		// Boundary case 2: r != null; r.min < 0, r.hour >= 0
		assertTrue(mQueue1.addSong(new Request(testSong1, hour, min)));
		assertTrue(mQueue1.getSongs().size() == mQueue2.getSongs().size());
		// Boundary case 3: r != null; r.min >= 0, r.hour < 0
		assertTrue(mQueue1.addSong(new Request(testSong2, -1, min)));
		assertTrue(mQueue1.getSongs().size() == mQueue2.getSongs().size());
		// Boundary case 4: r != null; r.song = null;
		assertFalse(mQueue2.addSong(new Request(null, 2, 2)));
	}


	/**
	 * Test method for {@link MusicManager.ActiveQueue#addSong(MusicManager.Request)}.
	 */
	@Test
	public void testAddSongCase1() {
		Song testSong1 = new Song("files/Sleep Away.mp3");
		DateTime date = new DateTime();
		int hour = date.getHourOfDay();
		int min = date.getMinuteOfHour();
		
		// Find time the latest existing song will be played
		int eHour = hour;
		int eMin = min;
		LinkedList<Song> list = mQueue1.getSongs();
		for(int i = 0; i < list.size(); i++) {
			eMin += list.get(i).getSongLength();
		}
		while(eMin >= 60) {
			eHour++;
			eMin -= 60;
		}
		// Insert at a position smaller than that time
		assertTrue(mQueue1.addSong(new Request(testSong1, eHour, eMin - 10)));
		// There should have been a swap assuming normal range of song length
		assertTrue(mQueue1.getSongs().size() == list.size());
		assertTrue(mQueue1.getSongs().contains(testSong1));
	}
	
	/**
	 * Test method for {@link MusicManager.ActiveQueue#addSong(MusicManager.Request)}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testAddSongCase2() throws ClassNotFoundException, SQLException {
		Song testSong1 = new Song("files/Bleed.mp3");
		DateTime date = new DateTime();
		int hour = date.getHourOfDay();
		int min = date.getMinuteOfHour();
		
		int pid = Global.getSRVInstance().PlaylistService().GetPlaylistIdByName("rUp");
		Collection<Song> coll = Global.getSRVInstance().SongService().getSongsByPlaylistId(pid);
		LinkedList<Song> link = new LinkedList<Song>();
		link.addAll(coll);
		Playlist playlist = new Playlist();
		playlist.setSongs(link);
		
		mMan.changePlaylist(playlist);
		mQueue1 = new ActiveQueue(MIN, mMan);
		mQueue1.setSongs(mList);

		// Additions; the length of the song list should be longer after a successful insert
		assertTrue(mQueue1.addSong(new Request(testSong1, hour, min + 40)));
		int newLength = mQueue1.getSongs().size();
		assertTrue(mQueue1.getSongs().get(newLength - 1).equals(testSong1));

		mQueue1.printList();
		System.out.println("New length: " + mQueue1.getSongs().size());
		// Check that insertion was +-3 minutes
		int sum = 0;
		int target = hour*60 + min;
		Song temp;
		LinkedList<Song> songs = mQueue1.getSongs();
		for(int i = 0; i < songs.size(); i++) {
			temp = songs.get(i);
			if(temp == testSong1) {
				System.out.println("Found it.");
				break;
			}
			sum += temp.getSongLength();
		}
		System.out.println("Sum: " + sum);
		System.out.println("Target: " + target);
		assertTrue(Math.abs(sum - target) <= 3); // Depending on sample songs used, this may fail.
		// With this set it always passes. Failing occasionally is OK.
	}
	
	/**
	 * Test method for {@link MusicManager.ActiveQueue#addSong(MusicManager.Request)}.
	 */
	@Test
	public void testAddSongCase3() {
		Song testSong1 = new Song("files/Bird Song.mp3");
		mQueue1 = new ActiveQueue(MIN, mMan);
		mQueue1.setSongs(mQueue2.getSongs());

		// Replacement; the length of the song list should be equal after a successful insert
		assertTrue(mQueue1.addSong(new Request(testSong1, -1, -1)));
		assertTrue(mQueue1.getSongs().size() == mQueue2.getSongs().size());
		assertTrue(mQueue1.getSongs().contains(testSong1));
		// It should be the third song on the list; requested songs w/o times are treated ASAP
		assertTrue(mQueue1.getSongs().get(2).getTitle().equals(testSong1.getTitle()));	
	}

	/**
	 * Test method for {@link MusicManager.ActiveQueue#startWatcher()}.
	 */
	@Test
	public void testStartWatcher() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link MusicManager.ActiveQueue#terminateWatcher()}.
	 */
	@Test
	public void testTerminateWatcher() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link MusicManager.ActiveQueue#generateSongs()}.
	 */
	@Test
	public void testGenerateSongs() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link MusicManager.ActiveQueue#fillGap(int)}.
	 */
	@Test
	public void testFillGap() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link MusicManager.ActiveQueue#run()}.
	 */
	@Test
	public void testRun() {
		//fail("Not yet implemented");
	}


}
