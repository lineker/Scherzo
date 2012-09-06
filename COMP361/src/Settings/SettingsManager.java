package Settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Abstract class to manage system and user settings
 * @author lineker
 *
 */
public class SettingsManager {
	
	/** Private Properties reference **/
	Properties configFile = null;
	/** isLoaded flag **/
	public boolean isLoaded = false;
	
	/**
	 * Default constructor
	 * @throws IOException
	 */
	public SettingsManager() throws IOException
	{
		configFile = new Properties();
		this.load();
	}
	
	/**
	 * Load settings file
	 * @return Properties object
	 * @throws IOException
	 */
	public Properties load() throws IOException
	{
		if(configFile == null)
			System.out.println("file null");
		configFile.load(new FileInputStream("settings.properties"));
		this.isLoaded = true;
		return configFile;
	}
	
	/**
	 * Get a property value by key
	 * @param key
	 * @return value string 
	 * @throws Exception
	 */
	public String getPropertyForKey(String key) throws Exception
	{
		String retVal = "";
		
		if(key == null || key == "")
			throw new Exception("key is not valid");
		
		if(!isLoaded)
			this.load();
		
		if(configFile != null && configFile.containsKey(key))
		{
			retVal = configFile.getProperty(key, "");
		}
		
		if(retVal == "")
			throw new Exception("Property not found");
		
		return retVal;
	}
	
	/**
	 * Set a property value
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void setPropertyForKey(String key, String value) throws Exception
	{
		if(key == null || key == "")
			throw new Exception("key is not valid");
		
		if(!isLoaded)
			this.load();
		
		configFile.setProperty(key, value);
		//configFile.s
	}
}
