package com.wyd.empire.world.server.handler.community;

import java.util.Date;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.BuySkill;
import com.wyd.empire.protocol.data.community.BuySkillOk;
import com.wyd.empire.world.bean.BuffRecord;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.ConsortiaContribute;
import com.wyd.empire.world.bean.ConsortiaSkill;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.item.DailyRewardVo;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.DailyActivityService;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> SearchCommunityHandler</code>Protocol.COMMUNITY
 * UseSkillHandle使用公会技能协议处理
 * 
 * @since JDK 1.6
 */
public class BuySkillHandler implements IDataHandler {
	private Logger log;

	public BuySkillHandler() {
		this.log = Logger.getLogger(BuySkillHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		BuySkill buySkill = (BuySkill) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			BuySkillOk buySkillOk = new BuySkillOk(data.getSessionId(), data.getSerial());
			ConsortiaSkill cs = (ConsortiaSkill) ServiceManager.getManager().getConsortiaService()
					.get(ConsortiaSkill.class, buySkill.getSkillId());
			ConsortiaContribute cc = ServiceManager.getManager().getPlayerSinConsortiaService()
					.getConsortiaContributeByPlayerId(player.getId(), player.getGuildId());
			PlayerSinConsortia psc = ServiceManager.getManager().getPlayerSinConsortiaService().findPlayerSinConsortia(player.getId());

			if (psc.getConsortia().getLevel() < cs.getComLv()) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_GETLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			String buffCode = "";
			switch (cs.getType()) {
				case Common.CONEXP :
					buffCode = Buff.CEXP;
					break;
				case Common.CONDEF :
					buffCode = Buff.CDEF;
					break;
				case Common.CONANGRYLOW :
					buffCode = Buff.CANGRYLOW;
					break;
				case Common.CONADDHP :
					buffCode = Buff.CADDHP;
					break;
				case Common.CONTREAT :
					buffCode = Buff.CTREAT;
					break;
				case Common.CONPOWER :
					buffCode = Buff.CPOWER;
					break;
				case Common.CONCRIT :
					buffCode = Buff.CCRIT;
					break;
				case Common.CONHURT :
					buffCode = Buff.CHURT;
					break;
				case Common.CONHPCAP :
					buffCode = Buff.CHPCAP;
					break;
				case Common.CONPOWERLOW :
					buffCode = Buff.CPOWERLOW;
					break;
				case Common.CSTRONG :
					buffCode = Buff.CSTRONG;
					break;
				case Common.CGETIN :
					buffCode = Buff.CGETIN;
					break;
				case Common.CGOLDLOW :
					buffCode = Buff.CGOLDLOW;
					break;
				case Common.CONGOLD :
					buffCode = Buff.GOLD;
					break;
			}
			if (null != player.getBuffMap().get(buffCode)) {
				throw new ProtocolException(data, ErrorMessages.COMMUNITY_USESAMEONE_MESSAGE);
			}
			int useDiamond = 0, useGold = 0, useBadge = 0;
			DailyActivityService dailyActivityService = ServiceManager.getManager().getDailyActivityService();
			DailyRewardVo dailyRewardVo = dailyActivityService.getBuyConsortiaSkillReward();
			if (buySkill.getBuySkillWayTag() == 1) {
				int cost = dailyActivityService.getRewardedVal(cs.getCostUseGold(), dailyRewardVo.getSubGold());
				if (player.getMoney() < cost) {
					throw new ProtocolException(data, ErrorMessages.COMMUNITY_NEEDGOLD_MESSAGE);
				}
				// 扣金币
				ServiceManager.getManager().getPlayerService().updatePlayerGold(player, -cost, "使用公会技能", cs.getComSkillName());
				useGold = cost;
			} else if (buySkill.getBuySkillWayTag() == 3) {
				if (psc.getDiscontribute() < cs.getCostUseContribution()) {
					throw new ProtocolException(data, ErrorMessages.COMMUNITY_NEEDCONTRIBUTE_MESSAGE);
				}
				// 扣贡献度
				cc.setDiscontribute(cc.getDiscontribute() - cs.getCostUseContribution());
				ServiceManager.getManager().getConsortiaService().update(cc);
				psc.setDiscontribute(psc.getDiscontribute() - cs.getCostUseContribution());
				ServiceManager.getManager().getConsortiaService().update(psc);
				useBadge = cs.getCostUseContribution();
			} else {
				int cost = dailyActivityService.getRewardedVal(cs.getCostUseTickets(), dailyRewardVo.getSubTicket());
				if (player.getDiamond() < cost) {
					throw new ProtocolException(data, ErrorMessages.COMMUNITY_NEEDDEMOND_MESSAGE);
				}
				// 扣钻石
				if (cs.getCostUseTickets() != 0) {
					ServiceManager.getManager().getPlayerService()
							.useTicket(player, cost, TradeService.ORIGIN_SKILLUSE, null, null, cs.getComSkillName());
					useDiamond = cost;
				}
			}
			// 加buff
			Buff buff = new Buff();
			buff.setBuffName(cs.getComSkillName());
			buff.setBuffCode(buffCode);
			buff.setIcon(cs.getIcon());
			if (cs.getType() == Common.CONEXP || cs.getType() == Common.CONANGRYLOW || cs.getType() == Common.CONADDHP
					|| cs.getType() == Common.CONHURT || cs.getType() == Common.CONPOWERLOW || cs.getType() == Common.CONHPCAP
					|| cs.getType() == Common.CGOLDLOW || cs.getType() == Common.CONGOLD || cs.getType() == Common.CGETIN) {
				buff.setAddType(0);
			} else {
				buff.setAddType(1);
			}
			buff.setQuantity(cs.getParam());
			buff.setEndtime(new Date().getTime() + 24 * 60 * 60 * 1000);
			buff.setSurplus(0);
			buff.setSkillId(cs.getId());
			buff.setSkillDetail(cs.getDesc());
			buff.setBufftype(cs.getType());
			ServiceManager.getManager().getBuffService().addBuff(player, buff);
			player.updateFight();
			// 保存数据库记录
			BuffRecord br = new BuffRecord();
			br.setBuffName(buff.getBuffName());
			br.setBuffCode(buff.getBuffCode());
			br.setConsortiaSkill(cs);
			br.setEndtime(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
			br.setPlayerId(player.getId());
			br.setQuantity(buff.getQuantity());
			br.setAddType(buff.getAddType());
			br.setSurplus(buff.getSurplus());
			br.setBuffType(buff.getBufftype());
			ServiceManager.getManager().getConsortiaService().save(br);
			session.write(buySkillOk);
			Consortia consortia = player.getGuild();
			if (null != consortia)
				GameLogService.guildSkill(player.getId(), player.getLevel(), consortia.getId(), consortia.getLevel(), cs.getId(),
						useDiamond, useGold, useBadge);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_GETLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}