package com.wyd.empire.world.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.wyd.empire.world.bean.FullServiceReward;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IRewardItemsService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class TheadFullServiceRewardService implements Runnable {
	private IRewardItemsService rewardItemsService = null;
	/**
	 * 线程执行间隔时间
	 */
	public long TIME = 1000 * 60 * 60 * 5;
	/**
	 * 读取页码最大值
	 */
	public static final int MAX_NUM = 10000;
	/**
	 * 读取数最大值
	 */
	public static final int PAGE_NUM = 1000;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public TheadFullServiceRewardService(IRewardItemsService rewardItemsService) {
		this.rewardItemsService = rewardItemsService;
		start();
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName("TheadFullServiceRewardService-Thread");
		t.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("TheadFullServiceRewardService-Thread");
				List<FullServiceReward> list = rewardItemsService.findFullServiceRewardByStatus();
				for (FullServiceReward fullServiceReward : list) {
					ServiceManager.getManager().getSimpleThreadPool().execute(createTask(fullServiceReward));
				}
				Thread.sleep(TIME);
			} catch (InterruptedException ex) {
			}
		}
	}

	private Runnable createTask(FullServiceReward fullServiceReward) {
		return new FullServiceRewardThread(fullServiceReward);
	}

	public class FullServiceRewardThread implements Runnable {
		private FullServiceReward fullServiceReward;

		public FullServiceRewardThread(FullServiceReward fullServiceReward) {
			this.fullServiceReward = fullServiceReward;
		}

		@Override
		public void run() {
			int pageNum = 0;
			if (fullServiceReward.getCurrentCount() != 0) {
				pageNum = fullServiceReward.getCurrentCount();
			}
			try {
				while (pageNum != MAX_NUM) {
					System.out.println("Thead - Sending . . .");
					List<Object> list = ServiceManager
							.getManager()
							.getIPlayerService()
							.getPlayerByLevel(fullServiceReward.getMinLevel(), fullServiceReward.getMaxLevel(), pageNum * PAGE_NUM,
									PAGE_NUM, sdf.format(fullServiceReward.getStartTime()), sdf.format(fullServiceReward.getEndTime()));
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService()
									.getWorldPlayerById(Integer.parseInt(obj.toString()));
							// 获得奖励列表
							List<RewardInfo> rewardList = ServiceUtils.getRewardInfo(fullServiceReward.getItemReward(), worldPlayer
									.getPlayer().getSex().intValue());
							for (RewardInfo rewardVo : rewardList) {
								if (rewardVo.getItemId() == Common.EXPID) {
									ServiceManager.getManager().getPlayerService().updatePlayerEXP(worldPlayer, rewardVo.getCount());
								} else if (rewardVo.getItemId() == Common.GOLDID) {
									ServiceManager.getManager().getPlayerService()
											.updatePlayerGold(worldPlayer, rewardVo.getCount(), "全服奖励", "-- " + " --");
								} else if (rewardVo.getItemId() == Common.DIAMONDID) {
									ServiceManager
											.getManager()
											.getPlayerService()
											.addTicket(worldPlayer, rewardVo.getCount(), 0, TradeService.ORIGIN_GM, 0, "", "全服奖励发放", "", "");
								} else {
									if (rewardVo.getCount() > 1000) {
										throw new Exception("物品给予数据异常");
									}
									if (rewardVo.isAddDay()) {
										ServiceManager
												.getManager()
												.getRechargeRewardService()
												.givenItems(worldPlayer, -1, rewardVo.getCount(), rewardVo.getItemId(),
														rewardVo.getLevel(), "全服奖励");
									} else {
										ServiceManager
												.getManager()
												.getRechargeRewardService()
												.givenItems(worldPlayer, rewardVo.getCount(), -1, rewardVo.getItemId(),
														rewardVo.getLevel(), "全服奖励");
									}
								}
							}
							// 保存邮件
							Mail mail = new Mail();
							mail.setTheme(fullServiceReward.getMailTitle());
							mail.setContent(fullServiceReward.getMailContent());
							mail.setIsRead(false);
							mail.setReceivedId(Integer.parseInt(obj.toString()));
							mail.setSendId(0);
							mail.setSendName(TipMessages.SYSNAME_MESSAGE);
							mail.setSendTime(new Date());
							mail.setType(1);
							mail.setBlackMail(false);
							mail.setIsStick(Common.IS_STICK);
							ServiceManager.getManager().getMailService().saveMail(mail, null);
						}
						pageNum++;
						list = null;
						fullServiceReward.setCurrentCount(pageNum + 1);
						ServiceManager.getManager().getRechargeRewardService().update(fullServiceReward);
					} else {
						pageNum = MAX_NUM;
						fullServiceReward.setIsAll(Common.LOG_AWARD_STATUS_Y);
						fullServiceReward.setCurrentCount(0);
						ServiceManager.getManager().getRechargeRewardService().update(fullServiceReward);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
