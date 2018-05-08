package com.pbarros.model;

import com.pbarros.repository.entity.PersonEntity;

public class PersonModel { 
  private Long oid; 
  private String name;
  
  public PersonModel(PersonEntity e) {
	  this.oid = e.getOid();
	  this.name = e.getName();
  }
  
  public PersonModel() {}
  
  public Long getOid() { 
    return oid; 
  } 

  public void setOid(Long oid) { 
    this.oid = oid; 
  } 

  public String getName() { 
    return name; 
  } 

  public void setName(String name) { 
    this.name = name; 
  }   
} 
