/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.util.lazyloading - ImageLoader.java
 * Date create: 2:59:58 PM - Nov 21, 2013 - 2013
 * 
 * 
 */
package com.arisvn.arissmarthiddenbox.util.lazyloading;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.entity.CallLogEntry;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.entity.SMSData;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageLoader.
 */
public class ImageLoader {

	/** The memory cache. */
	public MemoryCache memoryCache = new MemoryCache();
	
	/** The image views. */
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	
	/** The executor service. */
	ExecutorService executorService;
	
	/** The context. */
	Context context;
	
	/** The type. */
	int type;

	private Bitmap videoBitmap, audioBitmap, photoBitmap, no_Avatar;

	/**
	 * Instantiates a new image loader.
	 *
	 * @param context
	 *            the context
	 * @param type
	 *            the type
	 */
	public ImageLoader(Context context, int type) {
		this.context = context;
		executorService = Executors.newFixedThreadPool(5);
		this.type = type;
        videoBitmap = Utils.getBitmapViaType(context, Utils.TYPE_VIDEO);
        audioBitmap = Utils.getBitmapViaType(context, Utils.TYPE_AUDIO);
        photoBitmap = Utils.getBitmapViaType(context, Utils.TYPE_PHOTO);
		no_Avatar=Utils.getBitmapFromId(context, R.drawable.no_avatar);
	}

