/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.util - Compartor.java
 * Date create: 2:58:51 PM - Nov 21, 2013 - 2013
 * 
 * 
 */
package com.arisvn.arissmarthiddenbox.util;

import java.sql.Date;
import java.util.Comparator;

import android.util.Log;

import com.arisvn.arissmarthiddenbox.entity.AppItem;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.entity.CallLogEntry;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.entity.SMSData;

// TODO: Auto-generated Javadoc
/**
 * The Class Compartor.
 */
public class Compartor {

	/** Sort Name. */
    public static Comparator<BaseItemEntity> compareName = new Comparator<BaseItemEntity>() {
	
	@Override
        public int compare(BaseItemEntity lhs, BaseItemEntity rhs) {
            if (lhs instanceof FileItem && rhs instanceof FileItem) {
                String name1 = ((FileItem) lhs).getName().toUpperCase();
                String name2 = ((FileItem) rhs).getName().toUpperCase();
                if (isAsc) {
				return name1.compareTo(name2);
                } else {
				return name2.compareTo(name1);
			}
            }
            return 0;
        }
    };
    public static Comparator<BaseItemEntity> compareNumberSMS = new Comparator<BaseItemEntity>() {

        @Override
        public int compare(BaseItemEntity lhs, BaseItemEntity rhs) {
            if (lhs instanceof SMSData && rhs instanceof SMSData) {
            	String name1,name2;
            	if (((SMSData) lhs).getName()==null||((SMSData) lhs).getName().equals("")) {
					name1=((SMSData) lhs).getNumber().toUpperCase();
				} else {
					name1=((SMSData) lhs).getName();
				}
            	if (((SMSData) rhs).getName()==null||((SMSData) rhs).getName().equals("")) {
					name2=((SMSData) rhs).getNumber().toUpperCase();
				} else {
					name2=((SMSData) rhs).getName();
				}
                if (isAsc) {
                    return name1.compareTo(name2);
                } else {
                    return name2.compareTo(name1);
                }
            }
            return 0;
        }
    };
    public static Comparator<BaseItemEntity> compareNumberCallLog = new Comparator<BaseItemEntity>() {

        @Override
        public int compare(BaseItemEntity lhs, BaseItemEntity rhs) {
            if (lhs instanceof CallLogEntry && rhs instanceof CallLogEntry) {
            	String name1,name2;
            	if (((CallLogEntry) lhs).getName()==null||((CallLogEntry) lhs).getName().equals("")) {
					name1=((CallLogEntry) lhs).getNumber().toUpperCase();
				} else {
					name1=((CallLogEntry) lhs).getName();
				}
            	if (((CallLogEntry) rhs).getName()==null||((CallLogEntry) rhs).getName().equals("")) {
					name2=((CallLogEntry) rhs).getNumber().toUpperCase();
				} else {
					name2=((CallLogEntry) rhs).getName();
				}
                if (isAsc) {
                    return name1.compareTo(name2);
                } else {
                    return name2.compareTo(name1);
                }
            }
            return 0;
        }
    };
    public static Comparator<BaseItemEntity> compareContentSMS = new Comparator<BaseItemEntity>() {

        @Override
        public int compare(BaseItemEntity lhs, BaseItemEntity rhs) {
            if (lhs instanceof SMSData && rhs instanceof SMSData) {
                String body1 = ((SMSData) lhs).getBody().toUpperCase();
                String body2 = ((SMSData) rhs).getBody().toUpperCase();
                int i1=body1.length();
                int i2=body2.length();
                if (isAsc) {
                    if (i1 == i2)
                        return 0;
                    else if (i1 > i2)
						return 1;
					else
						return -1;
                } else {
                    if (i1 == i2)
						return 0;
                    else if (i1 > i2)
						return -1;
					else
						return 1;
                }
            }
            return 0;
        }
    };
    public static Comparator<BaseItemEntity> compareDateTimeCallLog = new Comparator<BaseItemEntity>() {

        @Override
        public int compare(BaseItemEntity lhs, BaseItemEntity rhs) {
            if (lhs instanceof CallLogEntry && rhs instanceof CallLogEntry) {
            	try {
            		Date date1 = new Date(Long.parseLong(((CallLogEntry) lhs).getDate()));
                	Date date2 = new Date(Long.parseLong(((CallLogEntry) rhs).getDate()));
                    if (isAsc) {
                        return date1.compareTo(date2);
                    } else {
                        return date2.compareTo(date1);
                    }
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("Tamle", getClass().getName()+"::compareDateTimeCallLog::compare::Can not convert string date to date");
                }
            }
            return 0;
        }
    };
    /** Sort Size. */
    public static Comparator<BaseItemEntity> compareSize = new Comparator<BaseItemEntity>() {
		@Override
        public int compare(BaseItemEntity lhs, BaseItemEntity rhs) {
            if (lhs instanceof FileItem && rhs instanceof FileItem) {

                long size1 = ((FileItem) lhs).getSize();
                long size2 = ((FileItem) rhs).getSize();
                if (isAsc) {
                    if (size1 == size2)
                        return 0;
                    else if (size1 > size2)
						return 1;
					else
						return -1;
                } else {
                    if (size1 == size2)
						return 0;
                    else if (size1 > size2)
						return -1;
					else
						return 1;
				}
            }
            return 0;
				
			}
		};
		
	/** The is asc. */
	public static boolean isAsc;
	  public static Comparator<BaseItemEntity> compareAppName = new Comparator<BaseItemEntity>() {

	        @Override
	        public int compare(BaseItemEntity lhs, BaseItemEntity rhs) {
	            if (lhs instanceof AppItem && rhs instanceof AppItem) {
	                String name1 = ((AppItem) lhs).getLabel().toLowerCase();
	                String name2 = ((AppItem) rhs).getLabel().toLowerCase();
	                if (isAsc) {
	                    return name1.compareTo(name2);
	                } else {
	                    return name2.compareTo(name1);
	                }
	            }
	            return 0;
	        }
	    };

}
