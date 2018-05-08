package com.pbarros.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.pbarros.model.PetModel;
import com.pbarros.repository.PetRepository;
import com.pbarros.repository.entity.PetEntity;

@FacesConverter(value = "itemConverter")
public class ItemConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent uiComponent, String value) {
        if(value != null || !value.isEmpty()){ 
        	return (PetModel) uiComponent.getAttributes().get(value);
        }
        
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent uiComponent, Object value) { 
        if (value instanceof PetModel) {
        	PetModel entity= (PetModel) value;
        	
            if (entity != null && entity instanceof PetModel && entity.getCode() != null) {
                uiComponent.getAttributes().put( entity.getCode().toString(), entity);
                return entity.getCode().toString();
            }
            
        }
        return "";
    }

}
