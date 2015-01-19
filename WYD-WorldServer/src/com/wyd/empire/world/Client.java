package com.wyd.empire.world;

public class Client {
	private int sessionId = -1;
	private int accountId = -1;
	private int gameAccountId = -1;
	private int playerId = -1;
	private int tokenAmount = 0;
	private String name;
	private String udid;
	private String password;
	private int flg;
	private STATUS status = STATUS.INIT;
	private int channel;
	private String uin;
	private String ip;// 客户端IP

	public Client(int sessionId) {
		this.sessionId = sessionId;

	}

	public boolean isLogin() {
		return ((this.status == STATUS.LOGIN) || (this.status == STATUS.PLAYERLOGIN) || (this.status == STATUS.CREATEPLAYE));
	}

	/**
	 * 判断用户是否已经登录
	 * 
	 * @return <tt>true</tt> 已经登录<br>
	 *         <tt>false</tt> 没有登录
	 */
	public boolean isPlayerLogin() {
		return (this.status == STATUS.PLAYERLOGIN);
	}

	/**
	 * 折扣比例
	 * 
	 * @return
	 */
	public int getDiscount() {
		return 100;
	}

	public static enum STATUS {
		INIT, LOGIN, PLAYERLOGIN, LOGOUT, CREATEPLAYE;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getGameAccountId() {
		return gameAccountId;
	}

	public void setGameAccountId(int gameAccountId) {
		this.gameAccountId = gameAccountId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getFlg() {
		return flg;
	}

	public void setFlg(int flg) {
		this.flg = flg;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public void setTokenAmount(int tokenAmount) {
		this.tokenAmount = tokenAmount;
	}

	public int getTokenAmount() {
		return tokenAmount;
	}

	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}