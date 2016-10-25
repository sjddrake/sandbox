/**
* This source code is the property of Lloyds TSB Group PLC.         
* All Rights Reserved.                                              
*                                                                   
* Class Name: ValidationHelper.class			                
*                                                                   
* Application Name: SLR                                            
*                                                                   
* Source File: ValidationHelper.java					        
*                                                                   
* Author(s): Various, see individual methods 							                  	                    
*                                                                   
* Date: 31-May-05                                                     
*                                                                   
* Description:
*   					                                
* @author 
* 
* @version %I%, %G%
* 
* @since   1.0  
* 
* @serial                                                      
* 
* @see package
*                                                                   
*/

package com.lloydstsb.chordiant.osp.helpers;

import java.util.Collection;
import java.util.Map;

import com.lloydstsb.framework.ea.transactioncontext.TransactionContext;


public class ValidationHelper {	

	private ValidationHelper(){
		// This is to make sure nobody creates one of these. Private constructor
		// All methods should be static		
	}
	
	/**
	* Series of utilities to ensure that parameters not null, empty, 
	* less than 1 or == 0 
	*  
	* @param String, Object, Collection, Map 
	* @return boolean		
	* @throws n/a
	*/
	
	public static boolean isEmpty(String s){
		
		boolean retVal = true;
		
		if( s == null || s.trim().equals("") || s.length() < 1){
			retVal = true;
		}		
		else{
			retVal = false;
		}
		
		return retVal;		
		
	} 
	
	/**
	 * See Javadoc above
	 */
	
	public static boolean isEmpty(Object obj){
		boolean retVal = true;
	
		if( obj == null){
			retVal = true;
		}		
		else{
			retVal = false;
		}
	
		return retVal;		
	
	} 
	/**
	 * See Javadoc above
	 */
	public static boolean isEmpty(Collection v){
		boolean retVal = true;
		
		if(v == null || v.isEmpty() || v.size() == 0){
			retVal = true;
		}		
		else{
			retVal = false;
		}
	
		return retVal;		
	
	}
	/**
	 * See Javadoc above
	 */ 
	
	public static boolean isEmpty(Map m){
		boolean retVal = true;
		
		if(m == null || m.isEmpty() || m.size() == 0){
			retVal = true;
		}		
		else{
			retVal = false;
		}
	
		return retVal;		
	
	} 	

}
