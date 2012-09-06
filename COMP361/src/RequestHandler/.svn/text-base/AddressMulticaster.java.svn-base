package RequestHandler;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * This will be a thread that repeatedly multicasts the ip and port of the media server
 * across an agreed upon multicast group.
 * @author Alicia Bendz
 *
 */
public class AddressMulticaster implements Runnable {
	/**
	 * The port of the server.
	 */
	private final int mPort;
	
	/**
	 * The address of the server.
	 */
	private final InetAddress mAddress;
	
	/**
	 * The interval to send each broadcast at.
	 */
	private static final int BROADCAST_INTERVAL = 2000;
	
	/**
	 * The multicast group to use.
	 */
	private static final String GROUP = "234.1.2.3";
	
	/**
	 * The port to send multicasts on.
	 */
	private static final int PORT = 2013;
	
	/**
	 * The shutdown switch.
	 */
	private boolean mShutdown = false;
	
	/**
	 * A single constructor to broadcast the input address and port.
	 * @param address The address to multicast.
	 * @param port The port to multicast.
	 */
	public AddressMulticaster(InetAddress address, int port){
		mPort = port;
		mAddress = address;
	}

	@Override
	public void run() {
		byte[] buffer;
		
		try {
			//connect and set up
			InetAddress group = InetAddress.getByName(GROUP);
			DatagramPacket packet;
			DatagramSocket socket = new DatagramSocket();
			
			while(!mShutdown){
				//encode and send
				buffer = (encodeAddress()).getBytes();
				packet = new DatagramPacket(buffer, buffer.length, group, PORT);
				socket.send(packet);
				//wait
				Thread.sleep(BROADCAST_INTERVAL);
			}
			
		} catch (Exception e) {
			System.err.println("Address Multicaster: ");
			e.printStackTrace();
		}
	}
	
	/**
	 * A method to encode the sent address which will include the port and IP.
	 * @return The encoded message to send.
	 */
	private String encodeAddress(){
		return "" + mAddress.getHostAddress() + " " + mPort;
	}
	
	/**
	 * A method that will stop the multicast.
	 */
	public void shutdown(){
		mShutdown = true;
	}

}