	/**
	 * Display image.
	 *
	 * @param imageID
	 *            the image id
	 * @param imageView
	 *            the image view
	 */
	public void displayImage(BaseItemEntity baseItemEntity, ImageView imageView) {
		if (baseItemEntity instanceof FileItem) {
			FileItem fileItem = (FileItem) baseItemEntity;
        imageViews.put(imageView, "" + fileItem.getId());
        Bitmap bitmap = memoryCache.get("" + fileItem.getId());
			if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
//				try {
//					((FileItem) baseItemEntity).setThumbnail(Utils.getByteArray(bitmap));
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
			} else {
            queuePhoto(fileItem, imageView);
		}
                    } else {
			// Contact bitmap
			if (baseItemEntity instanceof SMSData) {
				imageViews.put(imageView,
						"" + ((SMSData) baseItemEntity).getNumber());
				Bitmap bitmap = memoryCache.get(""
						+ ((SMSData) baseItemEntity).getNumber());
				if (bitmap != null)
					imageView.setImageBitmap(bitmap);
				else {
					queuePhoto(((SMSData) baseItemEntity), imageView);
				}
			} else if (baseItemEntity instanceof CallLogEntry) {
				imageViews.put(imageView,
						"" + ((CallLogEntry) baseItemEntity).getNumber());
				Bitmap bitmap = memoryCache.get(""
						+ ((CallLogEntry) baseItemEntity).getNumber());
				if (bitmap != null)
					imageView.setImageBitmap(bitmap);
				else {
					queuePhoto(((CallLogEntry) baseItemEntity), imageView);
				}
			}

		}

	}

	/**
	 * Queue photo.
	 * 
	 * @param imageName
	 *            the image name
	 * @param imageView
	 *            the image view
	 */
	private void queuePhoto(BaseItemEntity baseItemEntity, ImageView imageView) {
		PhotoToLoad p = new PhotoToLoad(baseItemEntity, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	/**
	 * Gets the bitmap.
	 * 
	 * @param imageID
	 *            the image id
	 * @param type
	 *            the type
	 * @return the bitmap
	 */
	private Bitmap getBitmap(String imageID, int type) {
		// from SD
		if (type == Utils.TYPE_THUMBNAIL) {
			// BaseFragment
			try {
				FileItem obj = App.getDB().getFile(Integer.parseInt(imageID));

				if (obj != null) {
					if (obj.getThumbnail() != null) {
						return BitmapFactory.decodeByteArray(
								obj.getThumbnail(), 0,
								obj.getThumbnail().length);
					} else {
                        switch (obj.getType()) {
                            case Utils.TYPE_VIDEO:
                                return videoBitmap;
                            case Utils.TYPE_AUDIO:
                                return audioBitmap;
                            case Utils.TYPE_PHOTO:
                                return photoBitmap;
                            default:
                                break;
                        }
                    }
                }
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}

		} else if (type==Utils.TYPE_CONTACT) {
			//get contact's thumbnail
			String id=Utils.fetchContactIdFromPhoneNumber(context, imageID);
			if (id==null|| id.length()==0) {
				return null;
			}
			Uri my_contact_Uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(id));
			InputStream photo_stream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),my_contact_Uri);            
			BufferedInputStream buf=new BufferedInputStream(photo_stream);
			Bitmap my_btmp = BitmapFactory.decodeStream(buf);
			try {
				if (buf != null) {
					buf.close();
				}
				if (photo_stream!=null) {
					photo_stream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return my_btmp;
		}
		Bitmap b = Utils
				.createThumbnail(context, Long.parseLong(imageID), type);
		return b;
	}

	// Task for the queue
	/**
	 * The Class PhotoToLoad.
	 */
	private class PhotoToLoad {
		
		/** The image name. */
		public BaseItemEntity baseItemEntity;
		
		/** The image view. */
		public ImageView imageView;

		/**
		 * Instantiates a new photo to load.
		 *
		 * @param u
		 *            the u
		 * @param i
		 *            the i
		 */
		public PhotoToLoad(BaseItemEntity entity, ImageView i) {
			baseItemEntity = entity;
			imageView = i;
		}
	}

	/**
	 * The Class PhotosLoader.
	 */
	class PhotosLoader implements Runnable {
		
		/** The photo to load. */
		PhotoToLoad photoToLoad;

		/**
		 * Instantiates a new photos loader.
		 *
		 * @param photoToLoad
		 *            the photo to load
		 */
		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp=null;
			switch (type) {
			case Utils.TYPE_CONTACT:
				if (photoToLoad.baseItemEntity instanceof SMSData) {
					bmp = getBitmap("" + ((SMSData)photoToLoad.baseItemEntity).getNumber(), type);
			if (bmp == null) {
						bmp = no_Avatar;
					}
					memoryCache.put("" +  ((SMSData)photoToLoad.baseItemEntity).getNumber(), bmp);
				} else if (photoToLoad.baseItemEntity instanceof CallLogEntry) {
					bmp = getBitmap("" + ((CallLogEntry)photoToLoad.baseItemEntity).getNumber(), type);
					if (bmp == null) {
						bmp = no_Avatar;
					}
					memoryCache.put("" +  ((CallLogEntry)photoToLoad.baseItemEntity).getNumber(), bmp);
				}
				
				break;

			default:
				if (type == Utils.TYPE_VIDEO) {
					String path = ((FileItem) photoToLoad.baseItemEntity)
							.getPathFrom(); /* get video path */
					;
					bmp = ThumbnailUtils.createVideoThumbnail(path,
							MediaStore.Images.Thumbnails.MICRO_KIND);
				} else {
					bmp = getBitmap(""
							+ ((FileItem) photoToLoad.baseItemEntity).getId(),
							type);
				}
				if (bmp == null) {
				bmp = Utils.getBitmapViaType(context, type);
			}
				memoryCache.put("" +  ((FileItem)photoToLoad.baseItemEntity).getId(), bmp);
				break;
			}
			
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	/**
	 * Image view reused.
	 *
	 * @param photoToLoad the photo to load
	 * @return true, if successful
	 */
	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (photoToLoad.baseItemEntity instanceof FileItem) {
			if (tag == null || !tag.equals("" + ((FileItem)photoToLoad.baseItemEntity).getId()))
			return true;
		} else if (photoToLoad.baseItemEntity instanceof SMSData) {
			if (tag == null || !tag.equals("" + ((SMSData)photoToLoad.baseItemEntity).getNumber()))
				return true;
		}else if (photoToLoad.baseItemEntity instanceof CallLogEntry) {
			if (tag == null || !tag.equals("" + ((CallLogEntry)photoToLoad.baseItemEntity).getNumber()))
				return true;
		}
		return false;
	}

	// Used to display bitmap in the UI thread
	/**
	 * The Class BitmapDisplayer.
	 */
	class BitmapDisplayer implements Runnable {
		
		/** The bitmap. */
		Bitmap bitmap;
		
		/** The photo to load. */
		PhotoToLoad photoToLoad;

		/**
		 * Instantiates a new bitmap displayer.
		 *
		 * @param b the b
		 * @param p the p
		 */
		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null) {
				photoToLoad.imageView.setImageBitmap(bitmap);
			}

		}
	}
}
