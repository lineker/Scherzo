package ManagerUI;

import globalAccess.Global;

import java.awt.Button;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import statistics.StatisticsUI;

import MusicManager.MusicManager;
import MusicManager.Playlist;
import MusicManager.Song;

/**
 * The sidebar button manager.
 */

@SuppressWarnings("serial")
public class JSidebar_old extends JPanel implements ActionListener {

	private Button[] vButtons;
	private Button[] cButtons;
	private LayoutManager layout;
	
	final private int vELEMENTS = 6;
	final private int cELEMENTS = 5;
	
	/* The MainFrame */
	private MainFrame mF;
	/* The MusicManager */
	private MusicManager mManager;
	

	public JSidebar_old(MainFrame mF, MusicManager m) throws Exception {
		// Try loading the look and feel
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) { }
		
		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		mManager = m;
		setLayout(layout);
		this.mF = mF;
		String[] viewLabels = {Global.getLOCInstance().getLocalizedString("Menu.Playlist"),
				Global.getLOCInstance().getLocalizedString("Sidebar.Queue"), 
				Global.getLOCInstance().getLocalizedString("Menu.Stats"),
				Global.getLOCInstance().getLocalizedString("Sidebar.Settings"), 
				Global.getLOCInstance().getLocalizedString("Sidebar.AddSongs"), 
				Global.getLOCInstance().getLocalizedString("Sidebar.AddStreamedSongs")};
		
		String[] controlLabels = {
				Global.getLOCInstance().getLocalizedString("Sidebar.ChangePlaylist"),
				Global.getLOCInstance().getLocalizedString("Sidebar.PlayPlaylist"), 
				Global.getLOCInstance().getLocalizedString("Sidebar.GeneratePlaylist"),
				Global.getLOCInstance().getLocalizedString("Sidebar.CreatePlaylist"),
				Global.getLOCInstance().getLocalizedString("Sidebar.DeletePlaylist")};
		
		// TODO Brief description of button
		String[] toolTipText= {};
		
		vButtons = new Button[vELEMENTS];
		cButtons = new Button[cELEMENTS];
		
		String openTag = "<html><b><font size = 4>";
		String closeTag = "</b></font></html>";
		
		JLabel view = new JLabel(openTag + Global.getLOCInstance().getLocalizedString("Menu.View")
				+ closeTag);
		view.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(view);
		
		for(int i = 0; i < vELEMENTS; i++) {
			vButtons[i] = new Button();
			vButtons[i].setMaximumSize(new Dimension(550, 50));
			vButtons[i].setLabel(viewLabels[i]);
			vButtons[i].addActionListener(this);
			add(vButtons[i]);
		}
		
		JLabel controls = new JLabel(openTag + Global.getLOCInstance().getLocalizedString("Sidebar.Control")
				+ closeTag);
		controls.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(controls);

		for(int i = 0; i < cELEMENTS; i++) {
			cButtons[i] = new Button();
			cButtons[i].setMaximumSize(new Dimension(550, 50));
			cButtons[i].setLabel(controlLabels[i]);

			cButtons[i].addActionListener(this);
			add(cButtons[i]);
		}
		
		setVisible(true);
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == vButtons[0]) {
			// View playlist
			if(mManager.getPlaylist() != null) {
				try {
					// Have to refresh! Get the songs from playlist using playlist ID.
	        		LinkedList<Song> pSongs = new LinkedList<Song>();
	        		String currName = mManager.getPlaylist().getName();
					try {
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
					JOptionPane.showMessageDialog(this, Global.getLOCInstance().getLocalizedString("Error.NoPlaylist"));
					//e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, Global.getLOCInstance().getLocalizedString("Error.NoPlaylist"));
			}
		} else if(e.getSource() == vButtons[1]) {
			// View Q
			try {
				mF.repaintListArea(0);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if(e.getSource() == vButtons[2]) {
			// View stats
			try {
				mF.repaintListArea(5);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} else if(e.getSource() == vButtons[3]) {
			// View settings
			new JSettings(mManager);

		} else if(e.getSource() == vButtons[4]) {
			// Add songs
			try {
				mF.repaintListArea(4);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if(e.getSource() == vButtons[5]) {
			// Add streaming songs
			
			if(mManager.ismStreamEnabled()) {
				try {
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					mF.repaintListArea(2);
					setCursor(Cursor.getDefaultCursor());
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, Global.getLOCInstance().getLocalizedString("Error.Streaming"));
				}
			} else {
				JOptionPane.showMessageDialog(this, Global.getLOCInstance().getLocalizedString("Error.Streaming"));
			}
			
		} else if(e.getSource() == cButtons[0]) {
			// Change plist
			try {
				new JPlaylistDialog(mManager, true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		} else if(e.getSource() == cButtons[1]) {
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
			
		} else if(e.getSource() == cButtons[2]) {
			// Generate a playlist
			try {
				mF.repaintListArea(3);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if(e.getSource() == cButtons[3]) {
			// Create a new playlist
			new NewPlaylistDialog(mManager);
		} else if(e.getSource() == cButtons[4]) {
			// Delete a Playlist
			try {
				new DeletePlaylist(mManager);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
			
	}
}
