package statistics;

import globalAccess.Global;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is a class that holds a request for statistics. This request is used by the
 * Statistics Generator and created by the statistics ui. This class is immutable.
 * @author Alicia Bendz
 *
 */
public class StatisticsRequest {
	
	/**
	 * The chart type of the request.
	 */
	private ChartType mChartType;
	
	/**
	 * The trait of the request.
	 */
	private Trait mTrait;
	
	/**
	 * The songs listed in the request.
	 */
	private List<Integer> mSongIds;
	
	/**
	 * The start date of the request.
	 */
	private Date mStartTime;
	
	/**
	 * The end date of the request.
	 */
	private Date mEndTime;

	/**
	 * Constructor that initializes all fields.
	 * @param type The chart type of the request. This cannot be null.
	 * @param songs The songs of the request.
	 * @param start The start date of the request. 
	 * @param end The end date of the request.
	 * @param trait The trait of the request. This cannot be null.
	 * @throws StatisticsException
	 */
	public StatisticsRequest(ChartType type, List<Integer> songs,
			Date start, Date end, Trait trait) throws StatisticsException{
		
		if(type == null || trait == null){
			throw new StatisticsException("Must specify chart type and trait.");
		}
		
		if(start != null && end != null && start.after(end))
			throw new StatisticsException("Invalid start and end dates.");
		
		mChartType = type;
		
		if(songs != null){
			mSongIds = new ArrayList<Integer>(); 
			mSongIds.addAll(songs);
		}
		
		mStartTime = start;
		mEndTime = end;
		mTrait = trait;
	}
	
	/**
	 * Get the trait of the request.
	 * @return The trait of the request.
	 */
	public Trait getTrait(){
		return mTrait;
	}

	/**
	 * Get the type of the request.
	 * @return The chart type of the request.
	 */
	public ChartType getChartType() {
		return mChartType;
	}
	
	/**
	 * Get the Songs of the request. This could be null.
	 * @return The songs of the request in a list.
	 */
	public List<Integer> getSongIds(){
		return new ArrayList<Integer>(mSongIds);
	}
	
	/**
	 * Get the start date of the request. This could be null.
	 * @return The start date or null.
	 */
	public Date getStart(){
		return mStartTime;
	}
	
	/**
	 * Get the end date of the request. This could be null.
	 * @return The end date or null.
	 */
	public Date getEnd(){
		return mEndTime;
	}

	@Override
	public String toString(){
		return mChartType + " : " + mTrait + " : " + mSongIds + " : " + mStartTime + " : "
				+ mEndTime;
	}
	
	/**
	 * Return a chart title based on the contents of the request.
	 * @return The generated chart title.
	 */
	public String chartTitle(){
		String title = mTrait.toString() + ": ";
		
		if(mStartTime == null){
			if(mEndTime == null){
				title+= Global.getLOCInstance().getLocalizedString("StatsTrait.allTime");
			}
			else{
				title += Global.getLOCInstance().getLocalizedString("StatsTrait.until") 
						+ " " + mEndTime;
			}
		} else {
			title += Global.getLOCInstance().getLocalizedString("StatsTrait.from")
					+ " " + mStartTime;
			if(mEndTime != null){
				title += Global.getLOCInstance().getLocalizedString("StatsTrait.until") 
						+ " " + mEndTime;
			}
		}
		
		return title;
	}
}
