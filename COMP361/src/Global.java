
import java.io.IOException;

import DataAccess.ServicePool;
import Settings.Localization;


/**
 * Access to the global stuff. This is a singleton; a global should be instantiated only once.
 * @author Rebecca
 *
 */
public class Global {
	/**
	 * Forced a singleton by having private variables. This way we catch the exception
	 * only once, here.
	 */
	
	/* String library */
	private static Localization LOC;
	/* Access to the db */
	private static ServicePool SRV;
	
	/**
	 * Instantiates.
	 * @throws IOException 
	 */
	private Global() throws IOException {
		LOC = new Localization("EN"); // default english
		SRV = new ServicePool();
	}
	
	/**
	 * Use this to get Localization.
	 * @return Localization
	 */
	public static Localization getLOCInstance() {
		if(LOC == null) {
			try {
				LOC = new Localization("EN");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return LOC;
	}
	
	/**
	 * Use this to get ServicePool
	 * @return ServicePool
	 */
	public static ServicePool getSRVInstance() {
		if(SRV == null) {
			SRV = new ServicePool();
		}
		return SRV;
	}
	
}
