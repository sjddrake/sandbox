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
public interface ClipboardItemI {

    public String getClipText();
    public String getDescription();
    public String getName();
    public void setClipText(String string);
    public void setDescription(String string);
    public void setName(String string);
        
}
