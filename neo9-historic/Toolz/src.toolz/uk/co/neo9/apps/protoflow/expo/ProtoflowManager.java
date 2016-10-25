package uk.co.neo9.apps.protoflow.expo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.UtilsBaseObject;
import uk.co.neo9.utilities.file.FileServer;

public class ProtoflowManager extends UtilsBaseObject {
	
	public static void main(String[] args) {
		
		ProtoflowManager manager = new ProtoflowManager();
		
		manager.go();
	}


	/*
	 * Fields to replace are:
	 * 
	 * 	1) coordinates
	 *  2) alt text (target page name)
	 *  3) target page file name
	 * 
	 */
	private String imageMapLinkTemplate = "<area shape=\"rectangle\" coords=\"%s\" alt=\"%s\" href=\"%s\" />";

	final String testFolder = "C:\\Z-SwapZones\\jmcg-dropbox\\Dropbox\\dev\\test\\ProtoFlow\\mxGraph\\";
	
	private void go() {


		
		final String testFile = "PageData.xls";
		final String testFileDetails = testFolder + testFile;
		
		
		// read in the Page Details from the spreadsheet
		List<PageDetails> pageModels = readPageList(testFileDetails);
		//logList(pageModels);
		Map<String, PageDetails> pageModelMap = buildPageModelMap(pageModels);
		
		
		// read in the link Details from the spreadsheet
		List<NavigationLinkDetails> linkModels = readNavLinksList(testFileDetails);
		//logList(linkModels);
//		Map<String, NavigationLinkDetails> linkModelMap = buildLinkModelMap(linkModels, true);
		
		
		// put the links and the pages together into the composite
		List<PageNavigationCompositeModel> fullModels = buildCompositeModels(pageModelMap,linkModels);
		
		// read in the navigation map
		
		
		// apply the navigation logic to the page models
		
		
		// generate the HTML files for each page
		for (PageNavigationCompositeModel model : fullModels) {
			String html = applyModelToTemplate(model);
			saveHtmlPage(model, html);
		}
		
		
		
		return;
	}

	
	private String applyModelToTemplate(PageNavigationCompositeModel model) {

		// load the template
		String hmtlTemplateText = loadHtmlTemplate();
		
		/* Replace the individual fields
		 
			Fields to fill are:
			
				1) Title
				2) Source Page Image File Details
				3) The map links block... as a whole!
				
			The map links block consists of lines structured like:
				
				> <area shape="rectangle" coords="732,108,90,90" alt="Number 3" href="3.htm" />		 
		 
		 */


		StringBuilder mapLinksBuff = new StringBuilder();
		for (NavigationLinkPage link : model.getLinks()) {
			String coords = link.getCoordinates();
			String altText = link.getTargetPage().getPageTitle();
			String url = derivePageFileName(link.getTargetPage());
			String imageMapLinkText = String.format(imageMapLinkTemplate, coords, altText, url);
			mapLinksBuff.append(imageMapLinkText);
			// mapLinksBuff.append(System.lineSeparator());
			mapLinksBuff.append(CommonConstants.NEWLINE);
		}
		
		/* All the links are treated as a single replace in the template file
		 * 
			<area shape="circle" coords="257,125,83" alt="number 1" href="1.htm" />
			<area shape="circle" coords="508,175,100" alt="number 2" href="2.htm" />
			<area shape="circle" coords="732,108,90" alt="Number 3" href="3.htm" /> 
		 */
		String pageTitle = model.getPage().getPageTitle();
		String pageImage = model.getPage().getImageName();
		String html = String.format(hmtlTemplateText, pageTitle, pageImage, mapLinksBuff);
		
		return html;
	}

	private String derivePageFileName(PageDetails pageModel) {
		String pageFileName = pageModel.getPageId()+".htm";
		return pageFileName;
	}


