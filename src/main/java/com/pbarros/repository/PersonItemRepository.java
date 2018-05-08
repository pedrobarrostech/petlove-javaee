package com.pbarros.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.pbarros.model.PetModel;
import com.pbarros.model.PersonItemModel;
import com.pbarros.repository.entity.PetEntity;
import com.pbarros.repository.entity.PersonEntity;
import com.pbarros.repository.entity.PersonItemEntity;
import com.pbarros.util.EntityManagerProd;

public class PersonItemRepository  implements Serializable{	
	private static final long serialVersionUID = 1L;

	@Inject
	private PersonItemEntity personItemEntity;
		
	private EntityManager entityManager;
	
	public boolean save(List<PetModel> lista, PersonEntity person) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		for(PetModel i:lista) {
			PersonItemEntity li = new PersonItemEntity();
			li.setPerson(person);
			
			PetEntity pet = new PetEntity(i);			
			li.setPet(pet);
			
			try {
				entityManager.persist(li);
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	public List<PersonItemModel> getItemByPerson(PersonEntity person){
		List<PersonItemModel> lista = new ArrayList<PersonItemModel>();
		
		entityManager = EntityManagerProd.JpaEntityManager();
		
		Query query = entityManager.createNamedQuery("PersonItemEntity.findByLanc");
		query.setParameter("person", person);
		
		for  (PersonItemEntity lancItemEnt: (List<PersonItemEntity>)query.getResultList() ) {
			PersonItemModel lancItemMod = new PersonItemModel(lancItemEnt);
			
			lista.add(lancItemMod);
		}
		
		return lista;		
	}
	
	public List<PersonItemEntity> getItemByPersonE(PersonEntity person){		
		entityManager = EntityManagerProd.JpaEntityManager();
		
		Query query = entityManager.createNamedQuery("PersonItemEntity.findByLanc");
		query.setParameter("person", person);
		
		return (List<PersonItemEntity>) query.getResultList();
	}

	public boolean delete(PersonItemEntity le) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		try {
			entityManager.remove(le);	
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean delete(Long id) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		try {
			PersonItemEntity liRemover = entityManager.find(PersonItemEntity.class, id);
			
			entityManager.remove(liRemover);	
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
