package com.wyd.empire.nearby.handler.admin;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.admin.UpdateGPSServerConfigure;
import com.wyd.empire.protocol.data.admin.UpdateGPSServerConfigureOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 更新GPS服务配置
 * @author chenjie
 */
public class UpdateGPSServerConfigureHandler implements IDataHandler {
    private Logger log;

    public UpdateGPSServerConfigureHandler() {
        this.log = Logger.getLogger(UpdateGPSServerConfigureHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        UpdateGPSServerConfigure cfg = (UpdateGPSServerConfigure)data;
        UpdateGPSServerConfigureOk updateCfgOk = new UpdateGPSServerConfigureOk(data.getSessionId(), data.getSerial());
        try {
        	ServiceManager.getManager().getConfiguration().setValue("maxresults", cfg.getMaxresults() + "");
        	ServiceManager.getManager().getConfiguration().setValue("pagesize", cfg.getPagesize() + "");
        	ServiceManager.getManager().getConfiguration().setValue("threshold", cfg.getThreshold() + "");
        	ServiceManager.getManager().getConfiguration().setValue("updatetime", cfg.getUpdatetime() + "");
        	ServiceManager.getManager().getConfiguration().setValue("maxfriendcount", cfg.getMaxfriendcount() + "");
        	ServiceManager.getManager().getConfiguration().saveFile();
        	updateCfgOk.setResult(true);
            session.send(updateCfgOk);
        } catch (Exception ex) {
            log.error(ex, ex);
        	updateCfgOk.setResult(false);
            session.send(updateCfgOk);
        }
    }
}