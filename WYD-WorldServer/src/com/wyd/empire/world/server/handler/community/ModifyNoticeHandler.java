package com.wyd.empire.world.server.handler.community;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.ModifyNotice;
import com.wyd.empire.protocol.data.community.ModifyNoticeOk;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 
 * 类 <code> ModifyNoticeHandler</code>Protocol.COMMUNITY _ModifyNotice修改公会公告协议处理
 * 
 * @since JDK 1.6
 * 
 */
public class ModifyNoticeHandler implements IDataHandler {
	private Logger log;

	public ModifyNoticeHandler() {
		this.log = Logger.getLogger(ExitCommunityHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		ModifyNotice modifyNotice = (ModifyNotice) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			ModifyNoticeOk modifyNoticeOk = new ModifyNoticeOk(data.getSessionId(), data.getSerial());
			modifyNoticeOk.setNotice(modifyNotice.getNotice());
			PlayerSinConsortia mypsc = ServiceManager.getManager().getPlayerSinConsortiaService().findPlayerSinConsortia(player.getId());
			// 获得相应的公会对象
			Consortia consortia = (Consortia) ServiceManager.getManager().getConsortiaService()
					.get(Consortia.class, modifyNotice.getCommunityId());
			if (consortia == null) {
				throw new NullPointerException();// 如果传过来参数有问题，找不到公会，抛空指针异常
			}
			// 只有会长副会长可以修改
			if (null == mypsc || !mypsc.getConsortia().equals(consortia) || mypsc.getPosition() > 1) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOAUTHORITY_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}

			if (modifyNotice.getNotice().getBytes("gbk").length > 100) {// 公告50个汉字
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOTICE_LONG, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			consortia.setInsideNotice(modifyNotice.getNotice());

			// 更新公会对象
			ServiceManager.getManager().getConsortiaService().update(consortia);

			session.write(modifyNoticeOk);

			// 加入日志
			log.info("公会进出会记录：玩家Id-" + player.getId() + "修改公告");
		} catch (ProtocolException ex) {
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOTICE_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());

		}
	}
}