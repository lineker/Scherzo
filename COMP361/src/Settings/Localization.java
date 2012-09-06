package Settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Abstract class to acess localized text
 * @author lineker
 *
 */
public class Localization {
	/** Private Properties reference **/
	Properties configFile = null;
	/** isLoaded flag **/
	public boolean isLoaded = false;
	/** Current Language. Default EN **/
	public String CurrentLanguage = "EN";
	
	/**
	 * Default constructor
	 * @throws IOException
	 */
	public Localization() throws IOException
	{
		this("EN");
	}
	
	/**
	 * Constructor where you can specify the current language.
	 * @param language
	 * @throws IOException
	 */
	public Localization(String language) throws IOException
	{
		CurrentLanguage = language;
		configFile = new Properties();
		this.load();
	}
	
	/**
	 * Load localization file
	 * @return Properties object
	 * @throws IOException
	 */
	public Properties load() throws IOException
	{
		if(configFile == null)
			System.out.println("file null");
		configFile.load(new FileInputStream("localization"+CurrentLanguage+".properties"));
		this.isLoaded = true;
		return configFile;
	}
	
	/**
	 * Get localized string
	 * @param key
	 * @return String
	 * @throws Exception
	 */
	public String getLocalizedString(String key)
	{
		String retVal = "";
		
		try
		{
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
		}
		catch(Exception ex)
		{
			//we just return empty if something wrong happens
		}
		
		return retVal;
	}
	
}
