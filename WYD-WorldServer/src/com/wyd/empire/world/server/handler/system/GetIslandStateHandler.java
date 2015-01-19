package com.wyd.empire.world.server.handler.system;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.system.GetIslandStateOk;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.ButtonInfo;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class GetIslandStateHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetIslandStateHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			// 获得特殊标示
			Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
			GetIslandStateOk getIslandStateOk = new GetIslandStateOk(data.getSessionId(), data.getSerial());
			OperationConfig operationConfig = ServiceManager.getManager().getVersionService().getVersion();
			if (map.get("islandState") == null) {
				getIslandStateOk.setIslandState(0);
			} else {
				getIslandStateOk.setIslandState(map.get("islandState"));
			}
			if (map.get("openTapjoy") == null || map.get("openTapjoy") == 0) {
				getIslandStateOk.setOpenTapjoy(false);
			} else {
				getIslandStateOk.setOpenTapjoy(true);
			}
			if (map.get("openNewTeach") == null || map.get("openNewTeach") == 0) {
				getIslandStateOk.setOpeanNewTeach(false);
			} else {
				getIslandStateOk.setOpeanNewTeach(true);
			}
			if (map.get("bindAccLevel") == null) {
				getIslandStateOk.setBindAccLevel(5);
			} else {
				getIslandStateOk.setBindAccLevel(map.get("bindAccLevel"));
			}
			if (map.get("bindAccDelta") == null) {
				getIslandStateOk.setBindAccDelta(300);
			} else {
				getIslandStateOk.setBindAccDelta(map.get("bindAccDelta"));
			}
			getIslandStateOk.setNoviceType(operationConfig.getNoviceType());
			if (map.get("openSMSCode") == null || map.get("openSMSCode") == 0) {
				getIslandStateOk.setOpenSMSCode(false);
			} else {
				getIslandStateOk.setOpenSMSCode(true);
			}
			if (map.get("openNotice") == null || map.get("openNotice") == 0) {
				getIslandStateOk.setPopNotice(false);
			} else if (map.get("openNotice") == 1) {
				if (null == player || player.isEverydayFirstLogin()) {
					getIslandStateOk.setPopNotice(true);
				} else {
					getIslandStateOk.setPopNotice(false);
				}
			} else if (map.get("openNotice") == 2) {
				getIslandStateOk.setPopNotice(true);
			}
			if (map.get("openSquare") == null || map.get("openSquare") == 0) {
				getIslandStateOk.setPopGoldPeople(false);
			} else if (map.get("openSquare") == 1) {
				if (null == player || player.isEverydayFirstLogin()) {
					getIslandStateOk.setPopGoldPeople(true);
				} else {
					getIslandStateOk.setPopGoldPeople(false);
				}
			} else if (map.get("openSquare") == 2) {
				getIslandStateOk.setPopGoldPeople(true);
			}
			getIslandStateOk.setWorldChatExp(ServiceManager.getManager().getVersionService().getWorldChatExp());
			getIslandStateOk.setColorChatExp(ServiceManager.getManager().getVersionService().getColorChatExp());
			if (map.get("probability_x") == null) {
				getIslandStateOk.setProbability_x(90);
			} else {
				getIslandStateOk.setProbability_x(map.get("probability_x"));
			}
			if (map.get("probability_y") == null) {
				getIslandStateOk.setProbability_y(10);
			} else {
				getIslandStateOk.setProbability_y(map.get("probability_y"));
			}
			getIslandStateOk.setInviteLevel(operationConfig.getNewPlayerLevel());

			if (map.get("waitTime") == null) {
				getIslandStateOk.setWaitTime(-1);
			} else {
				getIslandStateOk.setWaitTime(map.get("waitTime"));
			}

			if (map.get("battleWaitTime") == null) {
				getIslandStateOk.setBattleWaitTime(-1);
			} else {
				getIslandStateOk.setBattleWaitTime(map.get("battleWaitTime"));
			}

			if (map.get("petInheritanceLevel") == null) {
				getIslandStateOk.setPetInheritanceLevel(5);
			} else {
				getIslandStateOk.setPetInheritanceLevel(map.get("petInheritanceLevel"));
			}

			if (map.get("openLinShiVip") == null || map.get("openLinShiVip") == 0) {
				getIslandStateOk.setOpenLinShiVip(false);
			} else {
				getIslandStateOk.setOpenLinShiVip(true);
			}

			if (map.get("openBind") == null || map.get("openBind") == 0) {
				getIslandStateOk.setOpenBind(false);
			} else {
				getIslandStateOk.setOpenBind(true);
			}

			if (map.get("serviceMode") == null || map.get("serviceMode") == 0) {
				getIslandStateOk.setServiceMode(0);
			} else {
				getIslandStateOk.setServiceMode(1);
			}

			if (ServiceManager.getManager().getChallengeSerService().isInTime()) {
				getIslandStateOk.setChallengeStarted(true);
			} else {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MINUTE, 10);
				getIslandStateOk.setChallengeStarted(ServiceManager.getManager().getChallengeSerService().isInTime(cal.getTime()));
			}
			if (map.get("openTipLevel") != null) {
				getIslandStateOk.setOpenTipLevel(map.get("openTipLevel"));
			} else {
				getIslandStateOk.setOpenTipLevel(15);
			}
			if (Server.config.isCross()) {
				getIslandStateOk.setCrossLevel(operationConfig.getCrossLevel());
			} else {
				getIslandStateOk.setCrossLevel(0);
			}
			String moreGame = operationConfig.getMoreGame();

			getIslandStateOk.setMoreGame(getMoreGame(player, moreGame));
			getIslandStateOk.setSquareTip(getSquareTip(player));

			List<ButtonInfo> buttonList = ServiceManager.getManager().getOperationConfigService().getButtonList();
			int buttonCount = buttonList.size();
			int[] buttonId = new int[buttonCount];// 按钮id
			byte[] buttonType = new byte[buttonCount];// 按钮类型
														// 0主界面建筑按钮，1主界面左侧按钮，2主界面中部按钮，3主界面右侧按钮。
			String[] buttonIcon = new String[buttonCount];// 按钮的图标
			String[] buttonTips = new String[buttonCount];// 按钮的提示
			int[] buttonStatus1Level = new int[buttonCount];// 按钮状态 按钮显示不可用需求等级
			int[] buttonStatus2Level = new int[buttonCount];// 按钮状态
															// 按钮显示可用返回提示需求等级
			int[] buttonStatus3Level = new int[buttonCount];// 按钮状态
															// 按钮显示可用功能开放需求等级
			ButtonInfo button;
			for (int i = 0; i < buttonCount; i++) {
				button = buttonList.get(i);
				buttonId[i] = button.getButtonId();
				buttonType[i] = button.getButtonType();
				buttonIcon[i] = button.getButtonIcon() == null ? "" : button.getButtonIcon();
				buttonTips[i] = button.getButtonRemark() == null ? "" : button.getButtonRemark();
				buttonStatus1Level[i] = button.getButtonStatus1Level();
				buttonStatus2Level[i] = button.getButtonStatus2Level();
				buttonStatus3Level[i] = button.getButtonStatus3Level();
			}
			getIslandStateOk.setButtonId(buttonId);
			getIslandStateOk.setButtonType(buttonType);
			getIslandStateOk.setButtonIcon(buttonIcon);
			getIslandStateOk.setButtonTips(buttonTips);
			getIslandStateOk.setButtonStatus1Level(buttonStatus1Level);
			getIslandStateOk.setButtonStatus2Level(buttonStatus2Level);
			getIslandStateOk.setButtonStatus3Level(buttonStatus3Level);

			// 2.0 新加GM工具控制等级抛物线范围
			if (operationConfig.getLevelParabolaRange() == null || operationConfig.getLevelParabolaRange().equals("")) {
				getIslandStateOk.setPlayerLevel(new int[]{15, 20, 25, 35, 40});
				getIslandStateOk.setParabolaRange(new int[]{200, 175, 150, 150, 100});
			} else {
				String[] levelParabolaRange = operationConfig.getLevelParabolaRange().split("\\|");
				int[] playerLevel = new int[levelParabolaRange.length];
				int[] parabolaRange = new int[levelParabolaRange.length];
				for (int i = 0; i < levelParabolaRange.length; i++) {
					String[] str = levelParabolaRange[i].split("-");
					playerLevel[i] = Integer.parseInt(str[0]);
					parabolaRange[i] = Integer.parseInt(str[1].replace("\r", "").replace("\n", ""));
				}
				getIslandStateOk.setPlayerLevel(playerLevel);
				getIslandStateOk.setParabolaRange(parabolaRange);
			}
			if (map.get("itemsRemainimgDays") == null || map.get("itemsRemainimgDays") == 0) {
				getIslandStateOk.setShowItemsRemainimgDays(false);
			} else {
				getIslandStateOk.setShowItemsRemainimgDays(true);
			}

			if (map.get("rechargeCritFlag") == null || map.get("rechargeCritFlag") == 0) {
				getIslandStateOk.setOpenRechargeCritFlag(false);
			} else {
				getIslandStateOk.setOpenRechargeCritFlag(true);
			}

			if (map.get("soundRoomOpen") == null || map.get("soundRoomOpen") == 0) {
				getIslandStateOk.setSoundRoomOpen(false);
			} else {
				getIslandStateOk.setSoundRoomOpen(true);
			}

			if (map.get("switchGPS") == null || map.get("switchGPS") == 0) {
				getIslandStateOk.setSwitchGPS(false);
			} else {
				getIslandStateOk.setSwitchGPS(true);
			}

			if (map.get("accessFrequencyGPS") == null) {
				getIslandStateOk.setAccessFrequencyGPS(600);
			} else {
				getIslandStateOk.setAccessFrequencyGPS(map.get("accessFrequencyGPS"));
			}

			if (map.get("soundHostile") == null || map.get("soundHostile") == 0) {
				getIslandStateOk.setSoundHostile(false);
			} else {
				getIslandStateOk.setSoundHostile(true);
			}
			if (map.get("downloadRewardSwitch") == null || map.get("downloadRewardSwitch") == 0) {
				getIslandStateOk.setDownloadRewardSwitch(false);
			} else {
				getIslandStateOk.setDownloadRewardSwitch(true);
			}
			session.write(getIslandStateOk);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	private String getMoreGame(WorldPlayer player, String moreGame) {
		if (player == null || player.getPlayer() == null)
			return "-1";
		Integer areaId = player.getPlayer().getAreaId();
		if (areaId == null)
			return "-1";
		String userId = getUserId(player.getClient());
		return moreGame = "-1".equals(moreGame) ? "-1" : moreGame + "?userid=" + userId + "&serverCode=" + areaId + "&roleid="
				+ player.getId();
	}

	private String getUserId(Client client) {
		if (client == null)
			return "";
		String name = client.getName();
		return name.substring(name.indexOf("_") + 1);
	}

	/**
	 * 获得小金人提示
	 * 
	 * @param player
	 * @return
	 */
	public String getSquareTip(WorldPlayer player) {
		String str = "";
		if (null == player || null == TipMessages.SQUARE_TIP || "".equals(TipMessages.SQUARE_TIP) || "null".equals(TipMessages.SQUARE_TIP)) {
			return str;
		} else {
			String[] tipString = TipMessages.SQUARE_TIP.split("\\|");
			String timeString = ServiceManager.getManager().getVersionService().getVersion().getShowTipsTimePeriod();
			if (null == timeString || timeString.length() == 0 || "-1".equals(timeString)) {
				return str;
			}
			String[] timeArray = timeString.split("-");
			Date[] dates = new Date[2];
			dates[0] = DateUtil.parseDate(timeArray[0]);
			dates[1] = DateUtil.parseDate(timeArray[1]);
			if (!DateUtil.isBetweenTime(new Date(), dates)) {
				return str;
			}
			if (player.getTipMark() < tipString.length) {
				str = tipString[player.getTipMark()];
			}
			if (player.getTipMark() >= tipString.length - 1) {
				player.setTipMark(0);
			} else {
				player.setTipMark(player.getTipMark() + 1);
			}
		}
		return str;
	}
}
