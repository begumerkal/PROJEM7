package com.wyd.server.dispatcher;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import com.wyd.server.service.ServiceManager;
import com.wyd.session.Session;
public class DispatchSession extends Session {
    Logger      log = Logger.getLogger(DispatchSession.class);
    private int id;
    private String area;
    private String group;
    private Integer serverId;
    private String address;
    

    public DispatchSession(IoSession session) {
        super(session);
    }

    public void closed() {
        ServiceManager.getManager().getServerListService().removeServer(area, group, serverId, id);
    }

    public void created() {
    }

    public <T> void handle(T packet) {
    }

    public void idle(IdleStatus status) {
    }

    public void opened() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
    
    
}
