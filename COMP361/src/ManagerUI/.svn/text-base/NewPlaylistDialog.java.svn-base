package ManagerUI;

import globalAccess.Global;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import MusicManager.MusicManager;

/**
 * Dialog for users to create a new playlist.
 * @author Rebecca
 */

@SuppressWarnings("serial")
public class NewPlaylistDialog extends JDialog implements ActionListener {
    
    /**submit buttons */
    private final JButton mCreateButton;
    private final JTextField mInput;
    private final JButton mCancelButton;
    
    /** Main button panel*/
    private final JPanel buttonPanel;
    
    /** The MusicManager */
    private MusicManager mManager;
    
    /**
     * Constructor
     */
    public NewPlaylistDialog(MusicManager m){
        setTitle(Global.getLOCInstance().getLocalizedString("CreatePlaylist"));
        mManager = m;
        
        buttonPanel = new JPanel();
        final JPanel prompt = new JPanel(new BorderLayout());
        
        mInput = new JTextField(20);
        mCreateButton = new JButton(Global.getLOCInstance().getLocalizedString("Sidebar.CreatePlaylist"));
        mCancelButton = new JButton(Global.getLOCInstance().getLocalizedString("Button.Cancel"));
        
        mCreateButton.addActionListener(this);
        mCancelButton.addActionListener(this);
        mInput.addActionListener(this);
        
        buttonPanel.add(mCancelButton);
        buttonPanel.add(mCreateButton);
        prompt.add(new JLabel(Global.getLOCInstance().getLocalizedString("EnterName")), BorderLayout.WEST);
        prompt.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        prompt.add(mInput, BorderLayout.CENTER);
        prompt.add(buttonPanel, BorderLayout.SOUTH);
    
        getContentPane().add(prompt);  
        setLocationRelativeTo(getContentPane());
        pack();
        setVisible(true);
    }

    /**
     * Determine action based on button click.
     * @param event The triggering action event.
     */
    @SuppressWarnings("static-access")
	@Override
    public void actionPerformed(ActionEvent event) {
        if(mCreateButton == event.getSource()) {
        	
        	String name = mInput.getText();
        	
        	if(mManager.validatePlaylist(name)) {
        		// Insert into the database.
        		try {
					Global.getSRVInstance().PlaylistService().insertPlaylist(name);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					displayErrorMessage(Global.getLOCInstance().getLocalizedString("CreationError"));
				} catch (Exception e) {
					e.printStackTrace();
					displayErrorMessage(Global.getLOCInstance().getLocalizedString("CreationError"));
				}
        		
                setVisible(false);
        	} else {
        		displayErrorMessage(Global.getLOCInstance().getLocalizedString("NamingError"));
        	}
        }
        else if(mCancelButton == event.getSource()) {
        	setVisible(false);
        }
    }
    
    /**
     * Displays appropriate error message.
     * @param msg
     */
    private void displayErrorMessage(String error) {
    	JOptionPane.showMessageDialog(this, error);
    }
    
 }