package com.pinthecloud.moodly.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pinthecloud.moodly.util.RandomUtil;

public class Schedule implements Parcelable {
	private String start;
	private String end;
	private String time;
	
	public Schedule() {
		// Do nothing
	}
	public Schedule(Parcel in){
		this();
		readToParcel(in);
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	/*
	 * Parcelable
	 */
	public static Parcelable.Creator<Schedule> getCreator() {
		return CREATOR;
	}
	
	public static final Parcelable.Creator<Schedule> CREATOR = new Creator<Schedule>(){
		public Schedule createFromParcel(Parcel in){
			return new Schedule(in);
		}
		public Schedule[] newArray(int size){
			return new Schedule[size]; 
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getStart());
		dest.writeString(getEnd());
		dest.writeString(getTime());
	}
	public void readToParcel(Parcel in){
		setStart(in.readString());
		setEnd(in.readString());
		setTime(in.readString());
	}
	public JsonElement toJson() {
		JsonObject jo = new JsonObject();
		jo.addProperty("start", getStart());
		jo.addProperty("end", getEnd());
		jo.addProperty("time", getTime());
		return jo;
	}
	@Override
	public String toString() {
		return toJson().toString();
	}
	
	public static Schedule newSchedule() {
		Schedule s = new Schedule();
		s.setStart(RandomUtil.getDate());
		s.setEnd(RandomUtil.getDate());
		s.setTime(String.valueOf(RandomUtil.getInt(24)));
		return s;
	}
}
