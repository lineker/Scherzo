package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import MusicManager.Song;

/**
 * Database access service which is focused on dealing with songs
 * @author lineker
 *
 */
public class SongService extends DataAccess {
	
	/**
	 * Default Constructor
	 * @throws ClassNotFoundException
	 */
	public SongService() throws ClassNotFoundException
	{
		super();
	}
	
	/**
	 * Get all songs
	 * @return Collection<Song>
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public Collection<Song> getSongs() throws SQLException
	{
		Collection<Song> songs = null;
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("select * from Songs");
		songs = (Collection<Song>) this.ExecuteSelect(prep, new SongMapper(), false, connection);
	    
	    return songs;
	}
	
	/**
	 * Get songs given a list of songIds
	 * @param ids
	 * @return Collection of Songs
	 * @throws SQLException
	 */
	public Collection<Song> getSongs(int[] ids) throws SQLException
	{
		Collection<Song> songs = new LinkedList<Song>();
		
		for (int i = 0; i < ids.length; i++) {
			songs.add(getSongById(ids[i]));
		}
		
		return songs;
	}
	
	/**
	 * Get collection of songs that belong to playlist
	 * @param playlistId
	 * @return collection of songs
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Song> getSongsByPlaylistId(int playlistId) throws SQLException
	{
		Collection<Song> songs = null;
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("select * from Songs " +
																"inner join Song_Playlist on Song_Playlist.songId = Songs.id " +
																"where Song_Playlist.playlistId = ?");
		prep.setInt(1, playlistId);
		songs = (Collection<Song>) this.ExecuteSelect(prep, new SongMapper(), false, connection);
	    
	    return songs;
	}
	
	/**
	 * Get a Song by Id
	 * @param id
	 * @return Song
	 * @throws SQLException 
	 */
	public Song getSongById(int id) throws SQLException
	{
		Song song = null;
		
		Connection connection2 = GetConnection();
		
		PreparedStatement prep = connection2.prepareStatement("select * from Songs where Id ="+id);
		
		song = (Song) this.ExecuteSelect(prep, new SongMapper(), true, connection2);
	    
	    return song;
	}
	
	/**
	 * Add a song to db
	 * @param song
	 * @return Song with new Id
	 * @throws SQLException 
	 */
	public Song addSong(Song song) throws SQLException
	{
		// create a database connection
		Connection connection2 = GetConnection();
		
		PreparedStatement prep = connection2.prepareStatement("insert into Songs (streamingId,songName,artistName,albumName,trackType,size,totalTime,bitRate,playCount, dateAdded, location) " +
																				"values (?,?,?,?,?,?,?,?,?,?,?);");
		
		prep.setString(1, song.getStreamingID());
		prep.setString(2, song.getTitle());
		prep.setString(3, song.getArtist());
		prep.setString(4, song.getAlbum());
		prep.setString(5, song.getTrackType().toString());
		//size file not implemented to song object
		prep.setInt(6, 0);
		prep.setInt(7, song.getSec() + (song.getMin() * 60));
		//bitrate file not implemented to song object
		prep.setInt(8, 0);
		prep.setInt(9, song.getPlayCount());
		prep.setString(10, dateformatYYYYMMDD.format( new Date() ));
		prep.setString(11, song.getSrc());
		
		int id = this.ExecuteInsertUpdateDelete(prep, connection2);
		song.setId(id);
	   
	    return song;
	}
	
	/**
	 * @deprecated
	 * Update a song / Do not use this method
	 * @param song
	 * @throws Exception 
	 */
	public void updateSong(Song song) throws Exception
	{

		// create a database connection
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("UPDATE Songs SET streamingId=?, songName=?, artistName=?, albumName=?, " +
																					"trackType=?, totalTime=?, bitRate=?, playCount=? " +
																					"WHERE id=?");
		/*
		prep.setString(1, song.getStreamingID());
		prep.setString(2, song.getTitle());
		prep.setString(3, song.getArtist());
		prep.setString(4, song.getAlbum());
		prep.setString(5, song.getTrackType().toString());
		//prep.setInt(6, song.Size);
		prep.setInt(7, song.getSec() + (song.getMin() * 60));
		prep.setInt(8, song.bitRate);
		prep.setInt(9, song.PlayCount);
		prep.setInt(10, song.Id);*/
		
		throw new Exception("Song Update not implemented");
		
		//this.ExecuteInsertUpdateDelete(prep);

	}
	
	/**
	 * Delete a song
	 * @param id
	 * @throws SQLException 
	 */
	public void deleteSong(int id) throws SQLException
	{

		// create a database connection
		Connection connection = GetConnection();

		PreparedStatement prep = connection.prepareStatement("DELETE FROM Songs WHERE id="+id);
		
		this.ExecuteInsertUpdateDelete(prep, connection);
	    
	}
	
	/**
	 * Increments by +1 the song global counter
	 * @param songId
	 * @return you can ignore return value
	 * @throws SQLException
	 */
	public boolean updateRequestCount(int songId) throws SQLException
	{
		// create a database connection
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("update Songs set playCount = playCount + 1 where id = ?");
		prep.setInt(1, songId);
		
		int count = this.ExecuteInsertUpdateDelete(prep, connection);
		System.out.println("updated playcount to +1");
		if(count > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Updates LastPlayed property to current time the song
	 * @param songId
	 * @return you can ignore return value
	 * @throws SQLException
	 */
	public boolean updateLastPlayed(int songId) throws SQLException
	{
		// create a database connection
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("update Songs set lastPlayed = ? where id = ?");
		prep.setString(1, dateformatYYYYMMDD.format( new Date() ));
		prep.setInt(2, songId);
		
		int count = this.ExecuteInsertUpdateDelete(prep, connection);
		
		connection = GetConnection();
		
		PreparedStatement prep2 = connection.prepareStatement("insert into LogSongPlayed (songId,date) VALUES(?,?)");
		prep2.setString(2, dateformatYYYYMMDD.format( new Date() ));
		prep2.setInt(1, songId);
		
		this.ExecuteInsertUpdateDelete(prep2, connection);
		System.out.println("updated lastPlayed propertie");
		if(count > 0)
			return true;
		else
			return false;
	}
}

//http://www.zentus.com/sqlitejdbc/usage.html
//http://www.xerial.org/trac/Xerial/wiki/SQLiteJDBC#Usage
//EXAMPLES
//statement.executeUpdate("drop table if exists person");
//statement.executeUpdate("create table person (id integer, name string)");
//statement.executeUpdate("insert into person values(1, 'leo')");
//statement.executeUpdate("insert into person values(2, 'yui')");