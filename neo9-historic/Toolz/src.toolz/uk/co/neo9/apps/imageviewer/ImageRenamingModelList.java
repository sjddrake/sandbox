/*
 * Created on 27-May-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.apps.imageviewer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ImageRenamingModelList{
	
	private ArrayList list = new ArrayList();

	public void renameFiles(String root) {
		
		int noOfItems = this.size();
		for (int i = 0; i < noOfItems; i++) {
			ImageRenamingModel model = getItem(i);
			model.renameImageFile(root);
		}
	}
	
	
	public int moveUp(int position){
		
		// moves the item at 'position' up one in the list
		// returns false if it gets to top or is at top
		int newPosition = 0;
		
		if (position > 0){
			
			ImageRenamingModel thisItem = this.getItem(position);
			thisItem.decrement();
			ImageRenamingModel previousItem = this.getItem(position-1);
			previousItem.increment();
			list.set(position, previousItem);
			list.set(position-1, thisItem);
			newPosition = position-1;
		}
		
		return newPosition;
	}
	

	public int moveToStart(int position){

		int newPosition = 0;
		if (position > 0 && position < list.size()){
			
			ImageRenamingModel thisItem = this.removeItem(position);
			list.add(0, thisItem);
			setIndexes();
			newPosition = 0;
		} else {
			newPosition = position;
		}
		
		return newPosition;
	}	
	
	
	
	public int moveToEnd(int position){

		int newPosition = list.size()-1;
		if (position > -1 && position < list.size()){
			
			ImageRenamingModel thisItem = this.removeItem(position);
			list.add(newPosition, thisItem);
			setIndexes();

		} else {
			newPosition = position;
		}
		
		return newPosition;
	}	
	

	
	private void setIndexes() {
		if (list != null){
			int count = 1;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				ImageRenamingModel item = (ImageRenamingModel) iter.next();
				item.setIndex(count++);
			}
		}
	}


	public int moveDown(int position){
		
		// moves the item at 'position' up one in the list
		// returns false if it gets to top or is at top
		int newPosition = list.size()-1;
		
		if (position < list.size()-1){

			ImageRenamingModel thisItem = this.getItem(position);
			thisItem.increment();
			ImageRenamingModel nextItem = this.getItem(position+1);
			nextItem.decrement();			
			
			list.set(position, nextItem);
			list.set(position+1, thisItem);
			newPosition = position+1;
		}
		
		return newPosition;
	}	
	
	/**
	 * @return
	 */
	public ArrayList getAll() {
		return getList();
	}


	protected ArrayList getList() {
		return list;
	}
	
	public void add(ImageRenamingModel bo){
		list.add(bo);
	}

	public ImageRenamingModel getItem(int position){
		
		ImageRenamingModel item = null;
		
		if (list != null && position < list.size()){
			item = (ImageRenamingModel)list.get(position);
		}
		
		return item;		
	}
	

	public ImageRenamingModel removeItem(int row){
		ImageRenamingModel value = null;
		if (list != null) value = (ImageRenamingModel)list.remove(row);
		return value;
	}	
	
	public int size() {
		if (list != null)
			return list.size();
		else
			return 0;
	}

	public void clear() {
		if (list != null)
		{
			list.clear();
		}
	}


    public void populateImageList(File[] imageFiles){
    	
    	if (list == null) list = new ArrayList();
    	
    	// create a model per file and add it to the list
    	int count = 1;
		for (int i = 0; i < imageFiles.length; i++) {
			
			File file = imageFiles[i];
			
			if (isImageFile(file)){
			
				ImageRenamingModel model = new ImageRenamingModel();
				
				model.setImageFile(file);
				model.loadImage();
				model.setIndex(count++);
				
				list.add(model);
			}
		}
    }	
	
	
	
	
    private boolean isImageFile(File file) {
		
		boolean isValid =  false;
		
		if (file != null){
			
			String fileName = file.getName();
			if (fileName.toUpperCase().lastIndexOf(".JPG") > -1){
				isValid = true;
			}
		}
		
		return isValid;
	}



//	public Neo9ValidationObject validate() {
//		// TODO Auto-generated method stub
//		return null;
//	}	
	
	/* (non-Javadoc)
	 * @see uk.co.neo9.timekeeper.tpoc.framework.Neo9BOMarkerI#dump()
	 */
	public void dump() {
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			ImageRenamingModel bo = (ImageRenamingModel) iter.next();
			bo.dump();
		}
	}




}
