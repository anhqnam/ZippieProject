/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.database - SQLiteAdapter.java
 * Date create: 2:50:12 PM - Nov 21, 2013 - 2013
 * 
 * 
 */
package com.arisvn.arissmarthiddenbox.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.arisvn.arissmarthiddenbox.encryption.Encryption;
import com.arisvn.arissmarthiddenbox.entity.AppItem;
import com.arisvn.arissmarthiddenbox.entity.CallLogEntry;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.entity.SMSData;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class SQLiteAdapter. This class is used to manipulate with local DB.
 */
public class SQLiteAdapter {

    /** The Constant VERSION_DB. */
    private static final int VERSION_DB = 3;

	/** The Constant MYDATABASE_NAME. */
	public static final String MYDATABASE_NAME = "ARISLAB_SP";
	
	/** The Constant TABLE_HIDDEN_FILE. */
	public static final String TABLE_HIDDEN_FILE = "HIDDEN_FILE";

	/** The Constant KEY_FILE_ID. */
	public static final String KEY_FILE_ID = "_id";
	
	/** The Constant KEY_FILE_NAME. */
	public static final String KEY_FILE_NAME = "name";
	
	/** The Constant KEY_FILE_PATH_FROM. */
	public static final String KEY_FILE_PATH_FROM = "path_from";
	
	/** The Constant KEY_FILE_PATH_NEW. */
	public static final String KEY_FILE_PATH_NEW = "path_new";
	
	/** The Constant KEY_FILE_EXTENSION. */
	public static final String KEY_FILE_EXTENSION = "extention";
	
	/** The Constant KEY_FILE_TYPE. */
	public static final String KEY_FILE_TYPE = "type";
	
	/** The Constant KEY_FILE_SIZE. */
	public static final String KEY_FILE_SIZE = "size";
	
	/** The Constant KEY_FILE_THUMBNAIL. */
	public static final String KEY_FILE_THUMBNAIL = "thumbnail";

	/** The Constant SCRIPT_CREATE_HIDDEN_FILE. */
	private static final String SCRIPT_CREATE_HIDDEN_FILE = "create table if not exists "
			+ TABLE_HIDDEN_FILE
			+ " ("
			+ KEY_FILE_ID
			+ " integer primary key autoincrement, "
			+ KEY_FILE_NAME
			+ " text, "
			+ KEY_FILE_PATH_FROM
			+ " text, "
			+ KEY_FILE_PATH_NEW
			+ " text, "
			+ KEY_FILE_EXTENSION
			+ " text, "
			+ KEY_FILE_TYPE
			+ " integer,"
			+ KEY_FILE_SIZE
			+ " real,"+ KEY_FILE_THUMBNAIL + " blob)";

	/** The Constant columnHiddenFile. */
	private static final String[] columnHiddenFile = { KEY_FILE_ID,
			KEY_FILE_NAME, KEY_FILE_PATH_FROM, KEY_FILE_PATH_NEW,
			KEY_FILE_EXTENSION,KEY_FILE_TYPE, KEY_FILE_SIZE, KEY_FILE_THUMBNAIL };


    /** The Constant TABLE_HIDDEN_SMS. */
    public static final String TABLE_HIDDEN_SMS = "HIDDEN_SMS";

    /** The Constant KEY_SMS_ID. */
    public static final String KEY_SMS_ID = "_id";

    /** The Constant KEY_SMS_BODY. */
    public static final String KEY_SMS_BODY = "body";

    /** The Constant KEY_SMS_NUMBER. */
    public static final String KEY_SMS_NUMBER = "number";

    /** The Constant KEY_SMS_DATETIME. */
    public static final String KEY_SMS_DATETIME = "datetime";

    /** The Constant KEY_SMS_TYPE. */
    public static final String KEY_SMS_TYPE = "type";

    /** The Constant KEY_SMS_READ_TYPE. */
    public static final String KEY_SMS_READ_TYPE = "read";
    public static final String KEY_SMS_THREAD_ID = "thread_id";

    /** The Constant SCRIPT_CREATE_HIDDEN_SMS. */
    public static final String SCRIPT_CREATE_HIDDEN_SMS = "create table if not exists "
            + TABLE_HIDDEN_SMS
            + " ("
            + KEY_SMS_ID
            + " integer primary key autoincrement, "
            + KEY_SMS_BODY
            + " text, "
            + KEY_SMS_NUMBER
            + " text, "
            + KEY_SMS_THREAD_ID
            + " text, "
            + KEY_SMS_DATETIME
            + " text, "
            + KEY_SMS_READ_TYPE
            + " integer, "
            + KEY_SMS_TYPE
            + " text)";

