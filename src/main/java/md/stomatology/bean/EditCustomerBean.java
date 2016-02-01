package md.stomatology.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.springframework.dao.DataAccessException;

import md.stomatology.model.Customer;
import md.stomatology.model.User;
import md.stomatology.service.CustomerService;
import md.stomatology.util.WebUtil;

@ManagedBean
@ViewScoped
public class EditCustomerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value="#{CustomerService}")
    CustomerService customerService;

    private Long customerId;
    
    private Customer customer;
 
    @PostConstruct
    public void init() {
    	customerId = WebUtil.getRequestParam("customerId", Long.class);
    	if (customerId != null) {
    		customer = 	customerService.getCustomerById(customerId);
    	} else {
    		User currentUser = WebUtil.getCurrentUser();
    		customer = customerService.createNewCustomer(currentUser);	
    	}
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

