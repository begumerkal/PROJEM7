package com.wyd.empire.world.server.handler.admin;

import java.util.Properties;

import net.sf.json.JSONObject;

import com.wyd.empire.protocol.data.admin.GetSystemInfoResult;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 查询系统信息
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @author mazheng
 */
public class GetSystemInfoHandler implements IDataHandler {
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		GetSystemInfoResult getSystemInfoResult = new GetSystemInfoResult(data.getSessionId(), data.getSerial());
		try {
			Properties p = new Properties();
			p.setProperty("onlinePlayerNum", ServiceManager.getManager().getPlayerService().getOnlinePlayerNum() + "");
			p.setProperty("areaPlayerNum", ServiceManager.getManager().getIPlayerService().getAllPlayerNum(true) + "");
			p.setProperty("allPlayerNum", ServiceManager.getManager().getIPlayerService().getAllPlayerNum(false) + "");
			JSONObject object = JSONObject.fromObject(p);
			getSystemInfoResult.setContent(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
			getSystemInfoResult.setContent("");
		}
		session.write(getSystemInfoResult);
	}
}