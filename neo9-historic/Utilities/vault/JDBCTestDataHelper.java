/***********************************************************************
* This source code is the property of Lloyds TSB Group PLC.            *
* All Rights Reserved.                                                 *
*                                                                      *
* Class Name: JBDCTestDataHelper.java                                  *
*                                                                      *
* Application Name: STEAU                                              *
*                                                                      *
* Source File: JDBCTestDataHelper.java                                 *
*                                                                      *
* Version: 1.0                                                         *
*                                                                      *
* Author(s): Stephen Hardy, CRM                                        *
*                                                                      *
* Date: 23-Mar-05                                                      *
*                                                                      *
* Description:  XXXXXXXX                                               *
  This class is an utility class that will contain generic
  behaviour of and utility methods for a JDBC based Test Data helper. In order
  to benefit from this class you should sub-class it in order to make a domain
  specific Test Data Helper that will contain all the SQL statements required to
  set the test data for a domain and use the JDBC behaviour provided in this 
  class for CRUD operations.
  
  This class will create a <code>Connection</code> to the database using
  the below properties (which are defined in the 
  <code>testing.properties</code> file:
  
  <code>testDriver</code> The driver to use for database connection.    
  <code>testDBServer</code> The server name where the database is running.
  <code>testDBSid</code> The SID of the database to connect to.
  <code>testDBPort</code> The database port
  <code>testDBUser</code> The username to connect to the database with
  <code>testDBPassword</code> The password to connect to the database with.
 
  If any of the above system properties are missing then the defaults as
  specified by the below will be used:
 
  <code>DEFAULT_DRIVER</code>
  <code>DEFAULT_SERVER</code>
  <code>DEFAULT_PORT</code>
  <code>DEFAULT_SID</code>
  <code>DEFAULT_USERNAME</code>
  <code>DEFAULT_PASSWORD</code>
  
  The directory to look for properties file will be found by using the 
  <code>testingDir</code> system property or <code>DEFAULT_TESTING_DIR</code> if
  the system property is not set.

  Amendments
  Andrew Whitehouse 24/1/2006  Add retrieveColumn and overloaded insertRow/executeRowQuery methods

***********************************************************************/
package com.lloydstsb.java.crm.sbao.cimp.jdbc;

import com.lloydstsb.java.crm.sbao.cimp.clientobjects.ClientObjectFactory;
import com.lloydstsb.java.crm.sbao.cimp.clientobjects.ObjectKeyCO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import oracle.jdbc.OracleTypes;

/**
  This class is an utility class that will contain generic
  behaviour of and utility methods for a JDBC based Test Data helper. In order
  to benefit from this class you should sub-class it in order to make a domain
  specific Test Data Helper that will contain all the SQL statements required to
  set the test data for a domain and use the JDBC behaviour provided in this 
  class for CRUD operations.
  
  This class will create a <code>Connection</code> to the database using
  the below properties (which are defined in the 
  <code>testing.properties</code> file:
  
  <code>testDriver</code> The driver to use for database connection.    
  <code>testDBServer</code> The server name where the database is running.
  <code>testDBSid</code> The SID of the database to connect to.
  <code>testDBPort</code> The database port
  <code>testDBUser</code> The username to connect to the database with
  <code>testDBPassword</code> The password to connect to the database with.
 
  If any of the above system properties are missing then the defaults as
  specified by the below will be used:
 
  <code>DEFAULT_DRIVER</code>
  <code>DEFAULT_SERVER</code>
  <code>DEFAULT_PORT</code>
  <code>DEFAULT_SID</code>
  <code>DEFAULT_USERNAME</code>
  <code>DEFAULT_PASSWORD</code>
  
  The directory to look for sql files will be found by using the 
  <code>testingDir</code> system property or <code>DEFAULT_TESTING_DIR</code> if
  the system property is not set.
  
  @author Stephen Hardy
  @version 1.0
*/
public class JDBCTestDataHelper {

    //  defaults
    protected final static String DEFAULT_DRIVER 
                                        = "oracle.jdbc.driver.OracleDriver";
                                        
    protected final static String DEFAULT_SERVER = "p14814dev041.test.lloydstsb.co.uk";
    protected final static String DEFAULT_PORT = "1522";
    //INTDB9I1.CRMDEV04.LLOYDSTSB.CO.UK
    protected final static String DEFAULT_SID = "rsdv10g1";
    protected final static String DEFAULT_USERNAME = "SISTEAUUSER_CR";
    protected final static String DEFAULT_PASSWORD = "SISTEAUUSER_CR";
    protected final static String DEFAULT_TESTING_DIR = "Y://dev_j2ee_trap//development//Source//java//TRAP_Testing//src//com//lloydstsb//java//crm//sbao//cimp//testing";
    
    private static final String DEFAULT_ID_COLUMN = "ID";
    private static final String DEFAULT_LOCKID_COLUMN = "LOCK_FLAG";
    
    //  keys to look up system properties
    protected final static String KEY_DRIVER = "testDriver";
    protected final static String KEY_SERVER = "testDBServer";
    protected final static String KEY_PORT = "testDBPort";
    protected final static String KEY_SID = "testDBSid";
    protected final static String KEY_USERNAME = "testDBUser";
    protected final static String KEY_PASSWORD = "testDBPassword";
    protected final static String KEY_TESTING_DIR = "testingDir";    
    
