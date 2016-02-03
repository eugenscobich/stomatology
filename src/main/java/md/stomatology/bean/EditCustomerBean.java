package md.stomatology.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

import md.stomatology.model.Customer;
import md.stomatology.model.User;
import md.stomatology.service.CustomerService;
import md.stomatology.util.WebUtil;

@ManagedBean
@ViewScoped
@URLMapping(id = "edit-customer", pattern = "/edit-customer/#{customerId : editCustomerBean.customerId}", viewId = "/WEB-INF/views/pages/secure/edit-customer.xhtml")
public class EditCustomerBean implements Serializable {

	private static Logger LOG = LoggerFactory.getLogger(LoginBean.class);
	
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value="#{CustomerService}")
    CustomerService customerService;

    private Long customerId;
    
    private Customer customer;
 
    @URLAction
    public String loadCustomer() {
    	LOG.info("loadCustomer");
		if (customerId != null) {
			if (customerId != 0) {
				customer = customerService.getCustomerById(customerId);
			} else {
				User currentUser = WebUtil.getCurrentUser();
	    		customer = customerService.createNewCustomer(currentUser);	
			}
			return null;
		}

		WebUtil.addWarnMessage("could-not-load-customer-details", "", "");
		return "pretty:customer-list";
	}
    
    public void editCustomer(Customer customer) {
    	
    }
    
    public void removeCustomer(Customer customer) {
    	
    }
    
    private void newCustomer() {
    	
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

}

