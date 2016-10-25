/*
 * Created on 26-Oct-06
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.apps.clipboard;

import org.w3c.dom.Document;

import uk.co.neo9.utilities.file.FileServer;
import uk.co.neo9.utilities.xml.IXMLBuddyFactory;
import uk.co.neo9.utilities.xml.UtilitiesXMLReader;

/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ClipboardDBManager {

    private String dataFile = null;
    
    public ClipboardFolder loadClipboardItems(){
        
        ClipboardFolder folder = null;
        
        // load the xml data file
        String lFileDetails = getDataFileDetails();
        Document dom = null;
        dom = FileServer.loadXMLDocumentFromFile(lFileDetails.toString());
        
        if (dom==null) {
            System.err.println("Dom didn't load! -> " + lFileDetails);
            return null;
        }       
        
        // transform the dom into BOs
        IXMLBuddyFactory buddyFactory = new ClipboardXMLBuddyFactory(); 
        UtilitiesXMLReader.setBuddyFactory(buddyFactory);       
        folder = (ClipboardFolder)UtilitiesXMLReader.readDocument(dom);           
        
        return folder;
    }
 
 
 
    public void saveClipboardItems(ClipboardFolder folder){
        
        // convert the clipboard itmes to xml
        String xml = ClipboardItemXMLBuddy.generateXML(folder);
        
        // save the xml data file
        String lFileDetails = getDataFileDetails();
//        try {
//            FileServer.writeTextFile(lFileDetails, xml);           
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println(xml);

    }
    

    public void setDataFileDetails(String string) {
        dataFile = string;
    }

    public String getDataFileDetails() {

        final String lFileName = "clipboard.xml";
        final String lFolder = "D:\\Test\\clipboard\\";
        
        StringBuffer lFileDetails = new StringBuffer();
        lFileDetails.append(lFolder);
        lFileDetails.append("\\");
        lFileDetails.append(lFileName);
        
        return lFileDetails.toString();

//        return dataFile;
    }

}
