package com.wyd.empire.world.server.service.base.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.DownloadReward;
import com.wyd.empire.world.bean.LotteryReward;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.RechargeReward;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.dao.IDownloadRewardDao;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IDownloadRewardService;
import com.wyd.empire.world.server.service.base.IShopItemService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class DownloadRewardService extends UniversalManagerImpl implements IDownloadRewardService {
	Logger log = Logger.getLogger(RechargeRewardService.class);
	List<RechargeReward> rechargeRewardList = null;
	List<RechargeReward> everyDayRechargeRewardList = null;
	List<LotteryReward> LotteryRewardList = null;
	/**
	 * The dao instance injected by Spring.
	 */
	private IDownloadRewardDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "DownloadRewardService";

	public DownloadRewardService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IDownloadRewardService getInstance(ApplicationContext context) {
		return (IDownloadRewardService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IDownloadRewardDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IDownloadRewardDao getDao() {
		return this.dao;
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		dao.initData();
	}

	public DownloadReward findDownloadReward(int playerLv) {
		return dao.findDownloadReward(playerLv);
	}

	/**
	 * 分包下载奖励,升级时调用
	 * 
	 * @param player
	 */
	public void sendDownloadReward(WorldPlayer player) {
		// 发放分包下载奖励
		try {
			Map<String, Integer> specialMarkMap = ServiceManager.getManager().getOperationConfigService().getSpecialMark();
			Integer downloadRewardSwitch = specialMarkMap.get("downloadRewardSwitch");
			downloadRewardSwitch = downloadRewardSwitch == null ? 1 : downloadRewardSwitch;
			if (player.getPlayer().getZsLevel() == 0 && downloadRewardSwitch == 1) {
				DownloadReward downloadRewardList = findDownloadReward(player.getPlayer().getLevel());
				if (null == downloadRewardList)
					return;
				ServiceManager.getManager().getTaskService().getService()
						.sendSignReward(downloadRewardList.getReward(), player, 24, TradeService.ORIGIN_DOWNLOADREWARD, "分包下载奖励");
				// 发邮件
				List<RewardInfo> rList = ServiceUtils.getRewardInfo(downloadRewardList.getReward(), player.getPlayer().getSex().intValue());
				StringBuffer rewardStr = new StringBuffer();
				IShopItemService shopItemService = ServiceManager.getManager().getShopItemService();
				for (RewardInfo rewardInfo : rList) {
					ShopItem item = shopItemService.getShopItemById(rewardInfo.getItemId());
					rewardStr.append("[");
					rewardStr.append(item.getName());
					rewardStr.append("]X");
					rewardStr.append(rewardInfo.getCount());
					rewardStr.append("、");
				}

				Mail mail = new Mail();
				mail.setContent(TipMessages.DOWNLOADREWARD.replace("XXXXXX", rewardStr.toString()));
				mail.setReceivedId(player.getId());
				mail.setSendId(0);
				mail.setSendName(TipMessages.SYSNAME_MESSAGE);
				mail.setSendTime(new Date());
				mail.setIsRead(false);
				mail.setType(1);
				mail.setBlackMail(false);
				mail.setIsStick(Common.IS_STICK);
				mail.setTheme(TipMessages.SYS_MAIL);
				ServiceManager.getManager().getMailService().saveMail(mail, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}