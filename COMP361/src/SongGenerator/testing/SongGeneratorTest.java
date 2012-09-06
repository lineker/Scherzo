package SongGenerator.testing;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import MusicManager.MusicManager;
import MusicManager.Playlist;
import MusicManager.Song;
import SongGenerator.SimpleGenerator;
import SongGenerator.SongGenerator;

/**
 * Song Generator test class to test getting songs filling a specified time interval,
 * getting a specific number of songs, and a song filtering.
 * @author Alicia Bendz
 *
 */
public class SongGeneratorTest {
	
	/**
	 * If you want to add all the songs you have on your local computer, set this
	 * to the directory where you have all your music. This will search this directory
	 * and all subdirectories for mp3 files.
	 */
	private static final String MUSIC_PATH="C:\\Users\\Alicia\\Music";
	
	/**
	 * Music manager reference.
	 */
	private MusicManager mMusicMan;
	
	/**
	 * Song generator to test.
	 */
	private SongGenerator mSongGen;
	
	/**
	 * Create the song generator and music manager with a valid playlist.
	 */
	@Before
	public void setUp(){
		try {
			mMusicMan = new MusicManager(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Playlist p = new Playlist();
		LinkedList<Song> songs = new LinkedList<Song>();
		songs.add(new Song("./files/Bird Song.mp3"));
		songs.add(new Song("./files/Bleed.mp3"));
		songs.add(new Song("./files/Falling.mp3"));
		songs.add(new Song("./files/Hardest of Hearts.mp3"));
		songs.add(new Song("./files/Let Down.mp3"));
		songs.add(new Song("./files/I Like to Dance.mp3"));
		songs.add(new Song("./files/Tonight Tonight.mp3"));
		songs.add(new Song("./files/Kalimba.mp3"));
		songs.add(new Song("./files/Maid.mp3"));
		songs.add(new Song("./files/Sleep Away.mp3"));
		songs.add(new Song("./files/02 We're In The Club Now.mp3"));
		songs.add(new Song("./files/03 Married Life.mp3"));
		songs.add(new Song("./files/04 Carl Goes Up.mp3"));
		songs.add(new Song("./files/05 52 Chachki Pickup.mp3"));
		songs.add(new Song("./files/06 Paradise Found.mp3"));
		songs.add(new Song("./files/07 Walkin' The House.mp3"));
		songs.add(new Song("./files/01 Up With Titles.mp3"));
		songs.add(new Song("./files/23 The Spirit Of Adventure.mp3"));
		songs.add(new Song("./files/22 Up With End Credits.mp3"));
		songs.add(new Song("./files/ZThe New Jerusalem.mp3"));

		//add all songs on your machine
		File musicFolder = new File(MUSIC_PATH);
		addSongsInPath(musicFolder, songs);
		
		p.setSongs(songs);
		p.setName("better_be_unique");
		mMusicMan.changePlaylist(p);
		
		mSongGen = new SimpleGenerator(mMusicMan);
	}

	/**
	 * Test that the correct number of songs is returned from the playlist and that
	 * each song is playable.
	 */
	@Test
	public void testGetSongsInt() {
		List<Song> songs = new LinkedList<Song>();
		songs.addAll(mSongGen.getSongs(4));
		assertTrue(songs.size() == 4);
		List<Song> playlistSongs = mMusicMan.getPlaylist().getSongs();
		
		for(Song s : songs){
			assertTrue(s.getLastPlay() == null || 
					s.getLastPlay().isBefore(System.currentTimeMillis() 
					- mMusicMan.getSongReplayGap()*SongGenerator.MILLIS_IN_MIN));
			assertTrue(playlistSongs.contains(s));
		}
	}

	/**
	 * Test that the correct length in time of songs is returned from the playlist, that
	 * each song is playable, and that each song is in the playlist.
	 */
	@Test
	public void testGetSongsIntInt() {
		List<Song> songs = new LinkedList<Song>();
		List<Song> playlistSongs = mMusicMan.getPlaylist().getSongs();
		int tolerance = 5;
		int target = 45;
		songs.addAll(mSongGen.getSongs(target, tolerance));
		int sum = 0;
		
		for(Song s : songs){
			sum += s.getSongLength();
			assertTrue(s.getLastPlay() == null || 
					s.getLastPlay().isBefore(System.currentTimeMillis() 
					- mMusicMan.getSongReplayGap()*SongGenerator.MILLIS_IN_MIN));
			assertTrue(playlistSongs.contains(s));
		}
		
		assertTrue(Math.abs(sum - target) <= tolerance);
	}
	
	/**
	 * Method to add all .mp3 songs in a directory and a subdirectory. Assumes a non-circular
	 * file system.
	 * @param path The path to follow.
	 * @param songList The list to add to.
	 */
	private void addSongsInPath(File path, List<Song> songList){
		if(path.isDirectory()){
			for(File subFile : path.listFiles()){
				if(subFile.isDirectory()){
					addSongsInPath(subFile, songList);
				} else {
					if(subFile.getAbsolutePath().endsWith(".mp3")){
						songList.add(new Song(subFile.getAbsolutePath()));
					}
				}
			}
		}
	}
}
