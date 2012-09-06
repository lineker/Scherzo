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
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * The Playlist Tab for the music requester. 
 * This contains a list of the songs that can be requested.
 * This tab also has a menu that appears at the bottom of the screen.
 * @author Alicia Bendz
 *
 */
public class PlaylistTab extends ListActivity {
	/**
	 * The alert dialog used to communicate with the user.
	 */
	private AlertDialog mAlert;
	
	/**
	 * List to store the songs. String of what is displayed to the song object.
	 */
	private List<SongJson> mSongs;
	
	/**
	 * List Adaptor for the songs.
	 */
	private ArrayAdapter<String> mListAdapter;
	
	/**
	 * Variables to indicate the type of alert dialog to display.
	 */
	private static final int REQUEST_DIALOG = 0;
	private static final int TIME_SELECT_DIALOG = 1;
	private static final int HELP_DIALOG = 2;
	private static final int SETTINGS_DIALOG = 3;
	
	/**
	 * The selected song by user.
	 */
	private SongJson mSelectedSong = null;	
	
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	//initialize song list
    	mSongs = new ArrayList<SongJson>();
    	
    	//set up list view for songs
    	mListAdapter = new ArrayAdapter<String>(this, R.layout.list_item, 
    			new ArrayList<String>());
    	setListAdapter(mListAdapter);
    	final ListView lv = getListView();
    	lv.setTextFilterEnabled(true);
    	setContentView(lv);
    	  
