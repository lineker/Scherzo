package RequestHandler;

import java.awt.HeadlessException;
import java.util.LinkedList;

import MusicManager.MusicManager;
import MusicManager.Playlist;
import MusicManager.Song;

/**
 * A tester to handle the load on the reqeust handler. Run many different clients at once.
 * @author Alicia Bendz
 *
 */
public class TestRequestHandler implements Runnable {
	/**
	 * The handler being tested.
	 */
	private final RequestHandler mHandler;
	
	/**
	 * Initial client id.
	 */
	private int mClientIds = 0;

	@Override
	public void run() {
		mHandler.start();
		testOneClient();
		testMultipleClients(2);
		testMultipleClients(10);
		testMultipleClients(50);
	}
	
	//Create a new music manager and request handler.
	public TestRequestHandler() throws HeadlessException, Exception{
		MusicManager m = null;
		
		try {
			m = new MusicManager(false);
		} catch (Exception e) {
			e.printStackTrace();//TODO:?
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
		
		p.setSongs(songs);
		p.setName("unique_name01");
		m.changePlaylist(p);
		m.goLive();
		mHandler = new RequestHandler(2011, m);
	}
	
	/**
	 * Test with a single client.
	 */
	public void testOneClient(){
		new Thread(new Client(mClientIds++)).start();
	}
	
	/**
	 * Test with i clients.
	 * @param i The number of clients to test with.
	 */
	public void testMultipleClients(int i){
		for(int j = 0; j < i; j++){
			new Thread(new Client(mClientIds++)).start();
		}
	}
	
	public static void main(String[] args) throws HeadlessException, Exception{
		new Thread(new TestRequestHandler()).start();
	}
}
