package com.wyd.net;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
public abstract class Connector implements ISession { 
    protected boolean               connected         = false;
    protected boolean               valid             = false;
    protected InetSocketAddress     address;
    //是否需要安全性验证
    protected boolean               needSecureAuth;
    protected String                userName          = "";
    protected String                password          = "";
    protected boolean               needRetry         = true;
    protected NioSocketConnector    connector;
    protected SocketSessionConfig   config;
    protected int                   receiveBufferSize = 65534;
    protected int                   sendBufferSize    = 65534;
    protected IoSession             session;
    protected IDataHandler          loginHandler;
    protected IDataHandler          customHandler;
    protected IDataHandler          currentHandler;
    protected IoHandler             mainHandler       = new OriginalSessionHandler();
    protected static final Logger   log               = Logger.getLogger(Connector.class);
    protected Object                lock              = new Object();
    protected String                id;

    public Connector(String id, InetSocketAddress address, boolean needSecureAuth) {
        this.id = id;
        this.address = address;
        this.needSecureAuth = needSecureAuth;
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
        this.loginHandler = new LoginMessageHandler();
        init();
    }

    public void addIoFilterAdapter(String id, IoFilter filter) {
        if (this.connector != null) {
            this.connector.getFilterChain().addLast(id, filter);
        }
    }

    public abstract void init();

    public void connect() throws ConnectException {
        if ((this.connected) || (this.valid)) {
            throw new IllegalStateException("connection is connected");
        }
        if (this.needSecureAuth)
            this.currentHandler = this.loginHandler;
        else {
            this.currentHandler = null;
        }
        //this.connector.connect(this.address, this.mainHandler, this.config);
        // 设置连接超时检查时间
        //connector.setConnectTimeoutCheckInterval(30);
        // 设置事件处理器
        this.connector.setHandler(this.mainHandler);
        // 建立连接
        ConnectFuture future = connector.connect(this.address);
        // 等待连接创建完成
        future.awaitUninterruptibly();
        
        synchronized (this.lock) {
            try {
                this.lock.wait(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("111111 " + this.valid);
            if (!(this.valid)) {
            	System.out.println("22222 " + this.valid);
                if (this.session != null) {
                    this.session.close(true);
                }
                this.connected = false;
                throw new ConnectException("connect [" + this.address + "] error");
            }
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

    public boolean isConnected() {
        return this.connected;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void send(AbstractData data) {
        if (this.valid) send0(data);
    }

    public void close() {
        if ((this.session != null) && (this.session.isConnected())) {
            this.session.close(true);
        }
    }

    public SocketAddress getRemoteAddress() {
        return this.session.getRemoteAddress();
    }

    public boolean equals(ISession s) {
        if (this == s) return true;
        return this.id.equals(s.getId());
    }

    protected abstract void sendSecureAuthMessage();

    protected void send0(AbstractData data) {
        this.session.write(data);
    }

    protected void connected() {
    }

    protected abstract AbstractData processLogin(AbstractData paramAbstractData) throws Exception;

    protected void loginOk() {
        synchronized (this.lock) {
            this.currentHandler = null;
            this.valid = true;
            this.lock.notify();
            connected();
            log.info("ServerLoginOk");
        }
    }

    protected void loginFailed() {
        synchronized (this.lock) {
            this.lock.notify();
        }
    }
    class LoginMessageHandler implements IDataHandler {
        public AbstractData handle(AbstractData data) throws Exception {
            return Connector.this.processLogin(data);
        }
    }
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
            } else if (Connector.this.currentHandler != null) {
                Connector.this.currentHandler.handle(data);
            } else {
                IDataHandler handler = ProtocolFactory.getDataHandler(data);
                if (handler != null) handler.handle(data);
            }
        }

        public void sessionClosed(IoSession session) throws Exception {
        	System.out.println("sessionClosed "+session);
            Connector.this.connected = false;
            if ((Connector.this.needRetry) && (Connector.this.valid)) {
                Connector.this.valid = false;
                while (true) {
                    if (Connector.this.connected) return;
                    try {
                        Thread.sleep(3000L);
                    } catch (Exception ex) {
                    }
                    try {
                        Connector.this.initConnector();
                        Connector.this.connect();
                        Connector.log.info("breaked");
                    } catch (Exception e) {
                    }
                }
            }
            Connector.this.valid = false;
        }

        public void sessionOpened(IoSession session) throws Exception {
        	System.out.println("sessionOpened "+session);
            Connector.this.session = session;
            Connector.this.connected = true;
            if (!(Connector.this.needSecureAuth)) {
                Connector.this.valid = true;
                Connector.this.currentHandler = null;
                synchronized (Connector.this.lock) {
                    Connector.this.lock.notify();
                }
                Connector.this.connected();
            } else {
                Connector.this.sendSecureAuthMessage();
            }
        }

//        public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
//            super.sessionIdle(session, status);
//        }
    }
}
