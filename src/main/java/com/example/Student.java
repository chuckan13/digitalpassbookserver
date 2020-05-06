package com.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import org.hibernate.annotations.Type;

@Entity
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private long orgId;

  @NotEmpty
  private String name;

  @NotEmpty
  private String netId;

  // private String userName;
  
  @NotEmpty
  private String password;

  // @NotEmpty
  private String email;

  private String role;

  private boolean princetonStudent;

  private long defaultPasses;

  private String barCodeNumber;

  private long graduatingClass;

  @Type(type = "int-array")
  @Column(
      name = "bouncing_events",
      columnDefinition = "integer[]"
  )
  @JsonFormat(shape=JsonFormat.Shape.ARRAY)
  private int[] bouncingEvents;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getNetID() {
    return netId;
  }

  public String getPassword() {
    return password;
  }

  public Long getOrgId() {
    return orgId;
  }

  public String getEmail() {
    return email;
  }

  public String getRole() {
    return role;
  }

  // public String getUserName() {
  //   return userName;
  // }

  public Boolean getPrincetonStudent() {
    return princetonStudent;
  }

  public Long getDefaultPasses() {
    return defaultPasses;
  }

  public String getBarCodeNumber() {
    return barCodeNumber;
  }

  public Long getGraduatingClass() {
    return graduatingClass;
  }

  public int[] getBouncingEvents() {
    return bouncingEvents;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setNetID(String netId) {
    this.netId = netId;
  }

  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setRole(String role) {
    this.role = role;
  }

  // public void setUserName(String userName) {
  //   this.userName = userName;
  // }

  public void setPrincetonStudent(Boolean princetonStudent) {
    this.princetonStudent = princetonStudent;
  }

  public void setDefaultPasses(Long defaultPasses) {
    this.defaultPasses = defaultPasses;
  }

  public void setBarCodeNumber(String barCodeNumber) {
    this.barCodeNumber = barCodeNumber;
  }

  public void setGraduatingClass(Long graduatingClass) {
    this.graduatingClass = graduatingClass;
  }

  public void setBouncingEvents(int[] bouncingEvents) {
    this.bouncingEvents = bouncingEvents;
  }

  public void updateParameters(Student other) {
    this.name = other.getName();
    this.netId = other.getNetID();
    this.password = other.getPassword();
    this.orgId = other.getOrgId();
    this.email = other.getEmail();
    this.role = other.getRole();
    // this.userName = other.getUserName();
    this.princetonStudent = other.getPrincetonStudent();
    this.defaultPasses = other.getDefaultPasses();
    this.graduatingClass = other.getGraduatingClass();
    this.barCodeNumber = other.getBarCodeNumber();
    this.bouncingEvents = other.getBouncingEvents();
  }
}
