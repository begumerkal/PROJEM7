package com.wyd.channel.servlet;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.wyd.channel.bean.ChannelLoginHandle;
import com.wyd.channel.bean.ChannelLoginResult;
import com.wyd.channel.bean.ThirdConfig;
import com.wyd.channel.info.ChannelInfo;
import com.wyd.channel.info.ChannelInfo_360;
import com.wyd.channel.info.ChannelInfo_91;
import com.wyd.channel.info.ChannelInfo_UC;
import com.wyd.channel.result.LoginResult;
import com.wyd.channel.service.IThirdConfigService;
import com.wyd.channel.service.factory.ServiceManager;
import com.wyd.channel.service.impl.Access_360;
import com.wyd.channel.service.impl.Access_91;
import com.wyd.channel.service.impl.Access_UC;
import com.wyd.channel.utils.ChannelUtil;

public class ChannelService {
	private Logger log;
	private static ChannelService channelService = new ChannelService();

	public ChannelService() {
		log = Logger.getLogger(ChannelService.class);
	}

	public static ChannelService getChannelService() {
		return channelService;
	}

	public ChannelLoginHandle createLoginHandle(HttpServletRequest request, PrintWriter out) {
		ChannelLoginHandle handle = new ChannelLoginHandle(request);

		System.out.println(System.currentTimeMillis());
		ServiceManager.getManager().getHttpThreadPool().execute(new ChannelThread(handle, out));
		System.out.println(System.currentTimeMillis());
		return handle;
	}

	public class ChannelThread implements Runnable {
		private ChannelLoginHandle handle;
		private PrintWriter out;

		public ChannelThread(ChannelLoginHandle handle, PrintWriter out) {
			this.handle = handle;
			this.out = out;
		}

		public void run() {
			channelLogin(handle, out);
		}

		/**
		 * 
		 * 渠道登录验证
		 * 
		 * @param channel
		 *            渠道编号
		 * @param parameter
		 *            登录验证参数
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public void channelLogin(ChannelLoginHandle handle, PrintWriter out) {
			try {
				IThirdConfigService thirdConfigService = ServiceManager.getManager().getThirdConfigService();
				ThirdConfig config;
				String appKey = null, appId = null;

				HttpServletRequest request = handle.getRequest();
				String channelStr = request.getParameter("channelid");
				int channelid = StringUtils.hasText(channelStr) ? Integer.parseInt(channelStr) : 0;

				ChannelInfo channelInfo = null;
				Map<String, Object> parameters = request.getParameterMap();

				StringBuffer sb = new StringBuffer("渠道登录参数：");
				for (Entry<String, Object> element : parameters.entrySet()) {
					sb.append(element.getKey() + ":");
					sb.append(element.getValue() + ",");
				}
				log.info(sb.toString());

				switch (channelid) {
					case Access_91.CHANNEl_91_JJ :
						// client.setUin(channelInfo.getParameter()[0]);
					case Access_91.CHANNEl_91_IOS :
					case Access_91.CHANNEl_91_ANDROID :
						channelInfo = new ChannelInfo_91(ChannelUtil.APPID_91, ChannelUtil.APPKEY_91);
						break;
					case Access_UC.CHANNEl_UC :
						channelInfo = new ChannelInfo_UC(ChannelUtil.CPID_UC, ChannelUtil.GAMEID_UC, ChannelUtil.SERVICEID_UC,
								ChannelUtil.APIKEY_UC);
						break;
					case Access_360.CHANNEl_360 :
						channelInfo = new ChannelInfo_360(ChannelUtil.GRANT_TYPE_360, ChannelUtil.CLIENT_ID_360,
								ChannelUtil.CLIENT_SECRET_360, ChannelUtil.REDIRECT_URI_360);
						break;
				}
				
				if (channelInfo == null)
					return;

				channelInfo.setChannel(channelid);
				channelInfo.setRequest(request);
				LoginResult loginResult = com.wyd.channel.service.impl.ChannelService.channelLogin(channelInfo);

				ChannelLoginResult channelLoginResult = new ChannelLoginResult();
				channelLoginResult.setCode(loginResult.getCode() == null ? "" : loginResult.getCode());
				channelLoginResult.setMessage(loginResult.getMessage() == null ? "" : loginResult.getMessage());
				String thirdReturnMessage = loginResult.getThirdReturnMessage() == null ? "" : loginResult.getThirdReturnMessage();
				log.info("渠道号： " + channelid + " 第三方平台服务器返回的message: " + thirdReturnMessage + " 服务端返回的code: "
						+ channelLoginResult.getCode() + " 服务端返回的message：" + channelLoginResult.getMessage());
				// 处理完
				handle.setState(1);
				handle.setLoginResult(channelLoginResult);
				System.out.println(handle.toJSON());
				out.write(handle.toJSON());
				out.flush();
				out.close();

			} catch (Exception ex) {
				ex.printStackTrace();
				handle.setState(1);
				handle.setLoginResult(new ChannelLoginResult("-2", "发生异常：" + ex.getMessage()));
			}

		}
	}
}
