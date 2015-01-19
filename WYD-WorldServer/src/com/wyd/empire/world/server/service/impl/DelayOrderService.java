package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.RechargeService.OrderInfo;

public class DelayOrderService implements Runnable {
	private Logger errorLog = Logger.getLogger(RechargeService.class);
	List<OrderInfo> orderList = new ArrayList<RechargeService.OrderInfo>();

	public void start() {
		Thread t = new Thread(this);
		t.setName("DelayOrderService-Thread");
		t.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(60000);
				OrderInfo orderInfo;
				for (int i = orderList.size() - 1; i >= 0; i--) {
					orderInfo = orderList.get(i);
					ServiceManager.getManager().getPCRechargeService().addOrder(orderInfo);
					orderList.remove(i);
				}
			} catch (Exception ex) {
				errorLog.error(ex, ex);
			}
		}
	}

	public void addOrder(OrderInfo oi) {
		orderList.add(oi);
	}
}
