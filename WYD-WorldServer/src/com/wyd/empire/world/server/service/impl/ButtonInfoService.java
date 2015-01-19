package com.wyd.empire.world.server.service.impl;

import java.util.Collection;

import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class ButtonInfoService {

	public void synButtonInfo() {
		ServiceManager.getManager().getSimpleThreadPool().execute(createTask());
	}

	// 更新弹王按钮的状态
	private Runnable createTask() {
		return new ButtonInfoThread();
	}

	public class ButtonInfoThread implements Runnable {
		@Override
		public void run() {
			// 更新弹王按钮的状态
			Collection<WorldPlayer> playerList = ServiceManager.getManager().getPlayerService().getOnlinePlayer();
			for (WorldPlayer player : playerList) {
				player.synButtonInfo();
			}
		}
	}
}
