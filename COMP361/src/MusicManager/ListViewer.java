package MusicManager;

import javax.swing.JPanel;

/**
 * @author Rebecca
 * The JPanel that displays a MusicList.
 */
@SuppressWarnings("serial")
public class ListViewer extends JPanel {

	/** The MusicList displayed */
	MusicList mList;
	
	/**
	 * Constructor, creates a viewer for the given MusicList.
	 * @param list The MusicList to display.
	 */
	public ListViewer(MusicList list) {
		super(true);
		mList = list;
	}
	
	/**
	 * Called whenever the list needs to be repainted.
	 * (Maybe this doesn't need to be overridden...)
	 */
	public void repaint() {
		
	}
}
