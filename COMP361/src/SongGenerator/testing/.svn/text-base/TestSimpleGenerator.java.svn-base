package SongGenerator.testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import MusicManager.Song;
import SongGenerator.Constraint;
import SongGenerator.GenerationException;
import SongGenerator.Operator;
import SongGenerator.Property;
import SongGenerator.SimpleGenerator;
import SongGenerator.ToleranceGenerator;
import SongGenerator.Value;

/**
 * A class to test the simple generator. This will test that when asked for songs,
 * the song generator returns the correct number satisfying all the given constraints
 * (assuming enough songs satisfying the constraints exist).
 * @author Alicia Bendz
 *
 */
public class TestSimpleGenerator {
	
	/**
	 * Test that the simple generator returns songs that satisfy the 
	 * constraints. Test with both limited and unlimited numbers of songs.
	 */
	@Test
	public void testGetSongsIntListOfConstraint() {
		//set tolerance and create generator with lists
		SimpleGenerator gen = new SimpleGenerator(null);
		ArrayList<Constraint> constraints = new ArrayList<Constraint>();
		int size = 5; //how many songs to ask for
		
		//add constraints
		constraints.add(new Constraint(Property.LENGTH, Operator.GREATER, new Value(0)));
		constraints.add(new Constraint(Property.TITLE, Operator.CONTAINS, new Value("e")));
		constraints.add(new Constraint(Property.PLAY_COUNT, Operator.GREATER, new Value(-1)));
		
		//get songs from generator
		Collection<Song> songs = null;
		try {
			songs = gen.getSongs(size, constraints);
		} catch (GenerationException e) {
			e.printStackTrace();
		}
		
		//verify size is correct
		assertTrue(songs.size() == size);
		
		//check that each song returned satisfies every constraint
		for(Song s: songs){
			for(Constraint c : constraints){
				assertTrue(ToleranceGenerator.satisfied(s, c));
			}
		}
		
		//get songs from generator - this time with no limit on number
		songs = null;
		try {
			songs = gen.getSongs(-1, constraints);
		} catch (GenerationException e) {
			e.printStackTrace();
		}
		
		//count satisfied constraints for each song
		//check that each song returned satisfies every constraint
		for(Song s: songs){
			for(Constraint c : constraints){
				assertTrue(ToleranceGenerator.satisfied(s, c));
			}
		}
		
		//verify that songs were returned
		assertFalse(songs.size() == 0);
	}
}
