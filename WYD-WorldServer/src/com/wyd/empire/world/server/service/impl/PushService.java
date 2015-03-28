package com.wyd.empire.world.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.system.EarthPush;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.HttpClientUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class PushService {
	private static final String KEY = "ksdf6545glJ45Klk";
	private Logger log;

	public PushService() {
		log = Logger.getLogger(PushService.class);
	}

	/**
	 * EFUN地推
	 * 
	 * @param player
	 * @throws InterruptedException
	 */
	public void addPushInfo(WorldPlayer player) throws InterruptedException {
		if (20 == player.getLevel() && null != ServiceManager.getManager().getConfiguration().getString("pushurl")) {
			ServiceManager.getManager().getHttpThreadPool().execute(createPushTask(player));
		}
	}

	private Runnable createPushTask(WorldPlayer player) {
		return new PushThread(player);
	}

	/**
	 * EFUN等级通知
	 * 
	 * @param player
	 * @throws InterruptedException
	 */
	public void addPushLevelInfo(WorldPlayer player) throws InterruptedException {
		if (5 == player.getLevel() || 10 == player.getLevel()) {
			if (null != ServiceManager.getManager().getConfiguration().getString("pushlevelurl")) {
				ServiceManager.getManager().getHttpThreadPool().execute(createPushLevelTask(player));
			}
		}
	}

	private Runnable createPushLevelTask(WorldPlayer player) {
		return new PushLevelThread(player);
	}

	// 地推活动检测
	public class PushThread implements Runnable {
		private WorldPlayer player;

		public PushThread(WorldPlayer player) {
			this.player = player;
		}

		public void run() {
			String url = ServiceManager.getManager().getConfiguration().getString("pushurl");
			String userKey = player.getClient().getName().replace("EFHK_", "");
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(new NameValuePair("mac", player.getPlayer().getMac()));
			data.add(new NameValuePair("userKey", userKey));
			data.add(new NameValuePair("userId", player.getId() + ""));
			data.add(new NameValuePair("userName", player.getName() + ""));
			data.add(new NameValuePair("level", player.getLevel() + ""));
			String keyValue = player.getPlayer().getMac() + userKey + player.getId() + player.getName() + player.getLevel() + KEY;
			data.add(new NameValuePair("verify", ServiceUtils.getMD5(keyValue)));
			String retData = "";
			try {
				retData = HttpClientUtil.PostData(url + "/check", data);
			} catch (HttpException e) {
				log.error(e, e);
			} catch (IOException e) {
				log.error(e, e);
			}
			if (retData.startsWith("{")) {
				JSONObject jsObj = JSONObject.fromObject(retData);
				int status = jsObj.getInt("status");
				if (0 == status) {
					JSONObject pushInfo = JSONObject.fromObject(jsObj.getString("msg"));
					WorldPlayer player = ServiceManager.getManager().getPlayerService().getLoadPlayer(pushInfo.getInt("userId"));
					int channel = player.getPlayer().getChannel();
					if (1120 != channel && 1121 != channel) {
						return;
					}
					String dimCode = pushInfo.getString("dimCode");
					if (dimCode.equals("quanjia")) {
						// 保存邮件
						Mail mail = new Mail();
						if (player.getLevel() < 20) {
							mail.setTheme("中杯美式咖啡兌換碼");
							mail.setContent("全家送您“中杯美式咖啡”一份。兌換碼：" + jsObj.getString("rcode") + ",前1000名到達20級還有1000元兌獎卷哦。");
						} else {
							mail.setTheme("全家1000元兌換劵");
							mail.setContent("彈彈島OL送您【全家1000元兌換券】：" + jsObj.getString("rcode") + ",更多驚喜，盡在彈彈島OL。");
						}
						mail.setIsRead(false);
						mail.setReceivedId(player.getId());
						mail.setSendId(0);
						mail.setSendName(TipMessages.SYSNAME_MESSAGE);
						mail.setSendTime(new Date());
						mail.setType(1);
						mail.setBlackMail(false);
						mail.setIsStick(Common.IS_STICK);
						ServiceManager.getManager().getMailService().saveMail(mail, null);
					} else {
						// 保存邮件
						Mail mail = new Mail();
						String rewardName;
						String rewardUserId = pushInfo.getString("userKey");
						String rewardUserKey = pushInfo.getString("mac");
						String gameCode;
						String activityCode = "dddSpread";
						String verifyKey;
						String adsid = dimCode;
						if (1121 == channel) {
							gameCode = "bmhk";
						} else {
							gameCode = "bmhkios";
						}
						if (player.getLevel() < 20) {
							mail.setTheme("7-ELEVEN 減$5優惠活動兌獎郵件");
							mail.setContent("親愛的用戶：您可以通過【確認】按鈕再次打開減$5優惠兌獎頁面哦！");
							rewardName = "offers5yuan";
						} else {
							mail.setTheme("7-ELEVEN 現金券HK$200兌獎郵件");
							mail.setContent("親愛的用戶：您可以通過【確認】按鈕再次打開HK$200現金劵兌獎頁面哦！");
							rewardName = "reward200yuan";
						}
						verifyKey = activityCode + adsid + gameCode + rewardName + rewardUserKey + rewardUserId + "lsdfjqzdfe";
						StringBuffer showUrl = new StringBuffer("http://activity.efun.com/spread.spreadDdd.shtml?rewardName=");
						showUrl.append(rewardName);
						showUrl.append("&rewardUserId=");
						showUrl.append(rewardUserId);
						showUrl.append("&rewardUserKey=");
						showUrl.append(rewardUserKey);
						showUrl.append("&gameCode=");
						showUrl.append(gameCode);
						showUrl.append("&activityCode=");
						showUrl.append(activityCode);
						showUrl.append("&verifyKey=");
						showUrl.append(ServiceUtils.getMD5(verifyKey));
						showUrl.append("&adsid=");
						showUrl.append(adsid);
						mail.setIsRead(false);
						mail.setReceivedId(player.getId());
						mail.setSendId(0);
						mail.setSendName(TipMessages.SYSNAME_MESSAGE);
						mail.setSendTime(new Date());
						mail.setType(1);
						mail.setBlackMail(false);
						mail.setIsStick(Common.IS_STICK);
						mail.setRemark(showUrl.toString());
						ServiceManager.getManager().getMailService().saveMail(mail, null);
						EarthPush earthPush = new EarthPush();
						earthPush.setShowUrl(showUrl.toString());
						earthPush.setRemark("");
						player.sendData(earthPush);
					}
				}
			}
		}
	}

	// 推送等级信息
	public class PushLevelThread implements Runnable {
		private WorldPlayer player;

		public PushLevelThread(WorldPlayer player) {
			this.player = player;
		}

		@Override
		public void run() {
			String url = ServiceManager.getManager().getConfiguration().getString("pushlevelurl");
			String userId = player.getClient().getName().replace("EFHK_", "");
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(new NameValuePair("gameCode", "bmhk"));
			data.add(new NameValuePair("userid", userId));
			data.add(new NameValuePair("level", player.getLevel() + ""));
			data.add(new NameValuePair("roleid", player.getId() + ""));
			data.add(new NameValuePair("roleName", player.getName()));
			data.add(new NameValuePair("serverCode", WorldServer.config.getMachineCode() + ""));
			try {
				HttpClientUtil.PostData(url, data);
			} catch (HttpException e) {
				log.error(e, e);
			} catch (IOException e) {
				log.error(e, e);
			}
		}
	}
}
