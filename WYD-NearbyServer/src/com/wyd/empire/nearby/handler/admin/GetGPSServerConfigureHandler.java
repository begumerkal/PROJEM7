package com.wyd.empire.nearby.handler.admin;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.admin.GetGPSServerConfigureOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 获取GPS服务配置
 * @author chenjie
 */
public class GetGPSServerConfigureHandler implements IDataHandler {
    private Logger log;

    public GetGPSServerConfigureHandler() {
        this.log = Logger.getLogger(GetGPSServerConfigureHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        try {
        	GetGPSServerConfigureOk getCfgOk = new GetGPSServerConfigureOk(data.getSessionId(), data.getSerial());
        	getCfgOk.setMaxresults(500);
        	getCfgOk.setPagesize(50);
        	getCfgOk.setThreshold(500000);
        	getCfgOk.setUpdatetime(1000);
        	getCfgOk.setMaxfriendcount(500);
        	if (ServiceManager.getManager().getConfiguration().getValue("maxresults") != null) {
        		getCfgOk.setMaxresults(Integer.parseInt(ServiceManager.getManager().getConfiguration().getValue("maxresults")));
			}
        	
        	if (ServiceManager.getManager().getConfiguration().getValue("pagesize") != null) {
        		getCfgOk.setPagesize(Integer.parseInt(ServiceManager.getManager().getConfiguration().getValue("pagesize")));
			}
        	
        	if (ServiceManager.getManager().getConfiguration().getValue("threshold") != null) {
        		getCfgOk.setThreshold(Integer.parseInt(ServiceManager.getManager().getConfiguration().getValue("threshold")));
			}
        	
        	if (ServiceManager.getManager().getConfiguration().getValue("updatetime") != null) {
        		getCfgOk.setUpdatetime(Integer.parseInt(ServiceManager.getManager().getConfiguration().getValue("updatetime")));
        	}
        	
        	if (ServiceManager.getManager().getConfiguration().getValue("maxfriendcount") != null) {
        		getCfgOk.setMaxfriendcount(Integer.parseInt(ServiceManager.getManager().getConfiguration().getValue("maxfriendcount")));
        	}
        	getCfgOk.setOpen(true);
            session.send(getCfgOk);
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}