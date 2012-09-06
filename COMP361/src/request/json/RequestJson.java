package request.json;

public class RequestJson {
	
	/**
	 * Request type
	 * FEEDBACK,PLAYLIST,PLAYING,SONGREQUEST
	 */
	private RequestType RequestType;
	
	/**
	 * Source device type. Can be anything. Currently only Android or iOS supported.
	 */
	private String Source;
	/**
	 * Database primary key Id of the Song
	 */
	private String SongId;
	/**
	 * Feedback message
	 */
	private String FeedbackMessage;
	
	/**
	 * If user specify a specific time to play a song. Assumed to be on the day of the request.
	 * hh:mm
	 */
	private String PlaySpecificTime;
	
	public RequestJson(RequestType type, String src, String songid, String message, String playtime){
		RequestType = type;
		Source = src;
		SongId = songid;
		FeedbackMessage = message;
		PlaySpecificTime = playtime;
	}
	
	public RequestType getType(){
		return RequestType;
	}
	
	public String getSource(){
		return Source;
	}
	
	public String getSongId(){
		return SongId;
	}
	
	public String getFeedbackMessage(){
		return FeedbackMessage;
	}
	
	public String getPlaySpecificTime(){
		return PlaySpecificTime;
	}
}
