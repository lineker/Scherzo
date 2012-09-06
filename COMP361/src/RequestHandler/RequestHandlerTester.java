package RequestHandler;

import java.util.LinkedList;
import java.util.List;

import MusicManager.MusicManager;
import MusicManager.Playlist;
import MusicManager.Song;
import MusicManager.Song.TrackType;
import SongGenerator.Constraint;
import SongGenerator.Operator;
import SongGenerator.Property;
import SongGenerator.SimpleGenerator;
import SongGenerator.SongGenerator;
import SongGenerator.Value;

/**
 * A test class that runs the request handler. This will start the request handler on a 
 * default 2011 port.
 * @author Alicia Bendz
 *
 */
public class RequestHandlerTester {

	public static void main(String[] args) throws Exception {
		MusicManager m = new MusicManager(false);
		Playlist p = new Playlist();
		SongGenerator sg = new SimpleGenerator(null);
		List<Constraint> c = new LinkedList<Constraint>();
		c.add(new Constraint(Property.TITLE, Operator.NOT_CONTAINS, new Value("song")));
		LinkedList<Song> songs =  new LinkedList<Song>();
		
		//get some 35 existing songs
		songs.addAll(sg.getSongs(35, c));
		
		for(Song s:songs){
			if(s.getTrackType() == null)
				s.setTrackType(TrackType.local);
		}
		
		p.setSongs(songs);
		p.setName("unique_name02");
		m.changePlaylist(p);
		m.goLive();
		RequestHandler handler = new RequestHandler(-1, m);
		handler.start();
	}
}
