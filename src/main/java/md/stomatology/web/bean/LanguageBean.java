package md.stomatology.web.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@SessionScoped
public class LanguageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String localeCode;

	private static Map<String, Locale> countries;

	static {
		countries = new LinkedHashMap<String, Locale>();
		countries.put("en_GB", Locale.ENGLISH); // label, value
		countries.put("ru_RU", new Locale("ru", "RU"));
		countries.put("ro_RO", new Locale("ro", "RO"));
	}

	public Map<String, Locale> getCountriesInMap() {
		return countries;
	}

	public String getLocaleCode() {
		return FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String changeLocale(String locale) {
		for (Map.Entry<String, Locale> entry : countries.entrySet()) {
			if (entry.getKey().equals(locale)) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale(entry.getValue());
			}
		}
		return "";
	}

}