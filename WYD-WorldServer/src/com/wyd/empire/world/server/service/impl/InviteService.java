package com.wyd.empire.world.server.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.invite.GetInviteInfoOk;
import com.wyd.empire.protocol.data.invite.GetInviteListOk;
import com.wyd.empire.protocol.data.invite.GetInviteRewardOk;
import com.wyd.empire.world.bean.InviteReward;
import com.wyd.empire.world.bean.InviteServiceInfo;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.common.util.HttpClientUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.invite.InviteAddInfo;
import com.wyd.empire.world.invite.InviteListResult;
import com.wyd.empire.world.invite.SelectInviteListInfo;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IInviteService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class InviteService {
	public String postUrl;
	/** 获取成功邀请的玩家数量 */
	public static final int INVITE_NUM = 0;
	/** 成功邀请玩家 */
	public static final int INVITE_ACT = 1;
	/** 获取成功邀请的玩家列表 */
	public static final int INVITE_LIS = 2;
	/** 玩家领取奖励 */
	public static final int INVITE_REW = 3;
	IInviteService inviteService;
	private Logger log = Logger.getLogger(InviteService.class);

	public InviteService(IInviteService inviteService) {
		this.inviteService = inviteService;
	}

	public IInviteService getIInviteService() {
		return inviteService;
	}

	public void addInviteInfo(WorldPlayer player, int type, int pageIndex, int pageSize) throws InterruptedException {
		if (null == postUrl) {
			postUrl = ServiceManager.getManager().getConfiguration().getString("exchangeurl");
			if (null == postUrl)
				return;
		}
		if (type == INVITE_ACT) {
			if (null != player.getPlayer().getBindInviteCode() && player.getPlayer().getBindInviteCode().length() > 0
					&& !player.getPlayer().getBindInviteCode().equals("abc")) {
				ServiceManager.getManager().getHttpThreadPool().execute(createTask(player, type, pageIndex, pageSize));
			}
		} else {
			ServiceManager.getManager().getHttpThreadPool().execute(createTask(player, type, pageIndex, pageSize));
		}
	}

	private Runnable createTask(WorldPlayer worldPlayer, int type, int pageIndex, int pageSize) {
		return new InviteThread(worldPlayer, type, pageIndex, pageSize);
	}

	public class InviteThread implements Runnable {
		private WorldPlayer worldPlayer;
		private int pageIndex;
		private int pageSize;
		private int type;

		public InviteThread(WorldPlayer worldPlayer, int type, int pageIndex, int pageSize) {
			this.worldPlayer = worldPlayer;
			this.type = type;
			this.pageIndex = pageIndex;
			this.pageSize = pageSize;
		}

		@SuppressWarnings("static-access")
		public void run() {
			try {
				String url;
				byte[] data;
				InviteServiceInfo serviceInfo;
				InviteReward inviteReward;
				String inviteCode;
				String receipt;
				int num;
				int index = 0;
				switch (type) {
					case INVITE_NUM :
						url = postUrl + "/invitenum";
						inviteCode = InviteService.this.makeInviteCode(worldPlayer);
						data = CryptionUtil.Encrypt(inviteCode, ServiceManager.getManager().getConfiguration().getString("deckey"));
						receipt = HttpClientUtil.PostData(url, data);
						num = Integer.parseInt(receipt);
						if (0 == worldPlayer.getPlayer().getInviteGrade()) {
							worldPlayer.getPlayer().setInviteGrade(1);
						}
						serviceInfo = inviteService.getInviteServiceInfo();
						inviteReward = inviteService.getInviteReward(worldPlayer.getPlayer().getInviteGrade());
						if (null == inviteReward) {
							inviteReward = inviteService.getInviteReward(worldPlayer.getPlayer().getInviteGrade() - 1);
						}
						List<String> nameList = new ArrayList<String>();
						List<String> icnoList = new ArrayList<String>();
						List<Integer> countList = new ArrayList<Integer>();
						String[] itemIds = inviteReward.getRewardItemId().split(",");
						String[] names = inviteReward.getRewardItemName().split(",");
						String[] icnos = inviteReward.getRewardItemIcon().split(",");
						String[] counts = inviteReward.getRewardItemNum().split(",");
						index = 0;
						for (String idsex : itemIds) {
							int sex = Integer.parseInt(idsex.split("\\|")[1]);
							if (worldPlayer.getPlayer().getSex() == sex || 2 == sex) {
								nameList.add(names[index]);
								icnoList.add(icnos[index]);
								// System.out.println("-----------------"+counts[index].split("\\|")[0]);
								countList.add(Integer.parseInt(counts[index].split("\\|")[0]));
							}
							index++;
						}
						GetInviteInfoOk getInviteInfoOk = new GetInviteInfoOk();
						getInviteInfoOk.setInviteCode(inviteCode);
						getInviteInfoOk.setTotal(inviteReward.getInviteNum());
						getInviteInfoOk.setAmount(num);
						getInviteInfoOk.setNames(nameList.toArray(new String[0]));
						getInviteInfoOk.setRewards(icnoList.toArray(new String[0]));
						getInviteInfoOk.setCounts(ServiceUtils.getInts(countList.toArray()));
						getInviteInfoOk.setRemark(serviceInfo.getRewardRemark());
						if (inviteReward.getRewardGrade() == worldPlayer.getPlayer().getInviteGrade() && num >= inviteReward.getInviteNum()) {
							getInviteInfoOk.setCanReward(true);
						} else {
							getInviteInfoOk.setCanReward(false);
						}
						getInviteInfoOk.setBindInviteCode(worldPlayer.getPlayer().getBindInviteCode());
						worldPlayer.sendData(getInviteInfoOk);
						break;
					case INVITE_ACT :
						serviceInfo = inviteService.getInviteServiceInfo();
						if (worldPlayer.getLevel() == serviceInfo.getInviteLevel()) {
							url = postUrl + "/invite";
							String bindInviteCode = worldPlayer.getPlayer().getBindInviteCode();
							if (null != bindInviteCode && bindInviteCode.length() > 0) {
								InviteAddInfo inviteAddInfo = new InviteAddInfo();
								inviteAddInfo.setInviteCode(InviteService.this.makeInviteCode(worldPlayer));
								inviteAddInfo.setBindInviteCode(bindInviteCode);
								inviteAddInfo.setPlayerName(worldPlayer.getName());
								inviteAddInfo.setServiceName(serviceInfo.getServiceName());
								data = CryptionUtil.Encrypt(JSONObject.fromObject(inviteAddInfo).toString(), ServiceManager.getManager()
										.getConfiguration().getString("deckey"));
								HttpClientUtil.PostData(url, data);
							}
						}
						break;
					case INVITE_LIS :
						url = postUrl + "/invitelist";
						SelectInviteListInfo sili = new SelectInviteListInfo();
						sili.setInviteCode(InviteService.this.makeInviteCode(worldPlayer));
						sili.setPageIndex(pageIndex);
						sili.setPageSize(pageSize);
						data = CryptionUtil.Encrypt(JSONObject.fromObject(sili).toString(), ServiceManager.getManager().getConfiguration()
								.getString("deckey"));
						String ret = HttpClientUtil.PostData(url, data);
						JSONObject jsonObject = JSONObject.fromObject(ret);
						InviteListResult inviteListResult = (InviteListResult) jsonObject.toBean(jsonObject, InviteListResult.class);
						GetInviteListOk getInviteListOk = new GetInviteListOk();
						getInviteListOk.setServiceName(inviteListResult.getServiceName().toArray(new String[0]));
						getInviteListOk.setPlayerName(inviteListResult.getPlayerName().toArray(new String[0]));
						getInviteListOk.setPageIndex(pageIndex);
						getInviteListOk.setPageCount(inviteListResult.getPageCount());
						worldPlayer.sendData(getInviteListOk);
						break;
					case INVITE_REW :
						url = postUrl + "/invitenum";
						if (0 == worldPlayer.getPlayer().getInviteGrade()) {
							worldPlayer.getPlayer().setInviteGrade(1);
						}
						serviceInfo = inviteService.getInviteServiceInfo();
						inviteReward = inviteService.getInviteReward(worldPlayer.getPlayer().getInviteGrade());
						if (null == inviteReward) {
							inviteReward = inviteService.getInviteReward(worldPlayer.getPlayer().getInviteGrade() - 1);
						}
						if (inviteReward.getRewardGrade() == worldPlayer.getPlayer().getInviteGrade()) {
							inviteCode = InviteService.this.makeInviteCode(worldPlayer);
							data = CryptionUtil.Encrypt(inviteCode, ServiceManager.getManager().getConfiguration().getString("deckey"));
							receipt = HttpClientUtil.PostData(url, data);
							num = Integer.parseInt(receipt);
							if (inviteReward.getRewardGrade() == worldPlayer.getPlayer().getInviteGrade()
									&& num >= inviteReward.getInviteNum()) {
								worldPlayer.getPlayer().setInviteGrade(inviteReward.getRewardGrade() + 1);
								ServiceManager.getManager().getPlayerService().savePlayerData(worldPlayer.getPlayer());
								String[] rwIds = inviteReward.getRewardItemId().split(",");
								String[] nums = inviteReward.getRewardItemNum().split(",");
								String[] levels = inviteReward.getRewardItemLevel().split(",");
								index = 0;
								for (String idsex : rwIds) {
									int itemId = Integer.parseInt(idsex.split("\\|")[0]);
									int sex = Integer.parseInt(idsex.split("\\|")[1]);
									if (worldPlayer.getPlayer().getSex() == sex || 2 == sex) {
										num = Integer.parseInt(nums[index].split("\\|")[0]);
										switch (itemId) {
											case Common.DIAMONDID :
												ServiceManager.getManager().getPlayerService()
														.addTicket(worldPlayer, num, 0, TradeService.ORIGIN_INVITE, 0, "", "邀请玩家", "", "");
												break;
											case Common.GOLDID :
												ServiceManager.getManager().getPlayerService()
														.updatePlayerGold(worldPlayer, num, "邀请玩家", "邀请玩家");
												break;
											default :
												int count = -1;
												int day = -1;
												if (1 == Integer.parseInt(nums[index].split("\\|")[1])) {
													count = num;
												} else {
													day = num;
												}
												ServiceManager.getManager().getRechargeRewardService()
														.givenItems(worldPlayer, count, day, itemId, Integer.parseInt(levels[index]), null);
												break;
										}
									}
									index++;
								}
								GetInviteRewardOk getInviteRewardOk = new GetInviteRewardOk();
								worldPlayer.sendData(getInviteRewardOk);
								GameLogService.inviteReward(worldPlayer.getId(), worldPlayer.getLevel(), inviteReward.getId(), "("
										+ inviteReward.getRewardItemId() + ")(" + inviteReward.getRewardItemNum() + ")");
							}
						}
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e, e);
			}
		}
	}

	/**
	 * 生成玩家的邀请码
	 * 
	 * @param worldPlayer
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String makeInviteCode(WorldPlayer worldPlayer) throws UnsupportedEncodingException {
		String inviteCode = worldPlayer.getPlayer().getInviteCode();
		if (null == inviteCode || inviteCode.length() < 1) {
			inviteCode = Integer.toHexString(ServiceManager.getManager().getConfiguration().getInt("serviceid")) + "N"
					+ Integer.toHexString(worldPlayer.getId());
			inviteCode = inviteCode.toUpperCase();
			// inviteCode =
			// CryptionUtil.bytesToHexString(CryptionUtil.Encrypt(inviteCode,
			// ServiceManager.getManager().getConfiguration().getString("deckey")));
			worldPlayer.getPlayer().setInviteCode(inviteCode);
			ServiceManager.getManager().getPlayerService().savePlayerData(worldPlayer.getPlayer());
		}
		return inviteCode;
	}
}
