package com.wyd.empire.protocol.data.admin;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>LoginResult</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ADMIN下子命令ADMIN_LoginResult(登陆服务器结果)对应数据封装。
 * 
 * @see AbstractData
 * @author mazheng
 */
public class LoginResult extends AbstractData {
    private byte   loginCode;
    private String msg = "";
    private String userName;

    public LoginResult(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_LoginResult, sessionId, serial);
    }

    public LoginResult() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_LoginResult);
    }

    public byte getLoginCode() {
        return this.loginCode;
    }

    public void setLoginCode(byte loginCode) {
        this.loginCode = loginCode;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
