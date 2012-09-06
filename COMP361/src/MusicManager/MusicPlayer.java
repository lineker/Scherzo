package MusicManager;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * @author Rebecca
 * The thread of the MusicManager that is responsible for playing a 
 * single music file.
 */
public class MusicPlayer {

	private String source;
	private Player player;
	
	private boolean isPlaying;
	
	/** The handle on the streaming for pause and stop. */
	final AtomicBoolean mContinue = new AtomicBoolean(true);
	
	/**
	 * For testing only.
	 * @param src
	 */

	public MusicPlayer() {
		source = null;
		player = null;
		isPlaying = false;
		mContinue.set(true);
	}
	
	/**
	 * Terminates the player.
	 */
	public synchronized void close() { 
		isPlaying = false;
		if(player != null) {
			player.close(); 
			player = null;
		}

		mContinue.set(false);

		System.out.println("Returning the MusicPlayer.");
		return;
	}
	
	
	/**
	 * Plays the sound file and terminates upon completion.
	 * @param src the file to play
	 */
	public void play(String src) {
		source = src;
		
		if(source != null) {
			System.out.println("Starting the playing thread.");
	        try {
	            FileInputStream fis     = new FileInputStream(source);
	            BufferedInputStream bis = new BufferedInputStream(fis);
	            player = new Player(bis);
	        }
	        catch (Exception e) {
	            System.out.println("Problem playing file: " + source);
	            System.out.println(e);
	        }
	        
	        try {
				player.play();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
	        
	        /*
	        // Play the file in a new thread.
			t = new Thread() {
				public void run() {
					try {
						player.play();
						isPlaying = true;
					} catch(Exception e) {
						return;
					}
				}
			};
			t.start();
			*/
		} else  {
			System.out.println("File source was null; can't play it.");
		}

	} 
	
	public boolean getIsPlaying() {
		return isPlaying;
	}
	public void setIsPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	
	public void play(InputStream fis, Song song) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		 AudioInputStream in= AudioSystem.getAudioInputStream(fis);
		    AudioInputStream din = null;
		    AudioFormat baseFormat = in.getFormat();
		    AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
		                                                                                  baseFormat.getSampleRate(),
		                                                                                  16,
		                                                                                  baseFormat.getChannels(),
		                                                                                  baseFormat.getChannels() * 2,
		                                                                                  baseFormat.getSampleRate(),
		                                                                                  false);
		    din = AudioSystem.getAudioInputStream(decodedFormat, in);
		    
		    rawplay(decodedFormat, din, song);
		    in.close();
	} 
	
	private void rawplay(AudioFormat targetFormat, AudioInputStream din, Song song) throws IOException,LineUnavailableException
	{
	  byte[] data = new byte[4096];
	  SourceDataLine line = getLine(targetFormat); 
	  if (line != null)
	  {
		  
	    // Start
	    line.start();
	    int nBytesRead = 0, nBytesWritten = 0;
	    
	    int downloaded = 0;
	    int percentage = -1;
	    
	    while (nBytesRead != -1)
	    {
	    	if(!mContinue.get()) {
	    		// Quit and drain, close, return.
	    		break;
	    	}
	    	downloaded+=data.length;
	        nBytesRead = din.read(data, 0, data.length);
	        if (nBytesRead != -1) nBytesWritten = line.write(data, 0, nBytesRead);
	    }
	    
	    // Stop
	    line.drain();
	    line.stop();
	    line.close();
	    din.close();
	  } 
	}

	private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException
	{
	  SourceDataLine res = null;
	  DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
	  res = (SourceDataLine) AudioSystem.getLine(info);
	  res.open(audioFormat);
	  return res;
	} 
}
