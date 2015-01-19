package com.wyd.exchange.bean;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * The persistent class for the tab_application database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_invite_info")
public class InviteInfo implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
    private Integer           id;
    private String            serviceName;            //玩家所在的服务器名称
    private String            playerName;             //玩家名称
    private String            inviteCode;             //玩家的邀请码
    private String            bindInviteCode;         //玩家绑定的邀请码
    private int               inviteNum;              //玩家邀请的玩家数量
    private Date              bindTime;              //玩家邀请的玩家数量

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Basic()
    @Column(name = "service_name", length = 50)
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    @Basic()
    @Column(name = "player_name", length = 50)
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    @Basic()
    @Column(name = "invite_code", length = 50)
    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
    @Basic()
    @Column(name = "bind_invite_code", length = 50)
    public String getBindInviteCode() {
        return bindInviteCode;
    }

    public void setBindInviteCode(String bindInviteCode) {
        this.bindInviteCode = bindInviteCode;
    }
    @Basic()
    @Column(name = "invite_num", precision = 10)
    public int getInviteNum() {
        return inviteNum;
    }

    public void setInviteNum(int inviteNum) {
        this.inviteNum = inviteNum;
    }
    @Basic()
    @Column(name = "bind_time")
    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InviteInfo)) {
            return false;
        }
        InviteInfo castOther = (InviteInfo) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
}