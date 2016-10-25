/*
 * Created on 25-Oct-06
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.apps.clipboard;

import org.w3c.dom.Document;

import uk.co.neo9.utilities.file.FileServer;
import uk.co.neo9.utilities.xml.IXMLBuddyFactory;
import uk.co.neo9.utilities.xml.IXMLDataObject;
import uk.co.neo9.utilities.xml.UtilitiesXMLReader;
import uk.co.neo9.utilities.xml.XMLSimpleBuddyFactory;

/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ClipboardTester {

    public static void main(String[] args) {
        
//        final String lFileName = "clipboard.xml";
//        final String lFolder = "C:\\simonz\\My Stuff";
//        
//        StringBuffer lFileDetails = new StringBuffer();
//        lFileDetails.append(lFolder);
//        lFileDetails.append("\\");
//        lFileDetails.append(lFileName);
//        
//        Document dom = null;
//        dom = FileServer.loadXMLDocumentFromFile(lFileDetails.toString());
//        
//        if (dom==null) {
//            System.out.println("Dom didn't load! -> " + lFileDetails);
//            return;
//        }       
//        
//        IXMLBuddyFactory buddyFactory = new ClipboardXMLBuddyFactory(); //XMLSimpleBuddyFactory();
//        UtilitiesXMLReader.setBuddyFactory(buddyFactory);       
//        IXMLDataObject lIData = UtilitiesXMLReader.readDocument(dom);      
      
      
        ClipboardDBManager db = new ClipboardDBManager();
        ClipboardFolder folder = db.loadClipboardItems();
      
//        String xml = ClipboardItemXMLBuddy.generateXML(folder);
//        System.out.println(xml);
        
        db.saveClipboardItems(folder);
            
     return;  
    }
}
