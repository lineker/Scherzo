package statistics;

import globalAccess.Global;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartUtilities;

/**
 * Statistics viewer to display results of statistics request and allow exporting of
 * either text in csv format or png image.
 * @author Alicia Bendz
 *
 */
@SuppressWarnings("serial")
public class StatisticsViewer extends JFrame implements ActionListener{
	
	/**
	 * The menu items in the viewer.
	 */
	private final JMenuItem[] mMainMenuItems;
	
	/**
	 * The parent statistics generator used for this viewer.
	 */
	private final StatisticsGenerator mSg;
	
	/**
	 * Constructor to make the viewer. This sets up the menu and contents then displays
	 * the frame.
	 * @param sg The parent generator.
	 */
	public StatisticsViewer(StatisticsGenerator sg) {
        super(Global.getLOCInstance().getLocalizedString("StatsView.heading"));
        mSg = sg;
        
        //formatting
        setBackground(new Color(240, 240, 240));
        
        //menu bar
        JMenuBar mainMenuBar = new JMenuBar();
        JMenu mainMenu = new JMenu(Global.getLOCInstance()
        		.getLocalizedString("StatsView.menu"));
        mMainMenuItems = new JMenuItem[2];
        
        //default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        //set key shortcuts
        mainMenu.getAccessibleContext()
        	.setAccessibleDescription(Global.getLOCInstance()
        			.getLocalizedString("StatsView.menu"));
        mainMenuBar.add(mainMenu);
        
        //create menu items
        mMainMenuItems[0] = new JMenuItem(Global.getLOCInstance()
        		.getLocalizedString("StatsView.save"));
        mMainMenuItems[1] = new JMenuItem(Global.getLOCInstance()
        		.getLocalizedString("StatsView.close"));
        
        //add menu items and listener
        for(JMenuItem item : mMainMenuItems){
        	mainMenu.add(item);
        	item.addActionListener(this);
        }
 
        //Display the window.
        setJMenuBar(mainMenuBar);
        pack();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		//if the save option was chosen, save to file
		if(e.getSource() == mMainMenuItems[0]){
			//set up the file chooser
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			String ext;
			File f = null;
			
			//determine the saved file extension
			if(mSg.isChart()){
				chooser.setFileFilter(new FileNameExtensionFilter(".png", ".png"));
				ext = ".png";
			} else {
				chooser.setFileFilter(new FileNameExtensionFilter(".csv", ".csv"));
				ext =".csv";
			}
			
			//get user input
			int ret = chooser.showSaveDialog(this);
			
			//if user selected a file without errors, open that file with the correct
			//extension
			if(ret == JFileChooser.APPROVE_OPTION){
				f = chooser.getSelectedFile();
				f = new File(f.getAbsolutePath() + ext);
			} else {
				JOptionPane.showMessageDialog(this, 
						Global.getLOCInstance().getLocalizedString("StatsView.selectError"), 
						Global.getLOCInstance().getLocalizedString("StatsView.error"), 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try{
				if(mSg.isChart())
					ChartUtilities.saveChartAsPNG(f, mSg.getFreeChart(), getWidth(), getHeight());
				else{
					PrintWriter pw = new PrintWriter(f);
					pw.write(mSg.getCsv());
					pw.close();
				}
			} catch (IOException ioe){
				JOptionPane.showMessageDialog(this, 
						Global.getLOCInstance().getLocalizedString("StatsView.saveError"), 
						Global.getLOCInstance().getLocalizedString("StatsView.error"), 
						JOptionPane.ERROR_MESSAGE);
				ioe.printStackTrace();
			}

		} else if(e.getSource() == mMainMenuItems[1]){
			//dispose of this frame
			dispose();
		}
	}
 
}
