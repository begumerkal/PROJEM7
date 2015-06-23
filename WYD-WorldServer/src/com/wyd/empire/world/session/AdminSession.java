package com.wyd.empire.world.session;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.wyd.empire.world.entity.mysql.Admin;
import com.wyd.protocol.data.AbstractData;

/**
 * 游戏管理session
 * 
 * @author mazheng
 */
public class AdminSession extends ConnectSession {
	private static Logger log = Logger.getLogger(AdminSession.class);
	private Admin admin;
	private Vector<String> commandList = new Vector<String>();

	public boolean isLogin() {
		return (this.admin != null);
	}

	public AdminSession(IoSession session) {
		super(session);
	}

	public String getName() {
		return this.admin.getName();
	}

	public int getId() {
		return this.admin.getId();
	}

	public void login(Admin admin) {
		this.admin = admin;
		// String[] auth = admin.getAuth().split("[|]");
		// for (String a : auth)
		// this.commandList.add(a.toLowerCase());
	}

	/**
	 * 检查用户是否有权限操作对应命令，如果有权限返回false，如果无权限返回true
	 * 
	 * @param cmd
	 * @param data
	 * @return boolean
	 */
	public boolean checkPermission(String cmd, AbstractData data) {
		if ((this.commandList.contains(cmd.toLowerCase())) || (this.admin.getName().toLowerCase().equals("tt"))) {
			log.info(getName() + " [ADMIN][Execute] " + data.toString());
			return false;
		}
		return true;
	}

	/**
	 * 发送对话信息给管理员
	 * 
	 * @param srcId
	 * @param srcName
	 * @param destId
	 * @param destName
	 * @param msg
	 */
	public void receiveChatMessage(int srcId, String srcName, int destId, String destName, String msg) {
		// Chat chat = new Chat();
		// chat.setSrc(srcId);
		// chat.setSrcName(srcName);
		// chat.setDestName(destName);
		// chat.setDest(destId);
		// chat.setMsg(msg);
		// write(chat);
	}

	@Override
	public void closed() {
	}

	@Override
	public void created() {
	}

	@Override
	public <T> void handle(T packet) {
	}

	@Override
	public void idle(IoSession session, IdleStatus status) {
	}

	@Override
	public void opened() {
	}
}