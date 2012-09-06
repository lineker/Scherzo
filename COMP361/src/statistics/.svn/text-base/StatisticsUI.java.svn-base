package statistics;

import globalAccess.Global;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import MusicManager.Song;

/**
 * The UI for the Statistics Generation Module. Allows the user to select the data for
 * which they wish statistics to be generated. The user can also select the format of 
 * display.
 * @author Alicia Bendz
 *
 */
@SuppressWarnings("serial")
public class StatisticsUI extends JPanel {
	
	/**
	 * Testing Frame.
	 */
	private static JFrame frame = null;
	
	/**
	 * ComboBoxes for traits and chart types.
	 */
	private final JComboBox mTraits;
	private final JComboBox mChartType;
	
	/**
	 * CheckBoxes for date ranges.
	 */
	private final JCheckBox mStartCheck;
	private final JCheckBox mEndCheck;
	
	/**
	 * Text Fields for start and end dates.
	 */
	private final JTextField mStart;
	private final JTextField mEnd;
	
	/**
	 * Start and end dates.
	 */
	private Date mStartDate;
	private Date mEndDate;
	
	/**
	 * List of selected songs.
	 */
	private final List<Song> mSongList;
	
	/**
	 * Edit date buttons.
	 */
	private final JButton mEditDate;
	private final JButton mEditEndDate;
	
	/**
	 * Map from trait to chart type to indicate which chart types can be generated for
	 * each trait.
	 */
	private static final Map<Trait, LinkedList<ChartType>> TRAIT_CHARTS 
	= new HashMap<Trait, LinkedList<ChartType>>(){
		private static final long serialVersionUID = 1L;
		{
			put(Trait.CLIENT_TYPE, 
					new LinkedList<ChartType>(){
				private static final long serialVersionUID = 1L;
				{
					add(ChartType.BAR);
					add(ChartType.PIE);
					add(ChartType.TEXT);
				}
			} );
			
			put(Trait.FEEDBACK, 
					new LinkedList<ChartType>(){
				private static final long serialVersionUID = 1L;
				{
					add(ChartType.LINE);
					add(ChartType.TEXT);
				}
			} );

			put(Trait.SONG_PLAYS, 
					new LinkedList<ChartType>(){
				private static final long serialVersionUID = 1L;
				{
					add(ChartType.BAR);
					add(ChartType.TEXT);
				}
			} );
			
			put(Trait.SONG_REQUEST, 
					new LinkedList<ChartType>(){
				private static final long serialVersionUID = 1L;
				{
					add(ChartType.BAR);
					add(ChartType.LINE);
					add(ChartType.TEXT);
				}
			} );

			put(Trait.STREAMING, 
					new LinkedList<ChartType>(){
				private static final long serialVersionUID = 1L;
				{
					add(ChartType.BAR);
					add(ChartType.PIE);
					add(ChartType.TEXT);
				}
			} );

			put(Trait.TOTAL_REQUEST, 
					new LinkedList<ChartType>(){
				private static final long serialVersionUID = 1L;
				{
					add(ChartType.LINE);
					add(ChartType.TEXT);
				}
			} );
		}
	};
	
