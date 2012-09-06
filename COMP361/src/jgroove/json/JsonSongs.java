
package jgroove.json;

import java.util.HashMap;
import jgroove.json.JsonHeader;

/**
 * Json class representing the object returned by the Grooveshark's methods
 * ArtistGetSongs and AlbumGetSongs. HashMap[] songs contains the list of songs
 * found with they respective information when the string returned by callMethod
 * is deserialized.
 */
public class JsonSongs {
     /**
     * Response of the method. HashMap[] songs contains the list of songs
     */
    public static class Result {
        public HashMap<String, String>[] songs;
        public boolean hasMore;
    }

    public JsonHeader header;
    public Result result;
}
