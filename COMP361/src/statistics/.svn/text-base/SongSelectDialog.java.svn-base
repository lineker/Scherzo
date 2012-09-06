package statistics;

import globalAccess.Global;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import DataAccess.ServicePool;
import MusicManager.Song;
/**
 * This is the song select dialog that allows the user to choose which songs (s)he wants
 * statistics generated upon.
 * @author Alicia Bendz
 *
 */
@SuppressWarnings("serial")
public class SongSelectDialog extends JDialog implements ActionListener {
	/**
	 * All the buttons for this dialog.
	 */
	private final JButton mOkayButton;
    private final JButton mCancelButton;
    private final JButton mAddAllButton;
    private final JButton mAddOneButton;
    private final JButton mRemoveOneButton;
    private final JButton mRemoveAllButton;
    private final JButton mSearchButton;
    private final JButton mRefreshButton;
    
    /**
     * Search text for filtering songs.
     */
    private final JTextField mSearchText;
    
    /**
     * The parent UI.
     */
    private final StatisticsUI mParent;
    
    /**
     * The panels for this dialog.
     */
    private final JPanel mButtonPanel;
    private final JPanel mSearchPanel;
    private final JPanel mSongPanel;
    private final JPanel mSelectionPanel;
    private final JPanel mSelectionButtonPanel;
    private final JPanel mMidPanel;
    
    /**
     * Various song lists for filtering and storing selected songs.
     */
    private final List<Song> mAllSongs;
    private final List<Song> mSelectedSongs;
    private final List<Song> mFilteredSongs;
    
    /**
     * List models for displayed lists of songs.
     */
    private final DefaultListModel mSongListModel;
    private final DefaultListModel mSelectedListModel;
    
    /**
     * UI song lists.
     */
    private final JList mSongList; 
    private final JList mSelectedList;
    
