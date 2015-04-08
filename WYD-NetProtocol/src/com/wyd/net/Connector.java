package com.wyd.net;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
public abstract class Connector implements IConnector {
	protected static final Logger log = Logger.getLogger(Connector.class);
	protected InetSocketAddress address;
	protected String userName = "";
	protected String password = "";
	protected boolean needRetry = true;
	protected NioSocketConnector connector;
	protected SocketSessionConfig config;
	protected int receiveBufferSize = 65534;
	protected int sendBufferSize = 65534;
	protected IoSession session;
	protected String id;

	public Connector(String id, InetSocketAddress address) {
		this.id = id;
		this.address = address;
		initConnector();
	}

	public String getId() {
		return this.id;
	}

	/**
	 * 初始化连接器各基本参数
	 */
	private void initConnector() {
		this.connector = new NioSocketConnector(Runtime.getRuntime().availableProcessors() + 1);
		this.config = connector.getSessionConfig();
		this.config.setTcpNoDelay(true);
		this.config.setSendBufferSize(this.sendBufferSize);
		this.config.setReceiveBufferSize(this.receiveBufferSize);
		init();
	}

	public abstract void init();

	public void connect() throws ConnectException {
		if (this.isConnected()) {
			throw new IllegalStateException("connection is connected");
		}
		// 设置连接超时检查时间
		// this.connector.setConnectTimeoutCheckInterval(30);
		// 设置事件处理器
		this.connector.setHandler(new OriginalSessionHandler());
		// 建立连接
		ConnectFuture future = connector.connect(this.address);
		// 等待连接创建完成
		future.awaitUninterruptibly();
		
	}

	public boolean isConnected() {
		if(this.session == null)
			return false;
		return this.session.isConnected();
	}

	public void send(AbstractData data) {
		this.session.write(data);
	}

	public void close() {
		if ((this.session != null) && (this.session.isConnected())) {
			this.session.close(true);
		}
	}

	public SocketAddress getRemoteAddress() {
		return this.session.getRemoteAddress();
	}

	protected abstract void connected();

	/**
	 * 原始的会话处理
	 *
	 */
	public class OriginalSessionHandler extends IoHandlerAdapter {
		public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
			Connector.log.info(cause, cause);
		}

		public void messageReceived(IoSession session, Object message) throws Exception {
			AbstractData data = (AbstractData) message;
			if (data == null) {
				Connector.log.error("get a NULL data!");
			} else {
				IDataHandler handler = ProtocolFactory.getDataHandler(data);
				if (handler != null)
					handler.handle(data);
			}
		}

		public void sessionClosed(IoSession session) throws Exception {
			//断线重连
			if (Connector.this.needRetry) {
				while (true) {
					if (Connector.this.isConnected())
						return;
					try {
						Thread.sleep(12000L);
						Connector.this.initConnector();
						Connector.this.connect();
						Connector.log.info("breaked");
					} catch (Exception e) {
						Connector.log.error(e.getMessage());
					}
				}
			}
		}

		public void sessionOpened(IoSession session) throws Exception {
			Connector.this.session = session;
			Connector.this.connected();
		}

		public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
			super.sessionIdle(session, status);
		}
	}
	
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNeedRetry(boolean needRetry) {
		this.needRetry = needRetry;
	}

	public boolean isNeedRetry() {
		return this.needRetry;
	}
}