    //  constants
    protected final static String PROPERTIES_FILE = "testing.properties";
    protected final static String DATE_FORMAT = "yyyy-MM-dd HH24:mi:ss";
    protected final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected final static SimpleDateFormat SIMPLE_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected final static String SQL_BOOLEAN_FALSE = "0";
    protected final static String SQL_BOOLEAN_TRUE = "1";
    
    //  member variables
    private static String _driver = null;
    private static String _server = null;
    private static String _port = null;
    private static String _sid = null;
    private static String _username = null;
    private static String _password = null;
    private static Properties _properties = null;
    
    /**
     * Constructor
     */
    public JDBCTestDataHelper() {
        super();
    }
    
    /**
     * 
     * This method will return n ObjectKeyCO based on a 
     * SELECT statement passed in.
     * 
     * It will contain the id of the first record returned
     * 
     * 
     * 
     * 
      method-description
    
      @param pTableName
      @param pMap
      @return
      @throws Exception
      @return ObjectKeyCO
      @exception 
      @author ct015146
      @version
     */
    
	public static ObjectKeyCO executeSelectQuery(String pSQL) 
	 throws  Exception{
	
		 Connection mConn = null;
		 CallableStatement mCallStatement = null;
		 ResultSet rs = null;
		 String cud_Id = null;
		 ObjectKeyCO mObjectKeyCO = ClientObjectFactory.createObjectKeyCO();
		 
		 try {
			 //  get the connection, create callable statement and register outs
			 mConn = getConnection();
		 
			 mCallStatement = mConn.prepareCall(pSQL);
		     rs = mCallStatement.executeQuery(pSQL);
		
			 while(rs.next()){
				cud_Id = rs.getString(1);
				if (cud_Id != null){
				mObjectKeyCO.setId(new Integer(cud_Id));
				}
				else{
					mObjectKeyCO.setId(new Integer(0));
				}
				break;
			 }

		 } catch (SQLException e){
			 throw e;
		 } finally{
			 if (null != mConn){
				mConn.close();
			 }
		 }
		 return mObjectKeyCO;
	 }
    
	/**
	 * @param query A SQL query that will return a result set
	 * @return List A List of HashMap objects; each map entry has the column name as its key and the column value in the associated value
	 * @throws SQLException The query failed
	 * @throws ClassNotFoundException Failed to instantiate database driver for the connection
	 * @author Andrew Whitehouse
	 */
	public static List retrieveRows( String query ) throws SQLException, ClassNotFoundException
	{
		Connection connection = null;
		CallableStatement callStatement = null;
		ResultSet rs = null;
		 
		ArrayList list = new ArrayList();
		
		try 
		{
			connection = getConnection();
		 
			callStatement = connection.prepareCall(query);
			rs = callStatement.executeQuery(query);
		
			ResultSetMetaData rsmd = rs.getMetaData();
			
			while( rs.next() )
			{
				HashMap map = new HashMap();
				for ( int i=1; i <= rsmd.getColumnCount(); i++ )
				{
					map.put( rsmd.getColumnName(i), rs.getString(i) );										
				}
				list.add( map );
			}

		} catch (SQLException e){
			throw e;
		} finally{
			if (null != connection){
				connection.close();
			}
		}
		return list;
		
	}
	
	/**
	 * Get values in a single column
	 * @param query SQL query to select the column
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @author Andrew Whitehouse
	 */
	public static List retrieveColumn( String query ) 
		throws SQLException, ClassNotFoundException
	{
		List rowList = JDBCTestDataHelper.retrieveRows( query );

		List retList = null;
		
		if ( rowList != null )
		{
			retList = new ArrayList();
			
			for ( int i=0; i < rowList.size(); i++ )
			{
				HashMap map = (HashMap) rowList.get( i );
				if ( map.size() > 1 )
				{
					throw new IllegalArgumentException( "Query must return only a single column" );
				}
				
				Object[] columnValues = map.values().toArray();
				retList.add( columnValues[0] );
			}
		}	
		
		return retList;
	}

	/**
	 * Implementation of insertRow with default id and lock flag column values
	 * @param pTableName Name of table where row will be created
	 * @param pMap Name/value pairs corresponding to column names and values
	 * @return ObjectKeyCO containing id and lock flag of inserted row
	 * @throws Exception
     * @author Andrew Whitehouse
	 */
	protected static ObjectKeyCO insertRow( String pTableName, HashMap pMap )
		throws Exception
	{
		return insertRow( pTableName, pMap, DEFAULT_ID_COLUMN, DEFAULT_LOCKID_COLUMN );
	}
    
    
    /**
     * This method will create a row in the table identified by 
     * <code>pTableName</code> using the keys in the <code>HashMap</code> given
     * as the column names to be inserted to and the corresponding 
     * <code>HashMap</code> values as the vales to be added to the columns. 
     * 
     * It is the responsibility of the caller to ensure the mandatory fields
     * have been given.
     * 
     * @param pTableName <code>String</code> The table to insert into.
     * @param pMap <code>HashMap</code> The map of column names and values.
     * @param idColumn Column to use for id attribute (returned in ObjectKeyCO)
     * @param lockidColumn Column to use for lock flag attribute (returned in ObjectKeyCO)
     * @throws <code>Exception</code>
     * 
     * @author Stephen Hardy 
     */
    protected static ObjectKeyCO insertRow(String pTableName, HashMap pMap,
		String idColumn, String lockIdColumn )
    throws Exception{
        ObjectKeyCO mObjectKey = null;
        StringBuffer mSQL = null;
        //  create the 'INSERT INTO table_name (' template
        mSQL = new StringBuffer();
        mSQL.append("INSERT INTO ");
        mSQL.append(pTableName);
        mSQL.append(" (");
        //  iterate the map and add the keys as the column names
        for (Iterator iter = pMap.keySet().iterator(); iter.hasNext();) {
            String columnName = (String) iter.next();
            mSQL.append(columnName);
            if (iter.hasNext()){
                mSQL.append(",");
            }else{
                mSQL.append(") VALUES(");
            }
        }
        //  iterate the map and add the values to the statement
        for (Iterator iter = pMap.keySet().iterator(); iter.hasNext();) {
            String columnName = (String) iter.next();
            Object columnValue = (Object) pMap.get(columnName);
            mSQL.append(decodeJavaType(columnValue));
            if (iter.hasNext()){
                mSQL.append(",");
            }else{
                mSQL.append(")");
            }
        }
        //  execute
        mObjectKey = executeRowQuery( mSQL.toString(), idColumn, lockIdColumn );
        return mObjectKey;
    }
    
