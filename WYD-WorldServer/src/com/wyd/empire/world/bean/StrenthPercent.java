package com.wyd.empire.world.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the tab_operationconfig database table.
 * 
 * @author BEA Workshop
 */

@Entity()
@Table(name = "tab_percentwords")
public class StrenthPercent implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer percentLow;
	private Integer percentHigh;
	private String wordDesc;

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
	@Column(name = "percent_low", length = 500)
	public Integer getPercentLow() {
		return percentLow;
	}

	public void setPercentLow(Integer percentLow) {
		this.percentLow = percentLow;
	}

	@Basic()
	@Column(name = "percent_high", length = 500)
	public Integer getPercentHigh() {
		return percentHigh;
	}

	public void setPercentHigh(Integer percentHigh) {
		this.percentHigh = percentHigh;
	}

	@Basic()
	@Column(name = "word_desc", length = 500)
	public String getWordDesc() {
		return wordDesc;
	}

	public void setWordDesc(String wordDesc) {
		this.wordDesc = wordDesc;
	}

}