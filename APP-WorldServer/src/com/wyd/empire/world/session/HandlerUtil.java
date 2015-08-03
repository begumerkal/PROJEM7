package com.wyd.empire.world.session;

import org.apache.log4j.Logger;

import com.wyd.net.ProtocolFactory;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.HandlerMonitorService;
import com.wyd.session.Session;

/**
 * 处理世界服务器业务逻辑
 * 
 * @author sunzx
 *
 */
public class HandlerUtil {
	/** 总日志输出 */
	private static Logger log = Logger.getLogger(HandlerUtil.class);
	/** 记录Handler处理时间日志 */
	private static Logger timeLog = Logger.getLogger("handlerTimeLog");

	/**
	 * 处理世界服务器业务逻辑
	 * 
	 * @param session
	 *            会话信息
	 * @param abstractData
	 *            协议信息
	 * @param handler
	 *            业务逻辑处理器
	 */
	public static void doHandler(Session session, AbstractData dataobj) {
		try {
			IDataHandler handler = ProtocolFactory.getDataHandler(dataobj);
			if (handler == null) {
				session.handle(dataobj);
			} else {
				dataobj.setHandlerSource(session);
				long sTime = System.currentTimeMillis();
				System.out.println("handler:" + handler.getClass().getName() + ",dataobj:" + dataobj.getClass().getSimpleName() + ",sessionId:" + dataobj.getSessionId() + "<<<<<<<<");
				AbstractData sendData = handler.handle(dataobj);
				if (sendData != null) {
					session.write(sendData);
				}
				long time = System.currentTimeMillis() - sTime;
				if (time > 1000)
					timeLog.info(handler.getClass().getSimpleName() + "-----Time:" + time);
				// System.out.println(handler.getClass().getSimpleName() + "--"
				// + time);
			}
		} catch (ProtocolException e) {
			session.sendError(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("messageReceived-handle-error", e);
		}
	}
}
