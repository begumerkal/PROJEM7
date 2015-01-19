package com.wyd.empire.world.server.handler.community;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.GetSkillOk;
import com.wyd.empire.world.bean.ConsortiaSkill;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> SearchCommunityHandler</code>Protocol.COMMUNITY
 * _SearchCommunity公会升级协议处理
 * 
 * @since JDK 1.6
 */
public class GetSkillHandler implements IDataHandler {
	private Logger log;

	public GetSkillHandler() {
		this.log = Logger.getLogger(GetSkillHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			List<Buff> buffList = player.getBuffList();
			Map<String, Long> scMap = new HashMap<String, Long>();
			for (Buff sr : buffList) {
				scMap.put(sr.getBuffName(), sr.getEndtime());
			}
			GetSkillOk getSkillOk = new GetSkillOk(data.getSessionId(), data.getSerial());
			List<ConsortiaSkill> list = ServiceManager.getManager().getConsortiaService().getConsortiaSkill();
			PlayerSinConsortia psc = ServiceManager.getManager().getPlayerSinConsortiaService().findPlayerSinConsortia(player.getId());
			int[] skillId = new int[list.size()];// 技能id
			String[] skillIcon = new String[list.size()]; // 技能图标
			String[] skillName = new String[list.size()]; // 技能名称
			int[] countdownTime = new int[list.size()]; // 技能可以使用倒计时
			int[] conLev = new int[list.size()];

			int i = 0;
			for (ConsortiaSkill cs : list) {
				skillId[i] = cs.getId();
				skillIcon[i] = cs.getIcon();
				skillName[i] = cs.getComSkillName();
				countdownTime[i] = scMap.get(cs.getComSkillName()) == null ? 0 : (int) (scMap.get(cs.getComSkillName()) - new Date()
						.getTime()) / 1000 / 60;
				conLev[i] = cs.getComLv();
				i++;
			}

			getSkillOk.setSkillIcon(skillIcon);
			getSkillOk.setSkillId(skillId);
			getSkillOk.setSkillName(skillName);
			getSkillOk.setCountdownTime(countdownTime);
			getSkillOk.setConLev(conLev);
			getSkillOk.setPlayerContribution(psc.getDiscontribute());
			getSkillOk.setConContribution(psc.getContribute());
			session.write(getSkillOk);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_GETLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}