package com.wyd.empire.world.server.handler.wedding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.JoinWedding;
import com.wyd.empire.protocol.data.wedding.SendWedList;
import com.wyd.empire.world.bean.WeddingHall;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WeddingRoom;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.impl.MarryService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获得婚礼列表
 * 
 * @author Administrator
 */
public class GetWedListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetWedListHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			if (null != player) {
				WeddingHall wh = ServiceManager.getManager().getMarryService()
						.getWeddingHallByPlayerId(player.getPlayer().getSex(), player.getId());

				if (null == wh || wh.getStartTime().after(new Date())) {// 自己没有婚礼，则显示列表
					// 给婚礼排序
					ComparatorWeddingRoom comparatorWeddingRoom = new ComparatorWeddingRoom();

					Collections.sort(MarryService.weddingList, comparatorWeddingRoom);

					List<String> wedNum = new ArrayList<String>(); // 婚礼编号
					List<Integer> wedStatus = new ArrayList<Integer>(); // 婚礼状态
					List<Integer> type = new ArrayList<Integer>(); // 婚礼类型
					List<String> manName = new ArrayList<String>(); // 新郎名称
					List<String> womanName = new ArrayList<String>(); // 新娘名称
					List<String> startTime = new ArrayList<String>(); // 开始时间
					List<String> wedTime = new ArrayList<String>(); // 婚礼时间
					List<String> manIcon = new ArrayList<String>(); // 婚礼时间
					List<String> womanIcon = new ArrayList<String>(); // 婚礼时间
					boolean[] usePassword = new boolean[MarryService.weddingList.size()]; // 是否加密
					WorldPlayer wp;
					if (null != wh && wh.getStartTime().after(new Date())) {
						wedNum.add(wh.getManId() + "" + wh.getWomanId());
						type.add(wh.getWedtype());
						wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(wh.getManId());
						manName.add(wp.getName());
						manIcon.add(wp.getSuit_head());
						manIcon.add(wp.getSuit_face());
						wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(wh.getWomanId());
						womanName.add(wp.getName());
						womanIcon.add(wp.getSuit_head());
						womanIcon.add(wp.getSuit_face());
						startTime.add(DateUtil.format(wh.getStartTime(), "HH:mm"));
						wedStatus.add(0); // 还没开始
						wedTime.add(DateUtil.format(wh.getStartTime(), "HH:mm") + "-" + DateUtil.format(wh.getEndTime(), "HH:mm"));
					}
					int i = 0;
					for (WeddingRoom wr : MarryService.weddingList) {
						if (DateUtil.isSameDate(new Date(), wr.getWedHall().getStartTime())
								&& wr.getWedHall().getEndTime().after(new Date())) {
							wedNum.add(wr.getWedHall().getManId() + "" + wr.getWedHall().getWomanId());
							type.add(wr.getWedHall().getWedtype());
							wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(wr.getWedHall().getManId());
							manName.add(wp.getName());
							manIcon.add(wp.getSuit_head());
							manIcon.add(wp.getSuit_face());
							wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(wr.getWedHall().getWomanId());
							womanName.add(wp.getName());
							womanIcon.add(wp.getSuit_head());
							womanIcon.add(wp.getSuit_face());
							startTime.add(DateUtil.format(wr.getWedHall().getStartTime(), "HH:mm"));
							if (wr.getWedHall().getStartTime().after(new Date())) {
								wedStatus.add(0); // 还没开始
							} else if (wr.getWedHall().getStartTime().before(new Date()) && wr.getWedHall().getEndTime().after(new Date())) {
								wedStatus.add(1); // 进行中
							} else {
								wedStatus.add(2); // 结束
							}
							usePassword[i] = wr.getPassword() == "" ? false : true;
							wedTime.add(DateUtil.format(wr.getWedHall().getStartTime(), "HH:mm") + "-"
									+ DateUtil.format(wr.getWedHall().getEndTime(), "HH:mm"));
							i++;
						}
					}

					SendWedList sendWedList = new SendWedList(data.getSessionId(), data.getSerial());
					sendWedList.setManName(manName.toArray(new String[0]));
					sendWedList.setStartTime(startTime.toArray(new String[0]));
					sendWedList.setWedNum(wedNum.toArray(new String[0]));
					sendWedList.setWedStatus(ServiceUtils.getInts(wedStatus.toArray()));
					sendWedList.setWedType(ServiceUtils.getInts(type.toArray()));
					sendWedList.setWomanName(womanName.toArray(new String[0]));
					sendWedList.setWedTime(wedTime.toArray(new String[0]));
					sendWedList.setManIcon(manIcon.toArray(new String[0]));
					sendWedList.setWomanIcon(womanIcon.toArray(new String[0]));
					sendWedList.setUsePassword(usePassword);
					session.write(sendWedList);
				} else {// 如果自己有婚礼就进入自己的婚礼
					JoinWedding joinWedding = new JoinWedding(data.getSessionId(), data.getSerial());
					joinWedding.setHandlerSource(data.getHandlerSource());
					joinWedding.setSource(data.getSource());
					joinWedding.setWedNum(wh.getManId() + "" + wh.getWomanId());
					JoinWeddingHandler joinWeddingHandler = new JoinWeddingHandler();
					joinWeddingHandler.handle(joinWedding);
				}
			}

		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	class ComparatorWeddingRoom implements Comparator<WeddingRoom> {
		@Override
		public int compare(WeddingRoom wr1, WeddingRoom wr2) {
			if (wr1 == null)
				return 0;
			return (int) (wr1.getWedHall().getStartTime().getTime() - wr2.getWedHall().getStartTime().getTime());
		}
	}
}
