package SongGenerator.testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import DataAccess.ServicePool;
import MusicManager.Song;
import SongGenerator.GenerationException;
import SongGenerator.Operator;
import SongGenerator.Property;
import SongGenerator.SongComparator;

/**
 * Test class for SongComparator. Makes a song comparator for each property and for
 * both increasing and decreasing sorting and checks correct sorting of a list of songs.
 * @author Alicia Bendz
 *
 */
public class TestSongComparator {

	/**
	 * The list of songs to be used in each test.
	 */
	private ArrayList<Song> mSongs;
	
	/**
	 * Create the list of songs before each test.
	 * @throws ClassNotFoundException Possible failure of database.
	 * @throws SQLException Possible failure of database.
	 */
	@Before
	public void setUp() throws SQLException, ClassNotFoundException{
		mSongs = new ArrayList<Song>();
		mSongs.addAll(ServicePool.SongService().getSongs());
		Random r = new Random();
		
		//just in case songs don't have dates
		for(Song s : mSongs){
			if(s.getLastPlay() == null)
				s.setLastPlay(new DateTime(Math.abs(r.nextLong())));
		}
	}

	/**
	 * Test to check sorting by title in decreasing order (maximizing at the front of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMaxTitle() throws GenerationException{
		//create comparator
		SongComparator comparator = new SongComparator(Property.TITLE, Operator.MAXIMIZE);
		
		//shuffle songs
		Collections.shuffle(mSongs);
		
		//sort songs
		Collections.sort(mSongs, comparator);
		
		//check that each song is greater or equal to the song following it
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getTitle().compareTo(mSongs.get(i).getTitle()) >= 0);
		}
	}
	
	/**
	 * Test to check sorting by title in increasing order (minimizing at the front of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMinTitle() throws GenerationException{
		//create comparator
		SongComparator comparator = new SongComparator(Property.TITLE, Operator.MINIMIZE);
		
		//shuffle songs
		Collections.shuffle(mSongs);
		
		//sort songs
		Collections.sort(mSongs, comparator);
		
		//check that each song is less than or equal to the song following it
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getTitle().compareTo(mSongs.get(i).getTitle()) <= 0);
		}
	}
	
	/**
	 * Test to check sorting by artist in decreasing order (maximizing at the front of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMaxArtist() throws GenerationException{
		//create comparator
		SongComparator comparator = new SongComparator(Property.ARTIST, Operator.MAXIMIZE);
		
		//shuffle songs
		Collections.shuffle(mSongs);
		
		//sort songs
		Collections.sort(mSongs, comparator);
		
		//check that each song is greater than or equal to the song following it
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getArtist().compareTo(mSongs.get(i).getArtist()) >= 0);
		}
	}
	
	/**
	 * Test to check sorting by artist in increasing order (minimizing at the front of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMinArtist() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.ARTIST, Operator.MINIMIZE);
		
		Collections.shuffle(mSongs);
		
		Collections.sort(mSongs, comparator);
		
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getArtist().compareTo(mSongs.get(i).getArtist()) <= 0);
		}		
	}
	
	/**
	 * Test to check sorting by length in decreasing order (maximizing at the front of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMaxLength() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.LENGTH, Operator.MAXIMIZE);
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue((mSongs.get(i - 1).getMin() * 60 + mSongs.get(i - 1).getSec()) 
					>= (mSongs.get(i).getMin() * 60 + mSongs.get(i).getSec()));
		}	
	}
	
	/**
	 * Test to check sorting by length in increasing order (minimizing at the front of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMinLength() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.LENGTH, Operator.MINIMIZE);
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue((mSongs.get(i - 1).getMin() * 60 + mSongs.get(i - 1).getSec()) 
					<= (mSongs.get(i).getMin() * 60 + mSongs.get(i).getSec()));
		}		
	}
	
	/**
	 * Test to check sorting by album in decreasing order (maximizing at the front of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMaxAlbum() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.ALBUM, Operator.MAXIMIZE);
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getAlbum().compareTo(mSongs.get(i).getAlbum()) >= 0);
		}
	}
	
	/**
	 * Test to check sorting by album in increasing order (minimizing at the front of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMinAlbum() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.ALBUM, Operator.MINIMIZE);
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getAlbum().compareTo(mSongs.get(i).getAlbum()) <= 0);
		}
	}
	
	/**
	 * Test to check sorting by last played in decreasing order (maximizing at the front 
	 * of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMaxLastPlayed() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.LAST_PLAYED, Operator.MAXIMIZE);
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getLastPlay()
					.compareTo(mSongs.get(i).getLastPlay()) >= 0);
		}
	}
	
	/**
	 * Test to check sorting by last played in increasing order (minimizing at the front 
	 * of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMinLastPlayed() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.LAST_PLAYED, Operator.MINIMIZE);
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getLastPlay()
					.compareTo(mSongs.get(i).getLastPlay()) <= 0);
		}
	}
	
	/**
	 * Test to check sorting by play count in decreasing order (maximizing at the front 
	 * of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMaxPlayCount() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.PLAY_COUNT, Operator.MAXIMIZE);
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getPlayCount() >= mSongs.get(i).getPlayCount());
		}
	}
	
	/**
	 * Test to check sorting by play count in increasing order (minimizing at the front 
	 * of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMinPlayCount() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.PLAY_COUNT, Operator.MINIMIZE);
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getPlayCount() <= mSongs.get(i).getPlayCount());
		}
	}
	
	/**
	 * Test to check sorting by total request count in decreasing order (maximizing at 
	 * the front of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMaxRequestCountTotal() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.REQUEST_COUNT_TOTAL,
				Operator.MAXIMIZE);
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getTotalRequests() >= mSongs.get(i).getTotalRequests());
		}
	}
	
	/**
	 * Test to check sorting by total request count in increasing order (minimizing at the 
	 * front of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMinRequestCountTotal() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.REQUEST_COUNT_TOTAL,
				Operator.MINIMIZE);
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getTotalRequests() <= mSongs.get(i).getTotalRequests());
		}
	}
	
	/**
	 * Test to check sorting by daily request count in decreasing order (maximizing at the
	 *  front of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMaxRequestCountDay() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.REQUEST_COUNT_DAY, 
				Operator.MAXIMIZE);
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getReqCount() >= mSongs.get(i).getReqCount());
		}
	}
	
	/**
	 * Test to check sorting by daily request count in increasing order (minimizing at 
	 * the front of the list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMinRequestCountDay() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.REQUEST_COUNT_DAY, 
				Operator.MINIMIZE);
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		for(int i = 1; i < mSongs.size(); i++){
			assertTrue(mSongs.get(i - 1).getReqCount() <= mSongs.get(i).getReqCount());
		}
	}
	
	/**
	 * Test to check sorting by streaming in decreasing order (streamed songs at front of list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMaxStreaming() throws GenerationException{
		SongComparator comparator = new SongComparator(Property.STREAMING, Operator.MAXIMIZE);
		boolean check = true;
		int index = 0;
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		//Find the split between streamed and local songs
		while(check && index < mSongs.size()){
			check = check && mSongs.get(index).getIsStreamed();
			index++;
		}
		
		index--;
		
		//check all songs at head are streamed
		for(int i = 0 ; i < index ; i++){
			assertTrue(mSongs.get(i).getIsStreamed());
		}
		
		//check all songs at tail are local
		for(int i = index + 1 ; i < mSongs.size() ; i ++){
			assertFalse(mSongs.get(i).getIsStreamed());
		}
	}
	
	/**
	 * Test to check sorting by title in increasing order (streamed songs at end of list).
	 * @throws GenerationException 
	 */
	@Test
	public void testMinStreaming() throws GenerationException{
		SongComparator comparator 
			= new SongComparator(Property.STREAMING, Operator.MINIMIZE);
		boolean check = true;
		int index = 0;
		
		Collections.shuffle(mSongs);
		Collections.sort(mSongs, comparator);
		
		//Find the split between streamed and local songs
		while(check && index < mSongs.size()){
			check = check && !mSongs.get(index).getIsStreamed();
			index++;
		}
		
		index--;
		
		//check all songs at head are local
		for(int i = 0 ; i < index ; i++){
			assertFalse(mSongs.get(i).getIsStreamed());
		}
		
		//check all songs at tail are streamed
		for(int i = index + 1 ; i < mSongs.size() ; i ++){
			assertTrue(mSongs.get(i).getIsStreamed());
		}
	}

}
