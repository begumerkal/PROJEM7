package com.action;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.mongo.entity.LogEntity;
import com.mongo.impl.LogDao;

 

public class WriteLog extends HttpServlet{

	public WriteLog() {
		// TODO Auto-generated constructor stub
	}

    private static final String CONTENT_TYPE     = "text/html;charset=utf-8";
    private static final long   serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int type =  req.getParameter("type")==null?0:Integer.parseInt( req.getParameter("type"));
        int subType = req.getParameter("subType")==null?0:Integer.parseInt( req.getParameter("subType"));
        String msg = req.getParameter("msg");
        
		System.out.println("Bootstrapping HelloMongo");
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		LogDao  logDao = context.getBean(LogDao.class);
		
		LogEntity entity = new LogEntity();
		entity.setType(type);
		entity.setSubType(subType);
		entity.setMsg(msg);
		
//		logDao.save(entity);
		logDao.insert(entity);
		
		
		String sendStr = JSON.toJSONString(entity);
		resp.setContentType(CONTENT_TYPE);
		resp.setStatus(200);
		ServletOutputStream out = resp.getOutputStream();
		OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
		os.write(sendStr);
		os.flush();
		os.close();
    }
 

}
