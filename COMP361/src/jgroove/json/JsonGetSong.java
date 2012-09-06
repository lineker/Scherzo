
package jgroove.json;

import jgroove.json.JsonHeader;

/**
 * Json class representing the object returned by the Grooveshark's method
 * getSongFromIDEx. streamKey and ip are the fields needed to retrieve a song.
 */
public class JsonGetSong {
    /**
     * Response of the method. Ip contains the url to the host that has the song,
     * streamKey is the key needed to retrieve it
     */
    public static class Result{
        public int uSecs;
        public String FileToken;
        public String streamKey;
        public int streamServerID;
        public String ip;
    }

    public JsonHeader header;
    public Result result;
}