    	//add click listener for list items to show request dialog on click
    	lv.setOnItemClickListener(new OnItemClickListener(){

	    	@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
						long arg3) {
	    		//create dialog and set selected song
				try{ 
					mSelectedSong = mSongs.get(position);
				}catch (IndexOutOfBoundsException ioobe){
					//if the given list is empty, do nothing
					return;
				}
				
	        	mAlert = (AlertDialog) onCreateDialog(REQUEST_DIALOG);
	        	
	        	if(mAlert != null)
	        		mAlert.show();
			}});
    	
    	new GetPlaylist().execute(new Void[]{null});
    }
    
    @Override
    public Dialog onCreateDialog(int id){
    	//create the correct dialog based on input
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	
    	switch(id){
    	case REQUEST_DIALOG:
    		//add the song info if a valid song is selected, otherwise return null
    		if(mSelectedSong == null)
    			return null;
    		
    		builder.setTitle(getResources().getString(R.string.song_request) 
    				+ ": " + mSelectedSong.getTitle());
    		
    		//show selected song information
    		builder.setMessage(getResources().getString(R.string.title) 
    				+ ": " + mSelectedSong.getTitle() 
    				+ " \n" + getResources().getString(R.string.artist) 
    				+ ": " + mSelectedSong.getArtist() 
    				+ " \n" + getResources().getString(R.string.album)
    				+ ": " + mSelectedSong.getAlbum()
    				+ " \n" + getResources().getString(R.string.length)
    				+ ": " + mSelectedSong.getLength());
    		
    		builder.setPositiveButton(getResources().getString(R.string.request), 
    				new DialogInterface.OnClickListener(){
    			//click listener to send song request
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//send request
					new SendRequest().execute(new Integer[]{mSelectedSong.getId(), 
										null, null});
					mSelectedSong = null;
					
					Toast toast = Toast.makeText(getBaseContext(), 
							getResources().getString(R.string.request_sent), 
								Toast.LENGTH_LONG);
					toast.show();
					return;
				}
    			
    		});
    		
    		builder.setNegativeButton(getResources().getString(R.string.cancel),
    				new DialogInterface.OnClickListener(){
    			//click listener to close dialog
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//this doesn't do anything
					mSelectedSong = null;
					return;
				}
    			
    		});
    		
    		builder.setNeutralButton(getResources().getString(R.string.select_time), 
    							new DialogInterface.OnClickListener(){
    			//choose to send a time with the song request
				@Override
				public void onClick(DialogInterface dialog, int which) {
					TimePickerDialog time = 
							((TimePickerDialog) onCreateDialog(TIME_SELECT_DIALOG));
					time.setMessage(getResources().getString(R.string.request_time) 
							+ ": "+ mSelectedSong.getTitle());
					time.show();
				}
    			
    		});
    		
    		return builder.create();
    	
    	case TIME_SELECT_DIALOG:
    		return new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
    			
				//a dialog with a time selector
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					//set selected times
					
					new SendRequest().execute(new Integer[]{mSelectedSong.getId(), 
							hourOfDay, minute});
					
					//reset values
					mSelectedSong = null;
					
					Toast toast = Toast.makeText(getBaseContext(), 
							getResources().getString(R.string.request_sent),
							Toast.LENGTH_LONG);
					toast.show();
				}
			}, 12, 0, true);
    		
    	case HELP_DIALOG:
    		builder.setMessage(getResources().getString(R.string.help_playlist));
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
    public boolean onCreateOptionsMenu(Menu menu) {
    	//creates menu
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
        	new GetPlaylist().execute(new Void[]{null});
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
     * The GetPlaylist is an Asynchronous Task used to get the Playlist from the server.
     * There is no input, no progress units, but a list of songs as output. 
     * @author Alicia Bendz
     *
     */
    private class GetPlaylist extends AsyncTask<Void, Void, List<SongJson>> {

        @Override
        protected List<SongJson> doInBackground(Void... v) {
        	List<SongJson> returnSongs = new ArrayList<SongJson>();
            
            try{
            	//get the port if the port has not be retrieved
            	if(MusicRequesterActivity.PORT == -1){
            		MulticastListener.listen();
            		if(MusicRequesterActivity.PORT == -1)
            			return returnSongs;
            	}
            	
            	//Connect to server and create input and output streams
                Socket s = new Socket(MusicRequesterActivity.SERVER, 
                		MusicRequesterActivity.PORT);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in 
                	= new BufferedReader(new InputStreamReader(s.getInputStream()));
                
                //create request
                RequestJson request = new RequestJson(RequestType.PLAYLIST, 
                		MusicRequesterActivity.SOURCE, null, null, null);
                
                //create response and Gson object
                ResponseJson response;
                Gson gson = new Gson();
                
                //send request
                out.println(gson.toJson(request));
                
                //retrieve playing list
                response = gson.fromJson(in.readLine(), ResponseJson.class);
                
                //if there was no error and the response is of the correct type
                //read the list. Otherwise, throw an exception.
                if(response.getErrorMessage() == null && response.getType() == RequestType.PLAYLIST)
                	returnSongs.addAll(response.getSongs());
                else
                	throw new Exception(response.getErrorMessage());
                
                //close connection
                s.close();
                in.close();
                out.close();
                
            }catch (Exception e){
            	//Log exceptions
                Log.e(getLocalClassName(), "Playlist request send failed", e);
                returnSongs = null;
                
                //reset the port in case this is a connectivity problem
                MusicRequesterActivity.PORT = -1;
            }
            
            return returnSongs;
        }
       
        @Override
        protected void onPostExecute(List<SongJson> l) {
        	mSongs.clear();
        	
        	if(l == null)
        		return;
        	
        	mSongs.addAll(l);
        	getSongs();
        }
    }
    
    /**
     * SendRequest is an Asynchronous task that sends a song request to the server.
     * In takes the song id and time to play as input in an Integer array, there are no
     * progress units. There is no return.
     * @author Alicia Bendz
     *
     */
    private class SendRequest extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... ints) {
            	
        	try{
        		//get the server information if you don't have it
            	if(MusicRequesterActivity.PORT == -1){
            		MulticastListener.listen();
            		if(MusicRequesterActivity.PORT == -1)
            			return null;
            	}
            	
            	//Connect to server and create input and output streams
            	Socket s = new Socket(MusicRequesterActivity.SERVER, MusicRequesterActivity.PORT);
            	PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                
                //create request
                String playtime;
                if(ints[1] != null)
                	playtime = ints[1].intValue() + ":" + ints[2].intValue();
                else
                	playtime = null;
                
                RequestJson request = new RequestJson(RequestType.SONGREQUEST, 
                		MusicRequesterActivity.SOURCE, 
                		"" + ints[0], null, playtime);
                
                //create response and Gson object
                Gson gson = new Gson();
                
                //send request
                out.println(gson.toJson(request));
                
                //close connection
                s.close();
                out.close();
                
            }catch (Exception e){
            	//Log exceptions
                Log.e(getLocalClassName(), "Song request send failed", e);
                
                //reset the port in case this is a connectivity problem
                MusicRequesterActivity.PORT = -1;
            }
            
            return null;
        }
       
        @Override
        protected void onPostExecute(Void v) {
        	//refresh the playlist
        	new GetPlaylist().execute(new Void[]{null});
        }
    }
}
