package com.wyd.empire.world.server.handler.community;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.CreateCommunity;
import com.wyd.empire.protocol.data.community.CreateCommunityOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.KeywordsUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.ChatService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> CreateCommunityHandler</code>Protocol.COMMUNITY
 * _CreateCommunityHandler创建公会协议处理
 * 
 * @since JDK 1.6
 */
public class CreateCommunityHandler implements IDataHandler {
	private Logger log;

	public CreateCommunityHandler() {
		this.log = Logger.getLogger(ExitCommunityHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		CreateCommunity createCommunity = (CreateCommunity) data;
		try {
			// 校验名称
			if (createCommunity.getCommunityName().getBytes("gbk").length > 16 || createCommunity.getCommunityName().trim().length() == 0) {
				// 将String编码由utf8转成gbk,因为utf8每个汉字所占字节数不定，gbk每个汉字占2个字节（更容易判断）
				throw new ProtocolException(ErrorMessages.COMMUNITY_CREATENAMELONG_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			// 校验是否同名
			if (ServiceManager.getManager().getConsortiaService().getConsortiaByName(createCommunity.getCommunityName()) != null) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_CREATENAMESAME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			// 名称关键字校验
			if (KeywordsUtil.isInvalidName(createCommunity.getCommunityName())) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_CHECKNAME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			if (!(ServiceUtils.checkString(createCommunity.getCommunityName(), false))) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_CHECKNAME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			String newName = KeywordsUtil.filterKeywords(createCommunity.getCommunityName());
			if (!(newName.equals(createCommunity.getCommunityName()))) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_CHECKNAME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			// 判断会长是否是别的公会会员
			if (player.getGuildId() > 0) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_ISMEMBER_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), Common.COMMUNITYBUILDID);
			if (pifs == null || pifs.getPLastNum() < 1) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_CREATETICKET_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			// 创建公会并获得新公会的id
			int communityId = ServiceManager.getManager().getConsortiaService()
					.createCommunity(createCommunity.getCommunityName(), player.getId());
			// 返回协议
			CreateCommunityOk createCommunityOk = new CreateCommunityOk(data.getSessionId(), data.getSerial());
			createCommunityOk.setCommunityId(communityId);
			createCommunityOk.setCommunityName(createCommunity.getCommunityName());

			// 更新玩家公会构造券的数量
			pifs.setPLastNum(pifs.getPLastNum() - 1);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);

			ServiceManager.getManager().getPlayerItemsFromShopService()
					.saveGetItemRecord(player.getPlayer().getId(), Common.COMMUNITYBUILDID, -1, -1, -2, 1, null);
			// 删除会长先前申请的公会
			ServiceManager.getManager().getPlayerSinConsortiaService().deletePlayerSinConsortiaByPlayerId(player.getId(), 0);
			// 清除玩家别的工会的贡献度记录
			ServiceManager.getManager().getPlayerSinConsortiaService().deleteConsortiaContribute(communityId, player.getId());
			// 公会任务相关
			ServiceManager.getManager().getTaskService().updateConsortiaTask(player, 0);
			// 成就
			ServiceManager.getManager().getTitleService().joinConsortiaTask(player);
			player.initialGuild();
			// 进入工会频道
			ServiceManager.getManager().getChatService()
					.syncChannels(player.getId(), new String[]{ChatService.CHAT_GUILD_CHANNEL + "_" + player.getGuildId()}, new String[]{});
			log.info("公会进出会记录：玩家Id-" + player.getId() + "-公会Id-" + communityId + "-操作-创建公会");
			session.write(createCommunityOk);
			// 更新玩家信息
			Map<String, String> info = new HashMap<String, String>();
			info.put("position", TipMessages.POSITION0);
			info.put("guildName", createCommunity.getCommunityName());
			ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, player);
			// 更新玩家拥有的物品
			ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, pifs);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_CREATE_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}