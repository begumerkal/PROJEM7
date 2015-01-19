package com.wyd.empire.world.server.handler.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONObject;
import com.wyd.empire.protocol.data.admin.UpdateByJson;
import com.wyd.empire.protocol.data.admin.UpdateByJsonResult;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerPicture;
import com.wyd.empire.world.bean.Promotions;
import com.wyd.empire.world.common.util.KeyProcessService;
import com.wyd.empire.world.common.util.StrValueUtils;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * gm工具json数据更新
 * 
 * @see com.sumsharp.protocol.handler.IDataHandlerhua
 * @author mazheng
 */
public class UpdateByJsonHandler implements IDataHandler {
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		UpdateByJson updateByJson = (UpdateByJson) data;
		UpdateByJsonResult updateByJsonResult = new UpdateByJsonResult(data.getSessionId(), data.getSerial());
		try {
			int updateType = updateByJson.getUpdateType();
			switch (updateType) {
				case 5 :
					JSONObject mailObj = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getMailService()
							.updateMailHandle(mailObj.getString("id"), "Y", mailObj.getString("userName"));
					break;
				case 6 :
					JSONObject opMailObj = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getMailService().updateMailType(opMailObj.getString("id"), opMailObj.getInt("type"));
					break;
				case 7 :
					JSONObject cacheObj = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					int type = cacheObj.getInt("type");
					switch (type) {
						case 0 :
							ServiceManager.getManager().getMapsService().initDate();
							break;
						case 1 :
							int playerId = cacheObj.getInt("playerId");
							WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
							Player player = (Player) ServiceManager.getManager().getPlayerService().getService()
									.get(Player.class, playerId);
							worldPlayer.setPlayer(player);
							worldPlayer.initial();
							worldPlayer.firstLoad();
							break;
						case 2 :
							// 初始化首冲奖励、抽奖列表
							ServiceManager.getManager().getRechargeRewardService().findInitList();
							break;
						case 3 :
							// 初始促销列表
							ServiceManager.getManager().getPromotionsService().initPromotionsList();
							break;
						case 4 :
							// 初始化玩家升级提示语
							// ServiceManager.getManager().getPlayerAttributeService().init();
							break;
						case 5 :
							// 初始化自定义推荐列表
							ServiceManager.getManager().getShopItemService().getRecommendList();
							break;
						case 6 :
							// 重载初始化数据
							ServiceManager.getManager().initBaseData();
							break;
						case 7 :
							// 重载日常任务
							ServiceManager.getManager().getDailyActivityService().initData();
							break;
						default :
							break;
					}
					break;
				case 8 :
					JSONObject stickObject = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getMailService().updateMailStick(stickObject.getString("id"), stickObject.getInt("type"));
					break;
				case 9 :
					JSONObject bulletinObject = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getBulletinService().getService().deleteBulletin(bulletinObject.getString("ids"));
					break;
				case 10 :
					JSONObject activity = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getActivitiesAwardService().deleteActivityByIds(activity.getString("ids"));
					break;
				case 11 :
					JSONObject promotions = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getPromotionsService().getIPromotionsService()
							.deletePromotions(promotions.getString("ids"));
					break;
				case 12 :
					JSONObject promotionsObj = JSONObject.fromObject(updateByJson.getContent());
					Promotions promotion = (Promotions) ServiceManager.getManager().getPromotionsService().getIPromotionsService()
							.get(Promotions.class, promotionsObj.getInt("pId"));
					promotion.setShopItem(ServiceManager.getManager().getShopItemService().getShopItemById(promotionsObj.getInt("shopId")));
					promotion.setQuantity(promotionsObj.getInt("quantity"));
					promotion.setIsActivate(promotionsObj.getString("isActivate"));
					promotion.setDiscount(promotionsObj.getInt("discount"));
					promotion.setGold(promotionsObj.getInt("gold"));
					promotion.setCount(promotionsObj.getInt("count"));
					promotion.setDays(promotionsObj.getInt("days"));
					promotion.setPersonal(promotionsObj.getInt("personal"));
					ServiceManager.getManager().getPromotionsService().getIPromotionsService().update(promotion);
					break;
				case 13 :
					JSONObject loginRewardObj = JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getIPlayerService().deleteByRewardIds(loginRewardObj.getString("ids"));
					break;
				case 14 :
					JSONObject serverInfoObj = JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getInviteService().getIInviteService()
							.deleteByServerInfoIds(serverInfoObj.getString("ids"));
					break;
				case 15 :
					JSONObject inviteRewardObj = JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getInviteService().getIInviteService()
							.deleteByInviteRewardIds(inviteRewardObj.getString("ids"));
					break;
				case 16 :
					JSONObject recommendObj = JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getShopItemService().deleteByRecommendIds(recommendObj.getString("ids"));
					break;
				case 17 :
					JSONObject playerObj = JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getPlayerService().killLine(playerObj.getInt("playerId"));
					break;
				case 18 :
					JSONObject everyDayObj = JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getTaskService().getService().deleteByeveryDayIds(everyDayObj.getString("ids"));
					break;
				case 21 :
					JSONObject bulletinObj = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					String[] bulletIds = bulletinObj.optString("ids", "").split(",");
					List<Object> array = new ArrayList<Object>();
					StringBuilder hql = new StringBuilder();
					hql.append(" update tab_bulletin set is_activation =? where 1=1");
					array.add("Y");
					for (int i = 0; i < bulletIds.length; i++) {
						hql.append(" or id = ?");
						array.add(Integer.valueOf(bulletIds[i]));
					}
					// Bulletin bulletin=null;
					// for (String id : bulletIds) {
					// bulletin = (Bulletin)
					// ServiceManager.getManager().getBulletinService().getService().get(Bulletin.class,
					// Integer.valueOf(id));
					// if(bulletin!=null){
					// bulletin.setIsActivation("Y");
					// ServiceManager.getManager().getBulletinService().getService().update(bulletin);
					// }
					// }
					// ServiceManager.getManager().getBulletinService().getService().updateBulletByIds(bulletIds);
					ServiceManager.getManager().getBulletinService().getService().executeSql(hql.toString(), array.toArray());
					break;
				case 22 :// 更新每日签到奖励配置文件SIGN_INFO的描述值
					JSONObject signInfo = JSONObject.fromObject(updateByJson.getContent());
					StrValueUtils.getInstance().writeProperties("SIGN_INFO", signInfo.getString("signInfo"));
					TipMessages.SIGN_INFO = signInfo.getString("signInfo");
					break;
				case 23 :// 更新客户端活动url
					JSONObject url = JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getVersionService().setUrl(url.getString("url"), url.getString("areaIds"));
					break;
				case 24 :// 根据多个ID值删除活动促销
					JSONObject magnificationObj = JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getMagnificationService().deleteByIds(magnificationObj.getString("ids"));
					break;
				case 25 :// 根据多个ID值删除全服物品奖励
					JSONObject fullServiceRewardObj = JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getRewardItemsService().deleteByIds(fullServiceRewardObj.getString("ids"));
					break;
				case 28 :// 重置玩家日常任务
					WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService()
							.getWorldPlayerById(JSONObject.fromObject(updateByJson.getContent()).getInt("playerId"));
					ServiceManager.getManager().getPlayerTaskTitleService().initialPlayerTask(worldPlayer);
					break;
				case 29 :
					JSONObject dailyActivity = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getIDailyActivityService().deleteActivityByIds(dailyActivity.getString("ids"));
					break;
				case 31 :// 批量删除付费包奖励
					JSONObject appReward = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getPayAppRewardService().deleteAppReward(appReward.getString("ids"));
					break;
				case 32 :// 批量删除button信息
					JSONObject buttonInfo = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getOperationConfigService().deleteButtonInfo(buttonInfo.getString("ids"));
					List<WorldPlayer> playerList = new ArrayList<WorldPlayer>(ServiceManager.getManager().getPlayerService().getAllPlayer());
					for (WorldPlayer worldPlayers : playerList) {
						if (worldPlayers.getButtonInfo() != null) {
							worldPlayers.setButtonInfo(null);
						}
					}
					break;
				case 33 :// 批量审核通过玩家头像信息
					JSONObject PictureInfo = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					List<PlayerPicture> list = ServiceManager.getManager().getPictureUploadService()
							.getPictureListById(PictureInfo.getString("ids"));
					StringBuffer testUrls = null;
					for (PlayerPicture playerPicture : list) {
						String passUrl = playerPicture.getPictureUrlPass().replace(",,", ",");
						if (playerPicture.getPictureUrlTest().equals(null) || playerPicture.getPictureUrlTest().equals("")) {
							continue;
						}
						String[] testUrlArry = playerPicture.getPictureUrlTest().split(",");
						testUrls = new StringBuffer("");
						for (String urls : testUrlArry) {
							if (urls.equals("") || urls.equals("")) {
								continue;
							}
							String[] testUrl = urls.split("#");
							if (testUrl.length != 2) {
								continue;
							}
							if (testUrl[1].equals("-1") || passUrl.indexOf(testUrl[1]) == -1) {
								if (!testUrls.equals(null) && testUrls.length() != 0) {
									testUrls.append(",");
								}
								testUrls.append(testUrl[0]);
							} else {
								passUrl = passUrl.replace(testUrl[1], testUrl[0]);
							}
						}
						if (!passUrl.equals("")) {
							if (!testUrls.equals("")) {
								testUrls.append(",");
							}
							testUrls.append(passUrl);
						}
						playerPicture.setPictureUrlPass(testUrls.toString().replace(",,", ","));
						playerPicture.setPictureUrlTest("");
						playerPicture.setAuditedTime(new Date());
						ServiceManager.getManager().getPictureUploadService().update(playerPicture);
						WorldPlayer player = ServiceManager.getManager().getPlayerService().getLoadPlayer(playerPicture.getPlayerId());
						if (null != player) {
							player.setPlayerPicture(playerPicture);
						}
					}
					break;
				case 34 :// 批量删除玩家头像信息
					JSONObject Pictureids = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					List<PlayerPicture> deleteList = ServiceManager.getManager().getPictureUploadService()
							.getPictureListById(Pictureids.getString("ids"));
					for (PlayerPicture playerPicture : deleteList) {
						playerPicture.setPictureUrlTest("");
						playerPicture.setAuditedTime(new Date());
						ServiceManager.getManager().getPictureUploadService().update(playerPicture);
						WorldPlayer player = ServiceManager.getManager().getPlayerService().getLoadPlayer(playerPicture.getPlayerId());
						if (null != player) {
							player.setPlayerPicture(playerPicture);
						}
					}
					break;
				case 35 :// 删除一条外挂信息
					JSONObject keyProcess = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					KeyProcessService service = KeyProcessService.getInstance();
					if (keyProcess != null && !keyProcess.getString("value").equals("")) {
						service.removeProcess(keyProcess.getString("value"));
					} else {
						updateByJsonResult.setSuccess(false);
					}
					break;
				case 36 :// 重新初始化玩家任务列表
					JSONObject playerTask = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerTask.getInt("playerId"));
					if (player != null) {
						try {
							ServiceManager.getManager().getPlayerTaskTitleService().initCreateDefaultTaskList(player.getPlayer());
							player.initialPlayerTaskTitle();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				case 37 :// copy按钮信息列表
					JSONObject areasIds = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					ServiceManager.getManager().getVersionService().getService().copyButtonInfoByAreasIds(areasIds.getString("ids"));
					break;
				case 38 :// 更新玩家活跃度
					JSONObject playerVitality = (JSONObject) JSONObject.fromObject(updateByJson.getContent());
					WorldPlayer playerOl = ServiceManager.getManager().getPlayerService()
							.getWorldPlayerById(playerVitality.getInt("playerId"));
					if (playerOl != null) {
						try {
							playerOl.vigorUp(playerVitality.getInt("vigor"));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				default :
					break;
			}
			updateByJsonResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			updateByJsonResult.setSuccess(false);
		}
		session.write(updateByJsonResult);
	}
}