	/**
	 * This method will create a row in the table identified by 
	 * <code>pTableName</code> using the keys in the <code>HashMap</code> given
	 * as the column names to be inserted to and the corresponding 
	 * <code>HashMap</code> values as the vales to be added to the columns. 
	 * 
	 * It is the responsibility of the caller to ensure the mandatory fields
	 * have been given.
	 * 
	 * @param pTableName <code>String</code> The table to insert into.
	 * @param pMap <code>HashMap</code> The map of column names and values.
	 * @throws <code>Exception</code>
	 * 
	 * @author Stephen Hardy 
	 */
	protected static ObjectKeyCO insertRowSimple(String pTableName, HashMap pMap)
	throws Exception{
		ObjectKeyCO mObjectKey = null;
		StringBuffer mSQL = null;
		//  create the 'INSERT INTO table_name (' template
		mSQL = new StringBuffer();
		mSQL.append("INSERT INTO ");
		mSQL.append(pTableName);
		mSQL.append(" (");
		//  iterate the map and add the keys as the column names
		for (Iterator iter = pMap.keySet().iterator(); iter.hasNext();) {
			String columnName = (String) iter.next();
			mSQL.append(columnName);
			if (iter.hasNext()){
				mSQL.append(",");
			}else{
				mSQL.append(") VALUES(");
			}
		}
		//  iterate the map and add the values to the statement
		for (Iterator iter = pMap.keySet().iterator(); iter.hasNext();) {
			String columnName = (String) iter.next();
			Object columnValue = (Object) pMap.get(columnName);
			mSQL.append(decodeJavaType(columnValue));
			if (iter.hasNext()){
				mSQL.append(",");
			}else{
				mSQL.append(")");
			}
		}
		//  execute
		mObjectKey = executeRowQuerySimple(mSQL.toString());
		return mObjectKey;
	}

	/**
	 * This method will create a row in the table identified by 
	 * <code>pTableName</code> using the keys in the <code>HashMap</code> given
	 * as the column names to be inserted to and the corresponding 
	 * <code>HashMap</code> values as the vales to be added to the columns.
	 * The returned ObjectKeyCO will contain only a lockId and NO Id. 
	 * 
	 * It is the responsibility of the caller to ensure the mandatory fields
	 * have been given.
	 * 
	 * @param pTableName <code>String</code> The table to insert into.
	 * @param pMap <code>HashMap</code> The map of column names and values.
	 * @throws <code>Exception</code>
	 * 
	 * @author Stephen Hardy 
	 */
	protected static ObjectKeyCO insertRowSimpleNoId(String pTableName, HashMap pMap)
	throws Exception{
		ObjectKeyCO mObjectKey = null;
		StringBuffer mSQL = null;
		//  create the 'INSERT INTO table_name (' template
		mSQL = new StringBuffer();
		mSQL.append("INSERT INTO ");
		mSQL.append(pTableName);
		mSQL.append(" (");
		//  iterate the map and add the keys as the column names
		for (Iterator iter = pMap.keySet().iterator(); iter.hasNext();) {
			String columnName = (String) iter.next();
			mSQL.append(columnName);
			if (iter.hasNext()){
				mSQL.append(",");
			}else{
				mSQL.append(") VALUES(");
			}
		}
		//  iterate the map and add the values to the statement
		for (Iterator iter = pMap.keySet().iterator(); iter.hasNext();) {
			String columnName = (String) iter.next();
			Object columnValue = (Object) pMap.get(columnName);
			mSQL.append(decodeJavaType(columnValue));
			if (iter.hasNext()){
				mSQL.append(",");
			}else{
				mSQL.append(")");
			}
		}
		//  execute
		mObjectKey = executeRowQueryNoId(mSQL.toString());
        return mObjectKey;
    }
    
