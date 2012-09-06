package ManagerUI;

import java.util.NoSuchElementException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import MusicManager.ActiveQueue;
import MusicManager.MusicManager;

/**
 * @author Rebecca
 * The Music Player UI... separate for easy repainting.
 */
@SuppressWarnings("serial")
public class JPlayer extends JPanel {
    
	/* The ActiveQueue */
	private ActiveQueue aQueue;
    
    
	public JPlayer(MusicManager m) throws Exception {
		aQueue = m.getActiveQueue();
	
		/*
		Localization l = new Localization("EN"); // default english
		String test = l.getLocalizedString("Boo");
		System.out.println(test);
		*/
		
		System.out.println("Inside JPlayer constructor.");
		String data = "No song currently playing.";

		if(aQueue != null) {
			
			if(aQueue.getPlayer().getIsPlaying()) {
				try {
					data = "<b>Now playing:</b> " + 
							aQueue.getSongs().getFirst().printSong();
				} catch(NoSuchElementException e) {
					e.printStackTrace();
				}
			} 
		} else {
			data = "<b> Currently playing: </b> Nothing.";
		}
		
		String pList = "<htmL><b>Your playlist:</b> " + m.getPlaylist().toString() + 
				"<p><p>" + data + "</p></html>";
		
		JLabel info = new JLabel(pList);
		this.add(info);
		setVisible(true);
	}
	
}
