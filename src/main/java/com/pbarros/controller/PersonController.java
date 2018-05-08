package com.pbarros.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.context.RequestContext;

import com.pbarros.model.PetModel;
import com.pbarros.model.PersonItemModel;
import com.pbarros.model.PersonModel;
import com.pbarros.repository.PetRepository;
import com.pbarros.repository.PersonItemRepository;
import com.pbarros.repository.PersonRepository;
import com.pbarros.repository.entity.PersonEntity;
import com.pbarros.repository.entity.PersonItemEntity;
import com.pbarros.util.Message;

@Named(value="personController")
@RequestScoped
public class PersonController {  

	@Inject  
	private PersonRepository personRepository;
	
	@Inject
	private PetRepository petRepository;
	
	@Inject
	private PersonModel person;  
	
	@Inject
	private PersonItemRepository lcitRepository;
	
	private List<PersonModel> lista = null;
	private List<PersonItemModel> listaSavedItemss;
	
	public List<PersonModel> getLista() {
		return lista;
	}
	
	public PersonModel getPerson() {
		return person;
	}
	
	public void setPerson(PersonModel person) {
		this.person = person;
	}
	
	public List<PersonItemModel> getListaSavedItemss() {
		return listaSavedItemss;
	}
	
	public void setListaSavedItemss(List<PersonItemModel> listaSavedItemss) {
		this.listaSavedItemss = listaSavedItemss;
	}
	
	
	public void removerPerson(PersonModel person) {
		List<PersonItemEntity> listaRemover = lcitRepository.getItemByPersonE(new PersonEntity(person));
	
		boolean removeu = true;
		for(PersonItemEntity le:listaRemover) {
			removeu = lcitRepository.delete(le);
			if (!removeu) {
				Message.exibirDialogErro("Erro ao remover o lançamento.");
				return;
			}
		}

		if (!personRepository.delete(person.getOid().intValue())) {
			Message.exibirDialogErro("Ocorreu um erro ao remover o lançamento!");
			return;
		}
		
		this.lista.remove(person);
		
		Message.exibirDialogInfo("O lançamento foi removido!");		
	}
	
	public void mostrarPerson(PersonModel person) {
		this.person = person;
		listaSavedItemss = lcitRepository.getItemByPerson(new PersonEntity(person)); 
	}
	
	@PostConstruct
	public void init(){
		this.lista = personRepository.getAll();
		this.listaSavedItemss = new ArrayList<PersonItemModel>();
	}  
	
	public void handlePerson(PersonModel lc) {
		this.person = lc;
	}
}