    /**
     * This method will create a row in the table identified by 
     * <code>pTableName</code> using the keys in the <code>HashMap</code> given
     * as the column names to be inserted to and the corresponding 
     * <code>HashMap</code> values as the vales to be added to the columns. 
     * 
     * It is the responsibility of the caller to ensure the mandatory fields
     * have been given.
     * 
     * This method will not return the lock flag of the inserted row.
     * 
     * @param pTableName <code>String</code> The table to insert into.
     * @param pMap <code>HashMap</code> The map of column names and values.
     * @throws <code>Exception</code>
     * 
     * @author Darren Davies
     */
    protected static ObjectKeyCO insertRowNoLockFlag(String pTableName, HashMap pMap) throws Exception{
        StringBuffer mSQL = null;
        //  create the 'INSERT INTO table_name (' template
        mSQL = new StringBuffer();
        mSQL.append("INSERT INTO ");
        mSQL.append(pTableName);
        mSQL.append(" (");
        //  iterate the map and add the keys as the column names
        for (Iterator iter = pMap.keySet().iterator(); iter.hasNext();) {
            String columnName = (String) iter.next();
            mSQL.append(columnName);
            if (iter.hasNext()){
                mSQL.append(",");
            }else{
                mSQL.append(") VALUES(");
            }
        }
        //  iterate the map and add the values to the statement
        for (Iterator iter = pMap.keySet().iterator(); iter.hasNext();) {
            String columnName = (String) iter.next();
            Object columnValue = (Object) pMap.get(columnName);
            mSQL.append(decodeJavaType(columnValue));
            if (iter.hasNext()){
                mSQL.append(",");
            }else{
                mSQL.append(")");
            }
        }
        //  execute
        return executeRowQueryNoLockFlag(mSQL.toString());
    }

    /**
     * This method will update a row in the table identified by 
     * <code>pTableName</code> using the keys in the <code>HashMap</code> given
     * as the column names to be updated to and the corresponding 
     * <code>HashMap</code> values as the vales to be added to the columns. A
     * hashmap containg the criteria used to update the rows mus also be
     * provided.
     * 
     * @param pTableName <code>String</code> The table to update.
     * @param pMap <code>HashMap</code> The map of column names and values.
     * @throws <code>Exception</code>
     * 
     * @author Stephen Hardy 
     */
    protected static ObjectKeyCO updateRow( String pTableName, 
                                            HashMap pUpdateMap,
                                            HashMap pCriteriaMap)
    throws Exception{
        ObjectKeyCO mObjectKey = null;
        StringBuffer mSQL = null;
        //  check criteria map is nut null, otherwise all rows will be updated!
        if (null == pCriteriaMap){
            throw new Exception("The criteria map must nut be null!");
        }
        //  create the UPDATE statement
        mSQL = new StringBuffer();
        mSQL.append("UPDATE ");
        mSQL.append(pTableName);
        mSQL.append(" SET ");
        //  iterate the map and add the keys as the column names
        for (Iterator iter = pUpdateMap.keySet().iterator(); iter.hasNext();) {
            String columnName = (String) iter.next();
            mSQL.append(columnName);
            mSQL.append(" = ");
            mSQL.append(decodeJavaType(pUpdateMap.get(columnName)));
            if (iter.hasNext()){
                mSQL.append(", ");
            }
        }
        //  create the criteria statement
        mSQL.append("WHERE ");
        for (Iterator iter = pCriteriaMap.keySet().iterator(); iter.hasNext();){
            String columnName = (String) iter.next();
            mSQL.append(columnName);
            mSQL.append(" = ");
            Object columnValue = (Object) pCriteriaMap.get(columnName);
            mSQL.append(decodeJavaType(columnValue));
            if (iter.hasNext()){
                mSQL.append(" AND ");
            }
        }
        //  execute
        mObjectKey = executeRowQuery(mSQL.toString());
        return mObjectKey;
    }

	/**
	 * This method will update a row in the table identified by 
	 * <code>pTableName</code> using the keys in the <code>HashMap</code> given
	 * as the column names to be updated to and the corresponding 
	 * <code>HashMap</code> values as the vales to be added to the columns. A
	 * hashmap containg the criteria used to update the rows mus also be
	 * provided.
	 * 
	 * @param pTableName <code>String</code> The table to update.
	 * @param pMap <code>HashMap</code> The map of column names and values.
	 * @throws <code>Exception</code>
	 * 
	 * @author Eileen Abisgold
	 */
	protected static ObjectKeyCO updateRowNoLockFlag( String pTableName, 
											HashMap pUpdateMap,
											HashMap pCriteriaMap)
	throws Exception{
		ObjectKeyCO mObjectKey = null;
		StringBuffer mSQL = null;
		//  check criteria map is nut null, otherwise all rows will be updated!
		if (null == pCriteriaMap){
			throw new Exception("The criteria map must nut be null!");
		}
		//  create the UPDATE statement
		mSQL = new StringBuffer();
		mSQL.append("UPDATE ");
		mSQL.append(pTableName);
		mSQL.append(" SET ");
		//  iterate the map and add the keys as the column names
		for (Iterator iter = pUpdateMap.keySet().iterator(); iter.hasNext();) {
			String columnName = (String) iter.next();
			mSQL.append(columnName);
			mSQL.append(" = ");
			mSQL.append(decodeJavaType(pUpdateMap.get(columnName)));
			if (iter.hasNext()){
				mSQL.append(", ");
			}
		}
		//  create the criteria statement
		mSQL.append("WHERE ");
		for (Iterator iter = pCriteriaMap.keySet().iterator(); iter.hasNext();){
			String columnName = (String) iter.next();
			mSQL.append(columnName);
			mSQL.append(" = ");
			Object columnValue = (Object) pCriteriaMap.get(columnName);
			mSQL.append(decodeJavaType(columnValue));
			if (iter.hasNext()){
				mSQL.append(" AND ");
			}
		}
		//  execute
		mObjectKey = executeRowQueryNoLockFlag(mSQL.toString());
		return mObjectKey;
	}
	    
