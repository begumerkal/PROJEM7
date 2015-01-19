package com.wyd.service.bean;
// default package
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * TabPlayerTaskTitle entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tab_player_task_title")
public class PlayerTaskTitle implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // Fields
    private Integer           id;
    private int               playerId;
    private byte[]            titleList;

    // Constructors
    /** default constructor */
    public PlayerTaskTitle() {
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tptt_id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   
    @Column(name = "player_id")
    public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Column(name = "title_list")
    public byte[] getTitleList() {
        return this.titleList;
    }

    public void setTitleList(byte[] titleList) {
        this.titleList = titleList;
    }
   
}