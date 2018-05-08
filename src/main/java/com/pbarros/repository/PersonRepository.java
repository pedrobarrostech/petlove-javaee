package com.pbarros.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.pbarros.model.PetModel;
import com.pbarros.model.PersonModel;
import com.pbarros.repository.entity.PetEntity;
import com.pbarros.repository.entity.PersonEntity;
import com.pbarros.util.EntityManagerProd;

public class PersonRepository  implements Serializable{	
	@Inject
	private PersonEntity personEntity;
		
	private EntityManager entityManager;
	
	public List<PersonModel> getAll(){
		List<PersonModel> lista = new ArrayList<PersonModel>();
		
		entityManager = EntityManagerProd.JpaEntityManager();
		
		Query query = entityManager.createNamedQuery("PersonEntity.findAll");
		
		for  (PersonEntity lctoE: (List<PersonEntity>)query.getResultList() ) {
			PersonModel lctoM = new PersonModel(lctoE);
			
			lista.add(lctoM);
		}
		
		return lista;		
	}
	
	public PersonEntity save(PersonModel lc) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		PersonEntity personEntity = new PersonEntity(lc);
		
		entityManager.persist(personEntity);
		entityManager.flush();
		
		return personEntity;
	}
	
	public PersonEntity getLcto(Long code) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		return entityManager.find(PersonEntity.class,  code);		
	}
	
	public boolean delete(int code) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		PersonEntity itemE = this.getLcto(new Long(code));
		try {
			entityManager.remove(itemE);	
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean update(PersonModel lcto) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		PersonEntity lctoE = this.getLcto(lcto.getOid());
		lctoE.setName(lcto.getName());
		try {
			entityManager.merge(lctoE);	
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
