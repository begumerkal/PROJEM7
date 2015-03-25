package com.wyd.empire.world.server.handler.admin;

import com.wyd.empire.protocol.data.admin.Version;
import com.wyd.empire.protocol.data.admin.VersionResult;
import com.wyd.empire.world.common.util.VersionUtils;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 版本管理
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @author doter
 */
public class VersionHandler implements IDataHandler {
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		VersionResult versionResult = new VersionResult(data.getSessionId(), data.getSerial());
		try {
			Version version = (Version) data;
			String num = version.getNum();
			String updateurl = version.getUpdateurl();
			String remark = version.getRemark();
			String appraisal = version.getAppraisal();
			if (null != num || null != updateurl || null != remark) {
				if (null != num)
					VersionUtils.update("num", num);
				if (null != updateurl)
					VersionUtils.update("updateurl", updateurl);
				if (null != remark)
					VersionUtils.update("remark", remark);
				if (null != appraisal)
					VersionUtils.update("appraisal", appraisal);
				ServiceManager.getManager().getConnectService().UpdateVersion();
			}
			versionResult.setSuccess(true);
		} catch (Exception e) {
			versionResult.setSuccess(false);
		}
		session.write(versionResult);
	}
}