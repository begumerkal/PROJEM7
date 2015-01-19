package com.wyd.empire.world.server.handler.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.player.SendOnlinePlayer;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;
import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 类 <code> GetOnlinePlayerHandler</code>
 * Protocol.PLAYER_GetOnlinePlayerHandler获得在线玩家协议处理
 * 
 * @since JDK 1.6
 */
public class GetOnlinePlayerHandler implements IDataHandler {
	private Logger log;
	private static Comparator<WorldPlayer> playerComparator = new PlayerComparator();

	public GetOnlinePlayerHandler() {
		this.log = Logger.getLogger(GetOnlinePlayerHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer wp = session.getPlayer(data.getSessionId());
		try {
			GameLogService.getOnlinePlayer(wp.getId(), wp.getLevel());
			int pageNum = 1;
			int pageSize = 50;// 页面单页条数
			int pageCount = Common.PAGESIZE;// 返回给客户端的条数
			int endPagecount = 0;
			int stratPagecount = 0;
			List<WorldPlayer> onlist = new ArrayList<WorldPlayer>();
			Collection<WorldPlayer> online = ServiceManager.getManager().getPlayerService().getOnlinePlayer();
			if (online.size() < Common.ONLINENUM) {
				for (WorldPlayer player : online) {
					onlist.add(player);
				}
				List<WorldPlayer> hofflist = ServiceManager.getManager().getRobotService().getOfflinePlayer();
				int index = Common.ONLINENUM - onlist.size();
				int i = 0;
				for (WorldPlayer player : hofflist) {
					if (i < index) {
						onlist.add(player);
					} else {
						break;
					}
					i++;
				}
			} else {
				int i = 0;
				for (WorldPlayer player : online) {
					if (i < Common.ONLINENUM) {
						onlist.add(player);
					} else {
						break;
					}
					i++;
				}
			}
			Collections.sort(onlist, playerComparator);
			int totalSize = (int) Math.ceil(onlist.size() / (double) pageSize);
			stratPagecount = (pageNum - 1) * pageSize;
			endPagecount = pageNum * pageSize;
			endPagecount = endPagecount > onlist.size() ? onlist.size() : endPagecount;
			pageCount = endPagecount - stratPagecount;
			pageCount = pageCount > 0 ? pageCount : 0;
			int[] playerId = new int[pageCount];
			int[] zsleve = new int[pageCount];
			String[] playerName = new String[pageCount];
			int[] level = new int[pageCount];
			boolean[] sex = new boolean[pageCount];
			boolean[] ol = new boolean[pageCount];
			WorldPlayer worldPlayer;
			int index = 0;
			for (int i = stratPagecount; i < endPagecount; i++) {
				worldPlayer = onlist.get(i);
				ol[index] = true;
				playerId[index] = worldPlayer.getId();
				zsleve[index] = worldPlayer.getPlayer().getZsLevel();
				playerName[index] = worldPlayer.getName();
				level[index] = worldPlayer.getLevel();
				sex[index] = worldPlayer.getPlayer().getSex() == 0 ? false : true;
				index++;
			}
			onlist = null;
			SendOnlinePlayer sendOnlinePlayer = new SendOnlinePlayer(data.getSessionId(), data.getSerial());
			sendOnlinePlayer.setPlayerId(playerId);
			sendOnlinePlayer.setPlayerName(playerName);
			sendOnlinePlayer.setLevel(level);
			sendOnlinePlayer.setSex(sex);
			sendOnlinePlayer.setOnline(ol);
			sendOnlinePlayer.setPageNumber(pageNum);
			sendOnlinePlayer.setTotalPage(totalSize);
			sendOnlinePlayer.setZsleve(zsleve);
			session.write(sendOnlinePlayer);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.PLAYER_GETONLINEPLAYER_MESSAGE, data.getSerial(), data.getSessionId(),
					data.getType(), data.getSubType());
		}
	}

	static class PlayerComparator implements Comparator<WorldPlayer> {
		public int compare(WorldPlayer player1, WorldPlayer player2) {
			int f = player2.getLevel() - player1.getLevel();
			if (f < 0)
				f = -1;
			if (f > 0)
				f = 1;
			return f;
		}
	}
}