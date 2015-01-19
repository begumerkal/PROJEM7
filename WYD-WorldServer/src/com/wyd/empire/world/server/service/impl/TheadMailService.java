package com.wyd.empire.world.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.world.bean.RankRecord;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.StrValueUtils;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.server.service.base.IMailService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class TheadMailService implements Runnable {
	private IMailService mailService = null;
	Logger log = Logger.getLogger(TheadMailService.class);

	public TheadMailService(IMailService mailService) {
		this.mailService = mailService;
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName("MailService-Thread");
		t.start();
	}

	@Override
	public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		// SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while (true) {
			try {
				String time = sdf.format(new Date());
				if (0 == Integer.parseInt(time)) {
					// 删除过期邮件
					mailService.deleteOverDateMail(30);
					// 删除过期系统邮件
					mailService.deleteOverDateMail(7);
					// //清除每日奖励数据
					// log.info(sdf1.format(new
					// Date())+" 每日登陆奖励的领取人数："+ServiceManager.getManager().getTaskService().getService().getRecordNum());
					ServiceManager.getManager().getTaskService().getService().deleteAllRecord();
					Common.WISHNUM = 0;
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			try {
				TipMessages.SIGN_INFO = StrValueUtils.getInstance().getValueByKey("SIGN_INFO");
				// 获得当前的号数
				Calendar cal = Calendar.getInstance();
				if (cal.get(Calendar.DAY_OF_WEEK) - 1 == 1) {
					List<RankRecord> list = ServiceManager.getManager().getRankRecordService().getRankRecordList(1);
					for (RankRecord rr : list) {
						ServiceManager
								.getManager()
								.getChatService()
								.sendBulletinToWorld(TipMessages.HONOR_NOTICE.replace("XX", rr.getPlayer().getName()),
										rr.getPlayer().getName(), false);
					}
				}
				Thread.sleep(3600000L);
			} catch (InterruptedException ex) {
			}
		}
	}

	public IMailService getMailService() {
		return mailService;
	}
}
