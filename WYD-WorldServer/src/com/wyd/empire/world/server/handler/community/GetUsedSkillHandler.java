package com.wyd.empire.world.server.handler.community;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.GetUsedSkillOk;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> SearchCommunityHandler</code>Protocol.COMMUNITY
 * _GetUsedSkill获得已用公会技能
 * 
 * @since JDK 1.6
 */
public class GetUsedSkillHandler implements IDataHandler {
	private Logger log;

	public GetUsedSkillHandler() {
		this.log = Logger.getLogger(GetUsedSkillHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			List<Buff> list = player.getBuffList();

			GetUsedSkillOk getUsedSkillOk = new GetUsedSkillOk(data.getSessionId(), data.getSerial());

			List<Integer> skillId = new ArrayList<Integer>();// 技能id
			List<String> skillIcon = new ArrayList<String>();// 技能图标
			List<String> skillName = new ArrayList<String>();// 技能名称
			List<Integer> countdownTime = new ArrayList<Integer>();// 技能可以使用倒计时
			List<String> skillDetail = new ArrayList<String>();// 技能描述

			for (Buff buff : list) {
				if (buff.getSkillId() != 0) {
					skillId.add(buff.getSkillId());
					skillIcon.add(buff.getIcon());
					skillName.add(buff.getBuffName());
					skillDetail.add(TipMessages.DETAILNAME + buff.getBuffName() + "\n" + TipMessages.DETAILINFO + buff.getSkillDetail());
					countdownTime.add((int) (buff.getEndtime() - new Date().getTime()) / 1000 / 60);
				}
			}

			getUsedSkillOk.setSkillId(ServiceUtils.getInts(skillId.toArray()));
			getUsedSkillOk.setSkillIcon(skillIcon.toArray(new String[skillIcon.size()]));
			getUsedSkillOk.setSkillName(skillName.toArray(new String[skillName.size()]));
			getUsedSkillOk.setCountdownTime(ServiceUtils.getInts(countdownTime.toArray()));
			getUsedSkillOk.setSkillDetail(skillDetail.toArray(new String[skillDetail.size()]));
			session.write(getUsedSkillOk);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_GETLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}