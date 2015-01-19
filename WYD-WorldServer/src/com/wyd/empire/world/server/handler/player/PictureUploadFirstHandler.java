package com.wyd.empire.world.server.handler.player;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.player.PictureUploadFirst;
import com.wyd.empire.protocol.data.player.PictureUploadOk;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerPicture;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.Record;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取玩家的头像信息
 * 
 * @author Administrator
 */
public class PictureUploadFirstHandler implements IDataHandler {
	private Logger log;

	public PictureUploadFirstHandler() {
		this.log = Logger.getLogger(PictureUploadFirstHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		PictureUploadFirst pictureUploadFirst = (PictureUploadFirst) data;
		int playerId = pictureUploadFirst.getPlayerId();

		PlayerPicture playerPicture = null;
		try {
			WorldPlayer playerOther = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
			PictureUploadOk pictureUploadFirstOk = new PictureUploadOk(data.getSessionId(), data.getSerial());
			if (null == player || null == playerOther) {
				return;
			}
			/**
			 * --------------------------陈杰修改地方--------------------------------
			 * -------
			 */
			playerPicture = playerOther.getPlayerPicture();
			// 如果是自己查看,在未审核之前,自己可以查看自己上传待审核的头像
			if (player.getId() == playerOther.getId()) {
				String[] testArry = playerPicture.getPictureUrlTest().equals("") ? new String[0] : playerPicture.getPictureUrlTest().split(
						",");
				StringBuffer testUrl = new StringBuffer("");
				// 格式调整非空状态下前后加上","
				String passUrl = "," + playerPicture.getPictureUrlPass() + ",";
				// 处理对应的待审核图片替换已经更改的图片显示
				int index = 0;
				for (String urls : testArry) {
					// 格式： 待审核替换地址 # 待被替换图片地址 192.168.1.2#192.168.1.8
					String[] url = urls.split("#");
					if (index > 0) {
						testUrl.append(",");
					}
					testUrl.append(url[0]);
					if (url.length != 2) { // 容错处理
						passUrl = passUrl.replaceAll(url[1] + ",", "");
					}
					index++;
				}
				// 最终格式处理去除多余的","
				if (!passUrl.equals("") && !passUrl.equals(",")) {
					passUrl = passUrl.substring(1, passUrl.length() - 1);
				} else if (passUrl.equals(",")) {
					passUrl = "";
				}
				pictureUploadFirstOk.setPictureUrl(passUrl.equals("") ? new String[0] : passUrl.split(","));
				pictureUploadFirstOk.setPictureUrlTest(testUrl.toString().equals("") ? new String[0] : testUrl.toString().split(","));
			} else {
				pictureUploadFirstOk.setPictureUrl(playerPicture.getPictureUrlPass().split(","));
				pictureUploadFirstOk.setPictureUrlTest(new String[0]);// 非玩家本身不显示未审核的图片
			}
			/**
			 * --------------------------陈杰修改地方结束------------------------------
			 * ---------
			 */
			pictureUploadFirstOk.setAge(playerPicture.getAge());// 年龄
			pictureUploadFirstOk.setPersonContext(playerPicture.getSignatureContent());// 个性签名
			String headMessage, faceMessage;// 默认头像
			headMessage = playerOther.getSuit_head();
			faceMessage = playerOther.getSuit_face();
			pictureUploadFirstOk.setHeadMessage(headMessage);
			pictureUploadFirstOk.setFaceMessage(faceMessage);
			pictureUploadFirstOk.setCostellation(playerPicture.getConstellation());
			Player p;
			MarryRecord mr = ServiceManager.getManager().getMarryService()
					.getSingleMarryRecordByPlayerId(playerOther.getPlayer().getSex(), playerId, 1);
			if (null != mr) {
				if (playerId == mr.getManId()) {
					p = ServiceManager.getManager().getPlayerService().getPlayerById(mr.getWomanId());
				} else {
					p = ServiceManager.getManager().getPlayerService().getPlayerById(mr.getManId());
				}
				if (null != p) {
					pictureUploadFirstOk.setPartner(p.getName());
				} else {
					pictureUploadFirstOk.setPartner("0");
				}
			} else {
				pictureUploadFirstOk.setPartner("0");
			}
			player.setPlayerPicture(playerPicture);
			// System.out.println("PictureUploadFirstHandler:"+playerPicture.getPictureUrl());
			pictureUploadFirstOk.setVipMark(playerOther.isVip());
			pictureUploadFirstOk.setVipLevel(playerOther.getPlayer().getVipLevel());
			pictureUploadFirstOk.setFighting(playerOther.getFighting());
			List<Record> recordList = ServiceManager.getManager().getLogSerivce().getNowFight();
			int fightingRank = 0;
			for (int i = 0; i < recordList.size(); i++) {
				if (playerOther.getName().equals(recordList.get(i).getName())) {
					fightingRank = i + 1;
					break;
				}
			}
			pictureUploadFirstOk.setFightingRank(fightingRank);
			session.write(pictureUploadFirstOk);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
