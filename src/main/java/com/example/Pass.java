package com.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import org.hibernate.annotations.Type;
// import org.hibernate.annotations.Column;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
// import package.BaseEntity;

@Entity
public class Pass extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private long orgId;
  private long userId;
  private long eventsId;
  // private boolean transferability; //false means only members can transfer

  // @JsonFormat(pattern="yyyy-MM-dd@HH:mm:ss.SSSZ")
  private String date;

  @Type(type = "string-array")
  @Column(
      name = "owners",
      columnDefinition = "text[]"
  )
  @JsonFormat(shape=JsonFormat.Shape.ARRAY)
  private String[] owners;

  private boolean isLocked;

  public Long getId() {
    return id;
  }

  public Long getOrgId() {
    return orgId;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getEventsId() {
    return eventsId;
  }

  public String getDate() {
    return date;
  }

  public String[] getOwners() {
    return owners;
  }

  public Boolean getIsLocked() {
    return isLocked;
  }


  public void setDate(String date) {
    this.date = date;
  }

  public void setUserId(Long userid) {
    this.userId = userid;
  }

  public void setOwners(String[] owners) {
    this.owners = owners;
  }

  public void setIsLocked(Boolean isLocked) {
    this.isLocked = isLocked;
  }

  public void updateParameters(Pass other) {
    this.orgId = other.getOrgId();
    this.userId = other.getUserId();
    this.eventsId = other.getEventsId();
    this.date = other.getDate();
    this.owners = other.getOwners();
    this.isLocked = other.getIsLocked();
    // this.passName = other.getPassName();
    // this.transferability = other.getTransferability();
    // this.cutoff = other.getCutoff();
  }
}
