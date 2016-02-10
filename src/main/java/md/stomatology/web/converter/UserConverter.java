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
import md.stomatology.model.User;
import md.stomatology.model.type.GenderType;
import md.stomatology.service.AllergyService;
import md.stomatology.service.UserService;
import md.stomatology.util.WebUtil;

@ManagedBean
public class UserConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{userService}")
	private transient UserService userService;

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return userService.getUserById(Long.parseLong(value));
			} catch (NumberFormatException e) {
				FacesMessage facesMessage = WebUtil.getMessage(FacesMessage.SEVERITY_ERROR, "conversion-error", "not-a-valid-user");
				throw new ConverterException(facesMessage);
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null) {
			return String.valueOf(((User) object).getId());
		} else {
			return null;
		}
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	}
