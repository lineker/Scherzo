package RequestHandler;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import request.json.RequestJson;
import request.json.RequestType;
import request.json.ResponseJson;
import request.json.SongJson;
import DataAccess.ServicePool;
import MusicManager.MusicManager;
import MusicManager.Song;

import com.google.gson.Gson;

/**
 * Processing Thread class that processes requests from a client given a connection 
 * from the request handler.
 * @author Alicia Bendz
 *
 */
public class ProcessingThread extends Thread {
	/**
	 * Socket for the client connection.
	 */
	private final Socket mSocket;
	
	/**
	 * Music manager of the system.
	 */
	private final MusicManager mMusicManager;
	
	/**
	 * Constructor for the processing thread.
	 * @param socket - The Socket for the client connection.
	 * @param manager - The music manager of the system.
	 */
	public ProcessingThread(Socket socket, MusicManager manager){
		mSocket = socket;
		mMusicManager = manager;
	}
	
	@Override
	public void run(){
		//Input and output streams
		BufferedReader in;
		PrintWriter out;
		
		//Request object
		RequestJson request;
		Gson gson = new Gson();
		
		try {
			//No longer connected, move on
			if(!mSocket.isConnected())
				return;
			
			//Create input and output streams
			 in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			 out = new PrintWriter(mSocket.getOutputStream(), true);
			 
			 //read in request
			 request = gson.fromJson(in.readLine(), RequestJson.class);
			 //System.out.println(gson.toJson(request));
			 
			 //parse request and get reply
			 ResponseJson reply = parseRequest(request);
			 String stringReply = gson.toJson((reply));
			 //System.out.println(stringReply);
			 
			 //if there is a reply, send it back
			 if(reply != null)
				 out.println(stringReply);
			 
			 //close streams and connection
			 in.close();
			 out.close();
			 mSocket.close();
		} catch (Exception e) {
			System.err.println("Processing Thread: Processing Error - exiting");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * Method to parse the request and call the appropriate methods to execute.
	 * @param request The request sent by the client.
	 * @return The response to the client if there is one.
	 */
	private ResponseJson parseRequest(RequestJson request){
		//The possible response
		ResponseJson response = null;
		//intermediate variable for a list of songs
		List<Song> songs;
		
		//determine action based on request type
		switch(request.getType()){
		case FEEDBACK:
			//System.out.println("Feedback.");
			if(!storeFeedback(request.getSource(), request.getFeedbackMessage()))
				System.err.println("Processing Thread: Feedback failed to store.");
			break;
		case PLAYLIST:
			//System.out.println("Playlist.");
			songs = getPlaylist();
			
			//create playlist response
			response = new ResponseJson(RequestType.PLAYLIST, convertSongs(songs), null);
			break;
		case PLAYING:
			//System.out.println("Playing.");
			songs = getPlaying();
			//create playing response
			response = new ResponseJson(RequestType.PLAYING, convertSongs(songs), null);
			break;
		case SONGREQUEST:
			System.out.println("Songreq.");
			//check song in database
			Song s = null;
			try{
				s = getSong(Integer.parseInt(request.getSongId()));
			} catch (NumberFormatException nfe){
				//malformed request. Ignore.
				break;
			}
			
			//if present, send request to music manager, else ignore and move on
			if(s != null){
				//update request count
				try {
					ServicePool.SongService().updateRequestCount(s.getId());
					
					//store song request in database
					ServicePool.StatisticsService().insertLogRequest(request.getSource(), 
							s.getId());
				} catch (Exception e) {
					System.err.println("Processing Thread: Failed to update song request.");
				}
				
				if(request.getPlaySpecificTime() == null){
					mMusicManager.addRequest(s);
					System.out.println("BOO");
				}
				else{
					String[] time = request.getPlaySpecificTime().split(":");
					mMusicManager.addRequest(s, Integer.parseInt(time[0]), 
												Integer.parseInt(time[1]));
					System.out.println("BOO");
				}
			}
			break;
		default:
		}
		
		return response;
	}
	
	/**
	 * Method to convert a list of Songs to Json Object Songs.
	 * @param songs The list of songs to convert.
	 * @return The resulting list of SongJsons.
	 */
	private List<SongJson> convertSongs(List<Song> songs){
		List<SongJson> newSongs = new ArrayList<SongJson>();
		String sec;
		
		for(Song s: songs){
			if(s.getSec() < 10)
				sec = "0" + s.getSec();
			else
				sec = "" + s.getSec();
			newSongs.add(new SongJson(s.getId(), s.getTitle(), 
					s.getArtist(), s.getAlbum(), "" + s.getMin() 
					+ ":" + sec, false));
		}
		
		newSongs.remove(0);
		if(songs.get(0).getSec() < 10)
			sec = "0" + songs.get(0).getSec();
		else
			sec = "" + songs.get(0).getSec();
		newSongs.add(0, new SongJson(songs.get(0).getId(), songs.get(0).getTitle(), 
				songs.get(0).getArtist(), songs.get(0).getAlbum(), 
				"" + songs.get(0).getMin() + ":" + sec, true));
		
		return newSongs;
	}
	
	/**
	 * Given a song identifier, this method will return the song in the 
	 * database if it exists.
	 * @param sid The song identifier to use.
	 * @return The complete song from the database.
	 */
	private Song getSong(int sid){
		try {
			return ServicePool.SongService().getSongById(sid);
		} catch (Exception e) {
			System.err.println("Processing Thread: Invalid song request for song id " 
								+ sid);
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * This method will store the feedback from a client source in the database.
	 * @param source The source of the feedback.
	 * @param feedback The feedback message.
	 * @return Whether storing feedback was successful or not.
	 */
	private boolean storeFeedback(String source, String feedback){
		//System.out.println(feedback + " from " + source);
		try {
			ServicePool.FeedbackService().insertFeedback(source, feedback);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Get the list of songs that can be played from the music manager.
	 * @return The list of songs that can be played.
	 */
	private List<Song> getPlaylist(){
		return mMusicManager.getPlaylist().getSongs();
	}
	
	/**
	 * Get the list of playing songs from the music manager.
	 * @return The list of playing songs.
	 */
	private List<Song> getPlaying(){
		return mMusicManager.getActiveQueue().getSongs();
	}
	
}
