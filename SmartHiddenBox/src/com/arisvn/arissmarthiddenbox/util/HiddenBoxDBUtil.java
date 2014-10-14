/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.util - HiddenBoxDBUtil.java
 * Date create: 2:59:12 PM - Nov 21, 2013 - 2013
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.arisvn.arissmarthiddenbox.database.SQLiteAdapter;
import com.arisvn.arissmarthiddenbox.entity.CallLogEntry;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.entity.SMSData;

// TODO: Auto-generated Javadoc
/**
 * The Class HiddenBoxDBUtil.
 */
public class HiddenBoxDBUtil {
	
	/** The instance. */
	private static HiddenBoxDBUtil instance;
	private static String dbSdcard = Utils.FOLDER + "/" + SQLiteAdapter.MYDATABASE_NAME;
	/**
	 * Gets the single instance of HiddenBoxDBUtil.
	 *
	 * @return single instance of HiddenBoxDBUtil
	 */
	public static HiddenBoxDBUtil getInstance() {
		if (instance == null) {
			instance = new HiddenBoxDBUtil();
		}

		return instance;
	}

	
	

	/**
	 * Check database.
	 * 
	 * @param path
	 *            the path
	 * @return true, if successful
	 */
	public boolean checkDatabase(String path) {
		synchronized (this) {
			boolean result = false;
			SQLiteDatabase checkdb = null;
			try {
				File file = new File(path);
				if (!file.exists()) {
					return false;

				}
				checkdb = SQLiteDatabase.openDatabase(path, null,
						SQLiteDatabase.OPEN_READWRITE);
				if (checkdb != null) {
					checkdb.close();
					result = true;
				}
			} catch (Exception e) {
				new RuntimeException(e);
			} finally {
				if (checkdb != null) {
					checkdb.close();
				}
			}
			return result;
		}
	}

