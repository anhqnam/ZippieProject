/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.util - Utils.java
 * Date create: 2:46:43 PM - Nov 8, 2013 - 2013
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.StreamCorruptedException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.encryption.Encryption;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.entity.CallLogEntry;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.entity.SMSData;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

// TODO: Auto-generated Javadoc
/**
 * The Class Utils.
 * 
 * @author trungkd
 */
@SuppressLint("NewApi")
public class Utils {

    /** The folder. */
    public static String FOLDER = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/Encrypte";

    /** The sdcard. */
    public static String SDCARD = Environment.getExternalStorageDirectory()
            .getAbsolutePath();

    /** The restore folder. */
    public static String RESTORE_FOLDER = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/Encrypte/restore";

    /** The audio. */
    public static String _AUDIO = "/audio";

    /** The video. */
    public static String _VIDEO = "/video";

    /** The photo. */
    public static String _PHOTO = "/photo";

    public static String _MESSAGE = "/message";

    

    

    /** The action category. */
    public static String ACTION_CATEGORY = "ACTION_CATEGORY";

    public static String ACTION_LOCKAPP = "ACTION_LOCKAPP";

    /** The fragment photo. */
    public static String FRAGMENT_PHOTO = "FRAGMENT_PHOTO";

    /** The fragment video. */
    public static String FRAGMENT_VIDEO = "FRAGMENT_VIDEO";

    /** The fragment audio. */
    public static String FRAGMENT_AUDIO = "FRAGMENT_AUDIO";

    /** The fragment message. */
    public static String FRAGMENT_SMS = "FRAGMENT_MESSAGE";
    /** The fragment message. */
    public static String FRAGMENT_CALL_LOG = "FRAGMENT_CALL_LOG";
    /** The fragment message. */
    public static String FRAGMENT_APP = "FRAGMENT_APP";
    /** The fragment picker. */
    public static String FRAGMENT_PICKER = "FRAGMENT_PICKER";

    /** The fragment category. */
    public static String FRAGMENT_CATEGORY = "FRAGMENT_CATEGORY";

    /** The fragment picker. */
    public static String SMS_PICKER = "SMS_PICKER";

    /** The sms detail. */
    public static String SMS_DETAIL = "SMS_DETAIL";

    /** The fragment picker. */
    public static String CALL_PICKER = "CALL_PICKER";

    /** The fragment picker. */
    public static String APP_PICKER = "APP_PICKER";

    public static String PHOTO_DETAIL_FRAGMENT = "PHOTO_DETAIL_FRAGMENT";
    public static String VIDEO_DETAIL_FRAGMENT = "VIDEO_DETAIL_FRAGMENT";


    /** The Constant TYPE. */
    public static final String TYPE = "TYPE";

    /** The Constant TYPE_PHOTO. */
    public static final int TYPE_PHOTO = 0;

    /** The Constant TYPE_VIDEO. */
    public static final int TYPE_VIDEO = 1;

    /** The Constant TYPE_AUDIO. */
    public static final int TYPE_AUDIO = 2;

    public static final int TYPE_MESSAGE = 3;

    /** The Constant TYPE_THUMBNAIL. */
    public static final int TYPE_THUMBNAIL = 6;

	/** The Constant TYPE_THUMBNAIL. */
	public static final int TYPE_CONTACT = 7;

    /** The Constant IMPORT. */
    public static final int IMPORT = 4;

    /** The Constant EXPORT. */
    public static final int EXPORT = 5;

    /** The Constant FILE_MODE. */
    public static final String FILE_MODE = "file_mode";

    /** The Constant FILE_NAME. */
    public static final String FILE_NAME = "file_name";

    /** The Constant REQUEST_FILE. */
    public static final int REQUEST_FILE = 3;

    /** The Constant SELECT_FILE_DIALOG. */
    public static final String SELECT_FILE_DIALOG = "select file dialog";

    /** The Constant BACKUP_SQL. */
    public static final String BACKUP_SQL = "backup_file.txt";

    /** The tag. */
    public static String TAG = "debug";

    public static final String ENCRYPT_EXTENSION = ".ashb";

    public static final String FILE_PATH = "file_path";

