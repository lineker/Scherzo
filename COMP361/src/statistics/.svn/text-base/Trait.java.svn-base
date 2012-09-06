package statistics;

import globalAccess.Global;

/**
 * Enum created for allowable chart traits.
 * @author Alicia Bendz
 *
 */
public enum Trait {
	CLIENT_TYPE, 
	FEEDBACK, 
	SONG_REQUEST, 
	SONG_PLAYS, 
	TOTAL_REQUEST, 
	STREAMING;
	
	public String toString(){
		switch(this){
		case CLIENT_TYPE:
			return Global.getLOCInstance().getLocalizedString("StatsTrait.client");
		case FEEDBACK:
			return Global.getLOCInstance().getLocalizedString("StatsTrait.feedback");
		case SONG_REQUEST:
			return Global.getLOCInstance().getLocalizedString("StatsTrait.requestsSong");
		case SONG_PLAYS:
			return Global.getLOCInstance().getLocalizedString("StatsTrait.plays");
		case TOTAL_REQUEST:
			return Global.getLOCInstance().getLocalizedString("StatsTrait.requestsAll");
		case STREAMING:
			return Global.getLOCInstance().getLocalizedString("StatsTrait.streaming");
		default:
			return "";
		}
	}
}
