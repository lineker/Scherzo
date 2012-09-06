package DataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Interface to map a database row to a object
 * @author lineker
 *
 * @param <T> Object class
 */
public interface IDataMapper<T> {

	public T mapRow(ResultSet resultSet)  throws SQLException, ParseException;

}
