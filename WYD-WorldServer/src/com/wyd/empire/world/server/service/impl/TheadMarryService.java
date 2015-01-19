package com.wyd.empire.world.server.service.impl;

import org.apache.log4j.Logger;

import com.wyd.empire.world.server.service.base.IMarryService;

public class TheadMarryService implements Runnable {
	private IMarryService marryService = null;

	Logger log = Logger.getLogger(TheadMarryService.class);

	public TheadMarryService(IMarryService marryService) {
		this.marryService = marryService;
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName("MarryService-Thread");
		t.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				marryService.deleteMarryRecordByCreateTime();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(3600000L);
			} catch (InterruptedException ex) {
			}
		}
	}

	public IMarryService getMarryService() {
		return marryService;
	}
}
