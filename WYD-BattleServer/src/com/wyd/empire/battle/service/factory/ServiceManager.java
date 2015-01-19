package com.wyd.empire.battle.service.factory;
import java.io.File;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import com.wyd.empire.battle.service.ISessionService;
import com.wyd.empire.battle.service.impl.BattleTeamService;
import com.wyd.empire.battle.service.impl.PairService;
import com.wyd.empire.battle.service.impl.SessionService;
import com.wyd.empire.battle.stub.SessionStub;
import com.wyd.empire.battle.util.ThreadPool;
public class ServiceManager {
    protected static ServiceManager instance = null;
    private Configuration           configuration;
    private SessionStub             sessionStub;
    private ClientListManager       clientListManager;
    private ISessionService         sessionService;
    private BattleTeamService       battleTeamService;
    private PairService             pairService;
    private ThreadPool              simpleThreadPool;

    protected ServiceManager() {
        try {
            this.configuration = new PropertiesConfiguration("config.properties");
            this.clientListManager = new ClientListManager(new File(Thread.currentThread().getContextClassLoader().getResource("clients.txt").getPath()));
            this.sessionService = new SessionService();
            this.sessionStub = new SessionStub(this.configuration);
            this.battleTeamService = new BattleTeamService();
            this.pairService = new PairService();
            // 简单任务的线程池
            this.simpleThreadPool = new ThreadPool(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public ISessionService getSessionService() {
        return sessionService;
    }

    public SessionStub getSessionStub() {
        return sessionStub;
    }

    public ClientListManager getClientListManager() {
        return clientListManager;
    }

    public static ServiceManager getManager() {
        synchronized (ServiceManager.class) {
            if (null == instance) {
                instance = new ServiceManager();
                instance.init();
            }
        }
        return instance;
    }

    private void init() {
        this.clientListManager.start();
        this.battleTeamService.start();
        this.pairService.start();
    }

    public BattleTeamService getBattleTeamService() {
        return battleTeamService;
    }

    public PairService getPairService() {
        return pairService;
    }

    public ThreadPool getSimpleThreadPool() {
        return simpleThreadPool;
    }
}
