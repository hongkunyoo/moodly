package com.pinthecloud.moodly.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Perform implements Parcelable{
	@com.google.gson.annotations.SerializedName("id")
	private String id;
	@com.google.gson.annotations.SerializedName("performName")
	private String performName;
	@com.google.gson.annotations.SerializedName("price")
	private float price;
	@com.google.gson.annotations.SerializedName("theaterName")
	private String theaterName;
	@com.google.gson.annotations.SerializedName("theaterAddress")
	private String theaterAddress;
	@com.google.gson.annotations.SerializedName("posterUrl")
	private String posterUrl;
	// 일시, 분위기, 장르, 뮤지션, 영상 링크, 소개 글


	public Perform() {
		// Do nothing
	}
	public Perform(Parcel in){
		this();
		readToParcel(in);
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPerformName() {
		return performName;
	}
	public void setPerformName(String performName) {
		this.performName = performName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getTheaterName() {
		return theaterName;
	}
	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}
	public String getTheaterAddress() {
		return theaterAddress;
	}
	public void setTheaterAddress(String theaterAddress) {
		this.theaterAddress = theaterAddress;
	}
	public String getPosterUrl() {
		return posterUrl;
	}
	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}


	/*
	 * Parcelable
	 */
	public static Parcelable.Creator<Perform> getCreator() {
		return CREATOR;
	}

	public static final Parcelable.Creator<Perform> CREATOR = new Creator<Perform>(){
		public Perform createFromParcel(Parcel in){
			return new Perform(in);
		}
		public Perform[] newArray(int size){
			return new Perform[size]; 
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getId());
		dest.writeString(getPerformName());
		dest.writeFloat(getPrice());
		dest.writeString(getTheaterName());
		dest.writeString(getTheaterAddress());
		dest.writeString(getPosterUrl());
	}

	public void readToParcel(Parcel in){
		setId(in.readString());
		setPerformName(in.readString());
		setPrice(in.readFloat());
		setTheaterName(in.readString());
		setTheaterAddress(in.readString());
		setPosterUrl(in.readString());
	}
}
