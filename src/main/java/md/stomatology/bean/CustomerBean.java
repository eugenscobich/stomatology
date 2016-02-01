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
import md.stomatology.service.CustomerService;

@ManagedBean
@ViewScoped
public class CustomerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value="#{CustomerService}")
    CustomerService customerService;

    private List<Customer> customers;

    private Customer newCustomer;
 
    @PostConstruct
    public void init() {
    	customers = customerService.getCustomers();
    }
    
    public void removeCustomer(Customer customer) {
    	
    }
    
    private void newCustomer() {
    	
    }
    
    /*
    public String addCustomer() {
        try {
            Customer customer = new Customer();
            customer.setId(getId());
            customer.setName(getName());
            customer.setSurname(getSurname());
            getCustomerService().addCustomer(customer);
            reset();
            return "/pages/secure/list.xhtml?faces-redirect=true";
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return "/pages/unsecure/error.xhtml?faces-redirect=true";
    }

    public String updateCustomer(Customer customer) {
        try {
            getCustomerService().updateCustomer(customer);
            return "/pages/secure/list.xhtml?faces-redirect=true";       
        } catch (DataAccessException e) {
            e.printStackTrace();       
        }    
        return "/pages/unsecure/error.xhtml?faces-redirect=true";
    } 
 
    public String deleteCustomer(Customer customer) {
        try {
            getCustomerService().deleteCustomer(customer);
            customerList = null;
            getCustomerList();
            return "/pages/secure/list.xhtml?faces-redirect=true";       
        } catch (DataAccessException e) {
            e.printStackTrace();       
        }    
        return "/pages/unsecure/error.xhtml?faces-redirect=true";
    }

    public void onEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Item Edited");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        updateCustomer((Customer)event.getObject());
    }
    
    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Item Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }   
 */
    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

}

