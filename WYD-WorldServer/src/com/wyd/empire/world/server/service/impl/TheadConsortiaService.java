package com.wyd.empire.world.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.wyd.empire.world.consortia.MaxPrestigeVo;
import com.wyd.empire.world.server.service.base.IConsortiaService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class TheadConsortiaService implements Runnable {
	private IConsortiaService consortiaService = null;
	private MaxPrestigeVo maxPrestige = new MaxPrestigeVo();

	public TheadConsortiaService(IConsortiaService consortiaService) {
		this.consortiaService = consortiaService;
	}

	public MaxPrestigeVo getMaxPrestige() {
		return maxPrestige;
	}

	public void setMaxPrestige(MaxPrestigeVo maxPrestige) {
		this.maxPrestige = maxPrestige;
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName("ConsortiaService-Thread");
		t.start();
	}

	@Override
	public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		while (true) {
			try {
				consortiaService.updateCommunityRank();
				String time = sdf.format(new Date());
				if (0 == Integer.parseInt(time)) {
					consortiaService.updateEverydayAddToZero();
				}
				if (isMaxPrestige()) {
					setMaxPrestige(consortiaService.getMaxPrestige());
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(3600000L);
			} catch (InterruptedException ex) {
			}
		}
	}

	public IConsortiaService getConsortiaService() {
		return consortiaService;
	}

	public boolean isMaxPrestige() {
		Integer flag = ServiceManager.getManager().getVersionService().getSpecialMark().get("maxPrestige");
		if (flag == null) {
			return false;
		} else {
			return flag.intValue() == 1;
		}
	}

}
