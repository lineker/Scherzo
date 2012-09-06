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
import SongGenerator.Operator;
import SongGenerator.Property;
import SongGenerator.ToleranceGenerator;
import SongGenerator.Value;

public class TestToleranceGenerator {

	/**
	 * Test that the tolerance generator returns songs that satisfy the correct number
	 * of constraints (within the tolerance) and that the songs returned satisfy the 
	 * constraints. Test with both limited and unlimited numbers of songs.
	 */
	@Test
	public void testGetSongsIntListOfConstraint() {
		//set tolerance and create generator with lists
		int tolerance = 6;
		ToleranceGenerator gen = new ToleranceGenerator(null, tolerance);
		ArrayList<Constraint> constraints = new ArrayList<Constraint>();
		HashMap<Song, Integer> satCount = new HashMap<Song, Integer>();
		int size = 5; //how many songs to ask for
		
		//add constraints
		constraints.add(new Constraint(Property.TITLE, Operator.CONTAINS, new Value("i")));
		constraints.add(new Constraint(Property.LENGTH, Operator.GREATER, new Value(0)));
		constraints.add(new Constraint(Property.TITLE, Operator.CONTAINS, new Value("e")));
		constraints.add(new Constraint(Property.TITLE, Operator.CONTAINS, new Value("o")));
		constraints.add(new Constraint(Property.ARTIST, Operator.GREATER, new Value("F")));
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
				if(ToleranceGenerator.satisfied(s, c))
					satCount.put(s, satCount.get(s).intValue() + 1);
			}
		}
		
		//verify that each song satisfies the minimum number of constraints
		for(Song s : satCount.keySet()){
			assertTrue(satCount.get(s).intValue() >= constraints.size() - tolerance);
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
				if(ToleranceGenerator.satisfied(s, c))
					satCount.put(s, satCount.get(s).intValue() + 1);
			}
		}
		
		//verify that songs were returned
		assertFalse(songs.size() == 0);
		
		//verify that each song satisfies the minimum number of constraints
		for(Song s : satCount.keySet()){
			assertTrue(satCount.get(s).intValue() >= constraints.size() - tolerance);
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
		assertTrue(ToleranceGenerator.satisfied(s, c));
		
		c = new Constraint(Property.TITLE, Operator.CONTAINS, new Value("Monkey"));
		assertFalse(ToleranceGenerator.satisfied(s, c));
		
		//artist
		c = new Constraint(Property.ARTIST, Operator.CONTAINS, new Value(s.getArtist()));
		assertTrue(ToleranceGenerator.satisfied(s, c));
		
		c = new Constraint(Property.ARTIST, Operator.CONTAINS, new Value("Monkey"));
		assertFalse(ToleranceGenerator.satisfied(s, c));
		
		//length
		c = new Constraint(Property.LENGTH, Operator.EQUALS, 
				new Value(s.getMin() * 60 + s.getSec()));
		assertTrue(ToleranceGenerator.satisfied(s, c));
		
		c = new Constraint(Property.LENGTH, Operator.EQUALS, 
				new Value(s.getMin() * 40 + s.getSec()));
		assertFalse(ToleranceGenerator.satisfied(s, c));
		
		//streaming
		s.setTrackType(TrackType.local);
		c = new Constraint(Property.STREAMING, Operator.EQUALS, new Value(false));
		assertTrue(ToleranceGenerator.satisfied(s, c));
		
		c = new Constraint(Property.STREAMING, Operator.EQUALS, new Value(true));
		assertFalse(ToleranceGenerator.satisfied(s, c));
		
		//last played
		s.setLastPlay(new DateTime());
		Thread.sleep(20);
		c = new Constraint(Property.LAST_PLAYED, Operator.LESS, new Value(new DateTime()));
		assertTrue(ToleranceGenerator.satisfied(s, c));
		
		c = new Constraint(Property.LAST_PLAYED, Operator.GREATER, new Value(new DateTime()));
		assertFalse(ToleranceGenerator.satisfied(s, c));
		
		//play counts
		c = new Constraint(Property.PLAY_COUNT, Operator.GREATER, new Value(-6));
		assertTrue(ToleranceGenerator.satisfied(s, c));
		
		c = new Constraint(Property.PLAY_COUNT, Operator.EQUALS, new Value(-6));
		assertFalse(ToleranceGenerator.satisfied(s, c));
		
		c = new Constraint(Property.PLAY_COUNT_TOTAL, Operator.GREATER, new Value(-6));
		assertTrue(ToleranceGenerator.satisfied(s, c));
		
		c = new Constraint(Property.PLAY_COUNT_TOTAL, Operator.EQUALS, new Value(-6));
		assertFalse(ToleranceGenerator.satisfied(s, c));
		
		//request counts
		c = new Constraint(Property.REQUEST_COUNT_DAY, Operator.GREATER, new Value(-6));
		assertTrue(ToleranceGenerator.satisfied(s, c));
		
		c = new Constraint(Property.REQUEST_COUNT_DAY, Operator.EQUALS, new Value(-6));
		assertFalse(ToleranceGenerator.satisfied(s, c));
		
		c = new Constraint(Property.REQUEST_COUNT_TOTAL, Operator.GREATER, new Value(-6));
		assertTrue(ToleranceGenerator.satisfied(s, c));
		
		c = new Constraint(Property.REQUEST_COUNT_TOTAL, Operator.EQUALS, new Value(-6));
		assertFalse(ToleranceGenerator.satisfied(s, c));
		
		//album
		c = new Constraint(Property.ALBUM, Operator.CONTAINS, new Value(s.getAlbum()));
		assertTrue(ToleranceGenerator.satisfied(s, c));
		
		c = new Constraint(Property.ALBUM, Operator.CONTAINS, new Value("Monkey"));
		assertFalse(ToleranceGenerator.satisfied(s, c));
	}

}
