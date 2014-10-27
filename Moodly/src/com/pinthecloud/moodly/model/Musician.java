package com.pinthecloud.moodly.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Musician implements Parcelable{
	@com.google.gson.annotations.SerializedName("id")
	private String id;
	@com.google.gson.annotations.SerializedName("memberCount")
	private int memberCount;
	// 장르, 영상 리스트로


	public Musician() {
		// Do nothing
	}
	public Musician(Parcel in){
		this();
		readToParcel(in);
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
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
		dest.writeInt(getMemberCount());
	}

	public void readToParcel(Parcel in){
		setId(in.readString());
		setMemberCount(in.readInt());
	}
}
