package MusicManager;

import org.joda.time.DateTime;
import org.tritonus.share.sampled.file.TAudioFileFormat;
import java.io.File;
import java.io.IOException;

import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 * @author Rebecca
 * This class represents a Song from an mp3 with its properties as 
 * relevant to the MusicManager.
 */
public class Song {

	/** The link to the sound file */
	private String mSrc;
	
	/** The title, artist, and duration of the song as read from
	 * meta-data file */
	private String mTitle;
	private String mArtist;
	private String mAlbum;
	private int mMin;
	private int mSec;
	
	/** Total requests for current day/session */
	private int mSessionTotalRequests;
	/** Total requests for this song */
	private int mTotalRequests;
	/** Total times the song has been played for that session */
	private int mPlayCount;
	/** Total times the song has been played ever */
	private int mTotalPlayCount;	
	
	/** Last time the song was played */
	private DateTime mLastPlay;
	
	/** Flag to indicate whether song was a request */
	private boolean mUserRequested;
	
	/** Streaming ID */
	private String mStreamingID;
	/** ID */
	private int mId;
	
	/** State: true if streamed; false otherwise */
	private boolean mIsStreamed;

	private TrackType trackType;
	
	/** Enum for track type**/
	public enum TrackType
	{
		streaming,
		local
	}
	
	/** 
	 * Construct a new Song
	 */
	public Song(){
	}
	
	/** 
	 * Construct a new Song from a mp3 file and its meta-data file.
	 */
	public Song(String source) {
		mSrc = source;
		try {
			
			// Using TAudioFileFormat 
			File file = new File(mSrc);
			AudioFileFormat baseFileFormat = null;
			baseFileFormat = AudioSystem.getAudioFileFormat(file);
			
			if (baseFileFormat instanceof TAudioFileFormat)
			{
			    Map properties = ((TAudioFileFormat)baseFileFormat).properties();
			    mTitle = (String) properties.get("title");
			    mArtist = (String) properties.get("author");
			    mAlbum = (String) properties.get("album");
			    Long microseconds = (Long) properties.get("duration");
		        int mili = (int) (microseconds / 1000);
		        mSec = (mili / 1000) % 60;
		        mMin = (mili / 1000) / 60;
			}  else {
				// TODO tell user something went wrong and song couldn't be added.
				System.err.println("Not a readable TAudioFile.");
				mArtist = "";
				mAlbum = "";
				mTitle = "";
				mSec = -1;
				mMin = -1;
			}
		} catch (IOException e) {
			System.err.println("IO exception.");
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			System.err.println("Unsupported Audio File Exception.");
			//e1.printStackTrace();
			this.mSrc = null;
		}
			
		mTotalRequests = 0;
		mPlayCount = 0;
		
		// TODO Is it streamed?
		mIsStreamed = false;
		mUserRequested = false;
		mLastPlay = null;
	}
	
	/* For testing */
	public void printSongInfo() {
        System.out.println("Song title: " + mTitle);
        System.out.println("Artist: " + mArtist);
        System.out.println("Album: " + mAlbum);
        System.out.println("time = " + mMin + ":" + mSec);
	}
	
	/**
	 * Prints the Song length nicely for GUI.
	 */
	public String getPrintedLength() {
		if(mSec == 0 && mMin == 0) {
			return "";
		} else if(mSec < 10) {
			return mMin + ":0" + mSec;
		} else {
			return mMin + ":" + mSec;
		}
	}
	
	/**
	 * Gets the Song length in minutes, with seconds rounded for AQ.
	 * @return int the length
	 */
	public int getSongLength() {
		return this.mMin + Math.round(this.mSec/60);
	}
	
	public String printSong() {
		return mTitle + " by " + mArtist + " from " + mAlbum;
	}

	public boolean getIsStreamed() {
		return mIsStreamed;
	}

	public void setIsStreamed(boolean isStreamed) {
		this.mIsStreamed = isStreamed;
	}

	public int getPlayCount() {
		return mPlayCount;
	}

	/** 
	 * Returns last time song was played
	 * @return DateTime object
	 */	
	public DateTime getLastPlay() {
		return mLastPlay;
	}

	/**
	 * Set by ActiveQueue
	 * @param mLastPlay the date and time
	 */
	public void setLastPlay(DateTime mLastPlay) {
		
		// TODO Check that it's valid
		
		this.mLastPlay = mLastPlay;
	}

	public void setPlayCount(int playCount) {
		this.mPlayCount = playCount;
	}
	
	public void incrementPlayCount() {
		this.mPlayCount++;
	}

	public int getReqCount() {
		return mSessionTotalRequests;
	}

	public void setReqCount(int reqCount) {
		this.mSessionTotalRequests = reqCount;
	}

	public int getTotalRequests() {
		return mTotalRequests;
	}

	public void setTotalRequests(int totalRequests) {
		this.mTotalRequests = totalRequests;
	}

	public String getArtist() {
		return mArtist;
	}

	public void setArtist(String artist) {
		this.mArtist = artist;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}

	public String getSrc() {
		return mSrc;
	}

	public void setSrc(String src) {
		this.mSrc = src;
	}

	public String getAlbum() {
		return mAlbum;
	}

	public void setAlbum(String album) {
		this.mAlbum = album;
	}

	public int getMin() {
		return mMin;
	}
	
	public void setMin(int min) {
		this.mMin = min;
	}
	
	public int getSec() {
		return mSec;
	}
	
	public void setSec(int sec) {
		this.mSec = sec;
	}

	public String getStreamingID() {
		return mStreamingID;
	}

	public void setStreamingID(String streamingID) {
		this.mStreamingID = streamingID;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
	}
	
	public TrackType getTrackType() {
		return this.trackType;
	}

	public void setTrackType(TrackType type) {
		this.trackType = type;
	}

	public boolean isUserRequested() {
		return mUserRequested;
	}

	public void setUserRequested(boolean userRequested) {
		this.mUserRequested = userRequested;
	}
	
	@Override
	public String toString(){
		return mTitle + " - " + mArtist;
	}

	public int getTotalPlayCount() {
		return mTotalPlayCount;
	}

	public void setTotalPlayCount(int mTotalPlayCount) {
		this.mTotalPlayCount = mTotalPlayCount;
	}
}
