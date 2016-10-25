/*
 * Created on 27-May-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.apps.mealplanner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MealPlannerTableList{
	
	private ArrayList list = new ArrayList();

	
		
	private void setIndexes() {
		if (list != null){
			int count = 1;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				RecipeBOSelectionModel item = (RecipeBOSelectionModel) iter.next();
				item.setIndex(count++);
			}
		}
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
	
	public void add(RecipeBOSelectionModel bo){
		list.add(bo);
	}

	public void addAll(Collection items){
		list.addAll(items);
	}	

	public void addRecipes(Collection items){
		
		int index = 1;
		for (Iterator iter = items.iterator(); iter.hasNext();) {
			RecipeBO element = (RecipeBO) iter.next();
			RecipeBOSelectionModel model = new RecipeBOSelectionModel();
			model.setRecipeBO(element);
			model.setIndex(index);
			index++;
			list.add(model);
		}
	
	}	
	
	public RecipeBOSelectionModel getItem(int position){
		
		RecipeBOSelectionModel item = null;
		
		if (list != null && position < list.size()){
			item = (RecipeBOSelectionModel)list.get(position);
		}
		
		return item;		
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


	
	/* (non-Javadoc)
	 * @see uk.co.neo9.timekeeper.tpoc.framework.Neo9BOMarkerI#dump()
	 */
	public void dump() {
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			RecipeBOSelectionModel bo = (RecipeBOSelectionModel) iter.next();
			bo.dump();
		}
	}




}
