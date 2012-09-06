package com.me.android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

/**
 *  The Playling Tab for the music requester. This contains a list of the songs that will 
 *  be played next.
 * This tab also has a menu that appears at the bottom of the screen.
 * @author Alicia Bendz
 *
 */
public class PlayingTab extends ListActivity {
	/**
	 * The alert dialog used to communicate with the user.
	 */
	private AlertDialog mAlert;
	
	/**
	 * Constant to indicate that the dialog to create is a song info dialog.
	 */
	private static final int SONG_INFO_DIALOG = 0;
	
	/**
	 * Constant to indicate that the dialog to create is the help dialog.
	 */
	private static final int HELP_DIALOG = 1;
	
	/**
	 * Constant to indicate that the dialog to create is the settings dialog.
	 */
	private static final int SETTINGS_DIALOG = 2;
	
	/**
	 * List Adaptor for the songs.
	 */
	private ArrayAdapter<String> mListAdapter;
	
	/**
	 * List to store the songs. String of what is displayed to the song object.
	 */
	private List<SongJson> mSongs;
	
	/**
	 * The selected song by user.
	 */
	private SongJson mSelectedSong = null;
	
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	//initialize song list
    	mSongs = new ArrayList<SongJson>();

    	//set up list view for songs
    	mListAdapter = new ArrayAdapter<String>(this, R.layout.list_item, new ArrayList<String>());
    	setListAdapter(mListAdapter);
    	final ListView lv = getListView();
    	lv.setTextFilterEnabled(true);
    	setContentView(lv);
    	  
    	//add click listener for list items to show song info dialog on click
    	lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
		    	//create dialog and set selected song
				try{ 
					mSelectedSong = mSongs.get(index);
				}catch (IndexOutOfBoundsException ioobe){
					//if the given list is empty, do nothing
					return;
				}
				
