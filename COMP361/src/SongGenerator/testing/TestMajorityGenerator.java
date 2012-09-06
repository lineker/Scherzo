package SongGenerator.testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.junit.Test;

import MusicManager.Song;
import MusicManager.Song.TrackType;
import SongGenerator.Constraint;
import SongGenerator.GenerationException;
import SongGenerator.MajorityGenerator;
import SongGenerator.Operator;
import SongGenerator.Property;
import SongGenerator.Value;

/**
 * Class to test the Majority generator. Test that the majority generator returns 
 * songs that satisfy the majority of the given constraints and the right number of songs
 * are returned.
 * @author Alicia Bendz
 *
 */
public class TestMajorityGenerator {

	/**
	 * Test that the majority generator returns songs that satisfy the correct number
	 * of constraints (the majority) and that the songs returned satisfy the 
	 * constraints. Test with both limited and unlimited numbers of songs.
	 */
	@Test
	public void testGetSongsIntListOfConstraint() {
		MajorityGenerator gen = new MajorityGenerator(null);
		ArrayList<Constraint> constraints = new ArrayList<Constraint>();
		HashMap<Song, Integer> satCount = new HashMap<Song, Integer>();
		int size = 5; //how many songs to ask for
		
		//add constraints
		constraints.add(new Constraint(Property.TITLE, Operator.CONTAINS, new Value("i")));
		constraints.add(new Constraint(Property.LENGTH, Operator.GREATER, new Value(0)));
		constraints.add(new Constraint(Property.TITLE, Operator.CONTAINS, new Value("e")));
		constraints.add(new Constraint(Property.TITLE, Operator.CONTAINS, new Value("o")));
		constraints.add(new Constraint(Property.TITLE, Operator.CONTAINS, new Value("a")));
		constraints.add(new Constraint(Property.PLAY_COUNT, Operator.GREATER, new Value(0)));
		constraints.add(new Constraint(Property.LAST_PLAYED, Operator.LESS,
				new Value(new DateTime())));
		constraints.add(new Constraint(Property.TITLE, Operator.CONTAINS, new Value("u")));
		constraints.add(new Constraint(Property.REQUEST_COUNT_TOTAL, 
				Operator.LESS, new Value(1000)));
		
		//get songs from generator
		Collection<Song> songs = null;
		try {
			songs = gen.getSongs(size, constraints);
		} catch (GenerationException e) {
			e.printStackTrace();
		}
		
		//verify size is correct
		assertTrue(songs.size() == size);
		
		//count satisfied constraints for each song
		for(Song s: songs){
			satCount.put(s, 0);
			for(Constraint c : constraints){
				if(MajorityGenerator.satisfied(s, c))
					satCount.put(s, satCount.get(s).intValue() + 1);
			}
		}
		
		//verify that each song satisfies a majority of constraints
		for(Song s : satCount.keySet()){
			assertTrue(satCount.get(s).intValue() >= (constraints.size() + 1) / 2);
		}
		
		//get songs from generator - this time with no limit on number
		songs = null;
		try {
			songs = gen.getSongs(-1, constraints);
		} catch (GenerationException e) {
			e.printStackTrace();
		}
		
		//count satisfied constraints for each song
		for(Song s: songs){
			satCount.put(s, 0);
			for(Constraint c : constraints){
				if(MajorityGenerator.satisfied(s, c))
					satCount.put(s, satCount.get(s).intValue() + 1);
			}
		}
		
		//verify that songs were returned
		assertFalse(songs.size() == 0);
		
		//verify that each song satisfies a majority of constraints
		for(Song s : satCount.keySet()){
			assertTrue(satCount.get(s).intValue() >= (constraints.size() + 1) / 2);
		}
	}

