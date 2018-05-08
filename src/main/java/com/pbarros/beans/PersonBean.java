package com.pbarros.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Hibernate;
import org.primefaces.context.RequestContext;

import com.pbarros.model.PetModel;
import com.pbarros.model.PersonModel;
import com.pbarros.repository.PetRepository;
import com.pbarros.repository.PersonItemRepository;
import com.pbarros.repository.PersonRepository;
import com.pbarros.repository.entity.PetEntity;
import com.pbarros.repository.entity.PersonEntity;
import com.pbarros.repository.entity.PersonItemEntity;
import com.pbarros.util.Message;

@Named
@ViewScoped
public class PersonBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private PersonModel person;
	private List<PetModel> itemsPerson;
	private List<PersonItemEntity> savedItems;
	private List<PetModel> pets;
	private PetModel pet;
		
	@Inject
	private PetRepository petRepository;
	
	@Inject
	private PersonRepository lctoRepository;
	
	@Inject
	private PersonItemRepository lcitRepository;

	public PersonModel getPerson() {
		return person;
	}

	public void setPerson(PersonModel person) {
		this.person = person;
	}

	public List<PetModel> getItemsPerson() {
		return itemsPerson;
	}

	public void setItemsPerson(List<PetModel> itemsPerson) {
		this.itemsPerson = itemsPerson;
	}

	public List<PetModel> getPets() {
		return pets;
	}

	public void setPets(List<PetModel> pets) {
		this.pets = pets;
	}
	
	public PetModel getPet() {
		return pet;
	}

	public void setPet(PetModel pet) {
		this.pet = pet;
	}
	
	public List<PersonItemEntity> getSavedItems() {
		return savedItems;
	}

	public void setSavedItems(List<PersonItemEntity> savedItems) {
		this.savedItems = savedItems;
	}

	public PersonBean() {
		person = new PersonModel();
		itemsPerson = new ArrayList<PetModel>();
	}
	

	public void novoItem() {
	}
	
	public void adicionarItem() {
		if (pet != null) {
			itemsPerson.add(pet);
			setItemsPerson(this.itemsPerson);
			}
	}
    	
	public void removeItem(PetModel item) {		
		this.itemsPerson.remove(item);
		}
	
	public void save() {
		PersonEntity lcSalvo = lctoRepository.save(person);
		if (lcSalvo == null) {
			Message.mostrarMessageErro("Erro ao salvar o lançamento!");
			return;					
		}
		
		boolean salvouItens = lcitRepository.save(itemsPerson, lcSalvo);
		if (!salvouItens) {
			Message.mostrarMessageErro("Erro ao adicionar os itens!");
			return;			
		}
		
		init();
		
		Message.mostrarMessage("Lançamento cadastrado com sucesso");	
	}
	
	public void atualizar(PersonModel person) {
		this.person = person;
		itemsPerson = new ArrayList<PetModel>();
		savedItems = lcitRepository.getItemByPersonE(new PersonEntity(person));	
	}

	public void removeItemQdoLctoExiste(PersonItemEntity item) {
		if(item.getOid() == null) {
			itemsPerson.removeIf(p -> p.getCode() == item.getPet().getOid().intValue());
		}else {	
			if (!lcitRepository.delete(item.getOid())) {
				Message.mostrarMessageErro("Ocorreu um erro ao remover esse item");
				return;
			}
		}
		
		if(savedItems.size() > 0) {
			this.savedItems.remove(item);	
		}

	}
	
	public void adicionarItemQdoLctoExiste() {
		if (pet != null) {
			PersonItemEntity le = new PersonItemEntity();
			le.setPet(new PetEntity(pet));
			le.setPerson(new PersonEntity(person));

			savedItems.add(le);
			itemsPerson.add(pet);
		}
	}
	
	public void update() {
		boolean atualizou = lctoRepository.update(person);
		
		if(!atualizou) {			
			Message.mostrarMessageErro("Ocorreu um erro ao atualizar o lançamento");
			return;
		}

		if(itemsPerson.size() > 0) {
			PersonEntity lctoSalvo = lctoRepository.getLcto(person.getOid());
			
			boolean salvouItens = lcitRepository.save(itemsPerson, lctoSalvo);
			if (!salvouItens) {
				Message.mostrarMessageErro("Erro ao adicionar os itens novos no lançamento!");
				return;			
			}
		}
	}

	@PostConstruct
	public void init(){
		pets = petRepository.getAll();
		pet = new PetModel();
		itemsPerson = new ArrayList<PetModel>();
		savedItems = new ArrayList<PersonItemEntity>();
		person = new PersonModel();
	}
}
