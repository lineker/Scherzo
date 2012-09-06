package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

/**
 * Feedback Mapper
 * Maps a row from DB to a object Feedback
 * @author lineker
 *
 */
public class FeedbackMapper implements IDataMapper<Feedback> {

    @Override
	/**
	 * Map a row to a Song object
	 * @return Feedback object
	 * @throws SQLException
	 */
    public Feedback mapRow(ResultSet resultSet) throws SQLException, ParseException {
    	Feedback feed = new Feedback();
        
    	feed.setId(resultSet.getInt("id"));
    	feed.setSource(resultSet.getString("source"));
    	feed.setText(resultSet.getString("text")); 
    	feed.setDate((Date)DataAccess.dateformatYYYYMMDD.parse(resultSet.getString("date")));
        return feed;
    }

}