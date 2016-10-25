/*
 * Created on 14-Nov-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities.xml;



import java.util.Vector;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UtilitiesXMLReader {
	
	private static IXMLBuddyFactory buddyFactory;
	private static IXMLBuddyFactoryContext factoryContext = new XMLSimpleBuddyFactoryContext();


	public static void setBuddyFactory(IXMLBuddyFactory pFactory){
		buddyFactory = pFactory;
	}

	public static void setFactoryContext(IXMLBuddyFactoryContext pContext){
		factoryContext = pContext;
	}

	public static IXMLDataObject readDocument(Node pDocumentNode){

		IXMLDataObject lRoot = processElementNode(pDocumentNode);
		
		if (lRoot instanceof UtilitiesComplexXMLType) {
			
			UtilitiesComplexXMLType lGenericRoot = (UtilitiesComplexXMLType)(lRoot);
			if (lGenericRoot.getName().equalsIgnoreCase("#document")) {
				
				lRoot = (IXMLDataObject)(lGenericRoot.children.get(0));
			}
		}
		
		return lRoot;

	}
	
	
	private static Vector processAttributes(Node pNode){
		
		Vector lAttributes = new Vector();
		
		// get the nodemap
		NamedNodeMap lNodeMap = null;
		lNodeMap = pNode.getAttributes();
		
		for (int i = 0; i < lNodeMap.getLength(); i++) {
			
			Node lAttributeNode = lNodeMap.item(i);
			
			// now process this node
			IXMLDataObject lAttribute = processElementNode(lAttributeNode);
			lAttributes.add(lAttribute);
		
		}
		
				
		return lAttributes;
	}


	private static IXMLDataObject processElementNode(Node pElementNode){

		// need a buddy to process the node - this is the generic handle to it
		IXMLBuddy lElementDefinition = null;
		String lElementNodeName = new String(pElementNode.getNodeName());
	
	
		// check to see if it's an attribute node - these are always generic
		boolean lIsAttribute = false;
		if (pElementNode.getNodeType() == Node.ATTRIBUTE_NODE) lIsAttribute = true;
	
		boolean lIsComplexType = false;
		if (lIsAttribute) {
			
			// use an attribute definition buddy
			lElementDefinition = new XMLAttributeDefinition();
			lElementDefinition.setName(lElementNodeName);
			
		} else {


			// work out if the node is a complex type - ie has children
			NodeList lChildren = pElementNode.getChildNodes();
			
			for (int i = 0; i < lChildren.getLength(); i++) {
				Node lChildNode = lChildren.item(i);
				if (lChildNode.getNodeType() == Node.ELEMENT_NODE){
					lIsComplexType = true;
					break;
				}
			}				



			// use the factory to lookup the buddy to process this node
			// --> should be using the context here but dont know how to!!!
			if (buddyFactory != null) {
				factoryContext.setKey(lElementNodeName);
				factoryContext.setIsComplex(lIsComplexType);
				// lElementDefinition = buddyFactory.getXMLBuddy(lElementNodeName);
				lElementDefinition = buddyFactory.getXMLBuddy(factoryContext);
			}
			
			// if one isn't defined, use a generic one
			if (lElementDefinition == null){

				// now use the applicable generic buddy
				if (lIsComplexType){
					lElementDefinition = new UtilitiesComplexXMLType();
				} else {
					lElementDefinition = new UtilitiesSimpleXMLType();
				}
				
				// generic buddy needs to record the tag name
				lElementDefinition.setName(lElementNodeName);
			}
		}
		
	
		//
		//
		//		NOW LETS DO THE XML STUFF
		//
		//


	
		// process any attributes
		if (pElementNode.hasAttributes()){
						
			Vector lAttributes = processAttributes(pElementNode);
			lElementDefinition.addAttributes(lAttributes);
		}



		NodeList lChildren = pElementNode.getChildNodes();
		for (int j = 0; j < lChildren.getLength(); j++) {
			Node lChildNode = lChildren.item(j);
			
			int lNodeType = lChildNode.getNodeType();
			switch (lNodeType) {
	
				case Node.DOCUMENT_NODE :
					// process the element
					System.out.println(lChildNode.getNodeName());
					break;
				
				
				
				case Node.ELEMENT_NODE :
				
					// process the element
					IXMLDataObject lChildDO = processElementNode(lChildNode);
					lElementDefinition.addChild(lChildDO);
					
					//System.out.println(lChildNode.getNodeName());

				
					break;
					
					
				
				
				case Node.TEXT_NODE :
					
					String lTextNodeValue = new String(lChildNode.getNodeValue());
					lTextNodeValue = lTextNodeValue.trim();
					if (!lTextNodeValue.equalsIgnoreCase("")) {						
						lElementDefinition.setValue(lTextNodeValue);
					}
					break;
					
					

				
					
				default :
					break;
			}
			
		}
		
		return lElementDefinition.getDataObject();
	}
	
}
