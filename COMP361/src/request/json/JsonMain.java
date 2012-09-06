package request.json;

import java.util.ArrayList;

import com.google.gson.Gson;

public class JsonMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//mobile device prepare this message
		RequestJson msg = new RequestJson(RequestType.FEEDBACK, "ANDROID", null, "Howdy", null);
		//msg.SongId = "01";
//		msg.Source = "iOS";
//		msg.RequestType = "GetPlaylist";
		
		Gson gson = new Gson();
		String jsonmsg  = gson.toJson(msg);
		System.out.println(jsonmsg);  
		
		/**send request over to server**/
		
		//server will receive it and convert it back to object
		RequestJson ServerMsg = (new Gson().fromJson(jsonmsg, RequestJson.class));
		
		//server now prepares response
		ArrayList<SongJson> songs = new ArrayList<SongJson>();
		songs.add(new SongJson(1,"Title1","testArtist", null, null, true));
		songs.add(new SongJson(1,"Title2","testArtist2", null, null, false));
		ResponseJson msgResp = new ResponseJson(RequestType.PLAYLIST, songs, null);
		
		
		String jsonmsgResp  = gson.toJson(msgResp);
		System.out.println(jsonmsgResp);
		
		/** send response to mobile device**/
		
		ResponseJson ServerResponse = (new Gson().fromJson(jsonmsgResp, ResponseJson.class));
		
		//mobile do whatever.
		
	}

}