    /**
     * This method will delete a row in the table identified by 
     * <code>pTableName</code> using the keys in the <code>HashMap</code> given
     * as the column names and the values as values for the delete criteria;
     * 
     * @param pTableName <code>String</code> The table to insert into.
     * @param pMap <code>HashMap</code> The map of column names and values.
     * @throws <code>Exception</code>
     * 
     * @author Stephen Hardy 
     */
    public static void deleteRows(String pTableName, HashMap pMap)
    throws Exception{
        StringBuffer mSQL = null;
        //  create the 'INSERT INTO table_name (' template
        mSQL = new StringBuffer();
        mSQL.append("DELETE FROM ");
        mSQL.append(pTableName);
        mSQL.append(" WHERE ");
        //  iterate the map and add the keys as the column names
        for (Iterator iter = pMap.keySet().iterator(); iter.hasNext();) {
            String columnName = (String) iter.next();
            mSQL.append(columnName);
            mSQL.append(" = ");
            mSQL.append(decodeJavaType(pMap.get(columnName)));
            if (iter.hasNext()){
                mSQL.append(" AND ");
            } 
        }

        //  execute
        execute(mSQL.toString());
    }
    
    /**
     * This is a private helper method that will decode java types to the
     * required SQL formatting.
     * 
     * @param pType The java <code>Object</code>.
     * 
     * @author Stephen Hardy
     */
    protected static String decodeJavaType(Object pType){
        StringBuffer mSQL = new StringBuffer();
        if (null != pType){
            if (pType instanceof String){
                //  needs to be surrounded by ' '
                mSQL.append("'");
                mSQL.append(pType);
                mSQL.append("'");
            } else if (pType instanceof Timestamp){
                mSQL.append(normalize((Timestamp)pType)); 
            } else if (pType instanceof Date){
                //  format as described in DATE_FORMAT
                mSQL.append(normalize((Date)pType));
            } else if (pType instanceof Boolean){
                mSQL.append(normalize((Boolean)pType));
            } else {
                //  default to the toString of the object
                mSQL.append(pType.toString());
            }
        }else{
            mSQL.append("null");
        }
        return mSQL.toString();
    }
    
    /**
     * This is a private helper method that will decode a java date to the
     * required SQL formatting.
     * 
     * @param pType The java <code>Object</code>.
     * 
     * @author Stephen Hardy
     */
    private static String normalize(Date pDate){
        StringBuffer mSqlDate = new StringBuffer();
        if(null != pDate){
            mSqlDate.append("To_Date('");
            mSqlDate.append(SIMPLE_DATE_FORMAT.format(pDate));
            mSqlDate.append("','");
            mSqlDate.append(DATE_FORMAT);
            mSqlDate.append("')");
        } else {
            mSqlDate.append("null");
        }
        return mSqlDate.toString();
    }

    /**
     * This is a private helper method that will decode a java timestamp to the
     * required SQL formatting.
     * 
     * @param pType The java <code>Object</code>.
     * 
     * @author Stephen Hardy
     */
    private static String normalize(Timestamp pTimeStamp){
        StringBuffer mSqlDate = new StringBuffer();
        if(null != pTimeStamp){
            mSqlDate.append("To_Timestamp('");
            mSqlDate.append(SIMPLE_TIMESTAMP_FORMAT.format(pTimeStamp));
            mSqlDate.append("','");
            mSqlDate.append(DATE_FORMAT);
            mSqlDate.append("')");
        } else {
            mSqlDate.append("null");
        }
        return mSqlDate.toString();
    } 
    
    /**
     * This is a private helper method that will decode a java boolean to the
     * required SQL formatting.
     * 
     * @param pType The java <code>Object</code>.
     * 
     * @author Stephen Hardy
     */
    private static String normalize(Boolean pBoolean){
        StringBuffer mSqlBoolean = new StringBuffer();
        if(null != pBoolean){
            if (pBoolean.booleanValue()){
                mSqlBoolean.append(SQL_BOOLEAN_TRUE);
            } else {
                mSqlBoolean.append(SQL_BOOLEAN_FALSE);
            }
        } else {
            mSqlBoolean.append("null");
        }
        return mSqlBoolean.toString();
    }

    /**
	 * Implementation of executeRowQuery, with default id and lock flag column names
	 * used for values returned in ObjectKeyCO
	 * @param sql The SQL to execute
	 * @return ObjectKeyCO containing id and lock flag
	 * @throws Exception e
	 * @author Andrew Whitehouse
	 */
	public static ObjectKeyCO executeRowQuery( String sql ) throws Exception
	{
		return executeRowQuery( sql, DEFAULT_ID_COLUMN, DEFAULT_LOCKID_COLUMN );
	}

