package com.wyd.empire.world.server.handler.community;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.ModifyDeclaration;
import com.wyd.empire.protocol.data.community.ModifyDeclarationOk;
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
 * 类 <code> ModifyDeclarationHandler</code>Protocol.COMMUNITY
 * _ModifyDeclaration修改公会宣言协议处理
 * 
 * @since JDK 1.6
 * 
 */
public class ModifyDeclarationHandler implements IDataHandler {
	private Logger log;

	public ModifyDeclarationHandler() {
		this.log = Logger.getLogger(ExitCommunityHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		ModifyDeclaration modifyDeclaration = (ModifyDeclaration) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			PlayerSinConsortia mypsc = ServiceManager.getManager().getPlayerSinConsortiaService().findPlayerSinConsortia(player.getId());
			ModifyDeclarationOk modifyDeclarationOk = new ModifyDeclarationOk(data.getSessionId(), data.getSerial());
			modifyDeclarationOk.setDeclaration(modifyDeclaration.getDeclaration());

			// 获得相应的公会对象
			Consortia consortia = (Consortia) ServiceManager.getManager().getConsortiaService()
					.get(Consortia.class, modifyDeclaration.getCommunityId());

			if (consortia == null) {
				throw new NullPointerException();// 如果传过来参数有问题，找不到公会，抛空指针异常
			}

			// 只有会长副会长可以修改
			if (null == mypsc || !mypsc.getConsortia().equals(consortia) || mypsc.getPosition() > 1) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOAUTHORITY_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}

			if (modifyDeclaration.getDeclaration().getBytes("gbk").length > 100) {// 宣言50个汉字
				// utf8汉字长度不规范，转成gbk格式判断
				throw new ProtocolException(ErrorMessages.COMMUNITY_DECLARATION_LONG, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}

			consortia.setDeclaration(modifyDeclaration.getDeclaration());

			// 更新公会对象
			ServiceManager.getManager().getConsortiaService().update(consortia);

			session.write(modifyDeclarationOk);

			// 加入日志
			log.info("公会进出会记录：玩家Id-" + player.getId() + "修改宣言");

		} catch (ProtocolException ex) {
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_DECLARATION_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());

		}
	}
}