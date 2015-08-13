package com.app.empire.world.server.handler.chat;

import org.apache.log4j.Logger;

import com.app.empire.protocol.data.chat.ChangeChannel;
import com.app.empire.world.entity.mongo.Player;
import com.app.empire.world.model.player.WorldPlayer;
import com.app.empire.world.service.factory.ServiceManager;
import com.app.empire.world.session.ConnectSession;
import com.app.protocol.data.AbstractData;
import com.app.protocol.handler.IDataHandler;

/**
 * 更换当前频道
 * 
 * @author Administrator
 */
public class ChangeChannelHandler implements IDataHandler {
	Logger log = Logger.getLogger(ChangeChannelHandler.class);

	public AbstractData handle(AbstractData data) throws Exception {
		ServiceManager.getManager().getSimpleThreadPool().execute(createTask(data));
		return null;
	}

	private Runnable createTask(AbstractData data) {
		return new ChangeChannelThread(data);
	}

	public class ChangeChannelThread implements Runnable {
		private AbstractData data;

		public ChangeChannelThread(AbstractData data) {
			this.data = data;
		}

		@Override
		public void run() {
			try {
				ConnectSession session = (ConnectSession) data.getHandlerSource();
				WorldPlayer worldPlayer = session.getPlayer(data.getSessionId());
				if (null == worldPlayer) {
					return;
				}
				Player player= worldPlayer.getPlayer();
				ChangeChannel changeChannel = (ChangeChannel) data;
				int channelId = player.getChannelId();
//				Interface intf = (Interface) ServiceManager.getManager().getInterfaceService()
//						.get(Interface.class, changeChannel.getChannelId());
//				if (null != intf && 1 == intf.getIsmain()) {
//					ServiceManager.getManager().getChatService().changeMapChannel(player, channelId, changeChannel.getChannelId());
//					player.setChannelId(changeChannel.getChannelId());
//				}
//				if (Common.INTERFACE_ID_ACTIVE == channelId) {
//					player.updateButtonInfo(Common.BUTTON_ID_ACTIVE, false, 30);
//				}
//				ServiceManager.getManager().getTaskService().enterInterface(player, changeChannel.getChannelId());
				// 记录界面停留日志
//				int interfaceId = player.getInterfaceId();
//				long inTime = player.getInTime();
//				player.setInterfaceId(changeChannel.getChannelId());
//				player.setInTime(System.currentTimeMillis());
//				GameLogService.remainInterface(player.getId(), player.getLevel(), interfaceId, player.getInTime() - inTime);
//				if (changeChannel.getChannelId() == 79) {
//					GameLogService.clickRecharge(player.getId(), player.getLevel(), interfaceId);
//				}
			} catch (Exception ex) {
				log.error(ex, ex);
			}
		}
	}
}
