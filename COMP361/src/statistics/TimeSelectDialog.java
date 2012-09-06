package statistics;

import globalAccess.Global;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

/**
 * Time Select Dialog to select dates for the range of data for statistics generation
 * in the statistics UI.
 * @author Alicia Bendz
 *
 */
@SuppressWarnings("serial")
public class TimeSelectDialog extends JDialog {
	/**
	 * Spinner to use to select dates.
	 */
	private final SpinnerDateModel mDateModel;
	
	/**
	 * Parent statistics UI.
	 */
	private final StatisticsUI mParent;
	
	/**
	 * Variable to indicate whether the start date or the end date is being chosen.
	 */
	private boolean mStart;
	
	/**
	 * Constructor for the time select dialog with a reference to the parent 
	 * Statistics UI.
	 * @param parent The parent statistics UI.
	 */
	public TimeSelectDialog(StatisticsUI parent){
		super();
		
		//formatting and settings
		setAlwaysOnTop(true);
		setBackground(new Color(214, 217, 223));
		
		//initialization
		mParent = parent;
		mDateModel = new SpinnerDateModel(new Date(), new Date(0), new Date(), Calendar.HOUR);
		
		//create the spinner
		JSpinner date = new JSpinner(mDateModel);
		
		//create main panel
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//create instructional panel
		JPanel tempPanel = new JPanel();
		tempPanel.setBackground(new Color(214, 217, 223));
		tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.X_AXIS));
		tempPanel.add(new JLabel(Global.getLOCInstance()
				.getLocalizedString("StatsTime.instructions")));
		panel.add(tempPanel);
		
		//create date panel
		tempPanel = new JPanel();
		tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.X_AXIS));
		tempPanel.setBackground(new Color(214, 217, 223));
		date.setBackground(Color.WHITE);
		tempPanel.add(date);
		panel.add(tempPanel);
		panel.setBackground(new Color(214, 217, 223));
		
		//create and add okay button
		final JButton okay = new JButton(Global.getLOCInstance()
				.getLocalizedString("StatsTime.okay"));
		okay.setBackground(Color.WHITE);
		
		panel.add(okay);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//when okay is clicked, store selected date with parent statistics UI
		okay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() == okay){
					mParent.setDate(mStart, (Date) mDateModel.getValue());
					setVisible(false);
				}
			}});
		
		add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(false);
	}
	
	/**
	 * Set the range of acceptable dates and the currently selected date.
	 * @param d The currently selected date.
	 * @param start The lower bound date.
	 * @param end The upper bound date.
	 * @param startEnd Whether you are selecting a start date (true) or end date (false).
	 */
	protected void setTime(Date d, Date start, Date end, boolean startEnd){
		mDateModel.setEnd(end);
		mDateModel.setStart(start);
		mDateModel.setValue(d);
		mStart = startEnd;
	}
}
