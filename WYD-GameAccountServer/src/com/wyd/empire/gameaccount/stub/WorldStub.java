package com.wyd.empire.gameaccount.stub;
import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import com.wyd.empire.gameaccount.service.ISessionService;
import com.wyd.net.ProtocolFactory;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.data.DataBeanFilter;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.protocol.s2s.S2SDecoder;
import com.wyd.protocol.s2s.S2SEncoder;
import com.wyd.session.AcceptSession;
/**
 * 分区服务
 * @author sunzx
 *
 */
public class WorldStub {
    private NioSocketAcceptor   acceptor;
    private Configuration       configuration;
    private int                 receiveBufferSize = 32767;
    private int                 sendBufferSize    = 32767;
    private static final Logger log               = Logger.getLogger(WorldStub.class);
    private ISessionService     sessionService;

    public WorldStub(Configuration configuration, ISessionService sessionService) {
        this.configuration = configuration;
        this.sessionService = sessionService;
    }

    public void start() throws IOException {
        this.acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
        SocketSessionConfig  cfg = acceptor.getSessionConfig();
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
        //指定业务逻辑处理器
        acceptor.setHandler(new ClientSessionHandler());
        // 设置端口号
        acceptor.setDefaultLocalAddress(new InetSocketAddress(this.configuration.getString("serverip"), this.configuration.getInt("port")));
        acceptor.bind();
    }
    class ClientSessionHandler extends IoHandlerAdapter {
        public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
            WorldStub.log.info(cause, cause);
        }

        public void messageReceived(IoSession session, Object message) throws Exception {
            AbstractData msg = (AbstractData) message;
            AcceptSession s = (AcceptSession) session.getAttribute("acceptsession");
            if (s != null) {
                msg.setSource(s);
                IDataHandler handler = ProtocolFactory.getDataHandler(msg);
                if (handler == null){
                    WorldStub.log.info("can not find handler :" + msg.getType() + "." + msg.getSubType());
                }else {
                    handler.handle(msg);
                }
            }
        }

        public void sessionClosed(IoSession session) throws Exception {
            AcceptSession s = (AcceptSession) session.removeAttribute("acceptsession");
            if (s != null) {
                s.setValid(false);
                if (s.getId() != null) {
                    WorldStub.this.sessionService.removeSession(s);
                    WorldStub.log.info("Session[" + s.getId() + "]removed");
                }
            }
        }

        public void sessionOpened(IoSession session) throws Exception {
            AcceptSession s = new AcceptSession(session);
            s.setValid(false);
            session.setAttribute("acceptsession", s);
        }
    }
}