    public static final String FILE_ITEM = "FILE_ITEM";

    public static final String FILE_POSITION = "FILE_POSITION";

    public static final String SELECTED = "SELECTED";

    // sms message
    public static String ID = "_id";

    public static String DATE = "date";

    public static String NUMBBER = "address";
    public static String BODY = "body";

    public static String TYPE_SMS = "type";

    public static String FORMAT_DATE_SMS = "dd-MM-yyyy, HH:mm:ss ";

    public static String URI_SMS = "content://sms";

    public static String TYPE_INBOX = "inbox";

    public static String TYPE_SENT = "sent";

    public static String TYPE_DRAFT = "draft";

    public static String TYPE_SMS_INBOX = "1";

    public static String TYPE_SMS_SENT = "2";

    public static String TYPE_SMS_DRAFT = "3";

    // public static final int TYPE_FILE = -1;
    /** The Constant DATE. */
    public final static String CALL_DATE = Calls.DATE;

    /** The Constant NAME. */
    public final static String CALL_LOG_NAME = Calls.CACHED_NAME;

    /** The Constant NUMBER. */
    public final static String CALL_LOG_NUMBER = Calls.NUMBER;

    /** The Constant TYPE. */
    public final static String CALL_TYPE = Calls.TYPE;

    /** The Constant _ID. */
    public final static String _ID = Calls._ID;

    /** The Constant DURATION. */
    public final static String CALL_LOG_DURATION = "duration";

    /** The Constant ADDRESS. */
    public final static String SMS_ADDRESS = "address";

    /** The Constant PERSON. */
    public final static String SMS_PERSON = "person";

    /** The Constant BODY. */
    public final static String SMS_BODY = "body";

    /** The Constant SMS_READ. */
    public static final String SMS_READ = "read";

    public static final String SMS_DATE = "date";

    public static final String SMS_TYPE = "type";

    //open intent media
    public static final String URI_TYPE_IMAGE = "image/*";

    public static final String URI_TYPE_VIDEO = "video/*";

    public static final String URI_TYPE_AUDIO = "audio/*";
    public static final String URI_TYPE_SMS = "vnd.android-dir/mms-sms";
    /** The Constant CALL_LOGS. */
    public static final String[] CALL_LOGS = new String[] {
        _ID,
        CALL_LOG_NAME, CALL_LOG_NUMBER, CALL_LOG_DURATION, CALL_TYPE, CALL_DATE
    };

    private static final String SMS_THREAD_ID = "thread_id";
    /** The Constant SMS. */
    public static final String[] SMS = new String[] {
        _ID, SMS_PERSON,
        SMS_ADDRESS, SMS_BODY, TYPE, CALL_DATE, SMS_READ,SMS_THREAD_ID
    };

    /**
     * The Enum Methods.
     */
    public enum CATEGORY_ID {
        PHOTO, AUDIO, VIDEO, SMS, CALL_LOG, CAMERA, APP

    }

    public static final String _DATA = MediaStore.Images.Media.DATA;

    /** The Constant _ID. */
    public static final String MEDIA_ID = MediaStore.Images.Media._ID;

    /** The Constant _DISPLAY_NAME. */
    public static final String _DISPLAY_NAME = MediaStore.Images.Media.DISPLAY_NAME;

    /** The Constant _MIME_TYPE. */
    public static final String _MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;

    /** The Constant _SIZE. */
    public static final String _SIZE = MediaStore.Images.Media.SIZE;

    public static final int TYPE_CALLLOG =5;

    public static final String CALL_DETAIL = "CalllogDetail";

    public static final String POSISITON_SMS = "position";

    public static final String NUMBER_SMS = "number";

    public static final int OPEN_SMS_DETAIL = 10;

    public static final String FRAGMENT_RESET_PASSWORD = "reset_pass";

	public static final String SORT_SIZE_ENABLE = "SORT_SIZE_ENABLE";

	public static final String SORT_DATE_ENABLE = "SORT_DATE_ENABLE";

    public static final String SORT_NAME_ENABLE = "SORT_NAME_ENABLE";
    public static final String CHARACTER_PHONE_NUMBER = "+";

    private static final String DEFALT_REGION = "CH";

