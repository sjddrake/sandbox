package uk.co.neo9.utilities.xml;

/**
	
	====================== STILL TO DO ======================
	
	The simple & complex types can be used to successfuly read a whole XML doc
	without any custom buddies. Somehow get this capability to output instructions
	on just what needs to be customised. Then, after the handy instructions have 
	been sussed, do some code generation as the next step!!!!

*/


/**

	==================== How To Use The XML Buddy System ================
	
	PRE-REQUISITES
	
	The business objects that are built up from the XML document using the
	neo9 XML system must implement the interface IXMLDataObject.
	

	INSTRUCTIONS

	* Converting an XML document into a business object relationship 

	 1) Setup a tester
	 
	 The neo9 XML system will read in well formed XML from the word go. To
	 transform the XML into business objects, domain specific buddy classes
	 need to be created with key callback methods overriden. To aid this 
	 process, begin with creating a tester to read in a good example of the
	 XML document. The neo 9 XML system will then output hints on how to
	 implement the transformation solution and get the XML data into the
	 business object domain. 
	 
	 - create a test class that loads in a suitable example XML document and
	   passes it to the XMLReader with the default buddy factory:
	   
	   //****************** TESTER *************************
	   
		final String lFileName = "Paul - 08-04-2005.xml";
		final String lFolder = "D:/dev/neo9/timekeeper/test";
		
		StringBuffer lFileDetails = new StringBuffer();
		lFileDetails.append(lFolder);
		lFileDetails.append("/");
		lFileDetails.append(lFileName);
		
		Document dom = null;
		dom = FileServer.loadXMLDocumentFromFile(lFileDetails.toString());
		
		if (dom==null) {
			System.out.println("Dom didn't load! -> " + lFileDetails);
			return;
		}		
		
		IXMLBuddyFactory buddyFactory = new XMLSimpleBuddyFactory();
		UtilitiesXMLReader.setBuddyFactory(buddyFactory);		
		IXMLDataObject lIData = UtilitiesXMLReader.readDocument(dom);	   
	  
	  	lIData.generateBuddyInfo();
	  
	  //****************** TESTER *************************

	- run the tester and see the output as a guide to what buddy classes need
	  creating.



	2) Setup the xml buddy classes and their factory

	- create an XML Buddy (extend XMLAbstractBuddy) for each tag in the XML 
	  heirarchy that corresponds to a class in the business object domain.
	  Do NOT create buddies for simple value types and don't bother creating
	  buddies for list nodes. (The latter can be done if necessary later.)
	  
    - create a private dataObject attribute of the type of BO that corresponds
      to the tag's node in the XML structure and override getDataObject()
      to return it

	- override the addChild() method: this will be used to interogate the 
	  data types that for this XML node's child elements and then transform
	  those into attributes to add to the buddy's dataObject
	
	- create an xml buddy factory, extending XMLSimpleBuddyFactory, that will 
	  vend the buddies 

	- in the buddy factory, override "getXMLBuddy(String pTag)" and test the
	  tag parameter for tye XML node tag name that have had buddies created
	  for them. Simply instantiate and return the applicable buddy.

	

	
	3) Fill in the blanks for the buddies
	
	- in addChild(), test the type (use instanceOf)of the dataObject parameter 
	  being passed in and deal with the type as detailed below:
	
	- a UtilitiesSimpleXMLType will correspond to a simple typed attribute value
	  such as a String foreName or Integer age. It is identified by its tag 
	  attribute and will contain a String representation in its value attribute.
	  Transform the value as applicable and set the corresponding buddy.dataObject
	  attribute
	  
	- a UtilitiesComplexXMLType will correspond to an XML element that has non-simple
	  children. It is most likely to be a list type as, otherwise, a custom buddy 
	  would likely to have been created. Process the UtilitiesComplexXMLType.children
	  Vector attribute and test their type in the same manner as explained here.
	
	- a domain business object type expected of the XML node's children should simply
	  be used to set the applicable attribute on the buddy.dataObject associted object

	- repeat this for each of the buddies created  
	
	
	
	
	4) Update the tester to use the customisations
	
	- substitute the new buddy factory for the default one;
	
		// IXMLBuddyFactory buddyFactory = new XMLSimpleBuddyFactory(); <-- DEFAULT
		IXMLBuddyFactory buddyFactory = new TimekeeperXMLBuddyFactory(); <-- CUSTOM
		UtilitiesXMLReader.setBuddyFactory(buddyFactory);	

	- cast the output of the XML read to the business domain object that corresponds
	  to the root element of the XML document and output it
	  
		IXMLDataObject lIData = UtilitiesXMLReader.readDocument(dom);	   
	    // lIData.generateBuddyInfo();	  
	    if (lIData instanceof TimeSheetCompositeBO) ((TimeSheetCompositeBO)(lIData)).dump();

	- the modified tester is now the basis of how to integrate the XML reading 
	  capability into the business domain application
	  
	  


FURTHER NOTES
	  
 	- Lists and other multiplicity nodes can be left; if so, a dataObject of
	  type UtilitiesComplexXMLType will be passed to the parent nodes buddy
	  This will contain the list elements in its "children" attribute
	  
 */





public interface AAA_HowToUseTheXMLBuddySystem {

}
