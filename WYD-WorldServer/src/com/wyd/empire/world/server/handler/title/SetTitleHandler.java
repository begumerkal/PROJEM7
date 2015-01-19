package com.wyd.empire.world.server.handler.title;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.title.SetTitle;
import com.wyd.empire.protocol.data.title.SetTitleOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerDIYTitleService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.title.TitleIng;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 设置Title
 * 
 * @author Administrator
 */
public class SetTitleHandler implements IDataHandler {
	Logger log = Logger.getLogger(SetTitleHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		SetTitle setTitle = (SetTitle) data;
		try {
			IPlayerDIYTitleService playerDIYTitleService = ServiceManager.getManager().getPlayerDIYTitleService();
			SetTitleOk stOk = new SetTitleOk(data.getSessionId(), data.getSerial());
			// 0为系统称号1为自定义称号
			if (setTitle.getTitleType() == 0) {
				setSystemTitle(player, setTitle.getPtId(), stOk);
				playerDIYTitleService.selectTitle(player.getId(), -1);
			} else {
				String title = playerDIYTitleService.selectTitle(player.getId(), setTitle.getPtId());
				stOk.setTitle(title);
			}
			session.write(stOk);
			// 更新角色信息-称号
			Map<String, String> info = new HashMap<String, String>();
			info.put("title", player.getPlayerTitle());
			ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, player);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}

	private void setSystemTitle(WorldPlayer player, int ptId, SetTitleOk stOk) {
		TitleIng title = null;
		TitleIng title2 = null;
		for (TitleIng titleIng : player.getTitleIngList()) {
			if (titleIng.getTitleId() == ptId) {
				title = titleIng;
			}
			if (3 == titleIng.getStatus()) {
				title2 = titleIng;
			}
		}
		if (null != title2) {
			title2.setStatus((byte) 2);
		}
		if (null != title) {
			title.setStatus((byte) 3);
		}
		if (null != title) {
			stOk.setTitle(ServiceManager.getManager().getTitleService().getTitleById(title.getTitleId()).getTitle());
		} else {
			stOk.setTitle("");
		}
	}
}
