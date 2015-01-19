package com.wyd.empire.world.server.handler.errorcode;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.errorcode.GetSmsCodeNewList;
import com.wyd.empire.protocol.data.errorcode.GetSmsCodeNewListOk;
import com.wyd.empire.world.battle.MonthCardVo;
import com.wyd.empire.world.bean.BillingPoint;
import com.wyd.empire.world.bean.Channel;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取兑换比率列表
 * 
 * @author cj
 */
public class GetSmsCodeNewListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetSmsCodeNewListHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetSmsCodeNewList getCode = (GetSmsCodeNewList) data;
		try {
			List<Integer> idList = new ArrayList<Integer>();
			List<Integer> priceList = new ArrayList<Integer>();
			List<String> smsCodeList = new ArrayList<String>();
			List<Integer> itemIdList = new ArrayList<Integer>();
			List<String> itemNameList = new ArrayList<String>();
			List<String> itemIconList = new ArrayList<String>();
			// //数量类型0:天数 1:个数,2:月卡
			List<Integer> countTypeList = new ArrayList<Integer>();
			List<Integer> countList = new ArrayList<Integer>();
			List<String> remark1List = new ArrayList<String>();
			List<String> remark2List = new ArrayList<String>();
			// 扩展参数【默认空字符串】格式如：CPID:741511,CPServiceID:651110072600
			List<String> extensionInfoList = new ArrayList<String>();
			// 公司资质信息【默认空字符串】格式如：CPID:741511,CPServiceID:651110072600
			String qualificationInfo = "";
			// 获取此渠道信息
			Channel channel = ServiceManager.getManager().getRechargeService().getChannelById(getCode.getChannelId());
			if (null != channel) {
				// 赋值公司资质信息
				qualificationInfo = channel.getQualificationInfo();
			}
			List<BillingPoint> pointInfoList = ServiceManager.getManager().getOrderService().getPointList();
			for (BillingPoint pointInfo : pointInfoList) {
				// 过滤非通用数据和非本渠道数据
				if (pointInfo.getChannelId() != -1 && pointInfo.getChannelId() != getCode.getChannelId()) {
					continue;
				}
				ShopItem shopItem = ServiceManager.getManager().getShopItemService().getShopItemById(pointInfo.getItemId());
				if (null != shopItem) {
					idList.add(pointInfo.getId());
					priceList.add((int) pointInfo.getPrice());
					smsCodeList.add(pointInfo.getSmsCode());
					itemIdList.add(shopItem.getId());
					itemNameList.add(shopItem.getName());
					itemIconList.add(shopItem.getIcon());
					countTypeList.add(pointInfo.getType());
					countList.add(pointInfo.getCount());
					remark1List.add(pointInfo.getRemark1());
					remark2List.add(pointInfo.getRemark2());
					extensionInfoList.add(pointInfo.getExtensionInfo());
				} else {
					MonthCardVo menthCard = ServiceManager.getManager().getMonthCardService().getMonthCardById(pointInfo.getItemId());
					if (null != menthCard) {
						idList.add(pointInfo.getId());
						priceList.add((int) pointInfo.getPrice());
						smsCodeList.add(pointInfo.getSmsCode());
						itemIdList.add(menthCard.getCardId());
						itemNameList.add(menthCard.getMonthCardName());
						itemIconList.add("");
						countTypeList.add(pointInfo.getType());
						countList.add(pointInfo.getCount());
						remark1List.add(pointInfo.getRemark1());
						remark2List.add(pointInfo.getRemark2());
						extensionInfoList.add(pointInfo.getExtensionInfo());
					}
				}
			}
			GetSmsCodeNewListOk getSmsCodeListOk = new GetSmsCodeNewListOk(data.getSessionId(), data.getSerial());
			getSmsCodeListOk.setId(ServiceUtils.getInts(idList.toArray()));
			getSmsCodeListOk.setPrice(ServiceUtils.getInts(priceList.toArray()));
			getSmsCodeListOk.setSmsCode(smsCodeList.toArray(new String[0]));
			getSmsCodeListOk.setItemId(ServiceUtils.getInts(itemIdList.toArray()));
			getSmsCodeListOk.setItemName(itemNameList.toArray(new String[0]));
			getSmsCodeListOk.setItemIcon(itemIconList.toArray(new String[0]));
			getSmsCodeListOk.setCountType(ServiceUtils.getInts(countTypeList.toArray()));
			getSmsCodeListOk.setCount(ServiceUtils.getInts(countList.toArray()));
			getSmsCodeListOk.setRemark1(remark1List.toArray(new String[0]));
			getSmsCodeListOk.setRemark2(remark2List.toArray(new String[0]));
			getSmsCodeListOk.setExtensionInfo(extensionInfoList.toArray(new String[0]));
			getSmsCodeListOk.setQualificationInfo(qualificationInfo);
			session.write(getSmsCodeListOk);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
