package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import MusicManager.Playlist;


/**
 * Feedback Mapper
 * Maps a row from DB to a object Feedback
 * @author lineker
 *
 */
public class PlaylistMapper implements IDataMapper<Playlist> {

    @Override
	/**
	 * Map a row to a Song object
	 * @return Feedback object
	 * @throws SQLException
	 */
    public Playlist mapRow(ResultSet resultSet) throws SQLException, ParseException {
    	Playlist list = new Playlist();
        
    	list.setId(resultSet.getInt("id"));
    	list.setName(resultSet.getString("name"));

        return list;
    }

}