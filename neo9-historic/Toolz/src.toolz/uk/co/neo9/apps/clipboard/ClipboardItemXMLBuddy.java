/*
 * Created on 25-Oct-06
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.apps.clipboard;

import java.util.Iterator;
import java.util.Vector;

import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.UtilitiesTabulator;
import uk.co.neo9.utilities.xml.IXMLDataObject;
import uk.co.neo9.utilities.xml.UtilitiesComplexXMLType;
import uk.co.neo9.utilities.xml.UtilitiesSimpleXMLType;
import uk.co.neo9.utilities.xml.UtilitiesXMLTags;
import uk.co.neo9.utilities.xml.XMLAbstractBuddy;

/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ClipboardItemXMLBuddy extends XMLAbstractBuddy implements ClipboardXMLTagsI{

    private ClipboardItemI bo =  null;


    private ClipboardItemXMLBuddy(){};
    
    public ClipboardItemXMLBuddy(ClipboardItemI clipItem){
        bo = clipItem;
    }
    

    public IXMLDataObject getDataObject(){
        return (IXMLDataObject)bo;
    }
    
    
    public void addChild(IXMLDataObject pDataObject) {
        
        if (pDataObject != null) {
            
            if (pDataObject instanceof UtilitiesSimpleXMLType) {
            
                String tag = ((UtilitiesSimpleXMLType)pDataObject).getName();
                String value = ((UtilitiesSimpleXMLType)pDataObject).getValue();
            
                if (TAG_CLIPTEXT.equalsIgnoreCase(tag)){
                    this.bo.setClipText(value);
                } else if (TAG_NAME.equalsIgnoreCase(tag)){
                    this.bo.setName(value);
                } else if (TAG_DESCRIPTION.equalsIgnoreCase(tag)){
                    this.bo.setDescription(value);
                }
       
            } if (pDataObject instanceof UtilitiesComplexXMLType) {
            
                String tag = ((UtilitiesComplexXMLType)pDataObject).getName();
                String value = ((UtilitiesComplexXMLType)pDataObject).getValue();
                
                if (TAG_SIMPLE_CLIP_ITEMS.equalsIgnoreCase(tag) || TAG_TEMPLATE_CLIP_ITEMS.equalsIgnoreCase(tag)){
                    
                    Vector listItems = ((UtilitiesComplexXMLType)(pDataObject)).children;
                    for (Iterator iter = listItems.iterator(); iter.hasNext();) {
                        ClipboardItemI element = (ClipboardItemI) iter.next();
                        ((ClipboardFolder)(this.bo)).addClipItem(element);
                    }

                }//else if (TAG_NAME.equalsIgnoreCase(tag)){
//                    this.bo.setName(value);
//                } else if (TAG_DESCRIPTION.equalsIgnoreCase(tag)){
//                    this.bo.setDescription(value);
//                }
           
            }
            
        }

    }    
    
    
    public static String generateXML(ClipboardFolder folder){
        
        StringBuffer buf = new StringBuffer();
        
        // start root
        buf.append(UtilitiesXMLTags.buildTag(TAG_CLIPBOARD,true));
        buf.append(CommonConstants.NEWLINE);
        
        // simple items
        int tabStops = 1;
        buf.append(UtilitiesTabulator.addTabs(tabStops));
        buf.append(UtilitiesXMLTags.buildTag(TAG_SIMPLE_CLIP_ITEMS,true));
        buf.append(CommonConstants.NEWLINE);
        
        for (Iterator iter = folder.getSimpleClipItems().iterator(); iter.hasNext();) {
            
            SimpleClipboardItem item = (SimpleClipboardItem) iter.next();

            buf.append(UtilitiesTabulator.addTabs(tabStops+1));            
            buf.append(UtilitiesXMLTags.buildTag(TAG_SIMPLE_CLIP_ITEM,true));
            buf.append(CommonConstants.NEWLINE);
            
            buf.append(UtilitiesTabulator.addTabs(tabStops+2));              
            buf.append(UtilitiesXMLTags.buildElement(TAG_CLIPTEXT,item.getClipText()));
            buf.append(CommonConstants.NEWLINE);
            
            buf.append(UtilitiesTabulator.addTabs(tabStops+1));            
            buf.append(UtilitiesXMLTags.buildTag(TAG_SIMPLE_CLIP_ITEM,false));
            buf.append(CommonConstants.NEWLINE);
            
        }

        buf.append(UtilitiesTabulator.addTabs(tabStops)); 
        buf.append(UtilitiesXMLTags.buildTag(TAG_SIMPLE_CLIP_ITEMS,false));
        buf.append(CommonConstants.NEWLINE);
        

        // template items
        buf.append(UtilitiesTabulator.addTabs(tabStops));
        buf.append(UtilitiesXMLTags.buildTag(TAG_TEMPLATE_CLIP_ITEMS,true));
        buf.append(CommonConstants.NEWLINE);
        for (Iterator iter = folder.getTemplateClipItems().iterator(); iter.hasNext();) {
            
            TemplateClipboardItem item = (TemplateClipboardItem) iter.next();

            buf.append(UtilitiesTabulator.addTabs(tabStops+1));                
            buf.append(UtilitiesXMLTags.buildTag(TAG_TEMPLATE_CLIP_ITEM,true));
            buf.append(CommonConstants.NEWLINE);

            buf.append(UtilitiesTabulator.addTabs(tabStops+2));                          
            buf.append(UtilitiesXMLTags.buildElement(TAG_NAME,item.getName()));
            buf.append(CommonConstants.NEWLINE);

            buf.append(UtilitiesTabulator.addTabs(tabStops+2));             
            buf.append(UtilitiesXMLTags.buildElement(TAG_DESCRIPTION,item.getDescription()));
            buf.append(CommonConstants.NEWLINE);
            
            buf.append(UtilitiesTabulator.addTabs(tabStops+2));             
            buf.append(UtilitiesXMLTags.buildElement(TAG_CLIPTEXT,item.getClipText()));
            buf.append(CommonConstants.NEWLINE);
            
            buf.append(UtilitiesTabulator.addTabs(tabStops+1));    
            buf.append(UtilitiesXMLTags.buildTag(TAG_TEMPLATE_CLIP_ITEM,false));
            buf.append(CommonConstants.NEWLINE);            
        }       
        
        buf.append(UtilitiesTabulator.addTabs(tabStops)); 
        buf.append(UtilitiesXMLTags.buildTag(TAG_TEMPLATE_CLIP_ITEMS,false));
        buf.append(CommonConstants.NEWLINE);
        
        // end root
        buf.append(UtilitiesXMLTags.buildTag(TAG_CLIPBOARD,false));
        
        return buf.toString();
        
    }
}
