package com.wyd.empire.world.server.handler.community;

import java.util.Date;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.CommunitySetEnemy;
import com.wyd.empire.protocol.data.community.CommunitySetEnemyOk;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 
 * 类 <code> SetEnemyCommunityHandler</code>Protocol.COMMUNITY
 * _IsAcceptMember设置敌对公会
 * 
 * @since JDK 1.6
 * 
 */
public class CommunitySetEnemyHandler implements IDataHandler {
	private Logger log;

	public CommunitySetEnemyHandler() {
		this.log = Logger.getLogger(CommunitySetEnemyHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		CommunitySetEnemy setEnemyCommunity = (CommunitySetEnemy) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		boolean mark = false;
		try {
			CommunitySetEnemyOk enemyCommunityOk = new CommunitySetEnemyOk(data.getSessionId(), data.getSerial());

			// 获得要修改对象
			Consortia con = (Consortia) ServiceManager.getManager().getConsortiaService()
					.get(Consortia.class, setEnemyCommunity.getCommunityId());
			if (con == null) {
				throw new NullPointerException();// 如果传过来参数有问题，找不到公会，抛空指针异常
			}

			if (player.getId() != con.getPresident().getId().intValue()) {
				mark = true;
				throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_HAVE_RIGHT);
			}

			// 获得敌对公会对象
			Consortia enemyCon = (Consortia) ServiceManager.getManager().getConsortiaService()
					.getConsortiaById(setEnemyCommunity.getEnemyCommunityId());
			if (enemyCon == null && setEnemyCommunity.getEnemyCommunityId() != -1) {
				mark = true;
				throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_NOT_REAL);// 如果传过来参数有问题，找不到公会，抛空指针异常
			}

			if (setEnemyCommunity.getEnemyCommunityId() == -1) {
				con.setHosId(0);
				enemyCommunityOk.setResult(false);
			} else {
				con.setHosId(setEnemyCommunity.getEnemyCommunityId());
				enemyCommunityOk.setResult(true);
			}
			con.setHosTime(new Date());
			// 更新对象
			ServiceManager.getManager().getConsortiaService().update(con);

			session.write(enemyCommunityOk);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (mark) {
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			} else {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
		}
	}
}