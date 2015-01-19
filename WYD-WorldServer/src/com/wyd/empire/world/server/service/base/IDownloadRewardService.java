package com.wyd.empire.world.server.service.base;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.DownloadReward;
import com.wyd.empire.world.player.WorldPlayer;

public interface IDownloadRewardService extends UniversalManager {
	/**
	 * 初始化数据
	 */
	public void initData();

	public DownloadReward findDownloadReward(int playerLv);

	/**
	 * 分包下载奖励,升级时调用
	 * 
	 * @param player
	 */
	public void sendDownloadReward(WorldPlayer player);
}
