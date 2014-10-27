/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - CountryObject.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class CountryObject.
 */
public class CountryObject {
	
	/** The m code. */
	private String mCode;
	
	/** The m name. */
	private String mName;
	
	/** The m dial code. */
	private String mDialCode;
	
	/** The m photo. */
	private Object mPhoto;
	
	/**
	 * Instantiates a new country object.
	 */
	public CountryObject() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new country object.
	 *
	 * @param code the code
	 * @param name the name
	 * @param dialCode the dial code
	 */
	public CountryObject(String code, String name, String dialCode){
		this.mCode = code;
		this.mName = name;
		this.mDialCode = dialCode;
	}
	
	/**
	 * Gets the m code.
	 *
	 * @return the m code
	 */
	public String getmCode() {
		return mCode;
	}
	
	/**
	 * Sets the m code.
	 *
	 * @param mCode the new m code
	 */
	public void setmCode(String mCode) {
		this.mCode = mCode;
	}
	
	/**
	 * Gets the m name.
	 *
	 * @return the m name
	 */
	public String getmName() {
		return mName;
	}
	
	/**
	 * Sets the m name.
	 *
	 * @param mName the new m name
	 */
	public void setmName(String mName) {
		this.mName = mName;
	}
	
	/**
	 * Gets the m dial code.
	 *
	 * @return the m dial code
	 */
	public String getmDialCode() {
		return mDialCode;
	}
	
	/**
	 * Sets the m dial code.
	 *
	 * @param mDialCode the new m dial code
	 */
	public void setmDialCode(String mDialCode) {
		this.mDialCode = mDialCode;
	}
	
	/**
	 * Gets the m photo.
	 *
	 * @return the m photo
	 */
	public Object getmPhoto() {
		return mPhoto;
	}

	/**
	 * Sets the m photo.
	 *
	 * @param mPhoto the new m photo
	 */
	public void setmPhoto(Object mPhoto) {
		this.mPhoto = mPhoto;
	}
}
