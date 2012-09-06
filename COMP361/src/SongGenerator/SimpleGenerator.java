package SongGenerator;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import DataAccess.ServicePool;
import MusicManager.MusicManager;
import MusicManager.Song;
import SongGenerator.GenerationException.Cause;

/**
 * Simple song generator that will return song that exactly match the constraints given.
 * @author Alicia Bendz
 *
 */
public class SimpleGenerator extends SongGenerator {
	
	/**
	 * Constructor for the simple generator.
	 * @param manager The Music Manager of the system.
	 */
	public SimpleGenerator(MusicManager manager){
		super(manager);
	}

	@Override
	public Collection<Song> getSongs(int number,
			List<Constraint> constraints) throws GenerationException {
		boolean limit = true;
		
		if(number < 1)
			limit = false;
		
		//create result list
		LinkedList<Song> result = null;
		
		try {
			//get all songs from database
			Collection<Song> songs = ServicePool.SongService().getSongs();
			result = new LinkedList<Song>(songs);
			
			filter(result, constraints);
			
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
			
			if(constraints.size() > 1){
				//Just use the first min or max constraint if there were too many but log 
				//the mistake
				System.err.println("Simple Generator: Too many maximization/minimization " 
						+ "constraints.");
				throw new GenerationException("Simple Generator", 
						"Too many maximization/minimization constriants",
								Cause.REQUEST_MALFORMED);
			}
			
		} catch (Exception e) {
			System.err.println("Simple Song Generator: Error retreiving all songs" 
					+ " from database.");
			System.err.println(e.getMessage());
			throw new GenerationException("Simple Generator",
					"Error retriving songs from database",
					Cause.DATABASE, e);
		}
		
		//return the resulting list of songs
		return result;
	}
}
