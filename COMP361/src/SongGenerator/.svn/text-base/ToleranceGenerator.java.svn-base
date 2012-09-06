package SongGenerator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import DataAccess.ServicePool;
import MusicManager.MusicManager;
import MusicManager.Song;
import SongGenerator.GenerationException.Cause;

public class ToleranceGenerator extends SongGenerator {
	/**
	 * The number of constraints a song is allowed to fail.
	 */
	private final int mTolerance;

	/**
	 * Constructor for Tolerance Generator.
	 * @param manager The music manager of the system.
	 * @param tolerance The number of constraints a song can fail. The "tolerance" of the
	 * generator.
	 */
	public ToleranceGenerator(MusicManager manager, int tolerance) {
		super(manager);
		mTolerance = tolerance;
	}

	@Override
	public Collection<Song> getSongs(int number, List<Constraint> constraints)
		throws GenerationException {
		//create a map to count constraints satisfied by a song and a list for results
		Map<Song, Integer> songs = new HashMap<Song, Integer>();
		LinkedList<Song> result = new LinkedList<Song>();
		
		//variables for what the minimum number of satisfied constraints is and
		//the number of constraints
		int minConstraints;
		int constraintCount = 0;
		int minMax = 0;
		
		//indicates if a certain amount of songs must be returned
		boolean limit = true;
		
		if(number < 1)
			limit = false;
		
		try {
			//get songs
			for(Song s: ServicePool.SongService().getSongs()){
				songs.put(s, 0);
			}
			
			//for each constraint, increment the count of songs that satisfy it
			for(Constraint c : constraints){
				if(c.getOperator() == Operator.MAXIMIZE || c.getOperator() == Operator.MINIMIZE){
					minMax++;
					continue;
				}
				for(Song s : songs.keySet()){
					if(satisfied(s, c)){
						songs.put(s, songs.get(s).intValue() + 1);
					}
				}
				constraintCount++;
			}
			
			//determine minConstraints
			minConstraints = constraintCount - mTolerance;
			
			//collect songs that satisfy a tolerable number of constraints
			for(Song s : songs.keySet()){
				if(songs.get(s).intValue() >= minConstraints){
					result.add(s);
				}
			}
			
			//if there are more songs than asked for, remove some
			if(result.size() > number && limit){
				
				//if there is a maximization or minimization constraint, 
				//first sort the songs with the preferred songs at the beginning of the 
				//list
				if(!constraints.isEmpty()){
					if(constraints.get(0).getOperator() == Operator.MAXIMIZE 
							|| constraints.get(0).getOperator() == Operator.MINIMIZE){
						Collections.sort(result, 
								new SongComparator(constraints.get(0).getProperty(),
								constraints.get(0).getOperator()));
					}
				} else {
					//if there is no maximization or minimization, shuffle the list for 
					//randomness
					Collections.shuffle(result);
				}
				
				//remove songs from the end of the list until the right number is obtained
				while(result.size() > number){
					result.removeLast();
				}
			}
			
			if(minMax > 1){
				//Just use the first min or max constraint if there were too many but log 
				//the mistake
				System.err.println("Tolerance Generator: Too many maximization/minimization " 
						+ "constraints.");
				throw new GenerationException("Tolerance Generator", 
						"Too many maximization/minimization constriants",
								Cause.REQUEST_MALFORMED);
			}
			
		} catch (Exception e) {
			System.err.println("Tolerance Song Generator: Error...");
			System.err.println(e.getMessage());
			throw new GenerationException("Tolerance Generator",
					"",
					Cause.DATABASE, e);
		}
		
		return result;
	}

	/**
	 * Method that checks if a song satisfies a constraint.
	 * @param s Song in question.
	 * @param c Constraint in question.
	 * @return True if the song satisfies the constraint, False otherwise.
	 */
	public static boolean satisfied(Song s, Constraint c) {
		
		switch(c.getProperty()){
		case TITLE:
			if(!c.satisfies(new Value(s.getTitle())))
				return false;
			else
				break;
		case ARTIST:
			if(!c.satisfies(new Value(s.getArtist())))
				return false;
			else
				break;
		case LENGTH:
			if(!c.satisfies(new Value(s.getMin() * 60 + s.getSec())))
				return false;
			else
				break;
		case ALBUM:
			if(!c.satisfies(new Value(s.getAlbum())))
				return false;
			else
				break;
		case LAST_PLAYED:
			if(!c.satisfies(new Value(s.getLastPlay())))
				return false;
			else
				break;
		case PLAY_COUNT:
			if(!c.satisfies(new Value(s.getPlayCount())))
				return false;
			else
				break;
		case PLAY_COUNT_TOTAL:
			if(!c.satisfies(new Value(s.getPlayCount())))
				return false;
			else
				break;
		case REQUEST_COUNT_TOTAL:
			if(!c.satisfies(new Value(s.getTotalRequests())))
				return false;
			else
				break;
		case REQUEST_COUNT_DAY:
			if(!c.satisfies(new Value(s.getReqCount())))
				return false;
			else
				break;
		case STREAMING:
			if(!c.satisfies(new Value(s.getIsStreamed())))
				return false;
			else
				break;
		default:
			break;
		}

		return true;
	}
}
