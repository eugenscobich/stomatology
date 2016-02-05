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
import md.stomatology.service.AllergyService;
import md.stomatology.util.WebUtil;

@ManagedBean
public class AllergyConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{allergyService}")
	private transient AllergyService allergyService;

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return allergyService.getAllergyById(Long.parseLong(value));
			} catch (NumberFormatException e) {
				FacesMessage facesMessage = WebUtil.getMessage(FacesMessage.SEVERITY_ERROR, "conversion-error", "not-a-valid-allergy");
				throw new ConverterException(facesMessage);
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null) {
			return String.valueOf(((Allergy) object).getId());
		} else {
			return null;
		}
	}

	public AllergyService getAllergyService() {
		return allergyService;
	}

	public void setAllergyService(AllergyService allergyService) {
		this.allergyService = allergyService;
	}
}
