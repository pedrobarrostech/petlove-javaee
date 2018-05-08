package com.pbarros.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.pbarros.model.PersonModel;

@Table(name="person") 
@NamedQueries({
	@NamedQuery(name="PersonEntity.findAll", query = "SELECT l FROM PersonEntity l")
})
@Entity 
public class PersonEntity { 
  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY) 
  @Column(name = "oid") 
  private Long oid; 

  @Column(name = "name", length = 1000) 
  private String name; 
    
  public PersonEntity() {}
  
  public PersonEntity(PersonModel l) {
	  this.name = l.getName();
	  this.oid = l.getOid();
  }
 
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