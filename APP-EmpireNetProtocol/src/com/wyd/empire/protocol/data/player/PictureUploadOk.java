package com.wyd.empire.protocol.data.player;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class PictureUploadOk extends AbstractData {
    private String  pictureUrl[]; 	 // 通过审核头像路径,多条之间“,”分割
    private int     age;         	 // 年龄
    private int     costellation;	 // 星座
    private String  personContext;	 // 个性签名
    private String  partner;     	 // 伴侣
    private int     fighting;    	 // 战斗力
    private boolean vipMark;    	 // 是否是vip
    private int     vipLevel;    	 // vip等级
    private String  headMessage; 	 // 头部装备信息
    private String  faceMessage; 	 // 脸部装备信息
    private int     fightingRank; 	 // 战力排行
    private String  pictureUrlTest[];  //待审核头像路径,多条之间“,”分割

    public PictureUploadOk(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PictureUploadOk, sessionId, serial);
    }

    public PictureUploadOk() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PictureUploadOk);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCostellation() {
        return costellation;
    }

    public void setCostellation(int costellation) {
        this.costellation = costellation;
    }

    public String getPersonContext() {
        return personContext;
    }

    public void setPersonContext(String personContext) {
        this.personContext = personContext;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public int getFighting() {
        return fighting;
    }

    public void setFighting(int fighting) {
        this.fighting = fighting;
    }

    public boolean isVipMark() {
        return vipMark;
    }

    public void setVipMark(boolean vipMark) {
        this.vipMark = vipMark;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getHeadMessage() {
        return headMessage;
    }

    public void setHeadMessage(String headMessage) {
        this.headMessage = headMessage;
    }

    public String getFaceMessage() {
        return faceMessage;
    }

    public void setFaceMessage(String faceMessage) {
        this.faceMessage = faceMessage;
    }

    public int getFightingRank() {
        return fightingRank;
    }

    public void setFightingRank(int fightingRank) {
        this.fightingRank = fightingRank;
    }

	public String[] getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String[] pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String[] getPictureUrlTest() {
		return pictureUrlTest;
	}

	public void setPictureUrlTest(String[] pictureUrlTest) {
		this.pictureUrlTest = pictureUrlTest;
	}


    

}
