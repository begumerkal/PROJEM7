package com.wyd.empire.world.server.handler.system;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.system.GetKeyProcessOk;
import com.wyd.empire.world.common.util.KeyProcess;
import com.wyd.empire.world.common.util.KeyProcessService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取外挂进程列表
 * 
 * @author zengxc
 * 
 */
public class GetKeyProcessHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetKeyProcessHandler.class);

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		KeyProcessService service = KeyProcessService.getInstance();
		List<KeyProcess> keyprocesses = service.getKeyProcesses();
		String[] name = new String[keyprocesses.size()];
		String[] value = new String[keyprocesses.size()];
		for (int i = 0; i < keyprocesses.size(); i++) {
			KeyProcess process = keyprocesses.get(i);
			name[i] = process.getName();
			value[i] = process.getValue();
		}
		GetKeyProcessOk ok = new GetKeyProcessOk(data.getSessionId(), data.getSerial());
		ok.setName(name);
		ok.setValue(value);
		session.write(ok);
		return null;
	}
}
