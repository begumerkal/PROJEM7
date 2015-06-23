package com.wyd.empire.world.model;

public class Bulletin {
	private int id;
	private long sTime;
	private long eTime;
	private String sTimeString;
	private String eTimeString;
	private int frequency;
	private int nowTimes;
	private String content;

	public Bulletin() {
		id = this.hashCode();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getsTime() {
		return sTime;
	}

	public void setsTime(long sTime) {
		this.sTime = sTime;
	}

	public long geteTime() {
		return eTime;
	}

	public void seteTime(long eTime) {
		this.eTime = eTime;
	}

	public String getsTimeString() {
		return sTimeString;
	}

	public void setsTimeString(String sTimeString) {
		this.sTimeString = sTimeString;
	}

	public String geteTimeString() {
		return eTimeString;
	}

	public void seteTimeString(String eTimeString) {
		this.eTimeString = eTimeString;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getNowTimes() {
		return nowTimes;
	}

	public void setNowTimes(int nowTimes) {
		this.nowTimes = nowTimes;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
