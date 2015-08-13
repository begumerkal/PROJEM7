package com.app.empire.protocol.data.chat;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class ReceiveMessage extends AbstractData {
	private int    channel;
	private int sendId; 
	private String sendName;
	private int    reveId;
	private String reveName;
	private String message;
	private String time;
	//聊天类型：
	//0普通聊天
    //1普通公告
	//2结婚公告
	//3世界BOSS
	//4弹王挑战赛
    //5跨服对战聊天
	private int    chatType;
	//聊天子协议：
	//0普通聊天（1：普通聊天，2：彩色聊天）
    //1普通公告（1：普通公告，2：彩色公告）
	//2结婚公告（1：普通婚礼,2：浪漫婚礼，3：豪华婚礼，4：奢华婚礼）
	//3世界BOSS（1：世界BOSS开启,2：世界BOSS关闭）
	//4弹王挑战赛（1：弹王开始,2：弹王结束）
	private int  chatSubType;
	private int vipLevel;//发送人vip等级（系统的默认为0）
	
	public ReceiveMessage(int sessionId, int serial) {
		super(Protocol.MAIN_CHAT, Protocol.CHAT_ReceiveMessage, sessionId, serial);
	}

	public ReceiveMessage() {
		super(Protocol.MAIN_CHAT, Protocol.CHAT_ReceiveMessage);
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getReveName() {
		return reveName;
	}

	public void setReveName(String reveName) {
		this.reveName = reveName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getSendId() {
		return sendId;
	}

	public void setSendId(int sendId) {
		this.sendId = sendId;
	}

	public int getReveId() {
		return reveId;
	}

	public void setReveId(int reveId) {
		this.reveId = reveId;
	}

	public int getChatType() {
		return chatType;
	}

	public void setChatType(int chatType) {
		this.chatType = chatType;
	}
	
    public int getChatSubType() {
        return chatSubType;
    }

    public void setChatSubType(int chatSubType) {
        this.chatSubType = chatSubType;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }
}
