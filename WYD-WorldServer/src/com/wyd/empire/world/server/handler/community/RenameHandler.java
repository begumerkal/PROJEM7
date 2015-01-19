package com.wyd.empire.world.server.handler.community;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.Rename;
import com.wyd.empire.protocol.data.community.RenameOk;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.KeywordsUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> IsAcceptMemberHandler</code>Protocol.COMMUNITY_Rename公会改名
 * 
 * @since JDK 1.6
 */
public class RenameHandler implements IDataHandler {
	private Logger log;

	public RenameHandler() {
		this.log = Logger.getLogger(RenameHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		Rename rename = (Rename) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			Consortia cos = (Consortia) ServiceManager.getManager().getConsortiaService().get(Consortia.class, player.getGuildId());
			PlayerSinConsortia psc = ServiceManager.getManager().getPlayerSinConsortiaService().findPlayerSinConsortia(player.getId());
			if (null == cos || psc == null || !cos.equals(psc.getConsortia())) {// 没有加入公会
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOJOIN_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			if (psc.getPosition() != 0) {// 不是会长
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOAUTHORITY_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			// 校验名称
			if (rename.getNewName().getBytes("gbk").length > 16 || rename.getNewName().trim().length() == 0) {
				// 将String编码由utf8转成gbk,因为utf8每个汉字所占字节数不定，gbk每个汉字占2个字节（更容易判断）
				throw new ProtocolException(ErrorMessages.COMMUNITY_CREATENAMELONG_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			// 校验是否同名
			if (ServiceManager.getManager().getConsortiaService().getConsortiaByName(rename.getNewName()) != null) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_CREATENAMESAME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			// 名称关键字校验
			if (KeywordsUtil.isInvalidName(rename.getNewName())) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_CHECKNAME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			if (!(ServiceUtils.checkString(rename.getNewName(), false))) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_CHECKNAME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			String newName = KeywordsUtil.filterKeywords(rename.getNewName());
			if (!(newName.equals(rename.getNewName()))) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_CHECKNAME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), Common.RENAMECOMMUNITYPENID);
			if (pifs == null || pifs.getPLastNum() < 1) {// 改名劵数量不足
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			// 改公会名称
			cos.setName(rename.getNewName());
			ServiceManager.getManager().getConsortiaService().update(cos);
			// 消耗改名劵
			pifs.setPLastNum(pifs.getPLastNum() - 1);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);
			RenameOk renameOk = new RenameOk(data.getSessionId(), data.getSerial());
			renameOk.setNewName(cos.getName());
			// 修改会员
			List<PlayerSinConsortia> pscList = ServiceManager.getManager().getPlayerSinConsortiaService()
					.getCommunityMemberList(cos.getId(), 1);
			for (PlayerSinConsortia p : pscList) {
				WorldPlayer wp = ServiceManager.getManager().getPlayerService().getLoadPlayer(p.getPlayer().getId());
				if (null != wp)
					wp.setGuild(cos);
			}
			// 更新玩家拥有的物品
			ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, pifs);
			session.write(renameOk);
			// 通知在线人成员，名字变了
			List<PlayerSinConsortia> memberList = ServiceManager.getManager().getPlayerSinConsortiaService()
					.getCommunityMembers(player.getGuildId(), 1);
			for (PlayerSinConsortia member : memberList) {
				WorldPlayer memberPlayer = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(member.getPlayer().getId());
				if (memberPlayer != null) {
					// 更新角色信息-名称
					Map<String, String> info = new HashMap<String, String>();
					info.put("guildName", rename.getNewName());
					ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, memberPlayer);
				}
			}

			ServiceManager.getManager().getPlayerItemsFromShopService()
					.saveGetItemRecord(player.getPlayer().getId(), Common.RENAMECOMMUNITYPENID, -1, -1, -3, 1, null);
			ServiceManager.getManager().getTaskService().useSomething(player, pifs.getShopItem().getId(), 1);
			ServiceManager.getManager().getTitleService().useSomething(player, pifs.getShopItem().getId());
		} catch (ProtocolException ex) {
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}