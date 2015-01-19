package com.wyd.empire.world.server.handler.community;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.ApproveMember;
import com.wyd.empire.protocol.data.community.GetApprovingMemberList;
import com.wyd.empire.protocol.data.mail.SendMail;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.handler.mail.SendMailHandler;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.ChatService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> ApproveMemberHandler</code>Protocol.COMMUNITY _ApproveMember会员审批协议处理
 * 
 * @since JDK 1.6
 */
public class ApproveMemberHandler implements IDataHandler {
	private Logger log;

	public ApproveMemberHandler() {
		this.log = Logger.getLogger(ExitCommunityHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer worldPlayer = session.getPlayer(data.getSessionId());
		ApproveMember approveMember = (ApproveMember) data;
		try {
			PlayerSinConsortia psc = ServiceManager.getManager().getPlayerSinConsortiaService().findPlayerSinConsortia(worldPlayer.getId());
			if (psc == null || approveMember.getCommunityId() != psc.getConsortia().getId()) {
				throw new Exception("审批会员有问题，审批人权限不正确，工会ID：" + approveMember.getCommunityId() + ",审批人ID:" + worldPlayer.getId());// 如果传过来参数有问题，找不到公会，抛空指针异常
			}
			Consortia con = psc.getConsortia();
			if (con == null) {
				throw new Exception("如果传过来参数有问题，找不到公会。参数id：" + approveMember.getCommunityId());// 如果传过来参数有问题，找不到公会，抛空指针异常
			}
			if (approveMember.getIsApproved() && con.getIsAcceptMember()) {
				// 审批同意，把会员状态修改
				for (int id : approveMember.getPlayerId()) {
					WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(id);
					// 判断人数限制
					if (!(ServiceManager.getManager().getPlayerSinConsortiaService().checkCommunityNum(approveMember.getCommunityId()) < con
							.getTotalMember())) {
						ServiceManager.getManager().getPlayerSinConsortiaService()
								.deleteConsortiaMemberByPlayerId(approveMember.getCommunityId(), id, 0);
					} else {
						// 如果玩家没有加入公会
						if (null != player && player.getGuildId() == 0) {
							GameLogService.guildChange(player.getId(), player.getLevel(), con.getId(), con.getLevel(), 2,
									worldPlayer.getId(), worldPlayer.getLevel(), psc.getPosition());
							// 更新玩家状态
							ServiceManager.getManager().getPlayerSinConsortiaService().approveMember(id, approveMember.getCommunityId());
							// 删除玩家所有申请未审核记录
							ServiceManager.getManager().getPlayerSinConsortiaService().deletePlayerSinConsortiaByPlayerId(id, 0);
							// 公会任务
							ServiceManager.getManager().getTaskService().updateConsortiaTask(player, 1);
							// 成就
							ServiceManager.getManager().getTitleService().joinConsortiaTask(player);
							if (player != null) {
								player.setGuild(con);
								Map<String, String> info = new HashMap<String, String>();
								info.put("guildName", con.getName());
								PlayerSinConsortia playerConsortia = ServiceManager.getManager().getPlayerSinConsortiaService()
										.findPlayerSinConsortia(player.getId());
								info.put("position", playerConsortia.getPositionName());
								info.put("title", player.getPlayerTitle());
								ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, player);
							}
							// 清除玩家别的工会的贡献度记录
							ServiceManager.getManager().getPlayerSinConsortiaService()
									.deleteConsortiaContribute(approveMember.getCommunityId(), player.getId());
							// 发送邮件提醒
							SendMail sendMail = new SendMail(data.getSessionId(), data.getSerial());
							sendMail.setHandlerSource(data.getHandlerSource());
							sendMail.setSource(data.getSource());
							sendMail.setSendId(0);
							sendMail.setReceivedId(id);
							sendMail.setReveiverName("");
							sendMail.setTheme(TipMessages.COMMUNITY_COMMUNITYMAIL_MESSAGE);
							sendMail.setContent(TipMessages.COMMUNITY_PASS);
							sendMail.setMailType(1);
							SendMailHandler sendMailHandler = new SendMailHandler();
							sendMailHandler.handle(sendMail);
							// 进入工会频道
							ServiceManager
									.getManager()
									.getChatService()
									.syncChannels(player.getId(), new String[]{ChatService.CHAT_GUILD_CHANNEL + "_" + player.getGuildId()},
											new String[]{});
							// 加入日志
							log.info("公会进出会记录：玩家Id-" + player.getId() + "-公会Id-" + approveMember.getCommunityId() + "-操作-审批会员加入：审批人-"
									+ player.getId() + "-受审人-" + id);
						}
					}
				}
			} else {
				// 审批不同意，删除会员表里相关信息
				for (int id : approveMember.getPlayerId()) {
					ServiceManager.getManager().getPlayerSinConsortiaService()
							.deleteConsortiaMemberByPlayerId(approveMember.getCommunityId(), id, 0);
				}
			}
			// 返回成员列表
			GetApprovingMemberList approvingMemberList = new GetApprovingMemberList(data.getSessionId(), data.getSerial());
			approvingMemberList.setHandlerSource(approveMember.getHandlerSource());
			approvingMemberList.setSource(approveMember.getSource());
			approvingMemberList.setCommunityId(approveMember.getCommunityId());
			GetApprovingMemberListHandler approvingMemberListHandler = new GetApprovingMemberListHandler();
			approvingMemberListHandler.handle(approvingMemberList);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_APPROVE_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}