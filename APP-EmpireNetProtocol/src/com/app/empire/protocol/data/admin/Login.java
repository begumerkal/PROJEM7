package com.app.empire.protocol.data.admin;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>Login</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ADMIN下子命令ADMIN_Login(登陆服务器)对应数据封装。
 * 
 * @see AbstractData
 * @author mazheng
 */
public class Login extends AbstractData {
    private String name;
    private String password;

    public Login(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Login, sessionId, serial);
    }

    public Login() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Login);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
