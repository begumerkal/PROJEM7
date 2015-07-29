package com.wyd.channel.service.impl;
import com.wyd.channel.info.ChannelInfo;
import com.wyd.channel.info.ChannelInfo_360;
import com.wyd.channel.info.ChannelInfo_91;
import com.wyd.channel.info.ChannelInfo_UC;
import com.wyd.channel.result.LoginResult;
import com.wyd.channel.utils.Common;

/**
 * @author zengxc
 * @version 创建时间：2014-3-13
 */
public class ChannelService {

	/**
	 * 渠道登录验证
	 * 
	 * @param channelInfo
	 *            渠道信息<br/>
	 *            具体说明如下：<br/>
	 *            91渠道需要设置appId,appKey,parameter三个值<br/>
	 * @return 验证结果
	 */
	public static LoginResult channelLogin(ChannelInfo channelInfo) {
		int channel = channelInfo.getChannel();
		LoginResult channelLoginResult = new LoginResult();
		switch (channel) {
			case Access_91.CHANNEl_91_ANDROID :
				Access_91 access_91 = new Access_91();
				channelLoginResult = access_91.getUserLoginResult((ChannelInfo_91) channelInfo);
				break;
			case Access_UC.CHANNEl_UC :
				Access_UC access_uc = new Access_UC();
				channelLoginResult = access_uc.getUserLoginResult((ChannelInfo_UC) channelInfo);
				break;
			case Access_360.CHANNEl_360 :
				Access_360 access_360 = new Access_360();
				channelLoginResult = access_360.getUserLoginResult((ChannelInfo_360) channelInfo);
				break;
			default :
				// 客户端预留了一些需要服务端验证的渠道，但目前还不需要验证，默认返回验证成功
				channelLoginResult.setCode(Common.STATUS_SUCCESS);
				channelLoginResult.setMessage(Common.STATUS_SUCCESS);
				break;
		}
		return channelLoginResult;
	}
}
