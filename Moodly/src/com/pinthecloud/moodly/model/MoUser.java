package com.pinthecloud.moodly.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MoUser implements Parcelable{
	@com.google.gson.annotations.SerializedName("id")
	private String id;
	@com.google.gson.annotations.SerializedName("mobileType")
	private String mobileType = "Android";
	@com.google.gson.annotations.SerializedName("registrationId")
	private String registrationId;
	@com.google.gson.annotations.SerializedName("moId")
	private String moId;
	@com.google.gson.annotations.SerializedName("nickName")
	private String nickName;


	public MoUser() {
		// Do nothing
	}
	public MoUser(Parcel in){
		this();
		readToParcel(in);
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMobileType() {
		return mobileType;
	}
	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}
	public String getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	public String getMoId() {
		return moId;
	}
	public void setMoId(String moId) {
		this.moId = moId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	/*
	 * Parcelable
	 */
	public static Parcelable.Creator<MoUser> getCreator() {
		return CREATOR;
	}
	
	public static final Parcelable.Creator<MoUser> CREATOR = new Creator<MoUser>(){
		public MoUser createFromParcel(Parcel in){
			return new MoUser(in);
		}
		public MoUser[] newArray(int size){
			return new MoUser[size]; 
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getId());
		dest.writeString(getMobileType());
		dest.writeString(getRegistrationId());
		dest.writeString(getMoId());
		dest.writeString(getNickName());
	}

	public void readToParcel(Parcel in){
		setId(in.readString());
		setMobileType(in.readString());
		setRegistrationId(in.readString());
		setMoId(in.readString());
		setNickName(in.readString());
	}
}