	private String loadHtmlTemplate() {
		String templateFileName = "YF_PAGE_TEMPLATE.htm";
		String templateFileDetails = testFolder+templateFileName;
		
		String htmlTemplateText = null;
		try {
			htmlTemplateText = FileServer.readFullTextFile(templateFileDetails);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		
		return htmlTemplateText;
	}

	private void saveHtmlPage(PageNavigationCompositeModel model, String html) {

		System.out.println(html);
		
		String fileDetails = buildHTMLFileName(model);;
		try {
			FileServer.writeTextFile(fileDetails, html);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		
		
	}

	private String buildHTMLFileName(PageNavigationCompositeModel model) {
		StringBuilder buff = new StringBuilder(testFolder);
		buff.append(model.getPage().getPageId());
		buff.append(".htm");
		return buff.toString();
	}


	private List<PageNavigationCompositeModel> buildCompositeModels(Map<String, PageDetails> pageModelMap,
																	List<NavigationLinkDetails> linkModels) {
		
		
		List<PageNavigationCompositeModel> models = new ArrayList<PageNavigationCompositeModel>();
		
//		// need to start with the pages
//		Collection<PageDetails> pageModels = pageModelMap.values();
//		for (PageDetails pageDetails : pageModels) {
//			
//			// build a page link model first
//			NavigationLinkPage linkedPages = new NavigationLinkPage();
//			linkedPages.setSourcePage(pageDetails);
//			
//			// 
//			
//		}
		
		// first form an actual link between related page models
		List<NavigationLinkPage> linkedPageModels = new ArrayList<NavigationLinkPage>();
		for (NavigationLinkDetails navigationLinkDetails : linkModels) {
			
			NavigationLinkPage linkedPages = new NavigationLinkPage(navigationLinkDetails);
			linkedPages.setTargetPage(pageModelMap.get(linkedPages.getTargetPageId()));
			linkedPages.setSourcePage(pageModelMap.get(linkedPages.getSourcePageId()));
			linkedPageModels.add(linkedPages);
		}
		
		
		// now collect all the targets for each given source page
		Collection<PageDetails> sourcePages = pageModelMap.values();
		Map<String,PageNavigationCompositeModel> compositeModels = new HashMap<String, PageNavigationCompositeModel>();
		for (NavigationLinkPage linkedPages : linkedPageModels) {
			
			PageNavigationCompositeModel compositeModel = compositeModels.get(linkedPages.getSourcePageId());
			if (compositeModel ==  null) {
				compositeModel = new PageNavigationCompositeModel();
				compositeModel.setPage(linkedPages.getSourcePage());
				compositeModels.put(linkedPages.getSourcePageId(), compositeModel);
			}
			compositeModel.addLink(linkedPages); // add the link too!
			
			// also need to make sure the target is in the list
			PageDetails page = linkedPages.getTargetPage();
			compositeModel = compositeModels.get(page.getPageId());
			if (compositeModel ==  null) {
				compositeModel = new PageNavigationCompositeModel();
				compositeModel.setPage(page);
				compositeModels.put(page.getPageId(), compositeModel);
			}
		}
		
		
		// return as a list of the source pages, now all containing links to their target pages
		models.addAll(compositeModels.values());
		return models;
	}

	private Map<String, PageDetails> buildPageModelMap(
			List<PageDetails> pageModels) {
		Map<String, PageDetails> pageModelMap = new HashMap<String, PageDetails>();
		for (PageDetails pageDetails : pageModels) {
			pageModelMap.put(pageDetails.getPageId(), pageDetails);
		}
		return pageModelMap;
	}


//	private Map<String, NavigationLinkDetails> buildLinkModelMap(List<NavigationLinkDetails> models, boolean source) {
//		Map<String, NavigationLinkDetails> modelMap = new HashMap<String, NavigationLinkDetails>();
//		for (NavigationLinkDetails model : models) {
//			if (source) {
//				modelMap.put(model.getSourcePage(), model);
//			} else {
//				modelMap.put(model.getTargetPage(), model);
//			}
//		}
//		return modelMap;
//	}
	
	public <T> void logList(List<T> list) {
		for (Object object : list) {
			log(object.toString());
		}
	}
	
	
	
//	public void log(String message){
//		super.log(message);	
//	} 
	
	
	private List<PageDetails> readPageList(String testFileDetails) {
		// first load the excel spreadsheet
		
		Sheet sheet = loadExcelSheet(testFileDetails, 0);
		
		// read the sheet data into the models
		List<PageDetails> pageModels = extractPageModelsFromSheet(sheet);
		
		return pageModels;
	}
	

	
	private List<NavigationLinkDetails> readNavLinksList(String testFileDetails) {
		// first load the excel spreadsheet
		
		Sheet sheet = loadExcelSheet(testFileDetails, 1);
		
		// read the sheet data into the models
		List<NavigationLinkDetails> linkModels = extractLinkModelsFromSheet(sheet);
		
		return linkModels;
	}	
	
	
	protected List<PageDetails> extractPageModelsFromSheet(Sheet transactionsSheet){
		
		// this method simply turns the excel data into models 
		
		int noOfRows = transactionsSheet.getRows();
		
		List<PageDetails> models = new ArrayList<PageDetails>();
		boolean entriesExist = true;
		int rowIndex = 1;
		while (entriesExist) {
			
			int columnIndex = 0;
			Cell pageDetailsCell = transactionsSheet.getCell(columnIndex++,rowIndex); 
			
			if ((pageDetailsCell != null)) 
			{
				
				// get the contents as Strings
				String pageIdValue = pageDetailsCell == null ? null : pageDetailsCell.getContents(); 

				// transform the contents into models
				if (pageIdValue != null && pageIdValue.trim().length() > 0) {
					
					PageDetails model = new PageDetails();
					
					String pageTitleValue = getCellValueAsString(transactionsSheet.getCell(columnIndex++, rowIndex));
					String imageFileValue = getCellValueAsString(transactionsSheet.getCell(columnIndex++, rowIndex));
	
					model.setImageName(imageFileValue);
					model.setPageId(pageIdValue);
					model.setPageTitle(pageTitleValue);

					models.add(model);	
						
					
				} else {
					if (true){ // is logging on?
						log("Date value is missing - this must be the end of the data set - row index = "+rowIndex);
					}
					entriesExist = false;
				}
				
			}else {
				if (true){ // is logging on?
					log("Date cell is missing - this must be the end of the data set - row index = "+rowIndex);
				}
				entriesExist = false;
			}
			
			
			// increment count & do safety check
			rowIndex++;
			if (rowIndex>noOfRows-1){
				// log("ERROR - Stopped an infinite loop happening!!!");
				entriesExist = false;
			}
		}
		
		log("No of sortcodes = "+rowIndex);
		
		return models;
	}		


	protected List<NavigationLinkDetails> extractLinkModelsFromSheet(Sheet transactionsSheet){
		
		// this method simply turns the excel data into models 
		
		int noOfRows = transactionsSheet.getRows();
		
		List<NavigationLinkDetails> models = new ArrayList<NavigationLinkDetails>();
		boolean entriesExist = true;
		int rowIndex = 1;
		while (entriesExist) {
			
			int columnIndex = 0;
			Cell navDetailsCell = transactionsSheet.getCell(columnIndex++,rowIndex); 
			
			if ((navDetailsCell != null)) 
			{
				
				// get the contents as Strings
				String sourcePageIdValue = navDetailsCell == null ? null : navDetailsCell.getContents(); 

				// transform the contents into models
				if (sourcePageIdValue != null && sourcePageIdValue.trim().length() > 0) {
					
					NavigationLinkDetails model = new NavigationLinkDetails();
					
					String targetPageIdValue = getCellValueAsString(transactionsSheet.getCell(columnIndex++, rowIndex));
					String coordinatesValue = getCellValueAsString(transactionsSheet.getCell(columnIndex++, rowIndex));
	
					model.setCoordinates(coordinatesValue);
					model.setSourcePageId(sourcePageIdValue);
					model.setTargetPageId(targetPageIdValue);

					models.add(model);	
						
					
				} else {
					if (true){ // is logging on?
						log("Date value is missing - this must be the end of the data set - row index = "+rowIndex);
					}
					entriesExist = false;
				}
				
			}else {
				if (true){ // is logging on?
					log("Date cell is missing - this must be the end of the data set - row index = "+rowIndex);
				}
				entriesExist = false;
			}
			
			
			// increment count & do safety check
			rowIndex++;
			if (rowIndex>noOfRows-1){
				// log("ERROR - Stopped an infinite loop happening!!!");
				entriesExist = false;
			}
		}
		
		log("No of sortcodes = "+rowIndex);
		
		return models;
	}		

	
	protected Sheet loadExcelSheet(String lfilename, int sheetIndex){
		
		// String lfilename = Neo9TestingConstants.ROOT_TEST_FOLDER+"/WIPTracker/WIP Tracker Test 1.xls";
//		if (pFilename != null){
//			lfilename = pFilename;
//		}
		
		log("Loading spreadsheet: "+lfilename);
		
		// first load the spreadsheet
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(new File(lfilename));
		} catch (BiffException e) {
			log("Load failed - BiffException - exiting!");
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			log("Load failed - IOException - exiting!");
			e.printStackTrace();
			System.exit(-1);
		} 
		
		// now get the applicable worksheet and return it
		int noOfSheets = workbook.getNumberOfSheets();
		log("Number of sheets in workbook: " + noOfSheets);
		Sheet sheet = workbook.getSheet(sheetIndex); 
		return sheet;
	}
	
	
	private String getCellValueAsString(Cell cell) {
		String value = cell == null ? "" : cell.getContents(); 
		return value.trim();
	}
}
