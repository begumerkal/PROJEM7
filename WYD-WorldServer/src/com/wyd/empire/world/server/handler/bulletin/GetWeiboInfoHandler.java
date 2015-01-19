package com.wyd.empire.world.server.handler.bulletin;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bulletin.GetWeiboInfoOk;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.server.service.base.IShareService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取微博文字和图片链接
 * 
 * @author Administrator
 */
public class GetWeiboInfoHandler implements IDataHandler {
	private Logger log;

	public GetWeiboInfoHandler() {
		this.log = Logger.getLogger(GetWeiboInfoHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			OperationConfig operationConfig = ServiceManager.getManager().getVersionService().getVersion();
			GetWeiboInfoOk getWeiboInfoOk = new GetWeiboInfoOk(data.getSessionId(), data.getSerial());
			IShareService shareService = ServiceManager.getManager().getShareService();
			getWeiboInfoOk.setContent(shareService.getEditContent());
			getWeiboInfoOk.setPicurl(operationConfig.getWbPicUrl());
			getWeiboInfoOk.setWbAppKey(operationConfig.getWbAppKey());
			getWeiboInfoOk.setWebAppSecret(operationConfig.getWebAppSecret());
			getWeiboInfoOk.setWebAppRedirectUri(operationConfig.getWebAppRedirectUri());
			getWeiboInfoOk.setWbUid(operationConfig.getWbUid());
			session.write(getWeiboInfoOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}