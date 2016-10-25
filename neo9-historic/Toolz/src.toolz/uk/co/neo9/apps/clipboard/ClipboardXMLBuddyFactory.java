/*
 * Created on 25-Oct-06
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.apps.clipboard;

import uk.co.neo9.utilities.xml.IXMLBuddy;
import uk.co.neo9.utilities.xml.XMLSimpleBuddyFactory;

/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ClipboardXMLBuddyFactory extends XMLSimpleBuddyFactory implements ClipboardXMLTagsI{

    public IXMLBuddy getXMLBuddy(String pTag) {
        
        IXMLBuddy buddy = null;
        
        if (TAG_SIMPLE_CLIP_ITEM.equals(pTag)) {
            buddy = new ClipboardItemXMLBuddy(new SimpleClipboardItem());
        } else if (TAG_TEMPLATE_CLIP_ITEM.equals(pTag)) {
            buddy = new ClipboardItemXMLBuddy(new TemplateClipboardItem());
        } else if (TAG_CLIPBOARD.equals(pTag)) {
            buddy = new ClipboardItemXMLBuddy(new ClipboardFolder());
        }
        
        return buddy;
    }
    
}
