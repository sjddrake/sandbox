package uk.co.neo9.apps.accounts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import uk.co.neo9.utilities.UtilitiesTextHelper;
import uk.co.neo9.utilities.file.FileServer;


/*
 * Created on 06-May-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author simon.j.d.drake
 *
 * = = = = = = = = USING THE APPLICATOIN = = = = = = = = = = = =
 * 
 * Batch Mode:
 * 
 * > In this mode, the input is a text file containing a list of files to process
 * > the list is WITHOUT a path on them & the files must be in the working directory
 * > This application WILL ONLY PROCESS a single input file type, usually "Barclays Screen Scrape"
 * > Use the credit card sub-class version to process credit-card ones
 * [later enhancement could be to intelligently identify each one & enable changing type per file]
 */
public class AccountsDataReader {
	
	public static final String MODE_SINGLE_FILE = "SINGLEMODE";
	public static final String MODE_BATCH = "BATCHMODE";
	
	private boolean batchMode = false;
	
	private String workingFolder = null;
	private String accountsDataFileName = null;
	private String processedAccountsFileName = null; 
	private String accountsLookupFileName = null; 
	private int inputFileType = AccountsDataModel.IFT_BARCLAYS_SCREEN_SCRAPE;
	private String defaultYear = null;
	private int focusAccountId;

	private boolean useExtendedFields = false;
	private boolean overrideBatchOutputFileNaming = false;
	
	public static void main(String[] args) {
		
		AccountsDataReader a = new AccountsDataReader();
		
		// this is NAFF too!
		String chequeFileDetails = "D:/ZZ - Swap Zone/_ DropBoxes/jmcgDropBox/My Dropbox/Kathie and Simon/accounts/_ WIP/cheques.xls";
		ChequeDetailsLookup.setAccountsLookupFileName(chequeFileDetails);
		a.useExtendedFields = true;
		
		// and this!!!!!
		a.overrideBatchOutputFileNaming = true; // if this is false, take the file names from the input files, else hardcode
		
		// GET RID OF THIS LINE!
		//TODO setup a proper way to do all this!!!  a.inputFileType = AccountsDataModel.IFT_CC_SCREEN_SCRAPE;
		a.go(args);
		
	}
	
