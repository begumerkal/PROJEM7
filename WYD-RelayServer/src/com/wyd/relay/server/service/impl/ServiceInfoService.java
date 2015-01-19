package com.wyd.relay.server.service.impl;
import java.io.File;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import com.wyd.relay.bean.Service;
import com.wyd.relay.util.ServiceInfoUtils;
/**
 * 管理国家地区配置，新玩家分配机率配置
 * 
 * @author Administrator
 */
public class ServiceInfoService implements Runnable {
    private List<Service>                     serviceList;
    private File                              infoFile;
    private long                              infoLastModified;

    public ServiceInfoService() throws ConfigurationException {
        infoFile = new File(System.getProperty("user.dir") + "/serviceinfo.xml");
        this.infoLastModified = infoFile.lastModified();
        loadSources();
        start();
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.setName("ServiceInfoService-Thread");
        thread.start();
    }

    public void run() {
        while (true) {
            if (isFileModified()) {
                try {
                    loadSources();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean isFileModified() {
        long t = this.infoFile.lastModified();
        if (t != this.infoLastModified) {
            this.infoLastModified = t;
            return true;
        }
        return false;
    }

    private void loadSources() throws ConfigurationException {
        serviceList = ServiceInfoUtils.getInfoList();
    }

    /**
     * 获取游戏服务器列表信息
     * @return
     */
    public List<Service> getServiceList() {
        return serviceList;
    }
}
