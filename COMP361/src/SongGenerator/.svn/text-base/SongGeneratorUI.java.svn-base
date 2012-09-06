package SongGenerator;

import globalAccess.Global;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import DataAccess.ServicePool;
import MusicManager.Song;

/**
 * Playlist/Song generator UI class. Contains all the buttons 
 * and possible actions for playlist generation.
 * This will request generation and store generated playlists.
 * @author Alicia Bendz
 *
 */
public class SongGeneratorUI extends JPanel {
	/**
	 * Serialization ID - Default
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Testing frame. Comment out if not necessary.
	 */
	private static JFrame mMainFrame;
	
	/**
	 * Song generator to use.
	 */
	private final SongGenerator mSongGenerator;
	
	/**
	 * Text fields for name, values, and songs. The tolerance value is only used for 
	 * the tolerance generator.
	 */
	private JTextField mNameField, mValueField, mSongs, mToleranceValue;
	
	/**
	 * Drop down lists for properties, operators, and generator.
	 */
	private JComboBox mPropertyList;
	private JComboBox mProperties;
	private JComboBox mOperators;
	private JComboBox mGeneratorList;
	
	/**
	 * A no limit check box for number of songs.
	 */
	private JCheckBox mNoLimit;
	
	/**
	 * A Default List Model for the rules list.
	 */
	private DefaultListModel mDlm;
	
	/**
	 * A rule list to hold all created constraints.
	 */
	private final JList mRules;
	
	/**
	 * Buttons to clear form or create playlist
	 */
	private final JButton mClear, mGo;
	
	/**
	 * A map from Property to operator to indicate which operators to apply to which
	 * properties. These are listed in the Operator enum.
	 * @see Operator
	 */
	private static final Map<Property, LinkedList<Operator>> PROPERTY_OPS 
		= new HashMap<Property, LinkedList<Operator>>(){
			private static final long serialVersionUID = 1L;
			{
				put(Property.ALBUM, 
						new LinkedList<Operator>(){
					private static final long serialVersionUID = 1L;
					{
						add(Operator.CONTAINS);
						add(Operator.EQUALS);
						add(Operator.GREATER);
						add(Operator.GREATER_EQUAL);
						add(Operator.LESS);
						add(Operator.LESS_EQUAL);
						add(Operator.NOT_CONTAINS);
						add(Operator.NOT_EQUAL);
					}
				} );
				put(Property.ARTIST, 
						new LinkedList<Operator>(){
					private static final long serialVersionUID = 1L;
					{
						add(Operator.CONTAINS);
						add(Operator.EQUALS);
						add(Operator.GREATER);
						add(Operator.GREATER_EQUAL);
						add(Operator.LESS);
						add(Operator.LESS_EQUAL);
						add(Operator.NOT_CONTAINS);
						add(Operator.NOT_EQUAL);
					}
				} );
				put(Property.LAST_PLAYED, 
						new LinkedList<Operator>(){
					private static final long serialVersionUID = 1L;
					{
						add(Operator.EQUALS);
						add(Operator.GREATER);
						add(Operator.GREATER_EQUAL);
						add(Operator.LESS);
						add(Operator.LESS_EQUAL);
						add(Operator.NOT_EQUAL);
					}
				} );
				put(Property.LENGTH, 
						new LinkedList<Operator>(){
					private static final long serialVersionUID = 1L;
					{
						add(Operator.EQUALS);
						add(Operator.GREATER);
						add(Operator.GREATER_EQUAL);
						add(Operator.LESS);
						add(Operator.LESS_EQUAL);
						add(Operator.NOT_EQUAL);
					}
				} );
				put(Property.PLAY_COUNT_TOTAL, 
						new LinkedList<Operator>(){
					private static final long serialVersionUID = 1L;
					{
						add(Operator.EQUALS);
						add(Operator.GREATER);
						add(Operator.GREATER_EQUAL);
						add(Operator.LESS);
						add(Operator.LESS_EQUAL);
						add(Operator.NOT_EQUAL);
					}
				} );
				put(Property.PLAY_COUNT, 
						new LinkedList<Operator>(){
					private static final long serialVersionUID = 1L;
					{
						add(Operator.EQUALS);
						add(Operator.GREATER);
						add(Operator.GREATER_EQUAL);
						add(Operator.LESS);
						add(Operator.LESS_EQUAL);
						add(Operator.NOT_EQUAL);
					}
				} );
				put(Property.REQUEST_COUNT_DAY, 
						new LinkedList<Operator>(){
					private static final long serialVersionUID = 1L;
					{
						add(Operator.EQUALS);
						add(Operator.GREATER);
						add(Operator.GREATER_EQUAL);
						add(Operator.LESS);
						add(Operator.LESS_EQUAL);
						add(Operator.NOT_EQUAL);
					}
				} );
				put(Property.REQUEST_COUNT_TOTAL, 
						new LinkedList<Operator>(){
					private static final long serialVersionUID = 1L;
					{
						add(Operator.EQUALS);
						add(Operator.GREATER);
						add(Operator.GREATER_EQUAL);
						add(Operator.LESS);
						add(Operator.LESS_EQUAL);
						add(Operator.NOT_EQUAL);
					}
				} );
				put(Property.STREAMING, 
						new LinkedList<Operator>(){
						private static final long serialVersionUID = 1L;
					{
						add(Operator.EQUALS);
						add(Operator.NOT_EQUAL);
					}
				} );
				put(Property.TITLE, 
						new LinkedList<Operator>(){
							private static final long serialVersionUID = 1L;

					{
						add(Operator.CONTAINS);
						add(Operator.EQUALS);
						add(Operator.GREATER);
						add(Operator.GREATER_EQUAL);
						add(Operator.LESS);
						add(Operator.LESS_EQUAL);
						add(Operator.NOT_CONTAINS);
						add(Operator.NOT_EQUAL);
					}
				} );
			}
		};
	
