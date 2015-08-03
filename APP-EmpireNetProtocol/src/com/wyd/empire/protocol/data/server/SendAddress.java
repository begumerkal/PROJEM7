package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class SendAddress extends AbstractData {
	private int id;
    private String area;
    private String group;
    private int serverId;
    private String address;
    private String version;

    public SendAddress(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_SendAddress, sessionId, serial);
    }

    public SendAddress() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_SendAddress);
    }

	public int getId() {
		return id;
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

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
 

  
}
