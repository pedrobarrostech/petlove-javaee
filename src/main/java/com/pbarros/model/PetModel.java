package com.pbarros.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pbarros.repository.entity.PetEntity;
import com.pbarros.repository.entity.PersonItemEntity;

public class PetModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer code;
	private String description;
	private BigDecimal value;
	private List<PersonItemEntity> itensLcto;
	
	public PetModel(PetEntity i) {
		if (i.getOid() != null)
			this.code = i.getOid().intValue();
		this.description = i.getDescription();
		this.value = i.getValue();
		this.itensLcto = i.getItensLcto();
	}
	
	public PetModel() {}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	public List<PersonItemEntity> getItensLcto() {
		return itensLcto;
	}

	public void setItensLcto(List<PersonItemEntity> itensLcto) {
		this.itensLcto = itensLcto;
	}

	@Override
	public boolean equals(Object other){ 
		return other instanceof PetModel && (code != null) ? code.equals(((PetModel) other).code) : (other == this);
	}
	@Override
	public int hashCode(){
		return code != null ? this.getClass().hashCode() + code.hashCode() : super.hashCode();
	}
	@Override
	public String toString(){
		return this.description;
	}
	
}