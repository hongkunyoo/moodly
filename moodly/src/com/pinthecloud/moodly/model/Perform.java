package com.pinthecloud.moodly.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.pinthecloud.moodly.util.RandomUtil;

public class Perform implements Parcelable, JsonSerializer<Perform>, JsonDeserializer<Perform> {
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
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	public static Perform newPerform() {
		Perform p = new Perform();
		
		p.setId("per-"+RandomUtil.getString(7));
		p.setPerformName("per-"+RandomUtil.getObjName());
		p.setPrice(RandomUtil.getFloat());
		p.setPlaceName("per-"+RandomUtil.getObjName());
		p.setPlaceAddress01("per-"+RandomUtil.getObjName());
		p.setPlaceAddress02("per-"+RandomUtil.getString(8));
		p.setPosterUrl("per-http://"+RandomUtil.getString(6)+".com");
		p.setDescription("per-"+RandomUtil.getObjName()+"is a " + RandomUtil.getObjName());
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
				add(RandomUtil.getObjName());
				add(RandomUtil.getObjName());
			}
		});
		
		return p;
	}
	
	@Override
	public JsonElement serialize(Perform arg0, Type arg1,
			JsonSerializationContext arg2) {
		// TODO Auto-generated method stub
		GsonBuilder gb = new GsonBuilder();
		gb.addSerializationExclusionStrategy(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes arg0) {
				// TODO Auto-generated method stub
				String fieldName = arg0.getName();
				if ("schedule".equals(fieldName) || "lineup".equals(fieldName)
						|| "mood".equals(fieldName)) {
					return true;
				}
				return false;
			}
			
			@Override
			public boolean shouldSkipClass(Class<?> arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		Gson gson = gb.create();
		
		JsonObject jo = gson.fromJson(gson.toJson(arg0), JsonObject.class);
		jo.add("schedule", gson.fromJson(schedule.toString(), JsonElement.class));
		jo.add("lineup", gson.fromJson(lineup.toString(), JsonElement.class));
		jo.add("mood", gson.fromJson(mood.toString(), JsonElement.class));
		
		return jo;
	}
	@Override
	public Perform deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		// TODO Auto-generated method stub
		GsonBuilder gb = new GsonBuilder();
		gb.addDeserializationExclusionStrategy(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes arg0) {
				// TODO Auto-generated method stub
				String fieldName = arg0.getName();
				if ("schedule".equals(fieldName) || "lineup".equals(fieldName)
						|| "mood".equals(fieldName)) {
					return true;
				}
				return false;
			}
			
			@Override
			public boolean shouldSkipClass(Class<?> arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		Gson gson = gb.create();
		Perform per  = gson.fromJson(arg0, Perform.class);
		JsonObject jo = (JsonObject)arg0;
		
		List<Schedule> schedule = new ArrayList<Schedule>();
		JsonElement je = jo.get("schedule");
		JsonArray ja = je.getAsJsonArray();
		for (JsonElement jj : ja) {
			schedule.add(new Gson().fromJson(jj, Schedule.class));
		}
		per.setSchedule(schedule);
		
		List<Musician> lineup = new ArrayList<Musician>();
		je = jo.get("lineup");
		ja = je.getAsJsonArray();
		for (JsonElement jj : ja) {
			lineup.add(new Gson().fromJson(jj, Musician.class));
		}
		per.setLineup(lineup);
		
		List<String> mood = new ArrayList<String>();
		je = jo.get("mood");
		ja = je.getAsJsonArray();
		for (JsonElement jj : ja) {
			mood.add(new Gson().fromJson(jj, String.class));
		}
		per.setMood(mood);
		return per;
	}
}
