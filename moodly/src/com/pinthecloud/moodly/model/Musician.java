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
import com.pinthecloud.moodly.util.JsonUtil;
import com.pinthecloud.moodly.util.RandomUtil;

public class Musician implements Parcelable, JsonSerializer<Musician>, JsonDeserializer<Musician> {
	
	public enum Genre {
		DANCE(0),
		BALLAD(1),
		HIPHOP(2);
		
		private final int value;

		private Genre(final int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
		public static Genre toGenre(int i) {
			for (Genre g : values()) {
				if (g.getValue() == i) return g;
			}
			return null;
		}
	}
	
	private String id;
	private String korName;
	private String engName;
	private String snsLink;
	private String videoLink;
	private String webPage;
	private Genre genre;
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
	public String getSnsLink() {
		return snsLink;
	}
	public void setSnsLink(String snsLink) {
		this.snsLink = snsLink;
	}
	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	public String getWebPage() {
		return webPage;
	}
	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
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
		dest.writeString(getSnsLink());
		dest.writeString(getVideoLink());
		dest.writeString(getWebPage());
		dest.writeInt(getGenre().getValue());
		dest.writeList(getMember());
	}

	public void readToParcel(Parcel in){
		setId(in.readString());
		setKorName(in.readString());
		setEngName(in.readString());
		setSnsLink(in.readString());
		setVideoLink(in.readString());
		setWebPage(in.readString());
		setGenre(Genre.toGenre(in.readInt()));
		in.readList(member, String.class.getClassLoader());
		setMember(member);
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
	public static Musician newMusician() {
		Musician m = new Musician();
		m.setId("mu-"+RandomUtil.getString(7));
		m.setKorName(RandomUtil.getName());
		m.setEngName(RandomUtil.getName());
		m.setSnsLink("http://www.facebook.com/"+RandomUtil.getString(6));
		m.setVideoLink("http://www.youtube.com/"+RandomUtil.getString(6));
		m.setWebPage("http://www."+RandomUtil.getString(8)+".com");
		m.setGenre(Genre.toGenre(RandomUtil.getInt(Genre.values().length)));
		m.setMember(new ArrayList<String>(){
			{
				add("mem-"+RandomUtil.getName());
				add("mem-"+RandomUtil.getName());
				add("mem-"+RandomUtil.getName());
			}
		});
		return m;
	}
	
	@Override
	public JsonElement serialize(Musician arg0, Type arg1,
			JsonSerializationContext arg2) {
		// TODO Auto-generated method stub
		GsonBuilder gb = new GsonBuilder();
		gb.addSerializationExclusionStrategy(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes arg0) {
				// TODO Auto-generated method stub
				if ("member".equals(arg0.getName()) || "genre".equals(arg0.getName())) {
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
		jo.addProperty("genre", getGenre().getValue());
		jo.add("member", gson.fromJson(member.toString(), JsonElement.class));
		
		return jo;
	}
	
	@Override
	public Musician deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		// TODO Auto-generated method stub
		GsonBuilder gb = new GsonBuilder();
		gb.addDeserializationExclusionStrategy(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes arg0) {
				// TODO Auto-generated method stub
				if ("member".equals(arg0.getName()) || "genre".equals(arg0.getName())) {
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
		Musician m  = gson.fromJson(arg0, Musician.class);
		List<String> memList = new ArrayList<String>();
		JsonObject jo = (JsonObject)arg0;
		JsonElement je = jo.get("member");
		JsonArray ja = je.getAsJsonArray();
		for (JsonElement jj : ja) {
			memList.add(jj.getAsString());
		}
		m.setMember(memList);
		int index = JsonUtil.getInt(jo, "genre");
		m.setGenre(Genre.toGenre(index));
		return m;
	}
}
