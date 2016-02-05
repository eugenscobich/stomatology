package md.stomatology.web.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

import md.stomatology.model.Customer;
import md.stomatology.service.CustomerService;
import md.stomatology.util.WebUtil;
import md.stomatology.web.model.LazyCustomerDataModel;

@ManagedBean
@ViewScoped
@URLMapping(id = "customer-list", pattern = "/customer-list/", viewId = "/WEB-INF/views/pages/secure/customer-list.xhtml")
public class CustomerBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static Logger LOG = LoggerFactory.getLogger(CustomerBean.class);
    
    @ManagedProperty(value="#{customerService}")
    private CustomerService customerService;

    private LazyDataModel<Customer> customers;
    
    @URLAction(onPostback = false)
    public void loadCustomers() {
    	LOG.info("Load Customers");
    	try {
    		customers = new LazyCustomerDataModel(customerService);
    	} catch (Exception e) {
    		LOG.error(e.getMessage(), e);
    	}
    }
    
    public String removeCustomer(Customer customer) {
    	try {
    		customerService.removeCustomer(customer);
    		//customers = customerService.getCustomers(); 
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			WebUtil.addErrorMessage("could-not-remove-customer", "error", new String[]{ e.getMessage() });
			return "";
		}
		WebUtil.addSuccessMessage("customer-has-removed-successfully");
		return "";
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public LazyDataModel<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(LazyDataModel<Customer> customers) {
		this.customers = customers;
	}
	
}

