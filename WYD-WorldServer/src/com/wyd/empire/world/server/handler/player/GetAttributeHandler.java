package com.wyd.empire.world.server.handler.player;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.player.GetAttributeOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取升级提升
 * 
 * @author zguoqiu
 */
public class GetAttributeHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetAttributeHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			int level = player.getLevel();
			int hp = player.getMaxHP();
			int attack = player.getAttack();
			int defend = player.getDefend();
			GetAttributeOk getAttributeOk = new GetAttributeOk(data.getSessionId(), data.getSerial());
			getAttributeOk.setLevel(level);
			getAttributeOk.setHp(hp);
			getAttributeOk.setAttack(attack);
			getAttributeOk.setDefend(defend);
			getAttributeOk.setAttributeInfo("");
			session.write(getAttributeOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}
