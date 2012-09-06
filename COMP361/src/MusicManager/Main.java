package MusicManager;



import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;

import RequestHandler.RequestHandler;

import jgroove.StreamingManager;
import jgroove.IStream.topPlayed;

/**
 * @author Rebecca
 * Main entry to the server application; does set-up and initialization.
 */
public class Main {

	/* The MusicManager */
	private static MusicManager mManager;
	
	
	private static FileChannel channel;
   private static FileLock lock;
   private static File f;
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		f = null;
		 try {

             f = new File("scherzo.lock");
             System.out.println("Creating lock.");
             // Check if the lock exist
             if (f.exists()) {
            	 throw new RuntimeException("Cannot run multiple instances at once.");
             }
             // Try to get the lock
             channel = new RandomAccessFile(f, "rw").getChannel();
             lock = channel.tryLock();
             if(lock == null) {
                 channel.close();
                 throw new RuntimeException("Cannot run multiple instances at once.");
             }

			// The shutdown hook to release lock when application is done
	         ShutdownHook shutdownHook = new ShutdownHook();
	         Runtime.getRuntime().addShutdownHook(shutdownHook);
	 
			// Initiate the MusicManager.
			mManager = new MusicManager(true);
				
			RequestHandler r = new RequestHandler(-1, mManager);
			r.start();
			
			try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		 } catch(IOException e) {
             e.printStackTrace();
         }


		 
		//streamingTest();
	}
	

	/* Release and delete lock */
    public static void unlockFile() {
        try {
            if(lock != null) {
            	System.out.println("Releasing the lock.");
                lock.release();
                channel.close();
                f.delete();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    static class ShutdownHook extends Thread {
    	public void run() {
            unlockFile();
        }
    }

    
	public static void streamingTest() throws Exception
	{
		 String CurLine = ""; // Line read from standard in
         
		  System.out.println("PROOF OF CONCEPT GROOVESHARK STREAMING (type 'quit' to exit): ");
         InputStreamReader converter = new InputStreamReader(System.in);
         BufferedReader in = new BufferedReader(converter);
         
		StreamingManager jshark = new StreamingManager(null);
		
		ArrayList<Song> songs;        
		  while (!(CurLine.equals("quit"))){
			  
			  System.out.println("[ 1 ] Month Top songs");
			  System.out.println("[ 2 ] Day Top songs");
			  System.out.println("[ 3 ] Search");
			  System.out.print("Choose: ");
			  CurLine = in.readLine();
			  
			  if (CurLine.equals("1"))
			  {
				  songs = jshark.top(topPlayed.monthly);
			  }
			  else if (CurLine.equals("2"))
			  {
				  songs = jshark.top(topPlayed.daily);
			  }
			  else if ((CurLine.equals("3")))
			  {
				  System.out.print("query: ");
				  String query = in.readLine();
				  songs = jshark.search(query);
			  }
			  else
			  {
				  break;
			  }
			  
			  for (int i = 0;i<songs.size();i++){

					System.out.println(i+" : " + songs.get(i).getTitle() + " - " + songs.get(i).getArtist() + " | " + songs.get(i).getAlbum());
					
				}
			  
			  System.out.print("Choose one: ");
			  int index = Integer.parseInt(in.readLine());
			  
			 String songID = songs.get(index).getStreamingID();
			 String filename = songs.get(index).getTitle();
			 
			 //jshark.play(songs.get(index));
		  }
	}
}
