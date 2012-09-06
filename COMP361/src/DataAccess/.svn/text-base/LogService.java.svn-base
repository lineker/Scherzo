package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collection;

import statistics.LogRequest;


public class LogService extends DataAccess {
	
	/**
	 * Default Constructor
	 * @throws ClassNotFoundException
	 */
	public LogService() throws ClassNotFoundException
	{
		super();
	}
	
	/**
	 * Get request by range date
	 * @param from -> if from == null, assume beginning of time
	 * @param to -> if to == null, assume present date
	 * @return collection of requests
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Collection<LogRequest> getLogRequestsByDateRange(java.util.Date from, java.util.Date to) throws Exception
	{
		Collection<LogRequest> logs = null;
		
		if(from == null)
			from = beginDate();
		if(to == null)
			to = new java.util.Date();
		
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("select * from LogRequest where date BETWEEN ? AND ?");
		//prep.setDate(1, new java.sql.Date(from.getTime()));
		//prep.setDate(2, new java.sql.Date(to.getTime()));
		
		prep.setString(1, dateformatYYYYMMDD.format( from ));
		prep.setString(2, dateformatYYYYMMDD.format( to ));
		
		logs = (Collection<LogRequest>) this.ExecuteSelect(prep, new LogRequestMapper(), false,connection);
		System.out.println("returned list size :"+logs.size());//TODO: please remove
		return logs;
	}
	
	/**
	 * Get request by range date and songId
	 * @param from -> if from == null, assume beginning of time
	 * @param to -> if to == null, assume present date
	 * @return collection of requests
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Collection<LogRequest> getLogRequestsByDateRangeAndSongId(java.util.Date from, java.util.Date to, int songId) throws Exception
	{
		Collection<LogRequest> logs = null;
		
		if(from == null)
			from = beginDate();
		if(to == null)
			to = new java.util.Date();
		
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("select * from LogRequest where (date BETWEEN ? AND ?) AND (songId = ?)");
		prep.setString(1, dateformatYYYYMMDD.format( from ));
		prep.setString(2, dateformatYYYYMMDD.format( to ));
		prep.setInt(3, songId);
		logs = (Collection<LogRequest>) this.ExecuteSelect(prep, new LogRequestMapper(), false,connection);
		System.out.println("returned list size :"+logs.size());
		return logs;
	}
	
	/**
	 *  Store a log request for the given song from src and add the present time as the date
	 * @param src device type
	 * @param songId
	 * @returnid of the new entry
	 * @throws Exception
	 */
	public int insertLogRequest(String src, int songId) throws Exception
	{
		// create a database connection
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("insert into LogRequest (deviceType,songId,date) values " +
											"(?,?,?)", Statement.RETURN_GENERATED_KEYS);
		//"+ feedback.getTitle()+","+feedback.getText()+","+feedback.getSource()+","+feedback.getDate()+"
		
		prep.setString(1, src);
		prep.setInt(2, songId);
		//prep.setDate(4, new java.sql.Date(log.getRequestDate().getTime()));
		prep.setString(3, dateformatYYYYMMDD.format( new java.util.Date() ));
		//YYYY-MM-DD HH:MM:SS.SSS
		int id = this.ExecuteInsertUpdateDelete(prep, connection);
		System.out.println("log inserted id="+id);
	    return id;
	}
}

