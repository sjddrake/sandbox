/*
 * Created on 25-Oct-06
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.apps.clipboard;

import java.util.ArrayList;

/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ClipboardFolder extends ClipboardItemAdapter {
    
    private ClipboardItemCollection simpleClipItems = new ClipboardItemCollection();
    private ClipboardItemCollection templateClipItems = new ClipboardItemCollection();
    
    public void addClipItem(ClipboardItemI clipItem){       
        if (clipItem instanceof SimpleClipboardItem){
            simpleClipItems.add(clipItem);
        } else if (clipItem instanceof TemplateClipboardItem){
            templateClipItems.add(clipItem);
        }
    }

    public ArrayList getTemplateClipItems(){
        return this.templateClipItems.getAll();
    }
    
    public ArrayList getSimpleClipItems(){
        return this.simpleClipItems.getAll();
    }
    
    public ArrayList getAllClipItems(){
    	ArrayList allItems = new ArrayList(getSimpleClipItems());
    	allItems.addAll(getTemplateClipItems());
        return allItems;
    }    
}
