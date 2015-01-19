package com.wyd.empire.world.server.handler.community;

import java.util.Date;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.UseSkill;
import com.wyd.empire.protocol.data.community.UseSkillOk;
import com.wyd.empire.world.bean.BuffRecord;
import com.wyd.empire.world.bean.ConsortiaContribute;
import com.wyd.empire.world.bean.ConsortiaSkill;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
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
public class UseSkillHandler implements IDataHandler {
	private Logger log;

	public UseSkillHandler() {
		this.log = Logger.getLogger(UseSkillHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		UseSkill useSkill = (UseSkill) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		boolean mark = false;
		try {
			UseSkillOk useSkillOk = new UseSkillOk(data.getSessionId(), data.getSerial());
			ConsortiaSkill cs = (ConsortiaSkill) ServiceManager.getManager().getConsortiaService()
					.get(ConsortiaSkill.class, useSkill.getSkillId());
			ConsortiaContribute cc = ServiceManager.getManager().getPlayerSinConsortiaService()
					.getConsortiaContributeByPlayerId(player.getId(), player.getGuildId());
			if (player.getMoney() < cs.getCostUseGold()) {
				mark = true;
				throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_NEEDGOLD_MESSAGE);
			}
			if (player.getDiamond() < cs.getCostUseTickets()) {
				mark = true;
				throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_NEEDDEMOND_MESSAGE);
			}
			if (cc.getDiscontribute() < cs.getCostUseContribution()) {
				mark = true;
				throw new Exception(ErrorMessages.COMMUNITY_NEEDCONTRIBUTE_MESSAGE);
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
			}
			if (null != player.getBuffMap().get(buffCode)) {
				mark = true;
				throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_USESAMEONE_MESSAGE);
			}
			// 扣金币
			ServiceManager.getManager().getPlayerService().updatePlayerGold(player, -cs.getCostUseGold(), "使用公会技能", "");
			// 扣钻石
			if (cs.getCostUseTickets() != 0) {
				ServiceManager.getManager().getPlayerService()
						.useTicket(player, cs.getCostUseTickets(), TradeService.ORIGIN_SKILLUSE, null, null, cs.getComSkillName());
			}
			// 扣贡献度
			cc.setDiscontribute(cc.getDiscontribute() - cs.getCostUseContribution());
			ServiceManager.getManager().getConsortiaService().update(cc);
			// 加buff
			Buff buff = new Buff();
			buff.setBuffName(cs.getComSkillName());
			buff.setBuffCode(buffCode);
			buff.setIcon(cs.getIcon());
			if (cs.getType() == Common.CONEXP || cs.getType() == Common.CONANGRYLOW || cs.getType() == Common.CONADDHP
					|| cs.getType() == Common.CONHURT || cs.getType() == Common.CONPOWERLOW || cs.getType() == Common.CONHPCAP) {
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

			session.write(useSkillOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (mark) {
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			} else {
				throw new ProtocolException(ErrorMessages.COMMUNITY_GETLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
		}
	}
}