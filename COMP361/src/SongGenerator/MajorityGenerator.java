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

/**
 * This generator will return the songs that satisfy a majority of the constraints.
 * @author Alicia Bendz
 *
 */
public class MajorityGenerator extends SongGenerator {
	
	/**
	 * Constructor for the generator.
	 * @param manager The Music Manager of the system.
	 */
	public MajorityGenerator(MusicManager manager){
		super(manager);
	}
	
	@Override
	public Collection<Song> getSongs(int number, List<Constraint> constraints)
		throws GenerationException {
		//create a map to count constraints satisfied by a song and a list for results
		Map<Song, Integer> songs = new HashMap<Song, Integer>();
		LinkedList<Song> result = new LinkedList<Song>();
		List<Constraint> dummyConstraints = new LinkedList<Constraint>();
		dummyConstraints.addAll(constraints);
		
		//variables for what the majority is, the number of constraints
		int majority;
		int constraintCount = 0;
		
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
				if(c.getOperator() == Operator.MAXIMIZE || c.getOperator() == Operator.MINIMIZE)
					continue;
				for(Song s : songs.keySet()){
					if(satisfied(s, c)){
						songs.put(s, songs.get(s).intValue() + 1);
					}
				}
				constraintCount++;
				dummyConstraints.remove(c);
			}
			
			//determine majority
			majority = (int) Math.ceil((constraintCount + 1) / 2.0);
			
			//collect songs that satisfy a majority of constraints
			for(Song s : songs.keySet()){
				if(songs.get(s).intValue() >= majority){
					result.add(s);
				}
			}
			
			//if there are more songs than asked for, remove some
			if(result.size() > number && limit){
				
				//if there is a maximization or minimization constraint, 
				//first sort the songs with the preferred songs at the beginning of the 
				//list
				if(!dummyConstraints.isEmpty()){
					if(dummyConstraints.get(0).getOperator() == Operator.MAXIMIZE 
							|| dummyConstraints.get(0).getOperator() == Operator.MINIMIZE){
						Collections.sort(result, 
								new SongComparator(dummyConstraints.get(0).getProperty(),
										dummyConstraints.get(0).getOperator()));
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
			
			if(dummyConstraints.size() > 1){
				//Just use the first min or max constraint if there were too many but log 
				//the mistake
				System.err.println("Majority Generator: Too many maximization/minimization " 
						+ "constraints.");
				throw new GenerationException("Majority Generator", 
						"Too many maximization/minimization constriants",
								Cause.REQUEST_MALFORMED);
			}
			
		} catch (Exception e) {
			System.err.println("Majority Song Generator: Error retreiving all songs" 
					+ " from database.");
			System.err.println(e.getMessage());
			throw new GenerationException("Majority Generator",
					"Error retriving songs from database",
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
			if(!c.satisfies(new Value(s.getTotalPlayCount())))
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
