package com.wyd.service.bean;
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
 * 自定义称号
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_player_diy_title")
public class PlayerDIYTitle implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer           id;	      // 
    private int               icon;       // 图标
    private String            title;      // 称号
    private String            titleDesc;  // 描述
    private int               playerId;   // 玩家ID 
    private Date              startDate;  // 开始时间  
    private Date              endDate;    // 结束时间
    private int               state;      // 状态0未使用1使用
    
    
	@Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Basic()
    @Column(name = "icon", nullable = false, length = 2)
    public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	@Basic()
    @Column(name = "title", nullable = false, length = 50)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Basic()
    @Column(name = "title_desc", nullable = false, length = 150)
	public String getTitleDesc() {
		return titleDesc;
	}
	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}
	@Basic()
    @Column(name = "player_id", nullable = true, length = 4)
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	@Basic()
    @Column(name = "start_date")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Basic()
    @Column(name = "end_date")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Basic()
    @Column(name = "state", nullable = false,length=2)
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
    
    
   
	 
}