    /**
     * Constructor for Song select dialog. Constructed based on parent stats ui and
     * selected list of songs from parent.
     * @param selected Selects list of songs given by parent.
     * @param parent Parent statistics ui.
     */
    public SongSelectDialog(List<Song> selected, StatisticsUI parent){
        super();
        
        //formatting
        setBackground(new Color(214, 217, 223));
        setAlwaysOnTop(true);
        
        //copy selected songs
        mSelectedSongs = new LinkedList<Song>();
        mSelectedSongs.addAll(selected);
        mParent = parent;
        
        //get all songs from the database
        mAllSongs = new LinkedList<Song>();
        
        //initialise filtered songs
        mFilteredSongs = new LinkedList<Song>();
        
        //set up panels
        mButtonPanel = new JPanel();
        mButtonPanel.setBackground(new Color(214, 217, 223));
        mSearchPanel = new JPanel();
        mSearchPanel.setBackground(new Color(214, 217, 223));
        mSongPanel = new JPanel();
        mSongPanel.setBackground(new Color(214, 217, 223));
        mSelectionPanel = new JPanel();
        mSelectionPanel.setBackground(new Color(214, 217, 223));
        mSelectionButtonPanel = new JPanel();
        mSelectionButtonPanel.setBackground(new Color(214, 217, 223));
        mMidPanel = new JPanel();
        mMidPanel.setBackground(new Color(214, 217, 223));
        
        //set up panel layouts
        mButtonPanel.setLayout(new BoxLayout(mButtonPanel, BoxLayout.X_AXIS));
        mSearchPanel.setLayout(new BoxLayout(mSearchPanel, BoxLayout.X_AXIS));
        mSongPanel.setLayout(new BoxLayout(mSongPanel, BoxLayout.Y_AXIS));
        mSelectionPanel.setLayout(new BoxLayout(mSelectionPanel, BoxLayout.Y_AXIS));
        mSelectionButtonPanel.setLayout(new BoxLayout(mSelectionButtonPanel, BoxLayout.Y_AXIS));
        mMidPanel.setLayout(new BoxLayout(mMidPanel, BoxLayout.X_AXIS));
        
        //search input field
        mSearchPanel.add(Box.createHorizontalStrut(5));
        mSearchPanel.add(new JLabel(Global.getLOCInstance()
        		.getLocalizedString("StatsSong.search")));
        mSearchText = new JTextField();
        mSearchPanel.add(mSearchText);
        mSearchPanel.add(Box.createHorizontalStrut(5));
        
        //search button
        mSearchButton = new JButton(new ImageIcon(".\\imgs\\search_lense_16.png"));
        mSearchButton.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        mSearchButton.setBackground(Color.WHITE);
        mSearchPanel.add(mSearchButton);
        mSearchPanel.add(Box.createHorizontalStrut(15));
        mSearchPanel.setSize(40,10);
        
        //songs list
        JPanel songLabel = new JPanel();
        songLabel.setBackground(new Color(214, 217, 223));
        songLabel.setLayout(new BoxLayout(songLabel, BoxLayout.X_AXIS));
        songLabel.add(new JLabel(Global.getLOCInstance()
        		.getLocalizedString("StatsSong.songs")));
        songLabel.add(Box.createHorizontalGlue());
        mRefreshButton = new JButton(new ImageIcon(".\\imgs\\refresh_16.png"));
        mRefreshButton.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        songLabel.add(mRefreshButton);
        mSongPanel.add(songLabel);
        mSongListModel = new DefaultListModel();
        mSongList = new JList(mSongListModel);
        JScrollPane scrollSongs = new JScrollPane(mSongList); 
        mSongPanel.add(scrollSongs);
        
        //add all songs
        for(Song s : mAllSongs){
        	mSongListModel.addElement(s);
        }
        
        //selected songs list
        JPanel selectionLabel = new JPanel();
        selectionLabel.setBackground(new Color(214, 217, 223));
        selectionLabel.setLayout(new BoxLayout(selectionLabel, BoxLayout.X_AXIS));
        selectionLabel.add(new JLabel(Global.getLOCInstance()
        		.getLocalizedString("StatsSong.selection")));
        selectionLabel.add(Box.createHorizontalGlue());
        mSelectionPanel.add(selectionLabel);
        mSelectedListModel = new DefaultListModel();
        mSelectedList = new JList(mSelectedListModel);
        scrollSongs = new JScrollPane(mSelectedList);
        mSelectionPanel.add(scrollSongs);
        
        //add selected songs
        for(Song s : mSelectedSongs){
        	mSelectedListModel.addElement(s);
        }
        
        //create buttons
        mAddAllButton = new JButton(new ImageIcon(".\\imgs\\forward.png"));
        mAddAllButton.setBackground(Color.WHITE);
        mAddAllButton.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        mAddAllButton.setToolTipText(Global.getLOCInstance()
        		.getLocalizedString("StatsSong.add"));
        
        mAddOneButton = new JButton(new ImageIcon(".\\imgs\\next.png"));
        mAddOneButton.setToolTipText(Global.getLOCInstance()
        		.getLocalizedString("StatsSong.addSelected"));
        mAddOneButton.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        mAddOneButton.setBackground(Color.WHITE);
        
        mRemoveOneButton = new JButton(new ImageIcon(".\\imgs\\previous.png"));
        mRemoveOneButton.setBackground(Color.WHITE);
        mRemoveOneButton.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        mRemoveOneButton.setToolTipText(Global.getLOCInstance()
        		.getLocalizedString("StatsSong.removeSelected"));
        
        mRemoveAllButton = new JButton(new ImageIcon(".\\imgs\\backward.png"));
        mRemoveAllButton.setBackground(Color.WHITE);
        mRemoveAllButton.setToolTipText(Global.getLOCInstance()
        		.getLocalizedString("StatsSong.remove"));
        mRemoveAllButton.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        
        //add buttons
        mSelectionButtonPanel.add(Box.createVerticalStrut(15));
        mSelectionButtonPanel.add(mAddAllButton);
        mSelectionButtonPanel.add(Box.createVerticalStrut(5));
        mSelectionButtonPanel.add(mAddOneButton);
        mSelectionButtonPanel.add(Box.createVerticalStrut(5));
        mSelectionButtonPanel.add(mRemoveOneButton);
        mSelectionButtonPanel.add(Box.createVerticalStrut(5));        
        mSelectionButtonPanel.add(mRemoveAllButton);
        
        //add everything to middle panel
        mMidPanel.add(Box.createHorizontalStrut(5));
        mMidPanel.add(mSongPanel);
        mMidPanel.add(Box.createHorizontalGlue());
        mMidPanel.add(Box.createHorizontalStrut(5));
        mMidPanel.add(mSelectionButtonPanel);
        mMidPanel.add(Box.createHorizontalStrut(5));
        mMidPanel.add(Box.createHorizontalGlue());
        mMidPanel.add(mSelectionPanel);
        mMidPanel.add(Box.createHorizontalStrut(5));
        
        //create lower buttons
        mOkayButton = new JButton(Global.getLOCInstance()
        		.getLocalizedString("StatsSong.okay"));
        mOkayButton.setBackground(Color.WHITE);
        mCancelButton = new JButton(Global.getLOCInstance()
        		.getLocalizedString("StatsSong.cancel"));
        mCancelButton.setBackground(Color.WHITE);
        
        //add listeners
        mOkayButton.addActionListener(this);
        mCancelButton.addActionListener(this);
        mAddAllButton.addActionListener(this);
        mAddOneButton.addActionListener(this);
        mRemoveAllButton.addActionListener(this);
        mRemoveOneButton.addActionListener(this);
        mSearchButton.addActionListener(this);
        mRefreshButton.addActionListener(this);
        
        mSearchText.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getSource() == mSearchText 
						&& arg0.getKeyCode() == KeyEvent.VK_ENTER)
					filter();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				//not used
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				//not used
			}});
        
        //add panels
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(Box.createVerticalStrut(10));
        getContentPane().add(mSearchPanel);
        getContentPane().add(Box.createVerticalStrut(10));
        getContentPane().add(mMidPanel);
        getContentPane().add(Box.createVerticalStrut(10));
        getContentPane().add(mButtonPanel);
        getContentPane().add(Box.createVerticalStrut(10));
        getContentPane().setBackground(new Color(214, 217, 223));
       
        //add buttons for ok and cancel
        mButtonPanel.add(Box.createHorizontalStrut(5));
        mButtonPanel.add(mOkayButton);
        mButtonPanel.add(Box.createHorizontalStrut(5));
        mButtonPanel.add(mCancelButton);
        mButtonPanel.add(Box.createHorizontalGlue());
        
        //if database is inaccessible, show alternate ui
        try {
			mAllSongs.addAll(ServicePool.SongService().getSongs());
        } catch (Exception e) {
        	getContentPane().removeAll();
			getContentPane().add(alternateUI());
			e.printStackTrace();
		}
        
        pack();
        setLocationRelativeTo(null);
        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
    	//set selected songs in statistics ui
        if(mOkayButton == event.getSource()){
            setVisible(false);
            mParent.setSelectedSong(mSelectedSongs);
        } else if(mCancelButton == event.getSource()){
        	//close
            setVisible(false);
        } else if(mAddAllButton == event.getSource()){
        	//Add all songs in song list model
        	Song s;
        	
        	for(Object o : mSongListModel.toArray()){
        		mSelectedListModel.addElement(o);
        		s = (Song) o;
        		mSelectedSongs.add(s);
        	}
        } else if(mAddOneButton == event.getSource()){
        	//Add selected songs
        	Song s;
        	
        	for(Object o : mSongList.getSelectedValues()){
        		mSelectedListModel.addElement(o);
        		s = (Song) o;
        		mSelectedSongs.add(s);
        	}
        } else if(mRemoveOneButton == event.getSource()){
        	//Remove selected songs
        	Object[] remove = mSelectedList.getSelectedValues();
        	Song s;
        	
        	for(Object o : remove){
        		mSelectedListModel.removeElement(o);
        		s = (Song) o;
        		mSelectedSongs.remove(s);
        	}
        } else if(mRemoveAllButton == event.getSource()){
        	//remove all selected songs
        	mSelectedListModel.removeAllElements();
        	mSelectedSongs.clear();
        } else if(mSearchButton == event.getSource()){
        	//search songs
        	filter();
        } else if(mRefreshButton == event.getSource()){
        	//reset the songs
        	mSearchText.setText("");
        	filter();
        }
    }
    
    @Override
    public void setVisible(boolean visible){
    	super.setVisible(visible);
    	
    	//reset songs list and selected lists
    	if(visible){
    		mSearchText.setText("");
    		mSelectedSongs.clear();
    		mSelectedSongs.addAll(mParent.getSelectedSongs());
    		mSelectedListModel.removeAllElements();
    		mSongListModel.removeAllElements();
    		mFilteredSongs.clear();
    		
    		for(Song s : mSelectedSongs){
    			mSelectedListModel.addElement(s);
    		}
    		
    		for(Song s : mAllSongs){
    			mSongListModel.addElement(s);
    		}
    	}
    }
    
    /**
     * Only show songs that contain the text in the search field. If the search field
     * is empty display all the songs.
     */
    private void filter(){
    	String filterText = mSearchText.getText();
    	mSongListModel.clear();
    	mFilteredSongs.clear();
    	
    	//display all songs if filter is empty
    	if(filterText.equals("")){
    		for(Song s : mAllSongs){
    			mSongListModel.addElement(s);
    		}
    	} else {
    		//filter songs and add them to UI
    		for(Song s : mAllSongs){
    			if(s.toString().toLowerCase().contains(filterText.toLowerCase())){
    				mFilteredSongs.add(s);
    			}
    		}
    		
    		for(Song s : mFilteredSongs){
    			mSongListModel.addElement(s);
    		}
    	}
    	mSearchText.setText("");
    }
    
    /**
     * Alternative display when songs cannot be retrieved from the database.
     * @return Return the panel of the alternative UI.
     */
    private JPanel alternateUI(){
    	
    	JPanel altUi = new JPanel();
    	altUi.setLayout(new BoxLayout(altUi, BoxLayout.X_AXIS));
    	altUi.add(new JLabel(new ImageIcon(".\\imgs\\bonus.png")));
    	altUi.add(Box.createHorizontalStrut(5));
    	altUi.add(new JLabel(Global.getLOCInstance().getLocalizedString("StatsSong.noSongs")));
    	
    	return altUi;
    }
}

