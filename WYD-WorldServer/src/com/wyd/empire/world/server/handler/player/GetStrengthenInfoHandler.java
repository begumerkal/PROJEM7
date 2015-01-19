package com.wyd.empire.world.server.handler.player;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.player.GetStrengthenInfo;
import com.wyd.empire.protocol.data.player.GetStrengthenInfoOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取武器强化信息
 * 
 * @author Administrator
 */
public class GetStrengthenInfoHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetStrengthenInfoHandler.class);

	// 获取技能列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetStrengthenInfo getStrengthenInfo = (GetStrengthenInfo) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), getStrengthenInfo.getWeaponId());
			String detail = "";
			String attackStr = "";
			String defendStr = "";
			String specialStr = "";
			ShopItem si;
			if (pifs.getAttackStone() > 0) {
				si = ServiceManager.getManager().getShopItemService().getShopItemById(pifs.getAttackStone());
				attackStr = si.getName();
			} else {
				attackStr = TipMessages.NO_GETIN;
			}
			if (pifs.getDefenseStone() > 0) {
				si = ServiceManager.getManager().getShopItemService().getShopItemById(pifs.getDefenseStone());
				defendStr = si.getName();
			} else {
				defendStr = TipMessages.NO_GETIN;
			}
			if (pifs.getSpecialStone() > 0) {
				si = ServiceManager.getManager().getShopItemService().getShopItemById(pifs.getSpecialStone());
				specialStr = si.getName();
			} else {
				specialStr = TipMessages.NO_GETIN;
			}

			detail = TipMessages.STRENGTHENINFONEW.replace("##", pifs.getShopItem().getName()).replace("**", pifs.getStrongLevel() + "")
					.replace("@@", getMessage(pifs)).replace("~~", "\n").replace("STAR", pifs.getStars() + "").replace("ATTACK", attackStr)
					.replace("DEFEND", defendStr).replace("SPECIAL", specialStr);
			if (pifs.getWeapSkill1() == 0 && pifs.getWeapSkill2() == 0 && pifs.getShopItem().isWeapon()) {
				if (pifs.getShopItem().getMove() == 1) {
					detail += TipMessages.STRENGTHENINFONEW1.substring(0, TipMessages.STRENGTHENINFONEW1.indexOf("~"));
				} else {
					detail += TipMessages.STRENGTHENINFONEW1.replace("~~", "\n");
				}
			} else {
				WeapSkill ws;
				if (pifs.getWeapSkill1() != 0) {
					ws = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill1());
					detail += "\n" + ws.getSkillName();
					if (ws.getId() == pifs.getSkillLock()) {
						detail += TipMessages.SKILL_LOCK;
					}
					detail += ":";
					detail += ws.getRemark() + "\n";
				}
				if (pifs.getWeapSkill2() != 0) {
					ws = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill2());
					detail += ws.getSkillName();
					if (ws.getId() == pifs.getSkillLock()) {
						detail += TipMessages.SKILL_LOCK;
					}
					detail += ":";
					detail += ws.getRemark();
				}
				detail += "                                                                ";// 客户端bug，具体问莫剑锋
			}
			GetStrengthenInfoOk getStrengthenInfoOk = new GetStrengthenInfoOk(data.getSessionId(), data.getSerial());
			getStrengthenInfoOk.setDetail(detail);
			session.write(getStrengthenInfoOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.PLAYER_STOREEQU_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	/**
	 * 拼接显示字符串
	 * 
	 * @param playerItem
	 * @return
	 */
	private String getMessage(PlayerItemsFromShop playerItem) {
		ShopItem item = playerItem.getShopItem();
		int attack = playerItem.getPAddAttack() + item.getAddAttack() + (playerItem.getpAddForce() * 4 / 25);
		int hp = playerItem.getPAddHp() + item.getAddHp() + playerItem.getpAddPhysique();
		int defend = playerItem.getPAddDefend() + item.getAddDefend();
		int criticalCoef = playerItem.getpAddcrit();// 爆击
		int attackArea = item.getAttackArea();// 爆破范围
		String showString = "";

		// 攻击
		if (attack > 0) {
			showString += "\n" + TipMessages.ATTACK + ":" + attack;
		}
		// 生命
		if (hp > 0) {
			showString += "\n" + TipMessages.HP + ":" + hp;
		}
		// 防御
		if (defend > 0) {
			showString += "\n" + TipMessages.DEFEND + ":" + defend;
		}
		// 爆破范围
		if (attackArea > 0) {
			showString += "\n" + TipMessages.ATTACKAREA + ":" + attackArea;
		}
		// 满爆击
		if (criticalCoef > 0) {
			double fullCritical = Math.sqrt(100f / criticalCoef) * 100;
			showString += "\n" + TipMessages.FULLCRITICAL + ":" + (int) Math.round(fullCritical) + "%";
		}
		return showString;
	}
}
