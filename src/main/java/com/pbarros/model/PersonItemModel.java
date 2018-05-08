package com.pbarros.model;

import com.pbarros.repository.entity.PersonItemEntity;

public class PersonItemModel { 
	  private Long code; 	
	  private PersonModel person; 
	  private PetModel pet; 
	 
	  public PersonItemModel() {}
	  
	  public PersonItemModel(PersonItemEntity lancitem) {
		  this.code = lancitem.getOid();
		  this.person = new PersonModel(lancitem.getPerson());
		  this.pet = new PetModel(lancitem.getPet());
	  }
	  
	  public PersonModel getPerson() { 
	    return person; 
	  } 
	  
	  public void setPerson(PersonModel person) { 
	    this.person = person; 
	  } 
	  
	  public PetModel getItem() { 
	    return pet; 
	  } 
	  
	  public void setItem(PetModel pet) { 
	    this.pet = pet; 
	  }

	  public Long getcode() {
		return code;
	  }
	
	  public void setcode(Long code) {
		this.code = code;
	  }  
} 