	/**
	 * Constructor for the Statistics UI that creates all form components.
	 */
	public StatisticsUI(){
		super();
		
		//formatting
		setBackground(new Color(214, 217, 223));
		GridBagLayout mainLayout = new GridBagLayout();
		setLayout(mainLayout);
		
		//initialization and creation of song and time dialogs
		mSongList = new LinkedList<Song>();
		final SongSelectDialog songSelectDialog = new SongSelectDialog(mSongList, this);
		mStartDate = null;
		mEndDate = null;
		final TimeSelectDialog timeSelectDialog = new TimeSelectDialog(this);
		
		//title
		JLabel label = new JLabel(Global.getLOCInstance().getLocalizedString("Stats.title"));
		
		//title panel
		JPanel panel = new JPanel();
		panel.add(label);
		panel.setBackground(new Color(214, 217, 223));
		Border border = BorderFactory.createEmptyBorder(2,20,2,20);
		panel.setBorder(border);
		
		//set up constraints for layout
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(1, 10, 1, 10);
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//traits
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		mTraits = new JComboBox();
		panel.add(mTraits);

		//song select button
		final JButton songsButton = new JButton(Global.getLOCInstance()
				.getLocalizedString("Stats.songs"));
		songsButton.setBackground(new Color(214, 217, 223));
		panel.add(Box.createHorizontalStrut(15));
		panel.add(songsButton);
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		panel.setBackground(new Color(214, 217, 223));
		
		//add traits
		mTraits.addItem(Global.getLOCInstance().getLocalizedString("Stats.null"));
		for(Trait t : Trait.values()){
			mTraits.addItem(t);
		}
		
		constraints.gridy = 1;
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//chart types
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		mChartType = new JComboBox();
		panel.add(mChartType);
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		panel.setBackground(new Color(214, 217, 223));	
		
		mChartType.addItem(Global.getLOCInstance().getLocalizedString("Stats.null"));
		
		constraints.gridy = 2;
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//add a listener that will update chart types when trait is changed
		mTraits.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getSource() == mTraits){
					//if nothing is selected, reset chart types
					mChartType.removeAllItems();
					mChartType.addItem(Global.getLOCInstance()
							.getLocalizedString("Stats.null"));
					
					if(mTraits.getSelectedIndex() == 0){
						return;
					} else {
						//add chart types for that trait
						for(ChartType ct : TRAIT_CHARTS.get(mTraits.getSelectedItem())){
							mChartType.addItem(ct);
						}
					}
				}
			}});
		
		//Dates
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		label = new JLabel(Global.getLOCInstance().getLocalizedString("Stats.date"));
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		panel.setBackground(new Color(214, 217, 223));
		panel.add(label);
		
		constraints.gridy = 3;
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//Start date
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		label = new JLabel(Global.getLOCInstance().getLocalizedString("Stats.start"));
		
		//start check box
		mStartCheck = new JCheckBox(Global.getLOCInstance()
				.getLocalizedString("Stats.beginning"));
		mStartCheck.setBackground(new Color(214, 217, 223));
		
		//start date text field
		mStart = new JTextField();
		mStart.setEditable(false);
		mStart.setPreferredSize(new Dimension(200, 25));
		
		//edit button
		mEditDate = new JButton(new ImageIcon(".\\imgs\\pencil_edit_16.png"));
		mEditDate.setBackground(new Color(214, 217, 223));
		mEditDate.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		//label and panel
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		panel.setBackground(new Color(214, 217, 223));
		panel.add(label);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(mStart);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(mEditDate);
		panel.add(Box.createHorizontalStrut(15));
		panel.add(mStartCheck);
		
		constraints.gridy = 4;
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//End date
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		label = new JLabel(Global.getLOCInstance().getLocalizedString("Stats.end"));
		
		//end date check
		mEndCheck = new JCheckBox(Global.getLOCInstance().getLocalizedString("Stats.now"));
		mEndCheck.setBackground(new Color(214, 217, 223));
		
		//end date text field
		mEnd = new JTextField();
		mEnd.setEditable(false);
		mEnd.setPreferredSize(new Dimension(150, 25));
		
		//end date edit button
		mEditEndDate = new JButton(new ImageIcon(".\\imgs\\pencil_edit_16.png"));
		mEditEndDate.setBackground(new Color(214, 217, 223));
		mEditEndDate.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		//label and panel
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		panel.setBackground(new Color(214, 217, 223));	
		panel.add(label);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(mEnd);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(mEditEndDate);
		panel.add(Box.createHorizontalStrut(15));
		panel.add(mEndCheck);
		
		constraints.gridy = 5;
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//buttons
		panel = new JPanel();
		final JButton clear = new JButton(Global.getLOCInstance()
				.getLocalizedString("Stats.clear"));
		final JButton go = new JButton(Global.getLOCInstance()
				.getLocalizedString("Stats.create"));
		clear.setBackground(new Color(214, 217, 223));
		go.setBackground(new Color(214, 217, 223));
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		label.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 3));
		panel.add(Box.createHorizontalGlue());
		panel.add(clear);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(go);
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		panel.setBackground(new Color(214, 217, 223));
		
		constraints.gridy = 6;
		mainLayout.setConstraints(panel, constraints);
		add(panel);		
		
		MouseListener mouseListener = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getSource() == clear){
					//reset
					reset();
				} else if(arg0.getSource() == go){
					//make stats request and execute
					Trait t = null;
					ChartType ct = null;
					List<Integer> songIds = null;
					
					try {
						//get trait
						if(mTraits.getSelectedIndex() != 0){
							t = (Trait) mTraits.getSelectedItem();
						} else {
							return;
						}

						//get chart
						if(mChartType.getSelectedIndex() != 0){
							ct = (ChartType) mChartType.getSelectedItem();
						} else {
							return;
						}

						//get song list
						if(mSongList.size() > 0){
							songIds = new ArrayList<Integer>();

							for(Song s : mSongList){
								songIds.add(s.getId());
							}
						}
						
						if(t.equals(Trait.SONG_PLAYS) || t.equals(Trait.SONG_REQUEST)){
							if(songIds == null){
								JOptionPane.showMessageDialog(null, 
										Global.getLOCInstance()
											.getLocalizedString("Stats.noSongs"),
										Global.getLOCInstance().getLocalizedString("Stats.error"),
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						}

						//send request to be generated
						new Thread(new StatisticsGenerator(
								new StatisticsRequest(ct, songIds, mStartDate, mEndDate, t)))
						.start();
					} catch (StatisticsException e) {
						//Show error
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, 
								Global.getLOCInstance().getLocalizedString("Stats.errorMessage"), 
								Global.getLOCInstance().getLocalizedString("Stats.error"),
								JOptionPane.ERROR_MESSAGE);
					}

					reset();

				} else if(arg0.getSource() == songsButton){
					//show song select dialog
					songSelectDialog.setVisible(true);
				} else if(arg0.getSource() == mEditDate) {
					//show dialog to edit start date and restrict date ranges
					if(mStartDate != null){
						if(mEndDate != null)
							timeSelectDialog.setTime(mStartDate, new Date(0), mEndDate, true);
						else
							timeSelectDialog.setTime(mStartDate, new Date(0), new Date(), true);
					} else {
						if(mEndDate != null)
							timeSelectDialog.setTime(new Date(), new Date(0), mEndDate, true);
						else
							timeSelectDialog.setTime(new Date(), new Date(0), new Date(), true);
					}
					
					timeSelectDialog.setVisible(true);
					
				} else if(arg0.getSource() == mEditEndDate) {
					//show dialog to edit end date and restrict date ranges
					if(mEndDate != null){
						if(mStartDate != null)
							timeSelectDialog.setTime(mEndDate, mStartDate, new Date(), false);
						else
							timeSelectDialog.setTime(mEndDate, new Date(0), new Date(), false);
					} else {
						if(mStartDate != null)
							timeSelectDialog.setTime(new Date(), mStartDate, new Date(), false);
						else
							timeSelectDialog.setTime(new Date(), new Date(0), new Date(), false);
					}
					
					timeSelectDialog.setVisible(true);
					
				} else if(arg0.getSource() == mStartCheck){
					//clear start date
					if(mStartCheck.isSelected()){
						mStartDate = null;
						mStart.setText("");
						mEditDate.setEnabled(false);
					} else {
						mEditDate.setEnabled(true);
					}
				} else if(arg0.getSource() == mEndCheck){
					//clear end date 
					if(mEndCheck.isSelected()){
						mEndDate = null;
						mEnd.setText("");
						mEditEndDate.setEnabled(false);
					} else {
						mEditEndDate.setEnabled(true);
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				//unused
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				//unused
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				//unused
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				//unused
			}};
			
		songsButton.addMouseListener(mouseListener);
		mStartCheck.addMouseListener(mouseListener);
		mEndCheck.addMouseListener(mouseListener);
		clear.addMouseListener(mouseListener);
		go.addMouseListener(mouseListener);
		mEditDate.addMouseListener(mouseListener);
		mEditEndDate.addMouseListener(mouseListener);
		
	}
	
	/**
	 * Testing method.
	 */
	private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Generate Statistics");
        frame.add(new StatisticsUI());

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
 
	/**
	 * Testing Main
	 */
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    /**
     * Reset the form values and class values.
     */
    private void reset(){
    	mTraits.setSelectedIndex(0);
    	mChartType.removeAll();
    	mChartType.addItem(Global.getLOCInstance().getLocalizedString("Stats.null"));
    	mStartCheck.setSelected(false);
    	mEndCheck.setSelected(false);
    	mStart.setText("");
    	mEnd.setText("");
    	mEditDate.setEnabled(true);
    	mEditEndDate.setEnabled(true);
    	mSongList.clear();
    	mStartDate = null;
    	mEndDate = null;
    }

    /**
     * Set the selected songs of this song UI to the input list.
     * @param songs The songs to have selected.
     */
	protected void setSelectedSong(List<Song> songs) {
		mSongList.clear();
		mSongList.addAll(songs);
	}

	/**
	 * Set the start or end date to a given value.
	 * @param start This indicates whether the start date is to be set or the end date.
	 * True for the start date and false for the end date.
	 * @param value The value to set the date to.
	 */
	protected void setDate(boolean start, Date value) {
		//update the date and the corresponding text box accordingly
		if(start){
			mStartDate = value;
			if(mStartDate != null){
				mStart.setText(mStartDate.toString());
			} else {
				mStart.setText("");
			}
		} else {
			mEndDate = value;
			
			if(mEndDate != null){
				mEnd.setText(mEndDate.toString());
			} else {
				mEnd.setText("");
			}
		}
	}

	/**
	 * Return the list of selected songs.
	 * @return The list of currently selected songs.
	 */
	protected Collection<? extends Song> getSelectedSongs() {
		return mSongList;
	}
}
