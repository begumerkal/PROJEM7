package com.wyd.empire.world.server.handler.admin;

import java.util.ArrayList;
import java.util.List;
import com.wyd.empire.protocol.data.admin.Delete;
import com.wyd.empire.protocol.data.admin.DeleteResult;
import com.wyd.empire.world.bean.Bulletin;
import com.wyd.empire.world.bean.ButtonInfo;
import com.wyd.empire.world.bean.DownloadReward;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.bean.PayAppReward;
import com.wyd.empire.world.bean.PlayerDIYTitle;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerItemsFromShopService;
import com.wyd.empire.world.server.service.base.impl.PlayerItemsFromShopService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * gm工具删除数据
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @author mazheng
 */
public class DeleteHandler implements IDataHandler {
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		Delete delete = (Delete) data;
		DeleteResult deleteResult = new DeleteResult(data.getSessionId(), data.getSerial());
		int deleteType = delete.getDeleteType();
		int id = delete.getId();
		try {
			switch (deleteType) {
				case 0 :
					ServiceManager.getManager().getBulletinService().getService().remove(Bulletin.class, id);
					break;
				case 4 :
					IPlayerItemsFromShopService playerItemsFromShopService = ServiceManager.getManager().getPlayerItemsFromShopService();
					PlayerItemsFromShop playerItemsFromShop = (PlayerItemsFromShop) playerItemsFromShopService.get(
							PlayerItemsFromShop.class, id);
					if (playerItemsFromShop.getIsInUsed()) {
						ShopItem shopItem = playerItemsFromShop.getShopItem();
						WorldPlayer player = ServiceManager.getManager().getPlayerService()
								.getWorldPlayerById(playerItemsFromShop.getPlayerId());
						OperationConfig config = ServiceManager.getManager().getVersionService().getVersion();
						int suitNum = 0;

						PlayerItemsFromShopService p = (PlayerItemsFromShopService) ServiceManager.getManager()
								.getPlayerItemsFromShopService();

						if (shopItem.isWeapon()) {
							PlayerItemsFromShop playerItem = p.uniquePlayerItem(player.getId(), config.getSuitWeaponId());
							player.setWeapon(playerItem);
							suitNum = config.getSuitWeaponId();
						} else if (shopItem.isBody()) {
							suitNum = 1 == player.getPlayer().getSex().intValue() ? config.getGirlBodyId() : config.getBoyBodyId();
							PlayerItemsFromShop playerItem = p.uniquePlayerItem(player.getId(), suitNum);
							player.setBody(playerItem);
						} else if (shopItem.isWing()) {
							player.setWing(null);
						} else if (shopItem.isRing()) {
							player.setRing_L(null);
							player.setRing_R(null);

						} else if (shopItem.isNecklace()) {
							player.setNecklace(null);
						} else if (shopItem.isFace()) {
							suitNum = 1 == player.getPlayer().getSex().intValue() ? config.getGirlFaceId() : config.getBoyFaceId();
							PlayerItemsFromShop playerItem = p.uniquePlayerItem(player.getId(), suitNum);

							player.setFace(playerItem);
						} else if (shopItem.isHead()) {
							suitNum = 1 == player.getPlayer().getSex().intValue() ? config.getGirlHeadId() : config.getBoyHeadId();
							PlayerItemsFromShop playerItem = p.uniquePlayerItem(player.getId(), suitNum);
							player.setHead(playerItem);
						}
						ServiceManager.getManager().getPlayerItemsFromShopService().deletePlayerItem(playerItemsFromShop);
						if (suitNum != 0) {
							ServiceManager.getManager().getPlayerItemsFromShopService()
									.updateInUseByShopId(player.getPlayer().getId(), suitNum, true);
						}
						ServiceManager.getManager().getPlayerService().savePlayerData(player.getPlayer());
						player.updateFight();
					} else {
						ServiceManager.getManager().getPlayerItemsFromShopService().deletePlayerItem(playerItemsFromShop);
					}
					break;
				case 8 :
					ServiceManager.getManager().getPlayerDIYTitleService().remove(PlayerDIYTitle.class, id);
					break;
				case 9 :// 删除付费包奖励 TODO 此数据服务端开发人员未将它存到内存中去
					ServiceManager.getManager().getPayAppRewardService().remove(PayAppReward.class, id);
					break;
				case 10 :// 删除button信息
					ServiceManager.getManager().getOperationConfigService().remove(ButtonInfo.class, id);
					List<WorldPlayer> playerList = new ArrayList<WorldPlayer>(ServiceManager.getManager().getPlayerService().getAllPlayer());
					for (WorldPlayer worldPlayers : playerList) {
						if (worldPlayers.getButtonInfo() != null) {
							worldPlayers.setButtonInfo(null);
						}
					}
					break;
				case 11 :// 删除本地推送信息
					ServiceManager.getManager().getLocalPushListService().deletePushByIds(id);
					ServiceManager.getManager().getLocalPushListService().updateVersion(-1);
					ServiceManager.getManager().getLocalPushListService().initPushData();
					// new GetPushListHandler().notifyOnliePlayer();
					break;
				case 12 :// 删除分包奖励信息
					ServiceManager.getManager().getDownloadRewardService().remove(DownloadReward.class, id);
					break;
			}
			deleteResult.setSuccess(true);
		} catch (Exception e) {
			deleteResult.setSuccess(false);
		}
		session.write(deleteResult);
	}
}