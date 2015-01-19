package com.wyd.service.bean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tab_empireaccount")
public class Empireaccount  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String name;
  private String serverid;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name="id", unique=true, nullable=false, precision=10)
  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Basic
  @Column(name="name", length=25)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Basic
  @Column(name="serverid", length=10)
  public String getServerid() {
    return this.serverid;
  }

  public void setServerid(String serverid) {
    this.serverid = serverid;
  }
}