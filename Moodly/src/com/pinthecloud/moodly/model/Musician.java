package com.pinthecloud.moodly.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pinthecloud.moodly.util.RandomUtil;

public class Musician implements Parcelable {
	private String id;
	private String korName;
	private String engName;
	private String videoLink;
	private List<String> member;
	
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
	public String getKorName() {
		return korName;
	}
	public void setKorName(String name) {
		this.korName = name;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String name) {
		this.engName = name;
	}
	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	public List<String> getMember() {
		return member;
	}
	public void setMember(List<String> member) {
		this.member = member;
	}
	/*
	 * Parcelable
	 */
	public static Parcelable.Creator<Musician> getCreator() {
		return CREATOR;
	}
	
	public static final Parcelable.Creator<Musician> CREATOR = new Creator<Musician>(){
		public Musician createFromParcel(Parcel in){
			return new Musician(in);
		}
		public Musician[] newArray(int size){
			return new Musician[size]; 
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getId());
		dest.writeString(getKorName());
		dest.writeString(getEngName());
		dest.writeString(getVideoLink());
		dest.writeList(getMember());
	}

	public void readToParcel(Parcel in){
		setId(in.readString());
		setKorName(in.readString());
		setEngName(in.readString());
		setVideoLink(in.readString());
		in.readList(member, String.class.getClassLoader());
		setMember(member);
	}
	
	@Override
	public String toString() {
		return this.toJson().toString();
	}
	
	public JsonElement toJson() {
		Gson gson = new Gson();
		JsonObject jo = new JsonObject();
		
		jo.addProperty("id", getId());
		jo.addProperty("korName", getKorName());
		jo.addProperty("engName", getEngName());
		jo.addProperty("video", getVideoLink());
		jo.add("member", gson.fromJson(member.toString(), JsonElement.class));
		
		return jo;
	}
	
	public static Musician newMusician() {
		Musician m = new Musician();
		m.setId("mu-"+RandomUtil.getString(7));
		m.setKorName("mu-"+RandomUtil.getString(20));
		m.setEngName("mu-"+RandomUtil.getString(20));
		m.setVideoLink("http://www."+RandomUtil.getString(8)+".com");
		m.setMember(new ArrayList<String>(){
			{
				add("mem-"+RandomUtil.getString(10));
				add("mem-"+RandomUtil.getString(10));
				add("mem-"+RandomUtil.getString(10));
			}
		});
		return m;
	}
}
