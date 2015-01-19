package com.wyd.empire.world.server.handler.player;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.player.GetTopRecord;
import com.wyd.empire.protocol.data.player.GetTopRecordOk;
import com.wyd.empire.world.bean.PlayerStaWeek;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.Record;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取排行榜
 * 
 * @author Administrator
 */
public class GetTopRecordHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetTopRecordHandler.class);
	public static final int MAX_COUNT = 30;

	// 获取排行榜
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetTopRecord getTopRecord = (GetTopRecord) data;
		List<PlayerStaWeek> pswList = null;// 数据集合
		List<Record> recordList = null;
		String orderBy = null;
		GameLogService.ranking(player.getId(), player.getLevel(), getTopRecord.getOperate());
		try {
			switch (getTopRecord.getOperate()) {
				case 1 :
					orderBy = "levelRank";
					pswList = ServiceManager.getManager().getLogSerivce().getWeekLevel();
					break;
				case 2 :
					orderBy = "winNumRank";
					pswList = ServiceManager.getManager().getLogSerivce().getWeekWin();
					break;
				case 3 :
					orderBy = "goldRank";
					pswList = ServiceManager.getManager().getLogSerivce().getWeekGold();
					break;
				case 4 :
					orderBy = "ticketRank";
					pswList = ServiceManager.getManager().getLogSerivce().getWeekTick();
					break;
				case 5 :
					orderBy = "levelRank";
					pswList = ServiceManager.getManager().getLogSerivce().getMonthLevel();
					break;
				case 6 :
					orderBy = "winNumRank";
					pswList = ServiceManager.getManager().getLogSerivce().getMonthWin();
					break;
				case 7 :
					orderBy = "goldRank";
					pswList = ServiceManager.getManager().getLogSerivce().getMonthGold();
					break;
				case 8 :
					orderBy = "ticketRank";
					pswList = ServiceManager.getManager().getLogSerivce().getMonthTick();
					break;
				case 9 :
					recordList = ServiceManager.getManager().getLogSerivce().getNowLevel();
					break;
				case 10 :
					recordList = ServiceManager.getManager().getLogSerivce().getNowWin();
					break;
				case 11 :
					recordList = ServiceManager.getManager().getLogSerivce().getNowGold();
					break;
				case 12 :
					recordList = ServiceManager.getManager().getLogSerivce().getNowTick();
					break;
				case 13 :
					recordList = ServiceManager.getManager().getLogSerivce().getNowFight();
					break;
			}
			if (getTopRecord.getOperate() < 9 && (null == pswList || pswList.size() < 1)) {
				// 暂无排行数据
				throw new ProtocolException(ErrorMessages.PLAYER_GTR_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 发送给客户端的数据
			List<Integer> ranking = new ArrayList<Integer>();// 排名
			List<String> name = new ArrayList<String>();// 玩家名字
			List<Integer> amount = new ArrayList<Integer>();// 数字，，比如战斗力11112
			List<Integer> playerId = new ArrayList<Integer>();// 玩家ID
			List<Integer> trendRank = new ArrayList<Integer>();// 趋势
			if (getTopRecord.getOperate() < 9) {
				PlayerStaWeek psw;
				// 显示本人的排行
				for (int i = 0; i < pswList.size(); i++) {
					psw = pswList.get(i);
					if (psw.getPlayerId().intValue() == player.getId()) {
						ranking.add(i + 1);
						name.add(player.getName());
						amount.add(Integer.parseInt(PropertyUtils.getProperty(psw, orderBy).toString()));
						playerId.add(player.getId());
						break;
					}
				}
				WorldPlayer wp;
				// 显示的是玩家的排行
				for (int i = 0; i < pswList.size() && i < MAX_COUNT; i++) {
					psw = pswList.get(i);
					wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(psw.getPlayerId());
					if (null != wp) {
						ranking.add(i + 1);
						name.add(wp.getName());
						amount.add(Integer.parseInt(PropertyUtils.getProperty(psw, orderBy).toString()));
						playerId.add(psw.getPlayerId());
					}
				}
				// getTopRecord.getOperate() >9---mark就是旧的名次和新的名次的差值
			} else {
				for (int i = 0; i < recordList.size(); i++) {
					Record record1 = recordList.get(i);
					if (player.getId() == (record1.getId())) {
						ranking.add(i + 1);
						name.add(record1.getName());
						amount.add(record1.getData());
						playerId.add(player.getId());
						if (getTopRecord.getOperate() == 9) {
							if (Common.oldLevelMap.get(record1.getName()) == null) {
								trendRank.add(0);
							} else {
								int mark = i + 1 - Common.oldLevelMap.get(record1.getName());
								if (mark < 0) {
									trendRank.add(0);
								} else if (mark == 0) {
									trendRank.add(1);
								} else {
									trendRank.add(2);
								}
							}
						}
						if (getTopRecord.getOperate() == 10) {
							if (Common.oldWinMap.get(record1.getName()) == null) {
								trendRank.add(0);
							} else {
								int mark = i + 1 - Common.oldWinMap.get(record1.getName());
								if (mark < 0) {
									trendRank.add(0);
								} else if (mark == 0) {
									trendRank.add(1);
								} else {
									trendRank.add(2);
								}
							}
						}
						if (getTopRecord.getOperate() == 13) {
							if (Common.oldFightMap.get(record1.getName()) == null) {
								trendRank.add(0);
							} else {
								int mark = i + 1 - Common.oldFightMap.get(record1.getName());
								if (mark < 0) {
									trendRank.add(0);
								} else if (mark == 0) {
									trendRank.add(1);
								} else {
									trendRank.add(2);
								}
							}
						}
						break;
					}
				}
				Record record;
				for (int i = 0; i < recordList.size() && i < MAX_COUNT; i++) {
					record = recordList.get(i);
					if (getTopRecord.getOperate() == 9) {
						if (Common.oldLevelMap.get(record.getName()) == null) {
							trendRank.add(0);
						} else {
							int mark = i + 1 - Common.oldLevelMap.get(record.getName());
							// System.out.println(mark);
							if (mark < 0) {
								trendRank.add(0);
							} else if (mark == 0) {
								trendRank.add(1);
							} else {
								trendRank.add(2);
							}
						}
					} else if (getTopRecord.getOperate() == 10) {
						if (Common.oldWinMap.get(record.getName()) == null) {
							trendRank.add(0);
						} else {
							int mark = i + 1 - Common.oldWinMap.get(record.getName());
							if (mark < 0) {
								trendRank.add(0);
							} else if (mark == 0) {
								trendRank.add(1);
							} else {
								trendRank.add(2);
							}
						}
					} else if (getTopRecord.getOperate() == 13) {
						if (Common.oldFightMap.get(record.getName()) == null) {
							trendRank.add(0);
						} else {
							int mark = i + 1 - Common.oldFightMap.get(record.getName());
							if (mark < 0) {
								trendRank.add(0);
							} else if (mark == 0) {
								trendRank.add(1);
							} else {
								trendRank.add(2);
							}
						}
					}
					ranking.add(i + 1);
					name.add(record.getName());
					amount.add(record.getData());
					playerId.add(record.getId());
				}
			}
			GetTopRecordOk getTopRecordOk = new GetTopRecordOk(data.getSessionId(), data.getSerial());
			getTopRecordOk.setRanking(ServiceUtils.getInts(ranking.toArray()));
			getTopRecordOk.setName(name.toArray(new String[0]));
			getTopRecordOk.setAmount(ServiceUtils.getInts(amount.toArray()));
			getTopRecordOk.setPlayerId(ServiceUtils.getInts(playerId.toArray()));
			getTopRecordOk.setTrendRank(ServiceUtils.getInts(trendRank.toArray()));
			session.write(getTopRecordOk);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.PLAYER_GTR_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
