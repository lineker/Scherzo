package UI;

import globalAccess.Global;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;

import java.util.Collection;
import java.util.LinkedList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import jgroove.StreamingManager;
import jgroove.IStream.topPlayed;

import DataAccess.ServicePool;
import MusicManager.MusicManager;
import MusicManager.Playlist;
import MusicManager.Song;
import MusicManager.Song.TrackType;

@SuppressWarnings("serial")
public class UIStreaming extends JPanel {
    public static final String[] columnNames = {
        "Title", "Artist", "Album", "Button",""
    };//"Button"

    protected JTable table;
    protected JScrollPane scroller;
    
    protected StreamingTableModel tableModel;
    JTextField queryText;

    StreamingManager streaming = null;
    
    /** The MusicManager */
    private MusicManager mManager;
    
    /** Playlists and combo box */
    private LinkedList<Playlist> mPlaylists;
	@SuppressWarnings("rawtypes") JComboBox mPlaylistBox;

	public UIStreaming(MusicManager m) throws Exception {
    	streaming = new StreamingManager(m);
    	mManager = m;
    	mPlaylists = null;
    	mPlaylistBox = null;
    	
        initComponent();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void initComponent() throws Exception {
        setLayout(new BorderLayout());
		// Get all the playlists saved in db.
        mPlaylists = new LinkedList<Playlist>();
        
 		try {
 			mPlaylists = (LinkedList<Playlist>) ServicePool.PlaylistService().getPlaylistNames();
 		} catch (ClassNotFoundException e) {
 			e.printStackTrace();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
         
		int index = 0;
		String current = mManager.getmPlaylistName();
         String[] lists = new String[mPlaylists.size()];
         for(int i = 0; i < mPlaylists.size(); i++) {
         	lists[i] = mPlaylists.get(i).getName();
        	if(lists[i].equals(current)) {
        		index = i;
        	}
         }

		mPlaylistBox = new JComboBox(lists);
		mPlaylistBox.setSelectedIndex(index); // Want this to be the current playlist

    	//ServicePool srv = new ServicePool();
    	//Collection<Song> songs = srv.SongService().GetSongs();
    	
    	Collection<Song> songs = streaming.top(topPlayed.daily);
    	
        tableModel = new StreamingTableModel(columnNames, songs);
        tableModel.addTableModelListener(new UIStreaming.InteractiveTableModelListener());
        table = new JTable();
        table.setModel(tableModel);
        table.setSurrendersFocusOnKeystroke(true);
        if (!tableModel.hasEmptyRow()) {
            tableModel.addEmptyRow();
        }

        scroller = new javax.swing.JScrollPane(table);
        table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
        //add hidden column which hold id of songs
        TableColumn hidden = table.getColumnModel().getColumn(StreamingTableModel.HIDDEN_INDEX);
        hidden.setMinWidth(2);
        hidden.setPreferredWidth(2);
        hidden.setMaxWidth(2);
        hidden.setCellRenderer(new InteractiveRenderer(StreamingTableModel.HIDDEN_INDEX));
        
        //Implementation of a play button
        TableColumn buttonColumn = table.getColumnModel().getColumn(StreamingTableModel.BUTTON_INDEX);
        
        TableButton buttons = new TableButton();
        buttons.addHandler(new TableButton.TableButtonPressedHandler() {
			
			@Override
			public void onButtonPress(int row, int column) {
				// TODO Auto-generated method stub
				System.out.println("clicked");
				TableColumn hidden = table.getColumnModel().getColumn(StreamingTableModel.HIDDEN_INDEX);
				
				final Song record = (Song)tableModel.dataVector.get(row);
				
				// Play the file in a new thread.
				new Thread() {
					public void run() {
						try {
							//streaming.play(record);
						} catch(Exception e) {
							System.out.println(e);
						}
					}
				}.start();
			}
		});
        
        buttonColumn.setCellRenderer(buttons);
        buttonColumn.setCellEditor(buttons);
        //hiding button
        buttonColumn.setMinWidth(0);
        buttonColumn.setPreferredWidth(0);
        buttonColumn.setMaxWidth(0);
        //finish hiding button
        
        add(scroller, BorderLayout.CENTER);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        
        queryText = new JTextField();
        queryText.setSize(200, 30);
        topPanel.add(queryText, BorderLayout.CENTER);
        topPanel.add(mPlaylistBox, BorderLayout.NORTH);
        JButton submit = new JButton("Search");
        topPanel.add(submit, BorderLayout.EAST);
        submit.addActionListener(new ActionListener() { 
        	public void actionPerformed(ActionEvent e) { 
				try {
					Collection<Song> songs = streaming.search(queryText.getText());
					tableModel.setDataVector(songs);
					table.repaint();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	} 
        });
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        
        JButton addButton = new JButton("Add Selected");
        bottomPanel.add(addButton, BorderLayout.EAST);
        addButton.addActionListener(new ActionListener() { 
        	@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) { 
				try {
					//System.out.println("selected");
		        	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					LinkedList<Song> selectedSongs = new LinkedList<Song>();
					Song record;
					int[] rowIndexes = table.getSelectedRows();
					for (int i = 0; i < rowIndexes.length; i++) {
						
						record = (Song)tableModel.dataVector.get(rowIndexes[i]);
						record.setTrackType(TrackType.streaming);
						selectedSongs.add(record);
						// Add song to the database
						
						Global.getSRVInstance().SongService().addSong(record);
						System.out.println(record.getStreamingID() + " " + record.getTitle() + " " + record.getArtist() + " " + record.getAlbum());
					}
					
					addToSelectedPlaylist(selectedSongs);
					setCursor(Cursor.getDefaultCursor());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	} 
        });
        
        add(topPanel,BorderLayout.NORTH);
        add(bottomPanel,BorderLayout.SOUTH);
    }

    public void highlightLastRow(int row) {
        int lastrow = tableModel.getRowCount();
        if (row == lastrow - 1) {
            table.setRowSelectionInterval(lastrow - 1, lastrow - 1);
        } else {
            table.setRowSelectionInterval(row + 1, row + 1);
        }

        table.setColumnSelectionInterval(0, 0);
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
                if ((UIStreaming.this.tableModel.getRowCount() - 1) == row &&
                   !UIStreaming.this.tableModel.hasEmptyRow())
                {
                	UIStreaming.this.tableModel.addEmptyRow();
                }

                highlightLastRow(row);
            }

            return c;
        }
    }

    public class InteractiveTableModelListener implements TableModelListener {
        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                System.out.println("row: " + row + " column: " + column);
                table.setColumnSelectionInterval(column + 1, column + 1);
                table.setRowSelectionInterval(row, row);
            }
        }
    }

    /*
    private static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            JFrame frame = new JFrame("Interactive Form");
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                    System.exit(0);
                }
            });
            frame.getContentPane().add(new UIStreaming());
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
    
    /**
     * Adds the selected songs to the selected playlist.
     * @param listOfSongs LinkedList<Song> the selected songs to add.
     */
    public void addToSelectedPlaylist(LinkedList<Song> listOfSongs)
    {
    	int index = mPlaylistBox.getSelectedIndex();

        if(index != -1) {
        	// Just get the playlist ID and name.
        	Playlist selected = mPlaylists.get(index);
        	mManager.addSongsToPlaylist(selected, listOfSongs);
    	}
    	//System.out.println("add songs to current playlist");
    }
}
