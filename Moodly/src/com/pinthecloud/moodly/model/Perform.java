package com.pinthecloud.moodly.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Perform implements Parcelable{
	private String id;
	private String performName;
	private float price;
	private String placeName;
	private String placeAddress01;
	private String placeAddress02;
	private String posterUrl;
	private String startDate;
	private String endDate;
	private String time;
	private String description;
	private List<Musician> lineup;
	private List<String> mood;
	
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
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getPlaceAddress01() {
		return placeAddress01;
	}
	public void setPlaceAddress01(String placeAddress01) {
		this.placeAddress01 = placeAddress01;
	}
	public String getPlaceAddress02() {
		return placeAddress02;
	}
	public void setPlaceAddress02(String placeAddress02) {
		this.placeAddress02 = placeAddress02;
	}
	public String getPosterUrl() {
		return posterUrl;
	}
	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Musician> getLineup() {
		return lineup;
	}
	public void setLineup(List<Musician> lineup) {
		this.lineup = lineup;
	}
	public List<String> getMood() {
		return mood;
	}
	public void setMood(List<String> mood) {
		this.mood = mood;
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
		dest.writeString(getPlaceName());
		dest.writeString(getPlaceAddress01());
		dest.writeString(getPlaceAddress02());
		dest.writeString(getPosterUrl());
		dest.writeString(getStartDate());
		dest.writeString(getEndDate());
		dest.writeString(getTime());
		dest.writeString(getDescription());
		dest.writeList(lineup);
		dest.writeList(mood);
	}

	public void readToParcel(Parcel in){
		setId(in.readString());
		setPerformName(in.readString());
		setPrice(in.readFloat());
		setPlaceName(in.readString());
		setPlaceAddress01(in.readString());
		setPlaceAddress02(in.readString());
		setPosterUrl(in.readString());
		setStartDate(in.readString());
		setEndDate(in.readString());
		setTime(in.readString());
		setDescription(in.readString());
		in.readList(lineup, Musician.class.getClassLoader());
		setLineup(lineup);
		in.readList(mood, String.class.getClassLoader());
		setMood(mood);
	}
}
