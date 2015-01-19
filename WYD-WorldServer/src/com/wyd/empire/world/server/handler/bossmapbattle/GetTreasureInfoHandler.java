package com.wyd.empire.world.server.handler.bossmapbattle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.GetTreasureInfo;
import com.wyd.empire.protocol.data.bossmapbattle.GetTreasureInfoOk;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.bean.BossmapBuff;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.server.service.base.IWorldBossService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取宝箱信息
 * 
 * @author zengxc
 */
public class GetTreasureInfoHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetTreasureInfoHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetTreasureInfo info = (GetTreasureInfo) data;
		int battleId = info.getBattleId();
		BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
		if (null == battleTeam) {
			return;
		}
		com.wyd.empire.world.bean.Map map = ServiceManager.getManager().getMapsService().getWorldBossMapById(battleTeam.getMapId());
		List<java.util.Map<String, Integer>> buffPosList = map.BuffListPos();
		// 如果没有BuffList则这个协议什么都不用做。
		if (buffPosList == null) {
			return;
		}
		IWorldBossService wroldBossService = ServiceManager.getManager().getWorldBossService();
		int mapId = battleTeam.getMapId();
		// 是否世界BOSS
		if (wroldBossService.isWorldBossBattle(mapId)) {
			// 刷新地图BUFF
			ServiceManager.getManager().getWorldBossService().refreshWorldBossBuffSet(battleTeam.getBuffSet(), map.getId());
		}
		List<Integer> posX = new ArrayList<Integer>();
		List<Integer> posY = new ArrayList<Integer>();
		List<Integer> buffId = new ArrayList<Integer>();
		List<Integer> buffType = new ArrayList<Integer>();
		List<Integer> effect1 = new ArrayList<Integer>();
		List<Integer> effect2 = new ArrayList<Integer>();
		List<Integer> group = new ArrayList<Integer>();
		List<String> icon = new ArrayList<String>();
		List<String> name = new ArrayList<String>();
		List<Integer> probability = new ArrayList<Integer>();
		List<Integer> turn = new ArrayList<Integer>();
		BossmapBuff buff;
		for (int bbId : battleTeam.getBuffSet().toArray(new Integer[]{})) {
			if (buffPosList.size() > 0) {
				buff = ServiceManager.getManager().getBossmapBuffService().getBossmapBuffById(bbId);
				buffId.add(buff.getId());
				buffType.add(buff.getType());
				effect1.add(buff.getEffect1());
				effect2.add(buff.getEffect2());
				group.add(buff.getGroup());
				icon.add(buff.getIcon());
				name.add(buff.getName());
				probability.add(buff.getRealityPercent());
				turn.add(buff.getNum());
				Map<String, Integer> buffPos = buffPosList.remove(ServiceUtils.getRandomNum(0, buffPosList.size()));
				posX.add(buffPos.get("posX"));
				posY.add(buffPos.get("posY"));
			}
		}
		GetTreasureInfoOk ok = new GetTreasureInfoOk(data.getSessionId(), data.getSerial());
		ok.setBattleId(battleId);
		ok.setBuffId(ServiceUtils.getInts(buffId.toArray()));
		ok.setBuffType(ServiceUtils.getInts(buffType.toArray()));
		ok.setEffect1(ServiceUtils.getInts(effect1.toArray()));
		ok.setEffect2(ServiceUtils.getInts(effect2.toArray()));
		ok.setGroup(ServiceUtils.getInts(group.toArray()));
		ok.setIcon(icon.toArray(new String[]{}));
		ok.setName(name.toArray(new String[]{}));
		ok.setProbability(ServiceUtils.getInts(probability.toArray()));
		ok.setTurn(ServiceUtils.getInts(turn.toArray()));
		ok.setPosX(ServiceUtils.getInts(posX.toArray()));
		ok.setPosY(ServiceUtils.getInts(posY.toArray()));
		session.write(ok);
	}

}
