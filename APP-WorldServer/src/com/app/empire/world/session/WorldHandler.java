package com.app.empire.world.session;

import io.netty.channel.ChannelHandlerContext;

import com.app.empire.world.service.factory.ServiceManager;
import com.app.protocol.data.AbstractData;
import com.app.session.Session;
import com.app.session.ServerHandler;
import com.app.session.SessionRegistry;

/**
 * 类 SessionHandler Session处理类 继承SessionHandler ，由子类负责创建不同类型的Session类<br>
 * 抽象Session处理方法，实现基本处理逻辑
 * 
 * @since JDK 1.6
 */
public abstract class WorldHandler extends ServerHandler {

	/**
	 * 构造函数，初始化<tt>SessionRegistry</tt>值
	 * 
	 * @param registry
	 */
	public WorldHandler(SessionRegistry registry) {
		super(registry);
	}

	/*
	 * 处理dis 转发过来的消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		AbstractData dataobj = (AbstractData) msg;
		Session session = this.registry.getSession(ctx.channel());
		if (session != null)
			ServiceManager.getManager().getAbstractService().addAbstractInfo(dataobj, session);
		else
			System.out.println(dataobj.toString() + "<<<<<<<<<<<<<<<<<<<");
	}
}
