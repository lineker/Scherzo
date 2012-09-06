package SongGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

import MusicManager.MusicManager;
import MusicManager.Song;

/**
 * Abstract superclass for all Song Generators.
 * @author Alicia Bendz
 *
 */
public abstract class SongGenerator {
	
	/**
	 * Music Manager of the system.
	 */
	protected final MusicManager mMusicManager;
	
	/**
	 * Variable that determines how many lists to make in the get songs method for a
	 * target time.
	 */
	private static int SONG_LISTS = 15;
	
	/**
	 * Variable that limits how many iterations to do in the get songs method for a target
	 * time.
	 */
	private static int LOOP_LIMIT = 150;
	
	/**
	 * Milliseconds in a minute.
	 */
	public static final int MILLIS_IN_MIN = 60000;
	
	public SongGenerator(MusicManager manager){
		mMusicManager = manager;
	}
	
	/**
	 * Return some specified number of songs based on certain constraints. 
	 * This method will use the entire database to search for songs.
	 * This method is assume to be used from the UI.
	 * Only one maximization or minimization constraint is allowed.
	 * @param number The number of songs requested.
	 * @param constraints The constraints the songs must meet.
	 * @return The resulting set of songs.
	 */
	abstract public Collection<Song> getSongs(int number, List<Constraint> constraints) 
			throws GenerationException;
	
	/**
	 * Return some specified number of playable songs.
	 * This method will search the current playlist in the Music Manager.
	 * @param number The number of songs needed.
	 * @return The resulting set of songs that match the constraints.
	 * @throws GenerationException The generation exception that can be caused by this method.
	 */
	@SuppressWarnings("serial")
	public Collection<Song> getSongs(int number) {
		LinkedList<Song> songs = new LinkedList<Song>();
		
		//get the songs from the music manager
		songs.addAll(mMusicManager.getPlaylist().getSongs());
		
		//filter out songs that can't be played along with a length > 0 constraint
		filter(songs, 
				new LinkedList<Constraint>(){
					{add(new Constraint(Property.LAST_PLAYED, 
							Operator.LESS, 
							new Value(new DateTime(System.currentTimeMillis() 
									- mMusicManager.getSongReplayGap()*MILLIS_IN_MIN))));}});
		
		rearrange(songs);
		
		//cut down the list until it's the desired size
		while(songs.size() > number){
			songs.removeLast();
		}
		
		return songs;
	}
	
	/**
	 * Return the set of songs from the playlist that can be played and whose total length 
	 * is within a tolerance of a target length.
	 * @param targetLength The target length of the returned songs. This is in minutes.
	 * @param tolerance The tolerance value. This is in minutes.
	 * @return The collection of songs that hopefully is close to the target length.
	 */
	public Collection<Song> getSongs(int targetLength, int tolerance){
		//create necessary variables: song list, result list, constraint list, indices, sum
		List<Song> songs = new LinkedList<Song>();
		LinkedList<Song> result = new LinkedList<Song>();
		List<Constraint> cList = new LinkedList<Constraint>();
		List<LinkedList<Song>> lists = new ArrayList<LinkedList<Song>>(SONG_LISTS);
		targetLength *= 60;
		tolerance *= 60;
		int index = 0;
		boolean check = true;
		int sum = 0;
		int bestSum = 0;
		int bestIndex = -1;

		for(int i = 0; i < SONG_LISTS; i++){
			lists.add(new LinkedList<Song>());
		}

		//get songs and filter out all songs longer than the asked for length + tolerance
		songs.addAll(mMusicManager.getPlaylist().getSongs());
		cList.add(new Constraint(Property.LAST_PLAYED, 
				Operator.LESS, 
				new Value(new DateTime(System.currentTimeMillis() 
						- mMusicManager.getSongReplayGap()*MILLIS_IN_MIN))));

		//filter out songs that can't be played
		filter(songs, cList);
		Collections.shuffle(songs);

		int limit = 0;

		//the idea is to reshuffle the song list repeatedly and "deal" song to each list
		//as long as that list doesn't already have that song and is less than the target
		//length
		while(limit < LOOP_LIMIT){
			Collections.shuffle(songs);
			for(Song s : songs){
				for(int i = 0; i < (SONG_LISTS / 2) ; i++){
					if(!lists.get(index).contains(s) 
							&& !isTarget(lists.get(index), targetLength))
						lists.get(index).add(s);
					index = (index + 1) % 10;
				}
			}

			check = true;
			for(List<Song> l : lists){
				check = check && isTarget(l, targetLength);
			}

			if(check)
				break;

			limit++;
		}

		//calculate the lengths of all song lists and keep track of the best
		for(int i = 0; i < SONG_LISTS ; i++){
			sum = 0;
			for(Song s : lists.get(i)){
				sum += s.getMin() * 60 + s.getSec();
			}
			
			if(Math.abs(sum - targetLength) < Math.abs(bestSum - targetLength)){
				bestSum = sum;
				bestIndex = i;
			}
		}

		if(bestIndex > -1){
			result = lists.get(bestIndex);
			rearrange(result);
			return result;
		}
		
		//return a random list otherwise
		Collections.shuffle(lists);

		result.addAll(lists.get(0));

		rearrange(result);
		return result;
	}
	
