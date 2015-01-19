package com.wyd.empire.world.server.handler.player;

import java.util.Date;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.player.PictureUploadDelete;
import com.wyd.empire.protocol.data.player.PictureUploadDeleteOk;
import com.wyd.empire.world.bean.PlayerPicture;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 删除头像信息
 * 
 * @author Administrator
 *
 */
public class PictureUploadDeleteHandler implements IDataHandler {
	Logger log = Logger.getLogger(PictureUploadSaveHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		PictureUploadDelete pictureUploadDelete = (PictureUploadDelete) data;
		try {
			PlayerPicture playerPicture = player.getPlayerPicture();
			/**
			 * --------------------------陈杰修改地方--------------------------------
			 * -------
			 */
			String passUrls = "," + playerPicture.getPictureUrlPass() + ",";
			String testUrls = "," + playerPicture.getPictureUrlTest() + ",";
			for (String deleteUrl : pictureUploadDelete.getDeletePicture()) {
				passUrls = passUrls.replace(deleteUrl + ",", "");// 删除审核过的图片
				for (String testUrl : playerPicture.getPictureUrlTest().split(",")) {
					// 存在删除图片为待审核图片或者与通过图片存在关联时,一并删除
					if (testUrl.indexOf(deleteUrl) != -1) {
						testUrls = testUrls.replace(testUrl + ",", "");
					}
				}
			}
			// 最终格式处理去除多余的","
			if (!passUrls.equals("") && !passUrls.equals(",")) {
				passUrls = passUrls.substring(1, passUrls.length() - 1);
			} else if (passUrls.equals(",")) {
				passUrls = "";
			}
			if (!testUrls.equals("") && !testUrls.equals(",")) {
				testUrls = testUrls.substring(1, testUrls.length() - 1);
			} else if (testUrls.equals(",")) {
				testUrls = "";
			}
			playerPicture.setPictureUrlPass(passUrls);
			playerPicture.setPictureUrlTest(testUrls);
			playerPicture.setUpdateTime(new Date());
			/**
			 * --------------------------陈杰修改地方结束------------------------------
			 * ---------
			 */
			ServiceManager.getManager().getRechargeService().update(playerPicture);
			PictureUploadDeleteOk pictureUploadDeleteOk = new PictureUploadDeleteOk(data.getSessionId(), data.getSerial());
			session.write(pictureUploadDeleteOk);
			player.setPlayerPicture(playerPicture);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