	/**
	 * Test that the true and false outcome for each comparable trait are correctly
	 * returned.
	 * @throws InterruptedException
	 */
	@Test
	public void testSatisfied() throws InterruptedException {
		//test each trait for a song
		Constraint c;
		Song s = new Song("./files/23 The Spirit Of Adventure.mp3");
		
		//title
		c = new Constraint(Property.TITLE, Operator.CONTAINS, new Value(s.getTitle()));
		assertTrue(MajorityGenerator.satisfied(s, c));
		
		c = new Constraint(Property.TITLE, Operator.CONTAINS, new Value("Monkey"));
		assertFalse(MajorityGenerator.satisfied(s, c));
		
		//artist
		c = new Constraint(Property.ARTIST, Operator.CONTAINS, new Value(s.getArtist()));
		assertTrue(MajorityGenerator.satisfied(s, c));
		
		c = new Constraint(Property.ARTIST, Operator.CONTAINS, new Value("Monkey"));
		assertFalse(MajorityGenerator.satisfied(s, c));
		
		//length
		c = new Constraint(Property.LENGTH, Operator.EQUALS, 
				new Value(s.getMin() * 60 + s.getSec()));
		assertTrue(MajorityGenerator.satisfied(s, c));
		
		c = new Constraint(Property.LENGTH, Operator.EQUALS, 
				new Value(s.getMin() * 40 + s.getSec()));
		assertFalse(MajorityGenerator.satisfied(s, c));
		
		//streaming
		s.setTrackType(TrackType.local);
		c = new Constraint(Property.STREAMING, Operator.EQUALS, new Value(false));
		assertTrue(MajorityGenerator.satisfied(s, c));
		
		c = new Constraint(Property.STREAMING, Operator.EQUALS, new Value(true));
		assertFalse(MajorityGenerator.satisfied(s, c));
		
		//last played
		s.setLastPlay(new DateTime());
		Thread.sleep(20);
		c = new Constraint(Property.LAST_PLAYED, Operator.LESS, new Value(new DateTime()));
		assertTrue(MajorityGenerator.satisfied(s, c));
		
		c = new Constraint(Property.LAST_PLAYED, Operator.GREATER, new Value(new DateTime()));
		assertFalse(MajorityGenerator.satisfied(s, c));
		
		//play counts
		c = new Constraint(Property.PLAY_COUNT, Operator.GREATER, new Value(-6));
		assertTrue(MajorityGenerator.satisfied(s, c));
		
		c = new Constraint(Property.PLAY_COUNT, Operator.EQUALS, new Value(-6));
		assertFalse(MajorityGenerator.satisfied(s, c));
		
		c = new Constraint(Property.PLAY_COUNT_TOTAL, Operator.GREATER, new Value(-6));
		assertTrue(MajorityGenerator.satisfied(s, c));
		
		c = new Constraint(Property.PLAY_COUNT_TOTAL, Operator.EQUALS, new Value(-6));
		assertFalse(MajorityGenerator.satisfied(s, c));
		
		//request counts
		c = new Constraint(Property.REQUEST_COUNT_DAY, Operator.GREATER, new Value(-6));
		assertTrue(MajorityGenerator.satisfied(s, c));
		
		c = new Constraint(Property.REQUEST_COUNT_DAY, Operator.EQUALS, new Value(-6));
		assertFalse(MajorityGenerator.satisfied(s, c));
		
		c = new Constraint(Property.REQUEST_COUNT_TOTAL, Operator.GREATER, new Value(-6));
		assertTrue(MajorityGenerator.satisfied(s, c));
		
		c = new Constraint(Property.REQUEST_COUNT_TOTAL, Operator.EQUALS, new Value(-6));
		assertFalse(MajorityGenerator.satisfied(s, c));
		
		//album
		c = new Constraint(Property.ALBUM, Operator.CONTAINS, new Value(s.getAlbum()));
		assertTrue(MajorityGenerator.satisfied(s, c));
		
		c = new Constraint(Property.ALBUM, Operator.CONTAINS, new Value("Monkey"));
		assertFalse(MajorityGenerator.satisfied(s, c));
	}

}
