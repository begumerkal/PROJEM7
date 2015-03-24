package com.wyd.server.handler.server;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.server.SendAddress;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.server.dispatcher.DispatchSession;
import com.wyd.server.service.ServiceManager;

/**
 * 类 <code>SendAddressHandler</code>处理登录服务器信息相关Handler
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @since JDK 1.6
 */
public class SendAddressHandler implements IDataHandler {
	Logger log;

	public SendAddressHandler() {
		this.log = Logger.getLogger(SendAddressHandler.class);
	}

	public AbstractData handle(AbstractData data) throws Exception {
		SendAddress address = (SendAddress) data;
		DispatchSession session = (DispatchSession) data.getHandlerSource();
		System.out.println(address.getArea() + " " + address.getGroup() + " " + address.getServerId());

		if (ServiceManager.getManager().getConfigService().exisMachine(address.getArea(), address.getGroup(), address.getServerId())) {
			session.setId(address.getId());
			session.setArea(address.getArea());
			session.setGroup(address.getGroup());
			session.setServerId(address.getServerId());
			session.setAddress(address.getAddress());

			System.out.println("地区:" + address.getArea() + "组:" + address.getGroup() + "线:" + address.getId() + " 服务器ID:"
					+ address.getServerId());
			ServiceManager.getManager().getLineService().addSendAddress(address);
			this.log.info("Server [" + address.getArea() + "] [" + address.getGroup() + "] connected");
		} else {
			this.log.info("Server [" + address.getArea() + "] [" + address.getGroup() + "] connect fail");
			session.close();
		}
		return null;
	}
}
