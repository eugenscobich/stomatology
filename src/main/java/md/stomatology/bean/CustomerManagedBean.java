package md.stomatology.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.springframework.dao.DataAccessException;

import md.stomatology.model.Customer;
import md.stomatology.service.CustomerService;

@ManagedBean(name="customerMB")
@RequestScoped
public class CustomerManagedBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value="#{CustomerService}")
    CustomerService customerService;

    List<Customer> customerList;

    private int id;
    private String name;
    private String surname;
 
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
 
    public void reset() {
        this.setId(0);
        this.setName("");
        this.setSurname("");
    }

    public List<Customer> getCustomerList() {
        if(customerList == null){
            customerList = new ArrayList<Customer>();
            customerList.addAll(getCustomerService().getCustomers());
        }
        return customerList;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}

