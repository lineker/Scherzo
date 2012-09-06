
package jgroove.json;

import java.util.HashMap;
import jgroove.json.JsonHeader;

/**
 * Json class representing the object returned by the Grooveshark's method
 * PlaylistGetSongs, it is also valid for popularGetSongs.
 * HashMap[] Songs contains the list of songs found with they respective
 * information when the string returned by callMethod is deserialized.
 */
public class JsonPlaylistSongs {
     /**
     * Response of the method. HashMap[] Songs contains the list of songs
     */
    public static class Result {
        public HashMap<String, String>[] Songs;
    }

    public JsonHeader header;
    public Result result;
}
