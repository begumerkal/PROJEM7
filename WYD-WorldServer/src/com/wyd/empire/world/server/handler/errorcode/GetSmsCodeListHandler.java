package com.wyd.empire.world.server.handler.errorcode;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.errorcode.GetSmsCodeListOk;
import com.wyd.empire.world.bean.BillingPoint;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取兑换比率列表
 * 
 * @author Administrator
 */
public class GetSmsCodeListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetSmsCodeListHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			List<Integer> idList = new ArrayList<Integer>();
			List<Integer> priceList = new ArrayList<Integer>();
			List<String> smsCodeList = new ArrayList<String>();
			List<Integer> itemIdList = new ArrayList<Integer>();
			List<String> itemNameList = new ArrayList<String>();
			List<String> itemIconList = new ArrayList<String>();
			List<Integer> countTypeList = new ArrayList<Integer>();
			List<Integer> countList = new ArrayList<Integer>();
			List<String> remark1List = new ArrayList<String>();
			List<String> remark2List = new ArrayList<String>();
			List<BillingPoint> pointInfoList = ServiceManager.getManager().getOrderService().getPointList();
			for (BillingPoint pointInfo : pointInfoList) {
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
				}
			}
			GetSmsCodeListOk getSmsCodeListOk = new GetSmsCodeListOk(data.getSessionId(), data.getSerial());
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
			session.write(getSmsCodeListOk);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
