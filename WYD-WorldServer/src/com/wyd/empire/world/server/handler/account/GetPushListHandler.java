package com.wyd.empire.world.server.handler.account;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.account.GetPushList;
import com.wyd.empire.protocol.data.account.GetPushListOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取本地推送列表
 * 
 * @since JDK 1.6
 */
public class GetPushListHandler implements IDataHandler {
	private Logger log = Logger.getLogger(GetPushListHandler.class);

	public void handle(AbstractData data) throws Exception {
		String version = "1"; // 服务器最新版本号
		String pushList = "";// "{\"pushList\":{\"title\":\"弹弹岛\",\"content\":\"欢迎来到弹弹岛！\",\"startTime\":\"2014-08-08\",\"endTime\":\"2014-08-18\",\"startPushTime\":\"10:25:20\",\"daysOfWeek\":\"2,3\",\"type\":2},{\"title\":\"标题\",\"content\":\"内容\",\"startTime\":\"\",\"endTime\":\"\",\"startPushTime\":\"10:25:20\",\"daysOfWeek\":\"\",\"type\":1}}";
								// //推送列表数组里面是json字符串
		boolean isUpdate = false; // 是否需要更新客户端列表
		GetPushList getPushList = (GetPushList) data;
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			version = ServiceManager.getManager().getLocalPushListService().getVersion() + "";
			GetPushListOk getOk = new GetPushListOk(data.getSessionId(), data.getSerial());
			if (getPushList.getVersion().equals("0") || !getPushList.getVersion().equals(version)) {
				pushList = ServiceManager.getManager().getLocalPushListService().getJsonPushList();
				isUpdate = true;
			}
			getOk.setUpdates(isUpdate);
			getOk.setVersion(version);
			getOk.setPushList(pushList);
			session.write(getOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY)) {
				this.log.error(ex, ex);
			}
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}

	// /**
	// * GMT更新本地推送列表时通知在线玩家
	// */
	// public void notifyOnliePlayer() {
	// Collection<WorldPlayer> playerList =
	// ServiceManager.getManager().getPlayerService().getOnlinePlayer();
	// GetPushListOk getOk = new GetPushListOk();
	// getOk.setUpdates(true);
	// getOk.setVersion(ServiceManager.getManager().getLocalPushListService().getVersion()
	// + "");
	// getOk.setPushList(ServiceManager.getManager().getLocalPushListService().getJsonPushList());
	// for (WorldPlayer player : playerList) {
	// if (player == null)
	// continue;
	// try {
	// ServiceManager.getManager().getSimpleThreadPool().execute(new
	// SendLocalPushListThread(player, getOk));
	// } catch (Exception ex) {
	// log.error(ex, ex);
	// }
	// }
	// }
	//
	// public class SendLocalPushListThread implements Runnable {
	// WorldPlayer player;
	// GetPushListOk pushList;
	//
	// public SendLocalPushListThread(WorldPlayer player, GetPushListOk
	// pushList) {
	// this.player = player;
	// this.pushList = pushList;
	// }
	// @Override
	// public void run() {
	// player.sendData(pushList);
	// }
	// }
}