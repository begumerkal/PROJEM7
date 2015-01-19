package com.wyd.empire.world.server.handler.singlemap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.singlemap.ChallengeSuccess;
import com.wyd.empire.protocol.data.singlemap.ChallengeSuccessOk;
import com.wyd.empire.protocol.data.singlemap.IllegalData;
import com.wyd.empire.world.bean.PlayerSingleMap;
import com.wyd.empire.world.bossmaproom.GuaiPlayer;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.item.RewardItemsVo;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IGuaiService;
import com.wyd.empire.world.server.service.base.ISingleMapService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.BossBattleTeamService;
import com.wyd.empire.world.server.service.impl.MapsService;
import com.wyd.empire.world.server.service.impl.PlayerService;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 挑战成功
 * 
 * @author zengxc
 * 
 */
public class ChallengeSuccessHandler implements IDataHandler {
	Logger log = Logger.getLogger(StartChallengeHandler.class);
	ServiceManager manager = ServiceManager.getManager();
	MapsService mapsService = null;
	ISingleMapService singleMapService = null;
	IGuaiService guaiService = null;
	PlayerService playerService = null;

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (!player.isInSingleMap())
			return;
		int singlemapId = player.getSingleMapId();
		player.outSingleMap();
		try {
			int vigor = player.getVigor();
			ChallengeSuccess challengeSuccess = (ChallengeSuccess) data;
			mapsService = manager.getMapsService();
			singleMapService = manager.getSingleMapService();
			guaiService = manager.getGuaiService();
			playerService = manager.getPlayerService();
			com.wyd.empire.world.bean.Map singleMap = mapsService.getSingleMapById(singlemapId);
			int useVigor = singleMap.getVitalityExpend();
			// 是否足够活力
			if ((vigor - useVigor) < 0) {
				throw new ProtocolException(TipMessages.VIGOR_LOW, data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
			}
			// 扣除活力
			player.useVigor(useVigor);
			int bossmapSerial = singleMap.getBossmapSerial();
			// 判断上一个关卡有没有过,bossmapSerial=1表示第一关
			if (bossmapSerial > 1) {
				int prvId = singleMap.getId() - 1;
				PlayerSingleMap prvSingleMap = singleMapService.getPlayerSingleMap(player.getId(), prvId);
				if (prvSingleMap == null || prvSingleMap.getPassTimes() < 0) {
					throw new ProtocolException(TipMessages.SINGLEMAP_PREV_LEVEL, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
			}
			int addExp = 0, exp2 = 0;
			int upgradeExp = playerService.getUpgradeExp(player.getLevel(), player.getPlayer().getZsLevel());
			int nextUpgradeExp = playerService.getUpgradeExp(player.getLevel() + 1, player.getPlayer().getZsLevel());
			List<Integer> getReward = new ArrayList<Integer>();
			List<String> rewardIcon = new ArrayList<String>();
			List<String> rewardName = new ArrayList<String>();
			// 战斗检验(防外挂)
			String verifyString = singleMapService.battleVerify(player, getGuaiPlayer(singleMap), challengeSuccess.getBattleVerify());
			if (!verifyString.startsWith("success")) {
				IllegalData illegalData = new IllegalData(data.getSessionId(), data.getSerial());
				illegalData.setMessage(TipMessages.SINGLEMAP_ILLEGALDATA);
				session.write(illegalData);
				GameLogService.singleMapCheat(player.getId(), player.getLevel(), verifyString);
			} else {
				// 保存通关记录
				singleMapService.savePassTimes(player.getId(), singleMap.getId());
				// 得到掉落物品
				List<RewardItemsVo> rewardList = singleMapService.getRewardList(player.getId(), player.getPlayer().getSex(), singleMap);
				int index = 0, getitemid = 0, itemcount = 0;
				for (RewardItemsVo vo : rewardList) {
					rewardIcon.add(vo.getItemIcon());
					rewardName.add("x" + vo.getCount());
					if (vo.getOwnerId() == 1) {
						getReward.add(index);
						getitemid = vo.getItemId();
						itemcount = vo.getCount();
					}
					index++;
				}

				List<RewardInfo> mapRewards = ServiceUtils.getRewardInfo(singleMap.getReward(), player.getPlayer().getSex().intValue());
				for (RewardInfo reward : mapRewards) {
					switch (reward.getItemId()) {
						case Common.GOLDID :
							ServiceManager.getManager().getPlayerService()
									.updatePlayerGold(player, reward.getCount(), "地图掉落", "-- " + " --");
							break;
						case Common.EXPID :
							addExp = reward.getCount();
							// buff加成,VIP加成
							int exp1 = ServiceManager.getManager().getBuffService().getExp(player, addExp) - addExp;
							// 添加公会技能加成
							exp2 = (int) ServiceManager.getManager().getBuffService().getAddition(player, addExp, Buff.CEXP) - addExp;
							addExp = addExp + exp1 + exp2;
							playerService.updatePlayerEXP(player, addExp);
							break;
					}
				}
				int addDiamond = 0;
				int addGold = 0;
				int addBadge = 0;
				// 发放奖励
				if (getitemid == Common.GOLDID) {
					manager.getPlayerService().updatePlayerGold(player, itemcount, "单人副本掉落", "");
					addGold = itemcount;
				} else if (getitemid == Common.DIAMONDID) {
					manager.getPlayerService().addTicket(player, itemcount, 0, TradeService.ORIGIN_SINGLEMAP, 0, "", "单人副本掉落", "", "");
					// 记录获得钻石个数，等到GetPointsHandler协议时发送公告
					player.setSingleMapGetDiamond(itemcount);
					addDiamond = itemcount;
				} else {
					manager.getPlayerItemsFromShopService().playerGetItem(player.getId(), getitemid, itemcount, 29, null, 0, 0, 0);
					if (getitemid == Common.BADGEID) {
						addBadge = itemcount;
					}
				}
				ChallengeSuccessOk ok = new ChallengeSuccessOk(data.getSessionId(), data.getSerial());
				ok.setAddExp(addExp);
				ok.setExp(player.getPlayer().getExp());
				ok.setUpgradeExp(upgradeExp);
				ok.setNextUpgradeExp(nextUpgradeExp);
				ok.setGetReward(ServiceUtils.ListToInts(getReward));
				ok.setRewardIcon(ServiceUtils.ListToStrs(rewardIcon));
				ok.setRewardName(ServiceUtils.ListToStrs(rewardName));
				ok.setGuildAddExp(exp2);
				session.write(ok);
				verifyString = verifyString.replace("success,", "");
				GameLogService.challengeSuccess(player.getId(), player.getLevel(), player.isVip(), singleMap.getId(), addDiamond, addGold,
						addBadge, verifyString);
				ServiceManager.getManager().getTaskService().singMap(player, singleMap.getId());
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex, ex);
			throw new ProtocolException(data, ex.getMessage());
		} finally {
			player.outSingleMap();
		}

	}

	private List<GuaiPlayer> getGuaiPlayer(com.wyd.empire.world.bean.Map map) {
		List<GuaiPlayer> guais = new ArrayList<GuaiPlayer>();
		BossBattleTeamService bossBattleTeamService = manager.getBossBattleTeamService();
		List<Map<String, Integer>> bossPosList = bossBattleTeamService.getplayerPos(map.getGuaiList(), false);
		for (Map<String, Integer> m : bossPosList) {
			guais.add(guaiService.getGuaiById(1, m.get("id").intValue()));
		}
		return guais;
	}

}
