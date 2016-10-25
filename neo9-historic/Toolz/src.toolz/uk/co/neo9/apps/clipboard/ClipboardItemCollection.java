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
public class ClipboardItemCollection {
    
    private ArrayList items = new ArrayList();

    public void add(ClipboardItemI item){
        items.add(item);
    }
    
    public ArrayList getAll() {
        return items;
    }

}
