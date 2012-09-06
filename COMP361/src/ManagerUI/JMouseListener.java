package ManagerUI;

import globalAccess.Global;

import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JTable;

import MusicManager.MusicManager;
import MusicManager.Playlist;
import MusicManager.Song;

public class JMouseListener implements MouseListener {

	private MainFrame mF;
	private JTable mTable;
	private MusicManager mManager;
	
	public JMouseListener(MainFrame m, JTable table, MusicManager mus) {
		mF = m;
		mTable = table;
		mManager = mus;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int i = mTable.getSelectedRow();
		
		switch(i) {
		case 0:
			// View playlist
			if(mManager.getPlaylist() != null) {
				try {
					// Have to refresh! Get the songs from playlist using playlist ID.
	        		LinkedList<Song> pSongs = new LinkedList<Song>();
	        		String currName = mManager.getPlaylist().getName();
					try {
						@SuppressWarnings("static-access")
						int pid = Global.getSRVInstance().PlaylistService().
								GetPlaylistIdByName(currName);
						pSongs = (LinkedList<Song>) 
								Global.getSRVInstance().SongService().getSongsByPlaylistId(pid);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					
	        		Playlist p = new Playlist();
	        		p.setSongs(pSongs);
	        		p.setName(currName);
	        		mManager.changePlaylist(p); // This does the repaint.
				} catch (Exception e1) {
					mF.showMessage(Global.getLOCInstance().getLocalizedString("Error.NoPlaylist"));
					//e1.printStackTrace();
				}
			} else {
				mF.showMessage(Global.getLOCInstance().getLocalizedString("Error.NoPlaylist"));
			}
			break;
		case 1:
			// View Q
			try {
				mF.repaintListArea(0);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case 2:
			// View stats
			try {
				mF.repaintListArea(5);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case 3:
			// View settings
			new JSettings(mManager);
			break;
		case 4:
			// Add songs
			try {
				mF.repaintListArea(4);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case 5:
			// Add streaming songs
			
			if(mManager.ismStreamEnabled()) {
				try {
					mF.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					mF.repaintListArea(2);
					mF.setCursor(Cursor.getDefaultCursor());
				} catch (Exception e1) {
					e1.printStackTrace();
					mF.showMessage(Global.getLOCInstance().getLocalizedString("Error.Streaming"));
				}
			} else {
				mF.showMessage(Global.getLOCInstance().getLocalizedString("Error.Streaming"));
			}
			break;
		case 6:
			// Change plist
			try {
				new JPlaylistDialog(mManager, true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case 7:
			// Play the playlist
			try {
				if(mManager.status() && !mManager.isLive()) {
					mManager.goLive();
					mManager.getActiveQueue().setQueueList(mManager.getPlaylist().getSongs());
				}

			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case 8:
			// Generate a playlist
			try {
				mF.repaintListArea(3);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case 9:
			// Create a new playlist
			new NewPlaylistDialog(mManager);
			break;
		case 10:
			// Delete a Playlist
			try {
				new DeletePlaylist(mManager);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case 11:
			// View  a Playlist
			try {
				new JPlaylistDialog(mManager, false);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
			
		default:
			break;
		}
				
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
