package com.wyd.empire.world.server.service.impl;

import java.util.List;

import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.Bulletin;
import com.wyd.empire.world.server.service.base.IBulletinService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 公告管理服务
 * 
 * @author Administrator
 */
public class BulletinService implements Runnable {
	IBulletinService bulletinService;

	public BulletinService(IBulletinService bulletinService) {
		this.bulletinService = bulletinService;
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName("BulletinService-Thread");
		t.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				// System.out.println((new
				// Date()).toString()+"---BulletinService---sleep");
				Thread.sleep(60000L);
				// System.out.println((new
				// Date()).toString()+"---BulletinService---start");
				List<Bulletin> bulletinList = bulletinService.getBulletinList();
				Bulletin bulletin;
				for (int i = bulletinList.size() - 1; i > -1; i--) {
					bulletin = bulletinList.get(i);
					if (0 == (bulletin.getTimes() % bulletin.getRate())) {
						ServiceManager.getManager().getChatService().sendBulletinToWorld(bulletin.getContent(), "", false);
						bulletin.setTimes(1);
					} else {
						bulletin.setTimes(bulletin.getTimes() + 1);
					}
					bulletinService.update(bulletin);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	public IBulletinService getService() {
		return bulletinService;
	}

	public void addBulletin(Bulletin bulletin, String areas) {
		if (areas.equals(null) && areas.equals("")) {
			bulletin.setAreaId(WorldServer.config.getAreaId());
			bulletin.setTimes(0);
			bulletinService.save(bulletin);
		} else {
			String[] areaId = areas.split(",");
			for (int i = 0; i < areaId.length; i++) {
				bulletin.setAreaId(areaId[i]);
				bulletin.setTimes(0);
				bulletinService.save(bulletin);
			}
		}
	}

	/**
	 * 获取公告列表
	 * 
	 * @return
	 */
	public List<Bulletin> getBulletinList() {
		return bulletinService.getAllBulletinList();
	}

	/**
	 * 删除指定的公告
	 * 
	 * @param bids
	 * @return
	 */
	public boolean deleteBulletins(String bids) {
		try {
			String[] ids = bids.split(",");
			for (String id : ids) {
				if (null != id && id.length() > 0) {
					bulletinService.remove(Bulletin.class, id);
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据ID查询出公告记录
	 * 
	 * @param id
	 *            公告ID
	 * @return
	 */
	public Bulletin findById(int id) {
		return (Bulletin) bulletinService.get(Bulletin.class, id);
	}
}
