package com.pbarros.repository.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pbarros.model.PetModel;

@Entity
@Table(name = "pet")
@NamedQueries({ @NamedQuery(name = "PetEntity.findAll", query = "SELECT i FROM PetEntity i") })

public class PetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oid")
	private Long oid;

	@Column(name = "description", length = 255)
	private String description;

	@Column(name = "value", precision = 8, scale = 2)
	private BigDecimal value;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pet")
	private List<PersonItemEntity> itensLcto;

	public PetEntity() {
	}

	public PetEntity(PetModel im) {
		if (im.getCode() != null)
			this.oid = new Long(im.getCode());
		this.description = im.getDescription();
		this.value = im.getValue();
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

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

	public List<PersonItemEntity> getItensLcto() {
		return itensLcto;
	}

	public void setItensLcto(List<PersonItemEntity> itensLcto) {
		this.itensLcto = itensLcto;
	}

}