    /** The Constant TABLE_HIDDEN_CALLLOG. */
    public static final String TABLE_HIDDEN_CALLLOG = "callLog";
    public static final String TABLE_HIDDEN_APP = "hidden_app_table";
    public static final String HIDDEN_PACKAGE_NAME = "package_name";

    /** The Constant KEY_CALLLOG_ID. */
    public static final String KEY_CALLLOG_ID = "_id";

    /** The Constant KEY_CALLLOG_NUMBER. */
    public static final String KEY_CALLLOG_NUMBER = "number";

    /** The Constant KEY_CALLLOG_DATETIME. */
    public static final String KEY_CALLLOG_DATETIME = "datetime";

    /** The Constant KEY_CALLLOG_TYPE. */
    public static final String KEY_CALLLOG_TYPE = "type";
    public static final String KEY_CALLLOG_DURATION = "duration";
    /** The Constant SCRIPT_CREATE_HIDDEN_CALLLOG. */
    public static final String SCRIPT_CREATE_HIDDEN_CALLLOG = "create table if not exists "
            + TABLE_HIDDEN_CALLLOG
            + " ("
            + KEY_CALLLOG_ID
            + " integer primary key autoincrement, "
            + KEY_CALLLOG_NUMBER
            + " text, "
            + KEY_CALLLOG_DATETIME
            + " text,"
            + KEY_CALLLOG_DURATION
            + " text,"
            + KEY_CALLLOG_TYPE
            + " integer)";
    /** The Constant SCRIPT_CREATE_HIDDEN_CALLLOG. */
    public static final String SCRIPT_CREATE_HIDDEN_APP = "create table if not exists "
            + TABLE_HIDDEN_APP
            + " ("
            + KEY_CALLLOG_ID
            + " integer primary key autoincrement,"+HIDDEN_PACKAGE_NAME+" text)";

    /** The sq lite helper. */
    private SQLiteHelper sqLiteHelper;

    /** The sq lite database. */
    private SQLiteDatabase sqLiteDatabase;

	/** The context. */
	private Context context;

	/**
	 * Instantiates a new sQ lite adapter.
	 *
	 * @param c the c
	 */
	public SQLiteAdapter(Context c) {
		context = c;
		open();
	}

	
	/**
	 * Check Database is open or close.
	 *
	 * @return true, if is ready
	 */
	public boolean isReady() {
		if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Open Database when start app.
	 *
	 * @return the sQ lite adapter
	 * @throws SQLException the sQL exception
	 */
	public SQLiteAdapter open() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
				VERSION_DB);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	/**
	 * Close Database.
	 */
	public void close() {
		sqLiteHelper.close();
	}

	/**
	 * Insert file into Database.
	 *
	 * @param obj the obj
	 * @return the long
	 */
	public long insertFile(FileItem obj) {
		return sqLiteDatabase.insert(TABLE_HIDDEN_FILE, null,
				getFileValues(obj));
	}

	/**
	 * Delete file in Database.
	 *
	 * @param obj the obj
	 * @return the int
	 */
	public int deleteFile(FileItem obj) {
		return sqLiteDatabase.delete(TABLE_HIDDEN_FILE,
				KEY_FILE_ID + "=" + obj.getId(), null);
	}

