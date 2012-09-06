package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import MusicManager.Playlist;


public class PlaylistService extends DataAccess {
	
	/**
	 * Default Constructor
	 * @throws ClassNotFoundException
	 */
	public PlaylistService() throws ClassNotFoundException
	{
		super();
	}

	/**
	 * Returns a collection of playlist with Id and name.
	 * Playlist returned dont have songs
	 * @return collection of playlist
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Collection<Playlist> getPlaylistNames() throws Exception
	{
		Collection<Playlist> playlists = null;
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("select * from Playlist");
		playlists = (Collection<Playlist>) this.ExecuteSelect(prep, new PlaylistMapper(), false,connection);
	    
	    return playlists;
	}
	
	/**
	 * Return boolean indicating if playlist exist
	 * @param name
	 * @return true if playlist exist, false otherwise
	 * @throws SQLException
	 */
	public boolean doesPlaylistExist(String name) throws SQLException
	{
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("select id from Playlist where name = ?");
		prep.setString(1, name);
		Integer id = (Integer) this.ExecuteSelect(prep, null, false,connection);
		
		if(id == null)
			return false;
		else if(id > 0)
			return true;
		else
			return false;
		
	}
	
	public int GetPlaylistIdByName(String name) throws SQLException
	{
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("select id from Playlist where name = ?");
		prep.setString(1, name);
		Integer id = (Integer) this.ExecuteSelect(prep, null, false,connection);
		
		return id;
		
	}
	
	/**
	 * Add new playlist to database
	 * @param name
	 * @return id of new playlist
	 * @throws Exception
	 */
	public int insertPlaylist(String name) throws Exception
	{
		// create a database connection
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("insert into Playlist (name) values " +
											"(?)", Statement.RETURN_GENERATED_KEYS);
		//"+ feedback.getTitle()+","+feedback.getText()+","+feedback.getSource()+","+feedback.getDate()+"
		
		prep.setString(1, name);

		int id = this.ExecuteInsertUpdateDelete(prep,connection);

	    return id;
	}
	

	/**
	 * Delete playlist
	 * @param id
	 * @throws Exception
	 */
	public void deletePlaylist(int id) throws Exception
	{
		Connection connection = super.GetConnection();

		PreparedStatement prep = connection.prepareStatement("DELETE FROM Playlist WHERE id="+id);
		
		this.ExecuteInsertUpdateDelete(prep,connection);
	}
	
	/**
	 * Change name of playlist
	 * @param playlistId
	 * @param newName the new name
	 * @throws Exception
	 */
	public void updatePlaylist(int playlistId, String newName) throws Exception
	{
		// create a database connection
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("update Playlist set name = ?", Statement.RETURN_GENERATED_KEYS);
		//"+ feedback.getTitle()+","+feedback.getText()+","+feedback.getSource()+","+feedback.getDate()+"
		
		prep.setString(1, newName);

		int id = this.ExecuteInsertUpdateDelete(prep,connection);
	}
	
	public void addSongsToPlaylist(int playlistId, int[] songIds) throws SQLException
	{
		for (int i = 0; i < songIds.length; i++) {
			addSongToPlaylist(playlistId,songIds[i]);
		}
	}
	
	public void addSongToPlaylist(int playlistId, int songId) throws SQLException
	{
		// create a database connection
		Connection connection = GetConnection();
		
		PreparedStatement prep = connection.prepareStatement("insert into Song_Playlist (songId,playlistId) values " +
											"(?,?)", Statement.RETURN_GENERATED_KEYS);
		//"+ feedback.getTitle()+","+feedback.getText()+","+feedback.getSource()+","+feedback.getDate()+"
		
		prep.setInt(1, songId);
		prep.setInt(2, playlistId);

		this.ExecuteInsertUpdateDelete(prep,connection);
	}
	
}