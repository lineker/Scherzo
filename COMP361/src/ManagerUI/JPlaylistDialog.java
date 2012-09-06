package ManagerUI;

import globalAccess.Global;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import DataAccess.ServicePool;
import MusicManager.MusicManager;
import MusicManager.Playlist;
import MusicManager.Song;

/**
 * Dialog for user to change the playlist.
 * @author Rebecca Young
 */

@SuppressWarnings("serial")
public class JPlaylistDialog extends JDialog implements ActionListener {
    
    /* Components */
    private final JButton mOkButton;
    private final JButton mCancelButton;
    @SuppressWarnings("rawtypes")
	private JList mNameList;
    
    /* Main button panel*/
    private final JPanel mButtonPanel;
    
    private LinkedList<Playlist> mPLists;

    /* List of playlist names */
    private String[] mNames;
    
    /* The Music Manager */
    private MusicManager mManager;
    
    /* View playlist or change playlist? */
    private boolean mode;
    /**
     * Constructor
     * @param frame parent frame
     * @throws Exception 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public JPlaylistDialog(MusicManager m, boolean mode) throws Exception {
    	mManager = m;
    	
    	if(mode) {
        	setTitle(Global.getLOCInstance().getLocalizedString("Sidebar.ChangePlaylist"));
    	} else {
    		setTitle(Global.getLOCInstance().getLocalizedString("Sidebar.ViewAPlaylist"));
    	}

        this.mode = mode;
        mButtonPanel = new JPanel();
        final JPanel panel = new JPanel(new BorderLayout());
        
        mOkButton = new JButton(Global.getLOCInstance().getLocalizedString("Button.OK"));
        mCancelButton = new JButton(Global.getLOCInstance().getLocalizedString("Button.Cancel"));
        mOkButton.addActionListener(this);
        mCancelButton.addActionListener(this);
        
        mButtonPanel.add(mOkButton);
        mButtonPanel.add(mCancelButton);
        
        // Get all the playlists saved in db.
        mPLists = (LinkedList<Playlist>) ServicePool.PlaylistService().getPlaylistNames();
        
        mNames = new String[mPLists.size()];
        for(int i = 0; i < mPLists.size(); i++) {
        	mNames[i] = mPLists.get(i).getName();
        }
        
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        mNameList = new JList(mNames);

        mNameList.setLayoutOrientation(JList.VERTICAL);
        mNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane namesPane = new JScrollPane(mNameList);
        namesPane.setPreferredSize(new Dimension(250, 250));
        
        main.add(namesPane);
        main.add(Box.createVerticalStrut(5));
        
        panel.add(new JLabel(Global.getLOCInstance().getLocalizedString("SelectAPlaylist")), BorderLayout.NORTH);
        
        panel.add(main, BorderLayout.CENTER);
        
        // Force space
        panel.add(new JLabel("        "), BorderLayout.WEST);
        panel.add(new JLabel("        "), BorderLayout.EAST);
        panel.add(mButtonPanel, BorderLayout.SOUTH);
        getContentPane().add(panel);  
  
        setLocationRelativeTo(getContentPane());
        pack();
        setVisible(true);
    }

    /**
     * Determine action based on button click.
     * @param event The triggering action event.
     */
    @SuppressWarnings("static-access")
	@Override
    public void actionPerformed(ActionEvent event) {

        if(mOkButton == event.getSource()){
            int index = mNameList.getSelectedIndex();

            if(index == -1) {
            	// No selection. 
            	return;
        	}
        	// Ignore out of bounds.
        	if(index <= mPLists.size()) {
        		String name = mNames[index];

        		// Get the songs from playlist using playlist ID.
        		LinkedList<Song> pSongs = new LinkedList<Song>();
				try {
					int pid = Global.getSRVInstance().PlaylistService().GetPlaylistIdByName(name);
					pSongs = (LinkedList<Song>) 
							Global.getSRVInstance().SongService().getSongsByPlaylistId(pid);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
        		Playlist p = new Playlist();
        		p.setSongs(pSongs);
        		p.setName(name);
        		
        		if(mode) { // We want to change to this playlist
        			mManager.changePlaylist(p);
        		} else {
        			// We just want to view it
        			try {
        				JFrame f = new JFrame();
						f.add(new JMusicList(1, mManager, p));
						f.pack();
						f.setLocationRelativeTo(null);
						f.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
        		}
        		
        	}
        	
            setVisible(false);
        }
        else if(mCancelButton == event.getSource()) {
        	setVisible(false);
        }
    }
}