    /**
     * This method can be used in order to execute a single SQL statement on the
     * database and get back the <code>ObjectKeyCO</code> which contains the
     * id and lock id for that row.
     * 
     * @param pSQL SQL to be run on the database
     * @param idColumn Column from which id should be retrieved
     * @param lockIdColumn Column from which lock id should be retrieved
     * @return ObjectKeyCO Contains id and lock id of created row
     * @throws Exception An error has occured
     * @author Stephen Hardy
     * @author Andrew Whitehouse
     */
    protected static ObjectKeyCO executeRowQuery(String pSQL, String idColumn, String lockIdColumn ) 
    throws  Exception{
        ObjectKeyCO mObjectKeyCO = null;
        Connection mConn = null;
        CallableStatement mCallStatement = null;
        StringBuffer mDynamicSQL = null;
        int mId = 0;
        int mLockId = 0;
        
        /*
         * get the connection and execute the statement returning the ID and
         * LOCK_FLAG
         */
        try {
            //  create the dynamic sql
            mDynamicSQL = new StringBuffer();
            mDynamicSQL.append("begin ");
            mDynamicSQL.append(pSQL);
            mDynamicSQL.append(" returning " + idColumn + ", " + lockIdColumn + " into ?,?; end;");
            //  get the connection, create callable statement and register outs
            mConn = getConnection();
            mCallStatement = mConn.prepareCall(mDynamicSQL.toString());
            mCallStatement.registerOutParameter(1, OracleTypes.INTEGER);
            mCallStatement.registerOutParameter(2, OracleTypes.INTEGER);
            //System.out.println( "Executing following callable statement: "
            //                    +mDynamicSQL);
            mCallStatement.executeUpdate();
            //  get the outputs and create the object key
            mId = mCallStatement.getInt(1);
            mLockId = mCallStatement.getInt(2);
            //System.out.println("ID: "+mId);
            //System.out.println("Lock Id: "+mLockId);
            mObjectKeyCO = ClientObjectFactory.createObjectKeyCO();
            mObjectKeyCO.setId(new Integer(mId));
            mObjectKeyCO.setLockId(new Integer(mLockId));
        } catch (SQLException e){
            throw e;
        } finally{
            if (null != mConn){
                mConn.close();
            }
        }
        return mObjectKeyCO;
    }
    
    /**
	 * This method can be used in order to execute a single SQL statement on the
	 * database and get back the <code>ObjectKeyCO</code> which contains the
	 * id and lock id for that row.
	 * 
	 * @param pStatement SQL to be run on the database
	 * @return ObjectKeyCO Contains id and lock id of creted row
	 * @throws Exception An error has occured
	 * @author Stephen Hardy
	 */
	protected static ObjectKeyCO executeRowQuerySimple(String pSQL) 
	throws  Exception{
		ObjectKeyCO mObjectKeyCO = null;
		Connection mConn = null;
		CallableStatement mCallStatement = null;
		StringBuffer mDynamicSQL = null;
		int mId = 0;
		int mLockId = 0;
        
		/*
		 * get the connection and execute the statement returning the ID and
		 * LOCK_FLAG
		 */
		try {
			//  create the dynamic sql
			mDynamicSQL = new StringBuffer();
			mDynamicSQL.append("begin ");
			mDynamicSQL.append(pSQL);
			mDynamicSQL.append("; end;");
			//  get the connection, create callable statement and register outs
			mConn = getConnection();
			mCallStatement = mConn.prepareCall(mDynamicSQL.toString());
			//System.out.println( "Executing following callable statement: "
			//					+mDynamicSQL);
			mCallStatement.executeUpdate();
			//  get the outputs and create the object key
			//mId = mCallStatement.getInt(1);
			//mLockId = mCallStatement.getInt(2);
			//System.out.println("ID: "+mId);
			//System.out.println("Lock Id: "+mLockId);
			mObjectKeyCO = ClientObjectFactory.createObjectKeyCO();
			//mObjectKeyCO.setId(new Integer(mId));
			//mObjectKeyCO.setLockId(new Integer(mLockId));
		} catch (SQLException e){
			throw e;
		} finally{
			if (null != mConn){
				mConn.close();
			}
		}
		return mObjectKeyCO;
	}

    /**
     * This method can be used in order to execute a sql statement from which
     * you required no return values.
     * 
     * @param pStatement SQL to be run on the database
     * @throws Exception An error has occured
     * @author Stephen Hardy
     */
    protected static void execute(String pSQL) 
    throws  Exception{
        Connection mConn = null;
        try {
            mConn = getConnection();
            Statement s = mConn.createStatement();
            //System.out.println( "Executing following SQL: " + pSQL);
            s.execute(pSQL);
        } catch (SQLException e){
            throw e;
        } finally{
            if (null != mConn){
                mConn.close();
            }
        }
    }
    