		/**
		 * Constructor that generators UI and initiates necessary fields,
		 * @param generator The generator to use for getting songs.
		 */
	public SongGeneratorUI(SongGenerator generator){
		super();
		mSongGenerator = generator;
		
		//set background colour and layout
		this.setBackground(new Color(214, 217, 223));
		GridBagLayout mainLayout = new GridBagLayout();
		this.setLayout(mainLayout);
		
		//title
		JLabel label = 
				new JLabel(Global.getLOCInstance()
						.getLocalizedString("SongGen.generatePlaylist"));
		JPanel panel = new JPanel();
		panel.add(label);
		panel.setBackground(new Color(214, 217, 223));
		Border border = BorderFactory.createEmptyBorder(2,20,2,20);
		panel.setBorder(border);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//name
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		label = new JLabel(Global.getLOCInstance()
				.getLocalizedString("SongGen.name"));
		label.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 3));
		panel.add(label);
		mNameField = new JTextField();
		panel.add(mNameField);
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,300));
		panel.setBackground(new Color(214, 217, 223));
		
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//songs
		mSongs = new JTextField();
		mNoLimit = new JCheckBox(Global.getLOCInstance()
				.getLocalizedString("SongGen.noLimit"));
		mNoLimit.setBackground(Color.WHITE);
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		label = new JLabel(Global.getLOCInstance()
				.getLocalizedString("SongGen.songs"));
		label.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 3));
		panel.add(label);
		panel.add(mSongs);
		panel.add(mNoLimit);
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,350));
		panel.setBackground(new Color(214, 217, 223));
		
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//generator
		mGeneratorList = new JComboBox();
		mToleranceValue = new JTextField();
		mToleranceValue.setBackground(Color.WHITE);
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		label = new JLabel(Global.getLOCInstance()
				.getLocalizedString("SongGen.generator"));
		label.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 3));
		panel.add(label);
		panel.add(mGeneratorList);
		panel.add(mToleranceValue);
		panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,225));
		panel.setBackground(new Color(214, 217, 223));
		mToleranceValue.setEnabled(false);
		
		mGeneratorList.addItem(Global.getLOCInstance()
				.getLocalizedString("SongGen.simple"));
		mGeneratorList.addItem(Global.getLOCInstance()
				.getLocalizedString("SongGen.tolerance"));
		mGeneratorList.addItem(Global.getLOCInstance()
				.getLocalizedString("SongGen.majority"));
		
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//rules
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		label = new JLabel(Global.getLOCInstance()
				.getLocalizedString("SongGen.rules"));
		label.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 3));
		panel.add(label);
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,2,4));
		panel.setBackground(new Color(214, 217, 223));
		
		mainLayout.setConstraints(panel, constraints);
		add(panel);

		
		//radio buttons and selector
		final JRadioButton least = new JRadioButton(Global.getLOCInstance()
				.getLocalizedString("SongGen.least"));
		JRadioButton most = new JRadioButton(Global.getLOCInstance()
				.getLocalizedString("SongGen.most"));
		mPropertyList = new JComboBox();
		least.setBackground(Color.WHITE);
		most.setBackground(Color.WHITE);
		least.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		most.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		ButtonGroup group = new ButtonGroup();
		group.add(least);
		group.add(most);
		least.setSelected(true);
		
		mPropertyList.addItem(Global.getLOCInstance()
				.getLocalizedString("SongGen.null"));
		mPropertyList.addItem(Global.getLOCInstance()
				.getLocalizedString("SongGen.plays"));
		mPropertyList.addItem(Global.getLOCInstance()
				.getLocalizedString("SongGen.requests"));
		mPropertyList.addItem(Global.getLOCInstance()
				.getLocalizedString("SongGen.time"));
		mPropertyList.addItem(Global.getLOCInstance()
				.getLocalizedString("SongGen.recentPlay"));
		mPropertyList.addItem(Global.getLOCInstance()
				.getLocalizedString("SongGen.streamed"));
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		panel.setBackground(new Color(214, 217, 223));
		panel.add(least);
		panel.add(most);
		panel.add(mPropertyList);
		
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//advanced
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		label = new JLabel(Global.getLOCInstance()
				.getLocalizedString("SongGen.advanced"));
		label.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 3));
		panel.add(label);
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		panel.setBackground(new Color(214, 217, 223));
		
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//constraints
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		mProperties = new JComboBox();
		mOperators = new JComboBox();
		mValueField = new JTextField(Global.getLOCInstance()
				.getLocalizedString("SongGen.value"));
		final JButton add = new JButton(Global.getLOCInstance()
				.getLocalizedString("SongGen.add"));
		add.setBackground(Color.WHITE);
		mValueField.setPreferredSize(new Dimension(150, 15));
		panel.add(mProperties);
		panel.add(mOperators);
		panel.add(mValueField);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(add);
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		panel.setBackground(new Color(214, 217, 223));
		
		mProperties.addItem(Global.getLOCInstance()
				.getLocalizedString("SongGen.property"));
		for(Property p: Property.values()){
			mProperties.addItem(p);
		}
		
		mOperators.addItem(Global.getLOCInstance()
				.getLocalizedString("SongGen.operator"));
		for(Operator o : Operator.values()){
			mOperators.addItem(o);
		}
		
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//constraints list
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		mDlm = new DefaultListModel();
		mRules = new JList(mDlm);
		JScrollPane scroll = new JScrollPane(mRules);
		mRules.setPreferredSize(new Dimension(300, 90));
		label.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 3));
		panel.add(scroll);
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		panel.setBackground(new Color(214, 217, 223));
		
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//buttons
		panel = new JPanel();
		mClear = new JButton(Global.getLOCInstance()
				.getLocalizedString("SongGen.clear"));
		mGo = new JButton(Global.getLOCInstance()
				.getLocalizedString("SongGen.go"));
		mClear.setBackground(Color.WHITE);
		mGo.setBackground(Color.WHITE);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		label.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 3));
		panel.add(Box.createHorizontalGlue());
		panel.add(mClear);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(mGo);
		panel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		panel.setBackground(new Color(214, 217, 223));
		
		mainLayout.setConstraints(panel, constraints);
		add(panel);
		
		//clicks
		MouseListener mouseListener = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getSource().equals(mClear)){
					//reset upon clear
					reset();
				}
				else if(arg0.getSource().equals(mGo)){
					//get the required data from form and create playlist
					List<Constraint> cList = new LinkedList<Constraint>();
					int songNum = -1;
					
					try {
						if(ServicePool.PlaylistService().doesPlaylistExist(mNameField.getText())){
							JOptionPane.showMessageDialog(mMainFrame, 
									Global.getLOCInstance()
									.getLocalizedString("SongGen.invalidName"),
									Global.getLOCInstance()
									.getLocalizedString("SongGen.error"), 
									JOptionPane.ERROR_MESSAGE);
							setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							return;
						}
					} catch (Exception e){
						JOptionPane.showMessageDialog(mMainFrame, 
								Global.getLOCInstance()
								.getLocalizedString("SongGen.errorTitle") 
								+ e.getMessage(),
								Global.getLOCInstance()
								.getLocalizedString("SongGen.error"), 
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					//set wait cursor
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					enableForm(false);
					
					//get the maximization or minimization property
					if(mPropertyList.getSelectedIndex() != 0)
						cList.add(
								new Constraint(stringToProperty(
										(String) mPropertyList.getSelectedItem()),
										least.isSelected() ? Operator.MINIMIZE
												:Operator.MAXIMIZE, null));
					
					//add other constraints
					for(int i = 0;i < mDlm.getSize();i++){
						cList.add((Constraint) mDlm.get(i));
					}
					
					//get the limit of songs
					if(!mNoLimit.isSelected()){
						try{
							songNum = Integer.parseInt(mSongs.getText());
							if(songNum < 0)
								throw new NumberFormatException();
						}
						catch(NumberFormatException nfe){
							//if a non-number is added, show error
							JOptionPane.showMessageDialog(mMainFrame, 
									Global.getLOCInstance()
									.getLocalizedString("SongGen.invalidNum"),
									Global.getLOCInstance()
									.getLocalizedString("SongGen.error"), 
									JOptionPane.ERROR_MESSAGE);
							setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							enableForm(true);
							return;
						}
					}
					
					//try to get songs
					Collection<Song> results;
					
					//select and run generator
					try {
						if(mGeneratorList.getSelectedItem().equals(Global.getLOCInstance()
								.getLocalizedString("SongGen.simple"))){
							results = (new SimpleGenerator(null)).getSongs(songNum, cList);
						} else if(mGeneratorList.getSelectedItem().equals(Global.getLOCInstance()
								.getLocalizedString("SongGen.tolerance"))){
							
							int tol = -1;
							try{
								tol = Integer.parseInt(mToleranceValue.getText());
								if(tol < 0)
									throw new NumberFormatException();
							}catch (NumberFormatException nfe){
								JOptionPane.showMessageDialog(mMainFrame, 
										Global.getLOCInstance()
										.getLocalizedString("SongGen.invalidNum"),
										Global.getLOCInstance()
										.getLocalizedString("SongGen.error"), 
										JOptionPane.ERROR_MESSAGE);
								setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								enableForm(true);
								return;
							}
							
							results = (new ToleranceGenerator(null, tol)).getSongs(songNum, cList);
						} else if(mGeneratorList.getSelectedItem().equals(Global.getLOCInstance()
								.getLocalizedString("SongGen.majority"))){
							results = (new MajorityGenerator(null)).getSongs(songNum, cList);
						} else {
							results = mSongGenerator.getSongs(songNum, cList);
						}
						
						//restore cursor
						setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						enableForm(true);
						
						//show an error if there was a problem
						if(results == null){
							JOptionPane.showMessageDialog(mMainFrame, 
									Global.getLOCInstance()
									.getLocalizedString("SongGen.errorMessage"),
									Global.getLOCInstance()
									.getLocalizedString("SongGen.error"),
									JOptionPane.ERROR_MESSAGE);
						} else if(results.size() == 0){
							JOptionPane.showMessageDialog(mMainFrame, 
									Global.getLOCInstance()
									.getLocalizedString("SongGen.noSongs"),
									Global.getLOCInstance()
									.getLocalizedString("SongGen.noPlaylist"),
									JOptionPane.INFORMATION_MESSAGE);
							reset();
							return;
						} else {
							//show resulting playlist otherwise
							new PlaylistDialog(mMainFrame, results, mNameField.getText());
						}
						
						//store playlist in database
						int[] songIds = new int[results.size()];
						Song[] tempSongs = new Song[results.size()];
						
						int p = 0;
						for(Song s : results){
							tempSongs[p] = s;
							p++;
						}
						
						for(int i = 0; i < songIds.length ; i++){
							songIds[i] = tempSongs[i].getId();
						}
						
						int pid = ServicePool.PlaylistService()
								.insertPlaylist(mNameField.getText());
						
						ServicePool.PlaylistService().addSongsToPlaylist(pid, songIds);
						
						//clear
						reset();
					} catch (GenerationException e) {
						//generator failure
						setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						enableForm(true);
						
						JOptionPane.showMessageDialog(mMainFrame, 
								Global.getLOCInstance()
								.getLocalizedString("SongGen.errorTitle") 
								+ e.getMessage(),
								Global.getLOCInstance()
								.getLocalizedString("SongGen.error"), 
								JOptionPane.ERROR_MESSAGE);
						
						e.printStackTrace();
					} catch (Exception e) {
						//other failures
						setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						enableForm(true);
						
						JOptionPane.showMessageDialog(mMainFrame, 
								Global.getLOCInstance()
								.getLocalizedString("SongGen.errorTitle") 
								+ e.getMessage(),
								Global.getLOCInstance()
								.getLocalizedString("SongGen.error"), 
								JOptionPane.ERROR_MESSAGE);
						
						e.printStackTrace();
					}
				}
				else if(arg0.getSource().equals(add)){
					//add constraint to constraints list
					//check if properties and operator selected
					if(mProperties.getSelectedIndex() > 0 
							&& mOperators.getSelectedIndex() > 0){
						String value;
						
						//check if value is needed
						if((((Operator) mOperators.getSelectedItem())
										.equals(Operator.MAXIMIZE))
								|| (((Operator) mOperators.getSelectedItem())
										.equals(Operator.MINIMIZE))){
							value = "";
						} else {
							//get value
							value = mValueField.getText();
						}
						
						//create and add new constraint
						Constraint c 
							= new Constraint((Property) mProperties.getSelectedItem(), 
								(Operator) mOperators.getSelectedItem(), 
								new Value(value));
						mDlm.addElement(c);
					}
				}
				else if(arg0.getSource().equals(mValueField)){
					//clear value field upon focus
					if(mValueField.getText().equals(Global.getLOCInstance()
							.getLocalizedString("SongGen.value")))
						mValueField.setText("");
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
			}
		};
		
		//add mouse listeners
		mClear.addMouseListener(mouseListener);
		mGo.addMouseListener(mouseListener);
		add.addMouseListener(mouseListener);
		mValueField.addMouseListener(mouseListener);
		
		//changes to limit checkbox
		mNoLimit.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(arg0.getSource().equals(mNoLimit)){
					if(mNoLimit.isSelected()){
						mSongs.setEnabled(false);
						mSongs.setText("0");
					}
					else
						mSongs.setEnabled(true);
				}
			}
		});
		
		//add listener to change operators based on property
		ItemListener itemListener = new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getSource().equals(mProperties)){
					
					//if nothing selected, set nothing selected
					if(mProperties.getSelectedIndex() == 0)
						mOperators.setSelectedIndex(0);
					else {
						//change operators when property is selected
						mOperators.removeAllItems();
						mOperators.addItem(Global.getLOCInstance()
								.getLocalizedString("SongGen.operator"));
						for(Operator o 
								: PROPERTY_OPS
									.get((Property) mProperties.getSelectedItem())){
							mOperators.addItem(o);
						}
					}
					
					//reset value field
					mValueField.setText(Global.getLOCInstance()
							.getLocalizedString("SongGen.value"));
					
				} else if(arg0.getSource().equals(mGeneratorList)){
					//allow user to input a tolerance value
					if(mGeneratorList.getSelectedItem().equals(
							Global.getLOCInstance()
							.getLocalizedString("SongGen.tolerance")))
						mToleranceValue.setEnabled(true);
					else
						mToleranceValue.setEnabled(false);
				}
			}};
		
		mProperties.addItemListener(itemListener);
		mGeneratorList.addItemListener(itemListener);
		
		//key presses
		mRules.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				//delete constraints when delete is clicked
				if(arg0.getKeyCode() == KeyEvent.VK_DELETE){
					for(Object o : mRules.getSelectedValues()){
						mDlm.removeElement(o);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				//unused
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				//unused
			}});
	}
  
	
	/**
	 * Change a particular string to a property value.
	 * @param display The String selected.
	 * @return The corresponding property.
	 */
	private Property stringToProperty(String display){
		if(display.equals(Global.getLOCInstance()
				.getLocalizedString("SongGen.plays"))){
			return Property.PLAY_COUNT_TOTAL;
		} else if(display.equals(Global.getLOCInstance()
				.getLocalizedString("SongGen.requests"))){
			return Property.REQUEST_COUNT_TOTAL;
		} else if(display.equals(Global.getLOCInstance()
				.getLocalizedString("SongGen.time"))){
			return Property.LENGTH;
		} else if(display.equals(Global.getLOCInstance()
				.getLocalizedString("SongGen.streamed"))){
			return Property.STREAMING;
		} else{
			return Property.LAST_PLAYED;
		}
	}
    
    /**
     * Clear all form fields. Restore to defaults.
     */
    private void reset(){
    	enableForm(true);
    	mNameField.setText("");
		mSongs.setText("");
		mNoLimit.setSelected(false);
		mPropertyList.setSelectedIndex(0);
		mProperties.setSelectedIndex(0);
		mOperators.setSelectedIndex(0);
		mDlm.removeAllElements();
		mValueField.setText(Global.getLOCInstance()
				.getLocalizedString("SongGen.value"));
		mValueField.setEnabled(true);
		mToleranceValue.setEnabled(false);
		mToleranceValue.setText("0");
		mGeneratorList.setSelectedIndex(0);
    }
    
    /**
     * Enable or disable form elements.
     * @param enable True to enable, false to disable.
     */
    private void enableForm(boolean enable){
    	mNameField.setEnabled(enable);
		mSongs.setEnabled(enable);
		mNoLimit.setEnabled(enable);
		mPropertyList.setEnabled(enable);
		mProperties.setEnabled(enable);
		mOperators.setEnabled(enable);
		mValueField.setEnabled(enable);
		mRules.setEnabled(enable);
		mClear.setEnabled(enable);
		mGo.setEnabled(enable);
		mGeneratorList.setEnabled(enable);
    }
}
