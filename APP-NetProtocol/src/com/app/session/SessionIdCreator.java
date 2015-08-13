package com.app.session;

/**
 * 类 <code>SessionIdCreator</code><p>创建新的新的SessionId</p> 
 * 
 * @since JDK 1.6
 */
public class SessionIdCreator {
    private static int sessionId = 0;

    /**
     * 返回系统当前最新的<tt>SessionId</tt>
     * 
     * @return  <tt>sessionId</tt>
     */
    public static int getNewSessionId() {
        synchronized (SessionIdCreator.class) {
            return (sessionId++);
        }
    }
}
