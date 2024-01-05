package com.stonedt.entity;

public class OrganizationEntity {
  private Integer id;
  
  private String create_time;
  
  private String organization_id;
  
  private String organization_short;
  
  private String organization_name;
  
  private Integer organization_type;
  
  private String term_of_validity;
  
  private Integer status;
  
  private String organization_code;
  
  private String logo_url;
  
  private String system_title;
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getCreate_time() {
    return this.create_time;
  }
  
  public void setCreate_time(String create_time) {
    this.create_time = create_time;
  }
  
  public String getOrganization_id() {
    return this.organization_id;
  }
  
  public void setOrganization_id(String organization_id) {
    this.organization_id = organization_id;
  }
  
  public String getOrganization_short() {
    return this.organization_short;
  }
  
  public void setOrganization_short(String organization_short) {
    this.organization_short = organization_short;
  }
  
  public String getOrganization_name() {
    return this.organization_name;
  }
  
  public void setOrganization_name(String organization_name) {
    this.organization_name = organization_name;
  }
  
  public Integer getOrganization_type() {
    return this.organization_type;
  }
  
  public void setOrganization_type(Integer organization_type) {
    this.organization_type = organization_type;
  }
  
  public String getTerm_of_validity() {
    return this.term_of_validity;
  }
  
  public void setTerm_of_validity(String term_of_validity) {
    this.term_of_validity = term_of_validity;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public String getOrganization_code() {
    return this.organization_code;
  }
  
  public void setOrganization_code(String organization_code) {
    this.organization_code = organization_code;
  }
  
  public String getLogo_url() {
    return this.logo_url;
  }
  
  public void setLogo_url(String logo_url) {
    this.logo_url = logo_url;
  }
  
  public String getSystem_title() {
    return this.system_title;
  }
  
  public void setSystem_title(String system_title) {
    this.system_title = system_title;
  }
}
