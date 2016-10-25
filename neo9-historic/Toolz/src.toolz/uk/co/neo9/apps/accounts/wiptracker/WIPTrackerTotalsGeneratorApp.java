package uk.co.neo9.apps.accounts.wiptracker;

/**
 * How to run the app:
 * 
 * TEST - "WIP Tracker Test 5.xls" "output.csv" "C:\Test\WIPTracker" No
 * 
 * REAL - "WIP Tracker - 2010.01 - January 2010.xls" "output.csv" "Z:\Dropbox\Kathie and Simon\accounts\_ WIP\2010" No
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.UtilsBaseObject;
import uk.co.neo9.utilities.app.UtilitiesAppLaunchDetails;
import uk.co.neo9.utilities.app.UtilitiesAppLaunchDetailsContainer;
import uk.co.neo9.utilities.app.UtilitiesLaunchableApplicationI;
import uk.co.neo9.utilities.file.FileServer;

public class WIPTrackerTotalsGeneratorApp 
extends UtilsBaseObject
implements UtilitiesLaunchableApplicationI {
	
	
	protected WIPTrackerTotalsGeneratorComponent engine = new WIPTrackerTotalsGeneratorComponent();


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		WIPTrackerTotalsGeneratorApp app = new WIPTrackerTotalsGeneratorApp();
		app.go(args);

	}
	
	
	
	private boolean readMainArgs(String[] argsIN) {
		
		String argInstructions = getArgsInstructions();
		
		// first check there are some args
		if (argsIN == null || (argsIN.length != 3 && argsIN.length != 4)) {
			System.out.println(argInstructions);
			System.exit(1);
		}
		
		boolean lSuccess = true;
		engine.wipTrackerFileName = argsIN[0];
		engine.outputFileName = argsIN[1];
		engine.workingFolder = argsIN[2];
		
		
		if (engine.wipTrackerFileName == null || engine.outputFileName == null || engine.workingFolder == null){
			lSuccess = false;
		}

		if (argsIN.length > 3){
			if ("No".equalsIgnoreCase(argsIN[3])) {
				engine.checkedMode = false;
			} else {
				lSuccess = false;
			}
		}
		
		
		if (!lSuccess){
			System.out.println(argInstructions);
			System.exit(1);
		}
		

		
		return lSuccess;
	}	
	
	
	
	private String getArgsInstructions(){
		
		StringBuffer buf = new StringBuffer();
		
		buf.append("Please run with args being: ");
		buf.append(CommonConstants.NEWLINE);
		buf.append("	- WIPTrackerFilename");
		buf.append(CommonConstants.NEWLINE);
		buf.append("	- output file name");
		buf.append(CommonConstants.NEWLINE);
		buf.append("	- working folder");	
		buf.append(CommonConstants.NEWLINE);
		buf.append("	- checkedMode ('No' to disable, omitt to activate) ");	
		buf.append(CommonConstants.NEWLINE);
		buf.append("If nothing happens, check the console for hints on incomplete tracker data.");
		buf.append(CommonConstants.NEWLINE);
		
		return buf.toString();
	}
		
		
	
	
	protected void go(String[] mainArgs){

		// this makes it re-entrant when the naff launching framework caches the instance!
		// boolean useCheckedMode = true;
		boolean useCheckedMode = engine.checkedMode;
		engine.initialise(useCheckedMode);

		
		// get the command line details set .. this sets the engine parameters at the same time
		readMainArgs(mainArgs);	

		// load the the excel worksheet
//		String lfilename = Neo9TestingConstants.ROOT_TEST_FOLDER+"/WIPTracker/WIP Tracker Test 3.xls";
//		String lfilename = "D:/ZZ - Swap Zone/_ DropBoxes/jmcgDropBox/My Dropbox/Kathie and Simon/WIP/WIP Tracker - 2010.01 - January 2010.xls";
		String lfilename = engine.workingFolder+"/"+engine.wipTrackerFileName;
			

		// load the grid!!!
		WIPTrackerTotalsGrid grid = engine.loadWIPTotalsIntoGrid(lfilename);
		
		
	
		// write out the totals
		if (engine.validData) {
			String output = grid.outputGrid();
			List<String> lines = new ArrayList<String>();
			lines.add(output);
			writeCategoryTotalsToFile(lines);	
		//	writeCategoryTotalsToFile(csvOutputLines);	
		} else {
			if (isLoggingOn) {
				log("Didn't find any valid data so nothing to output!");
			}
		}
		
	}
	

	
	protected void writeCategoryTotalsToFile(List<String> csvOutputLines) {
		
//		String targetFolder = "C:/temp";
//		String filename = "WIPTrackerTotals.csv";
		
		String targetFolder = engine.workingFolder;
		String filename = engine.outputFileName;
		String fileDetails = targetFolder+"/"+filename;
		
		log("");
		log("Writing out results now to > "+fileDetails);
		
		Vector<String> vector = new Vector<String>();
		vector.addAll(csvOutputLines);
		try {
			FileServer.writeTextFile(fileDetails,vector);
		} catch (IOException e) {
			e.printStackTrace();
			log("Error - unable to write output file!");
			System.exit(-1);
		}
		
		log(".................................. done!");
	}	

	


	public void launch(String[] args) {
		this.go(args);
		
	}



	public UtilitiesAppLaunchDetailsContainer getLaunchDetails(String[] args) {
		
		// first, validate the command line args - if valid, we can pass them to the laucnher
		readMainArgs(args);
		
		UtilitiesAppLaunchDetailsContainer container = new UtilitiesAppLaunchDetailsContainer();
		UtilitiesAppLaunchDetails detail = null;

		container.setArgumentsHelpText(getArgsInstructions());
		
		List<UtilitiesAppLaunchDetails> argDetails = getArgDetails(args);
		for (Iterator iterator = argDetails.iterator(); iterator.hasNext();) {
			UtilitiesAppLaunchDetails argDetail = (UtilitiesAppLaunchDetails) iterator.next();
			container.addArgument(argDetail);
		}

		return container;
	}



	private List<UtilitiesAppLaunchDetails> getArgDetails(String[] args) {

		List<UtilitiesAppLaunchDetails> argDetails = new ArrayList<UtilitiesAppLaunchDetails>();

		UtilitiesAppLaunchDetails arg = new UtilitiesAppLaunchDetails();
		arg.setValue(engine.wipTrackerFileName);
		arg.setType(UtilitiesAppLaunchDetails.TYPE_FILENAME_ONLY);
		arg.setName("Tracker Filename (Input)");
		arg.setQuickLaunch(true);
		arg.setIndex(0);
		argDetails.add(arg);

		arg = new UtilitiesAppLaunchDetails();
		arg.setValue(engine.outputFileName);
		arg.setType(UtilitiesAppLaunchDetails.TYPE_FILENAME_ONLY);
		arg.setName("Output Text Filename");
		arg.setIndex(1);
		argDetails.add(arg);

		arg = new UtilitiesAppLaunchDetails();
		arg.setValue(engine.workingFolder);
		arg.setType(UtilitiesAppLaunchDetails.TYPE_FOLDER);
		arg.setName("Working Folder");
		arg.setQuickLaunch(true);
		arg.setIndex(2);
		argDetails.add(arg);
		
		return argDetails;
	}
	
	
}