	/**
	 * Get files from Database.
	 *
	 * @param type the type
	 * @return the all file
	 */
	public ArrayList<FileItem> getAllFile(int type) {
		ArrayList<FileItem> list = new ArrayList<FileItem>();
		Cursor cursor = sqLiteDatabase.query(TABLE_HIDDEN_FILE,
				columnHiddenFile, KEY_FILE_TYPE+"=?", new String[]{String.valueOf(type)}, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					FileItem obj = getFileItem(cursor);
					list.add(obj);
				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		return list;
	}

	
	/**
	 * Get each item base on cursor.
	 *
	 * @param cursor the cursor
	 * @return the file item
	 */
	private FileItem getFileItem(Cursor cursor) {
		FileItem obj = new FileItem();
		obj.setId(cursor.getInt(cursor.getColumnIndex(KEY_FILE_ID)));
		obj.setName(cursor.getString(cursor.getColumnIndex(KEY_FILE_NAME)));
		obj.setPathFrom(cursor.getString(cursor.getColumnIndex(KEY_FILE_PATH_FROM)));
		obj.setPathNew(cursor.getString(cursor.getColumnIndex(KEY_FILE_PATH_NEW)));
		obj.setExtension(cursor.getString(cursor.getColumnIndex(KEY_FILE_EXTENSION)));
		obj.setType(cursor.getInt(cursor.getColumnIndex(KEY_FILE_TYPE)));
		obj.setSize(cursor.getLong(cursor.getColumnIndex(KEY_FILE_SIZE)));
		byte[] byteArray = cursor.getBlob(cursor.getColumnIndex(KEY_FILE_THUMBNAIL));
		  if(byteArray != null)
		  obj.setThumbnail(byteArray);
		return obj;
	}

    /**
     * Gets the call log.
     *
     * @param id the id
     * @return the call log
     */
    public CallLogEntry getCallLog(int id) {
        Cursor cursor = sqLiteDatabase.query(TABLE_HIDDEN_CALLLOG,
                null, KEY_CALLLOG_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);
        CallLogEntry obj = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    obj = getCallLogItem(cursor);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return obj;
    }

    /**
     * Gets the all call log.
     *
     * @return the all call log
     */
    public ArrayList<CallLogEntry> getAllCallLog() {
        ArrayList<CallLogEntry> list = new ArrayList<CallLogEntry>();
        Cursor cursor = sqLiteDatabase.query(TABLE_HIDDEN_CALLLOG,null, null,null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    CallLogEntry obj = getCallLogItem(cursor);
                    list.add(obj);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return list;
    }

    /**
     * Gets the call log item.
     *
     * @param cursor the cursor
     * @return the call log item
     */
    private CallLogEntry getCallLogItem(Cursor cursor) {
        // TODO Auto-generated method stub
        CallLogEntry obj = new CallLogEntry();
        obj.setId(cursor.getString(cursor.getColumnIndex(KEY_CALLLOG_ID)));
        obj.setNumber(cursor.getString(cursor.getColumnIndex(KEY_CALLLOG_NUMBER)));
        obj.setDate(cursor.getString(cursor.getColumnIndex(KEY_CALLLOG_DATETIME)));
        obj.setType(cursor.getString(cursor.getColumnIndex(KEY_CALLLOG_TYPE)));
        obj.setDuration(cursor.getString(cursor.getColumnIndex(KEY_CALLLOG_DURATION)));
        return obj;
    }

    /**
     * Insert call log.
     *
     * @param obj the obj
     * @return the long
     */
    public long insertCallLog(CallLogEntry obj) {
        return sqLiteDatabase.insert(TABLE_HIDDEN_CALLLOG, null,
                getCallLogValues(obj));
    }

    /**
     * Gets the call log values.
     *
     * @param obj the obj
     * @return the call log values
     */
    private ContentValues getCallLogValues(CallLogEntry obj) {
        // TODO Auto-generated method stub
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CALLLOG_NUMBER, obj.getNumber());
        contentValues.put(KEY_CALLLOG_DATETIME, obj.getDate());
        contentValues.put(KEY_CALLLOG_TYPE, obj.getType());
        contentValues.put(KEY_CALLLOG_DURATION, obj.getDuration());
        return contentValues;
    }


    /**
     * Delete sms.
     *
     * @param obj the obj
     * @return the int
     */
    public int deleteCallLog(CallLogEntry obj) {
        return sqLiteDatabase.delete(TABLE_HIDDEN_CALLLOG,
                KEY_CALLLOG_ID+ "=" + obj.getId(), null);
    }

    /**
     * Gets the all smsinbox.
     *
     * @return the all smsinbox
     */
    public ArrayList<SMSData> getAllSMS() {
        ArrayList<SMSData> list = new ArrayList<SMSData>();
        Cursor cursor = sqLiteDatabase.query(TABLE_HIDDEN_SMS,null, null,null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    SMSData obj = getSMSItem(cursor);
                    list.add(obj);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return list;
    }

    public ArrayList<SMSData> getAllSMSGroupByPhoneNumber() {
        ArrayList<SMSData> list = new ArrayList<SMSData>();
        String query = "select * from "+TABLE_HIDDEN_SMS +"  group by "  +KEY_SMS_NUMBER;
        Cursor cursor = sqLiteDatabase
                .rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    SMSData obj = getSMSItem(cursor);
                    list.add(obj);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return list;
    }


    public ArrayList<SMSData> getAllSMSByNumber(String number) {
        ArrayList<SMSData> list = new ArrayList<SMSData>();
        Cursor cursor = sqLiteDatabase.query(TABLE_HIDDEN_SMS, null,KEY_SMS_NUMBER + "=?", new String[] {
                String.valueOf(number)
        }, null, null, null,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    SMSData obj = getSMSItem(cursor);
                    list.add(obj);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return list;
    }


    /**
     * Insert sms.
     *
     * @param obj the obj
     * @return the long
     */
    public long insertSMS(SMSData obj) {
        return sqLiteDatabase.insert(TABLE_HIDDEN_SMS, null,
                getSMSValues(obj));
    }

    /**
     * Delete sms.
     *
     * @param obj the obj
     * @return the int
     */
    public int deleteSMS(SMSData obj) {
        return sqLiteDatabase.delete(TABLE_HIDDEN_SMS,
                KEY_SMS_ID+ "=" + obj.getId(), null);
    }

    /**
     * Gets the sMS item.
     *
     * @param cursor the cursor
     * @return the sMS item
     */
    private SMSData getSMSItem(Cursor cursor) {
        SMSData obj = new SMSData();
        obj.setId(cursor.getString(cursor.getColumnIndex(KEY_SMS_ID)));
        obj.setBody(cursor.getString(cursor.getColumnIndex(KEY_SMS_BODY)));
        obj.setNumber(cursor.getString(cursor.getColumnIndex(KEY_SMS_NUMBER)));
        obj.setDateTime(cursor.getString(cursor.getColumnIndex(KEY_SMS_DATETIME)));
        obj.setType(cursor.getString(cursor.getColumnIndex(KEY_SMS_TYPE)));
        obj.setRead(cursor.getInt(cursor.getColumnIndex(KEY_SMS_READ_TYPE)));
        obj.setThread_id(cursor.getString(cursor.getColumnIndex(KEY_SMS_THREAD_ID)));
        return obj;
    }
    /**
     * Get file values.
     *
     * @param obj the obj
     * @return the file values
     */
    private ContentValues getFileValues(FileItem obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FILE_NAME, obj.getName());
        contentValues.put(KEY_FILE_PATH_FROM, obj.getPathFrom());
        contentValues.put(KEY_FILE_PATH_NEW, obj.getPathNew());
        contentValues.put(KEY_FILE_EXTENSION, obj.getExtension());
        contentValues.put(KEY_FILE_TYPE, obj.getType());
        contentValues.put(KEY_FILE_SIZE, obj.getSize());
        if (obj.getThumbnail()!=null) {
            contentValues.put(KEY_FILE_THUMBNAIL,
                    obj.getThumbnail());
        }
        return contentValues;
    }

    /**
     * Gets the sMS values.
     *
     * @param obj the obj
     * @return the sMS values
     */
    private ContentValues getSMSValues(SMSData obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_SMS_BODY, obj.getBody());
        contentValues.put(KEY_SMS_NUMBER, obj.getNumber());
        contentValues.put(KEY_SMS_DATETIME, obj.getDateTime());
        contentValues.put(KEY_SMS_TYPE, obj.getType());
        contentValues.put(KEY_SMS_READ_TYPE, obj.getRead());
        contentValues.put(KEY_SMS_THREAD_ID, obj.getThread_id());
        return contentValues;
    }
    /**
     * Checking file exist in Database.
     *
     * @param pathfrom the pathfrom
     * @return true, if is exist file name
     */
    public boolean isExistFileName(String pathfrom){
        Cursor cursor = sqLiteDatabase.query(TABLE_HIDDEN_FILE,
                columnHiddenFile, KEY_FILE_PATH_FROM+"=?", new String[]{pathfrom}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Checks if is encrypted file exist.
     *
     * @param pathNew the path new
     * @return true, if is encrypted file exist
     */
    public boolean isEncryptedFileExist(String pathNew){
        Cursor cursor = sqLiteDatabase.query(TABLE_HIDDEN_FILE,
                columnHiddenFile, KEY_FILE_PATH_NEW+"=?", new String[]{pathNew}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Gets the file thumbnail.
     *
     * @param id the id
     * @return the file thumbnail
     */
    public byte[] getFileThumbnail(int id) {
        Cursor cursor = sqLiteDatabase.query(TABLE_HIDDEN_FILE,
                columnHiddenFile, KEY_FILE_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);
        byte[] byteArray =null ;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    byteArray = cursor.getBlob(cursor.getColumnIndex(KEY_FILE_THUMBNAIL));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return byteArray;
    }


    /**
     * Gets the file.
     *
     * @param id the id
     * @return the file
     */
    public FileItem getFile(int id) {
        Cursor cursor = sqLiteDatabase.query(TABLE_HIDDEN_FILE,
                columnHiddenFile, KEY_FILE_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);
        FileItem obj = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    obj = getFileItem(cursor);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return obj;
    }
	public long insertHiddenApp(String packageName) {
		ContentValues contentValues=new ContentValues();
		contentValues.put(HIDDEN_PACKAGE_NAME, packageName);
		 return sqLiteDatabase.insert(TABLE_HIDDEN_APP, null,
	                contentValues);
	}

	public int unHideApp(String packageName) {
		   return sqLiteDatabase.delete(TABLE_HIDDEN_APP,
	                HIDDEN_PACKAGE_NAME+ "= ?", new String[]{packageName});

	}
	public List<String> getHiddenApps(){
		ArrayList<String> strings=new ArrayList<String>();
		Cursor cursor = sqLiteDatabase.query(TABLE_HIDDEN_APP,
				null, null, null, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					String packageName=cursor.getString(cursor.getColumnIndex(HIDDEN_PACKAGE_NAME));
					strings.add(packageName);
				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		return strings;
	}
	/**
	 * The Class SQLiteHelper.
	 */
	public class SQLiteHelper extends SQLiteOpenHelper {

		/**
		 * Instantiates a new sQ lite helper.
		 *
		 * @param context the context
		 * @param name the name
		 * @param factory the factory
		 * @param version the version
		 */
		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

        /* (non-Javadoc)
         * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SCRIPT_CREATE_HIDDEN_FILE);
            db.execSQL(SCRIPT_CREATE_HIDDEN_SMS);
            db.execSQL(SCRIPT_CREATE_HIDDEN_CALLLOG);
            db.execSQL(SCRIPT_CREATE_HIDDEN_APP);

        }

		/* (non-Javadoc)
		 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			onCreate(db);
			if (newVersion==2) {
				String password=SaveData.getInstance(context).getPassword();
				try {
					SaveData.getInstance(context).setPassword( Encryption.encrypt(context.getPackageName(),
								password));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
            if (newVersion>2) {
                db.execSQL(SCRIPT_CREATE_HIDDEN_SMS);
                db.execSQL(SCRIPT_CREATE_HIDDEN_CALLLOG);
                db.execSQL(SCRIPT_CREATE_HIDDEN_APP);
                SQLiteDatabase	checkdb = SQLiteDatabase.openDatabase(Utils.FOLDER + "/" + SQLiteAdapter.MYDATABASE_NAME, null,
						SQLiteDatabase.OPEN_READWRITE);
				if (checkdb != null) {
					checkdb.execSQL(SQLiteAdapter.SCRIPT_CREATE_HIDDEN_SMS);
					checkdb.execSQL(SQLiteAdapter.SCRIPT_CREATE_HIDDEN_CALLLOG);
					checkdb.execSQL(SQLiteAdapter.SCRIPT_CREATE_HIDDEN_APP);
					checkdb.close();
				}

            }
            if (newVersion>4) {
				   //update from version 2.0. Add thread id column
            	try {
	            	db.execSQL("ALTER TABLE " + TABLE_HIDDEN_SMS + " ADD "+KEY_SMS_THREAD_ID+" TEXT");
	            	SQLiteDatabase	checkdb = SQLiteDatabase.openDatabase(Utils.FOLDER + "/" + SQLiteAdapter.MYDATABASE_NAME, null,
							SQLiteDatabase.OPEN_READWRITE);
					if (checkdb != null) {
		            	checkdb.execSQL("ALTER TABLE " + TABLE_HIDDEN_SMS + " ADD "+KEY_SMS_THREAD_ID+" TEXT");
						checkdb.close();
				}
				} catch (SQLException e) {
					// TODO: handle exception
					Log.e("Tamle", "Update DB fail: "+e.toString());
				}
			}
        }
    }
	public boolean isExistApp( AppItem appItem) {
		// TODO Auto-generated method stub
		   Cursor cursor = sqLiteDatabase.query(TABLE_HIDDEN_APP,
	                null, HIDDEN_PACKAGE_NAME+"=?", new String[]{appItem.getPackageName()}, null, null, null);
	        if (cursor != null) {
	            if (cursor.moveToFirst()) {
	                return true;
	            }else{
	                return false;
	            }
	        }else{
	            return false;
	        }
	}
	public boolean isExistSMS(SMSData smsData) {
		// TODO Auto-generated method stub
		boolean isExist = false;
		Cursor cursor = null;
		try {
			cursor = sqLiteDatabase
					.query(TABLE_HIDDEN_SMS, null, KEY_SMS_DATETIME + " = ? and "
							+ KEY_SMS_NUMBER + " = ?", new String[] {
							smsData.getDateTime(), smsData.getNumber() }, null,
							null, null);
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					isExist = true;
				} else {
					isExist = false;
				}
			} else {
				isExist = false;
			}
		} catch (Exception e) {
			Log.e("", e.toString());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return isExist;

	}
}
