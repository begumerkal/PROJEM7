package com.wyd.empire.nearby.bean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * 附近玩家信息表
 * @author zgq
 */
@Entity()
@Table(name = "tab_player_info")
public class PlayerInfo extends BaseBean implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;// 角色附属信息id
	private int serviceId;// 角色所在的游戏服id
	private int playerId;// 角色id
	private String avatarURL;// 角色头像url
	private byte sex;// 角色性别
	private String playerName;// 角色名称
	private int fighting;// 角色战斗力
	private int longitude;// 玩家最近上传的经度（1000000倍）
	private int latitude;// 玩家最近上传的纬度（1000000倍）
	private String friendId;// 玩家好友
	private String suitHead;// 玩家装备头
    private String suitFace;// 玩家装备脸
	private boolean online;

	@Id()
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "assigned") 
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "service_id", precision = 10)
	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	@Basic()
	@Column(name = "player_id", precision = 10)
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Basic()
	@Column(name = "avatar_URL", length = 255)
	public String getAvatarURL() {
        return avatarURL == null ? "" : avatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}

    @Basic()
	@Column(name = "sex", precision = 3)
	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	@Basic()
	@Column(name = "player_name", length = 50)
	public String getPlayerName() {
		return playerName == null ? "" : playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Basic()
	@Column(name = "fighting", precision = 10)
	public int getFighting() {
		return fighting;
	}

	public void setFighting(int fighting) {
		this.fighting = fighting;
	}

	@Basic()
	@Column(name = "longitude", precision = 10)
	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	@Basic()
	@Column(name = "latitude", precision = 10)
	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	@Basic()
	@Column(name = "friend_id", length = 5000)
	public String getFriendId() {
		return null == friendId ? "" : friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	@Basic()
    @Column(name = "suit_head", length = 255)
    public String getSuitHead() {
        return suitHead == null ? "" : suitHead;
    }

    public void setSuitHead(String suitHead) {
        this.suitHead = suitHead;
    }

    @Basic()
    @Column(name = "suit_face", length = 255)
    public String getSuitFace() {
        return suitFace == null ? "" : suitFace;
    }

    public void setSuitFace(String suitFace) {
        this.suitFace = suitFace;
    }

    @Transient
    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}