package com.me.android;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Main Activity for Application. This activity contains each of the tabs for
 * playlist, feedback, and currently playing.
 * @author Alicia Bendz
 *
 */
public class MusicRequesterActivity extends TabActivity {
	/**
	 * Port, server, and source global variables.
	 */
	public static String SERVER = null;
	public static int PORT = -1;
	public static final String SOURCE = "ANDROID";
	private static Context mContext;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	//code according to specified android developer patterns
        super.onCreate(savedInstanceState);
        //set the correct xml layout
        setContentView(R.layout.main);
        
        mContext = getApplicationContext();
        
        //get resources
        Resources res = getResources();
        
        //the activity TabHost
        TabHost tabHost = getTabHost();
        
        //Reusable TabSpec for each tab
        TabHost.TabSpec spec;
        
        //Reusable Intent for each tab
        Intent intent;

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, PlayingTab.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("playing").setIndicator(res.getString(R.string.playingTitle),
        		res.getDrawable(R.drawable.ic_menu_music))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, PlaylistTab.class);
        spec = tabHost.newTabSpec("playlist").setIndicator(res.getString(R.string.playlistTitle),
        		res.getDrawable(R.drawable.ic_menu_database))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, FeedbackTab.class);
        spec = tabHost.newTabSpec("feedback").setIndicator(res.getString(R.string.feedbackTitle),
        		res.getDrawable(R.drawable.ic_menu_happy))
                      .setContent(intent);
        tabHost.addTab(spec);

        //set to start at playing tab
        tabHost.setCurrentTab(0);
    }
    
    public static Context getAppContext(){
    	return mContext;
    }
}