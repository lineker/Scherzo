package RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import request.json.RequestJson;
import request.json.RequestType;
import request.json.ResponseJson;

import com.google.gson.Gson;

/**
 * This is a testing class to simulate a client sending requests to the request handler.
 * The client will submit one request of each type.
 * @author Alicia Bendz
 *
 */
public class Client implements Runnable {
	/**
	 * The id of the client.
	 */
	private final int mID;
	
	/**
	 * A constructor to set the id of the client. The ids do not need to be unique
	 * but for testing purposes one might choose to make them unique.
	 * @param i The desired id.
	 */
	public Client(int i){
		mID = i;
	}

	@Override
	public void run() {
		//create sockets, streams, and required json objects.
		Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        Gson gson = new Gson();
        RequestJson req = null;
        ResponseJson resp = null;
        
        System.out.println("Start Client " + mID);
        try {
        	//connect and send feedback
            socket = new Socket("localhost", 2011);
            System.out.println("Client " + mID + " connected.");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            System.out.println("Client " + mID + " sending feedback.");
            req = new RequestJson(RequestType.FEEDBACK, "TEST_CLIENT", null,
            		"Feedback test from client " + mID, null);
            
            out.println(gson.toJson(req));
            
            socket.close();
            out.close();
            in.close();
            
            //connect and send a playlist request
            socket = new Socket("localhost", 2011);
            System.out.println("Client " + mID + " connected.");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Client " + mID + " requesting playlist.");
            req = new RequestJson(RequestType.PLAYLIST, "TEST_CLIENT", null,
            		"Playlist test from client " + mID, null);
            out.println(gson.toJson(req));
            resp = gson.fromJson(in.readLine(), ResponseJson.class);
            
            if(resp.getErrorMessage() == null)
            	System.out.println("Client " + mID + " received playlist.");
            else
            	System.err.println("Client " + mID + " playlist error: " 
            				+ resp.getErrorMessage());
            
            socket.close();
            out.close();
            in.close();
            
            //connect and send a playing list request
            socket = new Socket("localhost", 2011);
            System.out.println("Client " + mID + " connected.");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Client " + mID + " requesting playing.");
            req = new RequestJson(RequestType.PLAYING, "TEST_CLIENT", null,
            		"Playing request from client " + mID, null);
            out.println(gson.toJson(req));
            resp = gson.fromJson(in.readLine(), ResponseJson.class);
            
            if(resp.getErrorMessage() == null)
            	System.out.println("Client " + mID + " received playing.");
            else
            	System.err.println("Client " + mID + " playing error: " 
            				+ resp.getErrorMessage());
            
            socket.close();
            out.close();
            in.close();
            
            //connect and send a song request with no time
            socket = new Socket("localhost", 2011);
            System.out.println("Client " + mID + " connected.");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Client " + mID + " song request.");
            req = new RequestJson(RequestType.SONGREQUEST, "TEST_CLIENT", "1",
            		"Song request from client " + mID, null);
            out.println(gson.toJson(req));
            
            socket.close();
            out.close();
            in.close();
            
            //connect and send a song request with a time
            socket = new Socket("localhost", 2011);
            System.out.println("Client " + mID + " connected.");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Client " + mID + " song request with play time.");
            req = new RequestJson(RequestType.SONGREQUEST, "TEST_CLIENT", "1",
            		"Song request from client " + mID, "13:40");
            out.println(gson.toJson(req));
            
            System.out.println("Client " + mID + " done.");
            
            socket.close();
            out.close();
            in.close();
            
        } catch (Exception e) {
            System.err.println("Client " + mID + " error: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        } finally {
        	if(socket != null && !socket.isClosed()){
				try {
					socket.close();
				} catch (IOException e) {
					System.err.println("Cannot close socket for client " + mID);
				}
        	}
        	
        	if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					System.err.println("Cannot close input stream for client " + mID);
				}
        	}
        	if(out != null)
        		out.close();
        }
	}
}
