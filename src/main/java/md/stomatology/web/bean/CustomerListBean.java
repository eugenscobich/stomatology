package md.stomatology.web.bean;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

import md.stomatology.model.Customer;
import md.stomatology.repository.specification.SpecificationFilter;
import md.stomatology.service.CustomerService;
import md.stomatology.util.WebUtil;

@ManagedBean
@ViewScoped
@URLMapping(id = "customer-list", pattern = "/customer-list/", viewId = "/WEB-INF/views/pages/secure/customer-list.xhtml")
public class CustomerListBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static Logger LOG = LoggerFactory.getLogger(CustomerListBean.class);
    
    @ManagedProperty(value="#{customerService}")
    private CustomerService customerService;

    private LazyDataModel<Customer> customers;
    
    @URLAction(onPostback = false)
    public void loadCustomers() {
    	LOG.info("Load Customers");
    	try {
    		customers = new LazyCustomerDataModel();
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
	
	public class LazyCustomerDataModel extends LazyDataModel<Customer>{
		
		private static final long serialVersionUID = 1L;

		List<Customer> customers;
		
		@Override
	    public List<Customer> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
			
			Sort sort = null;
			if (StringUtils.isNotBlank(sortField) && sortOrder != SortOrder.UNSORTED) {
				Direction dir = sortOrder == SortOrder.ASCENDING ? Direction.ASC : Direction.DESC;
				sort = new Sort(dir, sortField);
			}
			
			int pageIndex = first / pageSize;
			
			Specifications<Customer> specifications = null;
			
			Iterator<Entry<String, Object>> filterIterator = null;
			if (filters.size() > 0) {
				filterIterator = filters.entrySet().iterator();
				Entry<String, Object> entry = filterIterator.next();
				specifications = Specifications.where(new SpecificationFilter<>(entry.getKey(), "like", entry.getValue()));
			}
			
			if (filterIterator != null) {
				for (; filterIterator.hasNext();) {
					Entry<String, Object> entry = filterIterator.next();
					specifications = specifications.and(new SpecificationFilter<>(entry.getKey(), "like", entry.getValue()));
				}
			}
			
			Page<Customer> page = customerService.getCustomers(specifications, pageIndex, pageSize, sort);
			customers = page.getContent(); 
			setRowCount((int) page.getTotalElements());
	        return customers; 
	    }
		
	}
	
}

