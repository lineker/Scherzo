
package jgroove.json;

import java.util.HashMap;
import jgroove.json.JsonHeader;

/**
 * Json class representing the object returned by the Grooveshark's method
 * getSearchResultsEx. HashMap[] result contains the list of songs found with
 * they respective information when the string returned by callMethod is
 * deserialized.
 */
public class JsonSearchResults {
    /**
     * Response of the method. HashMap[] result contains the list of songs
     */
    public static class Result {
        public HashMap<String, String>[] result;
        public String version;
        public boolean askForSuggestion;
    }
    
    public JsonHeader header;
    public Result result;
}
