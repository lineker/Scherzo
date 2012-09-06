package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import statistics.LogRequest;
/**
 * Feedback Mapper
 * Maps a row from DB to a object Feedback
 * @author lineker
 *
 */
public class LogRequestMapper implements IDataMapper<LogRequest> {

    @Override
	/**
	 * Map a row to a LogRequest object
	 * @return Feedback object
	 * @throws SQLException
	 */
    public LogRequest mapRow(ResultSet resultSet) throws SQLException, ParseException {
    	LogRequest log = new LogRequest();
        
    	log.setId(resultSet.getInt("id"));
    	log.setDeviceType(resultSet.getString("deviceType"));
    	log.setSongId(resultSet.getInt("songId"));
    	log.setRequestDate((Date)DataAccess.dateformatYYYYMMDD.parse(resultSet.getString("date")));

        return log;
    }

}