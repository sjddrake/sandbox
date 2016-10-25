package uk.co.neo9.apps.accounts.wiptracker;

import java.util.ArrayList;
import java.util.List;

import uk.co.neo9.test.Neo9TestingConstants;

public class WIPTrackerAnalyserApp {

	
	private WIPTrackerAnalyserComponent engine = new WIPTrackerAnalyserComponent();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		WIPTrackerAnalyserApp analyser = new WIPTrackerAnalyserApp();
		analyser.go(args);

	}

	
	protected void go(String[] mainArgs){

		engine.go(mainArgs);
		
	}		
	


	
	private List<String> getTestTrackerFilenames()  {
		
		List<String> filenames = new ArrayList<String>();
		
		String workingFolder = Neo9TestingConstants.ROOT_TEST_FOLDER+"/WIPTracker/";

		filenames.add(workingFolder+"WIP Tracker Test 1.xls");
		filenames.add(workingFolder+"WIP Tracker Test 3.xls");
		filenames.add(workingFolder+"WIP Tracker Test 5.xls");
		return filenames;
	}
	
}
