package DataAccess;

/**
 * Service Pool class represent a entry point to all services that have access to the database.
 * @author lineket
 *
 */
public class ServicePool {

	/** Private Reference to Service **/
	private static SongService mSongService = null;
	
	/** Private Reference to Service **/
	private static FeedbackService mFeedbackService = null;
	
	/** Private Reference to Service **/
	private static LogService mLogService = null;
	
	/** Private Reference to Service **/
	private static PlaylistService mPlaylistService = null;
	
	/**
	 * Get a SongService instance
	 * @return SongService object
	 * @throws ClassNotFoundException
	 */
	public static SongService SongService() throws ClassNotFoundException
	{
		//lazy instantiation
		if(mSongService == null)
			mSongService = new SongService();
		return mSongService;
			
	}
	
	/**
	 * Get a SongService instance
	 * @return SongService object
	 * @throws ClassNotFoundException
	 */
	public static FeedbackService FeedbackService() throws ClassNotFoundException
	{
		//lazy instantiation
		if(mFeedbackService == null)
			mFeedbackService = new FeedbackService();
		return mFeedbackService;
			
	}
	
	/**
	 * Get a SongService instance
	 * @return SongService object
	 * @throws ClassNotFoundException
	 */
	public static LogService StatisticsService() throws ClassNotFoundException
	{
		//lazy instantiation
		if(mLogService == null)
			mLogService = new LogService();
		return mLogService;
			
	}
	
	/**
	 * Get a SongService instance
	 * @return SongService object
	 * @throws ClassNotFoundException
	 */
	public static PlaylistService PlaylistService() throws ClassNotFoundException
	{
		//lazy instantiation
		if(mPlaylistService == null)
			mPlaylistService = new PlaylistService();
		return mPlaylistService;
			
	}
	
}
