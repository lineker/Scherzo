package ManagerUI;

import globalAccess.Global;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import DataAccess.ServicePool;
import MusicManager.MusicManager;
import MusicManager.Playlist;
import MusicManager.Song;
import MusicManager.Song.TrackType;

@SuppressWarnings("serial")
public class JMusicList extends JPanel {
	
	/** Column titles for the list types */
	protected final String[] mColumnNames = {"Title", "Artist",
			"Album", "Length"};

	/** JComponents for GUI */
    protected JTable mTable;
    protected JScrollPane mScroller;
    
    /** The MusicManager */
    protected MusicManager mManager;
    /** The list of songs to display */
    protected Collection<Song> mList;
    
    /** The table model */
    protected ListTableModel mTableModel;

    /** List types. By default, view all songs in database. */
    public static final int QUEUE = 0;
    public static final int PLAYLIST = 1;
    public static final int ALL_SONGS = 2;
    public static final int VIEW_PLIST = 3;
    
    /** Whether in playlist mode */
    private boolean mPMode = false;
    
    @SuppressWarnings("static-access")
	public JMusicList(int listType, MusicManager m, Playlist p) throws Exception {
    	mManager = m;
    	switch(listType) {
	    	case QUEUE:
	    		if(mManager.getActiveQueue() == null) {
	    			mList = new LinkedList<Song>();
	    		} else {
	    			mList = mManager.getActiveQueue().getSongs();
	    		}
	    		break;
	    	case PLAYLIST:
	    		mPMode = true;
	    		mList = mManager.getPlaylist().getSongs();
	    		break;
	    	case VIEW_PLIST:
	    		mList = p.getSongs();
	    		break;
	    	default:
	    		ServicePool srv = new ServicePool();
	    		mList = srv.SongService().getSongs();
	    		break;
    	}
    	
        initComponent();
    }
    
    public void initComponent() throws Exception {
    	
    	//ServicePool srv = new ServicePool();
    	//Collection<Song> songs = srv.SongService().GetSongs();
 
        mTableModel = new ListTableModel(mColumnNames, mList);
        
        mTable = new JTable();
        mTable.setModel(mTableModel);
        
        mTable.setSurrendersFocusOnKeystroke(true);
        
        try {
            if (!mTableModel.hasEmptyRow()) {
                mTableModel.addEmptyRow();
            }
        } catch (NullPointerException e) {
        	System.err.println("Ignoring null song.");
        }


        mScroller = new javax.swing.JScrollPane(mTable);
        mTable.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
        
        setLayout(new BorderLayout());
        add(mScroller, BorderLayout.CENTER);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        JButton addButton = new JButton("Remove Selected");
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        if(mPMode) {
            bottomPanel.add(addButton, BorderLayout.EAST);
        }
    
        addButton.addActionListener(new ActionListener() { 
        	public void actionPerformed(ActionEvent e) { 
        		if(mPMode) {
    				try {
    		        	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    					LinkedList<Song> selectedSongs = new LinkedList<Song>();
    					int[] rowIndexes = mTable.getSelectedRows();
    					
    					// Get song id from row indexes
    					LinkedList<Song> list = (LinkedList<Song>) mList;
    					for(int i = 0; i < rowIndexes.length; i++) {
    						selectedSongs.add(list.get(rowIndexes[i]));
    					}
    					
    					// Try to remove from the playlist
    					if(!mManager.removeFromSelectedPlaylist(selectedSongs)) {
    						System.err.println("Could not remove all songs from playlist.");
    					}
    					
    					setCursor(Cursor.getDefaultCursor());
    					
    				} catch (Exception e1) {
    					e1.printStackTrace();
    				}
        		}

        	} 
        });
        add(bottomPanel,BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);
    }

    public void highlightLastRow(int row) {
        int lastrow = mTableModel.getRowCount();
        if (row == lastrow - 1) {
            mTable.setRowSelectionInterval(lastrow - 1, lastrow - 1);
        } else {
            mTable.setRowSelectionInterval(row + 1, row + 1);
        }

        mTable.setColumnSelectionInterval(0, 0);
    }

    class InteractiveRenderer extends DefaultTableCellRenderer {
        protected int interactiveColumn;

        public InteractiveRenderer(int interactiveColumn) {
            this.interactiveColumn = interactiveColumn;
        }

        public Component getTableCellRendererComponent(JTable table,
           Object value, boolean isSelected, boolean hasFocus, int row,
           int column)
        {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == interactiveColumn && hasFocus) {
                if ((JMusicList.this.mTableModel.getRowCount() - 1) == row &&
                   !JMusicList.this.mTableModel.hasEmptyRow())
                {
                	JMusicList.this.mTableModel.addEmptyRow();
                }

                highlightLastRow(row);
            }

            return c;
        }
    }
}
