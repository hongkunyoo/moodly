package com.pinthecloud.moodly.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pinthecloud.moodly.util.RandomUtil;

public class Perform implements Parcelable{
	private String id;
	private String performName;
	private float price;
	private String placeName;
	private String placeAddress01;
	private String placeAddress02;
	private String posterUrl;
	private String description;
	private List<Schedule> schedule;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Schedule> getSchedule() {
		return schedule;
	}
	public void setSchedule(List<Schedule> list) {
		this.schedule = list;
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
		dest.writeString(getDescription());
		dest.writeList(schedule);
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
		setDescription(in.readString());
		in.readList(schedule, Schedule.class.getClassLoader());
		setSchedule(schedule);
		in.readList(lineup, Musician.class.getClassLoader());
		setLineup(lineup);
		in.readList(mood, String.class.getClassLoader());
		setMood(mood);
	}
	
	public JsonElement toJson() {
		Gson gson = new Gson();
		JsonObject jo = new JsonObject();
		
		jo.addProperty("id", getId());
		jo.addProperty("performName", getPerformName());
		jo.addProperty("price", getPrice());
		jo.addProperty("placeName", getPlaceName());
		jo.addProperty("placeAddress01", getPlaceAddress01());
		jo.addProperty("placeAddress02", getPlaceAddress02());
		jo.addProperty("postUrl", getPosterUrl());
		jo.addProperty("description", getDescription());
		jo.add("schedule", gson.fromJson(schedule.toString(), JsonElement.class));
		jo.add("lineup", gson.fromJson(lineup.toString(), JsonElement.class));
		jo.add("mood", gson.fromJson(mood.toString(), JsonElement.class));
		
		return jo;
	}
	@Override
	public String toString() {
		return toJson().toString();
	}
	public static Perform newPerform() {
		Perform p = new Perform();
		
		p.setId("per-"+RandomUtil.getString(7));
		p.setPerformName("per-"+RandomUtil.getString(5));
		p.setPrice(RandomUtil.getFloat());
		p.setPlaceName("per-"+RandomUtil.getString(7));
		p.setPlaceAddress01("per-"+RandomUtil.getString(8));
		p.setPlaceAddress02("per-"+RandomUtil.getString(8));
		p.setPosterUrl("per-"+RandomUtil.getString(8));
		p.setDescription("per-"+RandomUtil.getString(8));
		p.setSchedule(new ArrayList<Schedule>(){
			{
				add(Schedule.newSchedule());
				add(Schedule.newSchedule());
			}
		});
		p.setLineup(new ArrayList<Musician>(){
			{
				add(Musician.newMusician());
				add(Musician.newMusician());
			}
		});
		p.setMood(new ArrayList<String>(){
			{
				add(RandomUtil.getString(7));
				add(RandomUtil.getString(7));
			}
		});
		
		return p;
	}
}
