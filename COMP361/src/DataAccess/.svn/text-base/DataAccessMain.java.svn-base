package DataAccess;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import statistics.LogRequest;
import MusicManager.Playlist;
import MusicManager.Song;
import MusicManager.Song.TrackType;

public class DataAccessMain {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, Exception {
		ServicePool srv = new ServicePool();
		
		Collection<Playlist> plays = srv.PlaylistService().getPlaylistNames();
		
		System.out.println(plays.size());
		
		System.out.println(srv.PlaylistService().doesPlaylistExist("hiho"));
		System.out.println(srv.PlaylistService().doesPlaylistExist("Test Playlist"));
		
		srv.PlaylistService().insertPlaylist("hiho");
		System.out.println(srv.PlaylistService().doesPlaylistExist("hiho"));
		
		//Song getsong = srv.SongService().getSongById(16);
		
		//System.out.print(getsong.getAlbum() + " " + getsong.getId() + " " + getsong.getArtist());
		
		Song song = new Song();
		song.setAlbum("test album");
		song.setArtist("test artist");
		song.setMin(3);
		song.setSec(30);
		song.setSrc("C:mp3");
		song.setStreamingID("69");
		song.setTitle("title song");
		song.setTrackType(TrackType.streaming);
		
		//srv.SongService().addSong(song);
		
		//boolean b = srv.SongService().updateRequestCount(song.getId());
		
		//System.out.println("updated = " + b);
		
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date from = dfm.parse("2007-02-26 20:15:00");
		Date to =  dfm.parse("2012-02-05 20:15:00");
		
		//srv.FeedbackService().InsertFeedback("Android", "hey boy");
		
		
		Collection<Feedback> feeds = srv.FeedbackService().getFeedbackByDateRange(null, null);
		
		for (Iterator iterator = feeds.iterator(); iterator.hasNext();) {
			Feedback feedback = (Feedback) iterator.next();
			System.out.println(feedback.getText() + " " + feedback.getId());
		}
		
		//int id =srv.StatisticsService().insertLogRequest("IOS", 3);
		
		//System.out.println(id);
		
		Collection<LogRequest> logs = srv.StatisticsService().getLogRequestsByDateRange(null, null);
		
		System.out.println("looking for range date ="+logs.size());
		
		for (Iterator iterator = logs.iterator(); iterator.hasNext();) {
			LogRequest logRequest = (LogRequest) iterator.next();
			System.out.println("id="+logRequest.getId());
			System.out.println("date="+logRequest.getRequestDate());
		}
		
		Collection<LogRequest> logs2 = ServicePool.StatisticsService().getLogRequestsByDateRangeAndSongId(from, null, 3);
		System.out.println("range + songid(3) = "+logs2.size());
	}

}

