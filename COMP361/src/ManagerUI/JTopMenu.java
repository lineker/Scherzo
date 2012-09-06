package ManagerUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class JTopMenu extends JMenuBar implements ActionListener {
	
	/* menu header labels */
	private String[] hLabels = 
		{"File", "View", "Playlist", "Music Player", "Help"};
	
	/* menu item labels */
	private String[] iLabels = 
		{"Save Queue", "Save Playlist", "Open Playlist", "Generate Playlist",
			"My Queue", "My Playlist", "Statistics", "Feedback",
			"Add Song", "Remove Song",
			"Play", "Pause", "Stop",
			"About"};
	
	/* The menu headers */
	private JMenu[] headers = new JMenu[hLabels.length];
	/* The menu items */
	private JMenuItem[] items = new JMenuItem[iLabels.length];
	
	/* number of items for each header */
	private int[] elements = {4, 4, 2, 3, 1};
	
	public JTopMenu() {
		
		JMenuBar menuBar = new JMenuBar();
		
		// Add headers
		for(int i = 0; i < hLabels.length; i++) {
			headers[i] = new JMenu(hLabels[i]);
			// Add items
			for(int j = 0; j < elements[i]; j++) {
				items[j] = headers[i].add(iLabels[i]);
			}
			
			menuBar.add(headers[i]);
		}
		
	    
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