    /**
     * This method can be used in order to execute SQL on the database an return
     * the resultant <code>ResultSet</code>. The <code>String</code> pFile will
     * be located under the testing directory as specified by the .properties
     * file.
     * 
     * @param pFile <code>String</code> File containing SQL.
     * @return ResultSet Results of the database call.
     * @throws SQLException When an error occured executing the statement
     * @throws ClassNotFoundException When the driver cannot be found
     * @author Stephen Hardy
     */
    public static void executeBatch(String pFile) throws  Exception{
        String mSQL = null;      
        String mFilePath = null;
        //  set the directory
        mFilePath = getProperty(KEY_TESTING_DIR)+pFile;
        //  read the batch file from the dirctory
        //System.out.println("Going to run batch at: "+mFilePath);
        mSQL = readStringFromFile(mFilePath);
        //System.out.println("Running following sql:");
        //System.out.println(mSQL);
        execute(mSQL);
    }    
    
 
	/**
	* This method can be used in order to execute a single SQL statement on the
	* database and get back the <code>ObjectKeyCO</code> which contains the
	* id for that row.
	* 
	* @param pStatement SQL to be run on the database
	* @return ObjectKeyCO Contains id and lock id of creted row
	* @throws Exception An error has occured
	* @author Eileen Abisgold
	*/
   public static ObjectKeyCO executeRowQueryNoLockFlag(String pSQL) 
   throws  Exception{
	   ObjectKeyCO mObjectKeyCO = null;
	   Connection mConn = null;
	   CallableStatement mCallStatement = null;
	   StringBuffer mDynamicSQL = null;
	   int mId = 0;
        
	   /*
		* get the connection and execute the statement returning the ID 
		*/
	   try {
		   //  create the dynamic sql
		   mDynamicSQL = new StringBuffer();
		   mDynamicSQL.append("begin ");
		   mDynamicSQL.append(pSQL);
		   mDynamicSQL.append(" returning ID into ?; end;");
		   //  get the connection, create callable statement and register outs
		   mConn = getConnection();
		   mCallStatement = mConn.prepareCall(mDynamicSQL.toString());
		   mCallStatement.registerOutParameter(1, OracleTypes.INTEGER);
		   //System.out.println( "Executing following callable statement: "
			//				   +mDynamicSQL);
		   mCallStatement.executeUpdate();
		   //  get the outputs and create the object key
		   mId = mCallStatement.getInt(1);
		   //System.out.println("ID: "+mId);
		   mObjectKeyCO = ClientObjectFactory.createObjectKeyCO();
		   mObjectKeyCO.setId(new Integer(mId));
	   } catch (SQLException e){
		   throw e;
	   } finally{
		   if (null != mConn){
			   mConn.close();
		   }
	   }
	   return mObjectKeyCO;
   }
    
 
   /**
   * This method can be used in order to execute a single SQL statement on the
   * database and get back the <code>ObjectKeyCO</code> which contains the
   * lock flag for that row.
   * 
   * @param pStatement SQL to be run on the database
   * @return ObjectKeyCO Contains lock id of created row
   * @throws Exception An error has occured
   * @author Paul McCrady
   */
  protected static ObjectKeyCO executeRowQueryNoId(String pSQL) 
  throws  Exception{
	  ObjectKeyCO mObjectKeyCO = null;
	  Connection mConn = null;
	  CallableStatement mCallStatement = null;
	  StringBuffer mDynamicSQL = null;
	  int mLockId = 0;
        
	  /*
	   * get the connection and execute the statement returning the ID 
	   */
	  try {
		  //  create the dynamic sql
		  mDynamicSQL = new StringBuffer();
		  mDynamicSQL.append("begin ");
		  mDynamicSQL.append(pSQL);
		  mDynamicSQL.append(" returning LOCK_FLAG into ?; end;");
		  //  get the connection, create callable statement and register outs
		  mConn = getConnection();
		  mCallStatement = mConn.prepareCall(mDynamicSQL.toString());
		  mCallStatement.registerOutParameter(1, OracleTypes.INTEGER);
		  //System.out.println( "Executing following callable statement: "
	  		//		  +mDynamicSQL);
		  mCallStatement.executeUpdate();
		  //  get the outputs and create the object key
		  mLockId = mCallStatement.getInt(1);
		  //System.out.println("LOCK_FLAG: "+mLockId);
		  mObjectKeyCO = ClientObjectFactory.createObjectKeyCO();
		  mObjectKeyCO.setLockId(new Integer(mLockId));
	  } catch (SQLException e){
		  throw e;
	  } finally{
		  if (null != mConn){
			  mConn.close();
		  }
	  }
	  return mObjectKeyCO;
  }
    
   /**
     * This method will create a <code>Connection</code> to the database using
     * the below system properties:
     * 
     * testDBServer     - The server name where the database is running.
     * testDBSid        - The SID of the database to connect to.
     * testDBPort       - The database port
     * testDBUser       - The username to connect to the database with
     * testDBPassword   - The password to connect to the database with.
     * 
     * If any of the above system properties are missing then the defaults as
     * specified by the below will be used:
     * 
     * <code>DRIVER</code>
     * <code>SERVER</code>
     * <code>PORT</code>
     * <code>SID</code>
     * <code>DB_USERNAME</code>
     * <code>DB_PASSWORD</code>
     * 
     * @return Connection The database connection.
     * @throws SQLException When an error occured creating the connection
     * @throws ClassNotFoundException When the driver cannot be found
     * @author Stephen Hardy
     */
    protected static Connection getConnection()
    throws  ClassNotFoundException,
            SQLException{
        Connection mConnection = null;
        Class.forName(getDriver());
        mConnection =   DriverManager.getConnection (getUrl(), 
                                                    getUsername(), 
                                                    getPassword());    
        return mConnection;
    }
    
    /**
     * This method will build the database connection url using the server name,
     * port and SID.
     * 
     * @return String The url to use for the database connection.
     * @author Stephen Hardy
     */
    private static String getUrl(){
        String mUrl =   "jdbc:oracle:thin:@" + 
                        getServer() + 
                        ":" + 
                        getPort() + 
                        ":" + 
                        getSid();
        return mUrl;
    }
    
