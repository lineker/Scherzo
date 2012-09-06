package ManagerUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import globalAccess.Global;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import DataAccess.ServicePool;
import MusicManager.MusicManager;
import MusicManager.Playlist;
import MusicManager.Song;
import MusicManager.Song.TrackType;

/**
 * Frame displayed that allows users to locate local songs to add to the db.
 * @author Rebecca
 *
 */
@SuppressWarnings("serial")
public class LocateSongFrame extends JPanel {

	/* The MusicManager */
	private MusicManager mManager;
	
	
	@SuppressWarnings("rawtypes")
	private JComboBox mPlaylistBox;
	LinkedList<Playlist> mPLists;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LocateSongFrame(MusicManager m) {

		mManager = m;
		final JPanel panel = new JPanel(new BorderLayout());
		// Find the songs
		final JFileChooser fc = new JFileChooser();
		//int returnVal = fc.showDialog(this, "Add songs to selected playlist");
		mPlaylistBox = new JComboBox();
		// Get all the playlists saved in db.
        mPLists = new LinkedList<Playlist>();
		try {
			mPLists = (LinkedList<Playlist>) ServicePool.PlaylistService().getPlaylistNames();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(mPLists.size() == 0) {
			JOptionPane.showMessageDialog(this, Global.getLOCInstance().getLocalizedString("Error.NoPlaylists"));
		} else {
			
			int index = 0;
			String current = mManager.getmPlaylistName();
	        String[] lists = new String[mPLists.size()];
	        for(int i = 0; i < mPLists.size(); i++) {
	        	lists[i] = mPLists.get(i).getName();
	        	if(lists[i].equals(current)) {
	        		index = i;
	        	}
	        }
	
			mPlaylistBox = new JComboBox(lists);
			mPlaylistBox.setSelectedIndex(index); // Want this to be the current playlist
	
			this.add(mPlaylistBox);
			
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setMultiSelectionEnabled(true);
			fc.addActionListener(new ActionListener() {
	
				@SuppressWarnings("static-access")
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
			        	File[] files = fc.getSelectedFiles();
	
			        	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			        	LinkedList<Song> songs = new LinkedList<Song>();
			        	// Create the Song objects if they don't already exist
			        	Song s = new Song();
			        	for(int i = 0; i < files.length; i++) {
			        		if(true) {
			        			s = new Song(files[i].toString());
			        			if(s.getSrc() != null) {
			        				s.setTrackType(TrackType.local);
				        			// Save in db.
				        			try {
										Global.getSRVInstance().SongService().addSong(s);
									} catch (ClassNotFoundException e1) {
										e1.printStackTrace();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
				        			songs.add(s);
			        			}
			        		}
			        	}
			        	// Add songs to the selected playlist
			        	if(s.getSrc() != null) {
			        		addSongsToPlaylist(songs);
			        	}
			        	setCursor(Cursor.getDefaultCursor());
					} else if(e.getActionCommand().equals(JFileChooser.CANCEL_OPTION)) {
						// Do nothing.
						setVisible(false);
					} else if(e.getActionCommand().equals(JFileChooser.ERROR_OPTION)) {
					}
					
				}
			});
			JLabel playlistText = new JLabel(Global.getLOCInstance().getLocalizedString("AddSongs.SelectPlaylist"));
			JPanel top = new JPanel();
			top.setLayout(new BorderLayout());
			top.add(playlistText, BorderLayout.NORTH);
			top.add(mPlaylistBox, BorderLayout.CENTER);
			panel.add(top, BorderLayout.NORTH);
			panel.add(top, BorderLayout.NORTH);
		}
	
		panel.add(fc, BorderLayout.CENTER);
        
		this.add(panel);
		setVisible(true);
	}
	
	private void addSongsToPlaylist(LinkedList<Song> songs) {
    	int index = mPlaylistBox.getSelectedIndex();

        if(index != -1) {
        	// Just get the playlist ID and name.
        	Playlist selected = mPLists.get(index);
        	mManager.addSongsToPlaylist(selected, songs);
    	}
	}
	
	
}
