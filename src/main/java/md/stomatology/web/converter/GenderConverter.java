package md.stomatology.web.converter;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.springframework.ui.context.Theme;

import md.stomatology.model.Allergy;
import md.stomatology.model.type.GenderType;
import md.stomatology.service.AllergyService;
import md.stomatology.util.WebUtil;

@ManagedBean
public class GenderConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && value.trim().length() > 0) {
			return Boolean.valueOf(value) ? GenderType.MALE : GenderType.FEMALE;
			
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null) {
			return Boolean.toString((GenderType)object == GenderType.MALE);
		} else {
			return null;
		}
	}
}
