import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class FileChangeChecker {

	// For the static use of this utility class, all files 'checked' will
	// have an entry in the fileTimestamps map to cache the timestamp for
	// comparison on subsquent checks
	private static Map<String, Long> fileTimestamps = new HashMap<String, Long>();

	
	// the Checker can be used as an instance variable to check a single file
	private String fileDetailsToCheck = null;
	private Long fileTimeStamp = null;
	
	// the Checker needs initialising so hide this constructor to enforce this
	private FileChangeChecker() {
		super();
	}
	
	// When the Checker is used as an instance variable, it is pre-loaded
	// with the details of the file to be checked.
	public FileChangeChecker(String fileDetails) {
		super();
		fileDetailsToCheck = fileDetails;
	}
	
	
	// This method is used when the Checker is used as an instance variable.
	// In this mode, the Checker is dedicated to one file, the details of 
	// which are given in its constructor.
	public boolean checkFileChanged() throws FileChangeCheckerException{
		
		validateFileDetails(fileDetailsToCheck);
		  
		Long currentTimestamp = getFileTimestamp(fileDetailsToCheck);
		  
		Long previousTimestamp = fileTimeStamp;
		
		boolean hasChanged = false;
		if (previousTimestamp != null) {
			if (previousTimestamp.compareTo(currentTimestamp) != 0) {
				hasChanged = true;
				fileTimeStamp = new Long(currentTimestamp);
			}
		} else {
			// if there's no previous stamp cached then the file hasn't been touched before
			// - now is a good time to check the file details point to a real file
			fileTimeStamp = new Long(currentTimestamp);
		}
		  
		return hasChanged;
			
	}
	
	
	  private static long getFileTimestamp(String fileDetails) throws FileChangeCheckerException {
		  
		  // get a handle to the file and check it really exists
		  File file  = new File(fileDetails);
		  boolean exists = file.exists();
		  
		  if (exists == false) {
			throw new FileChangeCheckerException("Unable to find file: "+fileDetails);
		  }
		  
		  // if we get here, it exists - get the time stamp
		  long currentTimestamp = file.lastModified();	  
		  return currentTimestamp;

	  }

	
	  public static boolean checkFileChanged(String fileDetails) throws FileChangeCheckerException{
		  
		  validateFileDetails(fileDetails);
		  
		Long currentTimestamp = getFileTimestamp(fileDetails);
		  
		Long previousTimestamp = fileTimestamps.get(fileDetails);
		
		boolean hasChanged = false;
		if (previousTimestamp != null) {
			if (previousTimestamp.compareTo(currentTimestamp) != 0) {
				hasChanged = true;
				fileTimestamps.put(fileDetails,new Long(currentTimestamp));
			}
		} else {
			fileTimestamps.put(fileDetails,new Long(currentTimestamp));
		}
		  
		return hasChanged;
		
	  }
	  
	  
	  
	  private static void testFileDetails(String fileDetails) throws FileChangeCheckerException
	  {
		
			// open a file reader
			FileReader fr = null;
			try {
				fr  = new FileReader(fileDetails);
			} catch (IOException e) {
				FileChangeCheckerException fcce = null;
				fcce = new FileChangeCheckerException("Unable to open file: "+fileDetails);
				throw fcce;
			} 
		  
	  }
	  
	  
	  private static void validateFileDetails(String fileDetails) throws FileChangeCheckerException
	  {
		  
	    FileChangeCheckerException fccEx = null;
	    if (fileDetails == null) {
	    	fccEx = new FileChangeCheckerException("File details given for checking are null.");
		} else if (fileDetails.trim().length() == 0) {
			fccEx = new FileChangeCheckerException("File details given for checking are empty.");
		}
	    
	    if (fccEx != null) {
		  throw fccEx;
	    }
		  
	  }
	  
}
