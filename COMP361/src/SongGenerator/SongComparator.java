package SongGenerator;

import java.util.Comparator;

import org.joda.time.DateTime;

import MusicManager.Song;
import SongGenerator.GenerationException.Cause;

/**
 * Song comparator. Each song comparator compares songs by a given property.
 * @author Alicia Bendz
 *
 */
public class SongComparator implements Comparator<Song> {
	/**
	 * The property of the comparator.
	 */
	private final Property mProperty;
	
	/**
	 * The multiplier to maximize or minimize sorting. -1 if maximizing (sort in decreasing order)
	 * and 1 if minimizing (sort in increasing order).
	 */
	private final int mMaxOrMin;
	
	/**
	 * Constructor for song comparator.
	 * @param p Property the comparator uses.
	 * @param o Operator for the comparator. Will only accept maximization or minimization 
	 * operators. Comparator will not work otherwise.
	 * @throws GenerationException 
	 */
	public SongComparator(Property p, Operator o) throws GenerationException{
		mProperty = p;
		
		if(o == Operator.MAXIMIZE){
			//if maximizing, set multiplier to 1
			mMaxOrMin = -1;
		} else if ( o == Operator.MINIMIZE){
			//if minimizing set multiplier to -1
			mMaxOrMin = 1;
		} else {
			//if invalid operator, set property to null, multiplier to 0 and log an error
			p = null;
			mMaxOrMin = 0;
			System.err.println("Song Comparator: Bad operator given.");
			throw new GenerationException("Song Comparator", "Bad operator given.",
					Cause.REQUEST_MALFORMED);
		}
	}

	@Override
	public int compare(Song songA, Song songB) {
		
		//compare songs based on the assigned property
		switch(mProperty){
		case TITLE:
			return (songA.getTitle().compareTo(songB.getTitle())) * mMaxOrMin;
		case ARTIST:
			return (songA.getArtist().compareTo(songB.getArtist())) * mMaxOrMin;
		case LENGTH:
			return (songA.getMin() * 60 + songA.getSec() - songB.getMin() * 60 
					- songB.getSec()) * mMaxOrMin;
		case ALBUM:
			return (songA.getAlbum().compareTo(songB.getAlbum())) * mMaxOrMin;
		case LAST_PLAYED:
			//for comparator purposes, null translates to "beginning of time"
			if(songA.getLastPlay() == null){
				songA.setLastPlay(new DateTime(0));
			}
			
			if(songB.getLastPlay() == null){
				songB.setLastPlay(new DateTime(0));
			}
			
			return songA.getLastPlay().compareTo(songB.getLastPlay())*mMaxOrMin;
		case PLAY_COUNT:
			return (songA.getPlayCount() - songB.getPlayCount()) * mMaxOrMin;
		case PLAY_COUNT_TOTAL:
			return (songA.getTotalPlayCount() - songB.getTotalPlayCount()) * mMaxOrMin;
		case REQUEST_COUNT_TOTAL:
			return (songA.getTotalRequests() - songB.getTotalRequests()) * mMaxOrMin;
		case REQUEST_COUNT_DAY:
			return (songA.getReqCount() - songB.getReqCount()) * mMaxOrMin;
		case STREAMING:
			//true is considered greater than false here
			if(songA.getIsStreamed() && !songB.getIsStreamed())
				return 1 * mMaxOrMin;
			else if(!songA.getIsStreamed() && songB.getIsStreamed())
				return -1 * mMaxOrMin;
			else
				return 0;
		}
		
		return 0;
	}
}
