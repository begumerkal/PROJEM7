package com.wyd.empire.world.server.handler.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wyd.empire.protocol.data.admin.GivenItems;
import com.wyd.empire.protocol.data.admin.GivenItemsResult;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.Recharge;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 给予玩家物品
 * 
 * @author zgq
 */
public class GivenItemsHandler implements IDataHandler {
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		GivenItems givenItems = (GivenItems) data;
		int[] playerIds = givenItems.getPlayerIds();
		int[] itemIds = givenItems.getItemIds();
		int[] counts = givenItems.getCounts();
		int[] dayOrcount = givenItems.getDayOrcount();
		String remark = givenItems.getRemark();
		int[] strengthen = givenItems.getStrengthen();
		String[] orderNum = givenItems.getOrderNum();
		int[] itemType = givenItems.getItemType();
		List<Integer> pList = new ArrayList<Integer>();
		List<Integer> iList = new ArrayList<Integer>();
		List<Integer> cList = new ArrayList<Integer>();
		List<Integer> dList = new ArrayList<Integer>();
		List<Integer> sList = new ArrayList<Integer>();
		List<String> oList = new ArrayList<String>();
		List<Integer> itemTypeList = new ArrayList<Integer>();

		for (int i = 0; i < playerIds.length; i++) {
			try {
				WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerIds[i]);
				if (null == worldPlayer) {
					throw new Exception();
				}

				if (Common.GIVENITEM_TYPE_PET == itemType[i]) { // 发放宠物
					String warn = "";
					Map<Integer, PlayerPet> ppsMap = ServiceManager.getManager().getPetItemService().getPlayerPetMap(worldPlayer.getId());
					if (null != ppsMap.get(itemIds[i])) {// 校验是否已拥有
						warn += "玩家" + worldPlayer.getId() + "已拥有宠物" + itemIds[i] + ", ";
					}
					if (!warn.equals("")) {
						throw new Exception(warn);
					}
					ServiceManager.getManager().getPlayerPetService().playerGetPet(worldPlayer.getId(), itemIds[i], false);
					GameLogService.addPet(worldPlayer.getId(), worldPlayer.getLevel(), itemIds[i], 2, 0, 0, 0);
					// // 保存邮件
					// Mail mail = new Mail();
					// mail.setTheme(TipMessages.GIVENITEM_PET_MESSAGE);
					// mail.setContent(TipMessages.GIVENITEM_PET_MESSAGE);
					// mail.setIsRead(false);
					// mail.setReceivedId(worldPlayer.getId());
					// mail.setSendId(0);
					// mail.setSendName(TipMessages.SYSNAME_MESSAGE);
					// mail.setSendTime(new Date());
					// mail.setType(1);
					// mail.setBlackMail(false);
					// mail.setIsStick(Common.IS_STICK);
					// ServiceManager.getManager().getMailService().saveMail(mail,
					// null);
				} else { // 发放游戏物品
					if (Common.GOLDID == itemIds[i]) {
						ServiceManager.getManager().getPlayerService().updatePlayerGold(worldPlayer, counts[i], "GM给予", remark);
					} else if (Common.DIAMONDID == itemIds[i]) {
						String rechargeRemark = "";
						int orgigin = 0;
						String rechargeOrderNum = "";
						Float price = 0f;
						Date currentDate = null;
						if (!("-1").equals(orderNum[i])) {
							String[] vlaues = orderNum[i].split("\\|");
							// 充值未到账的补充充值记录
							Recharge recharge = ServiceManager.getManager().getRechargeService().getRechargeByAmount(counts[i]);
							orgigin = TradeService.ORIGIN_RECH;
							rechargeRemark = "充值未到账";
							rechargeOrderNum = vlaues[0];
							price = recharge.getPrice();
							if (vlaues.length > 1) {
								currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(vlaues[1]);
							} else {
								currentDate = new Date();
							}
						} else {
							orgigin = TradeService.ORIGIN_GM;
							rechargeRemark = remark;
							rechargeOrderNum = "";
							price = 0f;
							currentDate = new Date();
						}
						ServiceManager.getManager().getPlayerService()
								.addTicketGm(worldPlayer, counts[i], orgigin, price, rechargeOrderNum, rechargeRemark, "", "", currentDate);
					} else {
						int days = -1;
						int userNum = -1;
						if (0 == dayOrcount[i]) {
							days = counts[i];
						} else {
							userNum = counts[i];
						}
						ServiceManager.getManager().getRechargeRewardService()
								.givenItems(worldPlayer, userNum, days, itemIds[i], strengthen[i], remark);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				pList.add(playerIds[i]);
				iList.add(itemIds[i]);
				cList.add(counts[i]);
				dList.add(dayOrcount[i]);
				sList.add(strengthen[i]);
				oList.add(orderNum[i]);
				itemTypeList.add(itemType[i]);
			}
		}
		int length = pList.size();
		GivenItemsResult givenItemsResult = new GivenItemsResult(data.getSessionId(), data.getSerial());
		givenItemsResult.setLength(length);
		givenItemsResult.setPlayerIds(ServiceUtils.getInts(pList.toArray()));
		givenItemsResult.setItemIds(ServiceUtils.getInts(iList.toArray()));
		givenItemsResult.setCounts(ServiceUtils.getInts(cList.toArray()));
		givenItemsResult.setDayOrcount(ServiceUtils.getInts(dList.toArray()));
		givenItemsResult.setStrengthen(ServiceUtils.getInts(sList.toArray()));
		givenItemsResult.setOrderNum(ServiceUtils.getStrings(oList.toArray()));
		givenItemsResult.setItemType(ServiceUtils.getInts(itemTypeList.toArray()));
		session.write(givenItemsResult);
	}
}