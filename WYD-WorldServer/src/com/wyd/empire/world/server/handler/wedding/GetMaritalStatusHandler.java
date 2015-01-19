package com.wyd.empire.world.server.handler.wedding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.GetMaritalStatusOK;
import com.wyd.empire.protocol.data.wedding.GetMarryMailList;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.WeddingHall;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class GetMaritalStatusHandler implements IDataHandler {

	Logger log = Logger.getLogger(GetMaritalStatusHandler.class);

	/**
	 * 获取玩家婚姻情况
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {

			List<MarryRecord> mrList = ServiceManager.getManager().getMarryService()
					.getMarryRecordByPlayerId(player.getPlayer().getSex(), player.getId(), 0);
			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Player p;
			String[] marryDiamondStr = ServiceManager.getManager().getVersionService().getVersion().getMarryDiamond().split(",");
			OperationConfig config = ServiceManager.getManager().getVersionService().getVersion();
			int[] marryDiamondInt = new int[marryDiamondStr.length];
			int[] removeDiamond = {ServiceManager.getManager().getVersionService().getVersion().getDhPrice(),
					ServiceManager.getManager().getVersionService().getVersion().getJhPrice()};
			int i = 0;
			String detail = TipMessages.MARRY_INFO.replace("~~", "\n");
			for (String str : marryDiamondStr) {
				marryDiamondInt[i] = Integer.parseInt(str);
				i++;
			}

			if (mrList.size() > 0) {
				int[] marryMailId = new int[mrList.size()]; // 求婚信的Id
				String[] marryMailInfo = new String[mrList.size()]; // 求婚信的简述
				String[] sendTime = new String[mrList.size()]; // 发送时间
				GetMarryMailList getMarryMailList = new GetMarryMailList(data.getSessionId(), data.getSerial());
				int index = 0;
				for (MarryRecord mr : mrList) {
					marryMailId[index] = mr.getId();
					if (player.getPlayer().getSex() == 0) {
						p = ServiceManager.getManager().getPlayerService().getPlayerById(mr.getWomanId());
					} else {
						p = ServiceManager.getManager().getPlayerService().getPlayerById(mr.getManId());
					}

					switch (mr.getUseItemId()) {
						case Common.MARRYITEMID1 :// 梦幻求婚--钻戒
							marryMailInfo[index] = TipMessages.MARRY_THEME.replace("***", p.getName()).replace("##", TipMessages.DREAM);
							break;
						case Common.MARRYITEMID2 :// 浪漫求婚--易拉环
							marryMailInfo[index] = TipMessages.MARRY_THEME.replace("***", p.getName()).replace("##", TipMessages.ROMANTIC);
							break;
						case Common.MARRYITEMID3 :// 温馨求婚--玫瑰花束
							marryMailInfo[index] = TipMessages.MARRY_THEME.replace("***", p.getName()).replace("##", TipMessages.WARM);
							break;
						case Common.MARRYITEMID4 :// 朴实求婚--银行卡
							marryMailInfo[index] = TipMessages.MARRY_THEME.replace("***", p.getName()).replace("##", TipMessages.GUILELESS);
							break;
					}
					sendTime[index] = sdf.format(mr.getCreateTime());
					index++;
				}
				getMarryMailList.setMarryMailId(marryMailId);
				getMarryMailList.setMarryMailInfo(marryMailInfo);
				getMarryMailList.setSendTime(sendTime);
				getMarryMailList.setDetail(detail);

				session.write(getMarryMailList);
			} else {
				MarryRecord mr = ServiceManager.getManager().getMarryService()
						.getSingleMarryRecordByPlayerId(player.getPlayer().getSex(), player.getId(), 1);
				GetMaritalStatusOK getMaritalStatusOK = new GetMaritalStatusOK(data.getSessionId(), data.getSerial());

				if (null != mr) {
					if (player.getId() == mr.getManId()) {
						p = ServiceManager.getManager().getPlayerService().getPlayerById(mr.getWomanId());
					} else {
						p = ServiceManager.getManager().getPlayerService().getPlayerById(mr.getManId());
					}
					getMaritalStatusOK.setCoupleId(p.getId());
					getMaritalStatusOK.setCoupleName(p.getName());
					getMaritalStatusOK.setMarryStatus(mr.getStatusMode());
					getMaritalStatusOK.setWeddingType(mr.getType());
					getMaritalStatusOK.setRemoveDiamond(removeDiamond);
					getMaritalStatusOK.setMarryDiamond(marryDiamondInt);
					getMaritalStatusOK.setDetail(detail);

					WeddingHall wedHall = ServiceManager.getManager().getMarryService()
							.getWeddingHallByPlayerId(player.getPlayer().getSex(), player.getId());
					if (null == wedHall) {
						getMaritalStatusOK.setWedTime(-1);
					} else {
						if (wedHall.getStartTime().after(new Date())) {
							int wedTime = (int) DateUtil.compareDateOnSecond(wedHall.getStartTime(), new Date());
							getMaritalStatusOK.setWedTime(wedTime);
						} else if (wedHall.getStartTime().before(new Date()) && wedHall.getEndTime().after(new Date())) {
							getMaritalStatusOK.setWedTime(0);
						} else {
							getMaritalStatusOK.setWedTime(-1);
						}
					}
				} else {
					getMaritalStatusOK.setCoupleId(0);
					getMaritalStatusOK.setCoupleName("");
					getMaritalStatusOK.setMarryStatus(0);
					getMaritalStatusOK.setWeddingType(0);
					getMaritalStatusOK.setRemoveDiamond(removeDiamond);
					getMaritalStatusOK.setMarryDiamond(marryDiamondInt);
					getMaritalStatusOK.setDetail(detail);
					getMaritalStatusOK.setWedTime(-1);
				}
				// 增加结婚权利，暂订15级才可以发钻石
				// by zengxc 2013-9-12
				getMaritalStatusOK.setRightToMarry(config.getRightToMarryLevel());
				// 结婚可赠送钻石书
				Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
				if (map.get("openWedGive") == null || map.get("openWedGive") == 0) {
					getMaritalStatusOK.setCanGiveDiamond(-1);
				} else {
					if (map.get("openWedGive") == 2
							&& ServiceManager.getManager().getVersionService().getVersion().getGiveDiamondRatio() != 0) {
						getMaritalStatusOK.setCanGiveDiamond(player.getCanGiveDiamondMode2());
					} else {
						getMaritalStatusOK.setCanGiveDiamond(player.getCanGiveDiamondMode1());
					}
				}
				session.write(getMaritalStatusOK);
			}

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
