package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import MusicManager.Song;
import MusicManager.Song.TrackType;;

/**
 * Song Mapper
 * Maps a song row from DB to a object Song
 * @author lineker
 *
 */
public class SongMapper implements IDataMapper<Song> {

    @Override
	/**
	 * Map a row to a Song object
	 * @return Song object
	 * @throws SQLException
	 */
    public Song mapRow(ResultSet resultSet) throws SQLException {
        Song song = new Song();
        
        song.setTitle(resultSet.getString("songName"));
        song.setId(resultSet.getInt("id"));
        song.setStreamingID(resultSet.getString("streamingId")); 
        song.setAlbum(resultSet.getString("albumName"));
        song.setArtist(resultSet.getString("artistName"));
        song.setTrackType(TrackType.valueOf(resultSet.getString("trackType")));
        int length = resultSet.getInt("totalTime");
        
        song.setMin(length/60);
        song.setSec(length%60);
        
        song.setTotalPlayCount(resultSet.getInt("playCount"));
        song.setSrc(resultSet.getString("location"));
        /*
        song.Size = resultSet.getInt("size");
        song.TotalTime = resultSet.getInt("totalTime");
        song.DateAdded = resultSet.getDate("dateAdded");
        song.LastPlayed = resultSet.getDate("lastPlayed");
        song.bitRate = resultSet.getInt("bitRate");
        song.PlayCount = resultSet.getInt("playCount");
        song.Location = resultSet.getString("location");
        */
        return song;
    }

}