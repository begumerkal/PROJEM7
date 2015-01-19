package com.wyd.empire.protocol.data.admin;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 获取GPS服务配置成功
 * 
 * @see AbstractData
 * @author chenjie
 */
public class GetGPSServerConfigureOk extends AbstractData {
	 /**#查找附近玩家阈值1000000倍，5000相当于查找附近+-0.5度*/
    private int threshold;
    /**#附近玩家大返回数量*/
    private int maxresults;
    /**#附近玩家列表更新时间（毫秒）*/
    private int updatetime;
    /**#pagesize翻页大小*/
    private int pagesize;
    /** GPS服务是否开启或是否可以正常连接 */
    private boolean open;
    /** 玩家最大附件好友数量 */
    private int maxfriendcount;

    public GetGPSServerConfigureOk(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetGPSServerConfigureOk, sessionId, serial);
    }

    public GetGPSServerConfigureOk() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetGPSServerConfigureOk);
    }

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public int getMaxresults() {
		return maxresults;
	}

	public void setMaxresults(int maxresults) {
		this.maxresults = maxresults;
	}

	public int getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(int updatetime) {
		this.updatetime = updatetime;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public boolean getOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getMaxfriendcount() {
		return maxfriendcount;
	}

	public void setMaxfriendcount(int maxfriendcount) {
		this.maxfriendcount = maxfriendcount;
	}

    
}