    private static final int LENGTH_PHONE_NUMBER = 5;
    private static final String SMS_ID = "_id";

    /**
     * Encrypt file.
     * 
     * @param context the context
     * @param filePath the file path
     * @return true, if successful
     */
    public static boolean encrypt(Context context, String filePath,
            int bufferLenght) {
        if (Utils.isSDCardExist()) {
            try {
                Encryption encryption = new Encryption(context);
                // encrypt
                RandomAccessFile localRandomAccessFile = new RandomAccessFile(filePath, "rw");

                byte[] plainByteArray = new byte[(int) Math.min(localRandomAccessFile.length(),
                        bufferLenght)];
                localRandomAccessFile.seek(0L);
                localRandomAccessFile.readFully(plainByteArray);
                byte[] encryptedByte = encryption.getRequiredEncryptedBytesFromByte(plainByteArray);
                if (encryptedByte == null) {
                    Log.e("Tamle", "encrypt error: ");
                    localRandomAccessFile.close();
                } else {
                    localRandomAccessFile.seek(0L);
                    localRandomAccessFile.write(encryptedByte);
                    localRandomAccessFile.close();
                }
                return true;
            } catch (Exception e) {
                // TODO: handle exception
                return false;
            }
        } else {
            Log.e("Tamle", "encrypt error: there is no SDCard");
            return false;
        }

    }

    /**
     * Decrypt.
     * 
     * @param context the context
     * @param filePath the file path
     * @return true, if successful
     */
    public static boolean decrypt(Context context, String filePath) {
        try {
            int byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_PHOTO;

            if (filePath.startsWith(FOLDER + _AUDIO)) {
                byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_AUDIO;

            } else if (filePath.startsWith(FOLDER + _PHOTO)) {
                byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_PHOTO;

            } else if (filePath.startsWith(FOLDER + _VIDEO)) {
                byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_VIDEO;

            }

            Encryption encryption = new Encryption(context);
            // decrypt
            RandomAccessFile localRandomAccessFile2 = new RandomAccessFile(filePath, "rw");
            byte[] encryptedByteArray = new byte[(int) Math.min(localRandomAccessFile2.length(),
                    byte_to_encrypte)];
            localRandomAccessFile2.seek(0L);
            localRandomAccessFile2.read(encryptedByteArray);
            byte[] decryptedByteArray = encryption
                    .getRequiredDecryptedBytesFromByte(encryptedByteArray);
            if (decryptedByteArray == null) {
                Log.e(Utils.TAG, "decrypt error: ");
                localRandomAccessFile2.close();
            } else {
                localRandomAccessFile2.seek(0L);
                localRandomAccessFile2.write(decryptedByteArray);
                localRandomAccessFile2.close();
            }
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(Utils.TAG, "Utils: encrypt: " + e.toString());
            return false;
        }
    }

