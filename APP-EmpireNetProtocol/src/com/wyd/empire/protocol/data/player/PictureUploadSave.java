package com.wyd.empire.protocol.data.player;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class PictureUploadSave extends AbstractData {
	/**
	 * 头像路径 格式为:新地址#更改前原地址 没有为-1
	 * 【例如： 192.168.1.6#-1或192.168.1.6#192.168.1.8】
	 */
	private String pictureUrlTest[];
	private int age;                    //年龄
	private int constellation;			//星座
	private String personContext;		//个性签名
	

	public PictureUploadSave(int sessionId, int serial) {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PictureUploadSave,
				sessionId, serial);
	}

	public PictureUploadSave() {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PictureUploadSave);
	}


	public String[] getPictureUrlTest() {
		return pictureUrlTest;
	}

	public void setPictureUrlTest(String[] pictureUrlTest) {
		this.pictureUrlTest = pictureUrlTest;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getConstellation() {
		return constellation;
	}

	public void setConstellation(int constellation) {
		this.constellation = constellation;
	}

	public String getPersonContext() {
		return personContext;
	}

	public void setPersonContext(String personContext) {
		this.personContext = personContext;
	}

	
}
