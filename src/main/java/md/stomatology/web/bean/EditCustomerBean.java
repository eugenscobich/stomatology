package md.stomatology.web.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

import md.stomatology.model.Allergy;
import md.stomatology.model.Customer;
import md.stomatology.model.PastIllnesse;
import md.stomatology.model.User;
import md.stomatology.service.AllergyService;
import md.stomatology.service.CustomerService;
import md.stomatology.service.PastIllnesseService;
import md.stomatology.service.UserService;
import md.stomatology.util.WebUtil;

@ManagedBean
@ViewScoped
@URLMapping(id = "edit-customer", pattern = "/edit-customer/#{/[0-9]+/ customerId : editCustomerBean.customerId}", viewId = "/WEB-INF/views/pages/secure/edit-customer.xhtml")
public class EditCustomerBean implements Serializable {

	private static Logger LOG = LoggerFactory.getLogger(LoginBean.class);

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{customerService}")
	private transient CustomerService customerService;

	@ManagedProperty(value = "#{allergyService}")
	private transient AllergyService allergyService;
	
	@ManagedProperty(value = "#{userService}")
	private transient UserService userService;

	@ManagedProperty(value = "#{pastIllnesseService}")
	private transient PastIllnesseService pastIllnesseService;

	private Long customerId;

	private Customer customer;

	@URLAction(onPostback = false)
	public String loadCustomer() {
		LOG.info("loadCustomer");
		if (customerId != null) {
			if (customerId != 0) {
				try {
					customer = customerService.getCustomerById(customerId);
				} catch (Exception e) {
					WebUtil.addErrorMessage("could-not-load-customer-details", "error", e.getMessage());
					return "pretty:customer-list";
				}
			} else {
				User currentUser = WebUtil.getCurrentUser();
				customer = customerService.createNewCustomer(currentUser);
			}
			return null;
		}
		WebUtil.addWarnMessage("could-not-load-customer-details", "");
		return "pretty:customer-list";
	}

	public String save() {
		try {
			customerService.saveCustomer(customer);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			WebUtil.addErrorMessage("could-not-save-customer", "error", new String[]{ e.getMessage() });
			return "";
		}
		WebUtil.addSuccessMessage("customer-has-saved-successfully");
		return "pretty:customer-list";
	}
	
	public List<Allergy> completeAllergy(String query) {
		List<Allergy> allergies = allergyService.getAllergies(query, customer.getAllergies());
		return allergies;
	}

	public void handleSelectAllergy(SelectEvent event) {
		Allergy allergy = (Allergy) event.getObject();
		customer.getAllergies().add(allergy);
	}

	public void handleUnselectAllergy(UnselectEvent event) {
		Allergy allergy = (Allergy) event.getObject();
		customer.getAllergies().remove(allergy);
	}

	public List<PastIllnesse> completePastIllnesse(String query) {
		List<PastIllnesse> pastIllnesse = pastIllnesseService.getPastIllnesses(query, customer.getPastIllnesses());
		return pastIllnesse;
	}
	
	public void handleSelectPastIllnesse(SelectEvent event) {
		PastIllnesse pastIllnesse = (PastIllnesse) event.getObject();
		customer.getPastIllnesses().add(pastIllnesse);
	}

	public void handleUnselectPastIllnesse(UnselectEvent event) {
		PastIllnesse pastIllnesse = (PastIllnesse) event.getObject();
		customer.getPastIllnesses().remove(pastIllnesse);
	}
	
	public List<User> completeDentists(String query) {
		List<User> users = userService.getDentists(query, customer.getDentist());
		return users;
	}

	public void handleSelectDentist(SelectEvent event) {
		User dentist = (User) event.getObject();
		customer.setDentist(dentist);
	}

	public void handleUnselectDentist(UnselectEvent event) {
		customer.setDentist(null);
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public AllergyService getAllergyService() {
		return allergyService;
	}

	public void setAllergyService(AllergyService allergyService) {
		this.allergyService = allergyService;
	}

	public PastIllnesseService getPastIllnesseService() {
		return pastIllnesseService;
	}

	public void setPastIllnesseService(PastIllnesseService pastIllnesseService) {
		this.pastIllnesseService = pastIllnesseService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