	/**
	 * A method that filters out songs from a given list according to given constraints.
	 * This does not look at maximization or minimization constraints.
	 * @param songs The songs to filter.
	 * @param constraints The constraints the must be met for the songs to stay in the list.
	 */
	protected void filter(List<Song> songs, List<Constraint> constraints){
		List<Song> dummyList = new LinkedList<Song>();
		List<Constraint> dummyListC = new LinkedList<Constraint>();
		dummyList.addAll(songs);
		dummyListC.addAll(constraints);
		
		for(Constraint c : dummyListC){
			//for each constraint that isn't max or min, check if the constraint is 
			//satisfied for each song
			//if not, remove from the song collection
			
			if(c.getOperator() == Operator.MAXIMIZE || c.getOperator() == Operator.MINIMIZE)
				continue;
			for(Song s : dummyList){	
				switch(c.getProperty()){
				case TITLE:
					if(!c.satisfies(new Value(s.getTitle())))
						songs.remove(s);
					break;
				case ARTIST:
					if(!c.satisfies(new Value(s.getArtist())))
						songs.remove(s);
					break;
				case LENGTH:
					if(!c.satisfies(new Value(s.getMin() * 60 + s.getSec())))
						songs.remove(s);
					break;
				case ALBUM:
					if(!c.satisfies(new Value(s.getAlbum())))
						songs.remove(s);
					break;
				case LAST_PLAYED:
					if(!c.satisfies(new Value(s.getLastPlay() == null 
						? new DateTime(0L) : s.getLastPlay())))
						songs.remove(s);
					break;
				case PLAY_COUNT:
					if(!c.satisfies(new Value(s.getPlayCount())))
						songs.remove(s);
					break;
				case PLAY_COUNT_TOTAL:
					if(!c.satisfies(new Value(s.getTotalPlayCount())))
						songs.remove(s);
					break;
				case REQUEST_COUNT_TOTAL:
					if(!c.satisfies(new Value(s.getTotalRequests())))
						songs.remove(s);
					break;
				case REQUEST_COUNT_DAY:
					if(!c.satisfies(new Value(s.getReqCount())))
						songs.remove(s);
					break;
				case STREAMING:
					if(!c.satisfies(new Value(s.getIsStreamed())))
						songs.remove(s);
					break;
				default:
					break;
				}
			}
			
			//remove the constraint when all songs have been checked.
			constraints.remove(c);
		}
	}

	/**
	 * Method to check whether a list has reached the target length or not.
	 * @param list The list of songs in question.
	 * @param targetLength The target length.
	 * @return True if the songs in the list sum to the target length or greater.
	 */
	private boolean isTarget(List<Song> list, int targetLength) {
		int sum = 0;
		
		for(Song s : list){
			sum += s.getMin() * 60 + s.getSec();
		}
		
		if(sum >= targetLength)
			return true;
		else
			return false;
	}
	
	private void rearrange(LinkedList<Song> songs){
		LinkedList<Song> playing = mMusicManager.getActiveQueue().getSongs();
		
		for(Song s : playing){
			if(songs.contains(s)){
				songs.remove(s);
				songs.addLast(s);
			}
		}
	}
}
