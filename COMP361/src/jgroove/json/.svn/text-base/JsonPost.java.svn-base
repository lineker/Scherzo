

package jgroove.json;

import java.util.HashMap;

import java.io.IOException;

import jgroove.JGroove;
import jgroove.JGroovex;
import jgroove.json.JsonCountry;

/**
 * Class that represents the basic Json post object to make the petition to a
 * Grooveshark's service, it is mainly used by callMethod and should not be
 * used out of there but if you want to complicate your life.
 */
public class JsonPost {
	public final HashMap<String, Object> header = new HashMap<String, Object>();
	{
		header.put("country", country);
		header.put("uuid", JGroove.uuid);
		header.put("privacy", 0);
		header.put("session", JGroove.getCurrentSessionID());
	}
	public static final HashMap<String, String> country = new HashMap<String, String>();
	static {
		JsonCountry jcountry = new JsonCountry();
		try {
			
			
			jcountry = JGroovex.getCountry();
			
			jcountry.IPR = "0";
			jcountry.ID = "38";
			jcountry.CC1 = "137438953472";
			jcountry.CC2 = "0";
			jcountry.CC3 = "0";
			jcountry.CC4 = "0";
			
		} catch (Exception e) { //if error default country values
			jcountry.IPR = "1201";
			jcountry.ID = "223";
			jcountry.CC1 = "0";
			jcountry.CC2 = "0";
			jcountry.CC3 = "0";
			jcountry.CC4 = "2147483648";
			System.out.println("here");
			e.printStackTrace();
		}
		country.put("ID", jcountry.ID);
		country.put("CC1", jcountry.CC1);
		country.put("CC2", jcountry.CC2);
		country.put("CC3", jcountry.CC3);
		country.put("CC4", jcountry.CC4);
		country.put("IPR", jcountry.IPR);
	}
	
	public HashMap<String, Object> parameters;
	public String method;
	

	/**
	 * Attach the parameters and the method to the JsonPost abstract class to
	 * send it via http, it will also automatically get the session id if it
	 * hasn't been done already
	 * @param parameters Paramaters to post
	 * @param method Method to call
	 */
	public JsonPost(HashMap<String, Object> parameters, String method) throws IOException{
		this.parameters = parameters;
		this.method = method;

		System.out.println("DEBUGGGGG:"+method);

		if (method.equalsIgnoreCase("getStreamKeyFromSongIDEx")  || 
			method.equalsIgnoreCase("markSongComplete") || 
			method.equalsIgnoreCase("markSongDownloadedEx")||
			method.equalsIgnoreCase("markStreamKeyOver30Seconds"))
		{
			
			this.header.put("client", JGroove.nameJS);
			this.header.put("clientRevision", JGroove.versionJS);
			//JGroove.password = "theTicketsAreNowDiamonds";
			JGroove.password = "whereIsAllThePunch";
		}
		else if (method.equalsIgnoreCase("getSearchResultsEx")||
				method.equalsIgnoreCase("getResultsFromSearch")||
				method.equalsIgnoreCase("getSearchResults")||
				method.equalsIgnoreCase("authenticateUser")|| 
				method.equalsIgnoreCase("playlistAddSongToExisting") || 
				method.equalsIgnoreCase("createPlaylist") ||
				method.equalsIgnoreCase("popularGetSongs") || 
				method.equalsIgnoreCase("playlistGetSongs") || 
				method.equalsIgnoreCase("initiateQueue") || 
				method.equalsIgnoreCase("userAddSongsToLibrary") || 
				method.equalsIgnoreCase("userGetPlaylists")||
				method.equalsIgnoreCase("userGetSongsInLibrary")||
				method.equalsIgnoreCase("getCommunicationToken")||
				method.equalsIgnoreCase("authenticateUser")||
				method.equalsIgnoreCase("getFavorites")||
				method.equalsIgnoreCase("favorite")||
				method.equalsIgnoreCase("getCountry")||
				method.equalsIgnoreCase("albumGetSongs")){
			
			this.header.put("client", JGroove.nameHTML);
			this.header.put("clientRevision", JGroove.versionHTML);
			//JGroove.password = "imOnAHorse";
			JGroove.password = "hamburgerAndHotdogs";
		}
		
		if (JGroove.getCurrentSessionID().isEmpty()){
			this.header.put("session", JGroove.getSessionID());
		} else {
			this.header.put("session", JGroove.getCurrentSessionID());
		}
		
		
	}
}
