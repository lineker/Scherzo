package ManagerUI;

import globalAccess.Global;

import java.awt.Color;
import java.awt.Container;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import statistics.StatisticsUI;
import MusicManager.MusicManager;
import SongGenerator.SimpleGenerator;
import SongGenerator.SongGeneratorUI;
import UI.UIStreaming;

/**
 * @author Rebecca
 * The main JFrame for viewing the MusicManager and its components.
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    
	private JPlayerControls controls;
	private MusicManager mManager;
	
	/* The componenents */
	private JPanel mInfo;
	private JPanel pInfo;
	private JPanel mListArea;
	
	/* The current list view Q=0, Playlist=1, Streamed songs=2 */
	private int currListView;
	
	private GridBagLayout gridbag;
	private Container contentPane;
	
	/* The menu items */
	JMenuItem[] mItems = new JMenuItem[12];
	
	public MainFrame(MusicManager manager) throws Exception {
		currListView = 1;
		
		/* menu header labels */
		String[] mHLabels = 
			{Global.getLOCInstance().getLocalizedString("Menu.File"), 
				Global.getLOCInstance().getLocalizedString("Menu.View"), 
				Global.getLOCInstance().getLocalizedString("Menu.MusicPlayer"),
				Global.getLOCInstance().getLocalizedString("Menu.Help")};
		
		/* menu item labels */
		String[][] mILabels = 
			{{Global.getLOCInstance().getLocalizedString("Menu.OpenP"), 
				Global.getLOCInstance().getLocalizedString("CreatePlaylist"),
				Global.getLOCInstance().getLocalizedString("Menu.GenerateP"),
				Global.getLOCInstance().getLocalizedString("Menu.Exit")},
				{Global.getLOCInstance().getLocalizedString("Sidebar.Queue"), 
					Global.getLOCInstance().getLocalizedString("Menu.MyP"),
					Global.getLOCInstance().getLocalizedString("Menu.Stats"),
					Global.getLOCInstance().getLocalizedString("Sidebar.Settings")},
				{Global.getLOCInstance().getLocalizedString("Menu.Play"), 
							Global.getLOCInstance().getLocalizedString("Menu.Pause"),
					Global.getLOCInstance().getLocalizedString("Menu.Stop")},
				{ Global.getLOCInstance().getLocalizedString("Menu.About")}};
		
		/* The menu headers */
		JMenu[] mHeaders = new JMenu[mHLabels.length];

		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mManager.closeAll();
				System.exit(0);
			}
		});
		mManager = manager;
		
		mInfo = new JPanel();
		pInfo = new JPanel();
		mListArea = new JMusicList(currListView, mManager, null);
		
		//setBackground(Color.LIGHT_GRAY);
		
		// Fill screen
		//Toolkit tk = Toolkit.getDefaultToolkit();
		setPreferredSize(new Dimension(900, 600));
		
		contentPane = getContentPane();
		gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		contentPane.setLayout(gridbag);
		
		// Weight constraints
		gridbag.columnWeights = new double[] {1.5f, 1.0f, 1.0f, 1.5f, 1.0f};
		gridbag.rowWeights = new double[]{0.0f, 0.3f, 0.3f, 10.0f};
		
		setTitle(Global.getLOCInstance().getLocalizedString("ProjectName"));
		
		// Header menu bar 
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		int offset = 0;
		// Add headers
		for(int i = 0; i < mHLabels.length; i++) {
			mHeaders[i] = new JMenu(mHLabels[i]);
			// Add items
			for(int j = 0; j < mILabels[i].length; j++) {
				mItems[offset + j] = new JMenuItem(mILabels[i][j]);
				mHeaders[i].add(mItems[offset + j]);
				
			}
			offset += mILabels[i].length;
			menuBar.add(mHeaders[i]);
		}
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 5;	
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
		gridbag.setConstraints(menuBar, c);
		contentPane.add(menuBar);
		
		controls = new JPlayerControls(manager, this);
		GridBagConstraints c2 = new GridBagConstraints();
		controls.setBackground(Color.lightGray);
		c2.gridx = 0;
		c2.gridy = 1;
		c2.gridwidth = 1;
		c2.gridheight = 2;
		c2.fill = GridBagConstraints.BOTH;
		c2.anchor = GridBagConstraints.WEST;
		gridbag.setConstraints(controls, c2);
		contentPane.add(controls);	
		
		paintHeaderFiller();
		
		repaintSongInfo(null);
		repaintPlaylistInfo(null);
		
		JSidebar sidebar = new JSidebar(this, mManager);
		sidebar.setBackground(Color.gray);
		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 2;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTH;
		gridbag.setConstraints(sidebar, c);
		contentPane.add(sidebar);
		
		repaintListArea(0);
		addListeners();
		setLocationRelativeTo(null);
		refreshAll();
	}

	private void addListeners() {

		mItems[0].addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				// Open playlist
				try {
					new PlaylistDialog(mManager, true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
 		});		
		mItems[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// New Playlist
				try {
					new NewPlaylistDialog(mManager);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mItems[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Generate a Playlist
				try {
					repaintListArea(3);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mItems[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Exit
				mManager.closeAll();
				System.exit(0);
			}
		});
		mItems[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Currently playing view
				try {
					repaintListArea(0);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}); 
		mItems[5].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// View playlist
				try {
					repaintListArea(1);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		mItems[6].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Stats
				try {
					repaintListArea(5);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		mItems[7].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Settings
				new JSettings(mManager);
			}

		});
		mItems[8].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Play
				if(!mManager.isLive() || (mManager.isLive() && mManager.ismQueueIsPaused())) {
					try {
						mManager.goLive();
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		mItems[9].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Pause
				mManager.pause();
			}

		});
		mItems[10].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Stop
				if(mManager.isLive()) {
					try {
						mManager.stopNow();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		mItems[11].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// About
				JOptionPane.showMessageDialog(getContentPane(), 
						Global.getLOCInstance().getLocalizedString("About"));
			}

		});
	}

	/**
	 * Repaints the main list area.
	 * @throws Exception 
	 */
	public void repaintListArea(int listType) throws Exception {
		
		contentPane.remove(mListArea);
		GridBagConstraints c = new GridBagConstraints();
		if(listType == 0 || listType == 1) {
			mListArea = new JMusicList(listType, mManager, null);
		} else if(listType == 2) {
			mListArea = new UIStreaming(mManager);
		} else if(listType == 3) {
			SongGeneratorUI ui = new SongGeneratorUI(new SimpleGenerator(mManager));
			//Alicia changed null to mManager April 10
	        //Create and set up the window.
			mListArea = new JPanel();
	        mListArea.add(ui, BorderLayout.CENTER);
		} else if(listType == 4){
			mListArea = new LocateSongFrame(mManager);
		} else if(listType == 5) {
			mListArea = new StatisticsUI();
	 	} else {
			mListArea = new JPanel();
		}
		currListView = listType;
		c.gridx = 1;
		c.gridy = 3;
		c.gridheight = 2;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTH;
		gridbag.setConstraints(mListArea, c);
		contentPane.add(mListArea);	
		
		contentPane.repaint();
		refreshAll();
		currListView = listType;
	}
	
	/**
	 * Repaints the main list area.
	 * @throws Exception 
	 */
	public void paintHeaderFiller() throws Exception {
		String openTag = "<html><b>";
		String closeTag = "</b></html>";
		GridBagConstraints c = new GridBagConstraints();
		// Header filler
		JPanel cont = new JPanel();
		JLabel filler = new JLabel("");
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 2;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTH;
		cont.add(filler);
		cont.setBackground(Color.LIGHT_GRAY);
		gridbag.setConstraints(cont, c);
		contentPane.add(cont);
		
		// Song label
		JPanel cont2 = new JPanel();
		filler = new JLabel(openTag + Global.getLOCInstance().getLocalizedString("MainFrame.currPlay") + closeTag);
		c.gridx = 2;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.EAST;
		cont2.add(filler);
		cont2.setBackground(Color.LIGHT_GRAY);
		gridbag.setConstraints(cont2, c);
		contentPane.add(cont2);
		
		// Playlist label
		JPanel cont3 = new JPanel();
		filler = new JLabel(openTag + Global.getLOCInstance().getLocalizedString("MainFrame.selPlaylist") + closeTag);
		filler.setHorizontalTextPosition(JLabel.RIGHT);
		c.gridx = 2;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.EAST;
		cont3.add(filler);
		cont3.setBackground(Color.LIGHT_GRAY);
		gridbag.setConstraints(cont3, c);
		contentPane.add(cont3);
		
		JPanel cont4 = new JPanel();
		filler = new JLabel("");
		c.gridx = 4;
		c.gridy = 1;
		c.gridheight = 2;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTH;
		cont4.add(filler);
		cont4.setBackground(Color.LIGHT_GRAY);
		gridbag.setConstraints(cont4, c);
		contentPane.add(cont4);
	}
	
	/*
	 * JPlayer painter.
	 */
	public void repaintSongInfo(String data) throws Exception {
		contentPane.remove(mInfo);
		mInfo.removeAll();
		String msg = "";
		
		if(data != null) {
			msg = data; 
		} else {
			msg = Global.getLOCInstance().getLocalizedString("MainFrame.nothing");
		}
		
		JLabel sInfo = new JLabel(msg);
		
		mInfo.add(sInfo);
		mInfo.revalidate();
		
		mInfo.setBackground(Color.lightGray);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.SOUTH;
		gridbag.setConstraints(mInfo, c);
		contentPane.add(mInfo);	
		contentPane.repaint();
		refreshAll();
	}
	
	/*
	 * Playlist painter.
	 */
	public void repaintPlaylistInfo(String data) throws Exception {
		contentPane.remove(pInfo);
		pInfo.removeAll();
		String msg = "";
		
		if(data != null) {
			msg = data; 
		} else {
			msg = Global.getLOCInstance().getLocalizedString("MainFrame.noPlaylist");
		}
		
		JLabel sInfo = new JLabel(msg);
		
		pInfo.add(sInfo);
		pInfo.revalidate();
		
		pInfo.setBackground(Color.lightGray);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTH;
		gridbag.setConstraints(pInfo, c);
		contentPane.add(pInfo);	
		contentPane.repaint();
		refreshAll();
	}
	
	public void refreshAll() {
		pack();
		setVisible(true);
		
		//Alicia added this March 23
		((JPanel) contentPane).updateUI();
	}
	
	/** 
	 * Returns the current list view
	 * @return currListView int
	 */
	public int getListViewStatus() {
		return currListView;
	}
	
	/** 
	 * Sets the current list view
	 */
	public void setListViewStatus(int i) {
		if(i==0 || i==1|| i==2 || i==3) {
			currListView = i;
		}
	}
	
	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}

}
