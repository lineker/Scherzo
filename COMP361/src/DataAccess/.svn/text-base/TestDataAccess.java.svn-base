package DataAccess;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import MusicManager.Song;
import SongGenerator.SongGenerator;

public class TestDataAccess {
	
	/**
	 * Test that the play count increase by 1
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void testIncrementSongPlayes() throws SQLException, ClassNotFoundException {
		int songid = 1;
		Song s = ServicePool.SongService().getSongById(songid);
		int oldcount = s.getPlayCount();
		ServicePool.SongService().updateRequestCount(s.getId());
		ServicePool.SongService().updateLastPlayed(s.getId());
		s = ServicePool.SongService().getSongById(songid);
		int newcount = 	s.getTotalPlayCount();

		assertTrue(newcount == (oldcount+1));
		
	}
}
