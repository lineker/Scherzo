package RequestHandler;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import MusicManager.MusicManager;

/**
 * The Request Handler. This component is responsible for listening for and creating
 * connections to clients.
 * @author Alicia Bendz
 *
 */
public class RequestHandler extends Thread {
	/**
	 * The port to listen to. The default value is 2011.
	 */
	private static int mPort = 2014;
	
	/**
	 * The ServerSocket representing the port where the server is listening.
	 */
	private ServerSocket mServerSocket = null;
	
	/**
	 * A reference to the Music Manager of the system.
	 */
	private final MusicManager mMusicManager;
	
	/**
	 * The queue of waiting connections using a LinkedList for implementation.
	 */
	private final LinkedList<Socket> mConnectionQueue;
	
	/**
	 * The handler thread for this request handler.
	 */
	private final HandlerThread mHandlerThread;
	
	/**
	 * The multicasting thread for this requestHandler.
	 */
	private final AddressMulticaster mMulticaster;
	
	/**
	 * The shutdown variable.
	 */
	private Boolean mShutdown = new Boolean(false);
	
	/**
	 * A constructor that will set the values of the port and the Music Manager.
	 * If the port is set to anything other than an allowed port, it will take the default.
	 * @param port - The port to listen at.
	 * @param manager - The Music Manager of the system.
	 * @throws UnknownHostException 
	 */
	public RequestHandler(int port, MusicManager manager) throws UnknownHostException{
		mConnectionQueue = new LinkedList<Socket>();
		if(port > 1023 && port < 49152)
			mPort = port;
		
		mMusicManager = manager;
		mHandlerThread = new HandlerThread();
		mMulticaster = new AddressMulticaster(InetAddress.getLocalHost(), mPort);
	}
	
	/**
	 * The Shutdown method that sets the shutdown variable to stop both the
	 * RequestHandler and HandlerThread. This also closes the ServerSocket to
	 * stop accepting further connections.
	 */
	public void shutdown(){
		
		synchronized(mShutdown){
			mShutdown = new Boolean(true);
		}
		
		mMulticaster.shutdown();
		
		try {
			mServerSocket.close();
		} catch (IOException e) {
			System.err.println("Request Handler: Failed to close Server Socket.");
			System.err.println(e.getMessage());
		}
		
	}
	
	@Override
	public void run(){
		//Client connection
		Socket clientConnection;
		
		//Set up listening port
		try {
			mServerSocket = new ServerSocket(mPort);
		} catch (IOException e) {
			//Upon failure to create listening port, exit
			System.err.println("Request Handler: Could not establish listening port. Terminating.");
			System.err.println(e.getMessage());
			return;
		}
		
		//Start handler thread
		mHandlerThread.start();
		
		//start multicaster
		new Thread(mMulticaster).start();
		
		//Listen for connections
		while(true){
			
			synchronized(mShutdown){
				if(mShutdown.booleanValue())
					break;
			}
			
			System.out.println("Listening...");
			try {
				clientConnection = mServerSocket.accept();
				System.out.println("Accepted connection from: " + clientConnection.getPort());
			} catch (IOException e) {
				System.err.println("Request Handler: Could not establish connection.");
				System.err.println(e.getMessage());
				continue;
			}
			
			synchronized(mConnectionQueue){
				mConnectionQueue.addLast(clientConnection);
			}
		}
		
		System.out.println("RequestHandler: shutting down.");
	}

	/**
	 * Thread that takes waiting connections and starts ProcessingThreads for them.
	 * @author Alicia Bendz
	 *
	 */
	private class HandlerThread extends Thread {
		/**
		 * Constant to limit the number of threads running.
		 */
		private static final int THREADPOOL = 5;
		
		/**
		 * Constant indicating the time in milliseconds to wait before checking for waiting 
		 * connections again.
		 */
		private static final int SLEEPTIME = 1000;
		
		/**
		 * The current thread pool
		 */
		private ProcessingThread[] mThreads;
		
		/**
		 * Constructor for the HanlderThread. Initializes the thread pool.
		 */
		protected HandlerThread(){
			mThreads = new ProcessingThread[THREADPOOL];
			for(int i = 0; i < THREADPOOL; i++){
				mThreads[i] = null;
			}
		}

		@Override
		public void run() {
			//variable to check the size of the connection queue
			int queueSize = -1;
			
			//repeatedly check for connections and start processing if there are any
			while(true){
				
				synchronized(mShutdown){
					if(mShutdown.booleanValue())
						break;
				}
				
				//get size of connection queue
				synchronized(mConnectionQueue){
					queueSize = mConnectionQueue.size();
				}
				
				//if there are no waiting connections, wait for some specified time
				if(queueSize <= 0){
					try {
						Thread.sleep(SLEEPTIME);
					} catch (InterruptedException e) {
						System.err.println("HandlerThread: Could not sleep thread.");
						System.err.println(e.getMessage());
					}
				}
				//if there are connections, call the start connection method
				else{
					startConnection();
				}
			}
			
			System.out.println("Handler Thread: Shutting down.");
		}
		
		/**
		 * Method that starts a ProcessingThread for the first connection waiting in the 
		 * connection queue
		 */
		private void startConnection(){
			//indices to keep track of the location of the first free thread and the current 
			//place in the thread pool
			int freeThread = -1;
			int i = 0;
			
			//while a free position still hasn't been found
			while(freeThread < 0){
				//check to see if the current thread is done
				if(mThreads[i] != null && !mThreads[i].isAlive()){
					mThreads[i] = null;
				}
					
				//if the current thread is no longer running and a position hasn't been 
				//found, use this position
				if(mThreads[i] == null && freeThread < 0){
					freeThread = i;
				}
				
				i = (i+1) % THREADPOOL;
			}
			
			//start the processing thread for the first connection in the queue
			synchronized(mConnectionQueue){
				mThreads[freeThread] = new ProcessingThread(mConnectionQueue.removeFirst(), 
						mMusicManager);
				mThreads[freeThread].start();
			}
		}
	}
}
