package request.json;

import java.util.List;

public class ResponseJson {
	/**
	 * Request type
	 * FEEDBACK,PLAYLIST,PLAYING,SONGREQUEST
	 */
	private RequestType RequestType;
	/**
	 * List of SongJson
	 */
	private List<SongJson> SongsList;
	/**
	 * Error Message (optional)
	 */
	private String ErrorMessage;
	
	public ResponseJson(RequestType type, List<SongJson> songs, String error){
		RequestType = type;
		SongsList = songs;
		ErrorMessage = error;
	}
	
	public RequestType getType(){
		return RequestType;
	}
	
	public List<SongJson> getSongs(){
		return SongsList;
	}
	
	public String getErrorMessage(){
		return ErrorMessage;
	}
}
