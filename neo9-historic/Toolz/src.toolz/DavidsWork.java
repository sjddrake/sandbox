import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;


public class DavidsWork {

	
	private Map<String, Long> fileTimestamps = new HashMap<String, Long>();
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DavidsWork app = new DavidsWork();
		app.go();
		System.out.println("-------------");
		app.go();
		System.out.println("-------------");
		app.go();
	}
	
	public void go(){
		
		final String testRoot = "D:/Test/";
		String fileDetails = testRoot+"fileserver/"+"testTextFile.txt";
		
		File file = getFile(fileDetails);
		
		// get the time
		long currentTimestamp = file.lastModified();
		
		System.out.println("modified = "+currentTimestamp);
		
		// find the previous timestamp
		Long previousTimestamp = fileTimestamps.get(fileDetails);
		
		
		boolean hasChanged = false;
		if (previousTimestamp != null) {
			if (previousTimestamp != currentTimestamp) {
				hasChanged = true;
				fileTimestamps.put(fileDetails,new Long(currentTimestamp));
			}
		} else {
			hasChanged = true;
			fileTimestamps.put(fileDetails,new Long(currentTimestamp));
		}
		
		System.out.println("hasChanged = "+hasChanged);
	}

	
	  public static File getFile( String fileDetails ) {
		   
		  File file  = new File(fileDetails);
		  return file;

		  }
}
