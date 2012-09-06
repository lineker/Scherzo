package SongGenerator;

import globalAccess.Global;

/**
 * Enum for song properties.
 * @author Alicia Bendz
 *
 */
public enum Property {
	TITLE,
	ARTIST,
	LENGTH,
	ALBUM,
	LAST_PLAYED,
	PLAY_COUNT,
	PLAY_COUNT_TOTAL,
	REQUEST_COUNT_TOTAL,
	REQUEST_COUNT_DAY,
	STREAMING;
	
	@Override
	public String toString(){
		switch(this){
		case TITLE:
			return Global.getLOCInstance().getLocalizedString("SongProp.title");
		case ARTIST:
			return Global.getLOCInstance().getLocalizedString("SongProp.artist");
		case LENGTH:
			return Global.getLOCInstance().getLocalizedString("SongProp.length");
		case ALBUM:
			return Global.getLOCInstance().getLocalizedString("SongProp.album");
		case LAST_PLAYED:
			return Global.getLOCInstance().getLocalizedString("SongProp.lastPlay");
		case PLAY_COUNT:
			return Global.getLOCInstance().getLocalizedString("SongProp.plays");
		case PLAY_COUNT_TOTAL:
			return Global.getLOCInstance().getLocalizedString("SongProp.playsTotal");
		case REQUEST_COUNT_TOTAL:
			return Global.getLOCInstance().getLocalizedString("SongProp.requestTotal");
		case REQUEST_COUNT_DAY:
			return Global.getLOCInstance().getLocalizedString("SongProp.requests");
		case STREAMING:
			return Global.getLOCInstance().getLocalizedString("SongProp.stream");
		default:
			return null;
		}
	}
}
