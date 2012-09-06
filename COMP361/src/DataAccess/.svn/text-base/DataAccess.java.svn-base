package DataAccess;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import MusicManager.Song;

/**
 * Super class of a Service Class
 * Basic information shared by all services
 * @author lineker
 *
 */
public class DataAccess {
	
	/** Connection variable **/
	//Connection connection = null;
	/** DB name **/
	String dbName = "sample.db";
	
	public static SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	protected java.util.Date beginDate()
	{
		try {
			return (Date)DataAccess.dateformatYYYYMMDD.parse("2011-11-11 11:11:11");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Constructor loads sqlite driver and test to see if database exist.
	 * @throws ClassNotFoundException
	 */
	public DataAccess() throws ClassNotFoundException
	{
		// load the sqlite-JDBC driver using the current class loader
	    Class.forName("org.sqlite.JDBC");
	    TestIfDbExist();
	}
	
	/**
	 * Get database connection instance
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection GetConnection() throws SQLException
	{
		return DriverManager.getConnection("jdbc:sqlite:"+dbName);
		
	}
	
	/**
	 * Test if DB exist in the filesystem if not makes a new copy from /assets/dbName.sqlite
	 */
	private void TestIfDbExist()
	{
		File file=new File(dbName);
		boolean exists = file.exists();
		if(!exists)
		{
			IOHelper.copyfile("assets/"+dbName, dbName);
		}
	}
	
	/**
	 * Generic method to execute update/insert/delete query
	 * @param prep
	 * @return
	 */
	protected int ExecuteInsertUpdateDelete(PreparedStatement prep, Connection connection)
	{
		int id = -1;
		try
	    {			
			prep.executeUpdate();
			
			id = getId(prep);
			//System.out.println(id);
	    }
	    catch(SQLException e)
	    {
	      // if the error message is "out of memory", 
	      // it probably means no database file is found
    	System.err.println(e.getMessage());
	    }
	    finally
	    {
		  try
		  {
		    if(connection != null)
		      connection.close();
		  }
		  catch(SQLException e)
		  {
		    // connection close failed.
		    System.err.println(e);
		  }
	    }
	    
	    return id;
	}
	
	/**
	 * Generic select execution
	 * @param prep prepared query
	 * @param mapper if null will return the value in row and column index 0;
	 * @param isSingleSelection indicates if is a single select or a selection of a list
	 * @return returns a object which can be a single object or a collection<object>
	 */
	@SuppressWarnings("unchecked")
	public Object ExecuteSelect(PreparedStatement prep, IDataMapper mapper, boolean isSingleSelection, Connection connection)
	{
		
		Object obj = null;
		try
	    {
			//execute query
			ResultSet rs = prep.executeQuery();
			if(isSingleSelection && mapper != null)
				obj = Mapper.mapSingle(rs, mapper);
			else if(mapper != null)
				obj = Mapper.mapCollection(rs, mapper);
			else
			{
				obj = (Object)rs.getObject(1);
			}
				
			
	    }
	    catch(SQLException e)
	    {
	      // if the error message is "out of memory", 
	      // it probably means no database file is found
	      System.err.println(e.getMessage());
	    } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    finally
	    {
	      try
	      {
	        if(connection != null)
	          connection.close();
	      }
	      catch(SQLException e)
	      {
	        // connection close failed.
	        System.err.println(e);
	      }
	    }
	    
	    return obj;
	}
	
	private int getId(PreparedStatement prep) throws SQLException
	{
		int id = -1;
		ResultSet rs = prep.getGeneratedKeys();
	    rs.next();
	    id = rs.getInt(1);
		return id;
	}
	
	/**
	 * Deletes all data from database
	 * @throws SQLException 
	 */
	public void clearDatabase()
	{
		Connection connection = null;
		try
	    {		
			connection = GetConnection();
			
			PreparedStatement prep = connection.prepareStatement("delete from logsongplayed");
			prep.executeUpdate();
			
			PreparedStatement prep2 = connection.prepareStatement("delete from logrequest");
			prep2.executeUpdate();
			
			PreparedStatement prep3 = connection.prepareStatement("delete from feedback");
			prep3.executeUpdate();
			
			PreparedStatement prep4 = connection.prepareStatement("delete from song_playlist");
			prep4.executeUpdate();
			
			PreparedStatement prep5 = connection.prepareStatement("delete from playlist");
			prep5.executeUpdate();
			
			PreparedStatement prep6 = connection.prepareStatement("delete from songs");
			prep6.executeUpdate();

			System.out.println("done cleaning database");
	    }
	    catch(SQLException e)
	    {
	      // if the error message is "out of memory", 
	      // it probably means no database file is found
    	System.err.println(e.getMessage());
	    }
	    finally
	    {
		  try
		  {
		    if(connection != null)
		      connection.close();
		  }
		  catch(SQLException e)
		  {
		    // connection close failed.
		    System.err.println(e);
		  }
	    }
		
		
	}
}
