package ManagerUI;

import globalAccess.Global;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import MusicManager.MusicManager;

/**
 * Frame for main settings.
 * @author Rebecca
 *
 */
@SuppressWarnings("serial")
public class JSettings extends JFrame implements ActionListener {

	/* The MusicManager */
	private MusicManager mManager;
	
	/* Buttons */
	private JButton mSaveChanges;
	private JButton mCancel;
	
	/* The fields */
	private JTextField mSongPlay;
	private JTextField mPlaylistLength;
	private JCheckBox mStreaming;
	//private JCheckBox mClientSelect;
	
	public JSettings(MusicManager m) {
		mManager = m;
		setTitle(Global.getLOCInstance().getLocalizedString("Settings"));
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
        setPreferredSize(new Dimension(500, 300));
        
		JPanel buttons = new JPanel();
		mSaveChanges = new JButton(Global.getLOCInstance().getLocalizedString("Button.SaveChanges"));
		mCancel = new JButton(Global.getLOCInstance().getLocalizedString("Button.Cancel"));
		mSaveChanges.addActionListener(this);
		mCancel.addActionListener(this);
		
		buttons.add(mSaveChanges);
		buttons.add(mCancel);
		
		JPanel main1 = new JPanel();
		BoxLayout mainBox = new BoxLayout(main1, BoxLayout.Y_AXIS);
		main1.setLayout(mainBox);
		JPanel main2 = new JPanel();
		BoxLayout mainBox2 = new BoxLayout(main2, BoxLayout.Y_AXIS);
		main2.setLayout(mainBox2);
		
		// Panel for max song play
		JPanel songContent = new JPanel();
		BorderLayout box2 = new BorderLayout();
		songContent.setLayout(box2);
		mSongPlay = new JTextField(15);
		mSongPlay.setText(Integer.toString(mManager.getSongReplayGap()));
		JLabel maxSongLabel = new JLabel(Global.getLOCInstance().getLocalizedString("MaxSongLabel") + "      ");
		songContent.add(maxSongLabel, BorderLayout.WEST);
		songContent.add(mSongPlay, BorderLayout.CENTER);
		
		// Panel for min playlist length
		JPanel playContent = new JPanel();
		BorderLayout box0 = new BorderLayout();
        playContent.setLayout(box0);
		mPlaylistLength = new JTextField(15);
		mPlaylistLength.setText(Integer.toString(mManager.getMinListLength()));
		JLabel minPlayLabel = new JLabel(Global.getLOCInstance().getLocalizedString("MinPlayLabel"));
		playContent.add(minPlayLabel, BorderLayout.WEST);
		playContent.add(mPlaylistLength, BorderLayout.CENTER);
		
		// Panel for streaming
		JPanel streamContent = new JPanel();
		BorderLayout box1 = new BorderLayout();
        streamContent.setLayout(box1);
		mStreaming = new JCheckBox();
		mStreaming.setSelected(mManager.ismStreamEnabled());
		JLabel streamLabel = new JLabel(Global.getLOCInstance().getLocalizedString("StreamingLabel"));
		streamContent.add(streamLabel, BorderLayout.CENTER);
		streamContent.add(mStreaming, BorderLayout.WEST);
		
		// Panel for allowing clients to choose from streaming
		/*
		JPanel clientStreamContent = new JPanel(box1);
		BorderLayout box11 = new BorderLayout();
        clientStreamContent.setLayout(box11);
		JLabel clientLabel = new JLabel(Global.getLOCInstance().getLocalizedString("StreamingLabel"));
		mClientSelect = new JCheckBox();
		mClientSelect.setSelected(b)
		clientStreamContent.add(clientLabel, BorderLayout.CENTER);
		clientStreamContent.add(mClientSelect, BorderLayout.WEST);
		*/
		main1.add(songContent);
		main1.add(playContent);
		main2.add(streamContent);
		//main2.add(clientStreamContent);
		
		TitledBorder title1 = BorderFactory.createTitledBorder(Global.getLOCInstance().getLocalizedString("PlayingOptions"));
		main1.setBorder(title1);
		TitledBorder title2 = BorderFactory.createTitledBorder(Global.getLOCInstance().getLocalizedString("StreamingOptions"));
		main2.setBorder(title2);
		
		add(main1);
		add(main2);
		add(buttons);
		
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		boolean retry = false;
		boolean valid = true;
		  if(mSaveChanges == event.getSource()) { 
			  // Check that format is valid.
			  String plays = mSongPlay.getText();
			  String length = mPlaylistLength.getText();

			  boolean isSel = mStreaming.isSelected();
			  try {
				  int minPlayGap = Integer.parseInt(plays);
				  int minPlaylistLen = Integer.parseInt(length);
				  
				  if(minPlayGap <= 0) {
					  valid = false;
					  JOptionPane.showMessageDialog(getContentPane(), 
							  Global.getLOCInstance().getLocalizedString("RepeatError"));
					  retry = true;
				  }
				  if(minPlaylistLen <= 0) {
					  valid = false;
					  JOptionPane.showMessageDialog(getContentPane(), 
							  Global.getLOCInstance().getLocalizedString("PlaylistLengthError"));
					  retry = true;
				  }
				  
				  if(valid) {
					  // Now save it
					  mManager.setSongReplayGap(minPlayGap);
					  mManager.setMinListLength(minPlaylistLen);
				  }
  
			  } catch(NumberFormatException e) {
				  retry = true;
				  JOptionPane.showMessageDialog(getContentPane(), 
						  Global.getLOCInstance().getLocalizedString("ParseError"));
			  }
			  
			  mManager.setmStreamEnabled(isSel);
			  if(!retry) {
				  setVisible(false);
			  }
			  
		  } else if(mCancel == event.getSource()) {
			  // Return
			  setVisible(false);
		  }
	}
	
	
}
