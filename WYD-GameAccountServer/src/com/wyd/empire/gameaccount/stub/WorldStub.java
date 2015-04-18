package com.wyd.empire.gameaccount.stub;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.wyd.empire.gameaccount.session.AcceptSession;
import com.wyd.protocol.data.DataBeanFilter;
import com.wyd.protocol.s2s.S2SDecoder;
import com.wyd.protocol.s2s.S2SEncoder;
import com.wyd.session.Session;
import com.wyd.session.SessionHandler;
import com.wyd.session.SessionRegistry;
/**
 * 账号服务
 * 
 * @author doter
 *
 */
public class WorldStub {
	private NioSocketAcceptor acceptor;
	private Configuration configuration;
	private int receiveBufferSize = 32767;
	private int sendBufferSize = 32767;
	private static final Logger log = Logger.getLogger(WorldStub.class);
	private SessionRegistry registry;

	public WorldStub(Configuration configuration, SessionRegistry registry) {
		this.registry = new SessionRegistry();
		this.configuration = configuration;
	}

	public void start() throws IOException {
		this.acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
		SocketSessionConfig cfg = acceptor.getSessionConfig();
		cfg.setIdleTime(IdleStatus.BOTH_IDLE,30);
		cfg.setTcpNoDelay(true);
		cfg.setReuseAddress(true);
		if (this.configuration.containsKey("receivebuffersize")) {
			this.receiveBufferSize = this.configuration.getInt("receivebuffersize");
		}
		if (this.configuration.containsKey("sendbuffersize")) {
			this.sendBufferSize = this.configuration.getInt("sendbuffersize");
		}
		cfg.setReceiveBufferSize(this.receiveBufferSize);
		cfg.setSendBufferSize(this.sendBufferSize);
		this.acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new S2SEncoder(), new S2SDecoder()));
		this.acceptor.getFilterChain().addLast("uwap2data", new DataBeanFilter());
		acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(1, 4));

		// 指定业务逻辑处理器
		acceptor.setHandler(new ClientSessionHandler(this.registry));
		// 设置端口号
		acceptor.setDefaultLocalAddress(new InetSocketAddress(this.configuration.getString("serverip"), this.configuration.getInt("port")));
		acceptor.bind();
	}

	class ClientSessionHandler extends SessionHandler {

		public ClientSessionHandler(SessionRegistry registry) {
			super(registry);
		}

		@Override
		public Session createSession(IoSession ioSession) {
			return new AcceptSession(ioSession);
		}

	}
}
