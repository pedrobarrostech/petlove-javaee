package com.pbarros.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.pbarros.model.PersonItemModel;

@Table(name="personItem") 
@Entity 
@NamedQueries({
	@NamedQuery(name="PersonItemEntity.findByLanc", 
			    query = "SELECT l FROM PersonItemEntity l WHERE l.person = :person")
})
public class PersonItemEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oid")
	private Long oid; 

	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "oid_person") 
	private PersonEntity person; 


	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "oid_item") 
	private PetEntity pet; 

	public PersonItemEntity() {}
	
	public PersonItemEntity(PersonItemModel lm) {
		this.oid = lm.getcode();
		this.pet = new PetEntity(lm.getItem());
		this.person = new PersonEntity(lm.getPerson());
	}
	
	public PersonEntity getPerson() { 
		return person; 
	} 


	public void setPerson(PersonEntity person) { 
		this.person = person; 
	} 


	public PetEntity getPet() { 
		return pet; 
	} 


	public void setPet(PetEntity pet) { 
		this.pet = pet; 
	}


	public Long getOid() {
		return oid;
	}


	public void setOid(Long oid) {
		this.oid = oid;
	} 

	
}