/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: NhuHoang
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.entity - SMSData.java
 * Date create: 3:28:13 PM - Feb 21, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.entity;

// TODO: Auto-generated Javadoc
/**
 * The Class SMSData.
 */
public class SMSData extends BaseItemEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	/** The number. */
    private String number;

    /** The body. */
    private String body;

    /** The id. */
    private String id;

    /** The type. */
    private String type = "";

    /** The read. */
    private int read;
    private String thread_id;
    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /** The date time. */
    private String dateTime;

    /**
     * Gets the number.
     * 
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /** The is read. */
    private int isRead;

    /**
     * Sets the number.
     * 
     * @param number the new number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Gets the body.
     * 
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body.
     * 
     * @param body the new body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets the date time.
     * 
     * @return the date time
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date time.
     * 
     * @param dateTime the new date time
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the checks if is read.
     *
     * @return the isRead
     */
    public int getIsRead() {
        return isRead;
    }

    /**
     * Sets the checks if is read.
     *
     * @param isRead the isRead to set
     */
    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    /**
     * Gets the read.
     *
     * @return the read
     */
    public int getRead() {
        return read;
    }

    /**
     * Sets the read.
     *
     * @param read the read to set
     */
    public void setRead(int read) {
        this.read = read;
    }

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * @return the thread_id
     */
    public String getThread_id() {
        return thread_id;
    }

    /**
     * @param thread_id the thread_id to set
     */
    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

}
