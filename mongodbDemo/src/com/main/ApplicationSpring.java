package com.main;

import java.util.Date;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.action.WriteLog;
import com.mongo.entity.NameEntity;
import com.mongo.entity.UserEntity;
import com.mongo.impl.UserDao;


public class ApplicationSpring {

	private static ConfigurableApplicationContext context;
	public static void main(String[] args) {

		System.out.println("Bootstrapping HelloMongo111");
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDao  userDao = context.getBean(UserDao.class);
		
		
		NameEntity ne = new NameEntity();
		ne.setNickname("nickname");
		ne.setUsername("username");
		
		UserEntity entity1 = new UserEntity();
//		entity1.setId(1);
		entity1.setAge(111);
		entity1.setName(ne);
		entity1.setBirth(new Date());
		entity1.setPassword("asdfasdf");
		entity1.setRegionName("北京");
		entity1.setWorks(2);
		
		userDao.insert(entity1);
//		userDao.save(entity1);
 
		
//		try {
//			new ApplicationSpring().openSetverListServlet();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	
	
	
	
	private void openSetverListServlet() throws Exception {
		Server server = new Server();
		// // 设置jetty线程池
		// BoundedThreadPool threadPool = new BoundedThreadPool();
		// // 设置连接参数
		// threadPool.setMinThreads(50);
		// threadPool.setMaxThreads(1000);
		// 设置监听端口，ip地址
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(8888);
		connector.setHost("0.0.0.0");
		server.addConnector(connector);
		// 访问项目地址
		Context root = new Context(server, "/", 1);
		root.addServlet(new ServletHolder(new WriteLog()), "/log");//http://localhost:6887/?area=CN&group=1000_G1&serverid=0
//		root.addServlet(new ServletHolder(new ServerLoadServlet()), "/load/*");
		server.start();
	}
}
