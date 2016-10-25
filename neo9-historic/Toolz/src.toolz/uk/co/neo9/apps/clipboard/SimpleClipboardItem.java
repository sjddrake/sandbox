/*
 * Created on 25-Oct-06
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.apps.clipboard;

/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SimpleClipboardItem extends ClipboardItemAdapter {

    public String getDescription() {
        return getClipText();
    }

    public void setDescription(String string) {
        // do nothing
    }

    public String getName() {
        return getClipText();
    }
    
    public void setName(String string) {
        // do nothing
    }
}
