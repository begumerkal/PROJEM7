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
/**
 * The persistent class for the tab_application database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_code_exchange")
public class CodeExchange implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
    private Integer           id;
    private String            code;                 // 生成的礼包兑换码
    private Date              createtime;           // 创建时间
    private String            creator;              // 创建人
    private Integer           used;                 // 0:没被使用 1:已被使用 2:已成功兑换
    private Integer           extended;             // 0:没被发放 1:已经被发放
    private Date              extendtime;           // 发放时间
    private Integer           useful_life;          // 有效期,从发放时间开始算起多少天内
    private String            content;              // 赠送内容如:gold=1500&wp*28count*1day*-1sex*2&ticket=1&wp*1count*1day*-1sex*2
    private String            describe;             // 礼包内容描述备注
    private String            channel_id;           // 渠道号
    private Integer           getter_playerid;      // 获取者
    private String            getter_severid;       // 获取者所在服务器号
    private Date              gettime;              // 获取的时间
    private String            batchNum;             // 生成批次
    private Integer           minlevel;             // 领取奖励的最低等级
    private Integer           maxlevel;             // 领取奖励的最高等级
    private int               rewardType;           // 奖励的类型 0游戏物品奖励，1邮件奖励

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
    @Column(name = "code", length = 32)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic()
    @Column(name = "createtime")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Basic()
    @Column(name = "creator", length = 32)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic()
    @Column(name = "used", precision = 1)
    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    @Basic()
    @Column(name = "extended", precision = 1)
    public Integer getExtended() {
        return extended;
    }

    public void setExtended(Integer extended) {
        this.extended = extended;
    }

    @Basic()
    @Column(name = "extendtime")
    public Date getExtendtime() {
        return extendtime;
    }

    public void setExtendtime(Date extendtime) {
        this.extendtime = extendtime;
    }

    @Basic()
    @Column(name = "useful_life", precision = 10)
    public Integer getUseful_life() {
        return useful_life;
    }

    public void setUseful_life(Integer useful_life) {
        this.useful_life = useful_life;
    }

    @Basic()
    @Column(name = "content", length = 500)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic()
    @Column(name = "remark", length = 500)
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Basic()
    @Column(name = "channel_id", length = 32)
    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    @Basic()
    @Column(name = "getter_playerid", precision = 10)
    public Integer getGetter_playerid() {
        return getter_playerid;
    }

    public void setGetter_playerid(Integer getter_playerid) {
        this.getter_playerid = getter_playerid;
    }

    @Basic()
    @Column(name = "getter_severid", length = 32)
    public String getGetter_severid() {
        return getter_severid;
    }

    public void setGetter_severid(String getter_severid) {
        this.getter_severid = getter_severid;
    }

    @Basic()
    @Column(name = "gettime")
    public Date getGettime() {
        return gettime;
    }

    public void setGettime(Date gettime) {
        this.gettime = gettime;
    }

    @Basic()
    @Column(name = "batch_num", length = 32)
    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    @Basic()
    @Column(name = "minlevel", precision = 10)
    public Integer getMinlevel() {
        return minlevel;
    }

    public void setMinlevel(Integer minlevel) {
        this.minlevel = minlevel;
    }

    @Basic()
    @Column(name = "maxlevel", precision = 10)
    public Integer getMaxlevel() {
        return maxlevel;
    }

    public void setMaxlevel(Integer maxlevel) {
        this.maxlevel = maxlevel;
    }

    @Basic()
    @Column(name = "reward_type", precision = 1)
    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }
}