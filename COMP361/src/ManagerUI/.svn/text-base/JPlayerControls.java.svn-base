package ManagerUI;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import MusicManager.MusicManager;

/**
 * @author Rebecca
 * The Music Player controls panel.
 */
@SuppressWarnings("serial")
public class JPlayerControls extends JPanel implements ActionListener {

	/** Image dimensions in pixels */
	private final int SIZE = 30;
	
	/** Control buttons */
    private final JButton mGo;
    private final JButton mPause;
    private final JButton mStop;
    private final JButton mSkip;
    
	/** The MusicManager */
    private MusicManager mManager;
    public JPlayerControls(MusicManager m, MainFrame f) {
		mManager = m;
		ImageIcon playImg = new ImageIcon("imgs/play.png");
		mGo = new JButton(scale(playImg.getImage()));
		mGo.setActionCommand("Go");
		mGo.addActionListener(this);
		mGo.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.add(mGo);
		
		ImageIcon pauseImg = new ImageIcon("imgs/pause.png");
		mPause = new JButton(scale(pauseImg.getImage()));
		mPause.setActionCommand("Pause");
		mPause.addActionListener(this);
		mPause.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.add(mPause);
		
		ImageIcon stopImg = new ImageIcon("imgs/stop_2.png");
		mStop = new JButton(scale(stopImg.getImage()));
		mStop.setActionCommand("Stop");
		mStop.addActionListener(this);
		mStop.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.add(mStop);
		
		ImageIcon skipImg = new ImageIcon("imgs/forward.png");
		mSkip = new JButton(scale(skipImg.getImage()));
		mSkip.setActionCommand("Skip");
		mSkip.addActionListener(this);
		mSkip.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.add(mSkip);
		
		setVisible(true);
	}

	
	/*
	 * Scales whatever images selected for the control icons.
	 */
	public ImageIcon scale(Image img) {
	     int type = BufferedImage.TYPE_INT_RGB;
	     BufferedImage dst = new BufferedImage(SIZE, SIZE, type);
	     Graphics2D g2 = dst.createGraphics();
	     g2.drawImage(img, 0, 0, SIZE, SIZE, this);
	     g2.dispose();
	     return new ImageIcon(dst);		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(mGo == e.getSource()) {
			if(!mManager.isLive() || (mManager.isLive() && mManager.ismQueueIsPaused())) {
				try {
					mManager.goLive();
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else if(mPause == e.getSource()) {
			mManager.pause();
			
		} else if(mStop == e.getSource()) {
			if(mManager.isLive()) {
				try {
					mManager.stopNow();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else if(mSkip == e.getSource()) {
			try {
				if(!mManager.ismQueueIsPaused()) {
					mManager.pause();
				}
				mManager.skip();
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				waiting(2);
				mManager.goLive();
				setCursor(Cursor.getDefaultCursor());
				
				if(mManager.getmFrame() != null) {
					// If currently viewing the queue, repaint it
					if(mManager.getmFrame().getListViewStatus() == 0) {
						mManager.getmFrame().repaintListArea(0);
					}
				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	 
	public static void waiting (int n){
	        long t0, t1;

	        t0 =  System.currentTimeMillis();

	        do{
	            t1 = System.currentTimeMillis();
	        }
	        while ((t1 - t0) < (n * 1000));
	  }
}
