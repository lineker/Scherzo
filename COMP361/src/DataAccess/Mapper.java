package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Static class, where you can call map a collection or a single object
 * @author lineket
 *
 */
public class Mapper {
	
	/**
	 * Map a collection
	 * @param <T>
	 * @param resultSet Result set from DB
	 * @param mapper Implementation of IDataMapper
	 * @return Collection<T>
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public static <T> Collection<T> mapCollection(ResultSet resultSet, IDataMapper<T> mapper) throws SQLException, ParseException {
	    Collection<T> mappedObjects = new LinkedList<T>();
	    while(resultSet.next()) {
	        mappedObjects.add(mapper.mapRow(resultSet));
	    }
	    return mappedObjects;
	}
	
	/**
	 * Map a single object
	 * @param <T>
	 * @param resultSet
	 * @param mapper
	 * @return
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public static <T> T mapSingle(ResultSet resultSet, IDataMapper<T> mapper) throws SQLException, ParseException {
	    return mapper.mapRow(resultSet);
	}
}

