package jgroove;

import java.io.IOException;
import java.util.ArrayList;

import MusicManager.Song;

/**
 * Interface which has to be implemented to all streaming classes
 * which communicate to the streaming provider
 * @author lineket
 *
 */
public interface IStream {
	
	/**
	 * Enum used to return the top songs, daily and monthly
	 * @author lineket
	 *
	 */
	public enum topPlayed {
		monthly ,
		daily
	}
	
	/**
	 * Return array of top songs
	 * @param query
	 * @return Array<Song>
	 * @throws Exception
	 */
	public ArrayList<Song> top(topPlayed query) throws Exception;
	/**
	 * search for songs method
	 * @param query artist, song or album name.
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Song> search(String query) throws Exception;
	
	/**
	 * Download song | This is not being used
	 * @param id
	 * @param title
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void download(final String id, final String title) throws IOException, InterruptedException;
}
