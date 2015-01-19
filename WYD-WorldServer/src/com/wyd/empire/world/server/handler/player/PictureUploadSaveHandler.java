package com.wyd.empire.world.server.handler.player;

import java.util.Date;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.player.PictureUploadSave;
import com.wyd.empire.protocol.data.player.PictureUploadSaveOk;
import com.wyd.empire.world.bean.PlayerPicture;
import com.wyd.empire.world.common.util.KeywordsUtil;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 更新保存玩家头像信息
 * 
 * @author Administrator
 */
public class PictureUploadSaveHandler implements IDataHandler {
	Logger log = Logger.getLogger(PictureUploadSaveHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		PictureUploadSave pictureUploadSave = (PictureUploadSave) data;
		try {
			if (KeywordsUtil.isInvalidName(pictureUploadSave.getPersonContext())) {
				throw new ProtocolException(ErrorMessages.SIGNATURE_ILLEGAT, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			PlayerPicture playerPicture = player.getPlayerPicture();
			// 年龄
			playerPicture.setAge(pictureUploadSave.getAge());
			// 星座
			playerPicture.setConstellation(pictureUploadSave.getConstellation());
			// 个性签名
			if (pictureUploadSave.getPersonContext().length() > 200) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			playerPicture.setPersonContext(pictureUploadSave.getPersonContext());
			/**
			 * --------------------------陈杰修改地方--------------------------------
			 * -------
			 */
			// 不是每次都需要进行头像保存操作,当更改了头像才进行保存处理
			if (pictureUploadSave.getPictureUrlTest().length > 0) {
				String urls = playerPicture.getPictureUrlTest().equals("") ? "," : "," + playerPicture.getPictureUrlTest() + ",";
				for (String testUrl : pictureUploadSave.getPictureUrlTest()) {
					if (testUrl.indexOf("<") != -1) {
						throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
								data.getType(), data.getSubType());
					}
					// 头像路径 格式为:新替换地址#被替换原地址 (没有被替换地址时为-1)
					// 如:192.168.1.6#-1或192.168.1.6#192.168.1.8
					String[] urlArry = testUrl.split("#");
					if (urlArry.length != 2) {
						throw new ProtocolException(ErrorMessages.UPLOAD_FAILED, data.getSerial(), data.getSessionId(), data.getType(),
								data.getSubType());
					}
					// 如果被替换的地址是待审核的,那么直接将原替换地址变为最新的替换地址
					if (!urlArry[1].equals("-1") && urls.indexOf(urlArry[1]) != -1) {
						urls = urls.replace(urlArry[1], urlArry[0]);
					} else {
						urls = urls + testUrl + ",";
					}
				}
				// 最终格式处理去除多余的","
				if (!urls.equals("") && !urls.equals(",")) {
					urls = urls.substring(1, urls.length() - 1);
				} else if (urls.equals(",")) {
					urls = "";
				}
				playerPicture.setPictureUrlTest(urls);// 保存最终处理过的路径信息
			}
			/**
			 * --------------------------陈杰修改地方结束------------------------------
			 * ---------
			 */
			playerPicture.setUpdateTime(new Date());
			ServiceManager.getManager().getRechargeService().update(playerPicture);
			PictureUploadSaveOk pictureUploadSaveOk = new PictureUploadSaveOk(data.getSessionId(), data.getSerial());
			session.write(pictureUploadSaveOk);
			player.setPlayerPicture(playerPicture);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
