package com.me.android;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import android.util.Log;

/**
 * Mutlicast listener class will listen at the agreed upon port for the program group
 * to find out where the server is.
 * @author Alicia Bendz
 *
 */
public class MulticastListener{
	/**
	 * The multicast group.
	 */
	private static final String GROUP = "234.1.2.3";
	
	/**
	 * The port to receive multicasts.
	 */
	private static final int PORT = 2013;

	/**
	 * The method called that will listen for a server.
	 */
	public static void listen() {
		Log.v("Multicast Listener", "Looking for the server");
		
		try {
			//set up socket and group
			Log.v("Multicast Listener", "In try");
			InetAddress group = InetAddress.getByName(GROUP);
			MulticastSocket socket = new MulticastSocket(PORT);
			socket.setSoTimeout(5000);
			socket.joinGroup(group);
			byte[] buffer;
			DatagramPacket packet;
			String received;

			//receive packet
			buffer = new byte[500];
			packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			received = new String(packet.getData(), 0, packet.getLength());

			//decode packet
			String[] addresses = decode(received);

			//if packet has good data, use it
			if(addresses[0] != null){
				Log.v("Multicast Listener", "Setting Server to " + addresses[0] + " " 
							+ addresses[1]);
				MusicRequesterActivity.SERVER = addresses[0];
				MusicRequesterActivity.PORT = Integer.parseInt(addresses[1]);
			}

			Log.v("Multicast Listener", "Exiting");
		} catch (Exception e) {
			Log.e("Multicast Listener:","Error looking for server.", e);
		}
	}
	
	/**
	 * A decoding method for the messages sent in multicast.
	 * @param message The message to decode.
	 * @return The decoded array holding first the IP and next the port of the server.
	 */
	private static String[] decode(String message){
		//TODO
		return message.split(" ");
	}

}
