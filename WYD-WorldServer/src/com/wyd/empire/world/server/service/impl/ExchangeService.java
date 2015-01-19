package com.wyd.empire.world.server.service.impl;

import java.util.Date;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.common.util.HttpClientUtil;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.exchange.ExchangeInfo;
import com.wyd.empire.world.exchange.ExchangeResult;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class ExchangeService {
	public static final int TYPE1 = 1; // 查询兑换物品
	public static final int TYPE2 = 2; // 返回兑换状态
	private Logger log = Logger.getLogger("exchangeLog");

	public void addExchangeInfo(int playerId, String code, int type, int playerLevel, int channel) {
		Exchange exchangeInfo = new Exchange();
		exchangeInfo.setPlayerId(playerId);
		exchangeInfo.setCode(code);
		exchangeInfo.setType(type);
		exchangeInfo.setLevel(playerLevel);
		exchangeInfo.setChannel(channel);
		ServiceManager.getManager().getHttpThreadPool().execute(createTask(exchangeInfo));
	}

	private Runnable createTask(Exchange exchangeInfo) {
		return new ExchangeThread(exchangeInfo);
	}

	public class ExchangeThread implements Runnable {
		private Exchange exchange;

		public ExchangeThread(Exchange exchange) {
			this.exchange = exchange;
		}

		@Override
		public void run() {
			try {
				ExchangeInfo exchangeInfo = new ExchangeInfo();
				exchangeInfo.setPlayerId(exchange.getPlayerId());
				exchangeInfo.setServiceId(ServiceManager.getManager().getConfiguration().getString("serviceid"));
				exchangeInfo.setCode(exchange.getCode().toUpperCase());
				exchangeInfo.setLevel(exchange.getLevel());
				exchangeInfo.setChannel(exchange.getChannel());
				String postUrl = ServiceManager.getManager().getConfiguration().getString("exchangeurl");
				if (exchange.getType() == TYPE1) {
					postUrl += "/exchange";
				} else {
					postUrl += "/success";
				}
				byte[] data = CryptionUtil.Encrypt(JSONObject.fromObject(exchangeInfo).toString(), ServiceManager.getManager()
						.getConfiguration().getString("deckey"));
				String receipt = HttpClientUtil.PostData(postUrl, data);
				JSONObject jsonObject = JSONObject.fromObject(receipt);
				@SuppressWarnings("static-access")
				ExchangeResult exchangeResult = (ExchangeResult) jsonObject.toBean(jsonObject, ExchangeResult.class);
				Mail mail;
				switch (exchangeResult.getCode()) {
					case 0 :
						if (exchange.getType() == TYPE1) {
							chargeReward(exchangeInfo.getPlayerId(), exchangeInfo.getCode(), exchangeResult.getMessage(),
									exchange.getLevel(), exchange.getChannel(), exchangeResult.getRewardType());
						}
						break;
					case -1 :
						// 保存邮件
						mail = new Mail();
						mail.setContent(TipMessages.EXCHANGFILLCOUNT);
						mail.setIsRead(false);
						mail.setReceivedId(exchangeInfo.getPlayerId());
						mail.setSendId(0);
						mail.setSendName(TipMessages.SYSNAME_MESSAGE);
						mail.setSendTime(new Date());
						mail.setTheme(TipMessages.EXCHANGFILLTITLE);
						mail.setType(1);
						mail.setBlackMail(false);
						mail.setIsStick(Common.IS_STICK);
						ServiceManager.getManager().getMailService().saveMail(mail, null);
						break;
					case 6 :
						mail = new Mail();
						mail.setContent(TipMessages.EXCHANGFAILLEVEL);
						mail.setIsRead(false);
						mail.setReceivedId(exchangeInfo.getPlayerId());
						mail.setSendId(0);
						mail.setSendTime(new Date());
						mail.setTheme(TipMessages.EXCHANGFILLTITLE);
						mail.setType(1);
						mail.setBlackMail(false);
						mail.setIsStick(Common.IS_STICK);
						ServiceManager.getManager().getMailService().saveMail(mail, null);
						break;
				}
				log.info(exchange.getType() + "---" + exchange.getCode() + ":" + exchangeResult.getCode() + "---"
						+ exchangeResult.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class Exchange {
		private int playerId;
		private String code;
		private int type;
		private int level; // 玩家的等级
		private int channel; // 玩家的渠道号

		public int getPlayerId() {
			return playerId;
		}

		public void setPlayerId(int playerId) {
			this.playerId = playerId;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getChannel() {
			return channel;
		}

		public void setChannel(int channel) {
			this.channel = channel;
		}
	}

	/**
	 * 兑换物品
	 * 
	 * @param playerId
	 * @param code
	 * @param rewardString
	 */
	public void chargeReward(int playerId, String code, String rewardString, int palyerLevel, int channel, int rewardType) {
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
			String mailContent;
			if (0 == rewardType) {
				List<RewardInfo> rfList = ServiceManager.getManager().getTaskService().getService()
						.sendSignReward(rewardString, player, 13, TradeService.ORIGIN_EXCHANGE_CODE, "兑换码奖励");
				mailContent = TipMessages.EXCHANGEMAIL;
				mailContent = mailContent.replace("###", code);
				StringBuffer content = new StringBuffer();
				for (RewardInfo ri : rfList) {
					if (ri.getItemId() == Common.EXPID) {
						content.append(TipMessages.EXP);
						content.append("*");
						content.append(ri.getCount());
						content.append(" ");
					} else if (ri.getItemId() == Common.GOLDID) {
						content.append(TipMessages.GOLD);
						content.append("*");
						content.append(ri.getCount());
						content.append(" ");
					} else if (ri.getItemId() == Common.DIAMONDID) {
						content.append(TipMessages.DIAMOND);
						content.append("*");
						content.append(ri.getCount());
						content.append(" ");
					} else {
						ShopItem shopItem = ServiceManager.getManager().getShopItemService().getShopItemById(ri.getItemId());
						if (null != shopItem) {
							content.append(shopItem.getName());
							content.append("*");
							content.append(ri.getCount());
							content.append(" ");
						}
					}
				}
				mailContent = mailContent.replace("***", content);
			} else {
				mailContent = rewardString;
			}
			// 保存邮件
			Mail mail = new Mail();
			mail.setContent(mailContent);
			mail.setIsRead(false);
			mail.setReceivedId(player.getId());
			mail.setSendId(0);
			mail.setSendName(TipMessages.SYSNAME_MESSAGE);
			mail.setSendTime(new Date());
			mail.setTheme(TipMessages.EXCHANGEMAILTITLE);
			mail.setType(1);
			mail.setBlackMail(false);
			mail.setIsStick(Common.IS_STICK);
			ServiceManager.getManager().getMailService().saveMail(mail, null);
			addExchangeInfo(playerId, code, TYPE2, palyerLevel, channel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