    /**
     * This method can be used in order to read the contents of a specified file
     * and return them in a <code>String</code>. The path to the file must be 
     * relative to the value of <code>testingDir</code> or if that is null the
     * value of <code>DEFAULT_TESTING_DIR</code>.
     * 
     * @param pFile <code>String</code>
     * @return <code>String</code>
     * @throws Exception
     * @author Stephen Hardy
     */
    protected static String readStringFromFile(String pFile)
    throws Exception{
        FileReader mFileReader = null;
        String mResult = null;
        StringBuffer mStringBuffer = null;
        BufferedReader mBufferedReader = null;
        
        try {
            mFileReader = new FileReader(pFile);
            mBufferedReader = new BufferedReader(mFileReader);
            mStringBuffer = new StringBuffer();
            
            String line = null;
            while ((line = mBufferedReader.readLine()) != null) {
                mStringBuffer.append(line);
            }
            
            if(0 < mStringBuffer.length()){
                mResult = mStringBuffer.toString();
            }
            mBufferedReader.close();
        } catch (Exception e) {
            //System.out.println("Error trying to retrieve string from file");
            throw e;
        }
        return mResult;
    }

    /**
     * This method will return the driver name. It will attempt to use any
     * driver name passed in as a system argument first, if none then it will
     * use the specified default.
     * 
     * @return String The name of the driver.
     * @author Stephen Hardy
     */
    protected static String getDriver() {
        _driver = System.getProperty(KEY_DRIVER);
        if (null == _driver){
            _driver = DEFAULT_DRIVER;
        }
        return _driver;
    }

    /**
     * This method will return the password to use. It will attempt to use any
     * password passed in as a system argument first, if none then it will
     * use the specified default.
     * 
     * @return String The password to use for the database connection.
     * @author Stephen Hardy
     */
    protected static String getPassword() {
        _password = System.getProperty(KEY_PASSWORD);
        if (null == _password){
            _password = DEFAULT_PASSWORD;
        }
        return _password;
    }

    /**
     * This method will return the port to use. It will attempt to use any
     * port passed in as a system argument first, if none then it will
     * use the specified default.
     * 
     * @return String The port to use for the database connection.
     * @author Stephen Hardy
     */
    protected static String getPort() {
        _port = System.getProperty(KEY_PORT);
        if (null == _port){
            _port = DEFAULT_PORT;
        }
        return _port;
    }

    /**
     * This method will return the server to use. It will attempt to use any
     * server passed in as a system argument first, if none then it will
     * use the specified default.
     * 
     * @return String The server to use for the database connection.
     * @author Stephen Hardy
     */
    protected static String getServer() {
        _server = System.getProperty(KEY_SERVER);
        if (null == _server){
            _server = DEFAULT_SERVER;
        }
        return _server;
    }

    /**
     * This method will return the SID to use. It will attempt to use any
     * SID passed in as a system argument first, if none then it will
     * use the specified default.
     * 
     * @return String The SID to use for the database connection.
     * @author Stephen Hardy
     */
    protected static String getSid() {
        _sid = System.getProperty(KEY_SID);
        if (null == _sid){
            _sid = DEFAULT_SID;
        }
        return _sid;
    }

    /**
     * This method will return the username to use. It will attempt to use any
     * username passed in as a system argument first, if none then it will
     * use the specified default.
     * 
     * @return String The username to use for the database connection.
     * @author Stephen Hardy
     */
    protected static String getUsername() {
        _username = System.getProperty(KEY_USERNAME);
        if (null == _username){
            _username = DEFAULT_USERNAME;
        }
        return _username;
    }

    /**
     * This method will load the testing properties and load them to the
     * <code>_properties</code> member variable.
     * 
     * @exception <code>FileNotFoundException</code>
     * @exception <code>IOException</code>
     * @author Stephen Hardy
     */
    protected static void initProperties()
    throws  FileNotFoundException,
            IOException{
        _properties = new Properties();
         FileInputStream in = new FileInputStream(getPropertiesFileLocation());
        _properties.load(in);
        in.close();   
    }
    
    /**
     * This method will get a specified property (loaded from the 
     * <code>testing.properties</code> file. If the properties have not
     * previously been loaded then they will be loaded and the property 
     * returned.w
     * 
     * @exception <code>Exception</code>
     * @author Stephen Hardy
     */
    protected static String getProperty(String pKey) throws Exception{
        if (null == _properties){
            initProperties();
        }
        return _properties.getProperty(pKey);
    }
    
    /**
     * This method will get build the location of the testing properties file
     * using the <code>testingDir</code> system property. If the system property
     * is null then it will use <code>DEFAULT_TESTING_DIR</code>.
     * 
     * @return <code>String</code> The location of the properties file.
     * @author Stephen Hardy
     */
    protected static String getPropertiesFileLocation(){
        StringBuffer mLocation = null;
        mLocation = new StringBuffer();
        if (null != System.getProperty(KEY_TESTING_DIR)){
            mLocation.append(System.getProperty(KEY_TESTING_DIR));
        }else{
            mLocation.append(DEFAULT_TESTING_DIR);
        }
        mLocation.append(System.getProperty("file.separator"));
        mLocation.append(PROPERTIES_FILE);
        return mLocation.toString();
    }
    
    public static void main( String[] args ) throws Exception
    {
    	List rowList = retrieveRows( "SELECT * FROM AQCU_ACCOUNT_EXTRACTS" );
    	Iterator rowIter = rowList.iterator();
    	while ( rowIter.hasNext() )
    	{
    		HashMap map = (HashMap)rowIter.next();
    		//System.out.println( map.toString() );		
    	}
    }
   
}
