package com.wyd.dispatch;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
public class ClientSessionHandler extends IoHandlerAdapter {
	private SocketDispatcher dispatcher;

	public ClientSessionHandler(SocketDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void exceptionCaught(IoSession sesion, Throwable throwable) throws Exception {
	}

	public void messageReceived(IoSession session, Object object) throws Exception {
		this.dispatcher.dispatchToServer(session, object);
	}

	public void sessionClosed(IoSession session) throws Exception {
		this.dispatcher.unRegisterClient(session);
	}

	public void sessionCreated(IoSession session) throws Exception {
		this.dispatcher.registerClient(session);
	}

	public void sessionIdle(IoSession session, IdleStatus idleStatus) throws Exception {
		session.close(true);
	}
}