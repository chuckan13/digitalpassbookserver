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
public class Organization extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  // @NotEmpty
  private String name;

  // @NotEmpty 
  private String password;

  // @NotEmpty
  private String logo;

  // @NotEmpty
  private String signin;

  // @NotEmpty
  private String email;

  private String allocation;
  private String defaultEventName;
  private String defaultEventDescription;
  private String defaultEventLocation;
  private boolean defaultTransferability;
  private boolean defaultOpenTimeVisibility;
  private boolean defaultCloseDateVisibility;
  private boolean defaultCloseTimeVisibility;
  private boolean defaultAllStudentsVisibility;

  private long defaultPassesPerMember;
  private long defaultPassesPerSenior;
  private long defaultPassesPerJunior;
  private long defaultPassesPerSophomore;


  @Type(type = "string-array")
  @Column(
      name = "perm_guest_list",
      columnDefinition = "text[]"
  )
  @JsonFormat(shape=JsonFormat.Shape.ARRAY)
  private String[] permGuestList;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public String getLogo() {
    return logo;
  }

  public String getSignin() {
    return signin;
  }

  public String getEmail() {
    return email;
  }

  public String[] getPermGuestList() {
    return permGuestList;
  }

  public String getDefaultEventName() {
    return defaultEventName;
  }

  public String getDefaultEventDescription() {
    return defaultEventDescription;
  }

  public String getDefaultEventLocation() {
    return defaultEventLocation;
  }

  public Boolean getDefaultTransferability() {
    return defaultTransferability;
  }

  public Boolean getDefaultOpenTimeVisibility() {
    return defaultOpenTimeVisibility;
  }

  public Boolean getDefaultCloseDateVisibility() {
    return defaultCloseDateVisibility;
  }

  public Boolean getDefaultCloseTimeVisibility() {
    return defaultCloseTimeVisibility;
  }

  public Boolean getDefaultAllStudentsVisibility() {
    return defaultAllStudentsVisibility;
  }

  public Long getDefaultPassesPerMember() {
    return defaultPassesPerMember;
  }

  public Long getDefaultPassesPerSenior() {
    return defaultPassesPerSenior;
  }

  public Long getDefaultPassesPerJunior() {
    return defaultPassesPerJunior;
  }

  public Long getDefaultPassesPerSophomore() {
    return defaultPassesPerSophomore;
  }

  public String getAllocation() {
    return allocation;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public void setSignin(String signin) {
    this.signin = signin;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPermGuestList(String[] permGuestList) {
    this.permGuestList = permGuestList;
  }

  public void setAllocation(String allocation) {
    this.allocation = allocation;
  }

  public void setDefaultEventName(String defaultEventName) {
    this.defaultEventName = defaultEventName;
  }

  public void setDefaultEventDescription(String defaultEventDescription) {
    this.defaultEventDescription = defaultEventDescription;
  }

  public void setDefaultEventLocation(String defaultEventLocation) {
    this.defaultEventLocation = defaultEventLocation;
  }

  public void setDefaultTransferability(Boolean defaultTransferability) {
    this.defaultTransferability = defaultTransferability;
  }

  public void setDefaultOpenTimeVisibility(Boolean defaultOpenTimeVisibility) {
    this.defaultOpenTimeVisibility = defaultOpenTimeVisibility;
  }

  public void setDefaultCloseDateVisibility(Boolean defaultCloseDateVisibility) {
    this.defaultCloseDateVisibility = defaultCloseDateVisibility;
  }

  public void setDefaultCloseTimeVisibility(Boolean defaultCloseTimeVisibility) {
    this.defaultCloseTimeVisibility = defaultCloseTimeVisibility;
  }

  public void getDefaultAllStudentsVisibility(Boolean defaultAllStudentsVisibility) {
    this.defaultAllStudentsVisibility = defaultAllStudentsVisibility;
  }

  public void setDefaultPassesPerMember(long defaultPassesPerMember) {
    this.defaultPassesPerMember = defaultPassesPerMember;
  }

  public void setDefaultPassesPerSenior(long defaultPassesPerSenior) {
    this.defaultPassesPerSenior = defaultPassesPerSenior;
  }

  public void setDefaultPassesPerJunior(long defaultPassesPerJunior) {
    this.defaultPassesPerJunior = defaultPassesPerJunior;
  }

  public void setDefaultPassesPerSophomore(long defaultPassesPerSophomore) {
    this.defaultPassesPerSophomore = defaultPassesPerSophomore;
  }

  public void updateParameters(Organization other) {
    this.name = other.getName();
    this.password = other.getPassword();
    this.logo = other.getLogo();
    this.signin = other.getSignin();
    this.email = other.getEmail();
    this.permGuestList = other.getPermGuestList();
    this.defaultEventName = other.getDefaultEventName();
    this.defaultEventDescription = other.getDefaultEventDescription();
    this.defaultEventLocation = other.getDefaultEventLocation();
    this.defaultOpenTimeVisibility = other.getDefaultOpenTimeVisibility();
    this.defaultCloseDateVisibility = other.getDefaultCloseDateVisibility();
    this.defaultCloseTimeVisibility = other.getDefaultCloseTimeVisibility();
    this.defaultAllStudentsVisibility = other.getDefaultAllStudentsVisibility();
    this.allocation = other.getAllocation();
    this.defaultPassesPerMember = other.getDefaultPassesPerMember();
    this.defaultPassesPerSenior = other.getDefaultPassesPerSenior();
    this.defaultPassesPerJunior = other.getDefaultPassesPerJunior();
    this.defaultPassesPerSophomore = other.getDefaultPassesPerSophomore();
  }

}
