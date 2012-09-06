package SongGenerator;

import globalAccess.Global;

/**
 * An enum listing the possible operations for constraints. Note that some operators only 
 * apply to some properties.
 * EQUALS: Title, artist, length, album, last played, play count, request count total/day, 
 * Streaming
 * GREATER: Title, artist, length, album, last played, play count, request count total/day
 * LESS:Title, artist, length, album, last played, play count, request count total/day
 * GREATER_EQUAL: Title, artist, length, album, last played, play count, request count total/day
 * LESS_EQUAL: Title, artist, length, album, last played, play count, request count total/day
 * NOT_EQUAL: Title, artist, length, album, last played, play count, request count total/day, 
 * Streaming
 * CONTAINS: Title, artist, album
 * NOT_CONTAINS: Title, artist, album
 * MAXIMIZE: length, last played, play count, request count total/day, Streaming
 * MINIMIZE: length, last played, play count, request count total/day, Streaming
 * @author Alicia Bendz
 *
 */
public enum Operator {
	EQUALS,
	GREATER,
	LESS,
	GREATER_EQUAL,
	LESS_EQUAL,
	NOT_EQUAL,
	CONTAINS,
	NOT_CONTAINS,
	MAXIMIZE,
	MINIMIZE;
	
	@Override
	public String toString(){
		switch(this){
		case EQUALS:
			return Global.getLOCInstance().getLocalizedString("SongOps.equal");
		case GREATER:
			return Global.getLOCInstance().getLocalizedString("SongOps.greater");
		case LESS:
			return Global.getLOCInstance().getLocalizedString("SongOps.less");
		case GREATER_EQUAL:
			return Global.getLOCInstance().getLocalizedString("SongOps.equalGreater");
		case LESS_EQUAL:
			return Global.getLOCInstance().getLocalizedString("SongOps.equalLess");
		case NOT_EQUAL:
			return Global.getLOCInstance().getLocalizedString("SongOps.notEqual");
		case CONTAINS:
			return Global.getLOCInstance().getLocalizedString("SongOps.contains");
		case NOT_CONTAINS:
			return Global.getLOCInstance().getLocalizedString("SongOps.notContains");
		case MAXIMIZE:
			return Global.getLOCInstance().getLocalizedString("SongOps.max");
		case MINIMIZE:
			return Global.getLOCInstance().getLocalizedString("SongOps.min");
		default: 
			return null;
		}
	}
}