	/**
	 * Check and copy from app db.
	 * 
	 * @param context
	 *            the context
	 * @param localPath
	 *            the copy to
	 * @param databaseName
	 *            the database name
	 */
	public void checkAndCopyDBToApp(Context context, String localPath,
			String databaseName) {
		synchronized (this) {
			boolean dbexist = checkDatabase(localPath);
			if (dbexist) {
				try {
					PackageInfo pInfo;
					pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
					int versionCode = pInfo.versionCode;
					   if (versionCode>2) {
						   Log.d(Utils.TAG, getClass().getName()+"::checkAndCopyDBToApp():: create SMS, CallLog and App table if not exist");
							SQLiteDatabase	checkdb = SQLiteDatabase.openDatabase(localPath, null,
									SQLiteDatabase.OPEN_READWRITE);
							if (checkdb != null) {
								checkdb.execSQL(SQLiteAdapter.SCRIPT_CREATE_HIDDEN_SMS);
								checkdb.execSQL(SQLiteAdapter.SCRIPT_CREATE_HIDDEN_CALLLOG);
								checkdb.execSQL(SQLiteAdapter.SCRIPT_CREATE_HIDDEN_APP);
								checkdb.close();
							}
			            }
					if (versionCode > 4) {
						// update from version 2.0. Add thread id column
						try {
							SQLiteDatabase checkdb = SQLiteDatabase
									.openDatabase(localPath, null,
											SQLiteDatabase.OPEN_READWRITE);
							if (checkdb != null) {
								checkdb.execSQL("ALTER TABLE "
										+ SQLiteAdapter.TABLE_HIDDEN_SMS
										+ " ADD "
										+ SQLiteAdapter.KEY_SMS_THREAD_ID
										+ " TEXT");
								checkdb.close();
							}
						} catch (SQLException e) {
							// TODO: handle exception
						}

					}
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				String appDB = context.getDatabasePath(databaseName)
						.getAbsolutePath();
				String string = appDB.substring(0, appDB.lastIndexOf("/"));
				File databaseDir = new File(string);
				if (!databaseDir.exists()) {
					databaseDir.mkdirs();
				}
				copyDatabase(localPath, appDB);
				SaveData.getInstance(context).setIsCopyLocalDB(false);
			}
		}
	}

	/**
	 * Import local data to app.
	 *
	 * @param dbSource the db source
	 * @param dbDestination the db destination
	 */
	public void copyDatabase(String dbSource, String dbDestination) {
		// TODO Auto-generated method stub
		synchronized (this) {
			SQLiteDatabase checkdb = null;
			try {
				File file = new File(dbSource);
				if (!file.exists()) {
					return;
				}
				checkdb = SQLiteDatabase.openDatabase(dbSource, null,
						SQLiteDatabase.OPEN_READWRITE);
				if (checkdb != null) {
					// import data here
					File file2 = new File(dbDestination);
					if (file2.exists()) {
						file2.delete();
					}
					copyFile(dbSource, dbDestination);
					checkdb.close();
				}
			} catch (Exception e) {
				new RuntimeException(e);
			} finally {
				if (checkdb != null) {
					checkdb.close();
				}
			}
		}

	}

	/**
	 * Copydatabase.
	 * 
	 * @param copyFrom
	 *            the copy from
	 * @param copyTo
	 *            the copy to
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void copyFile(String copyFrom, String copyTo) throws IOException {
		synchronized (this) {
			InputStream myInput = null;
			OutputStream myOutPut = null;
			try {
				File file = new File(copyFrom);
				if (!file.exists()) {
					Log.e("Tamle", "copyFrom file is not exist");
					return;
				}
				myInput = new FileInputStream(file);
				myOutPut = new FileOutputStream(copyTo);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = myInput.read(buffer)) > 0) {
					myOutPut.write(buffer, 0, length);
				}
				myOutPut.flush();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("Tamle", "Copy exception: " + e.toString());
			} finally {
				if (myOutPut != null) {
					myOutPut.close();
				}
				if (myInput != null) {
					myInput.close();
				}

			}
		}
	}
	
	/**
	 * Insert file into Database on Sdcard.
	 *
	 * @param obj the obj
	 * @return the long
	 */
	public long insertFileSdcardDB(FileItem obj) {
		
		SQLiteDatabase checkdb = SQLiteDatabase.openDatabase(dbSdcard, null, SQLiteDatabase.OPEN_READWRITE);
		if (checkdb != null) {
			long result = 	checkdb.insert(SQLiteAdapter.TABLE_HIDDEN_FILE, null,
					getFileValues(obj));
			
			checkdb.close();
			return result;
		}else{
			return -1;
		}
		
	}
    public long insertSMSSdcardDB(SMSData obj) {

        SQLiteDatabase checkdb = SQLiteDatabase.openDatabase(dbSdcard, null, SQLiteDatabase.OPEN_READWRITE);
        if (checkdb != null) {
            long result = checkdb.insert(SQLiteAdapter.TABLE_HIDDEN_SMS, null,getSMSValues(obj));

            checkdb.close();
            return result;
        }else{
            return -1;
        }

    }
    private ContentValues getSMSValues(SMSData obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteAdapter.KEY_SMS_BODY, obj.getBody());
        contentValues.put(SQLiteAdapter.KEY_SMS_NUMBER, obj.getNumber());
        contentValues.put(SQLiteAdapter.KEY_SMS_DATETIME, obj.getDateTime());
        contentValues.put(SQLiteAdapter.KEY_SMS_TYPE, obj.getType());
        contentValues.put(SQLiteAdapter.KEY_SMS_READ_TYPE, obj.getRead());
        contentValues.put(SQLiteAdapter.KEY_SMS_THREAD_ID, obj.getThread_id());
        return contentValues;
    }
    public long insertCallLogSdcardDB(CallLogEntry obj) {

        SQLiteDatabase checkdb = SQLiteDatabase.openDatabase(dbSdcard, null, SQLiteDatabase.OPEN_READWRITE);
        if (checkdb != null) {
            long result = checkdb.insert(SQLiteAdapter.TABLE_HIDDEN_CALLLOG, null,getCallLogValues(obj));

            checkdb.close();
            return result;
        }else{
            return -1;
        }

    }
    private ContentValues getCallLogValues(CallLogEntry obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteAdapter.KEY_CALLLOG_DATETIME, obj.getDate());
        contentValues.put(SQLiteAdapter.KEY_CALLLOG_DURATION, obj.getDuration());
        contentValues.put(SQLiteAdapter.KEY_CALLLOG_NUMBER, obj.getNumber());
        contentValues.put(SQLiteAdapter.KEY_CALLLOG_TYPE, obj.getType());
        return contentValues;
    }
    /**
     * Delete file in Database on Sdcard.
     *
     * @param obj the obj
     * @return the int
     */
    public int deleteFile(FileItem obj) {

        SQLiteDatabase checkdb = SQLiteDatabase.openDatabase(dbSdcard, null, SQLiteDatabase.OPEN_READWRITE);
        if (checkdb != null) {
            int result = checkdb.delete(SQLiteAdapter.TABLE_HIDDEN_FILE,
                    SQLiteAdapter.KEY_FILE_ID + "=" + obj.getId(), null);

            checkdb.close();
            return result;
        }else{
            return -1;
        }

    }
    public int deleteSMS(SMSData obj) {

        SQLiteDatabase checkdb = SQLiteDatabase.openDatabase(dbSdcard, null, SQLiteDatabase.OPEN_READWRITE);
        if (checkdb != null) {
            int result = checkdb.delete(SQLiteAdapter.TABLE_HIDDEN_SMS,
                    SQLiteAdapter.KEY_SMS_ID + "=" + obj.getId(), null);

            checkdb.close();
            return result;
        }else{
            return -1;
        }

    }
    public int deleteCallLog(CallLogEntry obj) {

        SQLiteDatabase checkdb = SQLiteDatabase.openDatabase(dbSdcard, null, SQLiteDatabase.OPEN_READWRITE);
        if (checkdb != null) {
            int result = checkdb.delete(SQLiteAdapter.TABLE_HIDDEN_CALLLOG,
                    SQLiteAdapter.KEY_SMS_ID + "=" + obj.getId(), null);

            checkdb.close();
            return result;
        }else{
            return -1;
        }

    }
    /**
     * Get file values.
     *
     * @param obj the obj
     * @return the file values
     */
    private ContentValues getFileValues(FileItem obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteAdapter.KEY_FILE_NAME, obj.getName());
        contentValues.put(SQLiteAdapter.KEY_FILE_PATH_FROM, obj.getPathFrom());
        contentValues.put(SQLiteAdapter.KEY_FILE_PATH_NEW, obj.getPathNew());
        contentValues.put(SQLiteAdapter.KEY_FILE_EXTENSION, obj.getExtension());
        contentValues.put(SQLiteAdapter.KEY_FILE_TYPE, obj.getType());
        contentValues.put(SQLiteAdapter.KEY_FILE_SIZE, obj.getSize());
        if (obj.getThumbnail()!=null) {
            contentValues.put(SQLiteAdapter.KEY_FILE_THUMBNAIL,
                    obj.getThumbnail());
        }
        return contentValues;
    }

}
