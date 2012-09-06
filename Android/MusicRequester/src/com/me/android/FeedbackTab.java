package com.me.android;

import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * Feedback tab for music requester. 
 * Contains a text box and submission button for feedback.
 * @author Alicia Bendz
 *
 */
public class FeedbackTab extends Activity {
	/**
	 * The feedback text box and button.
	 */
	private EditText mText;
	private Button mButton;
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //set layout
        setContentView(R.layout.feedback);
        
        //get text box and button
        mText = (EditText) findViewById(R.id.editText1);
        mButton = (Button) findViewById(R.id.button1);
        
        //add the click listener to the button
        mButton.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View v) {
				sendMessage();
				
				//show toast for feedback confirmation
				Toast.makeText(v.getContext(), getResources()
						.getString(R.string.feedback_thanks), Toast.LENGTH_LONG).show();
			}});
    }
    
    /**
     * This method will read the message currently entered in the text box and
     * then create the asynchronous task SubmitMessage to send the feedback.
     */
    public void sendMessage(){
    	//create the asynchronous task
        SubmitMessage s = new SubmitMessage();
        
        //send the feedback message
        s.execute(new String[]{
        		mText.getText().toString()
        	});
    }
    
    /**
     * The SubmitMessage is an Asynchronous Task used to send feedback messages 
     * to the server. A String will be sent and one will be expected as a result. 
     * There are no progress units.
     * @author Alicia Bendz
     *
     */
    private class SubmitMessage extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... messages) {
            try{
            	//get server info if you don't have it
            	if(MusicRequesterActivity.PORT == -1){
            		MulticastListener.listen();
            		if(MusicRequesterActivity.PORT == -1)
            			return null;
            	}
            	
            	//Connect to server and create input and output streams
                Socket s = new Socket(MusicRequesterActivity.SERVER,
                		MusicRequesterActivity.PORT);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                RequestJson request = new RequestJson(RequestType.FEEDBACK,
                		MusicRequesterActivity.SOURCE, null, messages[0], null);
                Gson gson = new Gson();
                
                //send feedback message
                out.println(gson.toJson(request));
                
                //close connection
                s.close();
                out.close();
                
            }catch (Exception e){
            	//Log exceptions
                Log.e(getLocalClassName(), "Feedback send failed", e);
                mText.setText("");
                
                //reset the port in case this is a connectivity problem
                MusicRequesterActivity.PORT = -1;
            }
            
            return null;
        }
       
        @Override
        protected void onPostExecute(Void v) {
        	//when result is received, clear text box
            mText.setText("");
        }
    }
}
