package com.pbarros.repository;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.pbarros.model.PetModel;
import com.pbarros.repository.entity.PetEntity;
import com.pbarros.util.EntityManagerProd;

public class PetRepository implements Serializable{	
	@Inject
	private PetEntity petEntity;
		
	private EntityManager entityManager;
	
	/**
	 * Salvar um item no BD
	 * @param itemModel
	 */
	public boolean save(PetModel item) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		petEntity = new PetEntity(item);
		
		try {
			entityManager.persist(petEntity);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public PetEntity getItem(Long code) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		return entityManager.find(PetEntity.class,  code);		
	}
	
	public boolean update(PetModel pet) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		PetEntity petNew = this.getItem(new Long(pet.getCode()));	
		
		petNew.setDescription(pet.getDescription());
		petNew.setValue(pet.getValue());
		try {
			entityManager.merge(petNew);		
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean delete(int code) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		PetEntity petNew = this.getItem(new Long(code));
		try {
			entityManager.remove(petNew);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	

	public List<PetModel> getAll(){
		List<PetModel> lista = new ArrayList<PetModel>();
		
		entityManager = EntityManagerProd.JpaEntityManager();
		
		Query query = entityManager.createNamedQuery("PetEntity.findAll");
		
		for  (PetEntity itemE: (List<PetEntity>)query.getResultList() ) {
			PetModel itemM = new PetModel(itemE);
			lista.add(itemM);
		}
		
		return lista;		
	}

}
