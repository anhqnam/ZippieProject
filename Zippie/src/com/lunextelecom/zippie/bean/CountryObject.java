package com.lunextelecom.zippie.bean;

public class CountryObject {
	
	private String mCode;
	private String mName;
	private String mDialCode;
	private Object mPhoto;
	
	public CountryObject() {
		// TODO Auto-generated constructor stub
	}
	
	public CountryObject(String code, String name, String dialCode){
		this.mCode = code;
		this.mName = name;
		this.mDialCode = dialCode;
	}
	
	public String getmCode() {
		return mCode;
	}
	public void setmCode(String mCode) {
		this.mCode = mCode;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getmDialCode() {
		return mDialCode;
	}
	public void setmDialCode(String mDialCode) {
		this.mDialCode = mDialCode;
	}
	public Object getmPhoto() {
		return mPhoto;
	}

	public void setmPhoto(Object mPhoto) {
		this.mPhoto = mPhoto;
	}
}
