package ManagerUI;

import globalAccess.Global;

import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


import MusicManager.MusicManager;
import MusicManager.Playlist;
import MusicManager.Song;

/**
 * The sidebar button manager.
 */

@SuppressWarnings("serial")
public class JSidebar extends JPanel {

	private Button[] vButtons;
	private Button[] cButtons;
	private LayoutManager layout;
	
	final private int vELEMENTS = 7;
	final private int cELEMENTS = 5;
	
	/* The MainFrame */
	private MainFrame mF;
	/* The MusicManager */
	private MusicManager mManager;
	
	public JSidebar(MainFrame mF, MusicManager m) throws Exception {
		// Try loading the look and feel
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) { }
	
		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		mManager = m;
		setLayout(layout);
		this.mF = mF;
		
		String openTag = "<html><b><font size = 4>";
		String closeTag = "</b></font></html>";
		JLabel view = new JLabel(openTag + "CONTROLS"+ closeTag);
		
		String[] header = {"Controls"};
		Object[][] viewLabels = {{Global.getLOCInstance().getLocalizedString("Menu.Playlist")},
				{Global.getLOCInstance().getLocalizedString("Sidebar.Queue")}, 
				{Global.getLOCInstance().getLocalizedString("Menu.Stats")},
				{Global.getLOCInstance().getLocalizedString("Sidebar.Settings")}, 
				{Global.getLOCInstance().getLocalizedString("Sidebar.AddSongs")}, 
				{Global.getLOCInstance().getLocalizedString("Sidebar.AddStreamedSongs")},
				{Global.getLOCInstance().getLocalizedString("Sidebar.ChangePlaylist")},
				{Global.getLOCInstance().getLocalizedString("Sidebar.PlayPlaylist")}, 
				{Global.getLOCInstance().getLocalizedString("Sidebar.GeneratePlaylist")},
				{Global.getLOCInstance().getLocalizedString("Sidebar.CreatePlaylist")},
				{Global.getLOCInstance().getLocalizedString("Sidebar.DeletePlaylist")},
				{Global.getLOCInstance().getLocalizedString("Sidebar.ViewAPlaylist")}};
		
		final JTable table = new JTable(viewLabels, header) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				  return false; //Disallow the editing of any cell
				  }
	    };

		table.setRowHeight(35);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setShowHorizontalLines(true);
		table.setBackground(Color.LIGHT_GRAY);
		table.addMouseListener(new JMouseListener(mF, table, mManager)); 
		add(view);
		add(table);
		
		/*
		vButtons = new Button[vELEMENTS];
		cButtons = new Button[cELEMENTS];
		
		String openTag = "<html><b><font size = 4>";
		String closeTag = "</b></font></html>";
		
		JLabel view = new JLabel(openTag + Global.getLOCInstance().getLocalizedString("Menu.View")
				+ closeTag);
		view.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(view);
		
		for(int i = 0; i < vELEMENTS; i++) {
			vButtons[i] = new Button();
			vButtons[i].setMaximumSize(new Dimension(550, 50));
			vButtons[i].setLabel(viewLabels[i]);
			vButtons[i].addActionListener(this);
			add(vButtons[i]);
		}
		
		JLabel controls = new JLabel(openTag + Global.getLOCInstance().getLocalizedString("Sidebar.Control")
				+ closeTag);
		controls.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(controls);

		for(int i = 0; i < cELEMENTS; i++) {
			cButtons[i] = new Button();
			cButtons[i].setMaximumSize(new Dimension(550, 50));
			cButtons[i].setLabel(controlLabels[i]);

			cButtons[i].addActionListener(this);
			add(cButtons[i]);
		}
		*/
		setVisible(true);
	}

}
