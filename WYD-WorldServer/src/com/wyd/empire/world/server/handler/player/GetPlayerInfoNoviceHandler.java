package com.wyd.empire.world.server.handler.player;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.player.GetPlayerInfoNoviceOk;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IShopItemService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取角色信息(新手教学)
 * 
 * @author zengxc
 */
public class GetPlayerInfoNoviceHandler implements IDataHandler {
	private Logger log;

	public GetPlayerInfoNoviceHandler() {
		this.log = Logger.getLogger(GetPlayerInfoNoviceHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		try {
			GetPlayerInfoNoviceOk getPlayerInfoNoviceOk = new GetPlayerInfoNoviceOk(data.getSessionId(), data.getSerial());
			result(player, getPlayerInfoNoviceOk);
			session.write(getPlayerInfoNoviceOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}

	/**
	 * 新手教程使用的装扮
	 * 
	 * @param getPlayerInfoOk
	 * @author zengxc
	 */
	private void noviceTutorials(int sex, GetPlayerInfoNoviceOk getPlayerInfoOk) {
		IShopItemService shopService = ServiceManager.getManager().getShopItemService();
		int head = sex == 0 ? 303 : 351;
		int face = sex == 0 ? 166 : 131;
		int body = sex == 0 ? 295 : 344;
		int wing = sex == 0 ? 441 : 223;
		int weapon = sex == 0 ? 219 : 219;
		getPlayerInfoOk.setSuit_head(shopService.getShopItemById(head).getAnimationIndexCode());
		getPlayerInfoOk.setSuit_face(shopService.getShopItemById(face).getAnimationIndexCode());
		getPlayerInfoOk.setSuit_body(shopService.getShopItemById(body).getAnimationIndexCode());
		getPlayerInfoOk.setSuit_wing(shopService.getShopItemById(wing).getAnimationIndexCode());
		getPlayerInfoOk.setSuit_weapon(shopService.getShopItemById(weapon).getAnimationIndexCode());
	}

	public void result(WorldPlayer player, GetPlayerInfoNoviceOk getPlayerInfoOk) {
		getPlayerInfoOk.setPlayerId(player.getId());
		getPlayerInfoOk.setPlayerName(player.getName());
		getPlayerInfoOk.setTickets(player.getDiamond());
		getPlayerInfoOk.setMaxLevel(WorldServer.config.getMaxLevel(player.getPlayer().getZsLevel()));
		getPlayerInfoOk.setPlayerHp(player.getMaxHP());
		getPlayerInfoOk.setPlayerDefend(player.getDefend());
		getPlayerInfoOk.setPlayerDefense(player.getCrit());
		getPlayerInfoOk.setPlayerPhysical(player.getMaxPF());
		getPlayerInfoOk.setPlayerGold(player.getPlayer().getMoneyGold());
		getPlayerInfoOk.setPlayerHonor(player.getPlayer().getHonor());
		getPlayerInfoOk.setPlayerSex(player.getPlayer().getSex());
		getPlayerInfoOk.setLevel(player.getLevel());
		getPlayerInfoOk.setAttack(player.getAttack());
		getPlayerInfoOk.setExp(player.getPlayer().getExp());
		getPlayerInfoOk.setGuildName(player.getGuildName());
		getPlayerInfoOk.setMedalNum(player.getMedalNum());
		getPlayerInfoOk.setCritRate(player.getCrit());
		getPlayerInfoOk.setExplodeRadius(player.getExplodeRadius());
		getPlayerInfoOk.setProficiency(player.getProficiency());
		// 新手教程使用的装扮
		noviceTutorials(player.getPlayer().getSex(), getPlayerInfoOk);
		getPlayerInfoOk.setUpgradeexp(ServiceManager.getManager().getPlayerService()
				.getUpgradeExp(player.getLevel(), player.getPlayer().getZsLevel()));
		if (player.isVip()) {
			getPlayerInfoOk.setVipLevel(player.getPlayer().getVipLevel());
		} else {
			getPlayerInfoOk.setVipLevel(0);
		}
		getPlayerInfoOk.setSuit_wing(player.getSuit_wing());
		getPlayerInfoOk.setPlayer_title(player.getPlayerTitle());
		getPlayerInfoOk.setWeaponLevel(player.getWeaponLevel());
		String[] markStr = player.getPlayer().getWbUserId().split(",");
		Map<String, String> map = new HashMap<String, String>();
		String[] str;
		for (String s : markStr) {
			str = s.split("=");
			map.put(str[0], str[1]);
		}
		getPlayerInfoOk.setWbUserId(new String[]{map.get(Common.XLWB), map.get(Common.TXWB)});
		getPlayerInfoOk.setZsleve(player.getPlayer().getZsLevel());
		getPlayerInfoOk.setInjuryFree(player.getInjuryFree());
		getPlayerInfoOk.setWreckDefense(player.getWreckDefense());
		getPlayerInfoOk.setReduceCrit(player.getReduceCrit());
		getPlayerInfoOk.setReduceBury(player.getReduceBury());
		getPlayerInfoOk.setForce(player.getForce());
		getPlayerInfoOk.setArmor(player.getArmor());
		getPlayerInfoOk.setAgility(player.getAgility());
		getPlayerInfoOk.setPhysique(player.getPhysique());
		getPlayerInfoOk.setLuck(player.getLuck());
		getPlayerInfoOk.setFighting(player.getFighting());
		getPlayerInfoOk.setVipMark(0);
		getPlayerInfoOk.setVipLastDay(0);

		int heart = ServiceManager.getManager().getPlayerItemsFromShopService().getPlayerItemNum(player.getId(), Common.LOVEID);
		getPlayerInfoOk.setHeart(heart);

	}

}