package com.pbarros.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Hibernate;
import org.primefaces.context.RequestContext;

import com.pbarros.model.PetModel;
import com.pbarros.repository.PetRepository;
import com.pbarros.repository.entity.PetEntity;
import com.pbarros.util.Message;

@Named(value="petController")
@RequestScoped
public class PetController {	
	@Inject
	private PetRepository petRepository;
	
	@Inject
	private PetModel pet;
	
	private List<PetModel> list = null;
	
	public List<PetModel> getList() {
		return list;
	}

	public PetModel getPet() {
		return pet;
	}
	
	public void setPet(PetModel pet) {
		this.pet = pet;
	}
	
	public void save() {
		boolean save = petRepository.save(this.pet);
		
		if(save) {
			this.pet = null;
			Message.mostrarMessage("Registro salvo com sucesso!");
		}else {
			Message.mostrarMessageErro("Ocorreu um erro ao salvar o pet!");
		}
	}
	
	public void load(PetModel pet) {
		this.pet = pet;
	}
	
	public void update() {		
		boolean update = petRepository.update(this.pet);
		
		if (update) {
			this.init();
		}else {
			Message.mostrarMessageErro("Ocorreu um erro ao atualizar o pet!");
		}
	}
		
	public void delete(PetModel item) {
		PetEntity i = petRepository.getItem(new Long(item.getCode()));
		Hibernate.initialize(i.getItensLcto());
		
		if (i.getItensLcto().size() > 0) {
			Message.exibirDialogErro("Não foi possível excluir o item.\n"
											+ "Existem pessoas que dependem deste item.");		
			return;
		}
		
		boolean removeu = petRepository.delete(item.getCode().intValue());
		
		if (!removeu) {
			Message.exibirDialogErro("Ocorreu um erro ao remover o pet!");
			return;
		}
		
		this.list.remove(item);
		

		Message.exibirDialogInfo("O item foi removido!");
	}
	
	@PostConstruct
	public void init(){
		this.list = petRepository.getAll();
	}
}