    /**
     * Create folder stores file.
     * 
     * @param path the path
     */
    public static void makeFolder(String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists())
            file.mkdirs();
    }

    /**
     * Create thumbnail base on ID param.
     * 
     * @param context the context
     * @param id the id
     * @param type the type
     * @return the bitmap
     */
    public static Bitmap createThumbnail(Context context, long id, int type) {
        if (type == TYPE_AUDIO) {
            return createThumbnailForAudio(context, id);
        } else {
            return createThumbnailForImage(context, id);
        }
    }

    /**
     * Create image thumbnail.
     * 
     * @param context the context
     * @param id the id
     * @return the bitmap
     */
    private static Bitmap createThumbnailForImage(Context context, long id) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bmp = MediaStore.Images.Thumbnails.getThumbnail(
                context.getContentResolver(), id,
                MediaStore.Images.Thumbnails.MICRO_KIND, bmpFactoryOptions);
        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
                / (float) 100);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                / (float) 100);
        // If both of the ratios are greater than 1, one of the sides of
        // the image is greater than the screen
        if (heightRatio > 1 && widthRatio > 1) {
            if (heightRatio > widthRatio) {
                // Height ratio is larger, scale according to it
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                // Width ratio is larger, scale according to it
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }
        // Decode it for real
        bmpFactoryOptions.inJustDecodeBounds = false;
        bmpFactoryOptions.inPreferredConfig = Config.ARGB_8888;
        bmpFactoryOptions.inPurgeable = true;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        bmpFactoryOptions.inDensity = dm.densityDpi;
        bmp = MediaStore.Images.Thumbnails.getThumbnail(
                context.getContentResolver(), id,
                MediaStore.Images.Thumbnails.MICRO_KIND, bmpFactoryOptions);
        return bmp;
    }

    /**
     * Create audio thumbnail.
     * 
     * @param context the context
     * @param id the id
     * @return the bitmap
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    private static Bitmap createThumbnailForAudio(Context context, long id) {
        try {
			Bitmap bitmap = null;
			Cursor cursor = context.getContentResolver().query(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
					new String[] { MediaStore.Audio.Media.ALBUM_ID },
					Utils.MEDIA_ID + " = ?", new String[]{""+id}, null);
			if (cursor != null && cursor.moveToFirst()) {
				long albumId = cursor
						.getLong(cursor
								.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
				cursor.close();
				Uri sArtworkUri = Uri
						.parse("content://media/external/audio/albumart");
				Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri,
						albumId);
				try {
					bitmap = MediaStore.Images.Media.getBitmap(
							context.getContentResolver(), albumArtUri);
					bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

				} catch (FileNotFoundException exception) {
					exception.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {

			}

			return bitmap;

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets the bitmap from id.
     * 
     * @param context the context
     * @param id the id
     * @return the bitmap from id
     */
    public static Bitmap getBitmapFromId(Context context, int id) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Config.ARGB_8888;
        opts.inPurgeable = true;
        opts.inSampleSize = 1;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        opts.inDensity = dm.densityDpi;
        Resources res = context.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, id, opts);
        return bmp;
    }

    /**
     * Get the bitmap base on type param.
     * 
     * @param context the context
     * @param type the type
     * @return the bitmap via type
     */
    public static Bitmap getBitmapViaType(Context context, int type) {
        Bitmap bmp = null;
        switch (type) {
            case Utils.TYPE_AUDIO:
                bmp = Utils.getBitmapFromId(context, R.drawable.ic_no_audio);
                break;
            case Utils.TYPE_VIDEO:
                bmp = Utils.getBitmapFromId(context, R.drawable.ic_no_video);
                break;
            case Utils.TYPE_PHOTO:
                bmp = Utils.getBitmapFromId(context, R.drawable.ic_no_image);
                break;
        }
        return bmp;
    }

    /**
     * Delete directory.
     * 
     * @param file the file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void deleteDirectory(File file) throws IOException {
        if (file.isDirectory()) {
            // directory is empty, then delete it
            if (file.list().length == 0) {
                Log.d(Utils.TAG, "Restore folder is empty");
            } else {
                // list all the directory contents
                String files[] = file.list();
                for (String temp : files) {
                    // construct the file structure
                    File fileDelete = new File(file, temp);
                    Log.d(Utils.TAG, " Remove " + fileDelete.getPath());
                    // recursive delete
                    if (!fileDelete.isFile())
                        continue;
                    if (!fileDelete.delete())
                        Log.d(Utils.TAG,
                                "Couldn't remove " + fileDelete.getPath());
                }
            }
        }
    }

    /**
     * Empty restore folder.
     * 
     * @param dirName the dir name
     */
    public void emptyRestoreFolder(String dirName) {
        // TODO Auto-generated method stub
        if (isSDCardExist()) {
            File imagesDir = new File(dirName);
            try {
                deleteDirectory(imagesDir);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if is sD card exist.
     * 
     * @return true, if is sD card exist
     */
    public static boolean isSDCardExist() {
        return isExternalStorageAvailable() && !isExternalStorageReadOnly();
    }

    /**
     * Checks if is external storage available.
     * 
     * @return true, if is external storage available
     */
    public static boolean isExternalStorageAvailable() {
        boolean state = false;
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            state = true;
        }
        return state;
    }

    /**
     * Checks if is external storage read only.
     * 
     * @return true, if is external storage read only
     */
    public static boolean isExternalStorageReadOnly() {
        boolean state = false;
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            state = true;
        }
        return state;
    }

    /**
     * Read object.
     * 
     * @param file the file
     * @return the list
     */
    @SuppressWarnings("unchecked")
    public static List<? extends BaseItemEntity> readObject(String file) {
        List<FileItem> obj = null;
        FileInputStream fi;
        try {
            fi = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fi);
            obj = (List<FileItem>) ois.readObject();
            fi.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.e(Utils.TAG, "readObject exception: " + e.toString());
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            Log.e(Utils.TAG, "readObject exception: " + e.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e(Utils.TAG, "readObject exception: " + e.toString());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            Log.e(Utils.TAG, "readObject exception: " + e.toString());
        }

        return obj;
    }

    /**
     * Write object.
     * 
     * @param file the file
     * @param objItems the obj items
     */
    public static void writeObject(String file, List<? extends BaseItemEntity> objItems) {
        FileOutputStream fo;
        try {
            fo = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fo);
            oos.writeObject(objItems);
            oos.flush();
            fo.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.e(Utils.TAG, "writeObject exception: " + e.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e(Utils.TAG, "writeObject exception: " + e.toString());
        }

    }
    public static void setTypeface(Context context, TextView text, String fontName) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), fontName);
        text.setTypeface(font);
    }

    /**
     * Enable disable view.
     * 
     * @param view the view
     * @param enabled the enabled
     */
    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    /**
     * Gets the byte array.
     * 
     * @param bmp the bmp
     * @return the byte array
     */
    public static byte[] getByteArray(Bitmap bmp) {
        if (bmp != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bmp.recycle();
            bmp = null;
            return stream.toByteArray();
        } else {
            return null;
        }
    }

    public static void onRestoreCallLog(Context context, List<Object> list) {
        for (int i = 0; i < list.size(); i++) {
            CallLogEntry callLog = (CallLogEntry) list.get(i);
            Cursor cursor = context.getContentResolver().query(Calls.CONTENT_URI,
                    null, CALL_DATE + " = ? and " + CALL_LOG_NUMBER + " = ?",
                    new String[] {
                            callLog.getDate(), callLog.getNumber()
                    }, null);
            if (cursor == null || (cursor != null && cursor.getCount() == 0)) {
                // CallLog not exist. Insert into db
                ContentValues values = new ContentValues();
                values.put(CALL_LOG_NUMBER, callLog.getNumber());
                values.put(CALL_DATE, callLog.getDate());
                values.put(CALL_LOG_DURATION, callLog.getDuration());
                values.put(CALL_TYPE, callLog.getType());
                values.put(CALL_LOG_NAME, callLog.getName());
                context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
            } else {
                android.util.Log.e("Tamle", "Calllog from contact: " + callLog.getNumber()
                        + " at the time: " + callLog.getDate() + " exist");
                if (cursor!=null) {
                    cursor.close();
				}
            }
        }
    }

    /**
     * Cursor message.
     * 
     * @param context the context
     * @return the cursor
     */
    public static Cursor cursorMessage(Context context) {
        Cursor cursor = context.getContentResolver().query(Uri.parse(Utils.URI_SMS), SMS, null,
                null, CALL_DATE + " DESC");
        return cursor;
    }
    @SuppressLint("SimpleDateFormat")
    public static String getDatetime(long time) {
        Date d = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(Utils.FORMAT_DATE_SMS);
        String FormattedDate = sdf.format(d).toString();
        return FormattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static List<CallLogEntry> getCallLog(Activity activity) {
        List<CallLogEntry> listCallLogs = new ArrayList<CallLogEntry>();
        Cursor managedCursor = null ;
        try {
             managedCursor = activity.getContentResolver().query(CallLog.Calls.CONTENT_URI, CALL_LOGS, null,
                    null, CALL_DATE + " DESC");
            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            int idIndex= managedCursor.getColumnIndex(CallLog.Calls._ID);
            while (managedCursor.moveToNext()) {
                CallLogEntry callLog = new CallLogEntry();
                int id=managedCursor.getInt(idIndex);
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                String callDuration = managedCursor.getString(duration);
                int dircode = Integer.parseInt(callType);
                callLog.setId(String.valueOf(id));
                callLog.setNumber(phNumber);
                callLog.setDate(callDate);
                callLog.setType(String.valueOf(dircode));
                callLog.setDuration(callDuration);
                String contactName=Utils.getContactName(activity, phNumber);
                callLog.setName(contactName);
                listCallLogs.add(callLog);
            }
        } catch (Exception e) {
            // TODO: handle exception
            return listCallLogs;
        } finally{
        	if (managedCursor!=null) {
				managedCursor.close();
			}
        }
        return listCallLogs;
    }

    public static List<SMSData> getListSMSMessage(Activity activity) {
        // TODO Auto-generated method stub
        List<SMSData> smsList = new ArrayList<SMSData>();

        Cursor c = Utils.cursorMessage(activity);
        // getActivity().startManagingCursor(c);
        try {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        SMSData sms = new SMSData();
                        // number
                        String number = c.getString(2);

                        if (number != null && number.length() > 0) {
                            if (Utils.isCharacterPhoneNumber(number)) {
                                if (number.length() <= Utils.LENGTH_PHONE_NUMBER) {
                                    number = Utils.formatPhoneNumber(number);
                                } else {
                                    //change +849 = 09
                                    try {
                                        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
                                        PhoneNumber swissNumberProto = phoneUtil.parse(number,
                                                Utils.DEFALT_REGION);
                                        number = phoneUtil.format(swissNumberProto,
                                                PhoneNumberFormat.NATIONAL);
                                    } catch (NumberParseException e) {
                                        System.err.println("NumberParseException was thrown: "
                                                + e.toString());
                                    }
                                }

                            }
                            number = number.replaceAll(" ", "");
                            number = number.replaceAll("-", "");
                        }else{
                            number = Utils.TYPE_DRAFT;
                        }
                        // sms id
                        sms.setId(c.getString(c.getColumnIndexOrThrow(Utils.SMS_ID)));
                        sms.setNumber(number);
                        // body
                        sms.setBody(c.getString(3));
                        long time = c.getLong(c.getColumnIndexOrThrow(Utils.DATE));
                        sms.setDateTime(String.valueOf(time));
                        int isRead = c.getInt(c.getColumnIndexOrThrow(Utils.SMS_READ));
                        // 1 : read . 0: unread
                        sms.setIsRead(isRead);
                        int type = c.getInt(c.getColumnIndexOrThrow(Utils.TYPE_SMS));
                        sms.setType(String.valueOf(type));
                        String contactName = Utils.getContactName(activity, sms.getNumber());
                        sms.setName(contactName);
                        String thread_id = c.getString(c.getColumnIndexOrThrow(Utils.SMS_THREAD_ID));
                        sms.setThread_id(thread_id);
                        smsList.add(sms);

                    } while (c.moveToNext());
                }
            }
        } catch (Exception e) {
            Log.e("error", e.toString());
        }finally{
            c.close();
        }
        return smsList;

    }

    public static boolean isCharacterPhoneNumber(String phoneNumber) {
        boolean phone = false;
        String str = phoneNumber.substring(0, 1);
        if (str != null && str.length() > 0) {
            if (str.equals(Utils.CHARACTER_PHONE_NUMBER)) {
                phone = true;
            }
        }
        return phone;
    }

    public static String formatPhoneNumber(String phoneNumber) {
        String phone = "";
        if (isCharacterPhoneNumber(phoneNumber)) {
            phone = phoneNumber.substring(1);
        }
        return phone;
    }

    public static int deleteSMS(Context context, SMSData sms) {
        int count = 0;
        try {
            count = context.getContentResolver().delete(
                    Uri.parse(Utils.URI_SMS), Utils.SMS_ID+" = ?", new String[]{sms.getId()});
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("nhu ", "" + e.toString());
            return count;
        }
        return count;
    }

    public static void restoreSms(Activity context, SMSData sms) {

        if (!TextUtils.isEmpty(sms.getDateTime()) && !TextUtils.isEmpty(sms.getNumber())) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(Uri.parse(Utils.URI_SMS), null,
                        Utils.SMS_DATE + " = ? and " + Utils.SMS_ADDRESS + " = ?", new String[] {
                    sms.getDateTime(), sms.getNumber()
                }, null);
                if (cursor == null || (cursor != null && cursor.getCount() == 0)) {
                    ContentValues values = new ContentValues();
                    values.put(Utils.SMS_ADDRESS, sms.getNumber());
                    values.put(Utils.SMS_BODY, sms.getBody());
                    values.put(Utils.SMS_DATE, sms.getDateTime());
                    values.put(Utils.SMS_READ, sms.getType());
                    Log.e("", "type: " + sms.getType());
                    String uri = URI_SMS;
                    if (sms.getType().equals(Utils.TYPE_SMS_INBOX)) {
                        uri += "/" + TYPE_INBOX;
                    } else if (sms.getType().equals(Utils.TYPE_SMS_SENT)) {
                        uri += "/" + TYPE_SENT;
                    } else if (sms.getType().equals(Utils.TYPE_SMS_DRAFT)) {
                        uri += "/" + TYPE_DRAFT;
                    }
                    context.getContentResolver().insert(Uri.parse(uri), values);
                } else {
                    Log.e("",
                            "Message from contact: " + sms.getNumber() + " at the time: "
                                    + sms.getDateTime() + " exist");
                }
            } catch (Exception e) {
                Log.e("", e.toString());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }
    /**
     * Process encrypt.
     * 
     * @param context the context
     * @param fileItem the file item
     */
    public static void processEncrypt(Context context, FileItem fileItem) {
        if (Utils.isSDCardExist()) {
            if (!App.getDB().isExistFileName(fileItem.getPathFrom())) {
                String input = fileItem.getPathFrom();
                String output = null;
                int byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_PHOTO;
                Uri uri = null;
                if (fileItem.getType() == Utils.TYPE_AUDIO) {
                    output = Utils.FOLDER + Utils._AUDIO + "/"
                            + (System.currentTimeMillis() / 10);
                    byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_AUDIO;
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else if (fileItem.getType() == Utils.TYPE_PHOTO) {
                    output = Utils.FOLDER + Utils._PHOTO + "/"
                            + (System.currentTimeMillis() / 10);
                    byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_PHOTO;
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if (fileItem.getType() == Utils.TYPE_VIDEO) {
                    output = Utils.FOLDER + Utils._VIDEO + "/"
                            + (System.currentTimeMillis() / 10);
                    byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_VIDEO;
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }
                output = output + ENCRYPT_EXTENSION;
                fileItem.setPathNew(output);

                if (fileItem.getThumbnail() == null) {
					if (fileItem.getType() == Utils.TYPE_VIDEO) {
						String path = fileItem.getPathFrom(); /*
															 * get video path
															 */
						;
						Bitmap bmp = ThumbnailUtils.createVideoThumbnail(path,
								MediaStore.Images.Thumbnails.MICRO_KIND);
						fileItem.setThumbnail(Utils.getByteArray(bmp));
                    System.gc();
						if (bmp != null) {
							bmp.recycle();
						}
						bmp = null;
					} else {
						Bitmap bitmap = Utils.createThumbnail(context,
								fileItem.getId(), fileItem.getType());
						fileItem.setThumbnail(Utils.getByteArray(bitmap));
						System.gc();
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    bitmap = null;
                }

				}

                if (encrypt(context, input, byte_to_encrypte)) {
                    boolean moving = new File(input).renameTo(new File(
                            output));
                    if (moving) {
                        context.getContentResolver().delete(
                                uri,
                                Utils.MEDIA_ID + " = ?",
                                new String[] {
                                    String.valueOf(fileItem
                                            .getId())
                                });
                        App.getDB().insertFile(fileItem);
                        HiddenBoxDBUtil.getInstance().insertFileSdcardDB(
                                fileItem);
                    } else {
                        decrypt(context, input);
                    }
                }
            }
        } else {
            Log.e(Utils.TAG, "processEncrypt faile: there is no SDCard");
        }

    }

    public static void restoreCallLog(Activity activity, CallLogEntry callLogEntry) {
        // TODO Auto-generated method stub
        if (!TextUtils.isEmpty(callLogEntry.getDate())
                && !TextUtils.isEmpty(callLogEntry.getNumber())) {
            Cursor cursor = null;
            try {

                cursor = activity.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                        CallLog.Calls.DATE + " = ? and " +CallLog.Calls.NUMBER + " = ?", new String[] {
                        callLogEntry.getDate(), callLogEntry.getNumber()
                }, null);
                if (cursor == null || (cursor != null && cursor.getCount() == 0)) {
                    ContentValues values = new ContentValues();
                    values.put(CallLog.Calls.NUMBER, callLogEntry.getNumber());
                    values.put(CallLog.Calls.DATE, callLogEntry.getDate());
                    values.put(CallLog.Calls.TYPE, callLogEntry.getType());
                    values.put(CallLog.Calls.DURATION, callLogEntry.getDuration());
                    activity.getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
                }
            } catch (Exception e) {
                // TODO: handle exception
                Log.e("",
                        "Calllog : " + callLogEntry.getNumber() + " at the time: "
                                + callLogEntry.getDate() + " exist");
            } finally {
            	if (cursor!=null) {
					cursor.close();
				}
            	
            }
        }
    }

    public static boolean isExistCallLog(Activity activity, CallLogEntry callLogEntry) {
        // TODO Auto-generated method stub
        List<CallLogEntry> list = App.getDB().getAllCallLog();
        for (CallLogEntry callLogEntry2 : list) {
            if (callLogEntry2.getNumber().equals(callLogEntry.getNumber())) {
                if (Long.parseLong(callLogEntry2.getDate()) == Long.parseLong(callLogEntry
                        .getDate())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void deleteCallLog(Context context, CallLogEntry callLogEntry) {
		int foo = context.getContentResolver().delete(
				CallLog.Calls.CONTENT_URI, "_ID = ? ",
				new String[] { String.valueOf(callLogEntry.getId()) });
		if (foo <= 0) {
			Log.d("Tamle", "delete calllog fail: " + callLogEntry.getId());

		} else {
			Log.d("Tamle", "delete calllog : " + callLogEntry.getId());

		}
	}

	public static String getContactName(Context context,
			final String phoneNumber) {
		String[] projection = new String[] { PhoneLookup.DISPLAY_NAME };
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(phoneNumber));
		Cursor cursor = context.getContentResolver().query(uri, projection,
				null, null, null);

		String contactName = "";

		if (cursor.moveToFirst()) {
			contactName = cursor.getString(0);
		}

		cursor.close();
		cursor = null;

		return contactName;
	}
	public static String fetchContactIdFromPhoneNumber(Context context ,String phoneNumber) {
	    Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
	        Uri.encode(phoneNumber));
	    Cursor cursor = context.getContentResolver().query(uri,
	        new String[] { PhoneLookup.DISPLAY_NAME, PhoneLookup._ID },
	        null, null, null);

	    String contactId = "";

	    if (cursor.moveToFirst()) {
	        do {
	        contactId = cursor.getString(cursor
	            .getColumnIndex(PhoneLookup._ID));
	        } while (cursor.moveToNext());
	    }
	    if (cursor!=null) {
			cursor.close();
		}
	    return contactId;
	  }
    /**
     * Inits the cell width.
     *
     * @param activity the activity
     * @return the int
     */
    public static void initDeviceWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        SaveData.getInstance(activity).setDeviceWidth(metrics.widthPixels);
        int numberColumn = ((int) SaveData.getInstance(activity)
				.getDeviceWidth() / convertToPx(activity, activity.getResources().getInteger(R.integer.grid_cell_size)));
        SaveData.getInstance(activity).setGridColumn(numberColumn);
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap); 
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    public static int convertToPx(Context context,int dp){
        float d = context.getResources().getDisplayMetrics().density;
        int px = (int)(dp * d); //  pixels
        return  px;
    }

    public static List<Integer> initDataUserGuide() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(R.drawable.main1);
        list.add(R.drawable.main2);
        list.add(R.drawable.file_menu);
        list.add(R.drawable.file_menu1);
        list.add(R.drawable.grid_list);
        list.add(R.drawable.picker1);
        list.add(R.drawable.picker2);
        list.add(R.drawable.sorting);
        return list;
    }

}
