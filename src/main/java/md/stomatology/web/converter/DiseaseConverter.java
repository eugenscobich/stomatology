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
import md.stomatology.model.Disease;
import md.stomatology.service.AllergyService;
import md.stomatology.service.DiseaseService;
import md.stomatology.util.WebUtil;

@ManagedBean
public class DiseaseConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{diseaseService}")
	private transient DiseaseService diseaseService;

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return diseaseService.getDiseaseById(Long.parseLong(value));
			} catch (NumberFormatException e) {
				FacesMessage facesMessage = WebUtil.getMessage(FacesMessage.SEVERITY_ERROR, "conversion-error", "not-a-valid-disease");
				throw new ConverterException(facesMessage);
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null) {
			return String.valueOf(((Disease) object).getId());
		} else {
			return null;
		}
	}

	public DiseaseService getDiseaseService() {
		return diseaseService;
	}

	public void setDiseaseService(DiseaseService diseaseService) {
		this.diseaseService = diseaseService;
	}


}
