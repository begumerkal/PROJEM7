package com.wyd.empire.world.server.service.impl;

import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.challenge.SendRankList;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.InteRank;
import com.wyd.empire.world.bean.Integral;
import com.wyd.empire.world.bean.IntegralArea;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.common.util.HttpClientUtil;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class IntegralService {
	public static String postUrl;
	/** 获取弹王挑战赛相关信息 */
	public static final int INTEGRAL_GET_INFO = 0;
	/** 增加弹王挑战赛积分 */
	public static final int INTEGRAL_UPDATE = 1;
	/** 获取弹王挑战赛积分前30名 */
	public static final int INTEGRAL_TOP_THIRTY = 2;
	/** 更新弹王挑战赛积分前3名 */
	public static final int INTEGRAL_TOP_THREE = 3;
	/** 获取本赛季本服玩家的排名,赛季结束时发奖用 */
	public static final int INTEGRAL_ALL_LIST = 4;
	/** 清理弹王挑战赛积分 */
	public static final int INTEGRAL_CLEAN_UP = 5;
	private Logger log = Logger.getLogger(IntegralService.class);
	private Logger integralLog = Logger.getLogger("integralLog");
	/** 玩家获取积分排行榜 */
	private static SendRankList sendRankList;
	/** 积分排行榜最后更新时间 */
	private static long lastUpdateTime = 0;

	/**
	 * 执行弹王相关指令
	 * 
	 * @param type
	 */
	public void runOrder(int type) {
		ServiceManager.getManager().getHttpThreadPool().execute(createTask(type));
	}

	/**
	 * 获取弹王积分排名
	 * 
	 * @param worldPlayer
	 * @param type
	 */
	public void getRankList(WorldPlayer worldPlayer) {
		ServiceManager.getManager().getHttpThreadPool().execute(createTask(worldPlayer, INTEGRAL_TOP_THIRTY));
	}

	/**
	 * 玩家增加弹王积分
	 * 
	 * @param playerId
	 * @param playerName
	 * @param integral
	 * @param fighting
	 */
	public void addIntegral(WorldPlayer player, int integral) {
		IntegralInfo info = new IntegralInfo();
		info.setPlayerId(player.getId());
		info.setPlayerName(player.getName());
		info.setIntegral(integral);
		info.setFighting(player.getFighting());
		info.setServiceId(WorldServer.config.getMachineCode());
		info.setServiceName(WorldServer.config.getServerName());
		ServiceManager.getManager().getHttpThreadPool().execute(createTask(info, INTEGRAL_UPDATE));
	}

	private Runnable createTask(int type) {
		return new IntegralThread(type);
	}

	private Runnable createTask(WorldPlayer worldPlayer, int type) {
		return new IntegralThread(worldPlayer, type);
	}

	private Runnable createTask(IntegralInfo info, int type) {
		return new IntegralThread(info, type);
	}

	public class IntegralThread implements Runnable {
		private WorldPlayer worldPlayer;
		private int type;
		private IntegralInfo info;

		public IntegralThread(int type) {
			this.type = type;
		}

		public IntegralThread(IntegralInfo info, int type) {
			this.type = type;
			this.info = info;
		}

		public IntegralThread(WorldPlayer worldPlayer, int type) {
			this.worldPlayer = worldPlayer;
			this.type = type;
		}

		@SuppressWarnings({"deprecation", "unchecked"})
		public void run() {
			try {
				if (null == postUrl) {
					postUrl = ServiceManager.getManager().getConfiguration().getString("exchangeurl");
					if (null == postUrl)
						return;
				}
				String url;
				byte[] data;
				JSONArray jsonArray;
				String ret;
				switch (type) {
					case INTEGRAL_GET_INFO :
						url = postUrl + "/integralInfo";
						data = CryptionUtil.Encrypt(WorldServer.config.getMachineCode() + "", ServiceManager.getManager().getConfiguration()
								.getString("deckey"));
						int i = 0;
						boolean check = false;
						// 如果获取配置失败重新尝试10次
						while (!check && i < 10) {
							try {
								i++;
								ret = HttpClientUtil.PostData(url, data);
								if (!"fail".equals(ret)) {
									log.info("加载弹王配置:" + ret);
									com.wyd.empire.world.server.service.base.impl.ChallengeService.initConfig(ret);
									check = true;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (!check && i < 10) {
								Thread.sleep(1000 * i * i);
							}
						}
						if (!check) {
							integralLog.info("get integral config fall serverId:" + WorldServer.config.getMachineCode()+ "  url:" + url);
						}
						break;
					case INTEGRAL_UPDATE :
						url = postUrl + "/updateintegral";
						data = CryptionUtil.Encrypt(JSONObject.fromObject(info).toString(), ServiceManager.getManager().getConfiguration()
								.getString("deckey"));
						HttpClientUtil.PostData(url, data);
						break;
					case INTEGRAL_TOP_THIRTY :
						// 30秒内只刷新一次列表
						if ((System.currentTimeMillis() - lastUpdateTime) > 30000) {
							url = postUrl + "/topthirtylist";
							data = CryptionUtil.Encrypt(WorldServer.config.getMachineCode() + "", ServiceManager.getManager().getConfiguration()
									.getString("deckey"));
							ret = HttpClientUtil.PostData(url, data);
							jsonArray = JSONArray.fromObject(ret);
							List<Integral> list = (List<Integral>) JSONArray.toCollection(jsonArray, Integral.class);
							int[] integral = new int[list.size()];
							String[] area = new String[list.size()];
							String[] playerName = new String[list.size()];
							Integral integralBean;
							for (i = 0; i < list.size(); i++) {
								integralBean = list.get(i);
								integral[i] = integralBean.getIntegral();
								area[i] = integralBean.getServiceName();
								playerName[i] = integralBean.getPlayerName();
							}
							SendRankList rankList = new SendRankList();
							rankList.setAreaName(area);
							rankList.setIntegral(integral);
							rankList.setPlayerName(playerName);
							sendRankList = rankList;
							lastUpdateTime = System.currentTimeMillis();
						}
						SendRankList rankList = new SendRankList();
						rankList.setAreaName(sendRankList.getAreaName());
						rankList.setPlayerName(sendRankList.getPlayerName());
						rankList.setIntegral(sendRankList.getIntegral());
						worldPlayer.sendData(rankList);
						break;
					case INTEGRAL_TOP_THREE :
						url = postUrl + "/topthreelist";
						data = CryptionUtil.Encrypt(WorldServer.config.getMachineCode() + "", ServiceManager.getManager().getConfiguration()
								.getString("deckey"));
						ret = HttpClientUtil.PostData(url, data);
						jsonArray = JSONArray.fromObject(ret);
						List<Integral> inteList = (List<Integral>) JSONArray.toCollection(jsonArray, Integral.class);
						StringBuffer string = new StringBuffer();
						for (Integral in : inteList) {
							string.append(in.getPlayerName());
							string.append(",");
							string.append(in.getFighting());
							string.append(",");
							string.append(in.getServiceName());
							string.append("#");
						}
						if (string.length() > 0)
							string.deleteCharAt(string.length() - 1);
						Common.CHALLENGE_RANK = string.toString();
						break;
					case INTEGRAL_ALL_LIST :
						url = postUrl + "/rankbyservice";
						data = CryptionUtil.Encrypt(WorldServer.config.getMachineCode() + "", ServiceManager.getManager().getConfiguration()
								.getString("deckey"));
						i = 0;
						check = false;
						// 如果发送奖励失败重新尝试10次
						while (!check && i < 10) {
							try {
								i++;
								ret = HttpClientUtil.PostData(url, data);
								jsonArray = JSONArray.fromObject(ret);
								List<InteRank> irList = (List<InteRank>) JSONArray.toList(jsonArray, InteRank.class);
								IntegralArea ia;
								ShopItem si;
								for (InteRank ir : irList) {
									try {
										if (ir.getRankNum() == 1) {
											worldPlayer = ServiceManager.getManager().getPlayerService()
													.getWorldPlayerById(ir.getPlayerId());
											if (null != worldPlayer) {
												// 记录成就
												ServiceManager.getManager().getTitleService().inteRankFirst(worldPlayer);
											}
										}
										ia = ServiceManager.getManager().getChallengeSerService().getIntegralAreaByRank(ir.getRankNum());
										si = ServiceManager.getManager().getShopItemService().getShopItemById(ia.getReward_prop_id());
										ServiceManager.getManager().getPlayerItemsFromShopService()
												.playerGetItem(ir.getPlayerId(), ia.getReward_prop_id(), -1, -1, 1, 23, "挑战赛奖励", 0, 0, 0);
										sendMail(ir.getPlayerId(), TipMessages.CHALLENGE_MAIL_SEASON.replace("{1}", ir.getRankNum() + "")
												.replace("{2}", si.getName()));
									} catch (Exception e) {
										integralLog.info("send reward fall playerId:" + ir.getPlayerId() + "  rank:" + ir.getRankNum());
										e.printStackTrace();
									}
								}
								check = true;
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (!check && i < 10) {
								Thread.sleep(1000 * i * i);
							}
						}
						if (!check) {
							integralLog.info("send reward all fall serverId:" + WorldServer.config.getMachineCode() + "  url:" + url);
						}
						break;
					case INTEGRAL_CLEAN_UP :
						url = postUrl + "/cleanupintegral";
						data = CryptionUtil.Encrypt(WorldServer.config.getMachineCode() + "", ServiceManager.getManager().getConfiguration()
								.getString("deckey"));
						i = 0;
						check = false;
						// 如果删除积分失败重新尝试10次
						while (!check && i < 10) {
							try {
								i++;
								ret = HttpClientUtil.PostData(url, data);
								if ("success".equals(ret)) {
									check = true;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (!check && i < 10) {
								Thread.sleep(1000 * i * i);
							}
						}
						if (!check) {
							integralLog.info("clean up integral fall serverId:" + WorldServer.config.getMachineCode() + "  url:" + url);
						}
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e, e);
			}
		}
	}

	public class IntegralInfo {
		private int playerId;
		private String playerName;
		private int integral;
		private int fighting;
		private int serviceId;
		private String serviceName;

		public int getPlayerId() {
			return playerId;
		}

		public void setPlayerId(int playerId) {
			this.playerId = playerId;
		}

		public String getPlayerName() {
			return playerName;
		}

		public void setPlayerName(String playerName) {
			this.playerName = playerName;
		}

		public int getIntegral() {
			return integral;
		}

		public void setIntegral(int integral) {
			this.integral = integral;
		}

		public int getFighting() {
			return fighting;
		}

		public void setFighting(int fighting) {
			this.fighting = fighting;
		}

		public int getServiceId() {
			return serviceId;
		}

		public void setServiceId(int serviceId) {
			this.serviceId = serviceId;
		}

		public String getServiceName() {
			return serviceName;
		}

		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}
	}

	private void sendMail(int playerId, String content) {
		Mail mail = new Mail();
		mail.setContent(content);
		mail.setIsRead(false);
		mail.setReceivedId(playerId);
		mail.setSendId(0);
		mail.setSendName(TipMessages.SYSNAME_MESSAGE);
		mail.setSendTime(new Date());
		mail.setTheme(TipMessages.CHALLENGE_MAIL_THEME);
		mail.setType(1);
		mail.setBlackMail(false);
		mail.setIsStick(Common.IS_STICK);
		ServiceManager.getManager().getMailService().saveMail(mail, null);
	}
}
