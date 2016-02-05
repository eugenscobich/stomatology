package md.stomatology.web.converter;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import md.stomatology.model.Allergy;
import md.stomatology.model.PastIllnesse;
import md.stomatology.service.PastIllnesseService;
import md.stomatology.util.WebUtil;

@ManagedBean
public class PastIllnesseConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{pastIllnesseService}")
	private transient PastIllnesseService pastIllnesseService;

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return pastIllnesseService.getPastIllnesseById(Long.parseLong(value));
			} catch (NumberFormatException e) {
				FacesMessage facesMessage = WebUtil.getMessage(FacesMessage.SEVERITY_ERROR, "conversion-error",
						"not-a-valid-allergy");
				throw new ConverterException(facesMessage);
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null) {
			return String.valueOf(((PastIllnesse) object).getId());
		} else {
			return null;
		}
	}

	public PastIllnesseService getPastIllnesseService() {
		return pastIllnesseService;
	}

	public void setPastIllnesseService(PastIllnesseService pastIllnesseService) {
		this.pastIllnesseService = pastIllnesseService;
	}

}
