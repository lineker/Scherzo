package SongGenerator;

import globalAccess.Global;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import MusicManager.Song;

/**
 * A dialog to display the resulting playist created by a song generator.
 * @author Alicia Bendz
 *
 */
@SuppressWarnings("serial")
public class PlaylistDialog extends JDialog {

	/**
	 * Constructor with a reference to the parent frame, the collection of songs to display,
	 * and the name of the new playlist.
	 * @param f The parent frame.
	 * @param songs The songs to display.
	 * @param name The name of the playlist.
	 */
	public PlaylistDialog(Frame f, Collection<Song> songs, String name){
		super(f);
		
		//formatting
		setBackground(new Color(240, 240, 240));
		
		//main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(240, 240, 240));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		//display the list of songs
		JList songList = new JList(songs.toArray());
		JScrollPane scroll = new JScrollPane(songList);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		//add a button to close
		final JButton closeButton 
			= new JButton(Global.getLOCInstance().getLocalizedString("SongGen.close"));
		closeButton.setBackground(Color.WHITE);
		
		mainPanel.add( new JLabel(name));
		mainPanel.add(scroll);
		mainPanel.add(closeButton);
		add(mainPanel);
		
		//remove the frame when closed
		closeButton.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getSource().equals(closeButton))
					dispose();
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
			}});
		
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
