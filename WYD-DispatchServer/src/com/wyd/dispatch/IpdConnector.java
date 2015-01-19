package com.wyd.dispatch;
import java.net.InetSocketAddress;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import com.wyd.empire.protocol.data.server.SendAddress;
import com.wyd.net.Connector;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.data.DataBeanFilter;
import com.wyd.protocol.s2s.S2SDecoder;
import com.wyd.protocol.s2s.S2SEncoder;
import com.wyd.protocol.utils.IpUtil;
public class IpdConnector extends Connector {
	private static final Logger log = Logger.getLogger(IpdConnector.class);
	private Configuration configuration = null;

	/**
	 * 初始化Connector
	 * 
	 * @param id
	 * @param address
	 * @param configuration
	 */
	public IpdConnector(String id, InetSocketAddress address, Configuration configuration) {
		super(id, address, false);
		this.configuration = configuration;
	}

	/**
	 * 初始化过滤器
	 */
	public void init() {
		this.connector.getFilterChain().addLast("wyd2codec", new ProtocolCodecFilter(new S2SEncoder(), new S2SDecoder()));
		this.connector.getFilterChain().addLast("wyd2databean", new DataBeanFilter());
	}

	protected AbstractData processLogin(AbstractData data) throws Exception {
		return null;
	}

	protected void sendSecureAuthMessage() {
	}

	/**
	 * 连接Dispatcher Server服务器<br>
	 * 发送游戏登录服务器 id，名称，地址<br>
	 * 发送在线人数，最大人数限制，服务器状态
	 */
	protected void connected() {
		log.info("dispatcher server connected");
		int id = this.configuration.getInt("id");
		String area = this.configuration.getString("area");
		String type = this.configuration.getString("servertype");
		String ip = this.configuration.getString("publicserver");
		int port = this.configuration.getInt("publicport");
		String group = this.configuration.getString("group");
		String machineCode = this.configuration.getString("machinecode");
		String address = ip + "," + port;
		if (type.equals("singlesocket")) {
			String serverId = IpUtil.toServerID(ip, (short) port);
			ip = this.configuration.getString("proxyaddress");
			if (ip == null) {
				ip = this.configuration.getString("proxyip");
			}
			Object obj = this.configuration.getProperty("proxyport");
			port = Integer.parseInt(obj.toString());
			String st = this.configuration.getString("serverMode", "");
			String sn = this.configuration.getString("serverNumber", "1");
			address = ip + "," + port + "." + serverId;
			if (st.toUpperCase().equals("L7")) {
				address = address + "," + sn;
			}
		}
		// 注册服务器
		SendAddress send = new SendAddress();
		send.setId(id);
		send.setArea(area);
		send.setAddress(address);
		send.setGroup(group);
		send.setServerId(Integer.parseInt(machineCode));
		send(send);

		// SyncLoad load = new SyncLoad();
		// load.setCurrOnline(0);
		// load.setMaxOnline(this.configuration.getInt("maxplayer"));
		// load.setMaintance(this.configuration.getBoolean("maintance", true));
		// send(load);

	}
}