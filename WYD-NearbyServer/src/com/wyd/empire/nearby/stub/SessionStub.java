package com.wyd.empire.nearby.stub;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ThreadPoolExecutor;

//import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.nearby.util.Configuration;
import com.wyd.net.ProtocolFactory;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.data.DataBeanFilter;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.protocol.s2s.S2SDecoder;
import com.wyd.protocol.s2s.S2SEncoder;
import com.wyd.session.AcceptSession;
import com.wyd.session.HandlerMonitorService;
import com.wyd.thread.ThreadPool;
/**
 * 分区服务
 * @author zguoqiu
 */
public class SessionStub {
    private NioSocketAcceptor   acceptor;
    private Configuration       configuration;
    private int                 receiveBufferSize = 32767;
    private int                 sendBufferSize    = 32767;
    private static final Logger log               = Logger.getLogger(SessionStub.class);

    public SessionStub(Configuration configuration) {
        this.configuration = configuration;
    }

    public void start() throws IOException {
        this.acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
        SocketSessionConfig  cfg = acceptor.getSessionConfig();
        cfg.setTcpNoDelay(true);
        cfg.setReuseAddress(true);
        if (this.configuration.getValue("receivebuffersize") != null) {
            this.receiveBufferSize = Integer.parseInt(this.configuration.getValue("receivebuffersize"));
        }
        if (this.configuration.getValue("sendbuffersize") != null) {
            this.sendBufferSize = Integer.parseInt(this.configuration.getValue("sendbuffersize"));
        }
        cfg.setReceiveBufferSize(this.receiveBufferSize);
        cfg.setSendBufferSize(this.sendBufferSize);
        this.acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new S2SEncoder(), new S2SDecoder()));
        this.acceptor.getFilterChain().addLast("uwap2data", new DataBeanFilter());
        acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(1, 4));
        //指定业务逻辑处理器
        acceptor.setHandler(new ClientSessionHandler());
        // 设置端口号
        acceptor.setDefaultLocalAddress(new InetSocketAddress(this.configuration.getValue("serverip"), Integer.parseInt(this.configuration.getValue("port"))));
        acceptor.bind();
    }
    class ClientSessionHandler extends IoHandlerAdapter {

        private HandlerThreadPool   threadPool = null;
        
        public ClientSessionHandler(){
            threadPool = new HandlerThreadPool(10, 100, 50);
        }
        
        public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
            SessionStub.log.info(cause, cause);
        }

        public void messageReceived(IoSession session, Object message) throws Exception {
            AbstractData msg = (AbstractData) message;
            threadPool.execute(new ThreadTask(session, msg));
        }

        public void sessionClosed(IoSession session) throws Exception {
            AcceptSession s = (AcceptSession) session.removeAttribute("acceptsession");
            if (s != null) {
                s.setValid(false);
                if (s.getId() != null) {
                    ServiceManager.getManager().getSessionService().removeSession(s);
                    SessionStub.log.info("Session[" + s.getId() + "]removed");
                }
            }
        }

        public void sessionOpened(IoSession session) throws Exception {
            AcceptSession s = new AcceptSession(session);
            s.setValid(false);
            session.setAttribute("acceptsession", s);
        }
        
        class HandlerThreadPool extends ThreadPool {
            /**
             * 构造函数，初始化线程池及队列
             * @param minPoolSize 最小线程数
             * @param maxPoolSize 最大线程数
             * @param queurSize 等待队列大小
             */
            public HandlerThreadPool(int minPoolSize, int maxPoolSize, int queurSize) {
                super(minPoolSize, maxPoolSize, queurSize);
            }

            /**
             * 线程池已满,拒绝线程
             * @param runnable 任务信息
             * @param threadPoolExecutor 异常信息
             */
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                System.err.println("ThreadPool Is Full...");
            }
        }
        
        class ThreadTask implements Runnable {
            private IoSession session;
            private AbstractData data;
            /**
             * 构造函数，安化化相关参数
             * @param session 会放信息
             * @param message 协议信息
             */
            public ThreadTask(IoSession session, AbstractData data) {
                this.session = session;
                this.data = data;
            }

            @Override
            public void run() {
                try {
                    AcceptSession s = (AcceptSession) session.getAttribute("acceptsession");
                    if (s != null) {
                        data.setSource(s);
                        IDataHandler handler = ProtocolFactory.getDataHandler(data);
                        if (handler == null) {
                            SessionStub.log.info("can not find handler :" + data.getType() + "." + data.getSubType());
                        } else {
                            handler.handle(data);
                        }
                    }
                } catch (ProtocolException e) {
                    HandlerMonitorService.delMonitor(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    HandlerMonitorService.delMonitor(data);
                    log.error("messageReceived-handle-error", e);
                }
            }
        }
    }
}
