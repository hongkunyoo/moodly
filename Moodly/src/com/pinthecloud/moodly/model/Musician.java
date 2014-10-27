package com.pinthecloud.moodly.model;

import java.util.List;

public class Musician {
	private String name;
	private String videoLink;
	private List<String> member;
	private String leader;
	private String leaderPhone;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getLeaderPhone() {
		return leaderPhone;
	}
	public void setLeaderPhone(String leaderPhone) {
		this.leaderPhone = leaderPhone;
	}
}
