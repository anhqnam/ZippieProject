/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - CountryHelper.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.lunextelecom.zippie.bean.CountryObject;

// TODO: Auto-generated Javadoc
/**
 * The Class CountryHelper.
 */
public class CountryHelper implements ConnectionCallbacks,
				OnConnectionFailedListener, LocationListener{
	
	/**
	 * The Interface ConnectionGPSSuccess.
	 */
	public interface ConnectionGPSSuccess{
		
		/**
		 * Connect success.
		 *
		 * @param countryCode the country code
		 */
		public void connectSuccess(String countryCode);
	}
	
	/** The connection gps success. */
	private ConnectionGPSSuccess connectionGPSSuccess;
	
	/** The location client. */
	private LocationClient locationClient;
	
	/** The location request. */
	private LocationRequest locationRequest;
	
	/** The context. */
	private Context context;
	
	/**
	 * Instantiates a new country helper.
	 *
	 * @param context the context
	 * @param connectionGPSSuccess the connection gps success
	 */
	public CountryHelper(Context context, ConnectionGPSSuccess connectionGPSSuccess) {
		// TODO Auto-generated constructor stub
		locationClient = new LocationClient(context, this, this);
		locationRequest = new LocationRequest();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locationRequest.setInterval(5000);
		locationRequest.setFastestInterval(1000);
		this.connectionGPSSuccess = connectionGPSSuccess;
		this.context = context;
	}
	
	/**
	 * Connect gps.
	 */
	public void connectGPS(){
		locationClient.connect();
	}
	
	/**
	 * Disconnect gps.
	 */
	public void disconnectGPS(){
		if(locationClient != null && locationClient.isConnected()){
			locationClient.disconnect();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.google.android.gms.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener#onConnectionFailed(com.google.android.gms.common.ConnectionResult)
	 */
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks#onConnected(android.os.Bundle)
	 */
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		locationClient.requestLocationUpdates(locationRequest, this);
		Location location = locationClient.getLastLocation();
		if(location != null){
			Geocoder geocoder = new Geocoder(context, Locale.getDefault());
			try {
				List<Address> listAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
				if(listAddress != null && listAddress.size() >0){
					Address address = listAddress.get(0);
					connectionGPSSuccess.connectSuccess(address.getCountryCode());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	/* (non-Javadoc)
	 * @see com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks#onDisconnected()
	 */
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Gets the list country from json file.
	 *
	 * @param asset the asset
	 * @return the list country from json file
	 */
	public static List<CountryObject> getListCountryFromJsonFile(AssetManager asset){
		List<CountryObject> lst = new ArrayList<CountryObject>();
		try {
			JSONArray ja = new JSONArray(LoadJsonFromAsset("countries.json",asset));
			for(int i = 0, size = ja.length(); i< size; i++){
				JSONObject ob = ja.getJSONObject(i);
				String code = ob.getString("code");
				String name = ob.getString("name");
				String dial_code = ob.getString("dial_code");
				CountryObject c = new CountryObject(code,name, dial_code);
				lst.add(c);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lst;
	}
	
	/**
	 * Load json from asset.
	 *
	 * @param fileName the file name
	 * @param asset the asset
	 * @return the string
	 */
	private static String LoadJsonFromAsset(String fileName, AssetManager asset){
		try {
			InputStream is = asset.open(fileName);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			return new String(buffer,"UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	/**
	 * Gets the country object.
	 *
	 * @param countryCode the country code
	 * @param listCountry the list country
	 * @return the country object
	 */
	public static CountryObject getCountryObject(String countryCode, List<CountryObject> listCountry){
		if(listCountry != null){
			for(int i=0, size = listCountry.size(); i < size; i++){
				if(listCountry.get(i).getmCode().equals(countryCode)){
					return listCountry.get(i);
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets the country object by dial code.
	 *
	 * @param dialCode the dial code
	 * @param listCountry the list country
	 * @return the country object by dial code
	 */
	public static CountryObject getCountryObjectByDialCode(String dialCode, List<CountryObject> listCountry){
		if(listCountry != null){
			for(int i=0, size = listCountry.size(); i < size; i++){
				if(listCountry.get(i).getmDialCode().equals(dialCode)){
					return listCountry.get(i);
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets the array country name.
	 *
	 * @param listCountry the list country
	 * @return the array country name
	 */
	public static String[] getArrayCountryName(List<CountryObject> listCountry){
		if(listCountry != null && listCountry.size() >0){
			String[] results = new String[listCountry.size()];
			for(int i=0, size = listCountry.size(); i<size ; i++){
				CountryObject country = listCountry.get(i);
				results[i] = country.getmName()+" ("+country.getmDialCode()+")";
			}
			return results;
		}
		return null;
	}
	
	/**
	 * Gets the drawable flag country.
	 *
	 * @param asset the asset
	 * @param countryCode the country code
	 * @return the drawable flag country
	 */
	public static Drawable getDrawableFlagCountry(AssetManager asset,String countryCode){
		String path = "flags/"+countryCode.toLowerCase()+".png";
		try {
			InputStream is = asset.open(path);
			if(is != null){
				Drawable draw = Drawable.createFromStream(is, null);
				return  draw;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
