package com.wyd.empire.world.server.handler.community;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.AcceptMemberOr;
import com.wyd.empire.protocol.data.community.AcceptMemberOrOk;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 
 * 类 <code> IsAcceptMemberHandler</code>Protocol.COMMUNITY
 * _IsAcceptMember设置公会是否接受会员协议处理
 * 
 * @since JDK 1.6
 * 
 */
public class AcceptMemberOrHandler implements IDataHandler {
	private Logger log;

	public AcceptMemberOrHandler() {
		this.log = Logger.getLogger(AcceptMemberOrHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		AcceptMemberOr isAcceptMember = (AcceptMemberOr) data;

		try {
			AcceptMemberOrOk acceptMemberOrOk = new AcceptMemberOrOk(data.getSessionId(), data.getSerial());

			// 获得要修改对象
			Consortia con = (Consortia) ServiceManager.getManager().getConsortiaService()
					.get(Consortia.class, isAcceptMember.getCommunityId());
			if (con == null) {
				throw new NullPointerException();// 如果传过来参数有问题，找不到公会，抛空指针异常
			}

			con.setIsAcceptMember(isAcceptMember.getAcceptMember());
			// 更新对象
			ServiceManager.getManager().getConsortiaService().update(con);

			session.write(acceptMemberOrOk);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}