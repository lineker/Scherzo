package jgroove;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import jgroove.json.JsonGetSong.Result;
import MusicManager.MusicManager;
import MusicManager.MusicPlayer;
import MusicManager.Song;
import MusicManager.Song.TrackType;

/**
 * Abstract class to interact with Grooveshark server
 * It implements IStream
 * @author lineker
 *
 */
public class StreamingManager implements IStream {
	
	/** Timer reference **/
	public static Timer timer;
	public static MusicManager musicm;
	static Boolean isQueueInitiated = false;
	/**
	 * Default Constructor it set up the Queue
	 * @throws IOException
	 */
	public StreamingManager(MusicManager m) throws IOException
	{
		musicm = m;
		if(!isQueueInitiated)
		{
			//JGroovex.initiateQueue();
			isQueueInitiated = true;
		}
	}
	
	private void showConnectionError()
	{
		JOptionPane.showMessageDialog(musicm.getmFrame().getContentPane(), "We can't connect to Grooveshark server. Please verify your connection");
	}
	
	/**
	 * Get top songs
	 */
	public ArrayList<Song> top(topPlayed query) throws Exception {
		HashMap<String, String>[] songs = null;
		try
		{
			songs = JGroovex.getPopularSongs(query.toString()).result.Songs;
		}
		catch(Exception ex)
		{
			showConnectionError();
		}
		return parseSongs(songs);
	}
	
	/**
	 * Get songs based on a search query
	 */
	public ArrayList<Song> search(String query) throws Exception {
		HashMap<String, String>[] songs = null;
		try
		{
			songs = JGroovex.getSearchResults(query.replaceAll("[^\\p{ASCII}]", ""),"Songs").result.result;
		}
		catch(Exception ex)
		{
			showConnectionError();
		}

		return parseSongs(songs);
	}
	
	/**
	 * Parse songs returned by Grooveshark into Song object
	 * @param songs
	 * @return ArrayList<Song>
	 */
	private static ArrayList<Song> parseSongs(HashMap<String, String>[] songs) 
	{
		ArrayList<Song> listSongs = new ArrayList<Song>();
		if(songs != null)
		{
		
			for (int i = 0;i<songs.length;i++){
				
				Song newSong = new Song();
				
				String title = songs[i].get("SongName") != null ? songs[i].get("SongName") : songs[i].get("Name");
				newSong.setTitle(title);
				newSong.setArtist(songs[i].get("ArtistName"));
				newSong.setAlbum(songs[i].get("AlbumName"));
				newSong.setStreamingID(songs[i].get("SongID"));
				
				//newSong.Year = songs[i].get("Year");
				//newSong.TrackNumber = songs[i].get("TrackNum");
				
				Integer min = -1;
				
				try
				{
					min = Integer.parseInt(songs[i].get("EstimateDuration"));
				}
				catch(Exception ex)
				{
					min = -1;
				}
				if(min > -1)
				{
					newSong.setSec(min % 60);
					newSong.setMin(min / 60);
				}
				//System.out.println(newSong.getSec());
				newSong.setTrackType(TrackType.streaming);
				//System.out.println(min);
				listSongs.add(newSong);
			}
		}
		
		return listSongs;
	}
	
	/**
	 * Download a Song
	 */
	public void download(final String id, final String title) throws IOException, InterruptedException{

		Result songURL = null;
		try {
			songURL = JGroovex.getSongURL(id).result;
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		System.out.println("Serverid:"+songURL.streamServerID);
		System.out.println("Serverid:"+songURL.streamKey);
		System.out.println("Serverid:"+songURL.ip);
		System.out.println("Downloading");
		Object[] params = null;

		InputStream is = null;
		int downloaded=0;
		try {
			params =  JGroovex.getSongStream(songURL.ip, songURL.streamKey);
			JGroovex.markSongAsDownloaded(songURL.streamServerID, songURL.streamKey, id);
			initTimer(songURL.streamServerID, songURL.streamKey, id);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		is = (InputStream)params[1];
		int lenght = (Integer) params[0];

		File f=new File(title+".mp3");
		OutputStream out = null;
		try {
			out = new FileOutputStream(f);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		byte buf[]=new byte[3000];
		int len;
		
		int counter = 0;
		
		try {
			int percentage = -1;
			while((len=is.read(buf))>0){
				downloaded+=len;
				out.write(buf,0,len);
				counter++;
				
				//printing....
				if(percentage != downloaded*100/lenght)
				{
					percentage = downloaded*100/lenght;
					System.out.println(percentage+"%");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println("\nFile Downloaded");
		try {
			JGroovex.markSongComplete(songURL.streamServerID, songURL.streamKey, id);
			timer.cancel();
			System.out.println("\nMark song as completed");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	private boolean available()
	{
		try
		{
			Object s = JGroovex.getFavorites("0");
			return true;
		}
		catch(Exception ex)
		{
			showConnectionError();
			return false;
		}
	}
	
	/**
	 * Play a song 
	 * @param song
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void play(Song song, MusicPlayer player) throws IOException, InterruptedException{
		String id = new Integer(song.getStreamingID()).toString();
		Result songURL = null;
		try {
			
			songURL = JGroovex.getSongURL(id).result;
			
			System.out.println("Serverid:"+songURL.streamServerID);
			System.out.println("Serverid:"+songURL.streamKey);
			System.out.println("Serverid:"+songURL.ip);
			System.out.println("Downloading");
			Object[] params = null;
	
			InputStream is = null;
			if(songURL != null)
			{
				try {
					params =  JGroovex.getSongStream(songURL.ip, songURL.streamKey);
					//need to mark as downloaded
					JGroovex.markSongAsDownloaded(songURL.streamServerID, songURL.streamKey, id);
					//initiate timer to mark as song after 30 sec
					initTimer(songURL.streamServerID, songURL.streamKey, id);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				is = (InputStream)params[1];
				int lenght = (Integer) params[0];
		
				//MusicPlayer player = new MusicPlayer();
		
				try {
					System.out.println("start playing");
					player.play(is, song);
					System.out.println("after start playing");
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					is.close();
				} catch (IOException e) {
		
					e.printStackTrace();
				}
				System.out.println("\nFile playing");
				//if finishes playing mark as complete
				try {
					JGroovex.markSongComplete(songURL.streamServerID, songURL.streamKey, id);
					timer.cancel();
					System.out.println("\nMark song as completed");
				} catch (IOException e) {
		
					e.printStackTrace();
				}
				
			}
		} catch (Exception e1) {
			showConnectionError();
			e1.printStackTrace();
			try {
				musicm.stopNow();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Parse duration of a song
	 * @param time
	 * @return String duration
	 */
	private static String parseDuration(String time){
		if (time != null){
			double number = Double.parseDouble(time)/60;
			return String.format("%.2f", number)+ " mins";
		}
		return "NA";
	}
	
	/**
	 * Initiate a timer to call grooveshark back and markStreamKeyOver30Seconds
	 * @param streamServerID
	 * @param streamKey
	 * @param id
	 */
	public void initTimer(final int streamServerID, final String streamKey, final String id ){
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					JGroovex.markStreamKeyOver30Seconds(0, streamServerID, streamKey, id);

				} catch (IOException e) {

					e.printStackTrace();
				}
				timer.cancel();
			}
		}, 30 * 1000);
	}

	
}