		    	mAlert = (AlertDialog) onCreateDialog(SONG_INFO_DIALOG);
		    	if(mAlert != null)
					mAlert.show();

			}});
    	
    	//get playing list
    	new GetPlaying().execute(new Void[]{null});
    }
    
    /**
     * Sets the displayed list to show the current songs.
     */
    private void getSongs(){
    	//clear current list
    	mListAdapter.clear();
    	
    	//update list
    	for(SongJson s : mSongs){
    		mListAdapter.add(s.getTitle() + " - " + s.getArtist());
    	}
    }
    
    @Override
    public Dialog onCreateDialog(int id){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	
    	switch(id){
    	case SONG_INFO_DIALOG:
    		//add the song info if a valid song is selected, otherwise return null
    		if(mSelectedSong == null)
    			return null;
    		builder.setTitle(getResources().getString(R.string.song_info));
    		builder.setMessage(getResources().getString(R.string.title) 
    				+ ": " + mSelectedSong.getTitle() 
    				+ " \n" + getResources().getString(R.string.artist) 
    				+ ": " + mSelectedSong.getArtist() 
    				+ " \n" + getResources().getString(R.string.album)
    				+ ": " + mSelectedSong.getAlbum()
    				+ " \n" + getResources().getString(R.string.length)
    				+ ": " + mSelectedSong.getLength());
    		
    		builder.setPositiveButton(getResources().getString(R.string.done),
    				new DialogInterface.OnClickListener(){
    			//add "done" button to close dialog
    			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//set selected song to null
					mSelectedSong = null;
					return;
				}
    			
    		});
    		return builder.create();
    	case HELP_DIALOG:
    		builder.setMessage(getResources().getString(R.string.help_playing));
    		builder.setTitle(getResources().getString(R.string.help));
    		builder.setPositiveButton(getResources().getString(R.string.done),
    				new DialogInterface.OnClickListener(){
    			//add "done" button to close dialog
				@Override
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
    			
    		});
    		
    		return builder.create();
    	case SETTINGS_DIALOG:
    		builder.setMessage(getResources().getString(R.string.settings_text));
    		builder.setTitle(getResources().getString(R.string.settings));
    		
    		LayoutInflater factory = LayoutInflater.from(this);
            final View serverName = factory.inflate(R.layout.settings, null);
            
            final EditText server = (EditText) serverName.findViewById(R.id.serverText);
            final EditText port = (EditText) serverName.findViewById(R.id.portText);
            
    		builder.setPositiveButton(getResources().getString(R.string.done), 
    				new DialogInterface.OnClickListener(){
    			//add "done" button to close dialog
    			//update entered data - if data is invalid then don't change values
				@Override
				public void onClick(DialogInterface dialog, int which) {
					int temp = MusicRequesterActivity.PORT;
					try{
						if(!port.getText().toString().equals("" 
										+ MusicRequesterActivity.PORT))
							MusicRequesterActivity.PORT = 
								Integer.parseInt(port.getText().toString());
						
						if(!server.getText().toString()
									.equals(MusicRequesterActivity.SERVER))
							MusicRequesterActivity.SERVER = server.getText().toString();
					}catch (NumberFormatException nfe){
						//restore
						MusicRequesterActivity.PORT = temp;
						return;
					}
					
					return;
				}
    			
    		});
    		
    		builder.setNegativeButton(getResources().getString(R.string.cancel), 
    				new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//change nothing
				}
			});
    		
    		builder.setView(serverName);
    		server.setText(MusicRequesterActivity.SERVER);
    		port.setText("" + MusicRequesterActivity.PORT);
    		
    		return builder.create();
    	default:
    		return null;
    	}
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	//create menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
    	switch (item.getItemId()) {
        case R.id.refresh:
        	//send a request for the playing list
        	new GetPlaying().execute(new Void[]{null});
            return true;
        case R.id.help:
        	//this should display a popup showing help related to this tab
        	mAlert = (AlertDialog) onCreateDialog(HELP_DIALOG);
			mAlert.show();
            return true;
        case R.id.settings:
        	//show settings dialog
        	mAlert = (AlertDialog) onCreateDialog(SETTINGS_DIALOG);
			mAlert.show();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * GetPlaying is an Asynchronous task that gets the currently playing list. This 
     * takes no values, has no progress units, and returns a list of songs.
     * @author Alicia Bendz
     *
     */
    private class GetPlaying extends AsyncTask<Void, Void, List<SongJson>> {

        @Override
        protected List<SongJson> doInBackground(Void... v) {
        	List<SongJson> returnSongs = new ArrayList<SongJson>();
            
            try{
            	//get info for server if you don't have it
            	if(MusicRequesterActivity.PORT == -1){
            		MulticastListener.listen();
            		if(MusicRequesterActivity.PORT == -1)
            			return returnSongs;
            	}
            	
            	//Connect to server and create input and output streams
                Socket s = new Socket(MusicRequesterActivity.SERVER, MusicRequesterActivity.PORT);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in 
                	= new BufferedReader(new InputStreamReader(s.getInputStream()));
                
                //create request
                RequestJson request = new RequestJson(RequestType.PLAYING, 
                		MusicRequesterActivity.SOURCE,
                		null, null, null);
                
                //create response and Gson object
                ResponseJson response;
                Gson gson = new Gson();
                
                //send request
                out.println(gson.toJson(request));
                
                //retrieve playing list
                response = gson.fromJson(in.readLine(), ResponseJson.class);
                
                //if there was no error and the response is of the correct type
                //read the list. Otherwise, throw an exception.
                if(response.getErrorMessage() == null && response.getType() == RequestType.PLAYING)
                	returnSongs.addAll(response.getSongs());
                else
                	throw new Exception(response.getErrorMessage());
                
                //close connection
                s.close();
                in.close();
                out.close();
                
            }catch (Exception e){
            	//Log exceptions
                Log.e(getLocalClassName(), "Playing list request send failed", e);
                returnSongs = null;
                
                //reset the port in case this is a connectivity problem
                MusicRequesterActivity.PORT = -1;
            }
            
            return returnSongs;
        }
       
        @Override
        protected void onPostExecute(List<SongJson> l) {
        	//update lists of songs
        	mSongs.clear();
        	
        	if(l == null)
        		return;
        	
        	mSongs.clear();
        	mSongs.addAll(l);
        	getSongs();
        }
    }
}