	public void go(String[] mainArgs){
		
		readMainArgs(mainArgs);

		AccountDetailsLookup.loadAccountsLookup(accountsLookupFileName);
		
		if (batchMode) {
			processBatch();
		} else {
			processSingleFile();
		}
		
	}

	
	@SuppressWarnings("unchecked")
	private void processBatch(){

		System.out.println("processing batch...");
		
		// first read in the list of files to process
		String batchFileName = workingFolder+"/"+accountsDataFileName;
		// read in the file as lines
		List<String> lInputFileNames = null;
		try {
			lInputFileNames = FileServer.readTextFile(batchFileName); //"C:/DEV_BIN/test/accounts data.txt");
		} catch (IOException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// now for each one, do the processing
		for (Iterator<String> iter = lInputFileNames.iterator(); iter.hasNext();) {
			
			// process each input file in turn
			String lInputFileName = (String) iter.next();
			if (lInputFileName == null || lInputFileName.trim().length() == 0) {
				continue;
			}
			
			// setup the output file name
			defaultYear = generateDefaultYear(lInputFileName);
			String outputFileNameSeed = null;
			if (overrideBatchOutputFileNaming) {
				outputFileNameSeed = generateOutputFileNameSeed(lInputFileName); // extract the month but then use a hard code seed
			} else {
				outputFileNameSeed = lInputFileName; // use the input file name as it is
			}
			setupOutputFileDetails(outputFileNameSeed, true);
			
			// now process the file
			String lInputFileDetails = workingFolder+"/"+lInputFileName;
			processInputFile(lInputFileDetails);
			
		}
		
		
	}
	
	
	/**
	 * This provides a 'standard' output filename seed with only the 
	 * MONTH name being dynamic based upon the input file name. THis
	 * will only work IF the input file name is SPACE delimited. It
	 * will recognise FULL month names and the 3 letter abbreviation
	 * 
	 * @param lInputFileName
	 * @return
	 */
	private String generateOutputFileNameSeed(String lInputFileName) {
		StringBuffer buff = new StringBuffer();
		
		// find the month if we can
		boolean fullName = true;
		String monthName = UtilitiesTextHelper.extractMonthNameFromText(lInputFileName, fullName);
		if (monthName == null) {
			// no luck - just return what we have
			return lInputFileName;
		}
		
		// success - so build up the rest
		boolean isSimonz = lInputFileName.contains("imon") || lInputFileName.contains("IMON");
		boolean isJoint = lInputFileName.contains("oint") || lInputFileName.contains("OINT");
		
		// default to joint if we've found neither
		if (isJoint == false && isSimonz == false) {
			isJoint = true;
		}
		
		buff.append("processed ");
		if (isJoint) {
			buff.append("Joint Account ");
		}
		if (isSimonz) {
			buff.append("Simonz Account ");
		}
		
		buff.append(monthName);
		buff.append(" ");
		buff.append(defaultYear);
		
		return buff.toString();
	}

	private void processSingleFile(){
	
		// we have the output filename explicitly so use it!
		setupOutputFileDetails(processedAccountsFileName,false);
		
		// and pass in the input name
		String lInputFileDetails = workingFolder+"/"+accountsDataFileName;
		processInputFile(lInputFileDetails);
		
	}

	
	
	public void processCommand(CreditCardBFPBaseCommand command){
	
		useExtendedFields = command.getUseExtendedFieldsInTracker();
		workingFolder = command.getTargetFolder();
		inputFileType = command.getInputFileType();
		String lInputFileName = command.getInputFileName();
		defaultYear = generateDefaultYear(lInputFileName);
		if (command instanceof BarclaysCSVExportCommand) {
			focusAccountId = ((BarclaysCSVExportCommand) command).getFocusAccountId();
		}
		setupOutputFileDetails(lInputFileName,true);
		
		// the input file details are passed in directly
		String lInputFileDetails = workingFolder+"/"+lInputFileName;
		processInputFile(lInputFileDetails);
		
	}
	
	/**
	 * This primes the 'glabal variables' that holds parameters on how to save the output files
	 * 
	 * @param seedFileName
	 * @param generateFromSeed
	 */
	private void setupOutputFileDetails(String seedFileName, boolean generateFromSeed){
		
		// ----- for statements --------
		String statementParamId = OutputParameterModel.getStatementParamId();
		OutputParameterModel statement = OutputParameterModel.getParams(statementParamId);
		String lOutputFileName = null;
		if (generateFromSeed) {
			lOutputFileName = generateOutputFileName(seedFileName,"processed_");
		} else {
			lOutputFileName = seedFileName;
		}
		String lOutputFileDetails = workingFolder+"/"+lOutputFileName;
		statement.setOutputFileName(lOutputFileDetails);
		
		
		// ----- for TRACKERS - ORIGINAL --------
		String trackerParamId = OutputParameterModel.getTrackerParamId();
		OutputParameterModel tracker = OutputParameterModel.getParams(trackerParamId);
		if (generateFromSeed) {
			lOutputFileName = generateOutputFileName(seedFileName,"TRAK_");
		} else {
			lOutputFileName = "TRAK_"+seedFileName;
		}
		lOutputFileDetails = workingFolder+"/"+lOutputFileName;
		tracker.setOutputFileName(lOutputFileDetails);		
		
		
		// ----- for TRACKERS - EXTENDED --------
		String trackerExtParamId = OutputParameterModel.getTrackerExtendedParamId();
		OutputParameterModel trackerExt = OutputParameterModel.getParams(trackerExtParamId);
		if (generateFromSeed) {
			lOutputFileName = generateOutputFileName(seedFileName,"TRAK_");
		} else {
			lOutputFileName = "TRAK_"+seedFileName;
		}
		lOutputFileDetails = workingFolder+"/"+lOutputFileName;
		trackerExt.setOutputFileName(lOutputFileDetails);	
		
	}
	
	
	
	private String generateDefaultYear(String text) {
		String year = null;
		
		StringTokenizer s = new StringTokenizer(text," .",false);
		String lToken = null;
		while (s.hasMoreTokens()) {
			lToken = s.nextToken();			
			lToken = lToken.trim();
			
			if (lToken.indexOf("20") == 0){ // assumes we wont process older!!!
				year = lToken;
				break;
			}
		}	
		
		return year;
	}

	private void processInputFile(String pInputFileDetails){
		
		// read in the file as lines
		List lLines = null;
		try {
			lLines = FileServer.readTextFile(pInputFileDetails); //"C:/DEV_BIN/test/accounts data.txt");
		} catch (IOException e) {
			//  Auto-generated catch block
			System.out.println("failed to open file: "+pInputFileDetails);
			e.printStackTrace();
		}
		
		// look for line
		//final String START_LINE = "Transaction Money Out Money In Balance";
		// ==> above constant is no longer true!!!!
		int i = 0;
		for (int j = 0; j < lLines.size(); j++) {
			String lLine = (String) lLines.get(j);
			//if (lLine.trim().equalsIgnoreCase(START_LINE)){
			if (testFirstLine(lLine)){
				i = j+1; 
				break; 
			}
		}
		
		// chop lines up by empty line and load into a model
		List accountsDataList = new ArrayList();
		AccountsDataModel a = getDataModelInstance();
		for (int j = i; j < lLines.size(); j++) {
			String lLine = (String) lLines.get(j);
			a.addDataLine(lLine.trim());
			if (testEndOfDataBlock(lLine)){
				// come to the end of a block of data
				if (a.isDefined()) {
					accountsDataList.add(a);
				}
				a = getDataModelInstance();
			}
		}		
		
		
		// sort the data
		sortAccountsData(accountsDataList);
		
		
		// get models to interogate the data and write it out to file
		// - first for the statements format
		String statementParamId = OutputParameterModel.getStatementParamId();
		OutputParameterModel model = OutputParameterModel.getParams(statementParamId);
		processAndOutputModels(model.getOutputCSVHeader(),accountsDataList,model);
		
		// - then the tracker one
		String trackerParamId = null;
		if (useExtendedFields) {
			trackerParamId = OutputParameterModel.getTrackerExtendedParamId();
		} else {
			trackerParamId = OutputParameterModel.getTrackerParamId();
		}
		model = OutputParameterModel.getParams(trackerParamId);
		processAndOutputModels(model.getOutputCSVHeader(),accountsDataList,model);
		
		
	}

	
	
	private void processAndOutputModels(String csvHeader, List accountsDataList, OutputParameterModel model){
		
		// get models to interogate the data
		List lProcessedData = new ArrayList();
		lProcessedData.add(csvHeader); //prime the list with the header
		try {
			for (Iterator iter = accountsDataList.iterator(); iter.hasNext();) {
				AccountsDataModel element = (AccountsDataModel) iter.next();
				String output = element.output(model);
				if (output != null && output.trim().length() > 0) {
					lProcessedData.add(output);
				}
			}			
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println("an error occured reading the data file!");
		}

		
		
		// now output the data
		for (Iterator iter = lProcessedData.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			System.out.println(element);
		}
		
		
		// and save it to file
		// String lFileDetails = "c:/simonz/swap zone/accounts/processedAccounts.csv";
		String lFileDetails = model.getOutputFileName();
		try {		
			FileServer.writeTextFile(lFileDetails,lProcessedData);
			
		} catch (Exception e) {
			System.err.println("Unable to write out file: "+lFileDetails);
			e.printStackTrace();
		}
		
	}


	private void sortAccountsData(List accountsDataList) {
		
		if (inputFileType == AccountsDataModel.IFT_BARCLAYS_CSV_EXPORT ) {
			Collections.sort(accountsDataList, new AccountsDataModelComparator());
			
		} else if (inputFileType != AccountsDataModel.IFT_BARCLAYS_SCREEN_SCRAPE	) {
			Collections.sort(accountsDataList, new AccountsDataModelComparator());
		} 
	}

	private AccountsDataModel getDataModelInstance() {
		
		AccountsDataModel model = null;
		
		switch (this.inputFileType) {
			case AccountsDataModel.IFT_BARCLAYS_SCREEN_SCRAPE:
				model = new AccountsDataBarclays();	break;
				
			case AccountsDataModel.IFT_CC_SCREEN_SCRAPE:
				model = new AccountsDataTescoScreenScrape(); break;

			case AccountsDataModel.IFT_CC_ADOBE_SCRAPE:
				model = new AccountsDataTescoAdobeScrape();	break;
				
			case AccountsDataModel.IFT_CC_CHROME_SCRAPE:
				model = new AccountsDataTescoChromeScrape();	break;
				
			case AccountsDataModel.IFT_CC_CSV_EXPORT:
				model = new AccountsDataTescoCSVExport();	break;

			case AccountsDataModel.IFT_CASH_SCRAPBOOK:
				model = new AccountsDataCash();	break;				
				
			case AccountsDataModel.IFT_BARCLAYS_CSV_EXPORT:
				AccountsDataBarclaysCSVExport modelSubType;
				modelSubType = new AccountsDataBarclaysCSVExport();
				modelSubType.setFocusAccountId(getFocusAccountId());
				model = modelSubType;	break;
				
			default:
				throw new IllegalArgumentException("Haven't defined the model for the inputFileType: "+this.inputFileType);
		}
		
		if (model != null){
			model.setDefaultYear(defaultYear);
		}
		
		return model;
	}




	
	protected boolean readMainArgs(String[] argsIN) {
		
		// first check there are some args
		if (argsIN == null || argsIN.length == 0) {
			displayArgsInstructions();
			System.exit(1);
		}
		
		// now look for the optional mode and 
		// add in a default if its not there
		String modeParam = argsIN[0];
		String[] args = argsIN;
		if (MODE_SINGLE_FILE.equalsIgnoreCase(modeParam)) {
			batchMode = false;
		} else if (MODE_BATCH.equalsIgnoreCase(modeParam)) {
			batchMode = true;
		} else {
			// default to single mode and default in the mdoe string
			batchMode = false;
			int araySize = argsIN.length + 1; 
			args = new String[araySize];
			args[0] = MODE_SINGLE_FILE;
			for (int i = 0; i < argsIN.length; i++) {
				String arg = argsIN[i];
				args[i+1] = arg;
			}
			
		}
		
		
		
		boolean lSuccess = true;
		
		if (args.length > 3){
			
			// set the client server mode from the first one
			this.accountsDataFileName = args[1];
			this.processedAccountsFileName = args[2];
			this.workingFolder = args[3];
			
			
			if (accountsDataFileName == null || processedAccountsFileName == null || workingFolder == null){
				lSuccess = false;
			}
			 
			
		} else {
			
			lSuccess = false;
		}
		if (args.length > 4){
				
			accountsLookupFileName = args[4]; // this one is optional
		
		}
		
		if (!lSuccess){
			displayArgsInstructions();
			System.exit(1);
		}
		
		return lSuccess;
	}	
	
	protected void displayArgsInstructions(){
		System.out.println("Please run with args being: ");
		System.out.println("	- mode: SINGLEMODE or BATCHMODE [OPTIONAL - defaults to SINGLEMODE]");
		System.out.println("	- accounts data file name");
		System.out.println("	- output file name");
		System.out.println("	- working folder");
		System.out.println("	- accounts lookup data [OPTIONAL]");		
	}
	
	
	private boolean testFirstLine(String lLine){
		boolean isFirstLine = false;
		
		switch (this.inputFileType) {
			case AccountsDataModel.IFT_BARCLAYS_SCREEN_SCRAPE:
				final String BARCLAYS_FIRST_LINE = "TransactionMoneyOutMoneyInBalance";
				isFirstLine = testFirstLineByType(lLine,BARCLAYS_FIRST_LINE);	break;
				
			case AccountsDataModel.IFT_CC_SCREEN_SCRAPE:
				final String CC_SCREEN_FIRST_LINE = "TransactiondateTransactiondescriptionDebitsCredits";
				isFirstLine = testFirstLineByType(lLine,CC_SCREEN_FIRST_LINE);	break;

			case AccountsDataModel.IFT_CC_ADOBE_SCRAPE:
				final String CC_ADOBE_FIRST_LINE = "TransactionDateDescriptionAmount";
				isFirstLine = testFirstLineByType(lLine,CC_ADOBE_FIRST_LINE);	break;

			case AccountsDataModel.IFT_CC_CHROME_SCRAPE:
				final String CC_CHROME_FIRST_LINE = "TransDatePostDateDescriptionAmount";
				isFirstLine = testFirstLineByType(lLine,CC_CHROME_FIRST_LINE);	break;		
		}
		
		return isFirstLine;
	}
	
	

	private boolean testFirstLineByType(String lLine, String marker){
	
		boolean isFirstLine = false;
		
		StringTokenizer s = new StringTokenizer(lLine," ",false);
		String lToken = null;
		StringBuffer lTestString = new StringBuffer();
		// ArrayList lTokens = new ArrayList();
		while (s.hasMoreTokens()) {
			lToken = s.nextToken();
			
			// they've gone and added tabs in now so strip 'em out here
			lToken = lToken.trim();
			
			lTestString.append(lToken);
		}		
		
		if (lTestString.toString().equalsIgnoreCase(marker)){
			 isFirstLine = true; 
		}
		
		return isFirstLine;
	}


	
	private boolean testEndOfDataBlock(String lLine){
		boolean endOfBlock = false;
		
		if (this.inputFileType == AccountsDataModel.IFT_BARCLAYS_SCREEN_SCRAPE) {
			if (lLine.trim().length() == 0) {
				endOfBlock = true;
			}
					
		} else {
			endOfBlock = true;
		}
		
		return endOfBlock;
	}


//	private List readTextFile(String fileDetails) throws IOException
//	{
//
//	  // open a file reader
//	  FileReader fr = null;
//	  fr  = new FileReader(fileDetails);
//
//
//	  // initialise the vector to hold the strings
//	  Vector fileLines = new Vector();
//	  int nextString = 0;
//
//
//	  // this loop reads the whole file
//	  StringBuffer line = null;
//	  boolean endOfFile = false;
//	  while (endOfFile == false) 
//	  {
//		/*
//		// <debug>
//		System.out.print(nextString);
//		System.out.print(' ');
//		// </debug>
//		*/
//
//		// this loop reads in a line
//		line = new StringBuffer();
//		boolean endOfLine = false;
//		while (endOfLine == false) 
//		{
//
//		  // read some bytes till we reach the end of the line
//		  // or end of the file completely
//		  int i = fr.read();
//
//
//		  // process the character we've just read in
//		  if (i == -1) 
//		  {
//			  // reached the end of the file so the final line 
//			  // is in the buffer
//			  endOfFile = true;
//			  fileLines.addElement(line.toString());
//			  endOfLine = true;
//		   }
//
//		   else if (i == '\n') 
//		   {
//
//			  // reached the end of the line so add the previously read bytes
//			  // as a line into the array of strings
//			  fileLines.addElement(line.toString());
//			  endOfLine = true;
//			  nextString += 1;
//
//		   }
//
//		   else
//		   {
//			  // add the byte that's been read into the string buffer
//			  line.append((char)i);
//
//		   } // end of processing the character that's read
//
//
//		} // end of line loop
//
//
//	  /*
//	  // <debug>
//	  System.out.print(line);
//	  System.out.print('\n');
//	  System.out.flush();
//	  // </debug>
//	  */
//
//
//	  } // end of file loop
//
//
//	  // close the reader
//	  fr.close();
//
//
//	  return fileLines; 
//
//	}




	private String generateOutputFileName(String inputFileName, String suffix) {

		String lOutputFileName =  FileServer.extractFileNameOnly(inputFileName);
		if (suffix != null){
			// lOutputFileName = lOutputFileName + suffix;
			lOutputFileName = suffix + lOutputFileName;
		}
		lOutputFileName = lOutputFileName + ".csv";
		return lOutputFileName;
	}

	/**
	 * @return the inputFileType
	 */
	public int getInputFileType() {
		return inputFileType;
	}

	/**
	 * @param inputFileType the inputFileType to set
	 */
	public void setInputFileType(int inputFileType) {
		this.inputFileType = inputFileType;
	}
	
	public void setFocusAccountId(int focusAccountId) {
		this.focusAccountId = focusAccountId;
	}

	public int getFocusAccountId() {
		return focusAccountId;
	}
	

}
