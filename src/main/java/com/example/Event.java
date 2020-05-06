package com.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.Date;
import java.sql.Time;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import javax.persistence.Column;
import org.hibernate.annotations.Type;

@Entity
public class Event extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private long orgId;

  // @NotEmpty
  private String name;

  // @NotEmpty
  private String description;

  // @NotEmpty
  // @JsonFormat(pattern="yyyy-MM-dd@HH:mm:ss.SSSZ")
  private String date;

  // @NotEmpty
  // private Time start;

  // @NotEmpty
  // private Time ends;

  // @NotEmpty
  private String location;

  @Type(type = "string-array")
  @Column(
      name = "bouncers",
      columnDefinition = "text[]"
  )
  @JsonFormat(shape=JsonFormat.Shape.ARRAY)
  private String[] bouncers;

  private boolean openTimeVisibility; //default false
  private boolean closeDateVisibility; //default false
  private boolean closeTimeVisibility; //default false
  private boolean allStudentsVisibility; //default false

  // new fields
  // @JsonFormat(pattern="yyyy-MM-dd@HH:mm:ss.SSSZ")
  private String endDate;
  private boolean transferability; //false means only members can transfer...default true
  // @JsonFormat(pattern="yyyy-MM-dd@HH:mm:ss.SSSZ")
  private String cutoff;
  private long numArrived;


  public Long getId() {
    return id;
  }

  public void updateParameters(Event other) {
    this.orgId = other.getOrgId();
    this.name = other.getEventName();
    this.description = other.getDescription();
    this.date = other.getDate();
    // this.start = other.getStartTime();
    // this.ends = other.getEndTime();
    this.location = other.getLocation();
    this.openTimeVisibility = other.getOpenTimeVisibility();
    this.closeDateVisibility = other.getCloseDateVisibility();
    this.closeTimeVisibility = other.getCloseTimeVisibility();
    this.allStudentsVisibility = other.getAllStudentsVisibility();
    this.endDate = other.getEndDate();
    this.transferability = other.getTransferability();
    this.cutoff = other.getCutoff();
    this.bouncers = other.getBouncers();
    this.numArrived = other.getNumArrived();
  }

  public String[] getBouncers(){
    return bouncers;
  }

  public void setBouncers(String[] bouncers){
    this.bouncers = bouncers;
  }

  public Long getOrgId() {
    return orgId;
  }

  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }

  public String getEventName() {
    return name;
  }

  public void setEventName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String descrip) {
    this.description = descrip;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  // public Time getStartTime() {
  //   return start;
  // }

  // public void setStartTime(Time start) {
  //   this.start = start;
  // }

  // public Time getEndTime() {
  //   return ends;
  // }

  // public void setEndTime(Time end) {
  //   this.ends = end;
  // }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Boolean getOpenTimeVisibility() {
    return openTimeVisibility;
  }
  public void setOpenTimeVisibility(Boolean openTimeVisibility) {
    this.openTimeVisibility = openTimeVisibility;
  }

  public Boolean getCloseDateVisibility() {
    return closeDateVisibility;
  }
  public void setCloseDateVisibility(Boolean closeDateVisibility) {
    this.closeDateVisibility = closeDateVisibility;
  }

  public Boolean getCloseTimeVisibility() {
    return closeTimeVisibility;
  }
  public void setCloseTimeVisibility(Boolean closeTimeVisibility) {
    this.closeTimeVisibility = closeDateVisibility;
  }

  public Boolean getAllStudentsVisibility(){
    return allStudentsVisibility;
  }
  public void setAllStudentsVisibility(Boolean allStudentsVisibility) {
    this.allStudentsVisibility = allStudentsVisibility;
  }
  public String getEndDate(){
    return endDate;
  }
  public void setEndDate(String endDate){
    this.endDate=endDate;
  }
  public Boolean getTransferability() {
    return transferability;
  }
  public void setTransferability(Boolean transferability){
    this.transferability=transferability;
  }
  public String getCutoff() {
    return cutoff;
  }
  public void setCutoff(String cutoff) {
    this.cutoff = cutoff;
  }

  public Long getNumArrived() {
    return numArrived;
  }

  public void setNumArrived(Long numArrived) {
    this.numArrived = numArrived;
  }

}
